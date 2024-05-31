package gregicadditions.machines;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.SlotWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.TieredMetaTileEntity;
import gregtech.api.render.OrientedOverlayRenderer;
import gregtech.api.render.Textures;
import gregtech.api.util.GTUtility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TileEntityRockBreaker extends TieredMetaTileEntity {

    private final OrientedOverlayRenderer renderer;

    public TileEntityRockBreaker(ResourceLocation metaTileEntityId, OrientedOverlayRenderer renderer, int tier) {
        super(metaTileEntityId, tier);
        this.renderer = renderer;
        this.initializeInventory();
    }

    private int getInventorySize() {
        int sizeRoot = 1 + this.getTier();
        return sizeRoot * sizeRoot;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder holder) {
        return new TileEntityRockBreaker(metaTileEntityId, renderer, getTier());
    }

    @Override
    protected IItemHandlerModifiable createExportItemHandler() {
        return new ItemStackHandler(this.getInventorySize());
    }

    @Override
    public void update() {
        super.update();
        if (!getWorld().isRemote) {
            ItemStack output = getWitchWaterDrop();
            int largestSignal = 0;
            for (EnumFacing face : EnumFacing.VALUES)
                if (GTUtility.getRedstonePower(getWorld(), getPos(), face) > largestSignal)
                    largestSignal = GTUtility.getRedstonePower(getWorld(), getPos(), face);
            if (output == null && checkSides(Blocks.LAVA) && checkSides(Blocks.WATER)) {
                switch (largestSignal) {
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                        output = new ItemStack(Blocks.STONE, 1, 3);
                        break;
                    case 8:
                    case 9:
                    case 10:
                    case 11:
                        output = new ItemStack(Blocks.STONE, 1, 1);
                        break;
                    case 12:
                    case 13:
                    case 14:
                    case 15:
                        output = new ItemStack(Blocks.STONE, 1, 5);
                        break;
                    default:
                        output = new ItemStack(Blocks.COBBLESTONE);
                }
            }
            long energyToConsume = GTValues.V[getTier()] / 16;
            int waitTime = (int) Math.ceil(32 / (Math.pow(2, getTier())));
            if (output != null && getOffsetTimer() % waitTime == 0 && energyContainer.getEnergyStored() >= energyToConsume) {
                ItemHandlerHelper.insertItemStacked(this.exportItems, output, false);
                energyContainer.removeEnergy(energyToConsume);
            }
            if (getOffsetTimer() % 5 == 0) {
                pushItemsIntoNearbyHandlers(frontFacing);
            }
        }
    }

    @Nullable
    private ItemStack getWitchWaterDrop() {
        if (Loader.isModLoaded("exnihilocreatio"))
            return ExNihiloInterface.getWitchWaterDrop(this);
        return null;
    }

    boolean checkSides(Block liquid) {
        EnumFacing frontFacing = getFrontFacing();
        for (EnumFacing side : EnumFacing.VALUES) {
            if (side == frontFacing || side == EnumFacing.DOWN || side == EnumFacing.UP) continue;
            if (getWorld().getBlockState(getPos().offset(side)) == liquid.getDefaultState())
                return true;
        }
        return false;
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        renderer.render(renderState, translation, pipeline, getFrontFacing(), false);
        Textures.PIPE_OUT_OVERLAY.renderSided(frontFacing, renderState, translation, pipeline);
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        int rowSize = (int)Math.sqrt(this.getInventorySize());
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176, 18 + 18 * rowSize + 94).label(10, 5, this.getMetaFullName());

        for(int y = 0; y < rowSize; ++y) {
            for(int x = 0; x < rowSize; ++x) {
                int index = y * rowSize + x;
                builder.widget((new SlotWidget(this.exportItems, index, 89 - rowSize * 9 + x * 18, 18 + y * 18, true, false)).setBackgroundTexture(GuiTextures.SLOT));
            }
        }

        builder.bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 7, 18 + 18 * rowSize + 12);
        return builder.build(this.getHolder(), entityPlayer);
    }
}