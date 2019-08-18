package com.uadaf.sgexp.dimensions.data.features

import net.minecraft.nbt.NBTTagCompound
import kotlin.random.Random

class DimFeatureUndergroundGate : DimFeatureBase {

    var isUnderground: Boolean = false
        private set
    override fun NBTTagCompound.internalWriteToNBT() {
        setBoolean("isUnderground", isUnderground)
    }

    override fun NBTTagCompound.internalReadFromNBT() {
        isUnderground = getBoolean("isUnderground")
    }

    override fun fillDefault(rand: Random) {
        isUnderground = rand.nextFloat() < 0.1f //TODO: Move to config?
    }

    override fun toString(): String {
        return "isUnderground=$isUnderground"
    }


}