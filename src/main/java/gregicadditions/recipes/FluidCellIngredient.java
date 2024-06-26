package gregicadditions.recipes;

import gregicadditions.item.GAMetaItems;
import gregtech.api.recipes.CountableIngredient;
import gregtech.api.unification.material.type.FluidMaterial;
import gregtech.common.items.MetaItems;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStackSimple;

import javax.annotation.Nullable;

public class FluidCellIngredient extends Ingredient {

    final Fluid fluid;

    public FluidCellIngredient(Fluid fluid) {
        super(GAMetaItems.getFilledCell(fluid, 1));
        this.fluid = fluid;
    }

    public static CountableIngredient getIngredient(Fluid fluid, int count) {
        return new CountableIngredient(new FluidCellIngredient(fluid), count);
    }

    public static CountableIngredient getIngredient(FluidMaterial fluidMaterial, int count) {
        return new CountableIngredient(new FluidCellIngredient(fluidMaterial.getMaterialFluid()), count);
    }

    @Override
    public boolean apply(@Nullable ItemStack itemStack) {
        if (itemStack != null) {
            IFluidHandlerItem stackFluid = itemStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
            FluidStack drained = stackFluid == null ? null : ((FluidHandlerItemStackSimple) stackFluid).getFluid();
            return MetaItems.FLUID_CELL.isItemEqual(itemStack) && drained != null && drained.getFluid() == fluid && drained.amount == 1000;
        } else return false;
    }

    @Override
    public boolean isSimple() {
        return false;
    }

}