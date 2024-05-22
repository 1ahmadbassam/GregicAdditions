package gregicadditions;

import com.google.common.collect.ImmutableList;
import gregicadditions.item.BasicMaterial;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.Element;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.IMaterialHandler;
import gregtech.api.unification.material.MaterialIconSet;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.*;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import gregtech.common.items.MetaItems;
import knightminer.ceramics.Ceramics;
import knightminer.ceramics.blocks.BlockStained;
import knightminer.ceramics.items.ItemClayUnfired;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Tuple;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

@IMaterialHandler.RegisterMaterialHandler
public class GAMaterials implements IMaterialHandler {
    public static final List<Tuple<String, ItemStack>> oreDictionaryRemovals = new ArrayList<>();
    public static FluidMaterial FishOil;
    public static FluidMaterial RawGrowthMedium;
    public static FluidMaterial SterilizedGrowthMedium;
    public static DustMaterial Meat;
    public static FluidMaterial NeutralMatter;
    public static FluidMaterial PositiveMatter;
    public static IngotMaterial Neutronium;
    public static BasicMaterial Plasma;
    public static GemMaterial LigniteCoke;
    public static IngotMaterial MVSuperconductorBase;
    public static IngotMaterial HVSuperconductorBase;
    public static IngotMaterial EVSuperconductorBase;
    public static IngotMaterial IVSuperconductorBase;
    public static IngotMaterial LuVSuperconductorBase;
    public static IngotMaterial ZPMSuperconductorBase;
    public static IngotMaterial UVSuperconductorBase;
    public static BasicMaterial MVSuperconductor;
    public static BasicMaterial HVSuperconductor;
    public static BasicMaterial EVSuperconductor;
    public static BasicMaterial IVSuperconductor;
    public static BasicMaterial LuVSuperconductor;
    public static BasicMaterial ZPMSuperconductor;
    public static BasicMaterial UVSuperconductor;
    public static IngotMaterial Enderium;
    public static DustMaterial AluminoSilicateWool;
    public static DustMaterial MicaPulp;
    public static IngotMaterial Ultima;
    public static FluidMaterial IronChloride;
    public static DustMaterial QuartzSand;
    public static DustMaterial Massicot;
    public static DustMaterial AntimonyTrioxide;
    public static DustMaterial Zincite;
    public static DustMaterial CobaltOxide;
    public static DustMaterial ArsenicTrioxide;
    public static DustMaterial CupricOxide;
    public static DustMaterial Ferrosilite;

    public static IngotMaterial CompressedIron;
    public static IngotMaterial ToughAlloy;
    public static IngotMaterial SkySteel;
    public static IngotMaterial Ferroboron;

    public static DustMaterial Porcelain;

