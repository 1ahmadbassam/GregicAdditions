package gregicadditions;

import gregtech.api.unification.material.Materials;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.common.items.MetaItems;
import knightminer.ceramics.blocks.BlockStained;
import net.minecraft.item.ItemStack;

public final class GAUtils {

    public static final ItemStack[] molds = {
            MetaItems.SHAPE_MOLD_ANVIL.getStackForm(),
            MetaItems.SHAPE_MOLD_BALL.getStackForm(),
            MetaItems.SHAPE_MOLD_BLOCK.getStackForm(),
            MetaItems.SHAPE_MOLD_BOTTLE.getStackForm(),
            MetaItems.SHAPE_MOLD_CREDIT.getStackForm(),
            MetaItems.SHAPE_MOLD_CYLINDER.getStackForm(),
            MetaItems.SHAPE_MOLD_GEAR.getStackForm(),
            MetaItems.SHAPE_MOLD_INGOT.getStackForm(),
            MetaItems.SHAPE_MOLD_NAME.getStackForm(),
            MetaItems.SHAPE_MOLD_NUGGET.getStackForm(),
            MetaItems.SHAPE_MOLD_PLATE.getStackForm(),
            MetaItems.SHAPE_MOLD_GEAR_SMALL.getStackForm(),
            MetaItems.SHAPE_MOLD_ROTOR.getStackForm(),
            MetaItems.SHAPE_EXTRUDER_AXE.getStackForm(),
            MetaItems.SHAPE_EXTRUDER_BLOCK.getStackForm(),
            MetaItems.SHAPE_EXTRUDER_BOLT.getStackForm(),
            MetaItems.SHAPE_EXTRUDER_BOTTLE.getStackForm(),
            MetaItems.SHAPE_EXTRUDER_CELL.getStackForm(),
            MetaItems.SHAPE_EXTRUDER_FILE.getStackForm(),
            MetaItems.SHAPE_EXTRUDER_GEAR.getStackForm(),
            MetaItems.SHAPE_EXTRUDER_HAMMER.getStackForm(),
            MetaItems.SHAPE_EXTRUDER_HOE.getStackForm(),
            MetaItems.SHAPE_EXTRUDER_INGOT.getStackForm(),
            MetaItems.SHAPE_EXTRUDER_PIPE_LARGE.getStackForm(),
            MetaItems.SHAPE_EXTRUDER_PIPE_MEDIUM.getStackForm(),
            MetaItems.SHAPE_EXTRUDER_PICKAXE.getStackForm(),
            MetaItems.SHAPE_EXTRUDER_PLATE.getStackForm(),
            MetaItems.SHAPE_EXTRUDER_RING.getStackForm(),
            MetaItems.SHAPE_EXTRUDER_ROD.getStackForm(),
            MetaItems.SHAPE_EXTRUDER_SAW.getStackForm(),
            MetaItems.SHAPE_EXTRUDER_SHOVEL.getStackForm(),
            MetaItems.SHAPE_EXTRUDER_PIPE_SMALL.getStackForm(),
            MetaItems.SHAPE_EXTRUDER_SWORD.getStackForm(),
            MetaItems.SHAPE_EXTRUDER_PIPE_TINY.getStackForm(),
            MetaItems.SHAPE_EXTRUDER_WIRE.getStackForm()
    };
    public static final MaterialStack[] solderingList = {
            new MaterialStack(Materials.Tin, 2L),
            new MaterialStack(Materials.SolderingAlloy, 1L),
            new MaterialStack(Materials.Lead, 4L)
    };
    public static final MaterialStack[] sawLubricants = {
            new MaterialStack(Materials.Lubricant, 1L),
            new MaterialStack(Materials.DistilledWater, 3L),
            new MaterialStack(Materials.Water, 4L)
    };
    public static final MaterialStack[] cableFluids = {
            new MaterialStack(Materials.Rubber, 144),
            new MaterialStack(Materials.StyreneButadieneRubber, 108),
            new MaterialStack(Materials.SiliconeRubber, 72)
    };
    public static final MaterialStack[] cableDusts = {
            new MaterialStack(Materials.Polydimethylsiloxane, 1),
            new MaterialStack(Materials.PolyvinylChloride, 1)
    };
    public static final MaterialStack[] firstMetal = {
            new MaterialStack(Materials.Iron, 1),
            new MaterialStack(Materials.Nickel, 1),
            new MaterialStack(Materials.Invar, 2),
            new MaterialStack(Materials.Steel, 2),
            new MaterialStack(Materials.StainlessSteel, 3),
            new MaterialStack(Materials.Titanium, 3),
            new MaterialStack(Materials.Tungsten, 4),
            new MaterialStack(Materials.TungstenSteel, 5)
    };
    public static final MaterialStack[] lastMetal = {
            new MaterialStack(Materials.Tin, 0),
            new MaterialStack(Materials.Zinc, 0),
            new MaterialStack(Materials.Aluminium, 1)
    };
    public static final MaterialStack[] ironOres = {
            new MaterialStack(Materials.Pyrite, 1),
            new MaterialStack(Materials.BrownLimonite, 1),
            new MaterialStack(Materials.YellowLimonite, 1),
            new MaterialStack(Materials.Magnetite, 1),
            new MaterialStack(Materials.Iron, 1)
    };
    public static final MaterialStack[] lapisLike = {
            new MaterialStack(Materials.Lapis, 1),
            new MaterialStack(Materials.Lazurite, 1),
            new MaterialStack(Materials.Sodalite, 1)
    };

    private GAUtils() {
    }

    public static String getColorFromMeta(int i) {
        StringBuilder color = new StringBuilder(BlockStained.StainedColor.fromMeta(i).getName().toLowerCase());
        if (color.toString().contains("_")) {
            int idx = color.toString().indexOf('_');
            color.deleteCharAt(idx);
            color.setCharAt(idx, Character.toUpperCase(color.charAt(idx)));
        }
        if (color.length() > 0)
            color.setCharAt(0, Character.toUpperCase(color.charAt(0)));
        if (color.toString().equals("Silver")) {
            color.setLength(0);
            color.append("LightGray");
        }
        return color.toString();
    }
}
