package com.uadaf.sgexp.dimensions.data.features

import net.minecraft.nbt.NBTTagCompound
import kotlin.random.Random

class DimFeatureRain : DimFeatureBase {
    override val type: DimFeatureType = DimFeatureType.RAIN

    var canRain: Boolean = false
        private set

    override fun NBTTagCompound.internalWriteToNBT() {
        setBoolean("rain", canRain)
    }

    override fun NBTTagCompound.internalReadFromNBT() {
        canRain = getBoolean("rain")
    }

    override fun fillRandom(rand: Random) {
        canRain = rand.nextBoolean()
    }


}