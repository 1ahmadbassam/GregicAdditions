package gregicadditions.machines;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregicadditions.GAConfig;
import gregicadditions.GATextures;
import gregicadditions.item.GAMetaBlocks;
import gregicadditions.item.GAMultiblockCasing;
import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.capability.IWorkable;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.ItemHandlerList;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.gui.widgets.SlotWidget;
import gregtech.api.gui.widgets.TankWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import gregtech.api.multiblock.BlockPattern;
import gregtech.api.multiblock.FactoryBlockPattern;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.recipes.CokeOvenRecipe;
import gregtech.api.render.ICubeRenderer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Collections;

public class TileEntityCokeOven extends MultiblockControllerBase implements IWorkable {
    private int maxProgressDuration;
    private int currentProgress;
    private ItemStack outputItem;
    private FluidStack outputFluid;
    private boolean isActive;
    private boolean wasActiveAndNeedUpdate;
    private boolean isWorkingEnabled = true;

    private ItemStack lastInputStack = ItemStack.EMPTY;
    private CokeOvenRecipe previousRecipe;

    public TileEntityCokeOven(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    private static boolean areItemStacksEqual(ItemStack stackA, ItemStack stackB) {
        return (stackA.isEmpty() && stackB.isEmpty()) ||
                (ItemStack.areItemsEqual(stackA, stackB) &&
                        ItemStack.areItemStackTagsEqual(stackA, stackB));
    }

    @Override
    protected void updateFormedValid() {
        if (isWorkingEnabled) {
            if (GAConfig.Misc.cokeOvenInputBusEnable && getAbilities(MultiblockAbility.IMPORT_ITEMS) != null) {
                ItemHandlerList itemInput = new ItemHandlerList(this.getAbilities(MultiblockAbility.IMPORT_ITEMS));
                if (itemInput.getSlots() > 0 && !itemInput.getStackInSlot(0).isEmpty() && (importItems.getStackInSlot(0).isEmpty() || importItems.getStackInSlot(0).isItemEqual(itemInput.getStackInSlot(0)))) {
                    itemInput.setStackInSlot(0, ItemHandlerHelper.insertItemStacked(importItems, itemInput.getStackInSlot(0), false));
                }
            }
            if (getAbilities(MultiblockAbility.EXPORT_FLUIDS) != null) {
                FluidTankList fluidOutput = new FluidTankList(true, this.getAbilities(MultiblockAbility.EXPORT_FLUIDS));
                if (exportFluids.getTankAt(0).getFluidAmount() > 0 && !fluidOutput.getFluidTanks().isEmpty()) {
                    exportFluids.drain(fluidOutput.fill(exportFluids.getTankAt(0).getFluid(), true), true);
                }
            }
            if (getAbilities(MultiblockAbility.EXPORT_ITEMS) != null) {
                ItemHandlerList itemOutput = new ItemHandlerList(this.getAbilities(MultiblockAbility.EXPORT_ITEMS));
                if (!exportItems.getStackInSlot(0).isEmpty() && itemOutput.getSlots() > 0) {
                    exportItems.setStackInSlot(0, ItemHandlerHelper.insertItemStacked(itemOutput, exportItems.getStackInSlot(0), false));
                }
            }
            if (maxProgressDuration == 0) {
                if (tryPickNewRecipe()) {
                    if (wasActiveAndNeedUpdate) {
                        this.wasActiveAndNeedUpdate = false;
                    } else setActive(true);
                }
            } else if (++currentProgress >= maxProgressDuration) {
                finishCurrentRecipe();
                this.wasActiveAndNeedUpdate = true;
                return;
            }
            if (wasActiveAndNeedUpdate) {
                this.wasActiveAndNeedUpdate = false;
                setActive(false);
            }
        }
    }

    private void finishCurrentRecipe() {
        this.maxProgressDuration = 0;
        this.currentProgress = 0;
        MetaTileEntity.addItemsToItemHandler(exportItems, false, Collections.singletonList(outputItem));
        MetaTileEntity.addFluidsToFluidHandler(exportFluids, false, Collections.singletonList(outputFluid));
        this.outputItem = null;
        this.outputFluid = null;
        markDirty();
    }

    private boolean setupRecipe(ItemStack inputStack, CokeOvenRecipe recipe) {
        return inputStack.getCount() >= recipe.getInput().getCount() &&
                ItemHandlerHelper.insertItemStacked(exportItems, recipe.getOutput(), true).isEmpty() &&
                exportFluids.fill(recipe.getFluidOutput(), false) == recipe.getFluidOutput().amount;
    }

    private boolean tryPickNewRecipe() {
        ItemStack inputStack = importItems.getStackInSlot(0);
        if (inputStack.isEmpty()) {
            return false;
        }
        CokeOvenRecipe currentRecipe = getOrRefreshRecipe(inputStack);
        if (currentRecipe != null && setupRecipe(inputStack, currentRecipe)) {
            inputStack.shrink(currentRecipe.getInput().getCount());
            this.maxProgressDuration = currentRecipe.getDuration();
            this.currentProgress = 0;
            this.outputItem = currentRecipe.getOutput().copy();
            this.outputFluid = currentRecipe.getFluidOutput().copy();
            markDirty();
            return true;
        }
        return false;
    }

    private CokeOvenRecipe getOrRefreshRecipe(ItemStack inputStack) {
        CokeOvenRecipe currentRecipe = null;
        if (previousRecipe != null &&
                previousRecipe.getInput().getIngredient().apply(inputStack)) {
            currentRecipe = previousRecipe;
        } else if (!areItemStacksEqual(inputStack, lastInputStack)) {
            this.lastInputStack = inputStack.isEmpty() ? ItemStack.EMPTY : inputStack.copy();
            currentRecipe = RecipeMaps.COKE_OVEN_RECIPES.stream()
                    .filter(it -> it.getInput().getIngredient().test(inputStack))
                    .findFirst().orElse(null);
            if (currentRecipe != null) {
                this.previousRecipe = currentRecipe;
            }
        }
        return currentRecipe;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setBoolean("Active", isActive);
        data.setBoolean("WasActive", wasActiveAndNeedUpdate);
        data.setInteger("MaxProgress", maxProgressDuration);
        data.setBoolean("WorkingEnabled", isWorkingEnabled);
        if (maxProgressDuration > 0) {
            data.setInteger("Progress", currentProgress);
            NBTTagList itemOutputs = new NBTTagList();
            itemOutputs.appendTag(outputItem.writeToNBT(new NBTTagCompound()));
            data.setTag("Outputs", itemOutputs);
            NBTTagList fluidOutputs = new NBTTagList();
            fluidOutputs.appendTag(outputFluid.writeToNBT(new NBTTagCompound()));
            data.setTag("FluidOutputs", fluidOutputs);
        }
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.isActive = data.getBoolean("Active");
        this.wasActiveAndNeedUpdate = data.getBoolean("WasActive");
        this.maxProgressDuration = data.getInteger("MaxProgress");
        this.isWorkingEnabled = data.getBoolean("WorkingEnabled");
        if (maxProgressDuration > 0) {
            this.currentProgress = data.getInteger("Progress");
            NBTTagList itemOutputs = data.getTagList("Outputs", Constants.NBT.TAG_COMPOUND);
            outputItem = new ItemStack(itemOutputs.getCompoundTagAt(0));
            NBTTagList fluidOutputs = data.getTagList("FluidOutputs", Constants.NBT.TAG_COMPOUND);
            outputFluid = FluidStack.loadFluidStackFromNBT(fluidOutputs.getCompoundTagAt(0));
        }
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeBoolean(isActive);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.isActive = buf.readBoolean();
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == 100) {
            this.isActive = buf.readBoolean();
            getWorld().checkLight(getPos());
            getHolder().scheduleChunkForRenderUpdate();
        }
    }

