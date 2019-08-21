package com.uadaf.sgexp.dimensions.data.features

import net.minecraft.nbt.NBTTagCompound
import kotlin.random.Random

class DimFeaturePopulationType : DimFeatureBase {

    enum class PopulationType {
        UNINHABITED,
        HUMAN_GOAULD_SLAVES,
        FREE_HUMAN,
        GOAULD_STRONGHOLD,
        ANCIENT_OUTPOST;
    }

    var type: PopulationType = PopulationType.UNINHABITED
        private set

    override fun NBTTagCompound.internalWriteToNBT() {
        setString("type", type.name)
    }

    override fun NBTTagCompound.internalReadFromNBT() {
        type = PopulationType.valueOf(getString("type"))
    }

    override fun fillDefault(rand: Random) {
        val k = rand.nextFloat()
        type = when(k) {
            in 0.9..1.0 -> PopulationType.GOAULD_STRONGHOLD
            in 0.75..0.9 -> PopulationType.ANCIENT_OUTPOST
            in 0.6..0.75 -> PopulationType.FREE_HUMAN
            in 0.3..0.6 -> PopulationType.HUMAN_GOAULD_SLAVES
            else -> PopulationType.UNINHABITED
        }
    }

    override fun toString(): String {
        return "type=$type"
    }

}