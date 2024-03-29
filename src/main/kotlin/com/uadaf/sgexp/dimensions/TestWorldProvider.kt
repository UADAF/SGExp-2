package com.uadaf.sgexp.dimensions

import com.uadaf.sgexp.dimensions.data.DimensionStorage
import com.uadaf.sgexp.dimensions.data.features.DimFeatureBiome
import com.uadaf.sgexp.dimensions.data.features.DimFeatureEvaporation
import com.uadaf.sgexp.dimensions.data.features.DimFeatureRain
import com.uadaf.sgexp.dimensions.data.features.DimFeatureSkyColor
import com.uadaf.sgexp.registry.WorldRegistry
import net.minecraft.entity.Entity
import net.minecraft.util.math.Vec3d
import net.minecraft.world.WorldProvider
import net.minecraft.world.biome.BiomeProvider
import net.minecraft.world.biome.BiomeProviderSingle
import net.minecraft.world.chunk.Chunk
import net.minecraftforge.common.DimensionManager

class TestWorldProvider : WorldProvider() {

    val desc by lazy { DimensionStorage.getDimensionStorage(DimensionManager.getWorld(0)).dims[dimension]!! }

    override fun getDimensionType() = WorldRegistry.testDimType

    override fun canDoRainSnowIce(chunk: Chunk) = desc.get<DimFeatureRain>().canRain

    override fun doesWaterVaporize() = desc.get<DimFeatureEvaporation>().doesWaterEvaporate

    override fun getBiomeProvider(): BiomeProvider {
        val feature = desc.get<DimFeatureBiome>()
        return BiomeProviderSingle(feature.biome)
    }

    override fun getSkyColor(cameraEntity: Entity, partialTicks: Float): Vec3d {
        val c = desc.get<DimFeatureSkyColor>().color
        val b = c and 0xFF
        val g = (c shr 8) and 0xFF
        val r = (c shr 16) and 0xFF
        return Vec3d(r / 255.0, g / 255.0, b / 255.0)
    }

}