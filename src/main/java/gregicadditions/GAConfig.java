package gregicadditions;

import net.minecraftforge.common.config.Config;

@Config(modid = GregicAdditions.MODID)
public class GAConfig {

    @Config.Comment("Config options for GT6 features")
    public static GT6 GT6 = new GT6();
    @Config.Comment("Config options for GT5U features")
    public static GT5U GT5U = new GT5U();
    @Config.Comment("Config options for miscellaneous features")
    public static Misc Misc = new Misc();

    public static class GT6 {
        @Config.Comment("Bending Recipes (disabling Bending Cylinders' recipes disables all of them)")
        @Config.Name("Bending - Bending Cylinders' recipes")
        public boolean BendingCylinders = true;
        @Config.Name("Bending - Curved Plates' recipes")
        public boolean BendingCurvedPlates = true;
        @Config.Name("Bending - Rotors require Curved Plates")
        public boolean BendingRotors = true;
        @Config.Name("Bending - Rings are crafted with Bending Cylinders")
        public boolean BendingRings = true;
        @Config.Name("Bending - Foils are made with Bending Cylinders")
        public boolean BendingFoils = true;
        @Config.Name("Bending - Foils are automated in the Cluster Mill instead of the Bending Machine")
        public boolean BendingFoilsAutomatic = true;
        @Config.Name("Bending - Pipes are crafted with Curved Plates")
        public boolean BendingPipes = true;

        @Config.Comment("Set this to false to disable Plates being crafted from Double Ingots")
        @Config.Name("Plates are crafted from Double Ingots")
        public boolean PlateDoubleIngot = true;

        @Config.Comment("Set this to false to enable the GT5 Wrench recipes")
        @Config.Name("Wrenches are crafted with Plates instead of Ingots")
        public boolean ExpensiveWrenches = true;

        @Config.Comment("Set this to false to disable Drums")
        @Config.Name("Should Drums be registered?")
        public boolean registerDrums = true;

        @Config.Comment("Set this to false to disable support for Forestry Electron Tubes")
        @Config.Name("Should Electrodes be registered?")
        public boolean electrodes = true;
    }

    public static class GT5U {
        @Config.Comment("Set to false to disable GT5U Cable isolation recipes")
        @Config.Name("Cables can be isolated with different combinations of Rubbers and Dusts with varying efficiencies")
        public boolean CablesGT5U = true;

        @Config.Comment("Set these to false to disable the generated Compressor recipes for blocks")
        @Config.Name("Compression - Generate Compressor recipes for blocks")
        public boolean GenerateCompressorRecipes = true;
        @Config.Name("Compression - Generate Forge Hammer recipes for uncompressing blocks")
        public boolean GenerateForgeHammerRecipes = true;
        @Config.Name("Compression - Remove 3x3 crafting recipes for blocks")
        public boolean Remove3x3BlockRecipes = true;
        @Config.Name("Compression - Remove crafting recipes for uncompressing blocks")
        public boolean RemoveBlockUncraftingRecipes = true;

        @Config.Comment("Set to false to enable Log>Charcoal smelting recipes")
        @Config.Name("All Log to Charcoal smelting recipes will be removed")
        public boolean DisableLogToCharcoalSmelting = true;

        @Config.Comment("Set to false to disable generated wood sawing recipes")
        @Config.Name("A saw is required to get 4 Planks per Log")
        public boolean GeneratedSawingRecipes = true;