    @Override
    public int getProgress() {
        return currentProgress;
    }

    @Override
    public int getMaxProgress() {
        return maxProgressDuration;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        this.isActive = active;
        if (!getWorld().isRemote) {
            writeCustomData(100, b -> b.writeBoolean(isActive));
            getWorld().checkLight(getPos());
        }
    }

    @Override
    public int getLightValueForPart(IMultiblockPart sourcePart) {
        return sourcePart == null && isActive ? 15 : 0;
    }

    public double getProgressScaled() {
        return maxProgressDuration == 0 ? 0.0 : (currentProgress / (maxProgressDuration * 1.0));
    }

    protected IBlockState getCasingState() {
        return GAMetaBlocks.MUTLIBLOCK_CASING.getState(GAMultiblockCasing.CasingType.COKE_OVEN_BRICKS);
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return GATextures.COKE_OVEN_BRICKS;
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        GATextures.COKE_OVEN_OVERLAY.render(renderState, translation, pipeline, getFrontFacing(), isActive());
    }

    @Override
    protected IItemHandlerModifiable createImportItemHandler() {
        return new ItemStackHandler(1);
    }

    @Override
    protected IItemHandlerModifiable createExportItemHandler() {
        return new ItemStackHandler(1);
    }

    @Override
    protected FluidTankList createExportFluidHandler() {
        return new FluidTankList(true, new FluidTank(64000));
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XXX", "XXX", "XXX")
                .aisle("XXX", "X#X", "XXX")
                .aisle("XXX", "XYX", "XXX")
                .setAmountAtLeast('X', 20)
                .where('X', statePredicate(getCasingState()).or(tilePredicate((state, tile) -> tile.metaTileEntityId.equals(GATileEntities.COKE_FLUID_HATCH.metaTileEntityId) || (GATileEntities.COKE_ITEM_IN_BUS != null && tile.metaTileEntityId.equals(GATileEntities.COKE_ITEM_IN_BUS.metaTileEntityId)) || tile.metaTileEntityId.equals(GATileEntities.COKE_ITEM_OUT_BUS.metaTileEntityId))))
                .where('#', isAirPredicate())
                .where('Y', selfPredicate())
                .build();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder holder) {
        return new TileEntityCokeOven(metaTileEntityId);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing side) {
        if (capability == GregtechTileCapabilities.CAPABILITY_WORKABLE) {
            return GregtechTileCapabilities.CAPABILITY_WORKABLE.cast(this);
        } else if (capability == GregtechTileCapabilities.CAPABILITY_CONTROLLABLE) {
            return GregtechTileCapabilities.CAPABILITY_CONTROLLABLE.cast(this);
        } else if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ||
                capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return null;
        }
        return super.getCapability(capability, side);
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        return ModularUI.builder(GuiTextures.BRONZE_BACKGROUND, 176, 166)
                //.image(11, 12, 17, 50, GuiTextures.PATTERN_BRONZE_BLAST_FURNACE)
                .widget(new SlotWidget(importItems, 0, 33, 24, true, true)
                        .setBackgroundTexture(GuiTextures.BRONZE_SLOT, GuiTextures.BRONZE_FURNACE_OVERLAY))
                .progressBar(this::getProgressScaled, 58, 24, 20, 15, GuiTextures.BRONZE_BLAST_FURNACE_PROGRESS_BAR, ProgressWidget.MoveType.HORIZONTAL)
                .widget(new SlotWidget(exportItems, 0, 85, 24, true, false)
                        .setBackgroundTexture(GuiTextures.BRONZE_SLOT, GATextures.COAL_OVERLAY))
                .widget(new TankWidget(exportFluids.getTankAt(0), 102, 23, 18, 18)
                        .setBackgroundTexture(GATextures.BRONZE_FLUID_SLOT).setAlwaysShowFull(true).setContainerClicking(true, false))
                .bindPlayerInventory(entityPlayer.inventory, GuiTextures.BRONZE_SLOT)
                .build(getHolder(), entityPlayer);
    }


    @Override
    public boolean isWorkingEnabled() {
        return isWorkingEnabled;
    }

    @Override
    public void setWorkingEnabled(boolean b) {
        isWorkingEnabled = b;
    }
}

