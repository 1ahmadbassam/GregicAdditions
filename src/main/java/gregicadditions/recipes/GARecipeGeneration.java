package gregicadditions.recipes;

import gregicadditions.GAConfig;
import gregicadditions.GAUtils;
import gregicadditions.item.GAMetaItems;
import gregtech.api.items.ToolDictNames;
import gregtech.api.recipes.CountableIngredient;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.builders.ImplosionRecipeBuilder;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.FluidMaterial;
import gregtech.api.unification.material.type.IngotMaterial;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import gregtech.common.items.MetaItems;
import gregtech.loaders.recipe.RecyclingRecipes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.LanguageMap;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.ByteArrayInputStream;
import java.util.*;
import java.util.stream.Collectors;

import static gregtech.api.GTValues.L;
import static gregtech.api.GTValues.M;

public class GARecipeGeneration {
    private static final String shapeSmelterTranslations = "recipemap.alloy_smelter.name=Shape Smelter" +
            "\ngregtech.machine.steam_alloy_smelter_bronze.name=Steam Shape Smelter" +
            "\ngregtech.machine.steam_alloy_smelter_steel.name=High Pressure Shape Smelter" +
            "\ngregtech.machine.alloy_smelter.lv.name=Basic Shape Smelter" +
            "\ngregtech.machine.alloy_smelter.mv.name=Advanced Shape Smelter" +
            "\ngregtech.machine.alloy_smelter.hv.name=Advanced Shape Smelter II" +
            "\ngregtech.machine.alloy_smelter.ev.name=Advanced Shape Smelter III" +
            "\ngtadditions.machine.alloy_smelter.iv.name=Advanced Shape Smelter IV" +
            "\ngtadditions.machine.alloy_smelter.luv.name=Advanced Shape Smelter V" +
            "\ngtadditions.machine.alloy_smelter.zpm.name=Advanced Shape Smelter VI" +
            "\ngtadditions.machine.alloy_smelter.uv.name=Advanced Shape Smelter VII";