    public static void oreDictInit() {
        oreDictionaryRemovals.add(new Tuple<>("ingotClay", new ItemStack(Items.CLAY_BALL)));
        if (GAConfig.Misc.CeramicsIntegration && Loader.isModLoaded("ceramics")) {
            oreDictionaryRemovals.add(new Tuple<>("plateClay", new ItemStack(Ceramics.clayUnfired, 1, ItemClayUnfired.UnfiredType.CLAY_PLATE.getMeta())));
            oreDictionaryRemovals.add(new Tuple<>("plateClayRaw", new ItemStack(Ceramics.clayUnfired, 1, ItemClayUnfired.UnfiredType.CLAY_PLATE_RAW.getMeta())));
        }

        for (Tuple<String, ItemStack> entry : oreDictionaryRemovals) {
            for (ItemStack contained : OreDictionary.getOres(entry.getFirst())) {
                if (contained.getItem() == entry.getSecond().getItem() && contained.getMetadata() == entry.getSecond().getMetadata()) {
                    OreDictionary.getOres(entry.getFirst()).remove(contained);
                    break;
                }
            }
        }
        OreDictUnifier.registerOre(new ItemStack(Items.CLAY_BALL), OrePrefix.clump, Materials.Clay);
        if (GAConfig.Misc.CeramicsIntegration && Loader.isModLoaded("ceramics")) {
            OreDictUnifier.registerOre(new ItemStack(Ceramics.clayUnfired, 1, ItemClayUnfired.UnfiredType.CLAY_PLATE.getMeta()), OrePrefix.plate, Materials.Brick);
            OreDictUnifier.registerOre(new ItemStack(Ceramics.clayUnfired, 1, ItemClayUnfired.UnfiredType.CLAY_PLATE_RAW.getMeta()), OrePrefix.plate, Materials.Clay);
            OreDictUnifier.registerOre(new ItemStack(Ceramics.clayUnfired, 1, ItemClayUnfired.UnfiredType.PORCELAIN.getMeta()), OrePrefix.clump, GAMaterials.Porcelain);
            OreDictUnifier.registerOre(new ItemStack(Ceramics.clayUnfired, 1, ItemClayUnfired.UnfiredType.PORCELAIN_BRICK.getMeta()), OrePrefix.ingot, GAMaterials.Porcelain);
            OreDictUnifier.registerOre(new ItemStack(Ceramics.porcelain), OrePrefix.block, GAMaterials.Porcelain);
            for (int i = 1; i <= 15; i++) {
                OreDictUnifier.registerOre(new ItemStack(Ceramics.porcelain, 1, i), "blockPorcelainStained");
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
                OreDictUnifier.registerOre(new ItemStack(Ceramics.porcelain, 1, i), "blockPorcelain" + color);
            }
        }
    }

    public static void processGem(OrePrefix gemPrefix, GemMaterial material) {
        if (material.hasFlag(SolidMaterial.MatFlags.GENERATE_ROD)) {
            ModHandler.addShapedRecipe(String.format("stick_%s", material),
                    OreDictUnifier.get(OrePrefix.stick, material, 1),
                    "f ", " X",
                    'X', new UnificationEntry(gemPrefix, material));
            RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                    .input(gemPrefix, material)
                    .notConsumable(MetaItems.SHAPE_EXTRUDER_ROD)
                    .outputs(OreDictUnifier.get(OrePrefix.stick, material, 2))
                    .duration((int) material.getAverageMass() * 2)
                    .EUt(6 * getVoltageMultiplier(material))
                    .buildAndRegister();

        }
    }

    private static int getVoltageMultiplier(Material material) {
        return material instanceof IngotMaterial && ((IngotMaterial) material)
                .blastFurnaceTemperature >= 2800 ? 32 : 8;
    }

