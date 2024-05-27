package gregicadditions.integration;

import gregicadditions.GAConfig;
import gregicadditions.GAMaterials;
import gregicadditions.GAUtils;
import gregicadditions.item.GAMetaItems;
import gregtech.api.GTValues;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.items.MetaItems;
import knightminer.ceramics.Ceramics;
import knightminer.ceramics.items.ItemClayUnfired;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CeramicsIntegration {
    public static final List<Tuple<String, ItemStack>> oreDictionaryRemovals = new ArrayList<>();

    public static void oreDictInit() {
        oreDictionaryRemovals.add(new Tuple<>("plateClay", new ItemStack(Ceramics.clayUnfired, 1, ItemClayUnfired.UnfiredType.CLAY_PLATE.getMeta())));
        oreDictionaryRemovals.add(new Tuple<>("plateClayRaw", new ItemStack(Ceramics.clayUnfired, 1, ItemClayUnfired.UnfiredType.CLAY_PLATE_RAW.getMeta())));

        GAUtils.oreDictRemoval(oreDictionaryRemovals);

        OreDictUnifier.registerOre(new ItemStack(Ceramics.clayUnfired, 1, ItemClayUnfired.UnfiredType.CLAY_PLATE.getMeta()), OrePrefix.plate, Materials.Brick);
        OreDictUnifier.registerOre(new ItemStack(Ceramics.clayUnfired, 1, ItemClayUnfired.UnfiredType.CLAY_PLATE_RAW.getMeta()), OrePrefix.plate, Materials.Clay);
        OreDictUnifier.registerOre(new ItemStack(Ceramics.clayUnfired, 1, ItemClayUnfired.UnfiredType.PORCELAIN.getMeta()), OrePrefix.clump, GAMaterials.Porcelain);
        OreDictUnifier.registerOre(new ItemStack(Ceramics.clayUnfired, 1, ItemClayUnfired.UnfiredType.PORCELAIN_BRICK.getMeta()), OrePrefix.ingot, GAMaterials.Porcelain);
        OreDictUnifier.registerOre(new ItemStack(Ceramics.porcelain), OrePrefix.block, GAMaterials.Porcelain);
        for (int i = 1; i <= 15; i++) {
            OreDictUnifier.registerOre(new ItemStack(Ceramics.porcelain, 1, i), "blockPorcelainStained");
            String color = GAUtils.getColorFromMeta(i);
            OreDictUnifier.registerOre(new ItemStack(Ceramics.porcelain, 1, i), "blockPorcelain" + color);
        }
    }

    public static void init() {
        ModHandler.removeFurnaceSmelting(new ItemStack(Ceramics.clayUnfired, 1, ItemClayUnfired.UnfiredType.PORCELAIN.getMeta()));
        ModHandler.removeFurnaceSmelting(new ItemStack(Ceramics.clayUnfired, 1, ItemClayUnfired.UnfiredType.CLAY_PLATE_RAW.getMeta()));
        ModHandler.addSmeltingRecipe(GAMetaItems.UNFIRED_PORCELAIN_BRICK.getStackForm(), OreDictUnifier.get(OrePrefix.ingot, GAMaterials.Porcelain));
        ModHandler.addSmeltingRecipe(new ItemStack(Ceramics.clayHelmetRaw), new ItemStack(Ceramics.clayHelmet));
        ModHandler.addSmeltingRecipe(new ItemStack(Ceramics.clayChestplateRaw), new ItemStack(Ceramics.clayChestplate));
        ModHandler.addSmeltingRecipe(new ItemStack(Ceramics.clayLeggingsRaw), new ItemStack(Ceramics.clayLeggings));
        ModHandler.addSmeltingRecipe(new ItemStack(Ceramics.clayBootsRaw), new ItemStack(Ceramics.clayBoots));
        ModHandler.addSmeltingRecipe(OreDictUnifier.get(OrePrefix.dust, GAMaterials.Porcelain), OreDictUnifier.get(OrePrefix.ingot, GAMaterials.Porcelain));
        ModHandler.removeFurnaceSmelting(new ItemStack(Ceramics.claySoft));
        ModHandler.addSmeltingRecipe(new ItemStack(Ceramics.claySoft), OreDictUnifier.get(OrePrefix.block, GAMaterials.Porcelain));
        ModHandler.addSmeltingRecipe(GAMetaItems.UNFIRED_PORCELAIN_PLATE.getStackForm(), OreDictUnifier.get(OrePrefix.plate, GAMaterials.Porcelain));
    }

    public static void recipes() {
        ModHandler.addShapelessRecipe("unfired_porcelain_brick_shapeless", GAMetaItems.UNFIRED_PORCELAIN_BRICK.getStackForm(), new UnificationEntry(OrePrefix.valueOf("clay"), GAMaterials.Porcelain), MetaItems.WOODEN_FORM_BRICK);
        ModHandler.addShapedRecipe("unfired_porcelain_brick", GAMetaItems.UNFIRED_PORCELAIN_BRICK.getStackForm(8), "PPP", "PFP", "PPP", 'P', new UnificationEntry(OrePrefix.valueOf("clay"), GAMaterials.Porcelain), 'F', MetaItems.WOODEN_FORM_BRICK);

        ModHandler.addShapelessRecipe("unfired_porcelain_plate_shapeless", GAMetaItems.UNFIRED_PORCELAIN_PLATE.getStackForm(), 'h', new UnificationEntry(OrePrefix.valueOf("clay"), GAMaterials.Porcelain));
        ModHandler.addShapedRecipe("unfired_porcelain_plate", GAMetaItems.UNFIRED_PORCELAIN_PLATE.getStackForm(), "P", "P", 'P', new UnificationEntry(OrePrefix.valueOf("clay"), GAMaterials.Porcelain));

        RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder().input(OrePrefix.valueOf("clay"), GAMaterials.Porcelain).notConsumable(MetaItems.SHAPE_MOLD_INGOT).output(OrePrefix.ingot, GAMaterials.Porcelain).duration(200).EUt(2).buildAndRegister();
        RecipeMaps.MACERATOR_RECIPES.recipeBuilder().input(OrePrefix.ingot, GAMaterials.Porcelain).output(OrePrefix.dust, GAMaterials.Porcelain).duration(30).EUt(8).buildAndRegister();
        ModHandler.removeRecipeByName(new ResourceLocation("ceramics:decoration/unfired_porcelain_quartz"));
        ModHandler.removeRecipeByName(new ResourceLocation("ceramics:decoration/unfired_porcelain_bone_meal"));
        ModHandler.addShapedRecipe("unfired_porcelain_clump", new ItemStack(Ceramics.clayUnfired, 1, ItemClayUnfired.UnfiredType.PORCELAIN.getMeta()), " C ", " B ", " m ", 'C', "clay", 'B', new UnificationEntry(OrePrefix.dust, Materials.Bone));

        ModHandler.removeRecipeByName(new ResourceLocation("ceramics:armor/unfired_clay_plate"));
        ModHandler.addShapedRecipe("clay_plate", OreDictUnifier.get(OrePrefix.plate, Materials.Clay), "C", "C", 'C', "clay");

        ModHandler.removeRecipeByName(new ResourceLocation("ceramics:uncrafting/clay_plate"));
        ModHandler.addShapelessRecipe("clay_plate_gt", OreDictUnifier.get(OrePrefix.plate, Materials.Clay), 'h', "clay");
        ModHandler.removeRecipes(new ItemStack(Ceramics.clayUnfired, 1, ItemClayUnfired.UnfiredType.BUCKET.getMeta()));
        ModHandler.addShapedRecipe("clay_bucket", new ItemStack(Ceramics.clayUnfired, 1, ItemClayUnfired.UnfiredType.BUCKET.getMeta()), "   ", "P P", " P ", 'P', new UnificationEntry(OrePrefix.plate, Materials.Clay));
        ModHandler.removeRecipes(new ItemStack(Ceramics.clayUnfired, 1, ItemClayUnfired.UnfiredType.SHEARS.getMeta()));
        ModHandler.addShapedRecipe("clay_shears", new ItemStack(Ceramics.clayUnfired, 1, ItemClayUnfired.UnfiredType.SHEARS.getMeta()), "hP", "Pf", 'P', new UnificationEntry(OrePrefix.plate, Materials.Clay));
        ModHandler.addShapelessRecipe("porcelain_to_dust", OreDictUnifier.get(OrePrefix.dust, GAMaterials.Porcelain), 'm', new UnificationEntry(OrePrefix.valueOf("clay"), GAMaterials.Porcelain));

        RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder().input(OrePrefix.valueOf("clay"), GAMaterials.Porcelain, 4).notConsumable(MetaItems.SHAPE_MOLD_BLOCK).output(OrePrefix.block, GAMaterials.Porcelain).EUt(2).duration(600).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().input(OrePrefix.dust, Materials.Clay).input(OrePrefix.dust, Materials.Bone).output(OrePrefix.dust, GAMaterials.Porcelain).EUt(8).duration(30).buildAndRegister();
        RecipeMaps.MACERATOR_RECIPES.recipeBuilder().duration(120).EUt(8).inputs(new ItemStack(Ceramics.clayHard)).output(OrePrefix.dust, GAMaterials.Porcelain, 4).buildAndRegister();
        RecipeMaps.MACERATOR_RECIPES.recipeBuilder().duration(120).EUt(8).inputs(new ItemStack(Ceramics.clayHard, 1, 2)).output(OrePrefix.dust, GAMaterials.Porcelain, 4).buildAndRegister();
        RecipeMaps.MACERATOR_RECIPES.recipeBuilder().duration(720).EUt(8).inputs(new ItemStack(Ceramics.clayHard, 8, 3)).output(OrePrefix.dust, GAMaterials.Porcelain, 24).output(OrePrefix.dustTiny, Materials.Gold).buildAndRegister();
        RecipeMaps.MACERATOR_RECIPES.recipeBuilder().duration(120).EUt(8).inputs(new ItemStack(Ceramics.clayHard, 1, 6)).output(OrePrefix.dust, GAMaterials.Porcelain, 4).buildAndRegister();
        RecipeMaps.MACERATOR_RECIPES.recipeBuilder().duration(120).EUt(8).inputs(new ItemStack(Ceramics.clayHard, 1, 7)).output(OrePrefix.dust, GAMaterials.Porcelain, 4).buildAndRegister();

        RecipeMaps.MACERATOR_RECIPES.recipeBuilder().duration(120).EUt(8).inputs(new ItemStack(Ceramics.clayHard, 1, 1)).output(OrePrefix.dust, Materials.Brick, 4).buildAndRegister();

        RecipeMaps.MACERATOR_RECIPES.recipeBuilder().duration(60).EUt(8).inputs(new ItemStack(Ceramics.claySlab)).output(OrePrefix.dust, GAMaterials.Porcelain, 2).buildAndRegister();
        RecipeMaps.MACERATOR_RECIPES.recipeBuilder().duration(60).EUt(8).inputs(new ItemStack(Ceramics.claySlab, 1, 2)).output(OrePrefix.dust, GAMaterials.Porcelain, 2).buildAndRegister();
        RecipeMaps.MACERATOR_RECIPES.recipeBuilder().duration(480).EUt(8).inputs(new ItemStack(Ceramics.claySlab, 8, 3)).output(OrePrefix.dust, GAMaterials.Porcelain, 16).output(OrePrefix.dustTiny, Materials.Gold).buildAndRegister();
        RecipeMaps.MACERATOR_RECIPES.recipeBuilder().duration(60).EUt(8).inputs(new ItemStack(Ceramics.claySlab, 1, 6)).output(OrePrefix.dust, GAMaterials.Porcelain, 2).buildAndRegister();
        RecipeMaps.MACERATOR_RECIPES.recipeBuilder().duration(60).EUt(8).inputs(new ItemStack(Ceramics.claySlab, 1, 7)).output(OrePrefix.dust, GAMaterials.Porcelain, 2).buildAndRegister();

        RecipeMaps.MACERATOR_RECIPES.recipeBuilder().duration(60).EUt(8).inputs(new ItemStack(Ceramics.claySlab, 1, 1)).output(OrePrefix.dust, Materials.Brick, 2).buildAndRegister();

        RecipeMaps.MACERATOR_RECIPES.recipeBuilder().duration(180).EUt(8).inputs(new ItemStack(Ceramics.stairsPorcelainBricks)).output(OrePrefix.dust, GAMaterials.Porcelain, 6).buildAndRegister();
        RecipeMaps.MACERATOR_RECIPES.recipeBuilder().duration(180).EUt(8).inputs(new ItemStack(Ceramics.stairsMarineBricks)).output(OrePrefix.dust, GAMaterials.Porcelain, 6).buildAndRegister();
        RecipeMaps.MACERATOR_RECIPES.recipeBuilder().duration(180).EUt(8).inputs(new ItemStack(Ceramics.stairsRainbowBricks)).output(OrePrefix.dust, GAMaterials.Porcelain, 6).buildAndRegister();
        RecipeMaps.MACERATOR_RECIPES.recipeBuilder().duration(180).EUt(8).inputs(new ItemStack(Ceramics.stairsMonochromeBricks)).output(OrePrefix.dust, GAMaterials.Porcelain, 6).buildAndRegister();
        RecipeMaps.MACERATOR_RECIPES.recipeBuilder().duration(1440).EUt(8).inputs(new ItemStack(Ceramics.stairsGoldenBricks, 8)).output(OrePrefix.dust, GAMaterials.Porcelain, 48).output(OrePrefix.dustTiny, Materials.Gold).buildAndRegister();

        RecipeMaps.MACERATOR_RECIPES.recipeBuilder().duration(180).EUt(8).inputs(new ItemStack(Ceramics.stairsDarkBricks)).output(OrePrefix.dust, Materials.Brick, 6).buildAndRegister();

        for (Material m : Arrays.asList(Materials.Clay, Materials.Brick, GAMaterials.Porcelain)) {
            RecipeMaps.BENDER_RECIPES.recipeBuilder().duration(30).EUt(8).input(OrePrefix.ingot, m).notConsumable(new IntCircuitIngredient(0)).output(OrePrefix.plate, m).buildAndRegister();
            RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder().duration(60).EUt(8).input(OrePrefix.ingot, m, 2).notConsumable(MetaItems.SHAPE_MOLD_PLATE).output(OrePrefix.plate, m).buildAndRegister();
        }

        if (GAConfig.GT6.BendingCurvedPlates) {
            ModHandler.removeRecipes(new ItemStack(Ceramics.clayHelmetRaw));
            ModHandler.removeRecipes(new ItemStack(Ceramics.clayChestplateRaw));
            ModHandler.removeRecipes(new ItemStack(Ceramics.clayLeggingsRaw));
            ModHandler.removeRecipes(new ItemStack(Ceramics.clayBootsRaw));

            ModHandler.addShapedRecipe("clay_helmet", new ItemStack(Ceramics.clayHelmetRaw), "PPP", "ChC", 'P', new UnificationEntry(OrePrefix.plate, Materials.Clay), 'C', new UnificationEntry(OrePrefix.valueOf("plateCurved"), Materials.Clay));
            ModHandler.addShapedRecipe("clay_chestplate", new ItemStack(Ceramics.clayChestplateRaw), "PhP", "CPC", "CPC", 'P', new UnificationEntry(OrePrefix.plate, Materials.Clay), 'C', new UnificationEntry(OrePrefix.valueOf("plateCurved"), Materials.Clay));
            ModHandler.addShapedRecipe("clay_leggings", new ItemStack(Ceramics.clayLeggingsRaw), "PCP", "ChC", "C C", 'P', new UnificationEntry(OrePrefix.plate, Materials.Clay), 'C', new UnificationEntry(OrePrefix.valueOf("plateCurved"), Materials.Clay));
            ModHandler.addShapedRecipe("clay_boots", new ItemStack(Ceramics.clayBootsRaw), "P P", "ChC", 'P', new UnificationEntry(OrePrefix.plate, Materials.Clay), 'C', new UnificationEntry(OrePrefix.valueOf("plateCurved"), Materials.Clay));

            ModHandler.removeRecipes(new ItemStack(Ceramics.clayHelmet));
            ModHandler.removeRecipes(new ItemStack(Ceramics.clayChestplate));
            ModHandler.removeRecipes(new ItemStack(Ceramics.clayLeggings));
            ModHandler.removeRecipes(new ItemStack(Ceramics.clayBoots));
        }
        if (!GAConfig.GT6.PlateDoubleIngot) {
            ModHandler.addShapedRecipe("porcelain_plate", OreDictUnifier.get(OrePrefix.plate, GAMaterials.Porcelain), "h", "I", "I", 'I', new UnificationEntry(OrePrefix.ingot, GAMaterials.Porcelain));
        }
        ModHandler.removeRecipes(new ItemStack(Ceramics.clayUnfired, 1, ItemClayUnfired.UnfiredType.FAUCET.getMeta()));
        ModHandler.addShapedRecipe("unfired_faucet", new ItemStack(Ceramics.clayUnfired, 1, ItemClayUnfired.UnfiredType.FAUCET.getMeta()), "   ", "ChC", " P ", 'C', new UnificationEntry(OrePrefix.valueOf("clay"), GAMaterials.Porcelain), 'P', GAMetaItems.UNFIRED_PORCELAIN_PLATE.getStackForm());
        ModHandler.removeRecipes(new ItemStack(Ceramics.clayUnfired, 3, ItemClayUnfired.UnfiredType.CHANNEL.getMeta()));
        ModHandler.addShapedRecipe("unfired_channel", new ItemStack(Ceramics.clayUnfired, 3, ItemClayUnfired.UnfiredType.CHANNEL.getMeta()), "   ", "ChC", "PPP", 'C', new UnificationEntry(OrePrefix.valueOf("clay"), GAMaterials.Porcelain), 'P', GAMetaItems.UNFIRED_PORCELAIN_PLATE.getStackForm());

        ModHandler.removeRecipes(new ItemStack(Ceramics.clayBarrelUnfired));
        ModHandler.addShapedRecipe("unfired_barrel_clay", new ItemStack(Ceramics.clayBarrelUnfired), "P P", "PhP", " P ", 'P', new UnificationEntry(OrePrefix.plate, Materials.Clay));
        ModHandler.removeRecipes(new ItemStack(Ceramics.clayBarrelUnfired, 2, 1));
        ModHandler.addShapedRecipe("unfired_barrel_clay_extension", new ItemStack(Ceramics.clayBarrelUnfired, 2, 1), "P P", "P P", "P P", 'P', new UnificationEntry(OrePrefix.plate, Materials.Clay));

        ModHandler.removeRecipes(new ItemStack(Ceramics.clayBarrelUnfired, 1, 2));
        ModHandler.addShapedRecipe("unfired_barrel", new ItemStack(Ceramics.clayBarrelUnfired, 1, 2), "P P", "PhP", " P ", 'P', GAMetaItems.UNFIRED_PORCELAIN_PLATE.getStackForm());
        ModHandler.removeRecipes(new ItemStack(Ceramics.clayBarrelUnfired, 2, 3));
        ModHandler.addShapedRecipe("unfired_barrel_extension", new ItemStack(Ceramics.clayBarrelUnfired, 2, 3), "P P", "P P", "P P", 'P', GAMetaItems.UNFIRED_PORCELAIN_PLATE.getStackForm());

        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().input("blockPorcelainStained", 1).fluidInputs(Materials.Chlorine.getFluid(50)).output(OrePrefix.block, GAMaterials.Porcelain).duration(400).EUt(2).buildAndRegister();
        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().inputs(new ItemStack(Ceramics.clayBarrelStained, 1, GTValues.W)).fluidInputs(Materials.Chlorine.getFluid(50)).outputs(new ItemStack(Ceramics.clayBarrel)).duration(400).EUt(2).buildAndRegister();
        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().inputs(new ItemStack(Ceramics.clayBarrelStainedExtension, 1, GTValues.W)).fluidInputs(Materials.Chlorine.getFluid(50)).outputs(new ItemStack(Ceramics.clayBarrel, 1, 1)).duration(400).EUt(2).buildAndRegister();
        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().inputs(new ItemStack(Ceramics.porcelainBarrel, 1, GTValues.W)).fluidInputs(Materials.Chlorine.getFluid(50)).outputs(new ItemStack(Ceramics.porcelainBarrel)).duration(400).EUt(2).buildAndRegister();
        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().inputs(new ItemStack(Ceramics.porcelainBarrelExtension, 1, GTValues.W)).fluidInputs(Materials.Chlorine.getFluid(50)).outputs(new ItemStack(Ceramics.porcelainBarrelExtension)).duration(400).EUt(2).buildAndRegister();
        for (int i = 0; i <= 15; i++) {
            String color = GAUtils.getColorFromMeta(i);
            ModHandler.removeRecipes(new ItemStack(Ceramics.porcelain, 8, i));
            ModHandler.removeRecipes(new ItemStack(Ceramics.clayBarrelStained, 8, i));
            ModHandler.removeRecipes(new ItemStack(Ceramics.clayBarrelStained, 1, i));
            ModHandler.removeRecipes(new ItemStack(Ceramics.clayBarrelStainedExtension, 8, i));
            ModHandler.removeRecipes(new ItemStack(Ceramics.clayBarrelStainedExtension, 1, i));
            ModHandler.removeRecipes(new ItemStack(Ceramics.porcelainBarrel, 8, i));
            ModHandler.removeRecipes(new ItemStack(Ceramics.porcelainBarrel, 1, i));
            ModHandler.removeRecipes(new ItemStack(Ceramics.porcelainBarrelExtension, 8, i));
            ModHandler.removeRecipes(new ItemStack(Ceramics.porcelainBarrelExtension, 1, i));
            if (!color.equals("White")) {
                ModHandler.addShapedRecipe("porcelain_" + color.toLowerCase(), new ItemStack(Ceramics.porcelain, 8, i), "WWW", "WDW", "WWW", 'W', new UnificationEntry(OrePrefix.block, GAMaterials.Porcelain), 'D', "dye" + color);
                ModHandler.addShapelessRecipe("porcelain_shapeless_" + color.toLowerCase(), new ItemStack(Ceramics.porcelain, 1, i), new UnificationEntry(OrePrefix.block, GAMaterials.Porcelain), "dye" + color);
                ModHandler.addShapedRecipe("porcelain_barrel_" + color.toLowerCase(), new ItemStack(Ceramics.porcelainBarrel, 8, i), "WWW", "WDW", "WWW", 'W', new ItemStack(Ceramics.porcelainBarrel), 'D', "dye" + color);
                ModHandler.addShapelessRecipe("porcelain_barrel_shapeless_" + color.toLowerCase(), new ItemStack(Ceramics.porcelainBarrel, 1, i), new ItemStack(Ceramics.porcelainBarrel), "dye" + color);
                ModHandler.addShapedRecipe("porcelain_barrel_extension_" + color.toLowerCase(), new ItemStack(Ceramics.porcelainBarrelExtension, 8, i), "WWW", "WDW", "WWW", 'W', new ItemStack(Ceramics.porcelainBarrelExtension), 'D', "dye" + color);
                ModHandler.addShapelessRecipe("porcelain_barrel_extension_shapeless_" + color.toLowerCase(), new ItemStack(Ceramics.porcelainBarrelExtension, 1, i), new ItemStack(Ceramics.porcelainBarrelExtension), "dye" + color);
            }
            ModHandler.addShapedRecipe("brick_barrel_" + color.toLowerCase(), new ItemStack(Ceramics.clayBarrelStained, 8, i), "WWW", "WDW", "WWW", 'W', new ItemStack(Ceramics.clayBarrel), 'D', "dye" + color);
            ModHandler.addShapelessRecipe("brick_barrel_shapeless_" + color.toLowerCase(), new ItemStack(Ceramics.clayBarrelStained, 1, i), new ItemStack(Ceramics.clayBarrel), "dye" + color);
            ModHandler.addShapedRecipe("brick_barrel_extension_" + color.toLowerCase(), new ItemStack(Ceramics.clayBarrelStainedExtension, 8, i), "WWW", "WDW", "WWW", 'W', new ItemStack(Ceramics.clayBarrel, 1, 1), 'D', "dye" + color);
            ModHandler.addShapelessRecipe("brick_barrel_extension_shapeless_" + color.toLowerCase(), new ItemStack(Ceramics.clayBarrelStainedExtension, 1, i), new ItemStack(Ceramics.clayBarrel, 1, 1), "dye" + color);
        }
    }
}
