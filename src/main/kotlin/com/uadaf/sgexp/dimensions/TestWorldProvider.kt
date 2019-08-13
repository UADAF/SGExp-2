package com.uadaf.sgexp.dimensions

import com.uadaf.sgexp.SGExp
import com.uadaf.sgexp.dimensions.data.DimensionStorage
import com.uadaf.sgexp.registry.WorldRegistry
import net.minecraft.init.Biomes
import net.minecraft.world.DimensionType
import net.minecraft.world.WorldProvider
import net.minecraft.world.biome.BiomeProvider
import net.minecraft.world.biome.BiomeProviderSingle
import net.minecraft.world.chunk.Chunk
import net.minecraftforge.common.DimensionManager

class TestWorldProvider : WorldProvider() {

    override fun getDimensionType() = WorldRegistry.testDimType


    override fun canDoRainSnowIce(chunk: Chunk) = false

    override fun doesWaterVaporize() = true

    override fun getBiomeProvider(): BiomeProvider {
        val storage = DimensionStorage.getDimensionStorage(DimensionManager.getWorld(0))
        val desc = storage.dims[dimension]
        return BiomeProviderSingle(desc!!.biome)
    }

}