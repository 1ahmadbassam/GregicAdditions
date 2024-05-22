package gregicadditions.integration.jei;

import gregicadditions.GAConfig;
import gregicadditions.item.GAMetaBlocks;
import gregicadditions.item.GAMultiblockCasing;
import gregicadditions.machines.GATileEntities;
import gregicadditions.recipes.GARecipeMaps;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.unification.material.Materials;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.integration.jei.multiblock.MultiblockInfoCategory;
import gregtech.integration.jei.multiblock.MultiblockInfoPage;
import gregtech.integration.jei.multiblock.MultiblockInfoRecipeWrapper;
import gregtech.integration.jei.recipe.primitive.CokeOvenRecipeWrapper;
import knightminer.ceramics.Ceramics;
import mezz.jei.api.*;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@JEIPlugin
public class JEIGAPlugin implements IModPlugin {
    public static final Map<String, MultiblockInfoRecipeWrapper> multiblockRecipes = new HashMap<>();

    @Override
    public void onRuntimeAvailable(@Nonnull IJeiRuntime jeiRuntime) {
        // Hide GTCE coke oven multiblock and category
        // Unfortunately, the info for the block cannot be hidden without reflection
        // since JEI does not provide us with an API to do so (and reflection is overpowered
        // for such a trivial thing)
        if (GAConfig.Misc.cokeOvenEnable) {
            String multiblockUID = "gregtech:multiblock_info";
            IRecipeRegistry registry = jeiRuntime.getRecipeRegistry();
            IRecipeWrapper cokeOvenRecipe = registry.getRecipeWrapper(MultiblockInfoCategory.multiblockRecipes.get("coke_oven"), multiblockUID);
            if (cokeOvenRecipe != null)
                registry.hideRecipe(cokeOvenRecipe, multiblockUID);
            registry.hideRecipeCategory("gregtech:coke_oven");
        }
    }

    @Override
    public void registerCategories(@Nonnull IRecipeCategoryRegistration registry) {
        if (GAConfig.Misc.cokeOvenEnable)
            registry.addRecipeCategories(new CokeOvenRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void register(@Nonnull IModRegistry registry) {
        if (GAConfig.Misc.cokeOvenEnable)
            multiblockRecipes.put("ga_coke_oven", new MultiblockInfoRecipeWrapper(new CokeOvenInfo()));
        multiblockRecipes.put("ga_assembly_line", new MultiblockInfoRecipeWrapper(new AssemblyLineInfo()));
        multiblockRecipes.put("ga_fusion_reactor1", new MultiblockInfoRecipeWrapper(new FusionReactor1Info()));
        multiblockRecipes.put("ga_fusion_reactor2", new MultiblockInfoRecipeWrapper(new FusionReactor2Info()));
        multiblockRecipes.put("ga_fusion_reactor3", new MultiblockInfoRecipeWrapper(new FusionReactor3Info()));
        registry.addRecipes(multiblockRecipes.values(),
                "gregtech:multiblock_info");
        for (MetaTileEntity machine : GATileEntities.FLUID_CANNER) {
            registry.addIngredientInfo(machine.getStackForm(), VanillaTypes.ITEM,
                    "gregtech.machine.fluid_canner.jei_description");
        }
        if (GAConfig.Misc.cokeOvenEnable) {
            registry.addRecipes(GARecipeMaps.COKE_OVEN_RECIPES.stream()
                    .map(CokeOvenRecipeWrapper::new)
                    .collect(Collectors.toList()), "gregtech:ga_coke_oven");
            registry.addRecipeCatalyst(GATileEntities.COKE_OVEN.getStackForm(), "gregtech:ga_coke_oven");
        }
        for (MultiblockInfoPage multiblock : multiblockRecipes.values().stream().map(MultiblockInfoRecipeWrapper::getInfoPage).collect(Collectors.toList())) {
            registry.addIngredientInfo(multiblock.getController().getStackForm(),
                    VanillaTypes.ITEM,
                    multiblock.getDescription());
        }
        IIngredientBlacklist blacklist = registry.getJeiHelpers().getIngredientBlacklist();
        if (GAConfig.Misc.cokeOvenEnable) {
            blacklist.addIngredientToBlacklist(MetaTileEntities.COKE_OVEN.getStackForm());
            blacklist.addIngredientToBlacklist(MetaTileEntities.COKE_OVEN_HATCH.getStackForm());
            blacklist.addIngredientToBlacklist(MetaBlocks.METAL_CASING.getItemVariant(BlockMetalCasing.MetalCasingType.COKE_BRICKS));
        } else
            blacklist.addIngredientToBlacklist(GAMetaBlocks.MUTLIBLOCK_CASING.getItemVariant(GAMultiblockCasing.CasingType.COKE_OVEN_BRICKS));
        for (int i = 0; i < MetaTileEntities.AMPLIFABRICATOR.length; i++)
            blacklist.addIngredientToBlacklist(MetaTileEntities.AMPLIFABRICATOR[i].getStackForm());
        if (Materials.UUAmplifier.getFluid(1) != null)
            blacklist.addIngredientToBlacklist(Materials.UUAmplifier.getFluid(1));
        blacklist.addIngredientToBlacklist(MetaTileEntities.MAGIC_ENERGY_ABSORBER.getStackForm());
        if (GAConfig.Misc.CeramicsIntegration && Loader.isModLoaded("ceramics")) {
            blacklist.removeIngredientFromBlacklist(new ItemStack(Ceramics.clayHelmetRaw, 1));
            blacklist.removeIngredientFromBlacklist(new ItemStack(Ceramics.clayChestplateRaw, 1));
            blacklist.removeIngredientFromBlacklist(new ItemStack(Ceramics.clayLeggingsRaw, 1));
            blacklist.removeIngredientFromBlacklist(new ItemStack(Ceramics.clayBootsRaw, 1));
        }
    }
}
