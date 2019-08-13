package com.uadaf.sgexp.dimensions.data.features

import net.minecraft.nbt.NBTTagCompound
import kotlin.random.Random

class DimFeatureSkyColor : DimFeatureBase {

    override val type = DimFeatureType.SKY_COLOR

    var color: Int = -1
        private set

    override fun NBTTagCompound.internalWriteToNBT() {
        setInteger("color", color)
    }

    override fun NBTTagCompound.internalReadFromNBT() {
        color = getInteger("color")
    }

    override fun fillRandom(rand: Random) {
        color = rand.nextInt(0xFFFFFF + 1)
    }

    override fun toString() = "skycolor=${Integer.toHexString(color)}"

}