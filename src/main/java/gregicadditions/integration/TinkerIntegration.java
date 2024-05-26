package gregicadditions.integration;

import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import knightminer.ceramics.Ceramics;
import knightminer.ceramics.items.ItemClayUnfired;
import knightminer.tcomplement.shared.CommonsModule;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.events.TinkerRegisterEvent;
import slimeknights.tconstruct.library.smeltery.CastingRecipe;
import slimeknights.tconstruct.shared.TinkerFluids;

public class TinkerIntegration {
    public static void init() {
        TinkerRegistry.registerTableCasting(new ItemStack(Ceramics.clayUnfired), CommonsModule.castBucket, TinkerFluids.clay, 432);
        TinkerRegistry.registerTableCasting(new CastingRecipe(new ItemStack(Ceramics.clayUnfired), RecipeMatch.of(CommonsModule.castBucketClay), TinkerFluids.clay, 432, true, false));
    }

    public static class CeramicsTinkerIntegrationBus {
        @SubscribeEvent(priority = EventPriority.HIGH)
        public void castingRemoval(TinkerRegisterEvent.TableCastingRegisterEvent event) {
            if (event.getRecipe().matches(GameRegistry.makeItemStack("tconstruct:cast_custom", 3, 1, null), TinkerFluids.clay)) {
                ItemStack out = event.getRecipe().getResult(GameRegistry.makeItemStack("tconstruct:cast_custom", 3, 1, null), TinkerFluids.clay);
                ItemStack plate = new ItemStack(Ceramics.clayUnfired, 1, ItemClayUnfired.UnfiredType.CLAY_PLATE.getMeta());
                if (out.getItem() == plate.getItem() && out.getMetadata() == plate.getMetadata())
                    event.setCanceled(true);
            }
            if (Loader.isModLoaded("tcomplement")) {
                if (event.getRecipe().matches(GameRegistry.makeItemStack("tcomplement:cast", 0, 1, null), TinkerFluids.clay)) {
                    ItemStack out = event.getRecipe().getResult(GameRegistry.makeItemStack("tcomplement:cast", 0, 1, null), TinkerFluids.clay);
                    ItemStack bucket = new ItemStack(Ceramics.clayBucket);
                    if (out.getItem() == bucket.getItem() && out.getMetadata() == bucket.getMetadata())
                        event.setCanceled(true);
                }
                if (event.getRecipe().matches(GameRegistry.makeItemStack("tcomplement:cast_clay", 0, 1, null), TinkerFluids.clay)) {
                    ItemStack out = event.getRecipe().getResult(GameRegistry.makeItemStack("tcomplement:cast_clay", 0, 1, null), TinkerFluids.clay);
                    ItemStack bucket = new ItemStack(Ceramics.clayBucket);
                    if (out.getItem() == bucket.getItem() && out.getMetadata() == bucket.getMetadata())
                        event.setCanceled(true);
                }
            }
        }

        @SubscribeEvent(priority = EventPriority.HIGH)
        public void smeltingRemoval(TinkerRegisterEvent.MeltingRegisterEvent event) {
            if (event.getRecipe().getResult().amount == 288 && (event.getRecipe().matches(new ItemStack(Ceramics.clayUnfired, 1, ItemClayUnfired.UnfiredType.CLAY_PLATE_RAW.getMeta()))
                    || event.getRecipe().matches(OreDictUnifier.get(OrePrefix.plate, Materials.Clay))))
                event.setCanceled(true);
        }
    }
}
