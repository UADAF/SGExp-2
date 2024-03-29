package com.uadaf.sgexp.dimensions.data.features

import net.minecraft.nbt.NBTTagCompound
import kotlin.random.Random

class DimFeatureRain : DimFeatureBase {

    var canRain: Boolean = false
        private set

    override fun NBTTagCompound.internalWriteToNBT() {
        setBoolean("rain", canRain)
    }

    override fun NBTTagCompound.internalReadFromNBT() {
        canRain = getBoolean("rain")
    }

    override fun fillDefault(rand: Random) {
        canRain = rand.nextBoolean()
    }

    override fun toString(): String {
        return "can_rain=$canRain"
    }

}