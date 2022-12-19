package og.kel.snow;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.MiscConfiguredFeatures;
import net.minecraft.world.gen.feature.MiscPlacedFeatures;

public class Snow implements ModInitializer {
    @Override
    public void onInitialize() {
        BiomeModifications.create(new Identifier("snow", "add_freeze_top")).add(ModificationPhase.ADDITIONS, biomeSelectionContext -> (BiomeSelectors.foundInOverworld().test(biomeSelectionContext)) && (biomeSelectionContext.getBiome().getPrecipitation() != Biome.Precipitation.NONE) && (biomeSelectionContext.getBiome().getDownfall() > 0.0f) && !biomeSelectionContext.hasFeature(MiscConfiguredFeatures.FREEZE_TOP_LAYER), context -> context.getGenerationSettings().addFeature(GenerationStep.Feature.TOP_LAYER_MODIFICATION, MiscPlacedFeatures.FREEZE_TOP_LAYER));
        BiomeModifications.create(new Identifier("snow", "snow_in_overworld")).add(ModificationPhase.POST_PROCESSING, biomeSelectionContext -> (BiomeSelectors.foundInOverworld().test(biomeSelectionContext)) && (biomeSelectionContext.getBiome().getPrecipitation() != Biome.Precipitation.NONE) && (biomeSelectionContext.getBiome().getDownfall() > 0.0f), context -> {
            context.getWeather().setPrecipitation(Biome.Precipitation.SNOW);
            context.getWeather().setTemperature(0.0f);
            context.getWeather().setTemperatureModifier(Biome.TemperatureModifier.FROZEN);
        });
    }
}
