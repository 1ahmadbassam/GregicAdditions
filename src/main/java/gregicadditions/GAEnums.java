package gregicadditions;

import gregtech.api.GTValues;
import gregtech.api.unification.Element;
import gregtech.api.unification.material.MaterialIconSet;
import gregtech.api.unification.material.MaterialIconType;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.IngotMaterial;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.ore.OrePrefix;
import net.minecraftforge.common.util.EnumHelper;

import java.util.function.Predicate;

public class GAEnums {
    public static void preInit() {
        EnumHelper.addEnum(MaterialIconType.class, "plateCurved", new Class[0]);
        EnumHelper.addEnum(MaterialIconType.class, "ingotDouble", new Class[0]);
        EnumHelper.addEnum(MaterialIconType.class, "round", new Class[0]);


        EnumHelper.addEnum(Element.class, "Nt", new Class[]{long.class, long.class, long.class, String.class, String.class, boolean.class}, 0L, 5000L, -1L, null, "NEUTRONIUM", false);

        EnumHelper.addEnum(MaterialIconSet.class, "COKE", new Class[0]);

        EnumHelper.addEnum(OrePrefix.class, "plateCurved",
                new Class[]{String.class, long.class, Material.class, MaterialIconType.class, long.class, Predicate.class},
                "Curved Plate", GTValues.M, null, MaterialIconType.valueOf("plateCurved"), OrePrefix.Flags.ENABLE_UNIFICATION,
                pred((mat) -> ((mat instanceof IngotMaterial && mat.hasFlag(DustMaterial.MatFlags.GENERATE_PLATE)) || (mat.hasFlag(DustMaterial.MatFlags.GENERATE_PLATE) && mat.hasFlag(GAMaterials.GAMatFlags.GENERATE_CURVED_PLATE)))));

        EnumHelper.addEnum(OrePrefix.class, "ingotDouble",
                new Class[]{String.class, long.class, Material.class, MaterialIconType.class, long.class, Predicate.class},
                "Double Ingot", GTValues.M * 2, null, MaterialIconType.valueOf("ingotDouble"), OrePrefix.Flags.ENABLE_UNIFICATION,
                pred((mat) -> ((mat instanceof IngotMaterial && mat.hasFlag(DustMaterial.MatFlags.GENERATE_PLATE)) || (mat.hasFlag(GAMaterials.GAMatFlags.GENERATE_DOUBLE_INGOT) && mat.hasFlag(GAMaterials.GAMatFlags.IS_INGOT_AVAILABLE)))));
        EnumHelper.addEnum(OrePrefix.class, "round",
                new Class[]{String.class, long.class, Material.class, MaterialIconType.class, long.class, Predicate.class},
                "Round", GTValues.M / 9, null, MaterialIconType.valueOf("round"), OrePrefix.Flags.ENABLE_UNIFICATION,
                pred((mat) -> ((mat instanceof IngotMaterial && mat.hasFlag(DustMaterial.MatFlags.GENERATE_PLATE)) || (mat.hasFlag(GAMaterials.GAMatFlags.GENERATE_ROUND)))));
        EnumHelper.addEnum(OrePrefix.class, "clay",
                new Class[]{String.class, long.class, Material.class, MaterialIconType.class, long.class, Predicate.class},
                "Clay", GTValues.M, null, null, OrePrefix.Flags.ENABLE_UNIFICATION,
                pred((mat) -> false));

    }

    private static Predicate<Material> pred(Predicate<Material> in) {
        return in;
    }
}
