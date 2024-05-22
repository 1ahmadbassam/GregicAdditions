package gregicadditions.recipes;

import forestry.core.ModuleCore;
import forestry.core.items.EnumElectronTube;
import gregicadditions.GAConfig;
import gregicadditions.GAMaterials;
import gregicadditions.item.GAMetaBlocks;
import gregicadditions.item.GAMetaItems;
import gregicadditions.item.GAMultiblockCasing;
import gregicadditions.item.GATransparentCasing;
import gregicadditions.machines.GATileEntities;
import gregtech.api.GTValues;
import gregtech.api.items.ToolDictNames;
import gregtech.api.recipes.CountableIngredient;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.builders.ImplosionRecipeBuilder;
import gregtech.api.recipes.builders.IntCircuitRecipeBuilder;
import gregtech.api.recipes.builders.SimpleRecipeBuilder;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.MarkerMaterials.Tier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.FluidMaterial;
import gregtech.api.unification.material.type.GemMaterial;
import gregtech.api.unification.material.type.IngotMaterial;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import gregtech.common.blocks.BlockMachineCasing;
import gregtech.common.blocks.BlockMultiblockCasing.MultiblockCasingType;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.items.MetaItems;
import gregtech.common.metatileentities.MetaTileEntities;
import knightminer.ceramics.Ceramics;
import knightminer.ceramics.blocks.BlockStained;
import knightminer.ceramics.items.ItemClayUnfired;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

import static gregtech.api.GTValues.L;
import static gregtech.api.GTValues.M;

public class GARecipeAddition {

