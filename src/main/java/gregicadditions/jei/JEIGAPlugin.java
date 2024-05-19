package gregicadditions.jei;

import gregicadditions.machines.GATileEntities;
import gregicadditions.recipes.GARecipeMaps;
import gregtech.integration.jei.multiblock.MultiblockInfoRecipeWrapper;
import gregtech.integration.jei.recipe.primitive.CokeOvenRecipeWrapper;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;

import java.util.Arrays;
import java.util.stream.Collectors;

@JEIPlugin
public class JEIGAPlugin implements IModPlugin {
    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new CokeOvenRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
    }
    @Override
    public void register(IModRegistry registry) {
        registry.addRecipes(Arrays.asList(
                new MultiblockInfoRecipeWrapper(new CokeOvenInfo()),
                new MultiblockInfoRecipeWrapper(new AssemblyLineInfo()),
                new MultiblockInfoRecipeWrapper(new FusionReactor1Info()),
                new MultiblockInfoRecipeWrapper(new FusionReactor2Info()),
                new MultiblockInfoRecipeWrapper(new FusionReactor3Info())),
                "gregtech:multiblock_info");
        registry.addRecipes(GARecipeMaps.COKE_OVEN_RECIPES.stream()
                .map(CokeOvenRecipeWrapper::new)
                .collect(Collectors.toList()), "gregtech:ga_coke_oven");
        registry.addRecipeCatalyst(GATileEntities.COKE_OVEN.getStackForm(), "gregtech:ga_coke_oven");
    }
}