    public static void init() {
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
            if (recipe.getIngredients().size() == 9 || recipe.getIngredients().size() == 8) {
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
            if (recipe.getIngredients().size() == 1 && recipe.getIngredients().get(0).getMatchingStacks().length > 0 && (recipe.getRecipeOutput().getCount() == 9 || recipe.getRecipeOutput().getCount() == 8) && Block.getBlockFromItem(recipe.getIngredients().get(0).getMatchingStacks()[0].getItem()) == Blocks.AIR) {
                if (!recipesToRemove.contains(recipe.getRegistryName()) && GAMetaItems.hasPrefix(recipe.getIngredients().get(0).getMatchingStacks()[0], "dust", "dustTiny") && GAMetaItems.hasPrefix(recipe.getIngredients().get(0).getMatchingStacks()[0], "ingot") && GAConfig.Misc.Unpackager3x3Recipes) {
                    if (RecipeMaps.UNPACKER_RECIPES.findRecipe(Integer.MAX_VALUE, Arrays.asList(new ItemStack(recipe.getIngredients().get(0).getMatchingStacks()[0].getItem(), 1, recipe.getIngredients().get(0).getMatchingStacks()[0].getMetadata()), (new IntCircuitIngredient(1)).getMatchingStacks()[0]), Collections.emptyList(), Integer.MAX_VALUE) == null
                            && RecipeMaps.UNPACKER_RECIPES.findRecipe(Integer.MAX_VALUE, Arrays.asList(OreDictUnifier.getUnificated(new ItemStack(recipe.getIngredients().get(0).getMatchingStacks()[0].getItem(), 1, recipe.getIngredients().get(0).getMatchingStacks()[0].getMetadata())), (new IntCircuitIngredient(1)).getMatchingStacks()[0]), Collections.emptyList(), Integer.MAX_VALUE) == null)
                        if (OreDictUnifier.getUnificated(recipe.getIngredients().get(0).getMatchingStacks()[0]) != ItemStack.EMPTY)
                            RecipeMaps.UNPACKER_RECIPES.recipeBuilder().duration(10).EUt(12).inputs(CountableIngredient.from(OreDictUnifier.getUnificated(recipe.getIngredients().get(0).getMatchingStacks()[0]))).notConsumable(new IntCircuitIngredient(1)).outputs(OreDictUnifier.getUnificated(recipe.getRecipeOutput())).buildAndRegister();
                        else
                            RecipeMaps.UNPACKER_RECIPES.recipeBuilder().duration(10).EUt(12).inputs(CountableIngredient.from(recipe.getIngredients().get(0).getMatchingStacks()[0])).notConsumable(new IntCircuitIngredient(1)).outputs(recipe.getRecipeOutput()).buildAndRegister();
                }
            }
            if (recipe.getIngredients().size() == 1 && recipe.getIngredients().get(0).getMatchingStacks().length > 0 && recipe.getRecipeOutput().getCount() == 4) {
                if (!recipesToRemove.contains(recipe.getRegistryName()) && GAMetaItems.hasPrefix(recipe.getIngredients().get(0).getMatchingStacks()[0], "dust", "dustSmall") && GAConfig.Misc.Unpackager2x2Recipes)
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
            if (!recipe.getRecipeOutput().isEmpty() && !recipesToRemove.contains(recipe.getRegistryName())) {
                for (int i : OreDictionary.getOreIDs(recipe.getRecipeOutput())) {
                    if (OreDictionary.getOreName(i).equals("plankWood") && recipe.getIngredients().size() == 1 && recipe.getRecipeOutput().getCount() == 4) {
                        if (GAConfig.GT5U.GeneratedSawingRecipes) {
                            recipesToRemove.add(recipe.getRegistryName());
                            ModHandler.addShapelessRecipe("log_to_4_" + recipe.getRecipeOutput(), GTUtility.copyAmount(4, recipe.getRecipeOutput()), recipe.getIngredients().get(0).getMatchingStacks()[0], ToolDictNames.craftingToolSaw);
                            ModHandler.addShapelessRecipe("log_to_2_" + recipe.getRecipeOutput(), GTUtility.copyAmount(2, recipe.getRecipeOutput()), recipe.getIngredients().get(0).getMatchingStacks()[0]);
                        }
                    }
                }
            }
            if (Block.getBlockFromItem(recipe.getRecipeOutput().getItem()) instanceof BlockSlab
                    && !recipe.getIngredients().isEmpty() && recipe.getIngredients().get(0).getMatchingStacks().length > 0
                    && recipe.getRecipeOutput().getCount() > 1
                    && !recipesToRemove.contains(recipe.getRegistryName())
                    && recipe.getRegistryName() != null
                    && !Objects.equals(recipe.getRegistryName().getNamespace(),"gtadditions")) {
                if (GAConfig.Misc.harderSlabs) {
                    recipesToRemove.add(recipe.getRegistryName());
                    ModHandler.addShapedRecipe("plank_to_3_" + recipe.getRecipeOutput(), GTUtility.copyAmount(3, recipe.getRecipeOutput()), "SSS", 'S', recipe.getIngredients().get(0).getMatchingStacks()[0]);
                    ModHandler.addShapedRecipe("plank_to_6_" + recipe.getRecipeOutput(), GTUtility.copyAmount(6, recipe.getRecipeOutput()), "SSS", " s ", 'S', recipe.getIngredients().get(0).getMatchingStacks()[0]);
                }
                boolean match = RecipeMaps.CUTTER_RECIPES.findRecipe(Integer.MAX_VALUE, Collections.singletonList(recipe.getIngredients().get(0).getMatchingStacks()[0]), Collections.singletonList(Materials.Lubricant.getFluid(1)), Integer.MAX_VALUE) == null;
                if (RecipeMaps.CUTTER_RECIPES.findRecipe(Integer.MAX_VALUE, Collections.singletonList(GTUtility.copyAmount(3, recipe.getIngredients().get(0).getMatchingStacks()[0])), Collections.singletonList(Materials.Lubricant.getFluid(1)), Integer.MAX_VALUE) != null)
                    match = false;
                if (match) {
                    for (MaterialStack lube : GAUtils.sawLubricants) {
                        int duration = lube.amount == 4L ? 50 : lube.amount == 3L ? 32 : 12;
                        RecipeMaps.CUTTER_RECIPES.recipeBuilder().EUt(8).duration(duration).fluidInputs(((FluidMaterial) lube.material).getFluid((int) lube.amount)).inputs(new CountableIngredient(recipe.getIngredients().get(0), 1)).outputs(GTUtility.copyAmount(2, recipe.getRecipeOutput())).buildAndRegister();
                    }
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
                ItemStack woodStack = GTUtility.copy(stack);
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
            if (input1.getIngredient().getMatchingStacks().length > 0
                    && OreDictUnifier.getMaterial(input1.getIngredient().getMatchingStacks()[0]) != null
                    && input2.getIngredient().getMatchingStacks().length > 0
                    && OreDictUnifier.getMaterial(input2.getIngredient().getMatchingStacks()[0]) != null
                    && OreDictUnifier.getMaterial(output) != null) {
                MaterialStack input1Material = OreDictUnifier.getMaterial(input1.getIngredient().getMatchingStacks()[0]).copy(M * input1.getCount());
                MaterialStack input2Material = OreDictUnifier.getMaterial(input2.getIngredient().getMatchingStacks()[0]).copy(M * input2.getCount());
                MaterialStack outputMaterial = OreDictUnifier.getMaterial(output).copy(M * output.getCount());
                if (input1Material.material instanceof FluidMaterial && input2Material.material instanceof FluidMaterial && outputMaterial.material instanceof FluidMaterial
                        && ((FluidMaterial) input1Material.material).getMaterialFluid() != null
                        && ((FluidMaterial) input2Material.material).getMaterialFluid() != null
                        && ((FluidMaterial) outputMaterial.material).getMaterialFluid() != null
                        && (input1Material.material != input2Material.material)
                        && (input1Material.material != outputMaterial.material)
                        && (input2Material.material != outputMaterial.material)) {
                    if (getMixerRecipe(input1Material, input2Material) == null) {
                        int fluid1Amount = (int) (input1Material.amount * L / M);
                        int fluid2Amount = (int) (input2Material.amount * L / M);
                        RecipeMaps.MIXER_RECIPES.recipeBuilder()
                                .duration(((fluid1Amount + fluid2Amount) / L) * 25)
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

        if (GAConfig.Misc.fluidExtractDust) {
            for (Material mat : Material.MATERIAL_REGISTRY) {
                if (mat != Materials.Glass && mat != Materials.Ice && mat instanceof FluidMaterial && ((FluidMaterial) mat).getMaterialFluid() != null) {
                    for (OrePrefix prefix : OrePrefix.values()) {
                        if (!OreDictUnifier.get(prefix, mat).isEmpty()
                                && !(GAUtils.blacklistPrefix.contains(prefix))
                                && !(mat instanceof IngotMaterial && ((IngotMaterial) mat).blastFurnaceTemperature > 0 && GAUtils.blastPrefix.contains(prefix))
                                && prefix.getMaterialAmount(mat) >= M / 9) {
                            int voltageMultiplier = 1;
                            if (mat instanceof IngotMaterial) {
                                int blastFurnaceTemperature = ((IngotMaterial) mat).blastFurnaceTemperature;
                                voltageMultiplier = blastFurnaceTemperature == 0 ? 1 : blastFurnaceTemperature > 2000 ? 16 : 4;
                            }
                            if (RecipeMaps.FLUID_EXTRACTION_RECIPES.findRecipe(Integer.MAX_VALUE, Collections.singletonList(OreDictUnifier.get(prefix, mat)), Collections.emptyList(), Integer.MAX_VALUE) == null)
                                RecipeMaps.FLUID_EXTRACTION_RECIPES.recipeBuilder()
                                        .input(prefix, mat)
                                        .fluidOutputs(((FluidMaterial) mat).getFluid((int) (prefix.getMaterialAmount(mat) * L / M)))
                                        .duration((int) Math.max(1L, prefix.getMaterialAmount(mat) * 80 / M))
                                        .EUt(32 * voltageMultiplier)
                                        .buildAndRegister();
                        }
                    }
                }
            }
            if ((GAConfig.Misc.CeramicsIntegration && Loader.isModLoaded("ceramics")) || (Loader.isModLoaded("gtconstruct")))
                RecipeMaps.FLUID_EXTRACTION_RECIPES.recipeBuilder()
                        .input("clay", 1)
                        .fluidOutputs(Materials.Clay.getFluid(144))
                        .duration(80)
                        .EUt(32)
                        .buildAndRegister();
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

    public static void loadAlternateTranslations() {
        if (GAConfig.Misc.disableAlloySmelterAlloying) {
            LanguageMap.inject(new ByteArrayInputStream(shapeSmelterTranslations.getBytes()));
        }
    }

    public static void registerExtraComponents() {
        for (Material m : Material.MATERIAL_REGISTRY) {
            if (!OreDictUnifier.get(OrePrefix.ring, m).isEmpty() && !OreDictUnifier.get(OrePrefix.stick, m).isEmpty() && m != Materials.Rubber && m != Materials.StyreneButadieneRubber && m != Materials.SiliconeRubber && GAConfig.GT6.BendingRings && GAConfig.GT6.BendingCylinders) {
                ModHandler.removeRecipes(OreDictUnifier.get(OrePrefix.ring, m));
                ModHandler.addShapedRecipe("stick_to_ring_" + m, OreDictUnifier.get(OrePrefix.ring, m), "hS", " C", 'S', OreDictUnifier.get(OrePrefix.stick, m), 'C', "craftingToolBendingCylinderSmall");
            }
            if (!OreDictUnifier.get(OrePrefix.valueOf("plateCurved"), m).isEmpty() && GAConfig.GT6.BendingCurvedPlates && GAConfig.GT6.BendingCylinders) {
                if (m.compareTo(Materials.Diamond) != 0) {
                    ModHandler.addShapedRecipe("curved_plate_" + m, OreDictUnifier.get(OrePrefix.valueOf("plateCurved"), m), "h", "P", "C", 'P', new UnificationEntry(OrePrefix.plate, m), 'C', "craftingToolBendingCylinder");
                    RecipeMaps.BENDER_RECIPES.recipeBuilder().EUt(24).duration((int) (m.getAverageMass() * 1.5)).input(OrePrefix.plate, m).circuitMeta(0).output(OrePrefix.valueOf("plateCurved"), m).buildAndRegister();
                } else {
                    RecipeMaps.BENDER_RECIPES.recipeBuilder().EUt(64).duration(496).input(OrePrefix.plate, m).circuitMeta(0).output(OrePrefix.valueOf("plateCurved"), m).buildAndRegister();
                }
            }
            if (!OreDictUnifier.get(OrePrefix.rotor, m).isEmpty() && GAConfig.GT6.BendingRotors && GAConfig.GT6.BendingCylinders) {
                ModHandler.removeRecipes(OreDictUnifier.get(OrePrefix.rotor, m));
                ModHandler.addShapedRecipe("ga_rotor_" + m, OreDictUnifier.get(OrePrefix.rotor, m), "ChC", "SRf", "CdC", 'C', new UnificationEntry(OrePrefix.valueOf("plateCurved"), m), 'S', OreDictUnifier.get(OrePrefix.screw, m), 'R', OreDictUnifier.get(OrePrefix.ring, m));
                RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(240).EUt(24).inputs(OreDictUnifier.get(OrePrefix.valueOf("plateCurved"), m, 4), OreDictUnifier.get(OrePrefix.ring, m)).fluidInputs(Materials.SolderingAlloy.getFluid(32)).outputs(OreDictUnifier.get(OrePrefix.rotor, m)).buildAndRegister();
            }
            if (!OreDictUnifier.get(OrePrefix.foil, m).isEmpty()) {
                if (GAConfig.GT6.BendingFoils && GAConfig.GT6.BendingCylinders) {
                    ModHandler.addShapedRecipe("foil_" + m, OreDictUnifier.get(OrePrefix.foil, m, 2), "hPC", 'P', new UnificationEntry(OrePrefix.plate, m), 'C', "craftingToolBendingCylinder");
                }
                if (GAConfig.GT6.BendingFoilsAutomatic && GAConfig.GT6.BendingCylinders) {
                    GARecipeMaps.CLUSTER_MILL_RECIPES.recipeBuilder().EUt(24).duration((int) m.getAverageMass()).input(OrePrefix.plate, m).outputs(OreDictUnifier.get(OrePrefix.foil, m, 4)).buildAndRegister();
                } else {
                    RecipeMaps.BENDER_RECIPES.recipeBuilder().EUt(24).duration((int) m.getAverageMass()).circuitMeta(4).input(OrePrefix.plate, m).outputs(OreDictUnifier.get(OrePrefix.foil, m, 4)).buildAndRegister();
                }
            }

            if (!OreDictUnifier.get(OrePrefix.valueOf("round"), m).isEmpty()) {
                ModHandler.addShapedRecipe("round" + m, OreDictUnifier.get(OrePrefix.valueOf("round"), m), "fN", "N ", 'N', OreDictUnifier.get(OrePrefix.nugget, m));
                RecipeMaps.LATHE_RECIPES.recipeBuilder().EUt(8).duration((int) m.getAverageMass()).inputs(OreDictUnifier.get(OrePrefix.nugget, m)).output(OrePrefix.valueOf("round"), m).buildAndRegister();
                RecipeMaps.EXTRUDER_RECIPES.recipeBuilder().input(OrePrefix.nugget, m).notConsumable(MetaItems.SHAPE_EXTRUDER_ROD).output(OrePrefix.valueOf("round"), m).duration((int) m.getAverageMass()).EUt(6 * getVoltageMultiplier(m)).buildAndRegister();
            }

            //Cables
            if (m instanceof IngotMaterial && !OreDictUnifier.get(OrePrefix.cableGtSingle, m).isEmpty() && m != Materials.RedAlloy && m != Materials.Cobalt && m != Materials.Zinc && m != Materials.SolderingAlloy && m != Materials.Tin && m != Materials.Lead && GAConfig.GT5U.CablesGT5U) {
                for (MaterialStack stackFluid : GAUtils.cableFluids) {
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
                        for (MaterialStack stackDust : GAUtils.cableDusts) {
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
                        for (MaterialStack stackDust : GAUtils.cableDusts) {
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
                RecipeMaps.BENDER_RECIPES.recipeBuilder().circuitMeta(1).input(OrePrefix.ingot, m, 2).output(OrePrefix.valueOf("ingotDouble"), m).EUt(24).duration((int) (m.getAverageMass()) * 2).buildAndRegister();
                ModHandler.addShapedRecipe("double_ingot_to_plate_" + m, OreDictUnifier.get(OrePrefix.plate, m), "h", "I", 'I', new UnificationEntry(OrePrefix.valueOf("ingotDouble"), m));
            }

            if (!OreDictUnifier.get(OrePrefix.valueOf("plateCurved"), m).isEmpty() && GAConfig.GT6.BendingCurvedPlates && GAConfig.GT6.BendingCylinders && m.compareTo(Materials.Diamond) != 0) {
                ModHandler.addShapedRecipe("flatten_plate_" + m, OreDictUnifier.get(OrePrefix.plate, m), "h", "C", 'C', new UnificationEntry(OrePrefix.valueOf("plateCurved"), m));
            }

            //Pipes
            if (!OreDictUnifier.get(OrePrefix.pipeMedium, m).isEmpty() && GAConfig.GT6.BendingPipes) {
                ModHandler.removeRecipeByName(new ResourceLocation("gregtech:small_" + m + "_pipe"));
                ModHandler.removeRecipeByName(new ResourceLocation("gregtech:medium_" + m + "_pipe"));
                ModHandler.removeRecipeByName(new ResourceLocation("gregtech:large_" + m + "_pipe"));
                if (!OreDictUnifier.get(OrePrefix.valueOf("plateCurved"), m).isEmpty()) {
                    ModHandler.addShapedRecipe("pipe_ga_" + m, OreDictUnifier.get(OrePrefix.pipeMedium, m, 2), "PPP", "wCh", "PPP", 'P', new UnificationEntry(OrePrefix.valueOf("plateCurved"), m), 'C', "craftingToolBendingCylinder");
                    ModHandler.addShapedRecipe("pipe_ga_large_" + m, OreDictUnifier.get(OrePrefix.pipeLarge, m), "PhP", "PCP", "PwP", 'P', new UnificationEntry(OrePrefix.valueOf("plateCurved"), m), 'C', "craftingToolBendingCylinder");
                    ModHandler.addShapedRecipe("pipe_ga_small_" + m, OreDictUnifier.get(OrePrefix.pipeSmall, m, 4), "PwP", "PCP", "PhP", 'P', new UnificationEntry(OrePrefix.valueOf("plateCurved"), m), 'C', "craftingToolBendingCylinder");
                }
            }
        }
    }

    public static void registerRecyclingRecipes() {
        for (Material m : Material.MATERIAL_REGISTRY) {
            if (!OreDictUnifier.get(OrePrefix.valueOf("plateCurved"), m).isEmpty()) {
                ArrayList<MaterialStack> materialStacks = new ArrayList<>();
                materialStacks.add(new MaterialStack(m, M));
                ItemStack input = OreDictUnifier.get(OrePrefix.valueOf("plateCurved"), m);
                RecyclingRecipes.registerArcRecyclingRecipe(b -> b.inputs(input), materialStacks, false);
            }
            if (!OreDictUnifier.get(OrePrefix.valueOf("ingotDouble"), m).isEmpty()) {
                ArrayList<MaterialStack> materialStacks = new ArrayList<>();
                materialStacks.add(new MaterialStack(m, M * 2));
                ItemStack input = OreDictUnifier.get(OrePrefix.valueOf("ingotDouble"), m);
                RecyclingRecipes.registerArcRecyclingRecipe(b -> b.inputs(input), materialStacks, false);
            }
            if (!OreDictUnifier.get(OrePrefix.valueOf("round"), m).isEmpty()) {
                ArrayList<MaterialStack> materialStacks = new ArrayList<>();
                materialStacks.add(new MaterialStack(m, M / 9));
                ItemStack input = OreDictUnifier.get(OrePrefix.valueOf("round"), m);
                RecyclingRecipes.registerArcRecyclingRecipe(b -> b.inputs(input), materialStacks, false);
            }
        }
    }

    private static int getVoltageMultiplier(Material material) {
        return material instanceof IngotMaterial && ((IngotMaterial) material)
                .blastFurnaceTemperature >= 2800 ? 32 : 8;
    }
}
