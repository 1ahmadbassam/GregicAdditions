package gregicadditions.jei;

import gregicadditions.machines.GATileEntities;
import gregicadditions.recipes.GARecipeMaps;
import gregtech.integration.jei.multiblock.MultiblockInfoPage;
import gregtech.integration.jei.multiblock.MultiblockInfoRecipeWrapper;
import gregtech.integration.jei.recipe.primitive.CokeOvenRecipeWrapper;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@JEIPlugin
public class JEIGAPlugin implements IModPlugin {
    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new CokeOvenRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
    }
    @Override
    public void register(IModRegistry registry) {
        List<MultiblockInfoPage> multiblocks = Arrays.asList(
                new CokeOvenInfo(),
                new AssemblyLineInfo(),
                new FusionReactor1Info(),
                new FusionReactor2Info(),
                new FusionReactor3Info());
        registry.addRecipes(multiblocks.stream()
                        .map(MultiblockInfoRecipeWrapper::new)
                        .collect(Collectors.toList()),
                "gregtech:multiblock_info");
        registry.addRecipes(GARecipeMaps.COKE_OVEN_RECIPES.stream()
                .map(CokeOvenRecipeWrapper::new)
                .collect(Collectors.toList()), "gregtech:ga_coke_oven");
        registry.addRecipeCatalyst(GATileEntities.COKE_OVEN.getStackForm(), "gregtech:ga_coke_oven");
        for (MultiblockInfoPage multiblock : multiblocks) {
            registry.addIngredientInfo(multiblock.getController().getStackForm(),
                    VanillaTypes.ITEM,
                    multiblock.getDescription());
        }
    }
}