    @Override
    public void onMaterialsInit() {
        long STD_SOLID = DustMaterial.MatFlags.GENERATE_PLATE | SolidMaterial.MatFlags.GENERATE_ROD | IngotMaterial.MatFlags.GENERATE_BOLT_SCREW | SolidMaterial.MatFlags.GENERATE_LONG_ROD;
        long STD_METAL = DustMaterial.MatFlags.GENERATE_PLATE;
        long EXT_METAL = STD_METAL | gregtech.api.unification.material.type.SolidMaterial.MatFlags.GENERATE_ROD |
                gregtech.api.unification.material.type.IngotMaterial.MatFlags.GENERATE_BOLT_SCREW;
        long EXT2_METAL = EXT_METAL | gregtech.api.unification.material.type.SolidMaterial.MatFlags.GENERATE_GEAR |
                gregtech.api.unification.material.type.IngotMaterial.MatFlags.GENERATE_FOIL |
                gregtech.api.unification.material.type.IngotMaterial.MatFlags.GENERATE_FINE_WIRE;

        FishOil = new FluidMaterial(999, "fish_oil", 14467421, MaterialIconSet.FLUID, ImmutableList.of(), FluidMaterial.MatFlags.GENERATE_FLUID_BLOCK | Material.MatFlags.DISABLE_DECOMPOSITION);
        Neutronium = new IngotMaterial(998, "neutronium", 12829635, MaterialIconSet.METALLIC, 6, ImmutableList.of(), EXT2_METAL | gregtech.api.unification.material.type.IngotMaterial.MatFlags.GENERATE_RING | gregtech.api.unification.material.type.IngotMaterial.MatFlags.GENERATE_ROTOR | gregtech.api.unification.material.type.IngotMaterial.MatFlags.GENERATE_SMALL_GEAR | gregtech.api.unification.material.type.SolidMaterial.MatFlags.GENERATE_LONG_ROD | SolidMaterial.MatFlags.GENERATE_FRAME, Element.valueOf("Nt"), 24.0F, 12, 655360);
        RawGrowthMedium = new FluidMaterial(997, "raw_growth_medium", 10777425, MaterialIconSet.FLUID, ImmutableList.of(), FluidMaterial.MatFlags.GENERATE_FLUID_BLOCK | Material.MatFlags.DISABLE_DECOMPOSITION);
        SterilizedGrowthMedium = new FluidMaterial(996, "sterilized_growth_medium", 11306862, MaterialIconSet.FLUID, ImmutableList.of(), FluidMaterial.MatFlags.GENERATE_FLUID_BLOCK | Material.MatFlags.DISABLE_DECOMPOSITION);
        Meat = new DustMaterial(995, "meat", 12667980, MaterialIconSet.SAND, 1, ImmutableList.of(), Material.MatFlags.DISABLE_DECOMPOSITION);
        NeutralMatter = new FluidMaterial(993, "neutral_matter", 3956968, MaterialIconSet.FLUID, ImmutableList.of(), Material.MatFlags.DISABLE_DECOMPOSITION);
        PositiveMatter = new FluidMaterial(992, "positive_matter", 11279131, MaterialIconSet.FLUID, ImmutableList.of(), Material.MatFlags.DISABLE_DECOMPOSITION);
        Plasma = new BasicMaterial(990, "plasma", 15389725, MaterialIconSet.SHINY);
        LigniteCoke = new GemMaterial(989, "lignite_coke", 0x8b6464, MaterialIconSet.LIGNITE, 1, ImmutableList.of(new MaterialStack(Materials.Carbon, 1)), Material.MatFlags.DECOMPOSITION_BY_ELECTROLYZING | SolidMaterial.MatFlags.MORTAR_GRINDABLE | Material.MatFlags.FLAMMABLE | DustMaterial.MatFlags.NO_SMELTING | DustMaterial.MatFlags.NO_SMASHING);
        LigniteCoke.setBurnTime(2400);
        MVSuperconductorBase = new IngotMaterial(988, "mv_superconductor_base", 0x535353, MaterialIconSet.SHINY, 1, ImmutableList.of(new MaterialStack(Materials.Cadmium, 5), new MaterialStack(Materials.Magnesium, 1), new MaterialStack(Materials.Oxygen, 6)), STD_METAL, null, 2500);
        HVSuperconductorBase = new IngotMaterial(987, "hv_superconductor_base", 0x4a2400, MaterialIconSet.SHINY, 1, ImmutableList.of(new MaterialStack(Materials.Titanium, 1), new MaterialStack(Materials.Barium, 9), new MaterialStack(Materials.Copper, 10), new MaterialStack(Materials.Oxygen, 20)), STD_METAL, null, 3300);
        EVSuperconductorBase = new IngotMaterial(986, "ev_superconductor_base", 0x005800, MaterialIconSet.SHINY, 1, ImmutableList.of(new MaterialStack(Materials.Uranium, 1), new MaterialStack(Materials.Platinum, 3)), STD_METAL, null, 4400);
        IVSuperconductorBase = new IngotMaterial(985, "iv_superconductor_base", 0x300030, MaterialIconSet.SHINY, 1, ImmutableList.of(new MaterialStack(Materials.Vanadium, 1), new MaterialStack(Materials.Indium, 3)), STD_METAL, null, 5200);
        LuVSuperconductorBase = new IngotMaterial(984, "luv_superconductor_base", 0x7a3c00, MaterialIconSet.SHINY, 1, ImmutableList.of(new MaterialStack(Materials.Indium, 4), new MaterialStack(Materials.Bronze, 8), new MaterialStack(Materials.Barium, 2), new MaterialStack(Materials.Titanium, 1), new MaterialStack(Materials.Oxygen, 14)), STD_METAL, null, 6000);
        ZPMSuperconductorBase = new IngotMaterial(983, "zpm_superconductor_base", 0x111111, MaterialIconSet.SHINY, 1, ImmutableList.of(new MaterialStack(Materials.Naquadah, 4), new MaterialStack(Materials.Indium, 2), new MaterialStack(Materials.Palladium, 6), new MaterialStack(Materials.Osmium, 1)), STD_METAL, null, 8100);
        MVSuperconductor = new BasicMaterial(982, "mv_superconductor", 0x535353, MaterialIconSet.SHINY);
        HVSuperconductor = new BasicMaterial(981, "hv_superconductor", 0x4a2400, MaterialIconSet.SHINY);
        EVSuperconductor = new BasicMaterial(980, "ev_superconductor", 0x005800, MaterialIconSet.SHINY);
        IVSuperconductor = new BasicMaterial(979, "iv_superconductor", 0x300030, MaterialIconSet.SHINY);
        LuVSuperconductor = new BasicMaterial(978, "luv_superconductor", 0x7a3c00, MaterialIconSet.SHINY);
        ZPMSuperconductor = new BasicMaterial(977, "zpm_superconductor", 0x111111, MaterialIconSet.SHINY);
        Enderium = new IngotMaterial(976, "enderium", 0x23524a, MaterialIconSet.METALLIC, 3, ImmutableList.of(new MaterialStack(Materials.Lead, 3), new MaterialStack(Materials.Platinum, 1), new MaterialStack(Materials.EnderPearl, 1)), EXT_METAL | Material.MatFlags.DISABLE_DECOMPOSITION, null, 8.0F, 3.0F, 1280, 4500);
        AluminoSilicateWool = new DustMaterial(975, "alumino_silicate_wool", 0xbbbbbb, MaterialIconSet.SAND, 1, ImmutableList.of(), Material.MatFlags.DISABLE_DECOMPOSITION);
        MicaPulp = new DustMaterial(974, "mica_based", 0x917445, MaterialIconSet.SAND, 1, ImmutableList.of(), Material.MatFlags.DISABLE_DECOMPOSITION);
        Ultima = new IngotMaterial(973, "ultima", 0x443d4f, MaterialIconSet.SHINY, 3, ImmutableList.of(new MaterialStack(Materials.TinAlloy, 1), new MaterialStack(Materials.Ultimet, 1), new MaterialStack(Materials.Magnalium, 1), new MaterialStack(Materials.BlueSteel, 1), new MaterialStack(Materials.VanadiumSteel, 1), new MaterialStack(Materials.NiobiumNitride, 1), new MaterialStack(Materials.NaquadahAlloy, 1)), EXT2_METAL | Material.MatFlags.DISABLE_DECOMPOSITION, null, 9000);
        IronChloride = new FluidMaterial(972, "iron_chloride", 0x060b0b, MaterialIconSet.FLUID, ImmutableList.of(new MaterialStack(Materials.Iron, 1), new MaterialStack(Materials.Chlorine, 3)), Material.MatFlags.DECOMPOSITION_BY_ELECTROLYZING);
        QuartzSand = new DustMaterial(971, "sand", 0xd2cfbc, MaterialIconSet.SAND, 1, ImmutableList.of(), Material.MatFlags.DISABLE_DECOMPOSITION);
        Massicot = new DustMaterial(970, "massicot", 8943149, MaterialIconSet.SAND, 1, ImmutableList.of(new MaterialStack(Materials.Lead, 1), new MaterialStack(Materials.Oxygen, 1)), 0);
        AntimonyTrioxide = new DustMaterial(969, "antimony_trioxide", 8092544, MaterialIconSet.SAND, 1, ImmutableList.of(new MaterialStack(Materials.Antimony, 2), new MaterialStack(Materials.Oxygen, 3)), 0);
        Zincite = new DustMaterial(968, "zincite", 8947843, MaterialIconSet.SAND, 1, ImmutableList.of(new MaterialStack(Materials.Zinc, 1), new MaterialStack(Materials.Oxygen, 1)), 0);
        CobaltOxide = new DustMaterial(967, "cobalt_oxide", 3556352, MaterialIconSet.SAND, 1, ImmutableList.of(new MaterialStack(Materials.Cobalt, 1), new MaterialStack(Materials.Oxygen, 1)), 0);
        ArsenicTrioxide = new DustMaterial(966, "arsenic_trioxide", 15856113, MaterialIconSet.ROUGH, 1, ImmutableList.of(new MaterialStack(Materials.Arsenic, 2), new MaterialStack(Materials.Oxygen, 3)), 0);
        CupricOxide = new DustMaterial(964, "cupric_oxide", 526344, MaterialIconSet.SAND, 1, ImmutableList.of(new MaterialStack(Materials.Copper, 1), new MaterialStack(Materials.Oxygen, 1)), 0);
        Ferrosilite = new DustMaterial(963, "ferrosilite", 5256470, MaterialIconSet.SAND, 1, ImmutableList.of(new MaterialStack(Materials.Iron, 1), new MaterialStack(Materials.Silicon, 1), new MaterialStack(Materials.Oxygen, 3)), 0);
        UVSuperconductorBase = new IngotMaterial(962, "uv_superconductor_base", 0xff0000, MaterialIconSet.SHINY, 3, ImmutableList.of(new MaterialStack(Materials.NaquadahAlloy, 6), new MaterialStack(Materials.Ultimet, 4), new MaterialStack(Materials.Americium, 2), new MaterialStack(Materials.Tritanium, 2)), EXT2_METAL | Material.MatFlags.DISABLE_DECOMPOSITION, null, 9000);
        UVSuperconductor = new BasicMaterial(961, "uv_superconductor", 0xff0000, MaterialIconSet.SHINY);

        Ferroboron = new IngotMaterial(699, "ferroboron", 0x535353, MaterialIconSet.METALLIC, 2, ImmutableList.of(new MaterialStack(Materials.Steel, 1), new MaterialStack(Materials.Boron, 1)), STD_METAL);
        CompressedIron = new IngotMaterial(700, "iron_compressed", 0x6f6f6f, MaterialIconSet.DULL, 2, ImmutableList.of(), STD_SOLID | EXT2_METAL, Element.Fe);
        ToughAlloy = new IngotMaterial(701, "tough", 0x171221, MaterialIconSet.METALLIC, 2, ImmutableList.of(new MaterialStack(Ferroboron, 1), new MaterialStack(Materials.Lithium, 1)), STD_METAL);
        SkySteel = new IngotMaterial(702, "sky_steel", 0x797979, MaterialIconSet.SHINY, 4, ImmutableList.of(), STD_SOLID, Element.Fe, 7.0F, 3.0f, 900, 1850);

        Materials.Boron.addFlag(DustMaterial.MatFlags.SMELT_INTO_FLUID);

        MVSuperconductorBase.setCableProperties(128, 1, 2);
        HVSuperconductorBase.setCableProperties(512, 1, 2);
        EVSuperconductorBase.setCableProperties(2048, 2, 2);
        IVSuperconductorBase.setCableProperties(8192, 2, 2);
        LuVSuperconductorBase.setCableProperties(32768, 4, 2);
        ZPMSuperconductorBase.setCableProperties(131072, 4, 2);
        UVSuperconductorBase.setCableProperties(524288, 3, 2);

        Ultima.setCableProperties(524288, 3, 2);

        Materials.Copper.addFlag(SolidMaterial.MatFlags.GENERATE_LONG_ROD);
        Materials.Copper.addFlag(IngotMaterial.MatFlags.GENERATE_SPRING);
        Materials.Bronze.addFlag(SolidMaterial.MatFlags.GENERATE_LONG_ROD);
        Materials.Bronze.addFlag(IngotMaterial.MatFlags.GENERATE_SPRING);
        Materials.Brass.addFlag(SolidMaterial.MatFlags.GENERATE_LONG_ROD);
        Materials.Brass.addFlag(IngotMaterial.MatFlags.GENERATE_SPRING);

        Materials.Iron.addFlag(IngotMaterial.MatFlags.GENERATE_SMALL_GEAR);

        Materials.NiobiumTitanium.setFluidPipeProperties(450, 2900, true);
        Enderium.setFluidPipeProperties(650, 1500, true);
        Materials.Naquadah.setFluidPipeProperties(1000, 19000, true);
        Neutronium.setFluidPipeProperties(2800, 1000000, true);

        Materials.Diatomite.addFlag(DustMaterial.MatFlags.GENERATE_ORE);
        Materials.GarnetSand.addFlag(DustMaterial.MatFlags.GENERATE_ORE);
        Materials.Mica.addFlag(DustMaterial.MatFlags.GENERATE_ORE);
        Materials.Asbestos.addFlag(DustMaterial.MatFlags.GENERATE_ORE);
        Materials.Kyanite.addFlag(DustMaterial.MatFlags.GENERATE_ORE);
        Materials.Pollucite.addFlag(DustMaterial.MatFlags.GENERATE_ORE);
        Materials.BasalticMineralSand.addFlag(DustMaterial.MatFlags.GENERATE_ORE);
        Materials.GraniticMineralSand.addFlag(DustMaterial.MatFlags.GENERATE_ORE);
        Materials.FullersEarth.addFlag(DustMaterial.MatFlags.GENERATE_ORE);
        Materials.Gypsum.addFlag(DustMaterial.MatFlags.GENERATE_ORE);
        Materials.Zeolite.addFlag(DustMaterial.MatFlags.GENERATE_ORE);
        Materials.Kaolinite.addFlag(DustMaterial.MatFlags.GENERATE_ORE);
        Materials.Dolomite.addFlag(DustMaterial.MatFlags.GENERATE_ORE);
        Materials.Wollastonite.addFlag(DustMaterial.MatFlags.GENERATE_ORE);
        Materials.Trona.addFlag(DustMaterial.MatFlags.GENERATE_ORE);
        Materials.Andradite.addFlag(DustMaterial.MatFlags.GENERATE_ORE);
        Materials.Vermiculite.addFlag(DustMaterial.MatFlags.GENERATE_ORE);
        Materials.Alunite.addFlag(DustMaterial.MatFlags.GENERATE_ORE);
        Materials.GlauconiteSand.addFlag(DustMaterial.MatFlags.GENERATE_ORE);

        Materials.Naquadah.addFlag(IngotMaterial.MatFlags.GENERATE_FOIL);
        Materials.NaquadahEnriched.addFlag(IngotMaterial.MatFlags.GENERATE_FOIL);
        Materials.Duranium.addFlag(IngotMaterial.MatFlags.GENERATE_FOIL);
        Materials.Graphene.addFlag(IngotMaterial.MatFlags.GENERATE_FOIL);

        Materials.GreenSapphire.addFlag(DustMaterial.MatFlags.GENERATE_PLATE);

        Materials.Apatite.addFlag(SolidMaterial.MatFlags.GENERATE_ROD);
        Materials.EnderEye.addFlag(SolidMaterial.MatFlags.GENERATE_ROD);
        Materials.Plastic.addFlag(SolidMaterial.MatFlags.GENERATE_ROD);

        Materials.Rubber.addFlag(IngotMaterial.MatFlags.GENERATE_BOLT_SCREW);
        Materials.Apatite.addFlag(IngotMaterial.MatFlags.GENERATE_BOLT_SCREW);

        Materials.Tritanium.addFlag(SolidMaterial.MatFlags.GENERATE_FRAME);

        Materials.Tantalum.addFlag(IngotMaterial.MatFlags.GENERATE_DENSE);

        Materials.NitroFuel.addFlag(Material.MatFlags.DISABLE_DECOMPOSITION);
        Materials.Ash.addFlag(Material.MatFlags.DISABLE_DECOMPOSITION);

        Materials.Flint.addFlag(SolidMaterial.MatFlags.MORTAR_GRINDABLE);
        Materials.Diamond.addFlag(GAMatFlags.GENERATE_CURVED_PLATE);

        for (Material m : IngotMaterial.MATERIAL_REGISTRY)
            if (m instanceof IngotMaterial)
                m.addFlag(GAMatFlags.IS_INGOT_AVAILABLE);
        Materials.Clay.addFlag(GAMatFlags.IS_INGOT_AVAILABLE);
        Materials.Brick.addFlag(GAMatFlags.IS_INGOT_AVAILABLE);

        Materials.Coal.addFlag(DustMaterial.MatFlags.SMELT_INTO_FLUID);
        Materials.Diamond.addFlag(DustMaterial.MatFlags.SMELT_INTO_FLUID);

        if ((GAConfig.Misc.CeramicsIntegration && Loader.isModLoaded("ceramics")) || (Loader.isModLoaded("tconstruct")))
            Materials.Clay.addFlag(DustMaterial.MatFlags.SMELT_INTO_FLUID);
        if (GAConfig.Misc.CeramicsIntegration && Loader.isModLoaded("ceramics")) {
            Porcelain = new DustMaterial(703, "porcelain", 0xdfe7e6, MaterialIconSet.FINE, 1, ImmutableList.of(new MaterialStack(Materials.Clay, 1), new MaterialStack(Materials.Bone, 1)), SolidMaterial.MatFlags.MORTAR_GRINDABLE | STD_METAL | Material.MatFlags.DECOMPOSITION_BY_CENTRIFUGING);
            Materials.Clay.addFlag(DustMaterial.MatFlags.GENERATE_PLATE);
            Materials.Brick.addFlag(DustMaterial.MatFlags.GENERATE_PLATE);
            if (GAConfig.GT6.BendingCurvedPlates) {
                Materials.Clay.addFlag(GAMatFlags.GENERATE_CURVED_PLATE);
            }
            Porcelain.addFlag(GAMatFlags.IS_INGOT_AVAILABLE);
            if (GAConfig.GT6.PlateDoubleIngot)
                Porcelain.addFlag(GAMatFlags.GENERATE_DOUBLE_INGOT);
        }

        OrePrefix.gemChipped.setIgnored(LigniteCoke);
        OrePrefix.gemFlawed.setIgnored(LigniteCoke);
        OrePrefix.gemFlawless.setIgnored(LigniteCoke);
        OrePrefix.gemExquisite.setIgnored(LigniteCoke);
    }

    public static class GAMatFlags {
        public static final long GENERATE_CURVED_PLATE = GTUtility.createFlag(50);
        public static final long GENERATE_DOUBLE_INGOT = GTUtility.createFlag(51);
        public static final long GENERATE_ROUND = GTUtility.createFlag(52);
        public static final long IS_INGOT_AVAILABLE = GTUtility.createFlag(53);
    }
}