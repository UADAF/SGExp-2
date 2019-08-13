package com.uadaf.sgexp.dimensions.data

import com.uadaf.sgexp.R
import net.minecraft.init.Biomes
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.biome.Biome

class DimensionDescription {

    var biome: Biome
        private set
    init {
        biome = availableBiomes.random()
        R.logger.info("Creating dimension with $biome")
    }

    fun writeToNBT(nbt: NBTTagCompound) = nbt.apply {
        setInteger("biome", Biome.getIdForBiome(biome))
    }

    fun toNBT() = writeToNBT(NBTTagCompound())

    fun readFromNBT(nbt: NBTTagCompound) = with(nbt) {
        biome = Biome.getBiome(getInteger("biome"))!!
    }

    companion object {
        val availableBiomes = listOf(Biomes.DESERT, Biomes.DEEP_OCEAN, Biomes.PLAINS, Biomes.TAIGA)
        fun fromNBT(nbt: NBTTagCompound) = DimensionDescription().apply { readFromNBT(nbt) }
    }

}