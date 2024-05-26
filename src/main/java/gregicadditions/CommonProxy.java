package gregicadditions;

import gregicadditions.integration.CeramicsIntegration;
import gregicadditions.integration.ForestryIntegration;
import gregicadditions.integration.TinkerIntegration;
import gregicadditions.item.GAMetaBlocks;
import gregicadditions.item.GAMetaItems;
import gregicadditions.recipes.*;
import gregtech.api.unification.material.type.GemMaterial;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.blocks.VariantItemBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.function.Function;

@Mod.EventBusSubscriber(modid = GregicAdditions.MODID)
public class CommonProxy {
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> registry = event.getRegistry();

        registry.register(GAMetaBlocks.MUTLIBLOCK_CASING);
        registry.register(GAMetaBlocks.TRANSPARENT_CASING);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();

        registry.register(createItemBlock(GAMetaBlocks.MUTLIBLOCK_CASING, VariantItemBlock::new));
        registry.register(createItemBlock(GAMetaBlocks.TRANSPARENT_CASING, VariantItemBlock::new));
    }

    private static <T extends Block> ItemBlock createItemBlock(T block, Function<T, ItemBlock> producer) {
        ItemBlock itemBlock = producer.apply(block);
        if (block.getRegistryName() != null)
            itemBlock.setRegistryName(block.getRegistryName());
        return itemBlock;
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void runEarlyMaterialHandlers(RegistryEvent.Register<IRecipe> event) {
        // Hook into GTCE's processing handlers, to automatically generate rod recipes for gems
        OrePrefix.gem.addProcessingHandler(GemMaterial.class, GAMaterials::processGem);
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        GAMetaItems.registerOreDict();
        GAMetaItems.registerRecipes();
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerRecipesLate(RegistryEvent.Register<IRecipe> event) {
        // Initialize ore dictionary entries, removals can occur later no problem
        GARecipeAddition.oreDictInit();
        GAMachineRecipeRemoval.init();
        GARecipeGeneration.registerExtraComponents();
        GARecipeGeneration.registerRecyclingRecipes();
        GARecipeAddition.init();
        GARecipeAddition.init2();
        GARecipeAddition.registerCokeOvenRecipes();
        if (Loader.isModLoaded("forestry") && GAConfig.GT6.electrodes)
            ForestryIntegration.recipes();
        if (Loader.isModLoaded("ceramics") && GAConfig.Misc.CeramicsIntegration) {
            CeramicsIntegration.oreDictInit();
            CeramicsIntegration.recipes();
            if (Loader.isModLoaded("tconstruct") && Loader.isModLoaded("tcomplement"))
                TinkerIntegration.init();
        }
        MatterReplication.init();
        MachineCraftingRecipes.init();
        GeneratorFuels.init();
    }

    public void preInit() {
        GARecipeGeneration.loadAlternateTranslations();
        if (GAConfig.Misc.CeramicsIntegration && Loader.isModLoaded("tconstruct") && Loader.isModLoaded("ceramics"))
            MinecraftForge.EVENT_BUS.register(new TinkerIntegration.CeramicsTinkerIntegrationBus());
    }

    public void init() {
        GARecipeAddition.oreDictInit();
        if (Loader.isModLoaded("ceramics") && GAConfig.Misc.CeramicsIntegration) {
            CeramicsIntegration.oreDictInit();
            CeramicsIntegration.init();
        }
    }

    public void postInit() {
        // Late loaders for generation of recipes
        GARecipeGeneration.init();
    }
}