        @Config.Comment("Set these to false to disable higher tier versions of machines")
        @Config.Name("Should higher tier Alloy Smelters be registered?")
        public boolean highTierAlloySmelter = true;
        @Config.Name("Should higher tier Arc Furnaces be registered?")
        public boolean highTierArcFurnaces = true;
        @Config.Name("Should higher tier Assembling Machines be registered?")
        public boolean highTierAssemblers = true;
        @Config.Name("Should higher tier Autoclaves be registered?")
        public boolean highTierAutoclaves = true;
        @Config.Name("Should higher tier Bending Machines be registered?")
        public boolean highTierBenders = true;
        @Config.Name("Should higher tier Breweries be registered?")
        public boolean highTierBreweries = true;
        @Config.Name("Should higher tier Canning Machines be registered?")
        public boolean highTierCanners = true;
        @Config.Name("Should higher tier Centrifuges be registered?")
        public boolean highTierCentrifuges = true;
        @Config.Name("Should higher tier Chemical Baths be registered?")
        public boolean highTierChemicalBaths = true;
        @Config.Name("Should higher tier Chemical Reactors be registered?")
        public boolean highTierChemicalReactors = true;
        @Config.Name("Should higher tier Circuit Assembling Machines be registered?")
        public boolean highTierCircuitAssemblers = true;
        @Config.Name("Should higher tier Compressors be registered?")
        public boolean highTierCompressors = true;
        @Config.Name("Should higher tier Cutting Machines be registered?")
        public boolean highTierCutters = true;
        @Config.Name("Should higher tier Cluster Mills be registered?")
        public boolean highTierClusterMills = true;
        @Config.Name("Should higher tier Distilleries be registered?")
        public boolean highTierDistilleries = true;
        @Config.Name("Should higher tier Electric Furnaces be registered?")
        public boolean highTierElectricFurnace = true;
        @Config.Name("Should higher tier Electrolyzers be registered?")
        public boolean highTierElectrolyzers = true;
        @Config.Name("Should higher tier Electromagnetic Separators be registered?")
        public boolean highTierElectromagneticSeparators = true;
        @Config.Name("Should higher tier Extractors be registered?")
        public boolean highTierExtractors = true;
        @Config.Name("Should higher tier Extruders be registered?")
        public boolean highTierExtruders = true;
        @Config.Name("Should higher tier Fermenters be registered?")
        public boolean highTierFermenters = true;
        @Config.Name("Should higher tier Fishers be registered?")
        public boolean highTierFishers = true;
        @Config.Name("Should higher tier Fluid Canners be registered?")
        public boolean highTierFluidCanners = true;
        @Config.Name("Should higher tier Fluid Extractors be registered?")
        public boolean highTierFluidExtractors = true;
        @Config.Name("Should higher tier Fluid Heaters be registered?")
        public boolean highTierFluidHeaters = true;
        @Config.Name("Should higher tier Fluid Heaters be registered?")
        public boolean highTierFluidSolidifiers = true;
        @Config.Name("Should higher tier Forge Hammers be registered?")
        public boolean highTierForgeHammers = true;
        @Config.Name("Should higher tier Forming Presses be registered?")
        public boolean highTierFormingPresses = true;
        @Config.Name("Should higher tier Lathes be registered?")
        public boolean highTierLathes = true;
        @Config.Name("Should higher tier Microwaves be registered?")
        public boolean highTierMicrowaves = true;
        @Config.Name("Should higher tier Mixers be registered?")
        public boolean highTierMixers = true;
        @Config.Name("Should higher tier Ore Washers be registered?")
        public boolean highTierOreWashers = true;
        @Config.Name("Should higher tier Packagers be registered?")
        public boolean highTierPackers = true;
        @Config.Name("Should higher tier Plasma Arc Furnaces be registered?")
        public boolean highTierPlasmaArcFurnaces = true;
        @Config.Name("Should higher tier Polarizers be registered?")
        public boolean highTierPolarizers = true;
        @Config.Name("Should higher tier Precision Laser Engravers be registered?")
        public boolean highTierLaserEngravers = true;
        @Config.Name("Should higher tier Pumps be registered?")
        public boolean highTierPumps = true;
        @Config.Name("Should higher tier Rock Breakers be registered?")
        public boolean highTierRockBreakers = true;
        @Config.Name("Should higher tier Replicators be registered?")
        public boolean highTierReplicators = true;
        @Config.Name("Should higher tier Sifting Machines be registered?")
        public boolean highTierSifters = true;
        @Config.Name("Should higher tier Thermal Centrifuges be registered?")
        public boolean highTierThermalCentrifuges = true;
        @Config.Name("Should higher tier Macerators be registered?")
        public boolean highTierMacerators = true;
        @Config.Name("Should higher tier Mass Fabricators be registered?")
        public boolean highTierMassFabricators = true;
        @Config.Name("Should higher tier Unpackagers be registered?")
        public boolean highTierUnpackers = true;
        @Config.Name("Should higher tier Wiremills be registered?")
        public boolean highTierWiremills = true;
        @Config.Name("Should higher tier Block Breakers be registered?")
        public boolean highTierBlockBreakers = true;
        @Config.Name("Should higher tier Item Collectors be registered?")
        public boolean highTierItemCollectors = true;
        @Config.Name("Should higher tier Quantum Chests be registered?")
        public boolean highTierQuantumChests = true;
        @Config.Name("Should higher tier Quantum Tanks be registered?")
        public boolean highTierQuantumTanks = true;

