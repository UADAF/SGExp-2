package com.uadaf.sgexp.dimensions.data.features

import net.minecraft.nbt.NBTTagCompound
import kotlin.random.Random

interface DimFeatureBase {

    val type: DimFeatureType

    fun NBTTagCompound.internalWriteToNBT()

    fun writeToNBT(nbt: NBTTagCompound): NBTTagCompound {
        nbt.internalWriteToNBT()
        return nbt
    }

    fun NBTTagCompound.internalReadFromNBT()

    fun readFromNBT(nbt: NBTTagCompound) {
        nbt.internalReadFromNBT()
    }

    fun fillRandom(rand: Random)

}