package com.uadaf.sgexp.dimensions

import com.uadaf.sgexp.SGExp
import com.uadaf.sgexp.registry.WorldRegistry
import net.minecraft.init.Biomes
import net.minecraft.world.DimensionType
import net.minecraft.world.WorldProvider
import net.minecraft.world.biome.BiomeProvider
import net.minecraft.world.biome.BiomeProviderSingle
import net.minecraft.world.chunk.Chunk

class TestWorldProvider : WorldProvider() {

    override fun getDimensionType() = WorldRegistry.testDimType


    override fun canDoRainSnowIce(chunk: Chunk) = false

    override fun doesWaterVaporize() = true

    override fun getBiomeProvider(): BiomeProvider {
        return BiomeProviderSingle(Biomes.MUTATED_DESERT)
    }

}