        @Config.Comment("Set this to false to disable enhanced piston recipes")
        @Config.Name("Enhanced piston recipes")
        public boolean enhancedPistonRecipes = true;

        @Config.Comment("Set these to true to enable certain Batteries.")
        @Config.Name("Batteries - Enable an extra ZPM and UV Battery (this also makes the Ultimate Battery harder to make)")
        public boolean enableZPMAndUVBats = false;
        @Config.Name("Batteries - Replace the Ultimate Battery with a MAX Battery")
        public boolean replaceUVWithMAXBat = false;
    }

    public static class Misc {
        @Config.Comment("Set these to false to disable the generated Packager and Unpackager recipes")
        @Config.Name("Packaging - 1x1 recipes with 9 outputs can be automated with the Unpackager")
        public boolean Unpackager3x3Recipes = true;
        @Config.Name("Packaging - 1x1 recipes with 4 outputs can be automated with the Unpackager")
        public boolean Unpackager2x2Recipes = true;
        @Config.Name("Packaging - 3x3 recipes can automated with Packagers")
        public boolean Packager3x3Recipes = true;
        @Config.Name("Packaging - 2x2 recipes can automated with Packagers")
        public boolean Packager2x2Recipes = true;

        @Config.Comment("Set this to false to disable converting TNT to Dynamite in the Implosion Compressor")
        @Config.Name("Replace TNT in Implosion Compressor")
        public boolean replaceTNTImplosion = true;

        @Config.Comment("Set this to false to disable extended fluid extractor recipes for most versions of items")
        @Config.Name("Fluid extractor can extract fluid from a lot more variations")
        public boolean fluidExtractDust = true;

        @Config.Comment("Set this to true to disable the Alloy Smelter alloying.\nUseful if using TConstruct's smeltery.")
        @Config.Name("Disable alloy smelter alloying")
        public boolean disableAlloySmelterAlloying = false;

        @Config.Comment("Set this to false to disable harder recipes for slabs")
        @Config.Name("Slabs require saws")
        public boolean harderSlabs = true;

        @Config.Comment("Set this to false to disable Forestry Integration")
        @Config.Name("Adds recipes for Forestry's Electrodes")
        public boolean ForestryIntegration = true;

        @Config.Comment("Set this to false to disable Ceramics Integration")
        @Config.Name("Enhanced Integration for Ceramics mod")
        public boolean CeramicsIntegration = true;

        @Config.Comment("Set this to false to disable high tier Air Collectors")
        @Config.Name("Air Collector have IV and LuV version")
        public boolean highTierCollector = true;

        @Config.Comment("Set this to false to disable replacement of GTCE's Coke Oven")
        @Config.Name("Use enhanced Coke Oven which supports automation")
        public boolean cokeOvenEnable = true;

        @Config.Comment("Set this to false to disable input buses for the enhanced Coke Oven." +
                "\nOnly takes effect if replacement of GTCE's Coke Oven is enabled")
        @Config.Name("Enable input bus for enhanced Coke Oven")
        public boolean cokeOvenInputBusEnable = true;
    }
}
