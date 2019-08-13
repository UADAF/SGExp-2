package com.uadaf.sgexp.dimensions.data.features

import net.minecraft.init.Biomes
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.biome.Biome
import kotlin.random.Random

class DimFeatureBiome : DimFeatureBase {


    lateinit var biome: Biome
        private set

    override val type = DimFeatureType.BIOME

    override fun NBTTagCompound.internalWriteToNBT() {
        setInteger("biome", Biome.getIdForBiome(biome))
    }

    override fun NBTTagCompound.internalReadFromNBT() {
        biome = Biome.getBiome(getInteger("biome"))!!
    }

    override fun fillRandom(rand: Random) {
        val availableBiomes = listOf(Biomes.DESERT, Biomes.DEEP_OCEAN, Biomes.PLAINS, Biomes.TAIGA)
        biome = availableBiomes.random(rand)
    }

    override fun toString() = "biome=${biome.biomeName}"

}