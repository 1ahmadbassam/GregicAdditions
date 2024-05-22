package gregicadditions.recipes;

import gregicadditions.GAConfig;
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
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.util.GTUtility;
import gregtech.common.items.MetaItems;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.LanguageMap;
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
}
