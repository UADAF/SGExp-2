package com.uadaf.sgexp.dimensions.data.features

import net.minecraft.nbt.NBTTagCompound
import kotlin.random.Random

class DimFeatureEvaporation : DimFeatureBase {
    override val type = DimFeatureType.WATER_EVAPORATAION

    var doesWaterEvaporate: Boolean = false
        private set

    override fun NBTTagCompound.internalWriteToNBT() {
        setBoolean("eva", doesWaterEvaporate)
    }

    override fun NBTTagCompound.internalReadFromNBT() {
        doesWaterEvaporate = getBoolean("eva")
    }

    override fun fillRandom(rand: Random) {
        doesWaterEvaporate = rand.nextBoolean()
    }

    override fun toString(): String {
        return "water_evaporation=${doesWaterEvaporate}"
    }


}