    protected static final ItemStack[] molds = {
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
    private static final MaterialStack[] solderingList = {
            new MaterialStack(Materials.Tin, 2L),
            new MaterialStack(Materials.SolderingAlloy, 1L),
            new MaterialStack(Materials.Lead, 4L)
    };
    private static final MaterialStack[] sawLubricants = {
            new MaterialStack(Materials.Lubricant, 1L),
            new MaterialStack(Materials.DistilledWater, 3L),
            new MaterialStack(Materials.Water, 4L)
    };
    private static final MaterialStack[] cableFluids = {
            new MaterialStack(Materials.Rubber, 144),
            new MaterialStack(Materials.StyreneButadieneRubber, 108),
            new MaterialStack(Materials.SiliconeRubber, 72)
    };
    private static final MaterialStack[] cableDusts = {
            new MaterialStack(Materials.Polydimethylsiloxane, 1),
            new MaterialStack(Materials.PolyvinylChloride, 1)
    };
    private static final MaterialStack[] firstMetal = {
            new MaterialStack(Materials.Iron, 1),
            new MaterialStack(Materials.Nickel, 1),
            new MaterialStack(Materials.Invar, 2),
            new MaterialStack(Materials.Steel, 2),
            new MaterialStack(Materials.StainlessSteel, 3),
            new MaterialStack(Materials.Titanium, 3),
            new MaterialStack(Materials.Tungsten, 4),
            new MaterialStack(Materials.TungstenSteel, 5)
    };
    private static final MaterialStack[] lastMetal = {
            new MaterialStack(Materials.Tin, 0),
            new MaterialStack(Materials.Zinc, 0),
            new MaterialStack(Materials.Aluminium, 1)
    };
    private static final MaterialStack[] ironOres = {
            new MaterialStack(Materials.Pyrite, 1),
            new MaterialStack(Materials.BrownLimonite, 1),
            new MaterialStack(Materials.YellowLimonite, 1),
            new MaterialStack(Materials.Magnetite, 1),
            new MaterialStack(Materials.Iron, 1)
    };
    private static final MaterialStack[] lapisLike = {
            new MaterialStack(Materials.Lapis, 1),
            new MaterialStack(Materials.Lazurite, 1),
            new MaterialStack(Materials.Sodalite, 1)
    };

    public static void registerCokeOvenRecipes() {
        if (GAConfig.Misc.cokeOvenEnable) {
            CokeOvenRecipeBuilder.start().duration(1800).input(OrePrefix.log, Materials.Wood).output(new ItemStack(Items.COAL, 1, 1)).fluidOutput(Materials.Creosote.getFluid(500)).buildAndRegister();
            CokeOvenRecipeBuilder.start().duration(1800).input(OrePrefix.gem, Materials.Coal).output(OreDictUnifier.get(OrePrefix.gem, Materials.Coke)).fluidOutput(Materials.Creosote.getFluid(500)).buildAndRegister();
            CokeOvenRecipeBuilder.start().duration(1800).input(OrePrefix.gem, Materials.Lignite).output(OreDictUnifier.get(OrePrefix.gem, GAMaterials.LigniteCoke)).fluidOutput(Materials.Creosote.getFluid(500)).buildAndRegister();
            CokeOvenRecipeBuilder.start().duration(16200).input(OrePrefix.block, Materials.Coal).output(OreDictUnifier.get(OrePrefix.block, Materials.Coke)).fluidOutput(Materials.Creosote.getFluid(4500)).buildAndRegister();
        }
    }

    public static void init() {
        //GTNH Bricks
        if (GAConfig.Misc.cokeOvenEnable)
            ModHandler.removeRecipeByName(new ResourceLocation("gregtech:casing_coke_bricks"));
        ModHandler.removeRecipeByName(new ResourceLocation("gregtech:compressed_clay"));
        if (GAConfig.Misc.cokeOvenEnable) {
            ModHandler.removeFurnaceSmelting(MetaItems.COKE_OVEN_BRICK.getStackForm());
            ModHandler.addSmeltingRecipe(GAMetaItems.COMPRESSED_COKE_CLAY.getStackForm(), GAMetaItems.COKE_BRICK.getStackForm());
        } else
            ModHandler.addSmeltingRecipe(GAMetaItems.COMPRESSED_COKE_CLAY.getStackForm(), MetaItems.COKE_OVEN_BRICK.getStackForm());
        ModHandler.removeFurnaceSmelting(new ItemStack(Items.CLAY_BALL, 1, OreDictionary.WILDCARD_VALUE));
        ModHandler.addSmeltingRecipe(GAMetaItems.COMPRESSED_CLAY.getStackForm(), new ItemStack(Items.BRICK));
        if (GAConfig.Misc.cokeOvenEnable)
            RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder().duration(400).EUt(8).inputs(GAMetaItems.COMPRESSED_CLAY.getStackForm(2), new ItemStack(Blocks.SAND)).outputs(GAMetaItems.COKE_BRICK.getStackForm(2)).buildAndRegister();
        else
            RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder().duration(400).EUt(8).inputs(GAMetaItems.COMPRESSED_CLAY.getStackForm(2), new ItemStack(Blocks.SAND)).outputs(MetaItems.COKE_OVEN_BRICK.getStackForm(2)).buildAndRegister();
        RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder().duration(200).EUt(2).inputs(new ItemStack(Items.CLAY_BALL)).notConsumable(MetaItems.SHAPE_MOLD_INGOT).outputs(new ItemStack(Items.BRICK)).buildAndRegister();
        ModHandler.addShapelessRecipe("clay_brick", GAMetaItems.COMPRESSED_CLAY.getStackForm(), new ItemStack(Items.CLAY_BALL), MetaItems.WOODEN_FORM_BRICK);
        ModHandler.addShapedRecipe("eight_clay_brick", GAMetaItems.COMPRESSED_CLAY.getStackForm(8), "BBB", "BFB", "BBB", 'B', new ItemStack(Items.CLAY_BALL), 'F', MetaItems.WOODEN_FORM_BRICK);
        ModHandler.addShapedRecipe("coke_brick", GAMetaItems.COMPRESSED_COKE_CLAY.getStackForm(5), "SSS", "BFB", "BBB", 'B', new ItemStack(Items.CLAY_BALL), 'S', new ItemStack(Blocks.SAND), 'F', MetaItems.WOODEN_FORM_BRICK);
        if (GAConfig.Misc.cokeOvenEnable) {
            ModHandler.addShapedRecipe("coke_bricks", GAMetaBlocks.MUTLIBLOCK_CASING.getItemVariant(GAMultiblockCasing.CasingType.COKE_OVEN_BRICKS), "BB", "BB", 'B', GAMetaItems.COKE_BRICK.getStackForm());
        }
        //GT5U Old Primitive Brick Processing
        ModHandler.removeFurnaceSmelting(MetaItems.FIRECLAY_BRICK.getStackForm());
        ModHandler.removeRecipeByName(new ResourceLocation("gregtech:casing_primitive_bricks"));
        ModHandler.removeRecipeByName(new ResourceLocation("gregtech:brick_to_dust"));
        ModHandler.removeRecipeByName(new ResourceLocation("gregtech:brick_block_to_dust"));
        ModHandler.addSmeltingRecipe(GAMetaItems.COMPRESSED_FIRECLAY.getStackForm(), GAMetaItems.FIRECLAY_BRICK.getStackForm());
        RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder().input(OrePrefix.dust, Materials.Fireclay).outputs(GAMetaItems.COMPRESSED_FIRECLAY.getStackForm()).duration(100).EUt(2).buildAndRegister();
        ModHandler.addShapedRecipe("quartz_sand", OreDictUnifier.get(OrePrefix.dust, GAMaterials.QuartzSand), "S", "m", 'S', "sand");
        RecipeMaps.MACERATOR_RECIPES.recipeBuilder().duration(200).EUt(8).input("sand", 1).outputs(OreDictUnifier.get(OrePrefix.dust, GAMaterials.QuartzSand)).chancedOutput(OreDictUnifier.get(OrePrefix.dust, GAMaterials.QuartzSand), 2500, 1000).chancedOutput(OreDictUnifier.get(OrePrefix.dust, GAMaterials.QuartzSand), 2000, 1000).buildAndRegister();
        ModHandler.addShapelessRecipe("glass_dust_ga", OreDictUnifier.get(OrePrefix.dust, Materials.Glass), "dustSand", "dustFlint");
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(200).EUt(8).input(OrePrefix.dust, Materials.Flint).input(OrePrefix.dust, GAMaterials.QuartzSand, 4).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Glass, 4)).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(160).EUt(8).input(OrePrefix.dust, Materials.Flint).input(OrePrefix.dust, Materials.Quartzite, 4).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Glass, 4)).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(100).EUt(16).input(OrePrefix.dust, Materials.Calcite, 2).input(OrePrefix.dust, Materials.Stone).input(OrePrefix.dust, Materials.Clay).input(OrePrefix.dust, GAMaterials.QuartzSand).fluidInputs(Materials.Water.getFluid(2000)).fluidOutputs(Materials.Concrete.getFluid(2304)).buildAndRegister();

        //GT5U Misc Recipes
        ModHandler.addSmeltingRecipe(new ItemStack(Items.SLIME_BALL), MetaItems.RUBBER_DROP.getStackForm());
        ModHandler.removeRecipeByName(new ResourceLocation("minecraft:bone_meal_from_bone"));
        RecipeMaps.FORGE_HAMMER_RECIPES.recipeBuilder().inputs(new ItemStack(Items.BONE)).outputs(new ItemStack(Items.DYE, 3, 15)).duration(16).EUt(10).buildAndRegister();
        RecipeMaps.MACERATOR_RECIPES.recipeBuilder().inputs(new ItemStack(Items.BONE)).outputs(new ItemStack(Items.DYE, 3, 15)).duration(300).EUt(2).buildAndRegister();
        ModHandler.addSmeltingRecipe(GAMetaItems.COMPRESSED_FIRECLAY.getStackForm(), GAMetaItems.FIRECLAY_BRICK.getStackForm());
        RecipeMaps.MACERATOR_RECIPES.recipeBuilder().input(OrePrefix.plank.name(), 1).output(OrePrefix.dust, Materials.Wood).EUt(8).duration(30).buildAndRegister();

        //Shears
        ModHandler.removeRecipes(Items.SHEARS);
        ModHandler.addShapedRecipe("shears_iron", new ItemStack(Items.SHEARS), "hP", "Pf", 'P', new UnificationEntry(OrePrefix.plate, Materials.Iron));
        ModHandler.addShapedRecipe("shears_wrought_iron", new ItemStack(Items.SHEARS), "hP", "Pf", 'P', new UnificationEntry(OrePrefix.plate, Materials.WroughtIron));

        //GT6 Bending
        if (GAConfig.GT6.BendingCurvedPlates && GAConfig.GT6.BendingCylinders) {
            ModHandler.removeRecipeByName(new ResourceLocation("gregtech:iron_bucket"));
            ModHandler.addShapedRecipe("bucket", new ItemStack(Items.BUCKET), "ChC", " P ", 'C', "plateCurvedIron", 'P', "plateIron");
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(200).EUt(4).input(OrePrefix.valueOf("plateCurved"), Materials.Iron, 2).input(OrePrefix.plate, Materials.Iron).outputs(new ItemStack(Items.BUCKET)).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(200).EUt(4).input(OrePrefix.valueOf("plateCurved"), Materials.WroughtIron, 2).input(OrePrefix.plate, Materials.WroughtIron).outputs(new ItemStack(Items.BUCKET)).buildAndRegister();
            ModHandler.removeRecipeByName(new ResourceLocation("minecraft:iron_helmet"));
            ModHandler.addShapedRecipe("iron_helmet", new ItemStack(Items.IRON_HELMET), "PPP", "ChC", 'P', "plateIron", 'C', "plateCurvedIron");
            ModHandler.removeRecipeByName(new ResourceLocation("minecraft:iron_chestplate"));
            ModHandler.addShapedRecipe("iron_chestplate", new ItemStack(Items.IRON_CHESTPLATE), "PhP", "CPC", "CPC", 'P', "plateIron", 'C', "plateCurvedIron");
            ModHandler.removeRecipeByName(new ResourceLocation("minecraft:iron_leggings"));
            ModHandler.addShapedRecipe("iron_leggings", new ItemStack(Items.IRON_LEGGINGS), "PCP", "ChC", "C C", 'P', "plateIron", 'C', "plateCurvedIron");
            ModHandler.removeRecipeByName(new ResourceLocation("minecraft:iron_boots"));
            ModHandler.addShapedRecipe("iron_boots", new ItemStack(Items.IRON_BOOTS), "P P", "ChC", 'P', "plateIron", 'C', "plateCurvedIron");
            ModHandler.removeRecipeByName(new ResourceLocation("minecraft:diamond_helmet"));
            ModHandler.addShapedRecipe("diamond_helmet", new ItemStack(Items.DIAMOND_HELMET), "PPP", "ChC", 'P', "plateDiamond", 'C', "plateCurvedDiamond");
            ModHandler.removeRecipeByName(new ResourceLocation("minecraft:diamond_chestplate"));
            ModHandler.addShapedRecipe("diamond_chestplate", new ItemStack(Items.DIAMOND_CHESTPLATE), "PhP", "CPC", "CPC", 'P', "plateDiamond", 'C', "plateCurvedDiamond");
            ModHandler.removeRecipeByName(new ResourceLocation("minecraft:diamond_leggings"));
            ModHandler.addShapedRecipe("diamond_leggings", new ItemStack(Items.DIAMOND_LEGGINGS), "PCP", "ChC", "C C", 'P', "plateDiamond", 'C', "plateCurvedDiamond");
            ModHandler.removeRecipeByName(new ResourceLocation("minecraft:diamond_boots"));
            ModHandler.addShapedRecipe("diamond_boots", new ItemStack(Items.DIAMOND_BOOTS), "P P", "ChC", 'P', "plateDiamond", 'C', "plateCurvedDiamond");
            ModHandler.removeRecipeByName(new ResourceLocation("minecraft:golden_helmet"));
            ModHandler.addShapedRecipe("golden_helmet", new ItemStack(Items.GOLDEN_HELMET), "PPP", "ChC", 'P', "plateGold", 'C', "plateCurvedGold");
            ModHandler.removeRecipeByName(new ResourceLocation("minecraft:golden_chestplate"));
            ModHandler.addShapedRecipe("golden_chestplate", new ItemStack(Items.GOLDEN_CHESTPLATE), "PhP", "CPC", "CPC", 'P', "plateGold", 'C', "plateCurvedGold");
            ModHandler.removeRecipeByName(new ResourceLocation("minecraft:golden_leggings"));
            ModHandler.addShapedRecipe("golden_leggings", new ItemStack(Items.GOLDEN_LEGGINGS), "PCP", "ChC", "C C", 'P', "plateGold", 'C', "plateCurvedGold");
            ModHandler.removeRecipeByName(new ResourceLocation("minecraft:golden_boots"));
            ModHandler.addShapedRecipe("golden_boots", new ItemStack(Items.GOLDEN_BOOTS), "P P", "ChC", 'P', "plateGold", 'C', "plateCurvedGold");
            ModHandler.addShapedRecipe("chain_helmet", new ItemStack(Items.CHAINMAIL_HELMET), "RRR", "RhR", 'R', "ringIron");
            ModHandler.addShapedRecipe("chain_chestplate", new ItemStack(Items.CHAINMAIL_CHESTPLATE), "RhR", "RRR", "RRR", 'R', "ringIron");
            ModHandler.addShapedRecipe("chain_leggings", new ItemStack(Items.CHAINMAIL_LEGGINGS), "RRR", "RhR", "R R", 'R', "ringIron");
            ModHandler.addShapedRecipe("chain_boots", new ItemStack(Items.CHAINMAIL_BOOTS), "R R", "RhR", 'R', "ringIron");
        }

        ModHandler.addShapedRecipe("small_plasma_pipe", OreDictUnifier.get(OrePrefix.pipeSmall, GAMaterials.Plasma), "ESE", "NTN", "ESE", 'E', "platePlastic", 'S', OreDictUnifier.get(OrePrefix.wireGtDouble, Tier.Superconductor), 'N', "plateNeodymiumMagnetic", 'T', OreDictUnifier.get(OrePrefix.pipeSmall, Materials.Titanium));
        ModHandler.addShapedRecipe("medium_plasma_pipe", OreDictUnifier.get(OrePrefix.pipeMedium, GAMaterials.Plasma), "ESE", "NTN", "ESE", 'E', "platePlastic", 'S', OreDictUnifier.get(OrePrefix.wireGtQuadruple, Tier.Superconductor), 'N', "plateNeodymiumMagnetic", 'T', OreDictUnifier.get(OrePrefix.pipeMedium, Materials.Titanium));
        ModHandler.addShapedRecipe("large_plasma_pipe", OreDictUnifier.get(OrePrefix.pipeLarge, GAMaterials.Plasma), "ESE", "NTN", "ESE", 'E', "platePlastic", 'S', OreDictUnifier.get(OrePrefix.wireGtOctal, Tier.Superconductor), 'N', "plateNeodymiumMagnetic", 'T', OreDictUnifier.get(OrePrefix.pipeLarge, Materials.Titanium));

        if (GAConfig.GT6.BendingPipes && GAConfig.GT6.BendingCylinders) {
            ModHandler.removeRecipeByName(new ResourceLocation("gregtech:small_wooden_pipe"));
            ModHandler.removeRecipeByName(new ResourceLocation("gregtech:medium_wooden_pipe"));
            ModHandler.addShapedRecipe("pipe_ga_wood", OreDictUnifier.get(OrePrefix.pipeMedium, Materials.Wood, 2), "PPP", "sCh", "PPP", 'P', "plankWood", 'C', "craftingToolBendingCylinder");
            ModHandler.addShapedRecipe("pipe_ga_large_wood", OreDictUnifier.get(OrePrefix.pipeLarge, Materials.Wood), "PhP", "PCP", "PsP", 'P', "plankWood", 'C', "craftingToolBendingCylinder");
            ModHandler.addShapedRecipe("pipe_ga_small_wood", OreDictUnifier.get(OrePrefix.pipeSmall, Materials.Wood, 6), "PsP", "PCP", "PhP", 'P', "plankWood", 'C', "craftingToolBendingCylinder");
        }

        //Compressed Iron support - useful for some mods
        RecipeMaps.IMPLOSION_RECIPES.recipeBuilder().input(OrePrefix.ingot, Materials.Iron, 3).output(OrePrefix.ingot, GAMaterials.CompressedIron, 3).explosivesAmount(2).output(OrePrefix.dustTiny, Materials.DarkAsh, 3).EUt(30).duration(20).buildAndRegister();
        RecipeMaps.IMPLOSION_RECIPES.recipeBuilder().input(OrePrefix.block, Materials.Iron).output(OrePrefix.block, GAMaterials.CompressedIron).output(OrePrefix.dust, Materials.DarkAsh).explosivesAmount(6).EUt(30).duration(60).buildAndRegister();

        for (Material m : Material.MATERIAL_REGISTRY) {
            if (!OreDictUnifier.get(OrePrefix.ring, m).isEmpty() && !OreDictUnifier.get(OrePrefix.stick, m).isEmpty() && m != Materials.Rubber && m != Materials.StyreneButadieneRubber && m != Materials.SiliconeRubber && GAConfig.GT6.BendingRings && GAConfig.GT6.BendingCylinders) {
                ModHandler.removeRecipes(OreDictUnifier.get(OrePrefix.ring, m));
                ModHandler.addShapedRecipe("stick_to_ring_" + m, OreDictUnifier.get(OrePrefix.ring, m), "hS", " C", 'S', OreDictUnifier.get(OrePrefix.stick, m), 'C', "craftingToolBendingCylinderSmall");
            }
            if (!OreDictUnifier.get(OrePrefix.valueOf("plateCurved"), m).isEmpty() && GAConfig.GT6.BendingCurvedPlates && GAConfig.GT6.BendingCylinders) {
                if (m.compareTo(Materials.Diamond) != 0) {
                    ModHandler.addShapedRecipe("curved_plate_" + m, OreDictUnifier.get(OrePrefix.valueOf("plateCurved"), m), "h", "P", "C", 'P', new UnificationEntry(OrePrefix.plate, m), 'C', "craftingToolBendingCylinder");
                    RecipeMaps.BENDER_RECIPES.recipeBuilder().EUt(24).duration((int) m.getMass()).input(OrePrefix.plate, m).circuitMeta(0).outputs(OreDictUnifier.get(OrePrefix.valueOf("plateCurved"), m)).buildAndRegister();
                } else
                    RecipeMaps.BENDER_RECIPES.recipeBuilder().EUt(64).duration(496).input(OrePrefix.plate, m).circuitMeta(0).outputs(OreDictUnifier.get(OrePrefix.valueOf("plateCurved"), m)).buildAndRegister();
            }
            if (!OreDictUnifier.get(OrePrefix.rotor, m).isEmpty() && GAConfig.GT6.BendingRotors && GAConfig.GT6.BendingCylinders) {
                ModHandler.removeRecipes(OreDictUnifier.get(OrePrefix.rotor, m));
                ModHandler.addShapedRecipe("ga_rotor_" + m, OreDictUnifier.get(OrePrefix.rotor, m), "ChC", "SRf", "CdC", 'C', OreDictUnifier.get(OrePrefix.valueOf("plateCurved"), m), 'S', OreDictUnifier.get(OrePrefix.screw, m), 'R', OreDictUnifier.get(OrePrefix.ring, m));
                RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(240).EUt(24).inputs(OreDictUnifier.get(OrePrefix.valueOf("plateCurved"), m, 4), OreDictUnifier.get(OrePrefix.ring, m)).fluidInputs(Materials.SolderingAlloy.getFluid(32)).outputs(OreDictUnifier.get(OrePrefix.rotor, m)).buildAndRegister();
            }
            if (!OreDictUnifier.get(OrePrefix.foil, m).isEmpty()) {
                if (GAConfig.GT6.BendingFoils && GAConfig.GT6.BendingCylinders) {
                    ModHandler.addShapedRecipe("foil_" + m, OreDictUnifier.get(OrePrefix.foil, m, 2), "hPC", 'P', new UnificationEntry(OrePrefix.plate, m), 'C', "craftingToolBendingCylinder");
                }
                if (GAConfig.GT6.BendingFoilsAutomatic && GAConfig.GT6.BendingCylinders) {
                    GARecipeMaps.CLUSTER_MILL_RECIPES.recipeBuilder().EUt(24).duration((int) m.getMass()).input(OrePrefix.plate, m).outputs(OreDictUnifier.get(OrePrefix.foil, m, 4)).buildAndRegister();
                } else {
                    RecipeMaps.BENDER_RECIPES.recipeBuilder().EUt(24).duration((int) m.getMass()).circuitMeta(4).input(OrePrefix.plate, m).outputs(OreDictUnifier.get(OrePrefix.foil, m, 4)).buildAndRegister();
                }
            }

            if (!OreDictUnifier.get(OrePrefix.valueOf("round"), m).isEmpty()) {
                ModHandler.addShapedRecipe("round" + m, OreDictUnifier.get(OrePrefix.valueOf("round"), m), "fN", "N ", 'N', OreDictUnifier.get(OrePrefix.nugget, m));
                RecipeMaps.LATHE_RECIPES.recipeBuilder().EUt(8).duration((int) m.getMass()).inputs(OreDictUnifier.get(OrePrefix.nugget, m)).outputs(OreDictUnifier.get(OrePrefix.valueOf("round"), m)).buildAndRegister();
            }

            //Cables
            if (m instanceof IngotMaterial && !OreDictUnifier.get(OrePrefix.cableGtSingle, m).isEmpty() && m != Materials.RedAlloy && m != Materials.Cobalt && m != Materials.Zinc && m != Materials.SolderingAlloy && m != Materials.Tin && m != Materials.Lead && GAConfig.GT5U.CablesGT5U) {
                for (MaterialStack stackFluid : cableFluids) {
                    IngotMaterial fluid = (IngotMaterial) stackFluid.material;
                    int multiplier = (int) stackFluid.amount;
                    if (m == Materials.Tungsten || m == Materials.Osmium || m == Materials.Platinum || m == Materials.TungstenSteel || m == Materials.Graphene || m == Materials.VanadiumGallium || m == Materials.HSSG || m == Materials.YttriumBariumCuprate || m == Materials.Naquadah || m == Materials.NiobiumTitanium || m == Materials.NaquadahEnriched || m == Materials.Duranium || m == Materials.NaquadahAlloy) {
                        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(8).inputs(OreDictUnifier.get(OrePrefix.wireGtSingle, m), OreDictUnifier.get(OrePrefix.foil, m)).fluidInputs(fluid.getFluid(multiplier)).circuitMeta(24).outputs(OreDictUnifier.get(OrePrefix.cableGtSingle, m)).buildAndRegister();
                        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(8).inputs(OreDictUnifier.get(OrePrefix.wireGtDouble, m), OreDictUnifier.get(OrePrefix.foil, m, 2)).fluidInputs(fluid.getFluid(multiplier * 2)).circuitMeta(24).outputs(OreDictUnifier.get(OrePrefix.cableGtDouble, m)).buildAndRegister();
                        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(8).inputs(OreDictUnifier.get(OrePrefix.wireGtQuadruple, m), OreDictUnifier.get(OrePrefix.foil, m, 4)).fluidInputs(fluid.getFluid(multiplier * 4)).circuitMeta(24).outputs(OreDictUnifier.get(OrePrefix.cableGtQuadruple, m)).buildAndRegister();
                        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(8).inputs(OreDictUnifier.get(OrePrefix.wireGtOctal, m), OreDictUnifier.get(OrePrefix.foil, m, 8)).fluidInputs(fluid.getFluid(multiplier * 8)).circuitMeta(24).outputs(OreDictUnifier.get(OrePrefix.cableGtOctal, m)).buildAndRegister();
                        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(8).inputs(OreDictUnifier.get(OrePrefix.wireGtHex, m), OreDictUnifier.get(OrePrefix.foil, m, 16)).fluidInputs(fluid.getFluid(multiplier * 16)).circuitMeta(24).outputs(OreDictUnifier.get(OrePrefix.cableGtHex, m)).buildAndRegister();

                        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(8).inputs(OreDictUnifier.get(OrePrefix.wireGtSingle, m), OreDictUnifier.get(OrePrefix.foil, Materials.PolyphenyleneSulfide)).fluidInputs(fluid.getFluid(multiplier)).circuitMeta(24).outputs(OreDictUnifier.get(OrePrefix.cableGtSingle, m)).buildAndRegister();
                        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(8).inputs(OreDictUnifier.get(OrePrefix.wireGtDouble, m), OreDictUnifier.get(OrePrefix.foil, Materials.PolyphenyleneSulfide, 2)).fluidInputs(fluid.getFluid(multiplier * 2)).circuitMeta(24).outputs(OreDictUnifier.get(OrePrefix.cableGtDouble, m)).buildAndRegister();
                        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(8).inputs(OreDictUnifier.get(OrePrefix.wireGtQuadruple, m), OreDictUnifier.get(OrePrefix.foil, Materials.PolyphenyleneSulfide, 4)).fluidInputs(fluid.getFluid(multiplier * 4)).circuitMeta(24).outputs(OreDictUnifier.get(OrePrefix.cableGtQuadruple, m)).buildAndRegister();
                        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(8).inputs(OreDictUnifier.get(OrePrefix.wireGtOctal, m), OreDictUnifier.get(OrePrefix.foil, Materials.PolyphenyleneSulfide, 8)).fluidInputs(fluid.getFluid(multiplier * 8)).circuitMeta(24).outputs(OreDictUnifier.get(OrePrefix.cableGtOctal, m)).buildAndRegister();
                        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(8).inputs(OreDictUnifier.get(OrePrefix.wireGtHex, m), OreDictUnifier.get(OrePrefix.foil, Materials.PolyphenyleneSulfide, 16)).fluidInputs(fluid.getFluid(multiplier * 16)).circuitMeta(24).outputs(OreDictUnifier.get(OrePrefix.cableGtHex, m)).buildAndRegister();
                        for (MaterialStack stackDust : cableDusts) {
                            Material dust = stackDust.material;
                            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(8).inputs(OreDictUnifier.get(OrePrefix.wireGtSingle, m), OreDictUnifier.get(OrePrefix.foil, m), OreDictUnifier.get(OrePrefix.dustSmall, dust)).fluidInputs(fluid.getFluid(multiplier / 2)).outputs(OreDictUnifier.get(OrePrefix.cableGtSingle, m)).buildAndRegister();
                            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(8).inputs(OreDictUnifier.get(OrePrefix.wireGtDouble, m), OreDictUnifier.get(OrePrefix.foil, m, 2), OreDictUnifier.get(OrePrefix.dustSmall, dust, 2)).fluidInputs(fluid.getFluid(multiplier)).outputs(OreDictUnifier.get(OrePrefix.cableGtDouble, m)).buildAndRegister();
                            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(8).inputs(OreDictUnifier.get(OrePrefix.wireGtQuadruple, m), OreDictUnifier.get(OrePrefix.foil, m, 4), OreDictUnifier.get(OrePrefix.dustSmall, dust, 4)).fluidInputs(fluid.getFluid(multiplier * 2)).outputs(OreDictUnifier.get(OrePrefix.cableGtQuadruple, m)).buildAndRegister();
                            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(8).inputs(OreDictUnifier.get(OrePrefix.wireGtOctal, m), OreDictUnifier.get(OrePrefix.foil, m, 8), OreDictUnifier.get(OrePrefix.dustSmall, dust, 8)).fluidInputs(fluid.getFluid(multiplier * 4)).outputs(OreDictUnifier.get(OrePrefix.cableGtOctal, m)).buildAndRegister();
                            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(8).inputs(OreDictUnifier.get(OrePrefix.wireGtHex, m), OreDictUnifier.get(OrePrefix.foil, m, 16), OreDictUnifier.get(OrePrefix.dustSmall, dust, 16)).fluidInputs(fluid.getFluid(multiplier * 8)).outputs(OreDictUnifier.get(OrePrefix.cableGtHex, m)).buildAndRegister();

                            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(8).inputs(OreDictUnifier.get(OrePrefix.wireGtSingle, m), OreDictUnifier.get(OrePrefix.foil, Materials.PolyphenyleneSulfide), OreDictUnifier.get(OrePrefix.dustSmall, dust)).fluidInputs(fluid.getFluid(multiplier / 2)).outputs(OreDictUnifier.get(OrePrefix.cableGtSingle, m)).buildAndRegister();
                            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(8).inputs(OreDictUnifier.get(OrePrefix.wireGtDouble, m), OreDictUnifier.get(OrePrefix.foil, Materials.PolyphenyleneSulfide, 2), OreDictUnifier.get(OrePrefix.dustSmall, dust, 2)).fluidInputs(fluid.getFluid(multiplier)).outputs(OreDictUnifier.get(OrePrefix.cableGtDouble, m)).buildAndRegister();
                            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(8).inputs(OreDictUnifier.get(OrePrefix.wireGtQuadruple, m), OreDictUnifier.get(OrePrefix.foil, Materials.PolyphenyleneSulfide, 4), OreDictUnifier.get(OrePrefix.dustSmall, dust, 4)).fluidInputs(fluid.getFluid(multiplier * 2)).outputs(OreDictUnifier.get(OrePrefix.cableGtQuadruple, m)).buildAndRegister();
                            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(8).inputs(OreDictUnifier.get(OrePrefix.wireGtOctal, m), OreDictUnifier.get(OrePrefix.foil, Materials.PolyphenyleneSulfide, 8), OreDictUnifier.get(OrePrefix.dustSmall, dust, 8)).fluidInputs(fluid.getFluid(multiplier * 4)).outputs(OreDictUnifier.get(OrePrefix.cableGtOctal, m)).buildAndRegister();
                            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(8).inputs(OreDictUnifier.get(OrePrefix.wireGtHex, m), OreDictUnifier.get(OrePrefix.foil, Materials.PolyphenyleneSulfide, 16), OreDictUnifier.get(OrePrefix.dustSmall, dust, 16)).fluidInputs(fluid.getFluid(multiplier * 8)).outputs(OreDictUnifier.get(OrePrefix.cableGtHex, m)).buildAndRegister();
                        }
                    } else {
                        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(8).inputs(OreDictUnifier.get(OrePrefix.wireGtSingle, m)).fluidInputs(fluid.getFluid(multiplier)).circuitMeta(24).outputs(OreDictUnifier.get(OrePrefix.cableGtSingle, m)).buildAndRegister();
                        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(8).inputs(OreDictUnifier.get(OrePrefix.wireGtDouble, m)).fluidInputs(fluid.getFluid(multiplier * 2)).circuitMeta(24).outputs(OreDictUnifier.get(OrePrefix.cableGtDouble, m)).buildAndRegister();
                        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(8).inputs(OreDictUnifier.get(OrePrefix.wireGtQuadruple, m)).fluidInputs(fluid.getFluid(multiplier * 4)).circuitMeta(24).outputs(OreDictUnifier.get(OrePrefix.cableGtQuadruple, m)).buildAndRegister();
                        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(8).inputs(OreDictUnifier.get(OrePrefix.wireGtOctal, m)).fluidInputs(fluid.getFluid(multiplier * 8)).circuitMeta(24).outputs(OreDictUnifier.get(OrePrefix.cableGtOctal, m)).buildAndRegister();
                        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(8).inputs(OreDictUnifier.get(OrePrefix.wireGtHex, m)).fluidInputs(fluid.getFluid(multiplier * 16)).circuitMeta(24).outputs(OreDictUnifier.get(OrePrefix.cableGtHex, m)).buildAndRegister();
                        for (MaterialStack stackDust : cableDusts) {
                            Material dust = stackDust.material;
                            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(8).inputs(OreDictUnifier.get(OrePrefix.wireGtSingle, m), OreDictUnifier.get(OrePrefix.dustSmall, dust)).fluidInputs(fluid.getFluid(multiplier / 2)).outputs(OreDictUnifier.get(OrePrefix.cableGtSingle, m)).buildAndRegister();
                            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(8).inputs(OreDictUnifier.get(OrePrefix.wireGtSingle, m), OreDictUnifier.get(OrePrefix.dustSmall, dust)).fluidInputs(fluid.getFluid(multiplier / 2)).outputs(OreDictUnifier.get(OrePrefix.cableGtSingle, m)).buildAndRegister();
                            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(8).inputs(OreDictUnifier.get(OrePrefix.wireGtDouble, m), OreDictUnifier.get(OrePrefix.dustSmall, dust, 2)).fluidInputs(fluid.getFluid(multiplier)).outputs(OreDictUnifier.get(OrePrefix.cableGtDouble, m)).buildAndRegister();
                            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(8).inputs(OreDictUnifier.get(OrePrefix.wireGtQuadruple, m), OreDictUnifier.get(OrePrefix.dustSmall, dust, 4)).fluidInputs(fluid.getFluid(multiplier * 2)).outputs(OreDictUnifier.get(OrePrefix.cableGtQuadruple, m)).buildAndRegister();
                            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(8).inputs(OreDictUnifier.get(OrePrefix.wireGtOctal, m), OreDictUnifier.get(OrePrefix.dustSmall, dust, 8)).fluidInputs(fluid.getFluid(multiplier * 4)).outputs(OreDictUnifier.get(OrePrefix.cableGtOctal, m)).buildAndRegister();
                            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(8).inputs(OreDictUnifier.get(OrePrefix.wireGtHex, m), OreDictUnifier.get(OrePrefix.dustSmall, dust, 16)).fluidInputs(fluid.getFluid(multiplier * 8)).outputs(OreDictUnifier.get(OrePrefix.cableGtHex, m)).buildAndRegister();
                        }
                    }
                }
            }

            //GT6 Plate Recipe
            if (!OreDictUnifier.get(OrePrefix.plate, m).isEmpty() && !OreDictUnifier.get(OrePrefix.valueOf("ingotDouble"), m).isEmpty() && GAConfig.GT6.PlateDoubleIngot) {
                ModHandler.removeRecipes(OreDictUnifier.get(OrePrefix.plate, m));
                ModHandler.addShapedRecipe("ingot_double_" + m, OreDictUnifier.get(OrePrefix.valueOf("ingotDouble"), m), "h", "I", "I", 'I', new UnificationEntry(OrePrefix.ingot, m));
                ModHandler.addShapedRecipe("double_ingot_to_plate_" + m, OreDictUnifier.get(OrePrefix.plate, m), "h", "I", 'I', OreDictUnifier.get(OrePrefix.valueOf("ingotDouble"), m));
            }

            if (!OreDictUnifier.get(OrePrefix.valueOf("plateCurved"), m).isEmpty() && GAConfig.GT6.BendingCurvedPlates && GAConfig.GT6.BendingCylinders && m.compareTo(Materials.Diamond) != 0) {
                ModHandler.addShapedRecipe("flatten_plate_" + m, OreDictUnifier.get(OrePrefix.plate, m), "h", "C", 'C', OreDictUnifier.get(OrePrefix.valueOf("plateCurved"), m));
            }

            //Pipes
            if (!OreDictUnifier.get(OrePrefix.pipeMedium, m).isEmpty() && GAConfig.GT6.BendingPipes) {
                ModHandler.removeRecipeByName(new ResourceLocation("gregtech:small_" + m + "_pipe"));
                ModHandler.removeRecipeByName(new ResourceLocation("gregtech:medium_" + m + "_pipe"));
                ModHandler.removeRecipeByName(new ResourceLocation("gregtech:large_" + m + "_pipe"));
                if (!OreDictUnifier.get(OrePrefix.valueOf("plateCurved"), m).isEmpty()) {
                    ModHandler.addShapedRecipe("pipe_ga_" + m, OreDictUnifier.get(OrePrefix.pipeMedium, m, 2), "PPP", "wCh", "PPP", 'P', OreDictUnifier.get(OrePrefix.valueOf("plateCurved"), m), 'C', "craftingToolBendingCylinder");
                    ModHandler.addShapedRecipe("pipe_ga_large_" + m, OreDictUnifier.get(OrePrefix.pipeLarge, m), "PhP", "PCP", "PwP", 'P', OreDictUnifier.get(OrePrefix.valueOf("plateCurved"), m), 'C', "craftingToolBendingCylinder");
                    ModHandler.addShapedRecipe("pipe_ga_small_" + m, OreDictUnifier.get(OrePrefix.pipeSmall, m, 4), "PwP", "PCP", "PhP", 'P', OreDictUnifier.get(OrePrefix.valueOf("plateCurved"), m), 'C', "craftingToolBendingCylinder");
                }
            }
        }

        //Copying Molds
        for (ItemStack mold : molds)
            RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder().duration(200).EUt(256).inputs(MetaItems.SHAPE_EMPTY.getStackForm()).notConsumable(mold).outputs(mold).buildAndRegister();

        //Reinforced Glass
        ModHandler.removeRecipeByName(new ResourceLocation("gregtech:ingot_mixed_metal"));
        int multiplier2;
        for (MaterialStack metal1 : firstMetal) {
            IngotMaterial material1 = (IngotMaterial) metal1.material;
            int multiplier1 = (int) metal1.amount;
            for (MaterialStack metal2 : lastMetal) {
                IngotMaterial material2 = (IngotMaterial) metal2.material;
                if ((int) metal1.amount == 1)
                    multiplier2 = 0;
                else
                    multiplier2 = (int) metal2.amount;
                ModHandler.addShapedRecipe("mixed_metal_1_" + material1 + "_" + material2, MetaItems.INGOT_MIXED_METAL.getStackForm(multiplier1 + multiplier2), "F", "M", "L", 'F', new UnificationEntry(OrePrefix.plate, material1), 'M', "plateBronze", 'L', OreDictUnifier.get(OrePrefix.plate, material2));
                ModHandler.addShapedRecipe("mixed_metal_2_" + material1 + "_" + material2, MetaItems.INGOT_MIXED_METAL.getStackForm(multiplier1 + multiplier2), "F", "M", "L", 'F', new UnificationEntry(OrePrefix.plate, material1), 'M', "plateBrass", 'L', OreDictUnifier.get(OrePrefix.plate, material2));

                RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(40 * multiplier1 + multiplier2 * 40).EUt(8).input(OrePrefix.plate, material1).input(OrePrefix.plank, Materials.Bronze).input(OrePrefix.plate, material2).outputs(MetaItems.INGOT_MIXED_METAL.getStackForm(multiplier1 + multiplier2)).buildAndRegister();
                RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(40 * multiplier1 + multiplier2 * 40).EUt(8).input(OrePrefix.plate, material1).input(OrePrefix.plate, Materials.Brass).input(OrePrefix.plate, material2).outputs(MetaItems.INGOT_MIXED_METAL.getStackForm(multiplier1 + multiplier2)).buildAndRegister();
            }
        }

        RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder().duration(400).EUt(4).inputs(MetaItems.ADVANCED_ALLOY_PLATE.getStackForm()).input(OrePrefix.dust, Materials.Glass, 3).outputs(GAMetaBlocks.TRANSPARENT_CASING.getItemVariant(GATransparentCasing.CasingType.REINFORCED_GLASS, 4)).buildAndRegister();
        RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder().duration(400).EUt(4).inputs(MetaItems.ADVANCED_ALLOY_PLATE.getStackForm(), new ItemStack(Blocks.GLASS, 3)).outputs(GAMetaBlocks.TRANSPARENT_CASING.getItemVariant(GATransparentCasing.CasingType.REINFORCED_GLASS, 4)).buildAndRegister();

        //Iridium Alloy
        ModHandler.removeRecipeByName(new ResourceLocation("gregtech:ingot_iridium_alloy"));
        ModHandler.addShapedRecipe("iridium_alloy_plate", MetaItems.INGOT_IRIDIUM_ALLOY.getStackForm(), "AIA", "IDI", "AIA", 'A', MetaItems.ADVANCED_ALLOY_PLATE.getStackForm(), 'I', "plateIridium", 'D', "plateDiamond");
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(40).EUt(8).inputs(MetaItems.ADVANCED_ALLOY_PLATE.getStackForm(4)).input(OrePrefix.plate, Materials.Iridium, 4).input(OrePrefix.plate, Materials.Diamond).outputs(MetaItems.INGOT_IRIDIUM_ALLOY.getStackForm()).buildAndRegister();

        ModHandler.addShapelessRecipe("ultima", OreDictUnifier.get(OrePrefix.dust, GAMaterials.Ultima, 4), "dustTinAlloy", "dustUltimet", "dustMagnalium", "dustBlueSteel", "dustVanadiumSteel", "dustSterlingSilver", "dustHsss", "dustNaquadahAlloy");

        //Machine Component Recipes
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(40).EUt(30).input(OrePrefix.circuit, Tier.Basic, 4).input(OrePrefix.dust, Materials.EnderPearl).input(OrePrefix.wireGtSingle, Materials.Osmium, 4).outputs(MetaItems.FIELD_GENERATOR_LV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(40).EUt(30).input(OrePrefix.circuit, Tier.Basic, 4).input(OrePrefix.gem, Materials.EnderPearl).input(OrePrefix.wireGtSingle, Materials.Osmium, 4).outputs(MetaItems.FIELD_GENERATOR_LV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(80).EUt(120).input(OrePrefix.circuit, Tier.Good, 4).input(OrePrefix.dust, Materials.EnderEye).input(OrePrefix.wireGtDouble, Materials.Osmium, 4).outputs(MetaItems.FIELD_GENERATOR_MV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(80).EUt(120).input(OrePrefix.circuit, Tier.Good, 4).input(OrePrefix.gem, Materials.EnderEye).input(OrePrefix.wireGtDouble, Materials.Osmium, 4).outputs(MetaItems.FIELD_GENERATOR_MV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(160).EUt(480).input(OrePrefix.circuit, Tier.Advanced, 4).inputs(MetaItems.QUANTUM_EYE.getStackForm()).input(OrePrefix.wireGtQuadruple, Materials.Osmium, 4).outputs(MetaItems.FIELD_GENERATOR_HV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(320).EUt(1920).input(OrePrefix.circuit, Tier.Extreme, 4).input(OrePrefix.dust, Materials.NetherStar).input(OrePrefix.wireGtOctal, Materials.Osmium, 4).outputs(MetaItems.FIELD_GENERATOR_EV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(320).EUt(1920).input(OrePrefix.circuit, Tier.Extreme, 4).input(OrePrefix.gem, Materials.NetherStar).input(OrePrefix.wireGtOctal, Materials.Osmium, 4).outputs(MetaItems.FIELD_GENERATOR_EV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(640).EUt(7680).input(OrePrefix.circuit, Tier.Elite, 4).inputs(MetaItems.QUANTUM_STAR.getStackForm()).input(OrePrefix.wireGtHex, Materials.Osmium, 4).outputs(MetaItems.FIELD_GENERATOR_IV.getStackForm()).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(40).EUt(30).input(OrePrefix.cableGtSingle, Materials.Tin, 2).input(OrePrefix.stick, Materials.Iron, 2).input(OrePrefix.stick, Materials.IronMagnetic).input(OrePrefix.wireGtSingle, Materials.Copper, 4).outputs(MetaItems.ELECTRIC_MOTOR_LV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(40).EUt(30).input(OrePrefix.cableGtSingle, Materials.Tin, 2).input(OrePrefix.stick, Materials.Steel, 2).input(OrePrefix.stick, Materials.SteelMagnetic).input(OrePrefix.wireGtSingle, Materials.Copper, 4).outputs(MetaItems.ELECTRIC_MOTOR_LV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(80).EUt(120).input(OrePrefix.cableGtSingle, Materials.Copper, 2).input(OrePrefix.stick, Materials.Aluminium, 2).input(OrePrefix.stick, Materials.SteelMagnetic).input(OrePrefix.wireGtDouble, Materials.Copper, 4).outputs(MetaItems.ELECTRIC_MOTOR_MV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(160).EUt(480).input(OrePrefix.cableGtSingle, Materials.Gold, 2).input(OrePrefix.stick, Materials.StainlessSteel, 2).input(OrePrefix.stick, Materials.SteelMagnetic).input(OrePrefix.wireGtQuadruple, Materials.Copper, 4).outputs(MetaItems.ELECTRIC_MOTOR_HV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(320).EUt(1920).input(OrePrefix.cableGtSingle, Materials.Aluminium, 2).input(OrePrefix.stick, Materials.Titanium, 2).input(OrePrefix.stick, Materials.NeodymiumMagnetic).input(OrePrefix.wireGtOctal, Materials.Copper, 4).outputs(MetaItems.ELECTRIC_MOTOR_EV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(640).EUt(7680).input(OrePrefix.cableGtSingle, Materials.Tungsten, 2).input(OrePrefix.stick, Materials.TungstenSteel, 2).input(OrePrefix.stick, Materials.NeodymiumMagnetic).input(OrePrefix.wireGtHex, Materials.Copper, 4).outputs(MetaItems.ELECTRIC_MOTOR_IV.getStackForm()).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(40).EUt(30).input(OrePrefix.cableGtSingle, Materials.Tin).inputs(MetaItems.ELECTRIC_MOTOR_LV.getStackForm(2)).input(OrePrefix.plate, Materials.Rubber, 6).outputs(MetaItems.CONVEYOR_MODULE_LV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(80).EUt(120).input(OrePrefix.cableGtSingle, Materials.Copper).inputs(MetaItems.ELECTRIC_MOTOR_MV.getStackForm(2)).input(OrePrefix.plate, Materials.Rubber, 6).outputs(MetaItems.CONVEYOR_MODULE_MV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(160).EUt(480).input(OrePrefix.cableGtSingle, Materials.Gold).inputs(MetaItems.ELECTRIC_MOTOR_HV.getStackForm(2)).input(OrePrefix.plate, Materials.Rubber, 6).outputs(MetaItems.CONVEYOR_MODULE_HV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(320).EUt(1920).input(OrePrefix.cableGtSingle, Materials.Aluminium).inputs(MetaItems.ELECTRIC_MOTOR_EV.getStackForm(2)).input(OrePrefix.plate, Materials.Rubber, 6).outputs(MetaItems.CONVEYOR_MODULE_EV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(640).EUt(7680).input(OrePrefix.cableGtSingle, Materials.Tungsten).inputs(MetaItems.ELECTRIC_MOTOR_IV.getStackForm(2)).input(OrePrefix.plate, Materials.Rubber, 6).outputs(MetaItems.CONVEYOR_MODULE_IV.getStackForm()).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(40).EUt(30).input(OrePrefix.circuit, Tier.Basic, 2).inputs(OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.Tin, 2), OreDictUnifier.get(OrePrefix.gem, Materials.Quartzite)).input(OrePrefix.stick, Materials.Brass, 4).outputs(MetaItems.EMITTER_LV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(80).EUt(120).input(OrePrefix.circuit, Tier.Good, 2).inputs(OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.Copper, 2), OreDictUnifier.get(OrePrefix.gem, Materials.NetherQuartz)).input(OrePrefix.stick, Materials.Electrum, 4).outputs(MetaItems.EMITTER_MV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(160).EUt(480).input(OrePrefix.circuit, Tier.Advanced, 2).inputs(OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.Gold, 2), OreDictUnifier.get(OrePrefix.gem, Materials.Emerald)).input(OrePrefix.stick, Materials.Chrome, 4).outputs(MetaItems.EMITTER_HV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(320).EUt(1920).input(OrePrefix.circuit, Tier.Extreme, 2).inputs(OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.Aluminium, 2), OreDictUnifier.get(OrePrefix.gem, Materials.EnderPearl)).input(OrePrefix.stick, Materials.Platinum, 4).outputs(MetaItems.EMITTER_EV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(640).EUt(7680).input(OrePrefix.circuit, Tier.Elite, 2).inputs(OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.Tungsten, 2), OreDictUnifier.get(OrePrefix.gem, Materials.EnderEye)).input(OrePrefix.stick, Materials.Osmium, 4).outputs(MetaItems.EMITTER_IV.getStackForm()).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(40).EUt(30).input(OrePrefix.plate, Materials.Steel, 3).input(OrePrefix.cableGtSingle, Materials.Tin, 2).input(OrePrefix.stick, Materials.Steel, 2).input(OrePrefix.gearSmall, Materials.Steel).inputs(MetaItems.ELECTRIC_MOTOR_LV.getStackForm()).outputs(MetaItems.ELECTRIC_PISTON_LV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(80).EUt(120).input(OrePrefix.plate, Materials.Aluminium, 3).input(OrePrefix.cableGtSingle, Materials.Copper, 2).input(OrePrefix.stick, Materials.Aluminium, 2).input(OrePrefix.gearSmall, Materials.Aluminium).inputs(MetaItems.ELECTRIC_MOTOR_MV.getStackForm()).outputs(MetaItems.ELECTRIC_PISTON_MV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(160).EUt(480).input(OrePrefix.plate, Materials.StainlessSteel, 3).input(OrePrefix.cableGtSingle, Materials.Gold, 2).input(OrePrefix.stick, Materials.StainlessSteel, 2).input(OrePrefix.gearSmall, Materials.StainlessSteel).inputs(MetaItems.ELECTRIC_MOTOR_HV.getStackForm()).outputs(MetaItems.ELECTRIC_PISTON_HV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(320).EUt(1920).input(OrePrefix.plate, Materials.Titanium, 3).input(OrePrefix.cableGtSingle, Materials.Aluminium, 2).input(OrePrefix.stick, Materials.Titanium, 2).input(OrePrefix.gearSmall, Materials.Titanium).inputs(MetaItems.ELECTRIC_MOTOR_EV.getStackForm()).outputs(MetaItems.ELECTRIC_PISTON_EV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(640).EUt(7680).input(OrePrefix.plate, Materials.TungstenSteel, 3).input(OrePrefix.cableGtSingle, Materials.Tungsten, 2).input(OrePrefix.stick, Materials.TungstenSteel, 2).input(OrePrefix.gearSmall, Materials.TungstenSteel).inputs(MetaItems.ELECTRIC_MOTOR_IV.getStackForm()).outputs(MetaItems.ELECTRIC_PISTON_IV.getStackForm()).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(40).EUt(30).input(OrePrefix.circuit, Tier.Basic).input(OrePrefix.cableGtSingle, Materials.Tin, 3).input(OrePrefix.stick, Materials.Steel, 2).inputs(MetaItems.ELECTRIC_PISTON_LV.getStackForm(), MetaItems.ELECTRIC_MOTOR_LV.getStackForm(2)).outputs(MetaItems.ROBOT_ARM_LV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(80).EUt(120).input(OrePrefix.circuit, Tier.Good).input(OrePrefix.cableGtSingle, Materials.Copper, 3).input(OrePrefix.stick, Materials.Aluminium, 2).inputs(MetaItems.ELECTRIC_PISTON_MV.getStackForm(), MetaItems.ELECTRIC_MOTOR_MV.getStackForm(2)).outputs(MetaItems.ROBOT_ARM_MV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(160).EUt(480).input(OrePrefix.circuit, Tier.Advanced).input(OrePrefix.cableGtSingle, Materials.Gold, 3).input(OrePrefix.stick, Materials.StainlessSteel, 2).inputs(MetaItems.ELECTRIC_PISTON_HV.getStackForm(), MetaItems.ELECTRIC_MOTOR_HV.getStackForm(2)).outputs(MetaItems.ROBOT_ARM_HV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(320).EUt(1920).input(OrePrefix.circuit, Tier.Extreme).input(OrePrefix.cableGtSingle, Materials.Aluminium, 3).input(OrePrefix.stick, Materials.Titanium, 2).inputs(MetaItems.ELECTRIC_PISTON_EV.getStackForm(), MetaItems.ELECTRIC_MOTOR_EV.getStackForm(2)).outputs(MetaItems.ROBOT_ARM_EV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(640).EUt(7680).input(OrePrefix.circuit, Tier.Elite).input(OrePrefix.cableGtSingle, Materials.Tungsten, 3).input(OrePrefix.stick, Materials.TungstenSteel, 2).inputs(MetaItems.ELECTRIC_PISTON_IV.getStackForm(), MetaItems.ELECTRIC_MOTOR_IV.getStackForm(2)).outputs(MetaItems.ROBOT_ARM_IV.getStackForm()).buildAndRegister();

        for (MaterialStack stackFluid : cableFluids) {
            IngotMaterial m = (IngotMaterial) stackFluid.material;
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(40).EUt(30).input(OrePrefix.cableGtSingle, Materials.Tin).input(OrePrefix.screw, Materials.Tin).input(OrePrefix.rotor, Materials.Tin).input(OrePrefix.pipeMedium, Materials.Copper).inputs(MetaItems.ELECTRIC_MOTOR_LV.getStackForm()).input(OrePrefix.ring, m).outputs(MetaItems.ELECTRIC_PUMP_LV.getStackForm()).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(80).EUt(120).input(OrePrefix.cableGtSingle, Materials.Copper).input(OrePrefix.screw, Materials.Bronze).input(OrePrefix.rotor, Materials.Bronze).input(OrePrefix.pipeMedium, Materials.Steel).inputs(MetaItems.ELECTRIC_MOTOR_MV.getStackForm()).input(OrePrefix.ring, m).outputs(MetaItems.ELECTRIC_PUMP_MV.getStackForm()).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(160).EUt(480).input(OrePrefix.cableGtSingle, Materials.Gold).input(OrePrefix.screw, Materials.Steel).input(OrePrefix.rotor, Materials.Steel).input(OrePrefix.pipeMedium, Materials.StainlessSteel).inputs(MetaItems.ELECTRIC_MOTOR_HV.getStackForm()).input(OrePrefix.ring, m).outputs(MetaItems.ELECTRIC_PUMP_HV.getStackForm()).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(320).EUt(1920).input(OrePrefix.cableGtSingle, Materials.Aluminium).input(OrePrefix.screw, Materials.StainlessSteel).input(OrePrefix.rotor, Materials.StainlessSteel).input(OrePrefix.pipeMedium, Materials.Titanium).inputs(MetaItems.ELECTRIC_MOTOR_EV.getStackForm()).input(OrePrefix.ring, m).outputs(MetaItems.ELECTRIC_PUMP_EV.getStackForm()).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(640).EUt(7680).input(OrePrefix.cableGtSingle, Materials.Tungsten).input(OrePrefix.screw, Materials.TungstenSteel).input(OrePrefix.rotor, Materials.TungstenSteel).input(OrePrefix.pipeMedium, Materials.TungstenSteel).inputs(MetaItems.ELECTRIC_MOTOR_IV.getStackForm()).input(OrePrefix.ring, m).outputs(MetaItems.ELECTRIC_PUMP_IV.getStackForm()).buildAndRegister();
        }

        //Enhanced Piston Recipes
        if (GAConfig.GT5U.enhancedPistonRecipes) {
            ModHandler.removeRecipes(ItemBlock.getItemFromBlock(Blocks.PISTON));
            ModHandler.addShapedRecipe("piston_iron", new ItemStack(Blocks.PISTON), "WWW", "SGS", "SRS", 'W', new UnificationEntry(OrePrefix.plank, Materials.Wood), 'S', OrePrefix.stoneCobble, 'R', new UnificationEntry(OrePrefix.dust, Materials.Redstone), 'G', new UnificationEntry(OrePrefix.gearSmall, Materials.Iron));
            List<IngotMaterial> enhancedMaterialList = Arrays.asList(Materials.Iron, Materials.Steel, Materials.Aluminium, Materials.StainlessSteel, Materials.Titanium, Materials.TungstenSteel);
            for (int tier = 0; tier < enhancedMaterialList.size(); tier++) {
                int pistonCount = (int) Math.pow(2, tier);
                int EUt = (int) Math.ceil(1.875 * (int) Math.pow(4, tier + 1));
                IntCircuitRecipeBuilder recipe = RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder();
                for (int j = 0; j < ((pistonCount * 3) / 64); j++) {
                    recipe.input(OrePrefix.plank, Materials.Wood, 64);
                }
                if ((pistonCount * 3) % 64 != 0) recipe.input(OrePrefix.plank, Materials.Wood, (3 * pistonCount) % 64);
                for (int j = 0; j < ((pistonCount * 4) / 64); j++) {
                    recipe.input(OrePrefix.stoneCobble.name(), 64);
                }
                if ((pistonCount * 4) % 64 != 0) recipe.input(OrePrefix.stoneCobble.name(), (4 * pistonCount) % 64);
                recipe.input(OrePrefix.gearSmall, enhancedMaterialList.get(tier))
                        .fluidInputs(Collections.singletonList(Materials.Redstone.getFluid(144 * pistonCount)))
                        .outputs(new ItemStack(Blocks.PISTON, pistonCount))
                        .EUt(EUt)
                        .duration(20 * pistonCount)
                        .buildAndRegister();
            }
        }

        //Pyrolyse Oven Recipes
        RecipeMaps.PYROLYSE_RECIPES.recipeBuilder()
                .inputs(new ItemStack(Items.SUGAR, 23))
                .circuitMeta(1)
                .outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Charcoal, 12))
                .fluidOutputs(Materials.Water.getFluid(1500))
                .duration(640)
                .EUt(64)
                .buildAndRegister();

        RecipeMaps.PYROLYSE_RECIPES.recipeBuilder()
                .inputs(new ItemStack(Items.SUGAR, 23))
                .circuitMeta(2)
                .fluidInputs(Materials.Nitrogen.getFluid(400))
                .outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Charcoal, 12))
                .fluidOutputs(Materials.Water.getFluid(1500))
                .duration(320)
                .EUt(96)
                .buildAndRegister();

        //Chemical Reactor Cracking
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).fluidInputs(Materials.Hydrogen.getFluid(2000), Materials.Ethane.getFluid(1000)).fluidOutputs(Materials.HydroCrackedEthane.getFluid(1000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).fluidInputs(Materials.Hydrogen.getFluid(2000), Materials.Ethylene.getFluid(1000)).fluidOutputs(Materials.HydroCrackedEthylene.getFluid(1000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).fluidInputs(Materials.Hydrogen.getFluid(2000), Materials.Propene.getFluid(1000)).fluidOutputs(Materials.HydroCrackedPropene.getFluid(1000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).fluidInputs(Materials.Hydrogen.getFluid(2000), Materials.Propane.getFluid(1000)).fluidOutputs(Materials.HydroCrackedPropane.getFluid(1000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).fluidInputs(Materials.Hydrogen.getFluid(2000), Materials.LightFuel.getFluid(1000)).fluidOutputs(Materials.HydroCrackedLightFuel.getFluid(1000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).fluidInputs(Materials.Hydrogen.getFluid(2000), Materials.Butane.getFluid(1000)).fluidOutputs(Materials.HydroCrackedButane.getFluid(1000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).fluidInputs(Materials.Hydrogen.getFluid(2000), Materials.Naphtha.getFluid(1000)).fluidOutputs(Materials.HydroCrackedNaphtha.getFluid(1000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).fluidInputs(Materials.Hydrogen.getFluid(2000), Materials.HeavyFuel.getFluid(1000)).fluidOutputs(Materials.HydroCrackedHeavyFuel.getFluid(1000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).fluidInputs(Materials.Hydrogen.getFluid(2000), Materials.Gas.getFluid(1000)).fluidOutputs(Materials.HydroCrackedGas.getFluid(1000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).fluidInputs(Materials.Hydrogen.getFluid(2000), Materials.Butene.getFluid(1000)).fluidOutputs(Materials.HydroCrackedButene.getFluid(1000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).fluidInputs(Materials.Hydrogen.getFluid(2000), Materials.Butadiene.getFluid(1000)).fluidOutputs(Materials.HydroCrackedButadiene.getFluid(1000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).fluidInputs(Materials.Steam.getFluid(2000), Materials.Ethane.getFluid(1000)).fluidOutputs(Materials.SteamCrackedEthane.getFluid(1000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).fluidInputs(Materials.Steam.getFluid(2000), Materials.Ethylene.getFluid(1000)).fluidOutputs(Materials.SteamCrackedEthylene.getFluid(1000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).fluidInputs(Materials.Steam.getFluid(2000), Materials.Propene.getFluid(1000)).fluidOutputs(Materials.SteamCrackedPropene.getFluid(1000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).fluidInputs(Materials.Steam.getFluid(2000), Materials.Propane.getFluid(1000)).fluidOutputs(Materials.SteamCrackedPropane.getFluid(1000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).fluidInputs(Materials.Steam.getFluid(2000), Materials.LightFuel.getFluid(1000)).fluidOutputs(Materials.CrackedLightFuel.getFluid(1000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).fluidInputs(Materials.Steam.getFluid(2000), Materials.Butane.getFluid(1000)).fluidOutputs(Materials.SteamCrackedButane.getFluid(1000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).fluidInputs(Materials.Steam.getFluid(2000), Materials.Naphtha.getFluid(1000)).fluidOutputs(Materials.SteamCrackedNaphtha.getFluid(1000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).fluidInputs(Materials.Steam.getFluid(2000), Materials.HeavyFuel.getFluid(1000)).fluidOutputs(Materials.CrackedHeavyFuel.getFluid(1000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).fluidInputs(Materials.Steam.getFluid(2000), Materials.Gas.getFluid(1000)).fluidOutputs(Materials.SteamCrackedGas.getFluid(1000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).fluidInputs(Materials.Steam.getFluid(2000), Materials.Butene.getFluid(1000)).fluidOutputs(Materials.SteamCrackedButene.getFluid(1000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).fluidInputs(Materials.Steam.getFluid(2000), Materials.Butadiene.getFluid(1000)).fluidOutputs(Materials.SteamCrackedButadiene.getFluid(1000)).buildAndRegister();

        //Fish Oil
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(16).EUt(96).fluidInputs(GAMaterials.FishOil.getFluid(24)).fluidOutputs(Materials.Lubricant.getFluid(12)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(600).EUt(30).input(OrePrefix.dustTiny, Materials.SodiumHydroxide).fluidInputs(GAMaterials.FishOil.getFluid(6000), Materials.Methanol.getFluid(1000)).fluidOutputs(Materials.Glycerol.getFluid(1000), Materials.BioDiesel.getFluid(6000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(600).EUt(30).input(OrePrefix.dustTiny, Materials.SodiumHydroxide).fluidInputs(GAMaterials.FishOil.getFluid(6000), Materials.Ethanol.getFluid(1000)).fluidOutputs(Materials.Glycerol.getFluid(1000), Materials.BioDiesel.getFluid(6000)).buildAndRegister();

        //Fluid Heater Recipes
        RecipeMaps.FLUID_HEATER_RECIPES.recipeBuilder().duration(30).EUt(24).circuitMeta(1).fluidInputs(GAMaterials.RawGrowthMedium.getFluid(500)).fluidOutputs(GAMaterials.SterilizedGrowthMedium.getFluid(500)).buildAndRegister();

        //Oil Extractor Recipes
        RecipeMaps.FLUID_EXTRACTION_RECIPES.recipeBuilder().duration(160).EUt(4).inputs(new ItemStack(Items.FISH)).fluidOutputs(GAMaterials.FishOil.getFluid(40)).buildAndRegister();
        RecipeMaps.FLUID_EXTRACTION_RECIPES.recipeBuilder().duration(160).EUt(4).inputs(new ItemStack(Items.FISH, 1, 1)).fluidOutputs(GAMaterials.FishOil.getFluid(60)).buildAndRegister();
        RecipeMaps.FLUID_EXTRACTION_RECIPES.recipeBuilder().duration(160).EUt(4).inputs(new ItemStack(Items.FISH, 1, 2)).fluidOutputs(GAMaterials.FishOil.getFluid(70)).buildAndRegister();
        RecipeMaps.FLUID_EXTRACTION_RECIPES.recipeBuilder().duration(160).EUt(4).inputs(new ItemStack(Items.FISH, 1, 3)).fluidOutputs(GAMaterials.FishOil.getFluid(30)).buildAndRegister();

        //Misc Blast Furnace Recipes
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(120).EUt(120).blastFurnaceTemp(1200).input(OrePrefix.dust, Materials.Galena).fluidInputs(Materials.Oxygen.getFluid(3000)).outputs(OreDictUnifier.get(OrePrefix.dust, GAMaterials.Massicot), OreDictUnifier.get(OrePrefix.nugget, Materials.Lead, 6)).fluidOutputs(Materials.SulfurDioxide.getFluid(1000)).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(120).EUt(120).blastFurnaceTemp(1200).input(OrePrefix.dust, Materials.Stibnite).fluidInputs(Materials.Oxygen.getFluid(3000)).outputs(OreDictUnifier.get(OrePrefix.dust, GAMaterials.AntimonyTrioxide), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Ash)).fluidOutputs(Materials.SulfurDioxide.getFluid(1000)).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(120).EUt(120).blastFurnaceTemp(1200).input(OrePrefix.dust, Materials.Sphalerite).fluidInputs(Materials.Oxygen.getFluid(3000)).outputs(OreDictUnifier.get(OrePrefix.dust, GAMaterials.Zincite), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Ash)).fluidOutputs(Materials.SulfurDioxide.getFluid(1000)).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(120).EUt(120).blastFurnaceTemp(1200).input(OrePrefix.dust, Materials.Cobaltite).fluidInputs(Materials.Oxygen.getFluid(3000)).outputs(OreDictUnifier.get(OrePrefix.dust, GAMaterials.CobaltOxide), OreDictUnifier.get(OrePrefix.dust, GAMaterials.ArsenicTrioxide)).fluidOutputs(Materials.SulfurDioxide.getFluid(1000)).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(120).EUt(120).blastFurnaceTemp(1200).input(OrePrefix.dust, Materials.Tetrahedrite).fluidInputs(Materials.Oxygen.getFluid(3000)).outputs(OreDictUnifier.get(OrePrefix.dust, GAMaterials.CupricOxide), OreDictUnifier.get(OrePrefix.dustTiny, GAMaterials.AntimonyTrioxide, 3)).fluidOutputs(Materials.SulfurDioxide.getFluid(1000)).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(120).EUt(120).blastFurnaceTemp(1200).input(OrePrefix.dust, Materials.Chalcopyrite).fluidInputs(Materials.Oxygen.getFluid(3000)).outputs(OreDictUnifier.get(OrePrefix.dust, GAMaterials.CupricOxide), OreDictUnifier.get(OrePrefix.dust, GAMaterials.Ferrosilite)).fluidOutputs(Materials.SulfurDioxide.getFluid(1000)).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(120).EUt(120).blastFurnaceTemp(1200).input(OrePrefix.dust, Materials.Pentlandite).fluidInputs(Materials.Oxygen.getFluid(3000)).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Garnierite), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Ash)).fluidOutputs(Materials.SulfurDioxide.getFluid(1000)).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(120).EUt(120).blastFurnaceTemp(1200).input(OrePrefix.dust, Materials.Pyrite).fluidInputs(Materials.Oxygen.getFluid(3000)).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.BandedIron), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Ash)).fluidOutputs(Materials.SulfurDioxide.getFluid(1000)).buildAndRegister();

        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(240).EUt(120).blastFurnaceTemp(1200).input(OrePrefix.dust, GAMaterials.Massicot, 2).input(OrePrefix.dust, Materials.Carbon).outputs(OreDictUnifier.get(OrePrefix.ingot, Materials.Lead, 3), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Ash, 2)).fluidOutputs(Materials.CarbonDioxide.getFluid(1000)).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(240).EUt(120).blastFurnaceTemp(1200).input(OrePrefix.dust, GAMaterials.AntimonyTrioxide, 2).input(OrePrefix.dust, Materials.Carbon).outputs(OreDictUnifier.get(OrePrefix.ingot, Materials.Antimony, 3), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Ash, 2)).fluidOutputs(Materials.CarbonDioxide.getFluid(3000)).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(240).EUt(120).blastFurnaceTemp(1200).input(OrePrefix.dust, GAMaterials.CobaltOxide, 2).input(OrePrefix.dust, Materials.Carbon).outputs(OreDictUnifier.get(OrePrefix.ingot, Materials.Cobalt, 3), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Ash, 2)).fluidOutputs(Materials.CarbonDioxide.getFluid(1000)).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(240).EUt(120).blastFurnaceTemp(1200).input(OrePrefix.dust, GAMaterials.ArsenicTrioxide, 2).input(OrePrefix.dust, Materials.Carbon).outputs(OreDictUnifier.get(OrePrefix.ingot, Materials.Arsenic, 3), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Ash, 2)).fluidOutputs(Materials.CarbonDioxide.getFluid(1000)).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(240).EUt(120).blastFurnaceTemp(1200).input(OrePrefix.dust, GAMaterials.CupricOxide, 2).input(OrePrefix.dust, Materials.Carbon).outputs(OreDictUnifier.get(OrePrefix.ingot, Materials.Copper, 3), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Ash, 2)).fluidOutputs(Materials.CarbonDioxide.getFluid(1000)).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(240).EUt(120).blastFurnaceTemp(1200).input(OrePrefix.dust, Materials.Garnierite, 2).input(OrePrefix.dust, Materials.Carbon).outputs(OreDictUnifier.get(OrePrefix.ingot, Materials.Nickel, 3), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Ash, 2)).fluidOutputs(Materials.CarbonDioxide.getFluid(1000)).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(240).EUt(120).blastFurnaceTemp(1200).input(OrePrefix.dust, Materials.BandedIron, 2).input(OrePrefix.dust, Materials.Carbon).outputs(OreDictUnifier.get(OrePrefix.ingot, Materials.Iron, 3), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Ash, 2)).fluidOutputs(Materials.CarbonDioxide.getFluid(1000)).buildAndRegister();

        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(240).EUt(120).blastFurnaceTemp(1200).input(OrePrefix.dust, Materials.SiliconDioxide).input(OrePrefix.dust, Materials.Carbon, 2).outputs(OreDictUnifier.get(OrePrefix.ingot, Materials.Silicon), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Ash)).fluidOutputs(Materials.CarbonMonoxde.getFluid(2000)).buildAndRegister();

        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(240).EUt(120).blastFurnaceTemp(1200).input(OrePrefix.dust, Materials.Malachite, 2).input(OrePrefix.dust, Materials.Carbon).outputs(OreDictUnifier.get(OrePrefix.ingot, Materials.Copper, 3), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Ash, 2)).fluidOutputs(Materials.CarbonDioxide.getFluid(3000)).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(240).EUt(120).blastFurnaceTemp(1200).input(OrePrefix.dust, Materials.Magnetite, 2).input(OrePrefix.dust, Materials.Carbon).outputs(OreDictUnifier.get(OrePrefix.ingot, Materials.Iron, 3), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Ash, 2)).fluidOutputs(Materials.CarbonDioxide.getFluid(1000)).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(240).EUt(120).blastFurnaceTemp(1200).input(OrePrefix.dust, Materials.GraniticMineralSand, 2).input(OrePrefix.dust, Materials.Carbon).outputs(OreDictUnifier.get(OrePrefix.ingot, Materials.Iron, 3), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Ash, 2)).fluidOutputs(Materials.CarbonDioxide.getFluid(1000)).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(240).EUt(120).blastFurnaceTemp(1200).input(OrePrefix.dust, Materials.BrownLimonite, 2).input(OrePrefix.dust, Materials.Carbon).outputs(OreDictUnifier.get(OrePrefix.ingot, Materials.Iron, 3), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Ash, 2)).fluidOutputs(Materials.CarbonDioxide.getFluid(1000)).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(240).EUt(120).blastFurnaceTemp(1200).input(OrePrefix.dust, Materials.YellowLimonite, 2).input(OrePrefix.dust, Materials.Carbon).outputs(OreDictUnifier.get(OrePrefix.ingot, Materials.Iron, 3), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Ash, 2)).fluidOutputs(Materials.CarbonDioxide.getFluid(1000)).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(240).EUt(120).blastFurnaceTemp(1200).input(OrePrefix.dust, Materials.BasalticMineralSand, 2).input(OrePrefix.dust, Materials.Carbon).outputs(OreDictUnifier.get(OrePrefix.ingot, Materials.Iron, 3), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Ash, 2)).fluidOutputs(Materials.CarbonDioxide.getFluid(1000)).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(240).EUt(120).blastFurnaceTemp(1200).input(OrePrefix.dust, Materials.Cassiterite, 2).input(OrePrefix.dust, Materials.Carbon).outputs(OreDictUnifier.get(OrePrefix.ingot, Materials.Tin, 3), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Ash, 2)).fluidOutputs(Materials.CarbonDioxide.getFluid(1000)).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(240).EUt(120).blastFurnaceTemp(1200).input(OrePrefix.dust, Materials.CassiteriteSand, 2).input(OrePrefix.dust, Materials.Carbon).outputs(OreDictUnifier.get(OrePrefix.ingot, Materials.Tin, 3), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Ash, 2)).fluidOutputs(Materials.CarbonDioxide.getFluid(1000)).buildAndRegister();

        for (MaterialStack ore : ironOres) {
            Material materials = ore.material;
            RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(120).blastFurnaceTemp(1500).input(OrePrefix.ore, materials).input(OrePrefix.dust, Materials.Calcite).outputs(OreDictUnifier.get(OrePrefix.ingot, Materials.Iron, 3), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
            RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(120).blastFurnaceTemp(1500).input(OrePrefix.ore, materials).input(OrePrefix.dustTiny, Materials.Quicklime, 3).outputs(OreDictUnifier.get(OrePrefix.ingot, Materials.Iron, 2), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
        }

        //Mince Meat Recipes
        RecipeMaps.MACERATOR_RECIPES.recipeBuilder().duration(60).EUt(16).inputs(new ItemStack(Items.PORKCHOP)).outputs(OreDictUnifier.get(OrePrefix.dustSmall, GAMaterials.Meat, 6)).buildAndRegister();
        RecipeMaps.MACERATOR_RECIPES.recipeBuilder().duration(60).EUt(16).inputs(new ItemStack(Items.BEEF)).outputs(OreDictUnifier.get(OrePrefix.dustSmall, GAMaterials.Meat, 6)).buildAndRegister();
        RecipeMaps.MACERATOR_RECIPES.recipeBuilder().duration(60).EUt(16).inputs(new ItemStack(Items.RABBIT)).outputs(OreDictUnifier.get(OrePrefix.dustSmall, GAMaterials.Meat, 6)).buildAndRegister();
        RecipeMaps.MACERATOR_RECIPES.recipeBuilder().duration(40).EUt(16).inputs(new ItemStack(Items.CHICKEN)).outputs(OreDictUnifier.get(OrePrefix.dust, GAMaterials.Meat)).buildAndRegister();
        RecipeMaps.MACERATOR_RECIPES.recipeBuilder().duration(40).EUt(16).inputs(new ItemStack(Items.MUTTON)).outputs(OreDictUnifier.get(OrePrefix.dust, GAMaterials.Meat)).buildAndRegister();

        //Circuit Rabbit Hole - Layer 1
        ModHandler.removeRecipes(MetaItems.BASIC_CIRCUIT_LV.getStackForm());
        ModHandler.addShapedRecipe("basic_circuit_ga", MetaItems.BASIC_CIRCUIT_LV.getStackForm(), "RPR", "TBT", "CCC", 'R', MetaItems.RESISTOR, 'P', "plateSteel", 'T', MetaItems.VACUUM_TUBE, 'B', GAMetaItems.BASIC_BOARD, 'C', new UnificationEntry(OrePrefix.cableGtSingle, Materials.RedAlloy));
        ModHandler.removeRecipeByName(new ResourceLocation("gregtech:good_circuit"));
        ModHandler.addShapedRecipe("good_circuit_ga", GAMetaItems.GOOD_CIRCUIT.getStackForm(), "WPW", "CBC", "DCD", 'P', "plateSteel", 'C', MetaItems.BASIC_CIRCUIT_LV.getStackForm(), 'W', OreDictUnifier.get(OrePrefix.wireGtSingle, Materials.Copper), 'D', MetaItems.DIODE.getStackForm(), 'B', GAMetaItems.GOOD_PHENOLIC_BOARD);

        for (MaterialStack stack : solderingList) {
            IngotMaterial material = (IngotMaterial) stack.material;
            int multiplier = (int) stack.amount;
            GARecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder().duration(200).EUt(16).inputs(GAMetaItems.BASIC_BOARD.getStackForm(), MetaItems.RESISTOR.getStackForm(2), OreDictUnifier.get(OrePrefix.wireGtSingle, Materials.RedAlloy, 2), MetaItems.VACUUM_TUBE.getStackForm(2)).fluidInputs(material.getFluid(72 * multiplier)).outputs(MetaItems.BASIC_CIRCUIT_LV.getStackForm()).buildAndRegister();
            GARecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder().duration(200).EUt(16).inputs(GAMetaItems.BASIC_BOARD.getStackForm(), MetaItems.SMD_RESISTOR.getStackForm(2), OreDictUnifier.get(OrePrefix.wireGtSingle, Materials.RedAlloy, 2), MetaItems.VACUUM_TUBE.getStackForm(2)).fluidInputs(material.getFluid(72 * multiplier)).outputs(MetaItems.BASIC_CIRCUIT_LV.getStackForm()).buildAndRegister();
            GARecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder().duration(300).EUt(30).inputs(GAMetaItems.GOOD_PHENOLIC_BOARD.getStackForm(), MetaItems.BASIC_CIRCUIT_LV.getStackForm(2), MetaItems.DIODE.getStackForm(2)).input(OrePrefix.wireGtSingle, Materials.Copper, 2).fluidInputs(material.getFluid(72 * multiplier)).outputs(GAMetaItems.GOOD_CIRCUIT.getStackForm()).buildAndRegister();
            GARecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder().duration(300).EUt(30).inputs(GAMetaItems.GOOD_PHENOLIC_BOARD.getStackForm(), MetaItems.BASIC_CIRCUIT_LV.getStackForm(2), MetaItems.SMD_DIODE.getStackForm(2)).input(OrePrefix.wireGtSingle, Materials.Copper, 2).fluidInputs(material.getFluid(72 * multiplier)).outputs(GAMetaItems.GOOD_CIRCUIT.getStackForm()).buildAndRegister();
            GARecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder().duration(200).EUt(8).inputs(GAMetaItems.BASIC_BOARD.getStackForm(), MetaItems.INTEGRATED_LOGIC_CIRCUIT.getStackForm(), MetaItems.RESISTOR.getStackForm(2), OreDictUnifier.get(OrePrefix.wireFine, Materials.Copper)).fluidInputs(material.getFluid(72 * multiplier)).outputs(MetaItems.BASIC_ELECTRONIC_CIRCUIT_LV.getStackForm()).buildAndRegister();
            GARecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder().duration(200).EUt(8).inputs(GAMetaItems.BASIC_BOARD.getStackForm(), MetaItems.INTEGRATED_LOGIC_CIRCUIT.getStackForm(), MetaItems.SMD_RESISTOR.getStackForm(2), OreDictUnifier.get(OrePrefix.wireFine, Materials.Copper)).fluidInputs(material.getFluid(72 * multiplier)).outputs(MetaItems.BASIC_ELECTRONIC_CIRCUIT_LV.getStackForm()).buildAndRegister();
            GARecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder().duration(200).EUt(60).inputs(GAMetaItems.GOOD_PLASTIC_BOARD.getStackForm(), MetaItems.CENTRAL_PROCESSING_UNIT.getStackForm(4), MetaItems.RESISTOR.getStackForm(4), MetaItems.CAPACITOR.getStackForm(4), MetaItems.TRANSISTOR.getStackForm(4), OreDictUnifier.get(OrePrefix.wireFine, Materials.Copper, 2)).fluidInputs(material.getFluid(72 * multiplier)).outputs(MetaItems.ADVANCED_CIRCUIT_PARTS_LV.getStackForm(4)).buildAndRegister();
            GARecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder().duration(200).EUt(60).inputs(GAMetaItems.GOOD_PLASTIC_BOARD.getStackForm(), MetaItems.CENTRAL_PROCESSING_UNIT.getStackForm(4), MetaItems.SMD_RESISTOR.getStackForm(4), MetaItems.SMD_CAPACITOR.getStackForm(4), MetaItems.SMD_TRANSISTOR.getStackForm(4), OreDictUnifier.get(OrePrefix.wireFine, Materials.Copper, 2)).fluidInputs(material.getFluid(72 * multiplier)).outputs(MetaItems.ADVANCED_CIRCUIT_PARTS_LV.getStackForm(4)).buildAndRegister();
            GARecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(600).inputs(GAMetaItems.GOOD_PLASTIC_BOARD.getStackForm(), MetaItems.SYSTEM_ON_CHIP.getStackForm(4), OreDictUnifier.get(OrePrefix.wireFine, Materials.Copper, 2)).fluidInputs(material.getFluid(72 * multiplier)).outputs(MetaItems.ADVANCED_CIRCUIT_PARTS_LV.getStackForm(4)).buildAndRegister();
            GARecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder().duration(400).EUt(16).inputs(GAMetaItems.GOOD_PHENOLIC_BOARD.getStackForm(), MetaItems.BASIC_ELECTRONIC_CIRCUIT_LV.getStackForm(2), MetaItems.RESISTOR.getStackForm(4), OreDictUnifier.get(OrePrefix.wireFine, Materials.Electrum, 8)).fluidInputs(material.getFluid(72 * multiplier)).outputs(MetaItems.GOOD_INTEGRATED_CIRCUIT_MV.getStackForm()).buildAndRegister();
            GARecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder().duration(400).EUt(16).inputs(GAMetaItems.GOOD_PHENOLIC_BOARD.getStackForm(), MetaItems.BASIC_ELECTRONIC_CIRCUIT_LV.getStackForm(2), MetaItems.SMD_RESISTOR.getStackForm(4), OreDictUnifier.get(OrePrefix.wireFine, Materials.Electrum, 8)).fluidInputs(material.getFluid(72 * multiplier)).outputs(MetaItems.GOOD_INTEGRATED_CIRCUIT_MV.getStackForm()).buildAndRegister();
            GARecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder().duration(200).EUt(60).inputs(GAMetaItems.GOOD_PLASTIC_BOARD.getStackForm(), MetaItems.CENTRAL_PROCESSING_UNIT.getStackForm(), MetaItems.RESISTOR.getStackForm(2), MetaItems.CAPACITOR.getStackForm(2), MetaItems.TRANSISTOR.getStackForm(2), OreDictUnifier.get(OrePrefix.wireFine, Materials.RedAlloy, 2)).fluidInputs(material.getFluid(72 * multiplier)).outputs(MetaItems.ADVANCED_CIRCUIT_MV.getStackForm()).buildAndRegister();
            GARecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder().duration(200).EUt(60).inputs(GAMetaItems.GOOD_PLASTIC_BOARD.getStackForm(), MetaItems.CENTRAL_PROCESSING_UNIT.getStackForm(), MetaItems.SMD_RESISTOR.getStackForm(2), MetaItems.SMD_CAPACITOR.getStackForm(2), MetaItems.SMD_TRANSISTOR.getStackForm(2), OreDictUnifier.get(OrePrefix.wireFine, Materials.RedAlloy, 2)).fluidInputs(material.getFluid(72 * multiplier)).outputs(MetaItems.ADVANCED_CIRCUIT_MV.getStackForm()).buildAndRegister();
            GARecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(2400).inputs(GAMetaItems.GOOD_PLASTIC_BOARD.getStackForm(), MetaItems.SYSTEM_ON_CHIP.getStackForm(), OreDictUnifier.get(OrePrefix.wireFine, Materials.RedAlloy, 2)).fluidInputs(material.getFluid(72 * multiplier)).outputs(MetaItems.ADVANCED_CIRCUIT_MV.getStackForm()).buildAndRegister();
            GARecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder().duration(800).EUt(28).inputs(MetaItems.GOOD_INTEGRATED_CIRCUIT_MV.getStackForm(2), MetaItems.INTEGRATED_LOGIC_CIRCUIT.getStackForm(3), MetaItems.RANDOM_ACCESS_MEMORY.getStackForm(), MetaItems.TRANSISTOR.getStackForm(4), OreDictUnifier.get(OrePrefix.wireFine, Materials.Electrum, 16)).fluidInputs(material.getFluid(72 * multiplier)).outputs(GAMetaItems.ADVANCED_CIRCUIT.getStackForm()).buildAndRegister();
            GARecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder().duration(800).EUt(28).inputs(MetaItems.GOOD_INTEGRATED_CIRCUIT_MV.getStackForm(2), MetaItems.INTEGRATED_LOGIC_CIRCUIT.getStackForm(3), MetaItems.RANDOM_ACCESS_MEMORY.getStackForm(), MetaItems.SMD_TRANSISTOR.getStackForm(4), OreDictUnifier.get(OrePrefix.wireFine, Materials.Electrum, 16)).fluidInputs(material.getFluid(72 * multiplier)).outputs(GAMetaItems.ADVANCED_CIRCUIT.getStackForm()).buildAndRegister();
            GARecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder().duration(400).EUt(90).inputs(GAMetaItems.GOOD_PLASTIC_BOARD.getStackForm(), MetaItems.ADVANCED_CIRCUIT_MV.getStackForm(2), MetaItems.SMALL_COIL.getStackForm(4), MetaItems.CAPACITOR.getStackForm(4), MetaItems.RANDOM_ACCESS_MEMORY.getStackForm(4), OreDictUnifier.get(OrePrefix.wireFine, Materials.RedAlloy, 12)).fluidInputs(material.getFluid(144 * multiplier)).outputs(MetaItems.PROCESSOR_ASSEMBLY_HV.getStackForm()).buildAndRegister();
            GARecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder().duration(400).EUt(80).inputs(GAMetaItems.GOOD_PLASTIC_BOARD.getStackForm(), MetaItems.ADVANCED_CIRCUIT_MV.getStackForm(2), MetaItems.SMALL_COIL.getStackForm(4), MetaItems.SMD_CAPACITOR.getStackForm(4), MetaItems.RANDOM_ACCESS_MEMORY.getStackForm(4), OreDictUnifier.get(OrePrefix.wireFine, Materials.RedAlloy, 12)).fluidInputs(material.getFluid(144 * multiplier)).outputs(MetaItems.PROCESSOR_ASSEMBLY_HV.getStackForm()).buildAndRegister();
            GARecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder().duration(200).EUt(600).inputs(GAMetaItems.ADVANCED_BOARD.getStackForm(), MetaItems.NANO_CENTRAL_PROCESSING_UNIT.getStackForm(), MetaItems.SMD_RESISTOR.getStackForm(2), MetaItems.SMD_CAPACITOR.getStackForm(2), MetaItems.SMD_TRANSISTOR.getStackForm(2), OreDictUnifier.get(OrePrefix.wireFine, Materials.Electrum, 2)).fluidInputs(material.getFluid(72 * multiplier)).outputs(MetaItems.NANO_PROCESSOR_HV.getStackForm()).buildAndRegister();
            GARecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(9600).inputs(GAMetaItems.ADVANCED_BOARD.getStackForm(), MetaItems.SYSTEM_ON_CHIP.getStackForm(), OreDictUnifier.get(OrePrefix.wireFine, Materials.Electrum, 2)).fluidInputs(material.getFluid(72 * multiplier)).outputs(MetaItems.NANO_PROCESSOR_HV.getStackForm()).buildAndRegister();
            GARecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder().duration(400).EUt(90).inputs(GAMetaItems.GOOD_PLASTIC_BOARD.getStackForm(2), MetaItems.PROCESSOR_ASSEMBLY_HV.getStackForm(2), MetaItems.DIODE.getStackForm(4), MetaItems.RANDOM_ACCESS_MEMORY.getStackForm(4), OreDictUnifier.get(OrePrefix.wireFine, Materials.Electrum, 6)).fluidInputs(material.getFluid(144 * multiplier)).outputs(GAMetaItems.INTEGRATED_COMPUTER.getStackForm()).buildAndRegister();
            GARecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder().duration(400).EUt(90).inputs(GAMetaItems.GOOD_PLASTIC_BOARD.getStackForm(2), MetaItems.PROCESSOR_ASSEMBLY_HV.getStackForm(2), MetaItems.SMD_DIODE.getStackForm(4), MetaItems.RANDOM_ACCESS_MEMORY.getStackForm(4), OreDictUnifier.get(OrePrefix.wireFine, Materials.Electrum, 6)).fluidInputs(material.getFluid(144 * multiplier)).outputs(GAMetaItems.INTEGRATED_COMPUTER.getStackForm()).buildAndRegister();
            GARecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder().duration(400).EUt(600).inputs(GAMetaItems.ADVANCED_BOARD.getStackForm(), MetaItems.NANO_PROCESSOR_HV.getStackForm(2), MetaItems.SMALL_COIL.getStackForm(4), MetaItems.SMD_CAPACITOR.getStackForm(4), MetaItems.RANDOM_ACCESS_MEMORY.getStackForm(4), OreDictUnifier.get(OrePrefix.wireFine, Materials.Electrum, 6)).fluidInputs(material.getFluid(144 * multiplier)).outputs(MetaItems.NANO_PROCESSOR_ASSEMBLY_EV.getStackForm()).buildAndRegister();
            GARecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder().duration(200).EUt(2400).inputs(GAMetaItems.EXTREME_BOARD.getStackForm(), MetaItems.QBIT_CENTRAL_PROCESSING_UNIT.getStackForm(), MetaItems.NANO_CENTRAL_PROCESSING_UNIT.getStackForm(), MetaItems.SMD_CAPACITOR.getStackForm(2), MetaItems.SMD_TRANSISTOR.getStackForm(2), OreDictUnifier.get(OrePrefix.wireFine, Materials.Platinum, 2)).fluidInputs(material.getFluid(72 * multiplier)).outputs(MetaItems.QUANTUM_PROCESSOR_EV.getStackForm()).buildAndRegister();
            GARecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(38400).inputs(GAMetaItems.EXTREME_BOARD.getStackForm(), MetaItems.ADVANCED_SYSTEM_ON_CHIP.getStackForm(), OreDictUnifier.get(OrePrefix.wireFine, Materials.Platinum, 2)).fluidInputs(material.getFluid(72 * multiplier)).outputs(MetaItems.QUANTUM_PROCESSOR_EV.getStackForm()).buildAndRegister();
            GARecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder().duration(1600).EUt(480).inputs(OreDictUnifier.get(OrePrefix.frameGt, Materials.Aluminium), GAMetaItems.INTEGRATED_COMPUTER.getStackForm(2), MetaItems.SMALL_COIL.getStackForm(4), MetaItems.CAPACITOR.getStackForm(24), MetaItems.RANDOM_ACCESS_MEMORY.getStackForm(16), OreDictUnifier.get(OrePrefix.wireGtSingle, Materials.AnnealedCopper, 12)).fluidInputs(material.getFluid(288 * multiplier)).outputs(GAMetaItems.INTEGRATED_MAINFRAME.getStackForm()).buildAndRegister();
            GARecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder().duration(1600).EUt(480).inputs(OreDictUnifier.get(OrePrefix.frameGt, Materials.Aluminium), GAMetaItems.INTEGRATED_COMPUTER.getStackForm(2), MetaItems.SMALL_COIL.getStackForm(4), MetaItems.SMD_CAPACITOR.getStackForm(24), MetaItems.RANDOM_ACCESS_MEMORY.getStackForm(16), OreDictUnifier.get(OrePrefix.wireGtSingle, Materials.AnnealedCopper, 12)).fluidInputs(material.getFluid(288 * multiplier)).outputs(GAMetaItems.INTEGRATED_MAINFRAME.getStackForm()).buildAndRegister();
            GARecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder().duration(400).EUt(600).inputs(GAMetaItems.ADVANCED_BOARD.getStackForm(2), MetaItems.NANO_PROCESSOR_ASSEMBLY_EV.getStackForm(2), MetaItems.SMD_DIODE.getStackForm(4), MetaItems.NOR_MEMORY_CHIP.getStackForm(4), MetaItems.RANDOM_ACCESS_MEMORY.getStackForm(4), OreDictUnifier.get(OrePrefix.wireFine, Materials.Electrum, 6)).fluidInputs(material.getFluid(144 * multiplier)).outputs(GAMetaItems.NANO_COMPUTER.getStackForm()).buildAndRegister();
            GARecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder().duration(400).EUt(2400).inputs(GAMetaItems.EXTREME_BOARD.getStackForm(), MetaItems.QUANTUM_PROCESSOR_EV.getStackForm(2), MetaItems.SMALL_COIL.getStackForm(4), MetaItems.SMD_CAPACITOR.getStackForm(4), MetaItems.RANDOM_ACCESS_MEMORY.getStackForm(4), OreDictUnifier.get(OrePrefix.wireFine, Materials.Platinum, 6)).fluidInputs(material.getFluid(144 * multiplier)).outputs(MetaItems.DATA_CONTROL_CIRCUIT_IV.getStackForm()).buildAndRegister();
            GARecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder().duration(200).EUt(9600).inputs(GAMetaItems.ELITE_BOARD.getStackForm(), MetaItems.CRYSTAL_CENTRAL_PROCESSING_UNIT.getStackForm(), MetaItems.NANO_CENTRAL_PROCESSING_UNIT.getStackForm(), MetaItems.SMD_CAPACITOR.getStackForm(2), MetaItems.SMD_TRANSISTOR.getStackForm(2), OreDictUnifier.get(OrePrefix.wireFine, Materials.NiobiumTitanium, 2)).fluidInputs(material.getFluid(72 * multiplier)).outputs(MetaItems.CRYSTAL_PROCESSOR_IV.getStackForm()).buildAndRegister();
            GARecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(153600).inputs(GAMetaItems.ELITE_BOARD.getStackForm(), MetaItems.CRYSTAL_SYSTEM_ON_CHIP.getStackForm(), OreDictUnifier.get(OrePrefix.wireFine, Materials.NiobiumTitanium, 2)).fluidInputs(material.getFluid(72 * multiplier)).outputs(MetaItems.CRYSTAL_PROCESSOR_IV.getStackForm()).buildAndRegister();
            GARecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder().duration(1600).EUt(1920).inputs(OreDictUnifier.get(OrePrefix.frameGt, Materials.Aluminium), GAMetaItems.NANO_COMPUTER.getStackForm(2), MetaItems.SMALL_COIL.getStackForm(4), MetaItems.SMD_CAPACITOR.getStackForm(24), MetaItems.RANDOM_ACCESS_MEMORY.getStackForm(16), OreDictUnifier.get(OrePrefix.wireGtSingle, Materials.AnnealedCopper, 12)).fluidInputs(material.getFluid(288 * multiplier)).outputs(GAMetaItems.NANO_MAINFRAME.getStackForm()).buildAndRegister();
            GARecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder().duration(400).EUt(2400).inputs(GAMetaItems.EXTREME_BOARD.getStackForm(2), MetaItems.DATA_CONTROL_CIRCUIT_IV.getStackForm(2), MetaItems.SMD_DIODE.getStackForm(4), MetaItems.NOR_MEMORY_CHIP.getStackForm(4), MetaItems.RANDOM_ACCESS_MEMORY.getStackForm(4), OreDictUnifier.get(OrePrefix.wireFine, Materials.Platinum, 6)).fluidInputs(material.getFluid(144 * multiplier)).outputs(GAMetaItems.QUANTUM_COMPUTER.getStackForm()).buildAndRegister();
            GARecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder().duration(400).EUt(9600).inputs(GAMetaItems.ELITE_BOARD.getStackForm(), MetaItems.CRYSTAL_PROCESSOR_IV.getStackForm(2), MetaItems.SMALL_COIL.getStackForm(4), MetaItems.SMD_CAPACITOR.getStackForm(4), MetaItems.RANDOM_ACCESS_MEMORY.getStackForm(4), OreDictUnifier.get(OrePrefix.wireFine, Materials.NiobiumTitanium, 6)).fluidInputs(material.getFluid(144 * multiplier)).outputs(MetaItems.ENERGY_FLOW_CIRCUIT_LUV.getStackForm()).buildAndRegister();
            GARecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder().duration(200).EUt(38400).inputs(GAMetaItems.NEURO_PROCESSOR.getStackForm(), MetaItems.CRYSTAL_CENTRAL_PROCESSING_UNIT.getStackForm(), MetaItems.NANO_CENTRAL_PROCESSING_UNIT.getStackForm(), MetaItems.SMD_CAPACITOR.getStackForm(2), MetaItems.SMD_TRANSISTOR.getStackForm(2), OreDictUnifier.get(OrePrefix.wireFine, Materials.YttriumBariumCuprate, 2)).fluidInputs(material.getFluid(72 * multiplier)).outputs(MetaItems.WETWARE_PROCESSOR_LUV.getStackForm()).buildAndRegister();
            GARecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder().duration(1600).EUt(7680).inputs(OreDictUnifier.get(OrePrefix.frameGt, Materials.Aluminium), GAMetaItems.QUANTUM_COMPUTER.getStackForm(2), MetaItems.SMALL_COIL.getStackForm(4), MetaItems.SMD_CAPACITOR.getStackForm(24), MetaItems.RANDOM_ACCESS_MEMORY.getStackForm(16), OreDictUnifier.get(OrePrefix.wireGtSingle, Materials.AnnealedCopper, 12)).fluidInputs(material.getFluid(288 * multiplier)).outputs(GAMetaItems.QUANTUM_MAINFRAME.getStackForm()).buildAndRegister();
            GARecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder().duration(400).EUt(9600).inputs(GAMetaItems.ELITE_BOARD.getStackForm(2), MetaItems.ENERGY_FLOW_CIRCUIT_LUV.getStackForm(2), MetaItems.SMD_DIODE.getStackForm(4), MetaItems.NOR_MEMORY_CHIP.getStackForm(4), MetaItems.RANDOM_ACCESS_MEMORY.getStackForm(4), OreDictUnifier.get(OrePrefix.wireFine, Materials.NiobiumTitanium, 6)).fluidInputs(material.getFluid(144 * multiplier)).outputs(GAMetaItems.CRYSTAL_COMPUTER.getStackForm()).buildAndRegister();
            GARecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder().duration(400).EUt(38400).inputs(GAMetaItems.MASTER_BOARD.getStackForm(), MetaItems.WETWARE_PROCESSOR_LUV.getStackForm(2), MetaItems.SMALL_COIL.getStackForm(4), MetaItems.SMD_CAPACITOR.getStackForm(4), MetaItems.RANDOM_ACCESS_MEMORY.getStackForm(4), OreDictUnifier.get(OrePrefix.wireFine, Materials.YttriumBariumCuprate, 6)).fluidInputs(material.getFluid(144 * multiplier)).outputs(MetaItems.WETWARE_PROCESSOR_ASSEMBLY_ZPM.getStackForm()).buildAndRegister();
            GARecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder().duration(1600).EUt(30720).inputs(OreDictUnifier.get(OrePrefix.frameGt, Materials.Aluminium), GAMetaItems.CRYSTAL_COMPUTER.getStackForm(2), MetaItems.SMALL_COIL.getStackForm(4), MetaItems.SMD_CAPACITOR.getStackForm(24), MetaItems.RANDOM_ACCESS_MEMORY.getStackForm(16), OreDictUnifier.get(OrePrefix.wireGtSingle, GAMaterials.LuVSuperconductor, 12)).fluidInputs(material.getFluid(288 * multiplier)).outputs(GAMetaItems.CRYSTAL_MAINFRAME.getStackForm()).buildAndRegister();
            GARecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder().duration(400).EUt(38400).inputs(GAMetaItems.MASTER_BOARD.getStackForm(2), MetaItems.WETWARE_PROCESSOR_ASSEMBLY_ZPM.getStackForm(2), MetaItems.SMD_DIODE.getStackForm(4), MetaItems.NOR_MEMORY_CHIP.getStackForm(4), MetaItems.RANDOM_ACCESS_MEMORY.getStackForm(4), OreDictUnifier.get(OrePrefix.wireFine, Materials.YttriumBariumCuprate, 6)).fluidInputs(material.getFluid(144 * multiplier)).outputs(MetaItems.WETWARE_SUPER_COMPUTER_UV.getStackForm()).buildAndRegister();
            GARecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder().duration(512).EUt(1024).inputs(GAMetaItems.EXTREME_BOARD.getStackForm(), MetaItems.POWER_INTEGRATED_CIRCUIT.getStackForm(4), MetaItems.ENGRAVED_LAPOTRON_CHIP.getStackForm(18), MetaItems.NANO_CENTRAL_PROCESSING_UNIT.getStackForm(), OreDictUnifier.get(OrePrefix.wireFine, Materials.Platinum, 16)).fluidInputs(material.getFluid(144 * multiplier)).outputs(MetaItems.ENERGY_LAPOTRONIC_ORB.getStackForm()).buildAndRegister();
            GARecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder().duration(1024).EUt(4096).inputs(GAMetaItems.EXTREME_BOARD.getStackForm(), MetaItems.HIGH_POWER_INTEGRATED_CIRCUIT.getStackForm(4), MetaItems.ENERGY_LAPOTRONIC_ORB.getStackForm(8), MetaItems.QBIT_CENTRAL_PROCESSING_UNIT.getStackForm(), OreDictUnifier.get(OrePrefix.wireFine, Materials.Platinum, 16)).input(OrePrefix.plate, Materials.Europium, 4).fluidInputs(material.getFluid(144 * multiplier)).outputs(MetaItems.ENERGY_LAPOTRONIC_ORB2.getStackForm()).buildAndRegister();
            GARecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder().duration(400).EUt(90).inputs(GAMetaItems.GOOD_PLASTIC_BOARD.getStackForm(), MetaItems.ADVANCED_CIRCUIT_MV.getStackForm(), MetaItems.NAND_MEMORY_CHIP.getStackForm(32), MetaItems.RANDOM_ACCESS_MEMORY.getStackForm(4), OreDictUnifier.get(OrePrefix.wireFine, Materials.RedAlloy, 8)).input(OrePrefix.plate, Materials.Plastic, 4).fluidInputs(material.getFluid(144 * multiplier)).outputs(MetaItems.TOOL_DATA_STICK.getStackForm()).buildAndRegister();
            GARecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder().duration(400).EUt(1200).inputs(GAMetaItems.ADVANCED_BOARD.getStackForm(), MetaItems.NANO_PROCESSOR_HV.getStackForm(), MetaItems.RANDOM_ACCESS_MEMORY.getStackForm(4), MetaItems.RANDOM_ACCESS_MEMORY.getStackForm(32), MetaItems.NAND_MEMORY_CHIP.getStackForm(64), OreDictUnifier.get(OrePrefix.wireFine, Materials.Platinum, 32)).fluidInputs(material.getFluid(144 * multiplier)).outputs(MetaItems.TOOL_DATA_ORB.getStackForm()).buildAndRegister();
        }

        //Mixer Alloying
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(100).EUt(16).fluidInputs(Materials.Copper.getFluid(432), Materials.Tin.getFluid(144)).fluidOutputs(Materials.Bronze.getFluid(576)).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(100).EUt(16).fluidInputs(Materials.AnnealedCopper.getFluid(432), Materials.Tin.getFluid(144)).fluidOutputs(Materials.Bronze.getFluid(576)).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(100).EUt(16).fluidInputs(Materials.Copper.getFluid(432), Materials.Zinc.getFluid(144)).fluidOutputs(Materials.Brass.getFluid(576)).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(100).EUt(16).fluidInputs(Materials.AnnealedCopper.getFluid(432), Materials.Zinc.getFluid(144)).fluidOutputs(Materials.Brass.getFluid(576)).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(50).EUt(16).fluidInputs(Materials.Copper.getFluid(144), Materials.Nickel.getFluid(144)).fluidOutputs(Materials.Cupronickel.getFluid(288)).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(50).EUt(16).fluidInputs(Materials.AnnealedCopper.getFluid(144), Materials.Nickel.getFluid(144)).fluidOutputs(Materials.Cupronickel.getFluid(288)).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(25).EUt(16).fluidInputs(Materials.Redstone.getFluid(576), Materials.Copper.getFluid(144)).fluidOutputs(Materials.RedAlloy.getFluid(144)).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(25).EUt(16).fluidInputs(Materials.Redstone.getFluid(576), Materials.AnnealedCopper.getFluid(144)).fluidOutputs(Materials.RedAlloy.getFluid(144)).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(50).EUt(16).fluidInputs(Materials.Iron.getFluid(144), Materials.Tin.getFluid(144)).fluidOutputs(Materials.TinAlloy.getFluid(288)).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(50).EUt(16).fluidInputs(Materials.WroughtIron.getFluid(144), Materials.Tin.getFluid(144)).fluidOutputs(Materials.TinAlloy.getFluid(288)).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(75).EUt(16).fluidInputs(Materials.Iron.getFluid(288), Materials.Nickel.getFluid(144)).fluidOutputs(Materials.Invar.getFluid(432)).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(75).EUt(16).fluidInputs(Materials.WroughtIron.getFluid(288), Materials.Nickel.getFluid(144)).fluidOutputs(Materials.Invar.getFluid(432)).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(250).EUt(16).fluidInputs(Materials.Tin.getFluid(1296), Materials.Antimony.getFluid(144)).fluidOutputs(Materials.SolderingAlloy.getFluid(1440)).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(125).EUt(16).fluidInputs(Materials.Lead.getFluid(576), Materials.Antimony.getFluid(144)).fluidOutputs(Materials.BatteryAlloy.getFluid(720)).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(50).EUt(16).fluidInputs(Materials.Gold.getFluid(144), Materials.Silver.getFluid(144)).fluidOutputs(Materials.Electrum.getFluid(288)).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(75).EUt(16).fluidInputs(Materials.Aluminium.getFluid(288), Materials.Magnesium.getFluid(144)).fluidOutputs(Materials.Magnalium.getFluid(432)).buildAndRegister();

        //Ferroboron - Tough Alloy support, useful for sme mods
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(50).EUt(16).fluidInputs(Materials.Steel.getFluid(144), Materials.Boron.getFluid(144)).fluidOutputs(GAMaterials.Ferroboron.getFluid(288)).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(50).EUt(16).fluidInputs(GAMaterials.Ferroboron.getFluid(144), Materials.Lithium.getFluid(144)).fluidOutputs(GAMaterials.ToughAlloy.getFluid(288)).buildAndRegister();

        GARecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder().duration(30).EUt(4).input(OrePrefix.dust, Materials.Tantalum).input(OrePrefix.foil, Materials.Manganese).fluidInputs(Materials.Plastic.getFluid(144)).outputs(MetaItems.BATTERY_RE_ULV_TANTALUM.getStackForm(8)).buildAndRegister();

        //Circuit Rabbit Hole - Layer 2
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(400).EUt(480).inputs(GAMetaItems.ELITE_BOARD.getStackForm(), GAMetaItems.PETRI_DISH.getStackForm(), MetaItems.ELECTRIC_PUMP_LV.getStackForm(), MetaItems.SENSOR_LV.getStackForm()).input(OrePrefix.circuit, Tier.Good).fluidInputs(GAMaterials.SterilizedGrowthMedium.getFluid(250)).outputs(MetaItems.WETWARE_BOARD.getStackForm()).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(30).EUt(480).fluidInputs(GAMaterials.PositiveMatter.getFluid(10), GAMaterials.NeutralMatter.getFluid(10)).fluidOutputs(Materials.UUMatter.getFluid(20)).buildAndRegister();

        ModHandler.removeRecipes(MetaItems.COATED_BOARD.getStackForm(3));
        ModHandler.addShapedRecipe("coated_board_shaped", MetaItems.COATED_BOARD.getStackForm(3), "RRR", "BBB", "RRR", 'R', MetaItems.RUBBER_DROP, 'B', "plateWood");
        ModHandler.addShapelessRecipe("coated_board_shapeless", MetaItems.COATED_BOARD.getStackForm(), MetaItems.RUBBER_DROP, MetaItems.RUBBER_DROP, "plateWood");
        ModHandler.addShapedRecipe("basic_board", GAMetaItems.BASIC_BOARD.getStackForm(), "WWW", "WBW", "WWW", 'W', new UnificationEntry(OrePrefix.wireGtSingle, Materials.Copper), 'B', MetaItems.COATED_BOARD);
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(40).EUt(20).input(OrePrefix.plate, Materials.Wood).input(OrePrefix.foil, Materials.Copper, 4).fluidInputs(Materials.Glue.getFluid(72)).outputs(GAMetaItems.BASIC_BOARD.getStackForm()).buildAndRegister();
        ModHandler.addShapedRecipe("good_board", GAMetaItems.GOOD_PHENOLIC_BOARD.getStackForm(), "WWW", "WBW", "WWW", 'W', new UnificationEntry(OrePrefix.wireGtSingle, Materials.Gold), 'B', MetaItems.PHENOLIC_BOARD);
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(600).EUt(30).inputs(MetaItems.PHENOLIC_BOARD.getStackForm()).input(OrePrefix.foil, Materials.Gold, 4).fluidInputs(Materials.SodiumPersulfate.getFluid(200)).outputs(GAMetaItems.GOOD_PHENOLIC_BOARD.getStackForm()).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(600).EUt(30).inputs(MetaItems.PHENOLIC_BOARD.getStackForm()).input(OrePrefix.foil, Materials.Gold, 4).fluidInputs(GAMaterials.IronChloride.getFluid(100)).outputs(GAMetaItems.GOOD_PHENOLIC_BOARD.getStackForm()).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(800).EUt(30).inputs(MetaItems.PLASTIC_BOARD.getStackForm()).input(OrePrefix.foil, Materials.Copper, 6).fluidInputs(Materials.SodiumPersulfate.getFluid(500)).outputs(GAMetaItems.GOOD_PLASTIC_BOARD.getStackForm()).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(800).EUt(30).inputs(MetaItems.PLASTIC_BOARD.getStackForm()).input(OrePrefix.foil, Materials.Copper, 6).fluidInputs(GAMaterials.IronChloride.getFluid(250)).outputs(GAMetaItems.GOOD_PLASTIC_BOARD.getStackForm()).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(1200).EUt(30).inputs(MetaItems.EPOXY_BOARD.getStackForm()).input(OrePrefix.foil, Materials.Electrum, 8).fluidInputs(Materials.SodiumPersulfate.getFluid(1000)).outputs(GAMetaItems.ADVANCED_BOARD.getStackForm()).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(1200).EUt(30).inputs(MetaItems.EPOXY_BOARD.getStackForm()).input(OrePrefix.foil, Materials.Electrum, 8).fluidInputs(GAMaterials.IronChloride.getFluid(500)).outputs(GAMetaItems.ADVANCED_BOARD.getStackForm()).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(1800).EUt(30).inputs(MetaItems.FIBER_BOARD.getStackForm()).input(OrePrefix.foil, Materials.AnnealedCopper, 12).fluidInputs(Materials.SodiumPersulfate.getFluid(2000)).outputs(GAMetaItems.EXTREME_BOARD.getStackForm()).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(1800).EUt(30).inputs(MetaItems.FIBER_BOARD.getStackForm()).input(OrePrefix.foil, Materials.AnnealedCopper, 12).fluidInputs(GAMaterials.IronChloride.getFluid(1000)).outputs(GAMetaItems.EXTREME_BOARD.getStackForm()).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(2400).EUt(120).inputs(MetaItems.MULTILAYER_FIBER_BOARD.getStackForm()).input(OrePrefix.foil, Materials.Platinum, 16).fluidInputs(Materials.SodiumPersulfate.getFluid(4000)).outputs(GAMetaItems.ELITE_BOARD.getStackForm()).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(2400).EUt(120).inputs(MetaItems.MULTILAYER_FIBER_BOARD.getStackForm()).input(OrePrefix.foil, Materials.Platinum, 16).fluidInputs(GAMaterials.IronChloride.getFluid(2000)).outputs(GAMetaItems.ELITE_BOARD.getStackForm()).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(3000).EUt(480).inputs(MetaItems.WETWARE_BOARD.getStackForm()).input(OrePrefix.foil, Materials.NiobiumTitanium, 32).fluidInputs(Materials.SodiumPersulfate.getFluid(10000)).outputs(GAMetaItems.MASTER_BOARD.getStackForm()).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(3000).EUt(480).inputs(MetaItems.WETWARE_BOARD.getStackForm()).input(OrePrefix.foil, Materials.NiobiumTitanium, 32).fluidInputs(GAMaterials.IronChloride.getFluid(5000)).outputs(GAMetaItems.MASTER_BOARD.getStackForm()).buildAndRegister();

        ModHandler.removeRecipeByName(new ResourceLocation("gregtech:diode"));
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(600).EUt(30).input(OrePrefix.wireFine, Materials.Copper, 4).input(OrePrefix.dustSmall, Materials.GalliumArsenide).fluidInputs(Materials.Glass.getFluid(288)).outputs(MetaItems.DIODE.getStackForm(2)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(600).EUt(30).input(OrePrefix.wireFine, Materials.AnnealedCopper, 4).input(OrePrefix.dustSmall, Materials.GalliumArsenide).fluidInputs(Materials.Glass.getFluid(288)).outputs(MetaItems.DIODE.getStackForm(2)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(600).EUt(30).input(OrePrefix.wireFine, Materials.Copper, 4).input(OrePrefix.dustSmall, Materials.GalliumArsenide).fluidInputs(Materials.Plastic.getFluid(144)).outputs(MetaItems.DIODE.getStackForm(4)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(600).EUt(30).input(OrePrefix.wireFine, Materials.AnnealedCopper, 4).input(OrePrefix.dustSmall, Materials.GalliumArsenide).fluidInputs(Materials.Plastic.getFluid(144)).outputs(MetaItems.DIODE.getStackForm(4)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(600).EUt(30).input(OrePrefix.wireFine, Materials.Copper, 4).inputs(MetaItems.SILICON_WAFER.getStackForm()).fluidInputs(Materials.Glass.getFluid(288)).outputs(MetaItems.DIODE.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(600).EUt(30).input(OrePrefix.wireFine, Materials.AnnealedCopper, 4).inputs(MetaItems.SILICON_WAFER.getStackForm()).fluidInputs(Materials.Glass.getFluid(288)).outputs(MetaItems.DIODE.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(600).EUt(30).input(OrePrefix.wireFine, Materials.Copper, 4).inputs(MetaItems.SILICON_WAFER.getStackForm()).fluidInputs(Materials.Plastic.getFluid(144)).outputs(MetaItems.DIODE.getStackForm(2)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(600).EUt(30).input(OrePrefix.wireFine, Materials.AnnealedCopper, 4).inputs(MetaItems.SILICON_WAFER.getStackForm()).fluidInputs(Materials.Plastic.getFluid(144)).outputs(MetaItems.DIODE.getStackForm(2)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(600).EUt(30).input(OrePrefix.wireFine, Materials.Platinum, 8).input(OrePrefix.dust, Materials.GalliumArsenide).fluidInputs(Materials.Plastic.getFluid(288)).outputs(MetaItems.SMD_DIODE.getStackForm(32)).buildAndRegister();

        ModHandler.removeRecipes(MetaItems.RESISTOR.getStackForm(3));
        for (Material m : new Material[]{Materials.Coal, Materials.Charcoal, Materials.Carbon}) {
            ModHandler.addShapedRecipe("resistor_" + m, MetaItems.RESISTOR.getStackForm(), "RWR", "CMC", " W ", 'M', new UnificationEntry(OrePrefix.dust, m), 'R', MetaItems.RUBBER_DROP, 'W', "wireFineCopper", 'C', "wireGtSingleCopper");
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(320).EUt(16).input(OrePrefix.dust, m).input(OrePrefix.wireFine, Materials.Copper, 4).input(OrePrefix.wireGtSingle, Materials.Copper, 4).fluidInputs(Materials.Glue.getFluid(200)).outputs(MetaItems.RESISTOR.getStackForm(8)).buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(320).EUt(16).input(OrePrefix.dust, m).input(OrePrefix.wireFine, Materials.AnnealedCopper, 4).input(OrePrefix.wireGtSingle, Materials.Copper, 4).fluidInputs(Materials.Glue.getFluid(200)).outputs(MetaItems.RESISTOR.getStackForm(8)).buildAndRegister();
        }

        //Cutting Machine Recipes
        for (MaterialStack stack : sawLubricants) {
            FluidMaterial material = (FluidMaterial) stack.material;
            int multiplier = (int) stack.amount;
            int time = multiplier == 1L ? 4 : 1;
            RecipeMaps.CUTTER_RECIPES.recipeBuilder().duration(960 / time).EUt(420).inputs(MetaItems.CRYSTAL_CENTRAL_PROCESSING_UNIT.getStackForm()).fluidInputs(material.getFluid(2 * multiplier)).outputs(GAMetaItems.RAW_CRYSTAL_CHIP.getStackForm(2)).buildAndRegister();
        }

        //Assline Casing
        ModHandler.addShapedRecipe("assline_casing", GAMetaBlocks.MUTLIBLOCK_CASING.getItemVariant(GAMultiblockCasing.CasingType.TUNGSTENSTEEL_GEARBOX_CASING, 2), "PhP", "AFA", "PwP", 'P', "plateSteel", 'A', MetaItems.ROBOT_ARM_IV.getStackForm(), 'F', OreDictUnifier.get(OrePrefix.frameGt, Materials.TungstenSteel));

        //Circuit Rabbit Hole - Layer 3
        RecipeMaps.ELECTROLYZER_RECIPES.recipeBuilder().duration(900).EUt(30).fluidInputs(Materials.NickelSulfateSolution.getFluid(9000)).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Nickel)).fluidOutputs(Materials.Oxygen.getFluid(1000), Materials.SulfuricAcid.getFluid(8000)).buildAndRegister();
        RecipeMaps.ELECTROLYZER_RECIPES.recipeBuilder().duration(900).EUt(30).fluidInputs(Materials.CopperSulfateSolution.getFluid(9000)).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Copper)).fluidOutputs(Materials.Oxygen.getFluid(1000), Materials.SulfuricAcid.getFluid(8000)).buildAndRegister();
        RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(160).EUt(16).fluidInputs(Materials.Polystyrene.getFluid(36)).notConsumable(MetaItems.SHAPE_MOLD_CYLINDER.getStackForm()).outputs(GAMetaItems.PETRI_DISH.getStackForm()).buildAndRegister();
        RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(160).EUt(16).fluidInputs(Materials.Polytetrafluoroethylene.getFluid(36)).notConsumable(MetaItems.SHAPE_MOLD_CYLINDER.getStackForm()).outputs(GAMetaItems.PETRI_DISH.getStackForm()).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(900).EUt(480).blastFurnaceTemp(5000).inputs(GAMetaItems.RAW_CRYSTAL_CHIP.getStackForm()).input(OrePrefix.plate, Materials.Emerald).fluidInputs(Materials.Helium.getFluid(1000)).outputs(MetaItems.ENGRAVED_CRYSTAL_CHIP.getStackForm()).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(900).EUt(480).blastFurnaceTemp(5000).inputs(GAMetaItems.RAW_CRYSTAL_CHIP.getStackForm()).input(OrePrefix.plate, Materials.Olivine).fluidInputs(Materials.Helium.getFluid(1000)).outputs(MetaItems.ENGRAVED_CRYSTAL_CHIP.getStackForm()).buildAndRegister();
        RecipeMaps.EXTRACTOR_RECIPES.recipeBuilder().duration(300).EUt(1024).inputs(new ItemStack(Items.EGG)).chancedOutput(GAMetaItems.STEMCELLS.getStackForm(), 500, 1000).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(400).EUt(30).input(OrePrefix.dust, Materials.Iron).fluidInputs(Materials.HydrochloricAcid.getFluid(2000)).fluidOutputs(GAMaterials.IronChloride.getFluid(3000), Materials.Hydrogen.getFluid(3000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(400).EUt(1920).inputs(MetaItems.CENTRAL_PROCESSING_UNIT_WAFER.getStackForm(), MetaItems.CARBON_FIBERS.getStackForm(16)).fluidInputs(Materials.Glowstone.getFluid(576)).outputs(MetaItems.NANO_CENTRAL_PROCESSING_UNIT_WAFER.getStackForm()).buildAndRegister();

        //Circuit Rabbit Hole - Layer 4
        RecipeMaps.AUTOCLAVE_RECIPES.recipeBuilder().duration(12000).EUt(320).inputs(OreDictUnifier.get(OrePrefix.gemExquisite, Materials.Olivine)).fluidInputs(Materials.Europium.getFluid(16)).chancedOutput(GAMetaItems.RAW_CRYSTAL_CHIP.getStackForm(), 1000, 1000).buildAndRegister();
        RecipeMaps.AUTOCLAVE_RECIPES.recipeBuilder().duration(12000).EUt(320).inputs(OreDictUnifier.get(OrePrefix.gemExquisite, Materials.Emerald)).fluidInputs(Materials.Europium.getFluid(16)).chancedOutput(GAMetaItems.RAW_CRYSTAL_CHIP.getStackForm(), 1000, 1000).buildAndRegister();
        RecipeMaps.AUTOCLAVE_RECIPES.recipeBuilder().duration(150).EUt(6).input(OrePrefix.dust, Materials.Carbon).fluidInputs(Materials.Lutetium.getFluid(1)).chancedOutput(MetaItems.CARBON_FIBERS.getStackForm(2), 3333, 1000).buildAndRegister();
        RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder().duration(100).EUt(10000).inputs(MetaItems.ENGRAVED_CRYSTAL_CHIP.getStackForm()).notConsumable(OrePrefix.craftingLens, MarkerMaterials.Color.Lime).outputs(MetaItems.CRYSTAL_CENTRAL_PROCESSING_UNIT.getStackForm()).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(160).EUt(16).inputs(new ItemStack(Items.SUGAR, 4), OreDictUnifier.get(OrePrefix.dust, GAMaterials.Meat), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Salt)).fluidInputs(Materials.DistilledWater.getFluid(4000)).fluidOutputs(GAMaterials.RawGrowthMedium.getFluid(4000)).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(9000).EUt(120).blastFurnaceTemp(1784).input(OrePrefix.dust, Materials.Silicon, 32).input(OrePrefix.dustSmall, Materials.GalliumArsenide).outputs(MetaItems.SILICON_BOULE.getStackForm()).buildAndRegister();
    }

    public static void init2() {
        //Assline Recipes
        GARecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder().inputs(
                        OreDictUnifier.get(OrePrefix.stickLong, Materials.NeodymiumMagnetic, 1),
                        OreDictUnifier.get(OrePrefix.stickLong, Materials.HSSG, 2),
                        OreDictUnifier.get(OrePrefix.wireFine, Materials.AnnealedCopper, 64),
                        OreDictUnifier.get(OrePrefix.wireFine, Materials.AnnealedCopper, 64),
                        OreDictUnifier.get(OrePrefix.wireFine, Materials.AnnealedCopper, 64),
                        OreDictUnifier.get(OrePrefix.wireFine, Materials.AnnealedCopper, 64),
                        OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.YttriumBariumCuprate, 2)).fluidInputs(
                        Materials.SolderingAlloy.getFluid(144),
                        Materials.Lubricant.getFluid(250))
                .outputs(MetaItems.ELECTRIC_MOTOR_LUV.getStackForm()).duration(600).EUt(10240)
                .buildAndRegister();

        GARecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder().inputs(
                        OreDictUnifier.get(OrePrefix.stickLong, Materials.NeodymiumMagnetic, 2),
                        OreDictUnifier.get(OrePrefix.stickLong, Materials.HSSE, 4),
                        OreDictUnifier.get(OrePrefix.ring, Materials.HSSE, 4),
                        OreDictUnifier.get(OrePrefix.valueOf("round"), Materials.HSSE, 16),
                        OreDictUnifier.get(OrePrefix.wireFine, Materials.Platinum, 64),
                        OreDictUnifier.get(OrePrefix.wireFine, Materials.Platinum, 64),
                        OreDictUnifier.get(OrePrefix.wireFine, Materials.Platinum, 64),
                        OreDictUnifier.get(OrePrefix.wireFine, Materials.Platinum, 64),
                        OreDictUnifier.get(OrePrefix.cableGtQuadruple, Materials.VanadiumGallium, 2)).fluidInputs(
                        Materials.SolderingAlloy.getFluid(288),
                        Materials.Lubricant.getFluid(750))
                .outputs(MetaItems.ELECTRIC_MOTOR_ZPM.getStackForm()).duration(600).EUt(40960)
                .buildAndRegister();

        GARecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder().inputs(
                        OreDictUnifier.get(OrePrefix.block, Materials.NeodymiumMagnetic, 1),
                        OreDictUnifier.get(OrePrefix.stickLong, GAMaterials.Neutronium, 4),
                        OreDictUnifier.get(OrePrefix.ring, GAMaterials.Neutronium, 4),
                        OreDictUnifier.get(OrePrefix.valueOf("round"), GAMaterials.Neutronium, 16),
                        OreDictUnifier.get(OrePrefix.wireFine, GAMaterials.Ultima, 64),
                        OreDictUnifier.get(OrePrefix.wireFine, GAMaterials.Ultima, 64),
                        OreDictUnifier.get(OrePrefix.wireFine, GAMaterials.Ultima, 64),
                        OreDictUnifier.get(OrePrefix.wireFine, GAMaterials.Ultima, 64),
                        OreDictUnifier.get(OrePrefix.cableGtQuadruple, Materials.NiobiumTitanium, 2)).fluidInputs(
                        Materials.SolderingAlloy.getFluid(1296),
                        Materials.Lubricant.getFluid(2000))
                .outputs(MetaItems.ELECTRIC_MOTOR_UV.getStackForm()).duration(600).EUt(163840)
                .buildAndRegister();

        GARecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder().inputs(
                        MetaItems.ELECTRIC_MOTOR_LUV.getStackForm(),
                        OreDictUnifier.get(OrePrefix.pipeMedium, GAMaterials.Enderium, 2),
                        OreDictUnifier.get(OrePrefix.plate, Materials.HSSG, 2),
                        OreDictUnifier.get(OrePrefix.screw, Materials.HSSG, 8),
                        OreDictUnifier.get(OrePrefix.ring, Materials.SiliconeRubber, 4),
                        OreDictUnifier.get(OrePrefix.rotor, Materials.HSSG, 2),
                        OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.YttriumBariumCuprate, 2)).fluidInputs(
                        Materials.SolderingAlloy.getFluid(144),
                        Materials.Lubricant.getFluid(250))
                .outputs(MetaItems.ELECTRIC_PUMP_LUV.getStackForm()).duration(600).EUt(15360)
                .buildAndRegister();

        GARecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder().inputs(
                        MetaItems.ELECTRIC_MOTOR_ZPM.getStackForm(),
                        OreDictUnifier.get(OrePrefix.pipeMedium, Materials.Naquadah, 2),
                        OreDictUnifier.get(OrePrefix.plate, Materials.HSSE, 2),
                        OreDictUnifier.get(OrePrefix.screw, Materials.HSSE, 8),
                        OreDictUnifier.get(OrePrefix.ring, Materials.SiliconeRubber, 16),
                        OreDictUnifier.get(OrePrefix.rotor, Materials.HSSE, 2),
                        OreDictUnifier.get(OrePrefix.cableGtQuadruple, Materials.VanadiumGallium, 2)).fluidInputs(
                        Materials.SolderingAlloy.getFluid(288),
                        Materials.Lubricant.getFluid(750))
                .outputs(MetaItems.ELECTRIC_PUMP_ZPM.getStackForm()).duration(600).EUt(61440)
                .buildAndRegister();

        GARecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder().inputs(
                        MetaItems.ELECTRIC_MOTOR_UV.getStackForm(),
                        OreDictUnifier.get(OrePrefix.pipeMedium, GAMaterials.Neutronium, 2),
                        OreDictUnifier.get(OrePrefix.plate, GAMaterials.Neutronium, 2),
                        OreDictUnifier.get(OrePrefix.screw, GAMaterials.Neutronium, 8),
                        OreDictUnifier.get(OrePrefix.ring, Materials.SiliconeRubber, 16),
                        OreDictUnifier.get(OrePrefix.rotor, GAMaterials.Neutronium, 2),
                        OreDictUnifier.get(OrePrefix.cableGtQuadruple, Materials.NiobiumTitanium, 2)).fluidInputs(
                        Materials.SolderingAlloy.getFluid(1296),
                        Materials.Lubricant.getFluid(2000))
                .outputs(MetaItems.ELECTRIC_PUMP_UV.getStackForm()).duration(600).EUt(245760)
                .buildAndRegister();

        GARecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder().inputs(
                        MetaItems.ELECTRIC_MOTOR_LUV.getStackForm(2),
                        OreDictUnifier.get(OrePrefix.plate, Materials.HSSG, 2),
                        OreDictUnifier.get(OrePrefix.ring, Materials.HSSG, 4),
                        OreDictUnifier.get(OrePrefix.valueOf("round"), Materials.HSSG, 32),
                        OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.YttriumBariumCuprate, 2))
                .notConsumable(new IntCircuitIngredient(1)).fluidInputs(
                        Materials.StyreneButadieneRubber.getFluid(1440),
                        Materials.Lubricant.getFluid(250))
                .outputs(MetaItems.CONVEYOR_MODULE_LUV.getStackForm()).duration(600).EUt(15360)
                .buildAndRegister();

        GARecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder().inputs(
                        MetaItems.ELECTRIC_MOTOR_ZPM.getStackForm(2),
                        OreDictUnifier.get(OrePrefix.plate, Materials.HSSE, 2),
                        OreDictUnifier.get(OrePrefix.ring, Materials.HSSE, 4),
                        OreDictUnifier.get(OrePrefix.valueOf("round"), Materials.HSSE, 32),
                        OreDictUnifier.get(OrePrefix.cableGtQuadruple, Materials.VanadiumGallium, 2))
                .notConsumable(new IntCircuitIngredient(1)).fluidInputs(
                        Materials.StyreneButadieneRubber.getFluid(2880),
                        Materials.Lubricant.getFluid(750))
                .outputs(MetaItems.CONVEYOR_MODULE_ZPM.getStackForm()).duration(600).EUt(61440)
                .buildAndRegister();

        GARecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder().inputs(
                        MetaItems.ELECTRIC_MOTOR_UV.getStackForm(2),
                        OreDictUnifier.get(OrePrefix.plate, GAMaterials.Neutronium, 2),
                        OreDictUnifier.get(OrePrefix.ring, GAMaterials.Neutronium, 4),
                        OreDictUnifier.get(OrePrefix.valueOf("round"), GAMaterials.Neutronium, 32),
                        OreDictUnifier.get(OrePrefix.cableGtQuadruple, Materials.NiobiumTitanium, 2))
                .notConsumable(new IntCircuitIngredient(1)).fluidInputs(
                        Materials.StyreneButadieneRubber.getFluid(2880),
                        Materials.Lubricant.getFluid(2000))
                .outputs(MetaItems.CONVEYOR_MODULE_UV.getStackForm()).duration(600).EUt(245760)
                .buildAndRegister();

        GARecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder().inputs(
                        MetaItems.ELECTRIC_MOTOR_LUV.getStackForm(),
                        OreDictUnifier.get(OrePrefix.plate, Materials.HSSG, 6),
                        OreDictUnifier.get(OrePrefix.ring, Materials.HSSG, 4),
                        OreDictUnifier.get(OrePrefix.valueOf("round"), Materials.HSSG, 32),
                        OreDictUnifier.get(OrePrefix.stick, Materials.HSSG, 4),
                        OreDictUnifier.get(OrePrefix.gear, Materials.HSSG, 1),
                        OreDictUnifier.get(OrePrefix.gearSmall, Materials.HSSG, 2),
                        OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.YttriumBariumCuprate, 4))
                .notConsumable(new IntCircuitIngredient(2)).fluidInputs(
                        Materials.SolderingAlloy.getFluid(144),
                        Materials.Lubricant.getFluid(250))
                .outputs(MetaItems.ELECTRIC_PISTON_LUV.getStackForm()).duration(600).EUt(15360)
                .buildAndRegister();


        GARecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder().inputs(
                        MetaItems.ELECTRIC_MOTOR_ZPM.getStackForm(),
                        OreDictUnifier.get(OrePrefix.plate, Materials.HSSE, 6),
                        OreDictUnifier.get(OrePrefix.ring, Materials.HSSE, 4),
                        OreDictUnifier.get(OrePrefix.valueOf("round"), Materials.HSSE, 32),
                        OreDictUnifier.get(OrePrefix.stick, Materials.HSSE, 4),
                        OreDictUnifier.get(OrePrefix.gear, Materials.HSSE, 1),
                        OreDictUnifier.get(OrePrefix.gearSmall, Materials.HSSE, 2),
                        OreDictUnifier.get(OrePrefix.cableGtQuadruple, Materials.VanadiumGallium, 4))
                .notConsumable(new IntCircuitIngredient(2)).fluidInputs(
                        Materials.SolderingAlloy.getFluid(288),
                        Materials.Lubricant.getFluid(750))
                .outputs(MetaItems.ELECTRIC_PISTON_ZPM.getStackForm()).duration(600).EUt(61440)
                .buildAndRegister();

        GARecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder().inputs(
                        MetaItems.ELECTRIC_MOTOR_UV.getStackForm(),
                        OreDictUnifier.get(OrePrefix.plate, GAMaterials.Neutronium, 6),
                        OreDictUnifier.get(OrePrefix.ring, GAMaterials.Neutronium, 4),
                        OreDictUnifier.get(OrePrefix.valueOf("round"), GAMaterials.Neutronium, 32),
                        OreDictUnifier.get(OrePrefix.stick, GAMaterials.Neutronium, 4),
                        OreDictUnifier.get(OrePrefix.gear, GAMaterials.Neutronium, 1),
                        OreDictUnifier.get(OrePrefix.gearSmall, GAMaterials.Neutronium, 2),
                        OreDictUnifier.get(OrePrefix.cableGtQuadruple, Materials.NiobiumTitanium, 4))
                .notConsumable(new IntCircuitIngredient(2)).fluidInputs(
                        Materials.SolderingAlloy.getFluid(1296),
                        Materials.Lubricant.getFluid(2000))
                .outputs(MetaItems.ELECTRIC_PISTON_UV.getStackForm()).duration(600).EUt(245760)
                .buildAndRegister();

        GARecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder().inputs(
                        OreDictUnifier.get(OrePrefix.stickLong, Materials.HSSG, 4),
                        OreDictUnifier.get(OrePrefix.gear, Materials.HSSG, 1),
                        OreDictUnifier.get(OrePrefix.gearSmall, Materials.HSSG, 3),
                        MetaItems.ELECTRIC_MOTOR_LUV.getStackForm(2),
                        MetaItems.ELECTRIC_PISTON_LUV.getStackForm(),
                        OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.YttriumBariumCuprate, 6))
                .input(OrePrefix.circuit, Tier.Master, 2)
                .input(OrePrefix.circuit, Tier.Elite, 2)
                .input(OrePrefix.circuit, Tier.Extreme, 6).fluidInputs(
                        Materials.SolderingAlloy.getFluid(576),
                        Materials.Lubricant.getFluid(250))
                .outputs(MetaItems.ROBOT_ARM_LUV.getStackForm()).duration(600).EUt(20480)
                .buildAndRegister();

        GARecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder().inputs(
                        OreDictUnifier.get(OrePrefix.stickLong, Materials.HSSE, 4),
                        OreDictUnifier.get(OrePrefix.gear, Materials.HSSE, 1),
                        OreDictUnifier.get(OrePrefix.gearSmall, Materials.HSSE, 3),
                        MetaItems.ELECTRIC_MOTOR_ZPM.getStackForm(2),
                        MetaItems.ELECTRIC_PISTON_ZPM.getStackForm(),
                        OreDictUnifier.get(OrePrefix.cableGtQuadruple, Materials.VanadiumGallium, 6))
                .input(OrePrefix.circuit, Tier.Master, 4)
                .input(OrePrefix.circuit, Tier.Elite, 4)
                .input(OrePrefix.circuit, Tier.Extreme, 12).fluidInputs(
                        Materials.SolderingAlloy.getFluid(1152),
                        Materials.Lubricant.getFluid(750))
                .outputs(MetaItems.ROBOT_ARM_ZPM.getStackForm()).duration(600).EUt(81920)
                .buildAndRegister();

        GARecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder().inputs(
                        OreDictUnifier.get(OrePrefix.stickLong, GAMaterials.Neutronium, 4),
                        OreDictUnifier.get(OrePrefix.gear, GAMaterials.Neutronium, 1),
                        OreDictUnifier.get(OrePrefix.gearSmall, GAMaterials.Neutronium, 3),
                        MetaItems.ELECTRIC_MOTOR_UV.getStackForm(2),
                        MetaItems.ELECTRIC_PISTON_UV.getStackForm(),
                        OreDictUnifier.get(OrePrefix.cableGtQuadruple, Materials.NiobiumTitanium, 6))
                .input(OrePrefix.circuit, Tier.Master, 8)
                .input(OrePrefix.circuit, Tier.Elite, 8)
                .input(OrePrefix.circuit, Tier.Extreme, 24).fluidInputs(
                        Materials.SolderingAlloy.getFluid(2304),
                        Materials.Lubricant.getFluid(2000))
                .outputs(MetaItems.ROBOT_ARM_UV.getStackForm()).duration(600).EUt(327680)
                .buildAndRegister();

        GARecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder().inputs(
                        OreDictUnifier.get(OrePrefix.frameGt, Materials.HSSG, 1),
                        MetaItems.EMITTER_IV.getStackForm(),
                        MetaItems.EMITTER_EV.getStackForm(2),
                        MetaItems.EMITTER_HV.getStackForm(4),
                        OreDictUnifier.get(OrePrefix.foil, Materials.Electrum, 64),
                        OreDictUnifier.get(OrePrefix.foil, Materials.Electrum, 64),
                        OreDictUnifier.get(OrePrefix.foil, Materials.Electrum, 64),
                        OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.YttriumBariumCuprate, 7))
                .input(OrePrefix.circuit, Tier.Extreme, 7).fluidInputs(
                        Materials.SolderingAlloy.getFluid(576))
                .outputs(MetaItems.EMITTER_LUV.getStackForm()).duration(600).EUt(15360)
                .buildAndRegister();

        GARecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder().inputs(
                        OreDictUnifier.get(OrePrefix.frameGt, Materials.HSSE, 1),
                        MetaItems.EMITTER_LUV.getStackForm(),
                        MetaItems.EMITTER_IV.getStackForm(2),
                        MetaItems.EMITTER_EV.getStackForm(4),
                        OreDictUnifier.get(OrePrefix.foil, Materials.Platinum, 64),
                        OreDictUnifier.get(OrePrefix.foil, Materials.Platinum, 64),
                        OreDictUnifier.get(OrePrefix.foil, Materials.Platinum, 64),
                        OreDictUnifier.get(OrePrefix.cableGtQuadruple, Materials.VanadiumGallium, 7))
                .input(OrePrefix.circuit, Tier.Elite, 7).fluidInputs(
                        Materials.SolderingAlloy.getFluid(576))
                .outputs(MetaItems.EMITTER_ZPM.getStackForm()).duration(600).EUt(61440)
                .buildAndRegister();

        GARecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder().inputs(
                        OreDictUnifier.get(OrePrefix.frameGt, GAMaterials.Neutronium, 1),
                        MetaItems.EMITTER_ZPM.getStackForm(),
                        MetaItems.EMITTER_LUV.getStackForm(2),
                        MetaItems.EMITTER_IV.getStackForm(4),
                        OreDictUnifier.get(OrePrefix.foil, Materials.Osmiridium, 64),
                        OreDictUnifier.get(OrePrefix.foil, Materials.Osmiridium, 64),
                        OreDictUnifier.get(OrePrefix.foil, Materials.Osmiridium, 64),
                        OreDictUnifier.get(OrePrefix.cableGtQuadruple, Materials.NiobiumTitanium, 7))
                .input(OrePrefix.circuit, Tier.Master, 7).fluidInputs(
                        Materials.SolderingAlloy.getFluid(576))
                .outputs(MetaItems.EMITTER_UV.getStackForm()).duration(600).EUt(245760)
                .buildAndRegister();

        GARecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder().inputs(
                        OreDictUnifier.get(OrePrefix.frameGt, Materials.HSSG, 1),
                        MetaItems.SENSOR_IV.getStackForm(),
                        MetaItems.SENSOR_EV.getStackForm(2),
                        MetaItems.SENSOR_HV.getStackForm(4),
                        OreDictUnifier.get(OrePrefix.foil, Materials.Electrum, 64),
                        OreDictUnifier.get(OrePrefix.foil, Materials.Electrum, 64),
                        OreDictUnifier.get(OrePrefix.foil, Materials.Electrum, 64),
                        OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.YttriumBariumCuprate, 7))
                .input(OrePrefix.circuit, Tier.Extreme, 7).fluidInputs(
                        Materials.SolderingAlloy.getFluid(576))
                .outputs(MetaItems.SENSOR_LUV.getStackForm()).duration(600).EUt(15360)
                .buildAndRegister();

        GARecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder().inputs(
                        OreDictUnifier.get(OrePrefix.frameGt, Materials.HSSE, 1),
                        MetaItems.SENSOR_LUV.getStackForm(),
                        MetaItems.SENSOR_IV.getStackForm(2),
                        MetaItems.SENSOR_EV.getStackForm(4),
                        OreDictUnifier.get(OrePrefix.foil, Materials.Platinum, 64),
                        OreDictUnifier.get(OrePrefix.foil, Materials.Platinum, 64),
                        OreDictUnifier.get(OrePrefix.foil, Materials.Platinum, 64),
                        OreDictUnifier.get(OrePrefix.cableGtQuadruple, Materials.VanadiumGallium, 7))
                .input(OrePrefix.circuit, Tier.Elite, 7).fluidInputs(
                        Materials.SolderingAlloy.getFluid(576))
                .outputs(MetaItems.SENSOR_ZPM.getStackForm()).duration(600).EUt(61440)
                .buildAndRegister();

        GARecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder().inputs(
                        OreDictUnifier.get(OrePrefix.frameGt, GAMaterials.Neutronium, 1),
                        MetaItems.SENSOR_ZPM.getStackForm(),
                        MetaItems.SENSOR_LUV.getStackForm(2),
                        MetaItems.SENSOR_IV.getStackForm(4),
                        OreDictUnifier.get(OrePrefix.foil, Materials.Osmiridium, 64),
                        OreDictUnifier.get(OrePrefix.foil, Materials.Osmiridium, 64),
                        OreDictUnifier.get(OrePrefix.foil, Materials.Osmiridium, 64),
                        OreDictUnifier.get(OrePrefix.cableGtQuadruple, Materials.NiobiumTitanium, 7))
                .input(OrePrefix.circuit, Tier.Master, 7).fluidInputs(
                        Materials.SolderingAlloy.getFluid(576))
                .outputs(MetaItems.SENSOR_UV.getStackForm()).duration(600).EUt(245760)
                .buildAndRegister();

        GARecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder().inputs(
                        OreDictUnifier.get(OrePrefix.frameGt, Materials.HSSG, 1),
                        OreDictUnifier.get(OrePrefix.plate, Materials.HSSG, 6),
                        MetaItems.QUANTUM_STAR.getStackForm(),
                        MetaItems.EMITTER_LUV.getStackForm(4),
                        GAMetaItems.NEURO_PROCESSOR.getStackForm(8),
                        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
                        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
                        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
                        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
                        OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.YttriumBariumCuprate, 8))
                .input(OrePrefix.circuit, Tier.Master, 8)
                .input(OrePrefix.circuit, Tier.Elite, 8).fluidInputs(
                        Materials.SolderingAlloy.getFluid(576))
                .outputs(MetaItems.FIELD_GENERATOR_LUV.getStackForm()).duration(600).EUt(30720)
                .buildAndRegister();

        GARecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder().inputs(
                        OreDictUnifier.get(OrePrefix.plate, Materials.HSSE, 6),
                        MetaItems.QUANTUM_STAR.getStackForm(4),
                        MetaItems.EMITTER_ZPM.getStackForm(4),
                        MetaItems.CRYSTAL_PROCESSOR_IV.getStackForm(16),
                        GAMetaItems.NEURO_PROCESSOR.getStackForm(16),
                        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
                        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
                        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
                        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
                        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
                        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
                        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
                        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
                        OreDictUnifier.get(OrePrefix.cableGtQuadruple, Materials.VanadiumGallium, 8))
                .input(OrePrefix.circuit, Tier.Elite, 16).fluidInputs(
                        Materials.SolderingAlloy.getFluid(1152))
                .outputs(MetaItems.FIELD_GENERATOR_ZPM.getStackForm()).duration(600).EUt(122880)
                .buildAndRegister();

        GARecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder().inputs(
                        OreDictUnifier.get(OrePrefix.frameGt, GAMaterials.Neutronium, 1),
                        OreDictUnifier.get(OrePrefix.plate, GAMaterials.Neutronium, 6),
                        MetaItems.GRAVI_STAR.getStackForm(),
                        MetaItems.EMITTER_UV.getStackForm(4),
                        GAMetaItems.NEURO_PROCESSOR.getStackForm(64),
                        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
                        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
                        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
                        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
                        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
                        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
                        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
                        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
                        OreDictUnifier.get(OrePrefix.cableGtQuadruple, Materials.NiobiumTitanium, 8)).fluidInputs(
                        Materials.SolderingAlloy.getFluid(2304))
                .outputs(MetaItems.FIELD_GENERATOR_UV.getStackForm()).duration(600).EUt(491520)
                .buildAndRegister();

        GARecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder().inputs(
                        GAMetaItems.MASTER_BOARD.getStackForm(),
                        GAMetaItems.STEMCELLS.getStackForm(8),
                        MetaItems.GLASS_TUBE.getStackForm(8),
                        OreDictUnifier.get(OrePrefix.foil, Materials.SiliconeRubber, 64))
                .input(OrePrefix.plate, Materials.Gold, 8)
                .input(OrePrefix.plate, Materials.StainlessSteel, 4).fluidInputs(
                        GAMaterials.SterilizedGrowthMedium.getFluid(250),
                        Materials.UUMatter.getFluid(100), Materials.Water.getFluid(250), Materials.Lava.getFluid(1000))
                .outputs(GAMetaItems.NEURO_PROCESSOR.getStackForm()).duration(200).EUt(80000)
                .buildAndRegister();

        GARecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder().inputs(
                        OreDictUnifier.get(OrePrefix.frameGt, Materials.Tritanium, 4),
                        MetaItems.WETWARE_SUPER_COMPUTER_UV.getStackForm(4),
                        MetaItems.SMALL_COIL.getStackForm(4),
                        MetaItems.SMD_CAPACITOR.getStackForm(24),
                        MetaItems.SMD_RESISTOR.getStackForm(64),
                        MetaItems.SMD_TRANSISTOR.getStackForm(32),
                        MetaItems.SMD_DIODE.getStackForm(16),
                        MetaItems.RANDOM_ACCESS_MEMORY.getStackForm(16),
                        OreDictUnifier.get(OrePrefix.wireGtSingle, GAMaterials.Ultima, 32),
                        OreDictUnifier.get(OrePrefix.foil, Materials.SiliconeRubber, 64)).fluidInputs(
                        Materials.SolderingAlloy.getFluid(2880), Materials.Water.getFluid(10000))
                .outputs(MetaItems.WETWARE_MAINFRAME_MAX.getStackForm()).duration(2000).EUt(300000)
                .buildAndRegister();

        ItemStack last_bat = (GAConfig.GT5U.replaceUVWithMAXBat ? GAMetaItems.MAX_BATTERY : MetaItems.ZPM2).getStackForm();

        if (GAConfig.GT5U.enableZPMAndUVBats) {
            GARecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder().inputs(
                            OreDictUnifier.get(OrePrefix.plate, Materials.Europium, 16),
                            MetaItems.WETWARE_SUPER_COMPUTER_UV.getStackForm(),
                            MetaItems.WETWARE_SUPER_COMPUTER_UV.getStackForm(),
                            MetaItems.WETWARE_SUPER_COMPUTER_UV.getStackForm(),
                            MetaItems.WETWARE_SUPER_COMPUTER_UV.getStackForm(),
                            MetaItems.ENERGY_LAPOTRONIC_ORB2.getStackForm(8),
                            MetaItems.FIELD_GENERATOR_LUV.getStackForm(2),
                            MetaItems.NANO_CENTRAL_PROCESSING_UNIT.getStackForm(64),
                            MetaItems.NANO_CENTRAL_PROCESSING_UNIT.getStackForm(64),
                            MetaItems.SMD_DIODE.getStackForm(8),
                            OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.Naquadah, 32)).fluidInputs(
                            Materials.SolderingAlloy.getFluid(2880),
                            Materials.Water.getFluid(8000))
                    .outputs(GAMetaItems.ENERGY_MODULE.getStackForm()).duration(2000).EUt(100000)
                    .buildAndRegister();

            GARecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder().inputs(
                            OreDictUnifier.get(OrePrefix.plate, Materials.Americium, 16),
                            MetaItems.WETWARE_SUPER_COMPUTER_UV.getStackForm(),
                            MetaItems.WETWARE_SUPER_COMPUTER_UV.getStackForm(),
                            MetaItems.WETWARE_SUPER_COMPUTER_UV.getStackForm(),
                            MetaItems.WETWARE_SUPER_COMPUTER_UV.getStackForm(),
                            GAMetaItems.ENERGY_MODULE.getStackForm(8),
                            MetaItems.FIELD_GENERATOR_ZPM.getStackForm(2),
                            MetaItems.HIGH_POWER_INTEGRATED_CIRCUIT.getStackForm(64),
                            MetaItems.HIGH_POWER_INTEGRATED_CIRCUIT.getStackForm(64),
                            MetaItems.SMD_DIODE.getStackForm(16),
                            OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.NaquadahAlloy, 32)).fluidInputs(
                            Materials.SolderingAlloy.getFluid(2880),
                            Materials.Water.getFluid(16000))
                    .outputs(GAMetaItems.ENERGY_CLUSTER.getStackForm()).duration(2000).EUt(200000)
                    .buildAndRegister();

            GARecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder().inputs(
                            OreDictUnifier.get(OrePrefix.plate, GAMaterials.Neutronium, 16),
                            MetaItems.WETWARE_MAINFRAME_MAX.getStackForm(),
                            MetaItems.WETWARE_MAINFRAME_MAX.getStackForm(),
                            MetaItems.WETWARE_MAINFRAME_MAX.getStackForm(),
                            MetaItems.WETWARE_MAINFRAME_MAX.getStackForm(),
                            GAMetaItems.ENERGY_CLUSTER.getStackForm(8),
                            MetaItems.FIELD_GENERATOR_UV.getStackForm(2),
                            MetaItems.HIGH_POWER_INTEGRATED_CIRCUIT.getStackForm(64),
                            MetaItems.HIGH_POWER_INTEGRATED_CIRCUIT.getStackForm(64),
                            MetaItems.SMD_DIODE.getStackForm(16),
                            OreDictUnifier.get(OrePrefix.wireGtSingle, Tier.Superconductor, 32)).fluidInputs(
                            Materials.SolderingAlloy.getFluid(2880),
                            Materials.Water.getFluid(16000),
                            Materials.Naquadria.getFluid(1152))
                    .outputs(last_bat).duration(2000).EUt(300000)
                    .buildAndRegister();
        } else
            GARecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder().inputs(
                            OreDictUnifier.get(OrePrefix.plate, GAMaterials.Neutronium, 16),
                            MetaItems.WETWARE_MAINFRAME_MAX.getStackForm(),
                            MetaItems.WETWARE_MAINFRAME_MAX.getStackForm(),
                            MetaItems.WETWARE_MAINFRAME_MAX.getStackForm(),
                            MetaItems.WETWARE_MAINFRAME_MAX.getStackForm(),
                            MetaItems.ENERGY_LAPOTRONIC_ORB2.getStackForm(8),
                            MetaItems.FIELD_GENERATOR_UV.getStackForm(2),
                            MetaItems.HIGH_POWER_INTEGRATED_CIRCUIT.getStackForm(64),
                            MetaItems.HIGH_POWER_INTEGRATED_CIRCUIT.getStackForm(64),
                            MetaItems.SMD_DIODE.getStackForm(16),
                            OreDictUnifier.get(OrePrefix.wireGtSingle, Tier.Superconductor, 32)).fluidInputs(
                            Materials.SolderingAlloy.getFluid(2880), Materials.Water.getFluid(16000))
                    .outputs(last_bat).duration(2000).EUt(300000)
                    .buildAndRegister();

        GARecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder().inputs(
                        MetaBlocks.WIRE_COIL.getItemVariant(BlockWireCoil.CoilType.FUSION_COIL),
                        OreDictUnifier.get(OrePrefix.plate, Materials.Plutonium241),
                        OreDictUnifier.get(OrePrefix.plate, Materials.NetherStar),
                        MetaItems.FIELD_GENERATOR_IV.getStackForm(2),
                        MetaItems.HIGH_POWER_INTEGRATED_CIRCUIT.getStackForm(32),
                        OreDictUnifier.get(OrePrefix.wireGtSingle, GAMaterials.ZPMSuperconductor, 32))
                .input(OrePrefix.circuit, Tier.Ultimate)
                .input(OrePrefix.circuit, Tier.Ultimate)
                .input(OrePrefix.circuit, Tier.Ultimate)
                .input(OrePrefix.circuit, Tier.Ultimate).fluidInputs(
                        Materials.SolderingAlloy.getFluid(2880))
                .outputs(GATileEntities.FUSION_REACTOR[0].getStackForm()).duration(1000).EUt(30000)
                .buildAndRegister();

        GARecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder().inputs(
                        MetaBlocks.WIRE_COIL.getItemVariant(BlockWireCoil.CoilType.FUSION_COIL),
                        OreDictUnifier.get(OrePrefix.plate, Materials.Europium, 4),
                        MetaItems.FIELD_GENERATOR_LUV.getStackForm(2),
                        MetaItems.HIGH_POWER_INTEGRATED_CIRCUIT.getStackForm(48),
                        OreDictUnifier.get(OrePrefix.wireGtQuadruple, GAMaterials.ZPMSuperconductor, 32))
                .input(OrePrefix.circuit, Tier.Superconductor)
                .input(OrePrefix.circuit, Tier.Superconductor)
                .input(OrePrefix.circuit, Tier.Superconductor)
                .input(OrePrefix.circuit, Tier.Superconductor).fluidInputs(
                        Materials.SolderingAlloy.getFluid(2880))
                .outputs(GATileEntities.FUSION_REACTOR[1].getStackForm()).duration(1000).EUt(60000)
                .buildAndRegister();

        GARecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder().inputs(
                        MetaBlocks.WIRE_COIL.getItemVariant(BlockWireCoil.CoilType.FUSION_COIL),
                        MetaItems.WETWARE_MAINFRAME_MAX.getStackForm(),
                        MetaItems.WETWARE_MAINFRAME_MAX.getStackForm(),
                        MetaItems.WETWARE_MAINFRAME_MAX.getStackForm(),
                        MetaItems.WETWARE_MAINFRAME_MAX.getStackForm(),
                        OreDictUnifier.get(OrePrefix.plate, Materials.Americium, 4),
                        MetaItems.FIELD_GENERATOR_ZPM.getStackForm(2),
                        MetaItems.HIGH_POWER_INTEGRATED_CIRCUIT.getStackForm(64),
                        OreDictUnifier.get(OrePrefix.wireGtOctal, GAMaterials.ZPMSuperconductor, 64)).fluidInputs(
                        Materials.SolderingAlloy.getFluid(2880))
                .outputs(GATileEntities.FUSION_REACTOR[2].getStackForm()).duration(1000).EUt(90000)
                .buildAndRegister();

        if (GAConfig.GT5U.enhancedPistonRecipes) {
            List<IngotMaterial> enhancedMaterialList = Arrays.asList(Materials.HSSG, Materials.HSSE, GAMaterials.Neutronium);
            for (int i = 0; i < enhancedMaterialList.size(); i++) {
                int tier = 6 + i;
                int pistonCount = (int) Math.pow(2, tier);
                int EUt = (int) Math.ceil(1.875 * (int) Math.pow(4, tier + 1));
                SimpleRecipeBuilder recipe = GARecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder();
                if (tier == 8) {
                    recipe.input(OrePrefix.stoneCobble.name(), 512);
                    recipe.input(OrePrefix.stoneCobble.name(), 512);
                } else recipe.input(OrePrefix.stoneCobble.name(), 4 * pistonCount);
                recipe.input(OrePrefix.plank, Materials.Wood, 3 * pistonCount)
                        .input(OrePrefix.gearSmall, enhancedMaterialList.get(i), (int) Math.pow(2, i))
                        .input(OrePrefix.plate, enhancedMaterialList.get(i), (int) Math.pow(2, i))
                        .input(OrePrefix.gear, enhancedMaterialList.get(i), i + 1)
                        .fluidInputs(Collections.singletonList(Materials.Redstone.getFluid(144 * pistonCount)))
                        .outputs(new ItemStack(Blocks.PISTON, pistonCount))
                        .EUt(EUt)
                        .duration(20 * pistonCount)
                        .buildAndRegister();
            }
        }

        //Star Recipes
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(12000).EUt(8).input(OrePrefix.ingot, Materials.Plutonium, 8).input(OrePrefix.dustTiny, Materials.Uranium).fluidInputs(Materials.Air.getFluid(1000)).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Plutonium, 8)).fluidOutputs(Materials.Radon.getFluid(100)).buildAndRegister();
        RecipeMaps.AUTOCLAVE_RECIPES.recipeBuilder().duration(480).EUt(7680).inputs(new ItemStack(Items.NETHER_STAR)).fluidInputs(GAMaterials.Neutronium.getFluid(288)).outputs(MetaItems.GRAVI_STAR.getStackForm()).buildAndRegister();

        //Fusion Recipes
        RecipeMaps.FUSION_RECIPES.recipeBuilder().fluidInputs(Materials.Deuterium.getFluid(125), Materials.Tritium.getFluid(125)).fluidOutputs(Materials.Helium.getPlasma(125)).duration(16).EUt(4096).EUToStart(400000).buildAndRegister();
        RecipeMaps.FUSION_RECIPES.recipeBuilder().fluidInputs(Materials.Deuterium.getFluid(125), Materials.Helium3.getFluid(125)).fluidOutputs(Materials.Helium.getPlasma(125)).duration(16).EUt(2048).EUToStart(600000).buildAndRegister();
        RecipeMaps.FUSION_RECIPES.recipeBuilder().fluidInputs(Materials.Carbon.getFluid(125), Materials.Helium3.getFluid(125)).fluidOutputs(Materials.Oxygen.getPlasma(125)).duration(32).EUt(4096).EUToStart(800000).buildAndRegister();
        RecipeMaps.FUSION_RECIPES.recipeBuilder().fluidInputs(Materials.Beryllium.getFluid(16), Materials.Deuterium.getFluid(375)).fluidOutputs(Materials.Nitrogen.getPlasma(175)).duration(16).EUt(16384).EUToStart(1800000).buildAndRegister();
        RecipeMaps.FUSION_RECIPES.recipeBuilder().fluidInputs(Materials.Silicon.getFluid(16), Materials.Magnesium.getFluid(16)).fluidOutputs(Materials.Iron.getPlasma(125)).duration(32).EUt(8192).EUToStart(3600000).buildAndRegister();
        RecipeMaps.FUSION_RECIPES.recipeBuilder().fluidInputs(Materials.Potassium.getFluid(16), Materials.Fluorine.getFluid(125)).fluidOutputs(Materials.Nickel.getPlasma(125)).duration(16).EUt(32768).EUToStart(4800000).buildAndRegister();
        RecipeMaps.FUSION_RECIPES.recipeBuilder().fluidInputs(Materials.Beryllium.getFluid(16), Materials.Tungsten.getFluid(16)).fluidOutputs(Materials.Platinum.getFluid(16)).duration(32).EUt(32768).EUToStart(1500000).buildAndRegister();
        RecipeMaps.FUSION_RECIPES.recipeBuilder().fluidInputs(Materials.Neodymium.getFluid(16), Materials.Hydrogen.getFluid(48)).fluidOutputs(Materials.Europium.getFluid(16)).duration(64).EUt(24576).EUToStart(1500000).buildAndRegister();
        RecipeMaps.FUSION_RECIPES.recipeBuilder().fluidInputs(Materials.Lutetium.getFluid(16), Materials.Chrome.getFluid(16)).fluidOutputs(Materials.Americium.getFluid(16)).duration(96).EUt(49152).EUToStart(2000000).buildAndRegister();
        RecipeMaps.FUSION_RECIPES.recipeBuilder().fluidInputs(Materials.Plutonium.getFluid(16), Materials.Thorium.getFluid(16)).fluidOutputs(Materials.Naquadah.getFluid(16)).duration(64).EUt(32768).EUToStart(3000000).buildAndRegister();
        RecipeMaps.FUSION_RECIPES.recipeBuilder().fluidInputs(Materials.Americium.getFluid(16), Materials.Naquadria.getFluid(16)).fluidOutputs(GAMaterials.Neutronium.getFluid(2)).duration(200).EUt(98304).EUToStart(6000000).buildAndRegister();
        RecipeMaps.FUSION_RECIPES.recipeBuilder().fluidInputs(Materials.Tungsten.getFluid(16), Materials.Helium.getFluid(16)).fluidOutputs(Materials.Osmium.getFluid(16)).duration(64).EUt(24578).EUToStart(1500000).buildAndRegister();
        RecipeMaps.FUSION_RECIPES.recipeBuilder().fluidInputs(Materials.Manganese.getFluid(16), Materials.Hydrogen.getFluid(16)).fluidOutputs(Materials.Iron.getFluid(16)).duration(64).EUt(8192).EUToStart(1200000).buildAndRegister();
        RecipeMaps.FUSION_RECIPES.recipeBuilder().fluidInputs(Materials.Mercury.getFluid(16), Materials.Magnesium.getFluid(16)).fluidOutputs(Materials.Uranium.getFluid(16)).duration(64).EUt(49152).EUToStart(2400000).buildAndRegister();
        RecipeMaps.FUSION_RECIPES.recipeBuilder().fluidInputs(Materials.Gold.getFluid(16), Materials.Aluminium.getFluid(16)).fluidOutputs(Materials.Uranium.getFluid(16)).duration(64).EUt(49152).EUToStart(2400000).buildAndRegister();
        RecipeMaps.FUSION_RECIPES.recipeBuilder().fluidInputs(Materials.Uranium.getFluid(16), Materials.Helium.getFluid(16)).fluidOutputs(Materials.Plutonium.getFluid(16)).duration(128).EUt(49152).EUToStart(4800000).buildAndRegister();
        RecipeMaps.FUSION_RECIPES.recipeBuilder().fluidInputs(Materials.Vanadium.getFluid(16), Materials.Hydrogen.getFluid(125)).fluidOutputs(Materials.Chrome.getFluid(16)).duration(64).EUt(24576).EUToStart(1400000).buildAndRegister();
        RecipeMaps.FUSION_RECIPES.recipeBuilder().fluidInputs(Materials.Gallium.getFluid(16), Materials.Radon.getFluid(125)).fluidOutputs(Materials.Duranium.getFluid(16)).duration(64).EUt(16384).EUToStart(1400000).buildAndRegister();
        RecipeMaps.FUSION_RECIPES.recipeBuilder().fluidInputs(Materials.Titanium.getFluid(48), Materials.Duranium.getFluid(32)).fluidOutputs(Materials.Tritanium.getFluid(16)).duration(64).EUt(32768).EUToStart(2000000).buildAndRegister();
        RecipeMaps.FUSION_RECIPES.recipeBuilder().fluidInputs(Materials.Gold.getFluid(16), Materials.Mercury.getFluid(16)).fluidOutputs(Materials.Radon.getFluid(125)).duration(64).EUt(32768).EUToStart(2000000).buildAndRegister();
        RecipeMaps.FUSION_RECIPES.recipeBuilder().fluidInputs(Materials.Tantalum.getFluid(16), Materials.Tritium.getFluid(16)).fluidOutputs(Materials.Tungsten.getFluid(16)).duration(16).EUt(24576).EUToStart(2000000).buildAndRegister();
        RecipeMaps.FUSION_RECIPES.recipeBuilder().fluidInputs(Materials.Silver.getFluid(16), Materials.Lithium.getFluid(16)).fluidOutputs(Materials.Indium.getFluid(16)).duration(32).EUt(24576).EUToStart(3800000).buildAndRegister();
        RecipeMaps.FUSION_RECIPES.recipeBuilder().fluidInputs(Materials.NaquadahEnriched.getFluid(15), Materials.Radon.getFluid(125)).fluidOutputs(Materials.Naquadria.getFluid(3)).duration(64).EUt(49152).EUToStart(4000000).buildAndRegister();
        RecipeMaps.FUSION_RECIPES.recipeBuilder().fluidInputs(Materials.Lithium.getFluid(16), Materials.Tungsten.getFluid(16)).fluidOutputs(Materials.Iridium.getFluid(16)).duration(32).EUt(32768).EUToStart(3000000).buildAndRegister();

        //Fusion Casing Recipes
        ModHandler.addShapedRecipe("fusion_casing_1", MetaBlocks.MUTLIBLOCK_CASING.getItemVariant(MultiblockCasingType.FUSION_CASING), "PhP", "PHP", "PwP", 'P', "plateTungstenSteel", 'H', MetaBlocks.MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.LuV));
        ModHandler.addShapedRecipe("fusion_casing_2", MetaBlocks.MUTLIBLOCK_CASING.getItemVariant(MultiblockCasingType.FUSION_CASING_MK2), "PhP", "PHP", "PwP", 'P', "plateAmericium", 'H', MetaBlocks.MUTLIBLOCK_CASING.getItemVariant(MultiblockCasingType.FUSION_CASING));
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(16).inputs(MetaBlocks.MUTLIBLOCK_CASING.getItemVariant(MultiblockCasingType.FUSION_CASING)).input(OrePrefix.plate, Materials.Americium, 6).outputs(MetaBlocks.MUTLIBLOCK_CASING.getItemVariant(MultiblockCasingType.FUSION_CASING_MK2)).duration(50).buildAndRegister();

        ModHandler.addShapedRecipe("fusion_coil", MetaBlocks.WIRE_COIL.getItemVariant(BlockWireCoil.CoilType.FUSION_COIL), "CRC", "FSF", "CRC", 'C', "circuitMaster", 'R', MetaItems.NEUTRON_REFLECTOR.getStackForm(), 'F', MetaItems.FIELD_GENERATOR_MV.getStackForm(), 'S', MetaBlocks.WIRE_COIL.getItemVariant(BlockWireCoil.CoilType.SUPERCONDUCTOR));

        //Explosive Recipes
        ModHandler.removeRecipes(new ItemStack(Blocks.TNT));
        ModHandler.removeRecipes(MetaItems.DYNAMITE.getStackForm());
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(4).inputs(new ItemStack(Items.PAPER), new ItemStack(Items.STRING)).fluidInputs(Materials.Glyceryl.getFluid(500)).outputs(MetaItems.DYNAMITE.getStackForm()).buildAndRegister();
        ModHandler.addShapelessRecipe("tnt_from_dynamite", new ItemStack(Blocks.TNT), MetaItems.DYNAMITE, MetaItems.DYNAMITE, MetaItems.DYNAMITE, MetaItems.DYNAMITE);
        RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder().EUt(16).duration(180).inputs(MetaItems.DYNAMITE.getStackForm(4)).outputs(new ItemStack(Blocks.TNT)).buildAndRegister();

        //Electromagnetic Separator Recipes
        RecipeMaps.ELECTROMAGNETIC_SEPARATOR_RECIPES.recipeBuilder().duration(400).EUt(24).input(OrePrefix.dustPure, Materials.BrownLimonite).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.BrownLimonite)).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Iron), 4000, 1000).chancedOutput(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron), 2000, 1000).buildAndRegister();
        RecipeMaps.ELECTROMAGNETIC_SEPARATOR_RECIPES.recipeBuilder().duration(400).EUt(24).input(OrePrefix.dustPure, Materials.YellowLimonite).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.YellowLimonite)).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Iron), 4000, 1000).chancedOutput(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron), 2000, 1000).buildAndRegister();
        RecipeMaps.ELECTROMAGNETIC_SEPARATOR_RECIPES.recipeBuilder().duration(400).EUt(24).input(OrePrefix.dustPure, Materials.Nickel).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Nickel)).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Iron), 4000, 1000).chancedOutput(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron), 2000, 1000).buildAndRegister();
        RecipeMaps.ELECTROMAGNETIC_SEPARATOR_RECIPES.recipeBuilder().duration(400).EUt(24).input(OrePrefix.dustPure, Materials.Pentlandite).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Pentlandite)).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Iron), 4000, 1000).chancedOutput(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron), 2000, 1000).buildAndRegister();
        RecipeMaps.ELECTROMAGNETIC_SEPARATOR_RECIPES.recipeBuilder().duration(400).EUt(24).input(OrePrefix.dustPure, Materials.BandedIron).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.BandedIron)).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Iron), 4000, 1000).chancedOutput(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron), 2000, 1000).buildAndRegister();
        RecipeMaps.ELECTROMAGNETIC_SEPARATOR_RECIPES.recipeBuilder().duration(400).EUt(24).input(OrePrefix.dustPure, Materials.Ilmenite).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Ilmenite)).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Iron), 4000, 1000).chancedOutput(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron), 2000, 1000).buildAndRegister();
        RecipeMaps.ELECTROMAGNETIC_SEPARATOR_RECIPES.recipeBuilder().duration(400).EUt(24).input(OrePrefix.dustPure, Materials.Pyrite).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Pyrite)).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Iron), 4000, 1000).chancedOutput(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron), 2000, 1000).buildAndRegister();
        RecipeMaps.ELECTROMAGNETIC_SEPARATOR_RECIPES.recipeBuilder().duration(400).EUt(24).input(OrePrefix.dustPure, Materials.Tin).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Tin)).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Iron), 4000, 1000).chancedOutput(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron), 2000, 1000).buildAndRegister();
        RecipeMaps.ELECTROMAGNETIC_SEPARATOR_RECIPES.recipeBuilder().duration(400).EUt(24).input(OrePrefix.dustPure, Materials.Chromite).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Chromite)).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Iron), 4000, 1000).chancedOutput(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron), 2000, 1000).buildAndRegister();
        RecipeMaps.ELECTROMAGNETIC_SEPARATOR_RECIPES.recipeBuilder().duration(400).EUt(24).input(OrePrefix.dustPure, Materials.Monazite).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Monazite)).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Neodymium), 4000, 1000).chancedOutput(OreDictUnifier.get(OrePrefix.nugget, Materials.Neodymium), 2000, 1000).buildAndRegister();
        RecipeMaps.ELECTROMAGNETIC_SEPARATOR_RECIPES.recipeBuilder().duration(400).EUt(24).input(OrePrefix.dustPure, Materials.Bastnasite).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Bastnasite)).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Neodymium), 4000, 1000).chancedOutput(OreDictUnifier.get(OrePrefix.nugget, Materials.Neodymium), 2000, 1000).buildAndRegister();
        RecipeMaps.ELECTROMAGNETIC_SEPARATOR_RECIPES.recipeBuilder().duration(400).EUt(24).input(OrePrefix.dustPure, Materials.VanadiumMagnetite).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.VanadiumMagnetite)).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Gold), 4000, 1000).chancedOutput(OreDictUnifier.get(OrePrefix.nugget, Materials.Gold), 2000, 1000).buildAndRegister();
        RecipeMaps.ELECTROMAGNETIC_SEPARATOR_RECIPES.recipeBuilder().duration(400).EUt(24).input(OrePrefix.dustPure, Materials.Magnetite).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Magnetite)).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Gold), 4000, 1000).chancedOutput(OreDictUnifier.get(OrePrefix.nugget, Materials.Gold), 2000, 1000).buildAndRegister();

        //Lapotron Crystal Recipes
        ModHandler.removeRecipes(MetaItems.LAPOTRON_CRYSTAL.getStackForm());
        for (MaterialStack m : lapisLike) {
            GemMaterial gem = (GemMaterial) m.material;
            ModHandler.addShapedRecipe("lapotron_crystal_shaped" + gem, MetaItems.LAPOTRON_CRYSTAL.getStackForm(), "PCP", "RFR", "PCP", 'P', new UnificationEntry(OrePrefix.plate, gem), 'C', "circuitAdvanced", 'R', OreDictUnifier.get(OrePrefix.stick, gem), 'F', OreDictUnifier.get(OrePrefix.gemFlawless, Materials.Sapphire));
            ModHandler.addShapedRecipe("lapotron_crystal_shaped_with_crystal" + gem, MetaItems.LAPOTRON_CRYSTAL.getStackForm(), "PCP", "RFR", "PCP", 'P', new UnificationEntry(OrePrefix.plate, gem), 'C', "circuitExtreme", 'R', OreDictUnifier.get(OrePrefix.stick, gem), 'F', MetaItems.ENERGY_CRYSTAL.getStackForm());
            ModHandler.addShapelessRecipe("lapotron_crystal_shapeless" + gem, MetaItems.LAPOTRON_CRYSTAL.getStackForm(), OreDictUnifier.get(OrePrefix.gemExquisite, Materials.Sapphire), OreDictUnifier.get(OrePrefix.stick, gem), MetaItems.CAPACITOR.getStackForm());
        }

        //Configuration Circuit
        ModHandler.removeRecipes(MetaItems.BASIC_ELECTRONIC_CIRCUIT_LV.getStackForm());

        //MAX Machine Hull
        ModHandler.removeRecipeByName(new ResourceLocation("gregtech:casing_max"));
        ModHandler.removeRecipeByName(new ResourceLocation("gregtech:hull_max"));
        ModHandler.addShapedRecipe("ga_casing_max", MetaBlocks.MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.MAX), "PPP", "PwP", "PPP", 'P', new UnificationEntry(OrePrefix.plate, GAMaterials.Neutronium));
        ModHandler.addShapedRecipe("ga_hull_max", MetaTileEntities.HULL[GTValues.MAX].getStackForm(), "PHP", "CMC", 'M', MetaBlocks.MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.MAX), 'C', new UnificationEntry(OrePrefix.wireGtSingle, Tier.Superconductor), 'H', new UnificationEntry(OrePrefix.plate, GAMaterials.Neutronium), 'P', new UnificationEntry(OrePrefix.plate, Materials.Polytetrafluoroethylene));
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(16).input(OrePrefix.plate, GAMaterials.Neutronium, 8).outputs(MetaBlocks.MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.MAX)).circuitMeta(8).duration(50).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(16).inputs(MetaBlocks.MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.MAX)).input(OrePrefix.wireGtSingle, Tier.Superconductor, 2).fluidInputs(Materials.Polytetrafluoroethylene.getFluid(288)).outputs(MetaTileEntities.HULL[9].getStackForm()).buildAndRegister();

        //Dust melting
        RecipeMaps.FLUID_EXTRACTION_RECIPES.recipeBuilder().duration(80).EUt(32).input(OrePrefix.dust, Materials.Redstone).fluidOutputs(Materials.Redstone.getFluid(144)).buildAndRegister();
        RecipeMaps.FLUID_EXTRACTION_RECIPES.recipeBuilder().duration(80).EUt(32).input(OrePrefix.dust, Materials.Glowstone).fluidOutputs(Materials.Glowstone.getFluid(144)).buildAndRegister();
        RecipeMaps.FLUID_EXTRACTION_RECIPES.recipeBuilder().duration(80).EUt(32).input(OrePrefix.dust, Materials.Boron).fluidOutputs(Materials.Boron.getFluid(144)).buildAndRegister();

        RecipeMaps.MACERATOR_RECIPES.recipeBuilder().duration(30).EUt(8).inputs(new ItemStack(Items.CLAY_BALL)).output(OrePrefix.dust, Materials.Clay).buildAndRegister();

        //Gem Tool Part Fixes
        for (Material material : GemMaterial.MATERIAL_REGISTRY) {
            if (!OreDictUnifier.get(OrePrefix.gem, material).isEmpty() && !OreDictUnifier.get(OrePrefix.toolHeadHammer, material).isEmpty() && material != Materials.Flint) {
                ModHandler.removeRecipes(OreDictUnifier.get(OrePrefix.toolHeadAxe, material));
                ModHandler.addShapedRecipe("axe_head_" + material.toString(), OreDictUnifier.get(OrePrefix.toolHeadAxe, material), "GGh", "G  ", "f  ", 'G', new UnificationEntry(OrePrefix.gem, material));
                ModHandler.removeRecipes(OreDictUnifier.get(OrePrefix.toolHeadFile, material));
                ModHandler.addShapedRecipe("file_head_" + material, OreDictUnifier.get(OrePrefix.toolHeadFile, material), "G ", "G ", "fh", 'G', new UnificationEntry(OrePrefix.gem, material));
                ModHandler.removeRecipes(OreDictUnifier.get(OrePrefix.toolHeadHammer, material));
                ModHandler.addShapedRecipe("hammer_head_" + material, OreDictUnifier.get(OrePrefix.toolHeadHammer, material), "GG ", "GGf", "GG ", 'G', new UnificationEntry(OrePrefix.gem, material));
                ModHandler.removeRecipes(OreDictUnifier.get(OrePrefix.toolHeadHoe, material));
                ModHandler.addShapedRecipe("hoe_head_" + material, OreDictUnifier.get(OrePrefix.toolHeadHoe, material), "GGh", "f  ", 'G', new UnificationEntry(OrePrefix.gem, material));
                ModHandler.removeRecipes(OreDictUnifier.get(OrePrefix.toolHeadPickaxe, material));
                ModHandler.addShapedRecipe("pickaxe_head_" + material, OreDictUnifier.get(OrePrefix.toolHeadPickaxe, material), "GGG", "f h", 'G', new UnificationEntry(OrePrefix.gem, material));
                ModHandler.removeRecipes(OreDictUnifier.get(OrePrefix.toolHeadSaw, material));
                ModHandler.addShapedRecipe("saw_head_" + material, OreDictUnifier.get(OrePrefix.toolHeadSaw, material), "GG", "fh", 'G', new UnificationEntry(OrePrefix.gem, material));
                ModHandler.removeRecipes(OreDictUnifier.get(OrePrefix.toolHeadSense, material));
                ModHandler.addShapedRecipe("sense_head_" + material, OreDictUnifier.get(OrePrefix.toolHeadSense, material), "GGG", "hf ", 'G', new UnificationEntry(OrePrefix.gem, material));
                ModHandler.removeRecipes(OreDictUnifier.get(OrePrefix.toolHeadShovel, material));
                ModHandler.addShapedRecipe("shovel_head_" + material, OreDictUnifier.get(OrePrefix.toolHeadShovel, material), "fGh", 'G', new UnificationEntry(OrePrefix.gem, material));
                ModHandler.removeRecipes(OreDictUnifier.get(OrePrefix.toolHeadSword, material));
                ModHandler.addShapedRecipe("sword_head_" + material, OreDictUnifier.get(OrePrefix.toolHeadSword, material), "   ", " G ", "fGh", 'G', new UnificationEntry(OrePrefix.gem, material));
                ModHandler.removeRecipes(OreDictUnifier.get(OrePrefix.toolHeadUniversalSpade, material));
                ModHandler.addShapedRecipe("universal_spade_head_" + material, OreDictUnifier.get(OrePrefix.toolHeadUniversalSpade, material), "GGG", "GhG", " G ", 'G', new UnificationEntry(OrePrefix.gem, material));
            }
        }

        //Misc Recipe Patches
        RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder().duration(400).EUt(2).input(OrePrefix.dust, Materials.NetherQuartz).outputs(OreDictUnifier.get(OrePrefix.plate, Materials.NetherQuartz)).buildAndRegister();
        RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder().duration(400).EUt(2).input(OrePrefix.dust, Materials.CertusQuartz).outputs(OreDictUnifier.get(OrePrefix.plate, Materials.CertusQuartz)).buildAndRegister();

        //Change Solar Panel recipes
        ModHandler.removeRecipes(MetaItems.COVER_SOLAR_PANEL.getStackForm());
        ModHandler.addShapedRecipe("basic_solar_panel", MetaItems.COVER_SOLAR_PANEL.getStackForm(), "PGP", "WCW", "AAA", 'P', "plateSilicon", 'C', "circuitBasic", 'G', "paneGlass", 'W', "cableGtSingleCopper", 'A', "plateAluminium");
        ModHandler.removeRecipes(MetaItems.COVER_SOLAR_PANEL_ULV.getStackForm());
        ModHandler.addShapedRecipe("ulv_solar_panel", MetaItems.COVER_SOLAR_PANEL_ULV.getStackForm(), "SSS", "SCS", "SSS", 'C', "circuitBasic", 'S', MetaItems.COVER_SOLAR_PANEL);
        ModHandler.removeRecipes(MetaItems.COVER_SOLAR_PANEL_LV.getStackForm());
        ModHandler.addShapedRecipe("lv_solar_panel", MetaItems.COVER_SOLAR_PANEL_LV.getStackForm(), "SSS", "SCS", "SSS", 'C', "circuitGood", 'S', MetaItems.COVER_SOLAR_PANEL_ULV);
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(600).EUt(64).input(OrePrefix.plate, Materials.Silicon, 2).input(OrePrefix.paneGlass.name(), 1).input(OrePrefix.cableGtSingle, Materials.Copper).input(OrePrefix.circuit, Tier.Basic).input(OrePrefix.plate, Materials.Aluminium, 3).outputs(MetaItems.COVER_SOLAR_PANEL.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(160).EUt(16).inputs(MetaItems.COVER_SOLAR_PANEL.getStackForm(8)).input(OrePrefix.circuit, Tier.Basic).outputs(MetaItems.COVER_SOLAR_PANEL_ULV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(160).EUt(16).inputs(MetaItems.COVER_SOLAR_PANEL_ULV.getStackForm(8)).input(OrePrefix.circuit, Tier.Good).outputs(MetaItems.COVER_SOLAR_PANEL_LV.getStackForm()).buildAndRegister();

        //Improved Superconductor recipes
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(1200).EUt(120).input(OrePrefix.dust, Materials.Cadmium, 5).input(OrePrefix.dust, Materials.Magnesium).fluidInputs(Materials.Oxygen.getFluid(6000)).outputs(OreDictUnifier.get(OrePrefix.dust, GAMaterials.MVSuperconductorBase, 12)).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(2400).EUt(120).input(OrePrefix.dust, Materials.Titanium).input(OrePrefix.dust, Materials.Barium, 9).input(OrePrefix.dust, Materials.Copper, 10).fluidInputs(Materials.Oxygen.getFluid(20000)).outputs(OreDictUnifier.get(OrePrefix.dust, GAMaterials.HVSuperconductorBase, 40)).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(400).EUt(480).input(OrePrefix.dust, Materials.Uranium).input(OrePrefix.dust, Materials.Platinum, 3).outputs(OreDictUnifier.get(OrePrefix.dust, GAMaterials.EVSuperconductorBase, 4)).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(400).EUt(480).input(OrePrefix.dust, Materials.Vanadium).input(OrePrefix.dust, Materials.Indium, 3).outputs(OreDictUnifier.get(OrePrefix.dust, GAMaterials.IVSuperconductorBase, 4)).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(2400).EUt(1920).input(OrePrefix.dust, Materials.Indium, 4).input(OrePrefix.dust, Materials.Bronze, 8).input(OrePrefix.dust, Materials.Barium, 2).input(OrePrefix.dust, Materials.Titanium).fluidInputs(Materials.Oxygen.getFluid(14000)).outputs(OreDictUnifier.get(OrePrefix.dust, GAMaterials.LuVSuperconductorBase, 29)).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(1200).EUt(1920).input(OrePrefix.dust, Materials.Naquadah, 4).input(OrePrefix.dust, Materials.Indium, 2).input(OrePrefix.dust, Materials.Palladium, 6).input(OrePrefix.dust, Materials.Osmium).outputs(OreDictUnifier.get(OrePrefix.dust, GAMaterials.ZPMSuperconductorBase, 13)).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(1200).EUt(1920).input(OrePrefix.dust, Materials.NaquadahAlloy, 6).input(OrePrefix.dust, Materials.Ultimet, 4).input(OrePrefix.dust, Materials.Americium, 2).input(OrePrefix.dust, Materials.Tritanium, 2).outputs(OreDictUnifier.get(OrePrefix.dust, GAMaterials.UVSuperconductorBase, 13)).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(400).EUt(8).input(OrePrefix.dust, Materials.Lead, 3).input(OrePrefix.dust, Materials.Platinum).input(OrePrefix.dust, Materials.EnderPearl, 4).outputs(OreDictUnifier.get(OrePrefix.dust, GAMaterials.Enderium, 4)).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(300).EUt(120).inputs(OreDictUnifier.get(OrePrefix.wireGtSingle, GAMaterials.MVSuperconductorBase, 3), OreDictUnifier.get(OrePrefix.pipeTiny, Materials.StainlessSteel, 2), MetaItems.ELECTRIC_PUMP_MV.getStackForm(2)).fluidInputs(Materials.Nitrogen.getFluid(2000)).outputs(OreDictUnifier.get(OrePrefix.wireGtSingle, GAMaterials.MVSuperconductor, 3)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(200).EUt(256).inputs(OreDictUnifier.get(OrePrefix.wireGtSingle, GAMaterials.HVSuperconductorBase, 3), OreDictUnifier.get(OrePrefix.pipeTiny, Materials.Titanium, 2), MetaItems.ELECTRIC_PUMP_HV.getStackForm()).fluidInputs(Materials.Nitrogen.getFluid(2000)).outputs(OreDictUnifier.get(OrePrefix.wireGtSingle, GAMaterials.HVSuperconductor, 3)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(500).EUt(480).inputs(OreDictUnifier.get(OrePrefix.wireGtSingle, GAMaterials.EVSuperconductorBase, 9), OreDictUnifier.get(OrePrefix.pipeTiny, Materials.TungstenSteel, 6), MetaItems.ELECTRIC_PUMP_EV.getStackForm(2)).fluidInputs(Materials.Nitrogen.getFluid(6000)).outputs(OreDictUnifier.get(OrePrefix.wireGtSingle, GAMaterials.EVSuperconductor, 9)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(300).EUt(1920).inputs(OreDictUnifier.get(OrePrefix.wireGtSingle, GAMaterials.IVSuperconductorBase, 6), OreDictUnifier.get(OrePrefix.pipeTiny, Materials.NiobiumTitanium, 4), MetaItems.ELECTRIC_PUMP_IV.getStackForm()).fluidInputs(Materials.Nitrogen.getFluid(4000)).outputs(OreDictUnifier.get(OrePrefix.wireGtSingle, GAMaterials.IVSuperconductor, 6)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(350).EUt(7680).inputs(OreDictUnifier.get(OrePrefix.wireGtSingle, GAMaterials.LuVSuperconductorBase, 8), OreDictUnifier.get(OrePrefix.pipeTiny, GAMaterials.Enderium, 5), MetaItems.ELECTRIC_PUMP_LUV.getStackForm()).fluidInputs(Materials.Nitrogen.getFluid(6000)).outputs(OreDictUnifier.get(OrePrefix.wireGtSingle, GAMaterials.LuVSuperconductor, 8)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(400).EUt(30720).inputs(OreDictUnifier.get(OrePrefix.wireGtSingle, GAMaterials.ZPMSuperconductorBase, 16), OreDictUnifier.get(OrePrefix.pipeTiny, Materials.Naquadah, 6), MetaItems.ELECTRIC_PUMP_ZPM.getStackForm()).fluidInputs(Materials.Nitrogen.getFluid(8000)).outputs(OreDictUnifier.get(OrePrefix.wireGtSingle, GAMaterials.ZPMSuperconductor, 16)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(500).EUt(122880).inputs(OreDictUnifier.get(OrePrefix.wireGtSingle, GAMaterials.UVSuperconductorBase, 32), OreDictUnifier.get(OrePrefix.pipeTiny, GAMaterials.Neutronium, 7), MetaItems.ELECTRIC_PUMP_UV.getStackForm()).fluidInputs(Materials.Nitrogen.getFluid(10000)).outputs(OreDictUnifier.get(OrePrefix.wireGtSingle, GAMaterials.UVSuperconductor, 32)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(500).EUt(524288).inputs(OreDictUnifier.get(OrePrefix.wireGtSingle, GAMaterials.UVSuperconductorBase, 64), OreDictUnifier.get(OrePrefix.pipeTiny, GAMaterials.Neutronium, 14), MetaItems.ELECTRIC_PUMP_UV.getStackForm()).fluidInputs(Materials.Nitrogen.getFluid(120000)).outputs(OreDictUnifier.get(OrePrefix.wireGtSingle, Tier.Superconductor, 32)).buildAndRegister();

        //GTNH Coils
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(400).EUt(8).input(OrePrefix.dust, Materials.Mica, 3).input(OrePrefix.dust, Materials.RawRubber, 2).outputs(OreDictUnifier.get(OrePrefix.dust, GAMaterials.MicaPulp, 4)).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(400).EUt(8).input(OrePrefix.dust, Materials.Mica, 3).inputs(MetaItems.RUBBER_DROP.getStackForm()).outputs(OreDictUnifier.get(OrePrefix.dust, GAMaterials.MicaPulp, 4)).buildAndRegister();

        RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder().duration(1200).EUt(30).input(OrePrefix.dust, Materials.Sapphire).input(OrePrefix.dust, Materials.SiliconDioxide).outputs(OreDictUnifier.get(OrePrefix.dust, GAMaterials.AluminoSilicateWool, 2)).buildAndRegister();
        RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder().duration(1200).EUt(30).input(OrePrefix.dust, Materials.GreenSapphire).input(OrePrefix.dust, Materials.SiliconDioxide).outputs(OreDictUnifier.get(OrePrefix.dust, GAMaterials.AluminoSilicateWool, 2)).buildAndRegister();
        RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder().duration(1200).EUt(30).input(OrePrefix.dust, Materials.Ruby).input(OrePrefix.dust, Materials.SiliconDioxide).outputs(OreDictUnifier.get(OrePrefix.dust, GAMaterials.AluminoSilicateWool, 2)).buildAndRegister();

        RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder().duration(400).EUt(28).input(OrePrefix.dust, GAMaterials.MicaPulp, 4).input(OrePrefix.dust, Materials.Asbestos).outputs(GAMetaItems.MICA_SHEET.getStackForm(4)).buildAndRegister();

        RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder().duration(400).EUt(30).inputs(GAMetaItems.MICA_SHEET.getStackForm(4)).input(OrePrefix.dust, Materials.SiliconDioxide).outputs(GAMetaItems.MICA_INSULATOR_SHEET.getStackForm(4)).buildAndRegister();
        if (GAConfig.GT6.BendingFoilsAutomatic && GAConfig.GT6.BendingCylinders)
            GARecipeMaps.CLUSTER_MILL_RECIPES.recipeBuilder().duration(100).EUt(30).inputs(GAMetaItems.MICA_INSULATOR_SHEET.getStackForm()).outputs(GAMetaItems.MICA_INSULATOR_FOI.getStackForm(4)).buildAndRegister();
        else
            RecipeMaps.BENDER_RECIPES.recipeBuilder().duration(100).EUt(30).inputs(GAMetaItems.MICA_INSULATOR_SHEET.getStackForm()).circuitMeta(1).outputs(GAMetaItems.MICA_INSULATOR_FOI.getStackForm(4)).buildAndRegister();

        ModHandler.removeRecipeByName(new ResourceLocation("gregtech:heating_coil_cupronickel"));
        ModHandler.removeRecipeByName(new ResourceLocation("gregtech:heating_coil_kanthal"));
        ModHandler.removeRecipeByName(new ResourceLocation("gregtech:heating_coil_nichrome"));
        ModHandler.removeRecipeByName(new ResourceLocation("gregtech:heating_coil_tungstensteel"));
        ModHandler.removeRecipeByName(new ResourceLocation("gregtech:heating_coil_hss_g"));
        ModHandler.removeRecipeByName(new ResourceLocation("gregtech:heating_coil_naquadah"));
        ModHandler.removeRecipeByName(new ResourceLocation("gregtech:heating_coil_naquadah_alloy"));
        ModHandler.removeRecipeByName(new ResourceLocation("gregtech:wire_coil_cupronickel"));
        ModHandler.removeRecipeByName(new ResourceLocation("gregtech:wire_coil_kanthal"));
        ModHandler.removeRecipeByName(new ResourceLocation("gregtech:wire_coil_nichrome"));
        ModHandler.removeRecipeByName(new ResourceLocation("gregtech:wire_coil_tungstensteel"));
        ModHandler.removeRecipeByName(new ResourceLocation("gregtech:wire_coil_hss_g"));
        ModHandler.removeRecipeByName(new ResourceLocation("gregtech:wire_coil_naquadah"));
        ModHandler.removeRecipeByName(new ResourceLocation("gregtech:wire_coil_naquadah_alloy"));
        ModHandler.removeRecipeByName(new ResourceLocation("gregtech:wire_coil_superconductor"));

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(100).EUt(8).inputs(OreDictUnifier.get(OrePrefix.wireGtDouble, Materials.Cupronickel, 8), OreDictUnifier.get(OrePrefix.dust, GAMaterials.AluminoSilicateWool, 12)).fluidInputs(Materials.Tin.getFluid(144)).outputs(MetaBlocks.WIRE_COIL.getItemVariant(BlockWireCoil.CoilType.CUPRONICKEL)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(200).EUt(8).inputs(OreDictUnifier.get(OrePrefix.wireGtDouble, Materials.Cupronickel, 8), GAMetaItems.MICA_INSULATOR_FOI.getStackForm(8)).fluidInputs(Materials.Tin.getFluid(144)).outputs(MetaBlocks.WIRE_COIL.getItemVariant(BlockWireCoil.CoilType.CUPRONICKEL)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(300).EUt(30).inputs(OreDictUnifier.get(OrePrefix.wireGtDouble, Materials.Kanthal, 8), GAMetaItems.MICA_INSULATOR_FOI.getStackForm(8)).fluidInputs(Materials.Copper.getFluid(144)).outputs(MetaBlocks.WIRE_COIL.getItemVariant(BlockWireCoil.CoilType.KANTHAL)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(400).EUt(120).inputs(OreDictUnifier.get(OrePrefix.wireGtDouble, Materials.Nichrome, 8), GAMetaItems.MICA_INSULATOR_FOI.getStackForm(8)).fluidInputs(Materials.Aluminium.getFluid(144)).outputs(MetaBlocks.WIRE_COIL.getItemVariant(BlockWireCoil.CoilType.NICHROME)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(500).EUt(480).inputs(OreDictUnifier.get(OrePrefix.wireGtDouble, Materials.TungstenSteel, 8), GAMetaItems.MICA_INSULATOR_FOI.getStackForm(8)).fluidInputs(Materials.Nichrome.getFluid(144)).outputs(MetaBlocks.WIRE_COIL.getItemVariant(BlockWireCoil.CoilType.TUNGSTENSTEEL)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(600).EUt(1920).inputs(OreDictUnifier.get(OrePrefix.wireGtDouble, Materials.HSSG, 8), GAMetaItems.MICA_INSULATOR_FOI.getStackForm(8)).fluidInputs(Materials.Tungsten.getFluid(144)).outputs(MetaBlocks.WIRE_COIL.getItemVariant(BlockWireCoil.CoilType.HSS_G)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(700).EUt(4096).inputs(OreDictUnifier.get(OrePrefix.wireGtDouble, Materials.Naquadah, 8), GAMetaItems.MICA_INSULATOR_FOI.getStackForm(8)).fluidInputs(Materials.HSSG.getFluid(144)).outputs(MetaBlocks.WIRE_COIL.getItemVariant(BlockWireCoil.CoilType.NAQUADAH)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(800).EUt(7680).inputs(OreDictUnifier.get(OrePrefix.wireGtDouble, Materials.NaquadahAlloy, 8), GAMetaItems.MICA_INSULATOR_FOI.getStackForm(8)).fluidInputs(Materials.Naquadah.getFluid(144)).outputs(MetaBlocks.WIRE_COIL.getItemVariant(BlockWireCoil.CoilType.NAQUADAH_ALLOY)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(1000).EUt(9001).inputs(OreDictUnifier.get(OrePrefix.wireGtDouble, Tier.Superconductor, 8), GAMetaItems.MICA_INSULATOR_FOI.getStackForm(8)).fluidInputs(Materials.NaquadahAlloy.getFluid(144)).outputs(MetaBlocks.WIRE_COIL.getItemVariant(BlockWireCoil.CoilType.SUPERCONDUCTOR)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(1000).EUt(9001).inputs(OreDictUnifier.get(OrePrefix.wireGtDouble, GAMaterials.ZPMSuperconductor, 32), GAMetaItems.MICA_INSULATOR_FOI.getStackForm(16)).fluidInputs(Materials.NaquadahAlloy.getFluid(144)).outputs(MetaBlocks.WIRE_COIL.getItemVariant(BlockWireCoil.CoilType.SUPERCONDUCTOR)).buildAndRegister();
    }

    public static void forestrySupport() {
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(16).inputs(GAMetaItems.ELECTRODE_APATITE.getStackForm(), OreDictUnifier.get(OrePrefix.plate, Materials.Glass)).outputs(ModuleCore.getItems().tubes.get(EnumElectronTube.APATITE, 1)).buildAndRegister();
        RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder().duration(100).EUt(24).input(OrePrefix.stick, Materials.Apatite, 2).input(OrePrefix.bolt, Materials.Apatite).input(OrePrefix.dustSmall, Materials.Redstone, 2).outputs(GAMetaItems.ELECTRODE_APATITE.getStackForm()).buildAndRegister();
        RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder().duration(200).EUt(24).input(OrePrefix.stick, Materials.Apatite, 4).input(OrePrefix.bolt, Materials.Apatite, 2).input(OrePrefix.dust, Materials.Redstone).outputs(GAMetaItems.ELECTRODE_APATITE.getStackForm(2)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(16).inputs(GAMetaItems.ELECTRODE_BLAZE.getStackForm(), OreDictUnifier.get(OrePrefix.plate, Materials.Glass)).outputs(ModuleCore.getItems().tubes.get(EnumElectronTube.BLAZE, 1)).buildAndRegister();
        RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder().duration(200).EUt(24).input(OrePrefix.dust, Materials.Blaze, 2).input(OrePrefix.dustSmall, Materials.Blaze, 2).input(OrePrefix.dust, Materials.Redstone).outputs(GAMetaItems.ELECTRODE_BLAZE.getStackForm(2)).buildAndRegister();
        RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder().duration(400).EUt(24).input(OrePrefix.dust, Materials.Blaze, 5).input(OrePrefix.dust, Materials.Redstone, 2).outputs(GAMetaItems.ELECTRODE_BLAZE.getStackForm(4)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(16).inputs(GAMetaItems.ELECTRODE_BRONZE.getStackForm(), OreDictUnifier.get(OrePrefix.plate, Materials.Glass)).outputs(ModuleCore.getItems().tubes.get(EnumElectronTube.BRONZE, 1)).buildAndRegister();
        RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder().duration(100).EUt(24).input(OrePrefix.stick, Materials.Bronze, 2).input(OrePrefix.bolt, Materials.Bronze).input(OrePrefix.dustSmall, Materials.Redstone, 2).outputs(GAMetaItems.ELECTRODE_BRONZE.getStackForm()).buildAndRegister();
        RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder().duration(200).EUt(24).input(OrePrefix.stick, Materials.Bronze, 4).input(OrePrefix.bolt, Materials.Bronze, 2).input(OrePrefix.dust, Materials.Redstone).outputs(GAMetaItems.ELECTRODE_BRONZE.getStackForm(2)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(16).inputs(GAMetaItems.ELECTRODE_COPPER.getStackForm(), OreDictUnifier.get(OrePrefix.plate, Materials.Glass)).outputs(ModuleCore.getItems().tubes.get(EnumElectronTube.COPPER, 1)).buildAndRegister();
        RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder().duration(100).EUt(24).input(OrePrefix.stick, Materials.Copper, 2).input(OrePrefix.bolt, Materials.Copper).input(OrePrefix.dustSmall, Materials.Redstone, 2).outputs(GAMetaItems.ELECTRODE_COPPER.getStackForm()).buildAndRegister();
        RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder().duration(200).EUt(24).input(OrePrefix.stick, Materials.Copper, 4).input(OrePrefix.bolt, Materials.Copper, 2).input(OrePrefix.dust, Materials.Redstone).outputs(GAMetaItems.ELECTRODE_COPPER.getStackForm(2)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(16).inputs(GAMetaItems.ELECTRODE_DIAMOND.getStackForm(), OreDictUnifier.get(OrePrefix.plate, Materials.Glass)).outputs(ModuleCore.getItems().tubes.get(EnumElectronTube.DIAMOND, 1)).buildAndRegister();
        RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder().duration(100).EUt(24).input(OrePrefix.stick, Materials.Diamond, 2).input(OrePrefix.bolt, Materials.Diamond).input(OrePrefix.dustSmall, Materials.Redstone, 2).outputs(GAMetaItems.ELECTRODE_DIAMOND.getStackForm()).buildAndRegister();
        RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder().duration(200).EUt(24).input(OrePrefix.stick, Materials.Diamond, 4).input(OrePrefix.bolt, Materials.Diamond, 2).input(OrePrefix.dust, Materials.Redstone).outputs(GAMetaItems.ELECTRODE_DIAMOND.getStackForm(2)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(16).inputs(GAMetaItems.ELECTRODE_EMERALD.getStackForm(), OreDictUnifier.get(OrePrefix.plate, Materials.Glass)).outputs(ModuleCore.getItems().tubes.get(EnumElectronTube.EMERALD, 1)).buildAndRegister();
        RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder().duration(100).EUt(24).input(OrePrefix.stick, Materials.Emerald, 2).input(OrePrefix.bolt, Materials.Emerald).input(OrePrefix.dustSmall, Materials.Redstone, 2).outputs(GAMetaItems.ELECTRODE_EMERALD.getStackForm()).buildAndRegister();
        RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder().duration(200).EUt(24).input(OrePrefix.stick, Materials.Emerald, 4).input(OrePrefix.bolt, Materials.Emerald, 2).input(OrePrefix.dust, Materials.Redstone).outputs(GAMetaItems.ELECTRODE_EMERALD.getStackForm(2)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(16).inputs(GAMetaItems.ELECTRODE_ENDER.getStackForm(), OreDictUnifier.get(OrePrefix.plate, Materials.Glass)).outputs(ModuleCore.getItems().tubes.get(EnumElectronTube.ENDER, 1)).buildAndRegister();
        RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder().duration(200).EUt(24).input(OrePrefix.dust, Materials.Endstone, 2).input(OrePrefix.dustSmall, Materials.Endstone, 2).input(OrePrefix.dust, Materials.EnderEye).outputs(GAMetaItems.ELECTRODE_ENDER.getStackForm(2)).buildAndRegister();
        RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder().duration(400).EUt(24).input(OrePrefix.dust, Materials.Endstone, 5).input(OrePrefix.dust, Materials.EnderEye, 2).outputs(GAMetaItems.ELECTRODE_ENDER.getStackForm(4)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(16).inputs(GAMetaItems.ELECTRODE_GOLD.getStackForm(), OreDictUnifier.get(OrePrefix.plate, Materials.Glass)).outputs(ModuleCore.getItems().tubes.get(EnumElectronTube.GOLD, 1)).buildAndRegister();
        RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder().duration(100).EUt(24).input(OrePrefix.stick, Materials.Gold, 2).input(OrePrefix.bolt, Materials.Gold).input(OrePrefix.dustSmall, Materials.Redstone, 2).outputs(GAMetaItems.ELECTRODE_GOLD.getStackForm()).buildAndRegister();
        RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder().duration(200).EUt(24).input(OrePrefix.stick, Materials.Gold, 4).input(OrePrefix.bolt, Materials.Gold, 2).input(OrePrefix.dust, Materials.Redstone).outputs(GAMetaItems.ELECTRODE_GOLD.getStackForm(2)).buildAndRegister();
        if (Loader.isModLoaded("ic2") || Loader.isModLoaded("binniecore")) {
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(16).inputs(GAMetaItems.ELECTRODE_IRON.getStackForm(), OreDictUnifier.get(OrePrefix.plate, Materials.Glass)).outputs(ModuleCore.getItems().tubes.get(EnumElectronTube.IRON, 1)).buildAndRegister();
            RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder().duration(100).EUt(24).input(OrePrefix.stick, Materials.Iron, 2).input(OrePrefix.bolt, Materials.Iron).input(OrePrefix.dustSmall, Materials.Redstone, 2).outputs(GAMetaItems.ELECTRODE_IRON.getStackForm()).buildAndRegister();
            RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder().duration(200).EUt(24).input(OrePrefix.stick, Materials.Iron, 4).input(OrePrefix.bolt, Materials.Iron, 2).input(OrePrefix.dust, Materials.Redstone).outputs(GAMetaItems.ELECTRODE_IRON.getStackForm(2)).buildAndRegister();
        }
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(16).inputs(GAMetaItems.ELECTRODE_LAPIS.getStackForm(), OreDictUnifier.get(OrePrefix.plate, Materials.Glass)).outputs(ModuleCore.getItems().tubes.get(EnumElectronTube.LAPIS, 1)).buildAndRegister();
        RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder().duration(100).EUt(24).input(OrePrefix.stick, Materials.Lapis, 2).input(OrePrefix.bolt, Materials.Lapis).input(OrePrefix.dustSmall, Materials.Redstone, 2).outputs(GAMetaItems.ELECTRODE_LAPIS.getStackForm()).buildAndRegister();
        RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder().duration(200).EUt(24).input(OrePrefix.stick, Materials.Lapis, 4).input(OrePrefix.bolt, Materials.Lapis, 2).input(OrePrefix.dust, Materials.Redstone).outputs(GAMetaItems.ELECTRODE_LAPIS.getStackForm(2)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(16).inputs(GAMetaItems.ELECTRODE_OBSIDIAN.getStackForm(), OreDictUnifier.get(OrePrefix.plate, Materials.Glass)).outputs(ModuleCore.getItems().tubes.get(EnumElectronTube.OBSIDIAN, 1)).buildAndRegister();
        RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder().duration(200).EUt(24).input(OrePrefix.dust, Materials.Obsidian, 2).input(OrePrefix.dustSmall, Materials.Obsidian, 2).input(OrePrefix.dust, Materials.Redstone).outputs(GAMetaItems.ELECTRODE_OBSIDIAN.getStackForm(2)).buildAndRegister();
        RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder().duration(400).EUt(24).input(OrePrefix.dust, Materials.Obsidian, 5).input(OrePrefix.dust, Materials.Redstone, 2).outputs(GAMetaItems.ELECTRODE_OBSIDIAN.getStackForm(4)).buildAndRegister();
        if (Loader.isModLoaded("extrautils2")) {
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(16).inputs(GAMetaItems.ELECTRODE_ORCHID.getStackForm(), OreDictUnifier.get(OrePrefix.plate, Materials.Glass)).outputs(ModuleCore.getItems().tubes.get(EnumElectronTube.ORCHID, 1)).buildAndRegister();
            RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder().duration(400).EUt(24).inputs(new ItemStack(Blocks.REDSTONE_ORE, 5), OreDictUnifier.get(OrePrefix.dust, Materials.Redstone)).outputs(GAMetaItems.ELECTRODE_ORCHID.getStackForm(4)).buildAndRegister();
        }
        if (Loader.isModLoaded("ic2") || Loader.isModLoaded("techreborn") || Loader.isModLoaded("binniecore")) {
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(16).inputs(GAMetaItems.ELECTRODE_RUBBER.getStackForm(), OreDictUnifier.get(OrePrefix.plate, Materials.Glass)).outputs(ModuleCore.getItems().tubes.get(EnumElectronTube.RUBBER, 1)).buildAndRegister();
            RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder().duration(100).EUt(24).input(OrePrefix.stick, Materials.Rubber, 2).input(OrePrefix.bolt, Materials.Rubber).input(OrePrefix.dustSmall, Materials.Redstone, 2).outputs(GAMetaItems.ELECTRODE_RUBBER.getStackForm()).buildAndRegister();
            RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder().duration(200).EUt(24).input(OrePrefix.stick, Materials.Rubber, 4).input(OrePrefix.bolt, Materials.Rubber, 2).input(OrePrefix.dust, Materials.Redstone).outputs(GAMetaItems.ELECTRODE_RUBBER.getStackForm(2)).buildAndRegister();
        }
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(16).inputs(GAMetaItems.ELECTRODE_TIN.getStackForm(), OreDictUnifier.get(OrePrefix.plate, Materials.Glass)).outputs(ModuleCore.getItems().tubes.get(EnumElectronTube.TIN, 1)).buildAndRegister();
        RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder().duration(100).EUt(24).input(OrePrefix.stick, Materials.Tin, 2).input(OrePrefix.bolt, Materials.Tin).input(OrePrefix.dustSmall, Materials.Redstone, 2).outputs(GAMetaItems.ELECTRODE_TIN.getStackForm()).buildAndRegister();
        RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder().duration(200).EUt(24).input(OrePrefix.stick, Materials.Tin, 4).input(OrePrefix.bolt, Materials.Tin, 2).input(OrePrefix.dust, Materials.Redstone).outputs(GAMetaItems.ELECTRODE_TIN.getStackForm(2)).buildAndRegister();
    }

    public static void generatedRecipes() {
        Set<ResourceLocation> recipesToRemove = new HashSet<>();

        if (GAConfig.GT5U.GenerateCompressorRecipes) {
            ModHandler.removeRecipeByName(new ResourceLocation("minecraft:glowstone"));
            ModHandler.removeRecipeByName(new ResourceLocation("gregtech:block_compress_glowstone"));
            ModHandler.removeRecipeByName(new ResourceLocation("minecraft:quartz_block"));
            ModHandler.removeRecipeByName(new ResourceLocation("gregtech:block_compress_nether_quartz"));
            ModHandler.removeRecipeByName(new ResourceLocation("gregtech:block_decompress_nether_quartz"));
            RecipeMaps.FORGE_HAMMER_RECIPES.recipeBuilder().duration(400).EUt(2).inputs(OreDictUnifier.get(OrePrefix.block, Materials.NetherQuartz)).outputs(OreDictUnifier.get(OrePrefix.gem, Materials.NetherQuartz, 4)).buildAndRegister();
            RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder().duration(400).EUt(2).input(OrePrefix.gem, Materials.NetherQuartz, 4).outputs(new ItemStack(Blocks.QUARTZ_BLOCK)).buildAndRegister();
            RecipeMaps.FORGE_HAMMER_RECIPES.recipeBuilder().duration(400).EUt(2).inputs(new ItemStack(Blocks.GLOWSTONE)).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Glowstone, 4)).buildAndRegister();
            RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder().duration(400).EUt(2).input(OrePrefix.dust, Materials.Glowstone, 4).outputs(new ItemStack(Blocks.GLOWSTONE)).buildAndRegister();
            RecipeMaps.UNPACKER_RECIPES.recipeBuilder().duration(10).EUt(12).inputs(new ItemStack(Blocks.HAY_BLOCK)).notConsumable(new IntCircuitIngredient(1)).outputs(new ItemStack(Items.WHEAT, 9)).buildAndRegister();
        }

        for (IRecipe recipe : CraftingManager.REGISTRY) {
            if (recipe.getIngredients().size() == 9) {
                if (recipe.getIngredients().get(0).getMatchingStacks().length > 0 && Block.getBlockFromItem(recipe.getRecipeOutput().getItem()) != Blocks.AIR && recipe.getRecipeOutput().getItem() != ItemBlock.getItemFromBlock(Blocks.HAY_BLOCK)) {
                    boolean match = true;
                    for (int i = 1; i < recipe.getIngredients().size(); i++) {
                        if (recipe.getIngredients().get(i).getMatchingStacks().length == 0 || !recipe.getIngredients().get(0).getMatchingStacks()[0].isItemEqual(recipe.getIngredients().get(i).getMatchingStacks()[0])) {
                            match = false;
                            break;
                        }
                    }
                    if (match) {
                        if (GAConfig.GT5U.Remove3x3BlockRecipes)
                            recipesToRemove.add(recipe.getRegistryName());
                        if (GAConfig.GT5U.GenerateCompressorRecipes) {
                            if (RecipeMaps.COMPRESSOR_RECIPES.findRecipe(Integer.MAX_VALUE, Collections.singletonList(new ItemStack(recipe.getIngredients().get(0).getMatchingStacks()[0].getItem(), recipe.getIngredients().size(), recipe.getIngredients().get(0).getMatchingStacks()[0].getMetadata())), Collections.emptyList(), Integer.MAX_VALUE) == null
                                    && RecipeMaps.COMPRESSOR_RECIPES.findRecipe(Integer.MAX_VALUE, Collections.singletonList(OreDictUnifier.getUnificated(new ItemStack(recipe.getIngredients().get(0).getMatchingStacks()[0].getItem(), recipe.getIngredients().size(), recipe.getIngredients().get(0).getMatchingStacks()[0].getMetadata()))), Collections.emptyList(), Integer.MAX_VALUE) == null)
                                if (OreDictUnifier.getUnificated(recipe.getIngredients().get(0).getMatchingStacks()[0]) != ItemStack.EMPTY)
                                    RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder().duration(400).EUt(2).inputs(CountableIngredient.from(OreDictUnifier.getUnificated(recipe.getIngredients().get(0).getMatchingStacks()[0]), recipe.getIngredients().size())).outputs(OreDictUnifier.getUnificated(recipe.getRecipeOutput())).buildAndRegister();
                                else
                                    RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder().duration(400).EUt(2).inputs(CountableIngredient.from(recipe.getIngredients().get(0).getMatchingStacks()[0], recipe.getIngredients().size())).outputs(recipe.getRecipeOutput()).buildAndRegister();
                        }
                    }
                }
            }
            if (recipe.getIngredients().size() == 1 && recipe.getIngredients().get(0).getMatchingStacks().length > 0 && recipe.getRecipeOutput().getCount() == 9 && Block.getBlockFromItem(recipe.getIngredients().get(0).getMatchingStacks()[0].getItem()) != Blocks.AIR && recipe.getIngredients().get(0).getMatchingStacks()[0].getItem() != ItemBlock.getItemFromBlock(Blocks.HAY_BLOCK)) {
                if (GAConfig.GT5U.RemoveBlockUncraftingRecipes)
                    recipesToRemove.add(recipe.getRegistryName());
                if (GAConfig.GT5U.GenerateForgeHammerRecipes && RecipeMaps.FORGE_HAMMER_RECIPES.findRecipe(Integer.MAX_VALUE, Collections.singletonList(new ItemStack(recipe.getIngredients().get(0).getMatchingStacks()[0].getItem(), recipe.getIngredients().size(), recipe.getIngredients().get(0).getMatchingStacks()[0].getMetadata())), Collections.emptyList(), Integer.MAX_VALUE) == null
                        && RecipeMaps.FORGE_HAMMER_RECIPES.findRecipe(Integer.MAX_VALUE, Collections.singletonList(OreDictUnifier.getUnificated(new ItemStack(recipe.getIngredients().get(0).getMatchingStacks()[0].getItem(), recipe.getIngredients().size(), recipe.getIngredients().get(0).getMatchingStacks()[0].getMetadata()))), Collections.emptyList(), Integer.MAX_VALUE) == null)
                    if (OreDictUnifier.getUnificated(recipe.getIngredients().get(0).getMatchingStacks()[0]) != ItemStack.EMPTY)
                        RecipeMaps.FORGE_HAMMER_RECIPES.recipeBuilder().duration(400).EUt(2).inputs(CountableIngredient.from(OreDictUnifier.getUnificated(recipe.getIngredients().get(0).getMatchingStacks()[0]), recipe.getIngredients().size())).outputs(OreDictUnifier.getUnificated(recipe.getRecipeOutput())).buildAndRegister();
                    else
                        RecipeMaps.FORGE_HAMMER_RECIPES.recipeBuilder().duration(400).EUt(2).inputs(recipe.getIngredients().get(0).getMatchingStacks()[0]).outputs(recipe.getRecipeOutput()).buildAndRegister();
            }
            if (recipe.getIngredients().size() == 9) {
                if (recipe.getIngredients().get(0).getMatchingStacks().length > 0 && Block.getBlockFromItem(recipe.getRecipeOutput().getItem()) == Blocks.AIR) {
                    boolean match = true;
                    for (int i = 1; i < recipe.getIngredients().size(); i++) {
                        if (recipe.getIngredients().get(i).getMatchingStacks().length == 0 || !recipe.getIngredients().get(0).getMatchingStacks()[0].isItemEqual(recipe.getIngredients().get(i).getMatchingStacks()[0])) {
                            match = false;
                            break;
                        }
                    }
                    if (match && !recipesToRemove.contains(recipe.getRegistryName()) && GAMetaItems.hasPrefix(recipe.getRecipeOutput(), "dust", "dustTiny") && GAMetaItems.hasPrefix(recipe.getIngredients().get(0).getMatchingStacks()[0], "nugget") && recipe.getRecipeOutput().getCount() == 1 && GAConfig.Misc.Packager3x3Recipes) {
                        if (RecipeMaps.PACKER_RECIPES.findRecipe(Integer.MAX_VALUE, Arrays.asList(new ItemStack(recipe.getIngredients().get(0).getMatchingStacks()[0].getItem(), recipe.getIngredients().size(), recipe.getIngredients().get(0).getMatchingStacks()[0].getMetadata()), (new IntCircuitIngredient(1)).getMatchingStacks()[0]), Collections.emptyList(), Integer.MAX_VALUE) == null
                                && RecipeMaps.PACKER_RECIPES.findRecipe(Integer.MAX_VALUE, Arrays.asList(OreDictUnifier.getUnificated(new ItemStack(recipe.getIngredients().get(0).getMatchingStacks()[0].getItem(), recipe.getIngredients().size(), recipe.getIngredients().get(0).getMatchingStacks()[0].getMetadata())), (new IntCircuitIngredient(1)).getMatchingStacks()[0]), Collections.emptyList(), Integer.MAX_VALUE) == null)
                            if (OreDictUnifier.getUnificated(recipe.getIngredients().get(0).getMatchingStacks()[0]) != ItemStack.EMPTY)
                                RecipeMaps.PACKER_RECIPES.recipeBuilder().duration(10).EUt(12).inputs(CountableIngredient.from(OreDictUnifier.getUnificated(recipe.getIngredients().get(0).getMatchingStacks()[0]), recipe.getIngredients().size())).notConsumable(new IntCircuitIngredient(1)).outputs(OreDictUnifier.getUnificated(recipe.getRecipeOutput())).buildAndRegister();
                            else
                                RecipeMaps.PACKER_RECIPES.recipeBuilder().duration(10).EUt(12).inputs(CountableIngredient.from(recipe.getIngredients().get(0).getMatchingStacks()[0], recipe.getIngredients().size())).notConsumable(new IntCircuitIngredient(1)).outputs(recipe.getRecipeOutput()).buildAndRegister();
                    }
                }
            }
            if (recipe.getIngredients().size() == 1 && recipe.getIngredients().get(0).getMatchingStacks().length > 0 && recipe.getRecipeOutput().getCount() == 9 && Block.getBlockFromItem(recipe.getIngredients().get(0).getMatchingStacks()[0].getItem()) == Blocks.AIR) {
                boolean match = true;
                for (int i = 1; i < recipe.getIngredients().size(); i++) {
                    if (recipe.getIngredients().get(i).getMatchingStacks().length == 0 || !recipe.getIngredients().get(0).getMatchingStacks()[0].isItemEqual(recipe.getIngredients().get(i).getMatchingStacks()[0])) {
                        match = false;
                        break;
                    }
                }
                if (match && !recipesToRemove.contains(recipe.getRegistryName()) && GAMetaItems.hasPrefix(recipe.getIngredients().get(0).getMatchingStacks()[0], "dust", "dustTiny") && GAMetaItems.hasPrefix(recipe.getIngredients().get(0).getMatchingStacks()[0], "ingot") && recipe.getRecipeOutput().getCount() == 9 && GAConfig.Misc.Unpackager3x3Recipes) {
                    if (RecipeMaps.UNPACKER_RECIPES.findRecipe(Integer.MAX_VALUE, Arrays.asList(new ItemStack(recipe.getIngredients().get(0).getMatchingStacks()[0].getItem(), 1, recipe.getIngredients().get(0).getMatchingStacks()[0].getMetadata()), (new IntCircuitIngredient(1)).getMatchingStacks()[0]), Collections.emptyList(), Integer.MAX_VALUE) == null
                            && RecipeMaps.UNPACKER_RECIPES.findRecipe(Integer.MAX_VALUE, Arrays.asList(OreDictUnifier.getUnificated(new ItemStack(recipe.getIngredients().get(0).getMatchingStacks()[0].getItem(), 1, recipe.getIngredients().get(0).getMatchingStacks()[0].getMetadata())), (new IntCircuitIngredient(1)).getMatchingStacks()[0]), Collections.emptyList(), Integer.MAX_VALUE) == null)
                        if (OreDictUnifier.getUnificated(recipe.getIngredients().get(0).getMatchingStacks()[0]) != ItemStack.EMPTY)
                            RecipeMaps.UNPACKER_RECIPES.recipeBuilder().duration(10).EUt(12).inputs(CountableIngredient.from(OreDictUnifier.getUnificated(recipe.getIngredients().get(0).getMatchingStacks()[0]))).notConsumable(new IntCircuitIngredient(1)).outputs(OreDictUnifier.getUnificated(recipe.getRecipeOutput())).buildAndRegister();
                        else
                            RecipeMaps.UNPACKER_RECIPES.recipeBuilder().duration(10).EUt(12).inputs(CountableIngredient.from(recipe.getIngredients().get(0).getMatchingStacks()[0])).notConsumable(new IntCircuitIngredient(1)).outputs(recipe.getRecipeOutput()).buildAndRegister();
                }
            }
            if (recipe.getIngredients().size() == 1 && recipe.getIngredients().get(0).getMatchingStacks().length > 0 && recipe.getRecipeOutput().getCount() == 4) {
                boolean match = true;
                for (int i = 1; i < recipe.getIngredients().size(); i++) {
                    if (recipe.getIngredients().get(i).getMatchingStacks().length == 0 || !recipe.getIngredients().get(0).getMatchingStacks()[0].isItemEqual(recipe.getIngredients().get(i).getMatchingStacks()[0])) {
                        match = false;
                        break;
                    }
                }
                if (match && !recipesToRemove.contains(recipe.getRegistryName()) && GAMetaItems.hasPrefix(recipe.getIngredients().get(0).getMatchingStacks()[0], "dust", "dustSmall") && recipe.getRecipeOutput().getCount() == 1 && GAConfig.Misc.Unpackager2x2Recipes)
                    if (RecipeMaps.UNPACKER_RECIPES.findRecipe(Integer.MAX_VALUE, Arrays.asList(new ItemStack(recipe.getIngredients().get(0).getMatchingStacks()[0].getItem(), recipe.getIngredients().size(), recipe.getIngredients().get(0).getMatchingStacks()[0].getMetadata()), (new IntCircuitIngredient(2)).getMatchingStacks()[0]), Collections.emptyList(), Integer.MAX_VALUE) == null
                            && RecipeMaps.UNPACKER_RECIPES.findRecipe(Integer.MAX_VALUE, Arrays.asList(OreDictUnifier.getUnificated(new ItemStack(recipe.getIngredients().get(0).getMatchingStacks()[0].getItem(), recipe.getIngredients().size(), recipe.getIngredients().get(0).getMatchingStacks()[0].getMetadata())), (new IntCircuitIngredient(2)).getMatchingStacks()[0]), Collections.emptyList(), Integer.MAX_VALUE) == null)
                        if (OreDictUnifier.getUnificated(recipe.getIngredients().get(0).getMatchingStacks()[0]) != ItemStack.EMPTY)
                            RecipeMaps.UNPACKER_RECIPES.recipeBuilder().duration(10).EUt(12).inputs(CountableIngredient.from(OreDictUnifier.getUnificated(recipe.getIngredients().get(0).getMatchingStacks()[0]), recipe.getIngredients().size())).notConsumable(new IntCircuitIngredient(2)).outputs(OreDictUnifier.getUnificated(recipe.getRecipeOutput())).buildAndRegister();
                        else
                            RecipeMaps.UNPACKER_RECIPES.recipeBuilder().duration(10).EUt(12).inputs(CountableIngredient.from(recipe.getIngredients().get(0).getMatchingStacks()[0], recipe.getIngredients().size())).notConsumable(new IntCircuitIngredient(2)).outputs(recipe.getRecipeOutput()).buildAndRegister();
            }
            if (recipe.getIngredients().size() == 4) {
                if (recipe.getIngredients().get(0).getMatchingStacks().length > 0 && Block.getBlockFromItem(recipe.getRecipeOutput().getItem()) != Blocks.QUARTZ_BLOCK && Block.getBlockFromItem(recipe.getRecipeOutput().getItem()) != Blocks.TNT) {
                    boolean match = true;
                    for (int i = 1; i < recipe.getIngredients().size(); i++) {
                        if (recipe.getIngredients().get(i).getMatchingStacks().length == 0 || !recipe.getIngredients().get(0).getMatchingStacks()[0].isItemEqual(recipe.getIngredients().get(i).getMatchingStacks()[0])) {
                            match = false;
                            break;
                        }
                    }
                    if (match && !recipesToRemove.contains(recipe.getRegistryName()) && GAMetaItems.hasPrefix(recipe.getRecipeOutput(), "dust", "dustSmall") && recipe.getRecipeOutput().getCount() == 1 && GAConfig.Misc.Packager2x2Recipes)
                        if (RecipeMaps.PACKER_RECIPES.findRecipe(Integer.MAX_VALUE, Arrays.asList(new ItemStack(recipe.getIngredients().get(0).getMatchingStacks()[0].getItem(), recipe.getIngredients().size(), recipe.getIngredients().get(0).getMatchingStacks()[0].getMetadata()), (new IntCircuitIngredient(2)).getMatchingStacks()[0]), Collections.emptyList(), Integer.MAX_VALUE) == null
                                && RecipeMaps.PACKER_RECIPES.findRecipe(Integer.MAX_VALUE, Arrays.asList(OreDictUnifier.getUnificated(new ItemStack(recipe.getIngredients().get(0).getMatchingStacks()[0].getItem(), recipe.getIngredients().size(), recipe.getIngredients().get(0).getMatchingStacks()[0].getMetadata())), (new IntCircuitIngredient(2)).getMatchingStacks()[0]), Collections.emptyList(), Integer.MAX_VALUE) == null)
                            if (OreDictUnifier.getUnificated(recipe.getIngredients().get(0).getMatchingStacks()[0]) != ItemStack.EMPTY)
                                RecipeMaps.PACKER_RECIPES.recipeBuilder().duration(10).EUt(12).inputs(CountableIngredient.from(OreDictUnifier.getUnificated(recipe.getIngredients().get(0).getMatchingStacks()[0]), recipe.getIngredients().size())).notConsumable(new IntCircuitIngredient(2)).outputs(OreDictUnifier.getUnificated(recipe.getRecipeOutput())).buildAndRegister();
                            else
                                RecipeMaps.PACKER_RECIPES.recipeBuilder().duration(10).EUt(12).inputs(CountableIngredient.from(recipe.getIngredients().get(0).getMatchingStacks()[0], recipe.getIngredients().size())).notConsumable(new IntCircuitIngredient(2)).outputs(recipe.getRecipeOutput()).buildAndRegister();
                }
            }
            //Generate Plank Recipes
            if (!recipe.getRecipeOutput().isEmpty() && !recipesToRemove.contains(recipe.getRegistryName()))
                for (int i : OreDictionary.getOreIDs(recipe.getRecipeOutput())) {
                    if (OreDictionary.getOreName(i).equals("plankWood") && recipe.getIngredients().size() == 1 && recipe.getRecipeOutput().getCount() == 4) {
                        if (GAConfig.GT5U.GeneratedSawingRecipes) {
                            ModHandler.removeRecipeByName(recipe.getRegistryName());
                            ModHandler.addShapelessRecipe("log_to_4_" + recipe.getRecipeOutput(), GTUtility.copyAmount(4, recipe.getRecipeOutput()), recipe.getIngredients().get(0).getMatchingStacks()[0], ToolDictNames.craftingToolSaw);
                            ModHandler.addShapelessRecipe("log_to_2_" + recipe.getRecipeOutput(), GTUtility.copyAmount(2, recipe.getRecipeOutput()), recipe.getIngredients().get(0).getMatchingStacks()[0]);
                        }
                        RecipeMaps.CUTTER_RECIPES.recipeBuilder().duration(200).EUt(8).inputs(recipe.getIngredients().get(0).getMatchingStacks()[0]).fluidInputs(Materials.Lubricant.getFluid(1)).outputs(GTUtility.copyAmount(6, recipe.getRecipeOutput()), OreDictUnifier.get(OrePrefix.dust, Materials.Wood, 2)).buildAndRegister();
                    }
                }
        }


        for (ResourceLocation r : recipesToRemove)
            ModHandler.removeRecipeByName(r);
        recipesToRemove.clear();

        //Disable Wood To Charcoal Recipes
        List<ItemStack> allWoodLogs = OreDictionary.getOres("logWood").stream()
                .flatMap(stack -> ModHandler.getAllSubItems(stack).stream())
                .collect(Collectors.toList());

        for (ItemStack stack : allWoodLogs) {
            ItemStack smeltingOutput = ModHandler.getSmeltingOutput(stack);
            if (!smeltingOutput.isEmpty() && smeltingOutput.getItem() == Items.COAL && smeltingOutput.getMetadata() == 1 && GAConfig.GT5U.DisableLogToCharcoalSmelting) {
                ItemStack woodStack = stack.copy();
                woodStack.setItemDamage(OreDictionary.WILDCARD_VALUE);
                ModHandler.removeFurnaceSmelting(woodStack);
            }
        }

        // Implosion Recipe Fix
        if (GAConfig.Misc.replaceTNTImplosion) {
            Collection<Recipe> implosionRecipes = new ArrayList<>(RecipeMaps.IMPLOSION_RECIPES.getRecipeList());
            for (Recipe recipe : implosionRecipes) {
                List<CountableIngredient> ingredients = recipe.getInputs();
                int explosivesAmount = 0;
                boolean replaceRecipe = false;
                for (Iterator<CountableIngredient> i = ingredients.iterator(); i.hasNext(); ) {
                    CountableIngredient ic = i.next();
                    if (ic.getIngredient().getMatchingStacks()[0].getItem() == ItemBlock.getItemFromBlock(Blocks.TNT)) {
                        explosivesAmount += ic.getCount();
                        i.remove();
                        replaceRecipe = true;
                    }
                }
                if (replaceRecipe) {
                    RecipeMaps.IMPLOSION_RECIPES.removeRecipe(recipe);
                    ingredients.removeIf((ic) -> ic.getIngredient().getMatchingStacks()[0].getItem() == ItemBlock.getItemFromBlock(Blocks.TNT));
                    ImplosionRecipeBuilder builder = RecipeMaps.IMPLOSION_RECIPES.recipeBuilder().EUt(recipe.getEUt())
                            .duration(recipe.getDuration())
                            .explosivesAmount((explosivesAmount == 0) ? 8 : explosivesAmount * 8)
                            .explosivesType(MetaItems.DYNAMITE.getStackForm());
                    for (CountableIngredient ic : ingredients)
                        builder.inputs(ic);
                    builder.outputs(recipe.getOutputs());
                    builder.buildAndRegister();
                }
            }
        }

        // Alloy smelting -> Mixing
        Collection<Recipe> alloySmeltingRecipes = new ArrayList<>(RecipeMaps.ALLOY_SMELTER_RECIPES.getRecipeList());
        for (Recipe recipe : alloySmeltingRecipes) {
            if (recipe.getInputs().size() != 2 || recipe.getOutputs().size() != 1) continue;
            CountableIngredient input1 = recipe.getInputs().get(0);
            CountableIngredient input2 = recipe.getInputs().get(1);
            ItemStack output = recipe.getOutputs().get(0);
            if (OreDictUnifier.getMaterial(input1.getIngredient().getMatchingStacks()[0]) != null
            && OreDictUnifier.getMaterial(input2.getIngredient().getMatchingStacks()[0]) != null
            && OreDictUnifier.getMaterial(output) != null) {
                MaterialStack input1Material = OreDictUnifier.getMaterial(input1.getIngredient().getMatchingStacks()[0]).copy(M * input1.getCount());
                MaterialStack input2Material = OreDictUnifier.getMaterial(input2.getIngredient().getMatchingStacks()[0]).copy(M * input2.getCount());
                MaterialStack outputMaterial = OreDictUnifier.getMaterial(output).copy(M * output.getCount());
                if (input1Material.material instanceof FluidMaterial && input2Material.material instanceof FluidMaterial && outputMaterial.material instanceof FluidMaterial
                        && ((FluidMaterial) input1Material.material).getFluid(1) != null
                        && ((FluidMaterial) input2Material.material).getFluid(1) != null
                        && ((FluidMaterial) outputMaterial.material).getFluid(1) != null) {
                    if(getMixerRecipe(input1Material, input2Material) == null) {
                        int fluid1Amount = (int) (input1Material.amount * L / M);
                        int fluid2Amount = (int) (input2Material.amount * L / M);
                        RecipeMaps.MIXER_RECIPES.recipeBuilder()
                                .duration(((fluid1Amount + fluid2Amount)/L)*25)
                                .EUt(recipe.getEUt())
                                .fluidInputs(((FluidMaterial) input1Material.material).getFluid(fluid1Amount),
                                        ((FluidMaterial) input2Material.material).getFluid(fluid2Amount))
                                .fluidOutputs(((FluidMaterial) outputMaterial.material).getFluid((int) (outputMaterial.amount * L / M)))
                                .buildAndRegister();
                    }
                    if (GAConfig.Misc.disableAlloySmelterAlloying)
                        RecipeMaps.ALLOY_SMELTER_RECIPES.removeRecipe(recipe);
                }
            }
        }
    }

    @Nullable
    private static Recipe getMixerRecipe(@Nonnull MaterialStack mat1, @Nonnull MaterialStack mat2) {
        return RecipeMaps.MIXER_RECIPES.findRecipe(Integer.MAX_VALUE,
                Collections.emptyList(),
                Arrays.asList(
                        ((FluidMaterial) mat1.material).getFluid((int) (mat1.amount * L / M)),
                        ((FluidMaterial) mat2.material).getFluid((int) (mat2.amount * L / M))
                ),
                Integer.MAX_VALUE
        );
    }

    public static void recipeCeramicsIntegration() {
        ModHandler.addShapelessRecipe("unfired_porcelain_brick_shapeless", GAMetaItems.UNFIRED_PORCELAIN_BRICK.getStackForm(), "clayPorcelain", MetaItems.WOODEN_FORM_BRICK);
        ModHandler.addShapedRecipe("unfired_porcelain_brick", GAMetaItems.UNFIRED_PORCELAIN_BRICK.getStackForm(8), "PPP", "PFP", "PPP", 'P', "clayPorcelain", 'F', MetaItems.WOODEN_FORM_BRICK);

        ModHandler.addShapelessRecipe("unfired_porcelain_plate_shapeless", GAMetaItems.UNFIRED_PORCELAIN_PLATE.getStackForm(), 'h', "clayPorcelain");
        ModHandler.addShapedRecipe("unfired_porcelain_plate", GAMetaItems.UNFIRED_PORCELAIN_PLATE.getStackForm(), "P", "P", 'P', "clayPorcelain");

        RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder().input("clayPorcelain", 1).notConsumable(MetaItems.SHAPE_MOLD_INGOT).output(OrePrefix.ingot, GAMaterials.Porcelain).duration(200).EUt(2).buildAndRegister();
        RecipeMaps.MACERATOR_RECIPES.recipeBuilder().input(OrePrefix.ingot, GAMaterials.Porcelain).output(OrePrefix.dust, GAMaterials.Porcelain).duration(30).EUt(8).buildAndRegister();
        ModHandler.removeRecipeByName(new ResourceLocation("ceramics:decoration/unfired_porcelain_quartz"));
        ModHandler.removeRecipeByName(new ResourceLocation("ceramics:decoration/unfired_porcelain_bone_meal"));
        ModHandler.addShapedRecipe("unfired_porcelain_clump", OreDictUnifier.get("clayPorcelain"), " C ", " B ", " m ", 'C', "clay", 'B', new UnificationEntry(OrePrefix.dust, Materials.Bone));

        ModHandler.removeRecipeByName(new ResourceLocation("ceramics:armor/unfired_clay_plate"));
        ModHandler.addShapedRecipe("clay_plate", OreDictUnifier.get(OrePrefix.plate, Materials.Clay), "C", "C", 'C', "clay");

        ModHandler.removeRecipeByName(new ResourceLocation("ceramics:uncrafting/clay_plate"));
        ModHandler.addShapelessRecipe("clay_plate_gt", OreDictUnifier.get(OrePrefix.plate, Materials.Clay), 'h', "clay");
        ModHandler.removeRecipes(new ItemStack(Ceramics.clayUnfired, 1, ItemClayUnfired.UnfiredType.BUCKET.getMeta()));
        ModHandler.addShapedRecipe("clay_bucket", new ItemStack(Ceramics.clayUnfired, 1, ItemClayUnfired.UnfiredType.BUCKET.getMeta()), "   ", "P P", " P ", 'P', new UnificationEntry(OrePrefix.plate, Materials.Clay));
        ModHandler.removeRecipes(new ItemStack(Ceramics.clayUnfired, 1, ItemClayUnfired.UnfiredType.SHEARS.getMeta()));
        ModHandler.addShapedRecipe("clay_shears", new ItemStack(Ceramics.clayUnfired, 1, ItemClayUnfired.UnfiredType.SHEARS.getMeta()), "hP", "Pf", 'P', new UnificationEntry(OrePrefix.plate, Materials.Clay));
        ModHandler.addShapelessRecipe("porcelain_to_dust", OreDictUnifier.get(OrePrefix.dust, GAMaterials.Porcelain), 'm', "clayPorcelain");

        RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder().input("clayPorcelain", 4).notConsumable(MetaItems.SHAPE_MOLD_BLOCK).output(OrePrefix.block, GAMaterials.Porcelain).EUt(2).duration(600).buildAndRegister();
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
            RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder().duration(60).EUt(8).input(OrePrefix.ingot, m, 2).notConsumable(MetaItems.SHAPE_MOLD_INGOT).output(OrePrefix.plate, m).buildAndRegister();
        }

        if (GAConfig.GT6.BendingCurvedPlates) {
            ModHandler.removeRecipes(new ItemStack(Ceramics.clayHelmetRaw));
            ModHandler.removeRecipes(new ItemStack(Ceramics.clayChestplateRaw));
            ModHandler.removeRecipes(new ItemStack(Ceramics.clayLeggingsRaw));
            ModHandler.removeRecipes(new ItemStack(Ceramics.clayBootsRaw));

            ModHandler.addShapedRecipe("clay_helmet", new ItemStack(Ceramics.clayHelmetRaw), "PPP", "ChC", 'P', new UnificationEntry(OrePrefix.plate, Materials.Clay), 'C', OreDictUnifier.get(OrePrefix.valueOf("plateCurved"), Materials.Clay));
            ModHandler.addShapedRecipe("clay_chestplate", new ItemStack(Ceramics.clayChestplateRaw), "PhP", "CPC", "CPC", 'P', new UnificationEntry(OrePrefix.plate, Materials.Clay), 'C', OreDictUnifier.get(OrePrefix.valueOf("plateCurved"), Materials.Clay));
            ModHandler.addShapedRecipe("clay_leggings", new ItemStack(Ceramics.clayLeggingsRaw), "PCP", "ChC", "C C", 'P', new UnificationEntry(OrePrefix.plate, Materials.Clay), 'C', OreDictUnifier.get(OrePrefix.valueOf("plateCurved"), Materials.Clay));
            ModHandler.addShapedRecipe("clay_boots", new ItemStack(Ceramics.clayBootsRaw), "P P", "ChC", 'P', new UnificationEntry(OrePrefix.plate, Materials.Clay), 'C', OreDictUnifier.get(OrePrefix.valueOf("plateCurved"), Materials.Clay));

            ModHandler.removeRecipes(new ItemStack(Ceramics.clayHelmet));
            ModHandler.removeRecipes(new ItemStack(Ceramics.clayChestplate));
            ModHandler.removeRecipes(new ItemStack(Ceramics.clayLeggings));
            ModHandler.removeRecipes(new ItemStack(Ceramics.clayBoots));
        }
        if (!GAConfig.GT6.PlateDoubleIngot) {
            ModHandler.addShapedRecipe("porcelain_plate", OreDictUnifier.get(OrePrefix.plate, GAMaterials.Porcelain), "h", "I", "I", 'I', new UnificationEntry(OrePrefix.ingot, GAMaterials.Porcelain));
        }
        ModHandler.removeRecipes(new ItemStack(Ceramics.clayUnfired, 1, ItemClayUnfired.UnfiredType.FAUCET.getMeta()));
        ModHandler.addShapedRecipe("unfired_faucet", new ItemStack(Ceramics.clayUnfired, 1, ItemClayUnfired.UnfiredType.FAUCET.getMeta()), "   ", "ChC", " P ", 'C', "clayPorcelain", 'P', GAMetaItems.UNFIRED_PORCELAIN_PLATE.getStackForm());
        ModHandler.removeRecipes(new ItemStack(Ceramics.clayUnfired, 3, ItemClayUnfired.UnfiredType.CHANNEL.getMeta()));
        ModHandler.addShapedRecipe("unfired_channel", new ItemStack(Ceramics.clayUnfired, 3, ItemClayUnfired.UnfiredType.CHANNEL.getMeta()), "   ", "ChC", "PPP", 'C', "clayPorcelain", 'P', GAMetaItems.UNFIRED_PORCELAIN_PLATE.getStackForm());

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
            ModHandler.removeRecipes(new ItemStack(Ceramics.porcelain, 8, i));
            ModHandler.removeRecipes(new ItemStack(Ceramics.clayBarrelStained, 8, i));
            ModHandler.removeRecipes(new ItemStack(Ceramics.clayBarrelStained, 1, i));
            ModHandler.removeRecipes(new ItemStack(Ceramics.clayBarrelStainedExtension, 8, i));
            ModHandler.removeRecipes(new ItemStack(Ceramics.clayBarrelStainedExtension, 1, i));
            ModHandler.removeRecipes(new ItemStack(Ceramics.porcelainBarrel, 8, i));
            ModHandler.removeRecipes(new ItemStack(Ceramics.porcelainBarrel, 1, i));
            ModHandler.removeRecipes(new ItemStack(Ceramics.porcelainBarrelExtension, 8, i));
            ModHandler.removeRecipes(new ItemStack(Ceramics.porcelainBarrelExtension, 1, i));
            if (!color.toString().equals("White")) {
                ModHandler.addShapedRecipe("porcelain_" + color.toString().toLowerCase(), new ItemStack(Ceramics.porcelain, 8, i), "WWW", "WDW", "WWW", 'W', new UnificationEntry(OrePrefix.block, GAMaterials.Porcelain), 'D', "dye" + color);
                ModHandler.addShapelessRecipe("porcelain_shapeless_" + color.toString().toLowerCase(), new ItemStack(Ceramics.porcelain, 1, i), new UnificationEntry(OrePrefix.block, GAMaterials.Porcelain), "dye" + color);
                ModHandler.addShapedRecipe("porcelain_barrel_" + color.toString().toLowerCase(), new ItemStack(Ceramics.porcelainBarrel, 8, i), "WWW", "WDW", "WWW", 'W', new ItemStack(Ceramics.porcelainBarrel), 'D', "dye" + color);
                ModHandler.addShapelessRecipe("porcelain_barrel_shapeless_" + color.toString().toLowerCase(), new ItemStack(Ceramics.porcelainBarrel, 1, i), new ItemStack(Ceramics.porcelainBarrel), "dye" + color);
                ModHandler.addShapedRecipe("porcelain_barrel_extension_" + color.toString().toLowerCase(), new ItemStack(Ceramics.porcelainBarrelExtension, 8, i), "WWW", "WDW", "WWW", 'W', new ItemStack(Ceramics.porcelainBarrelExtension), 'D', "dye" + color);
                ModHandler.addShapelessRecipe("porcelain_barrel_extension_shapeless_" + color.toString().toLowerCase(), new ItemStack(Ceramics.porcelainBarrelExtension, 1, i), new ItemStack(Ceramics.porcelainBarrelExtension), "dye" + color);
            }
            ModHandler.addShapedRecipe("brick_barrel_" + color.toString().toLowerCase(), new ItemStack(Ceramics.clayBarrelStained, 8, i), "WWW", "WDW", "WWW", 'W', new ItemStack(Ceramics.clayBarrel), 'D', "dye" + color);
            ModHandler.addShapelessRecipe("brick_barrel_shapeless_" + color.toString().toLowerCase(), new ItemStack(Ceramics.clayBarrelStained, 1, i), new ItemStack(Ceramics.clayBarrel), "dye" + color);
            ModHandler.addShapedRecipe("brick_barrel_extension_" + color.toString().toLowerCase(), new ItemStack(Ceramics.clayBarrelStainedExtension, 8, i), "WWW", "WDW", "WWW", 'W', new ItemStack(Ceramics.clayBarrel, 1, 1), 'D', "dye" + color);
            ModHandler.addShapelessRecipe("brick_barrel_extension_shapeless_" + color.toString().toLowerCase(), new ItemStack(Ceramics.clayBarrelStainedExtension, 1, i), new ItemStack(Ceramics.clayBarrel, 1, 1), "dye" + color);
        }
    }

    public static void initCeramicsIntegration() {
        ModHandler.removeFurnaceSmelting(new ItemStack(Ceramics.clayUnfired, 1, ItemClayUnfired.UnfiredType.PORCELAIN.getMeta()));
        ModHandler.removeFurnaceSmelting(new ItemStack(Ceramics.clayUnfired, 1, ItemClayUnfired.UnfiredType.CLAY_PLATE_RAW.getMeta()));
        GameRegistry.addSmelting(GAMetaItems.UNFIRED_PORCELAIN_BRICK.getStackForm(), OreDictUnifier.get(OrePrefix.ingot, GAMaterials.Porcelain), 0.1f);
        GameRegistry.addSmelting(new ItemStack(Ceramics.clayHelmetRaw), new ItemStack(Ceramics.clayHelmet), 0.1f);
        GameRegistry.addSmelting(new ItemStack(Ceramics.clayChestplateRaw), new ItemStack(Ceramics.clayChestplate), 0.1f);
        GameRegistry.addSmelting(new ItemStack(Ceramics.clayLeggingsRaw), new ItemStack(Ceramics.clayLeggings), 0.1f);
        GameRegistry.addSmelting(new ItemStack(Ceramics.clayBootsRaw), new ItemStack(Ceramics.clayBoots), 0.1f);
        GameRegistry.addSmelting(OreDictUnifier.get(OrePrefix.dust, GAMaterials.Porcelain), OreDictUnifier.get(OrePrefix.ingot, GAMaterials.Porcelain), 0.1f);
        ModHandler.removeFurnaceSmelting(new ItemStack(Ceramics.claySoft));
        GameRegistry.addSmelting(new ItemStack(Ceramics.claySoft), OreDictUnifier.get(OrePrefix.block, GAMaterials.Porcelain), 0.1f);
        GameRegistry.addSmelting(GAMetaItems.UNFIRED_PORCELAIN_PLATE.getStackForm(), OreDictUnifier.get(OrePrefix.plate, GAMaterials.Porcelain), 0.1f);
    }
}
