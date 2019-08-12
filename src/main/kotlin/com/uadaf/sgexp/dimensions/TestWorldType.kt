package com.uadaf.sgexp.dimensions

import com.uadaf.sgexp.SGExp
import net.minecraft.init.Biomes
import net.minecraft.world.World
import net.minecraft.world.WorldType
import net.minecraft.world.biome.BiomeProvider
import net.minecraft.world.biome.BiomeProviderSingle
import net.minecraft.world.gen.ChunkGeneratorOverworld
import net.minecraft.world.gen.IChunkGenerator

class TestWorldType : WorldType("testWorld") {

    override fun canBeCreated() = true

    override fun getBiomeProvider(world: World) = BiomeProviderSingle(Biomes.MUTATED_DESERT)

    override fun getChunkGenerator(world: World, generatorOptions: String) =
            ChunkGeneratorOverworld(world, world.seed, false, generatorOptions)
}