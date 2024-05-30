package gregicadditions.machines;

import exnihilocreatio.ModFluids;
import gregtech.api.util.XSTR;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class ExNihiloInterface {
    private static final XSTR random = new XSTR();

    @Nullable
    static ItemStack getWitchWaterDrop(TileEntityRockBreaker instance) {
        if (instance.checkSides(Blocks.WATER) && instance.checkSides(ModFluids.blockWitchwater)) {
            return new ItemStack(Blocks.DIRT, 1, random.nextInt(3));
        }
        return null;
    }
}
