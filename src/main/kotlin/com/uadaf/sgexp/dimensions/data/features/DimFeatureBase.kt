package com.uadaf.sgexp.dimensions.data.features

import net.minecraft.nbt.NBTTagCompound
import kotlin.random.Random

interface DimFeatureBase {

    fun NBTTagCompound.internalWriteToNBT()

    fun writeToNBT(nbt: NBTTagCompound): NBTTagCompound {
        nbt.internalWriteToNBT()
        return nbt
    }

    fun NBTTagCompound.internalReadFromNBT()

    fun readFromNBT(nbt: NBTTagCompound) {
        nbt.internalReadFromNBT()
    }

    fun fillDefault(rand: Random)

}