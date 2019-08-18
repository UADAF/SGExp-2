package com.uadaf.sgexp.dimensions.data.features

import net.minecraft.nbt.NBTTagCompound
import kotlin.random.Random

class DimFeaturePegasusGate : DimFeatureBase {

    var pegasusGate: Boolean = false
        private set

    override fun NBTTagCompound.internalWriteToNBT() {
        setBoolean("pegasus", pegasusGate)
    }

    override fun NBTTagCompound.internalReadFromNBT() {
        pegasusGate = getBoolean("pegasus")
    }

    override fun fillDefault(rand: Random) {
        pegasusGate = rand.nextFloat() < 0.3 //TODO: Move to config?
    }

    override fun toString(): String {
        return "pegasus=$pegasusGate"
    }

}