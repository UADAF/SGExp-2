package com.uadaf.sgexp.dimensions.data

import com.uadaf.sgexp.R
import com.uadaf.sgexp.dimensions.data.features.*
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.nbt.NBTTagList
import net.minecraftforge.common.util.Constants
import kotlin.random.Random

class DimensionDescription {

    val features: MutableMap<Class<out DimFeatureBase>, DimFeatureBase> = mutableMapOf()

    inline fun <reified T : DimFeatureBase> get() = features[T::class.java] as T

    fun setFeature(feature: DimFeatureBase) {
        features[feature.javaClass] = feature
    }

    fun setDefaultFeature(rand: Random, f: DimFeatureBase) {
        f.fillDefault(rand)
        setFeature(f)
    }

    fun writeToNBT(nbt: NBTTagCompound) = nbt.apply {
        val lst = NBTTagList()
        setTag("features", lst)
        for ((_, f) in features) {
            val tag = NBTTagCompound()
            tag.setString("class", f.javaClass.name)
            tag.setTag("feature", f.writeToNBT(NBTTagCompound()))
            lst.appendTag(tag)
        }
    }

    fun toNBT() = writeToNBT(NBTTagCompound())

    fun readFromNBT(nbt: NBTTagCompound) {
        nbt.getTagList("features", Constants.NBT.TAG_COMPOUND).forEach {
            val nn = it as NBTTagCompound
            val clazz = Class.forName(nn.getString("class")).asSubclass(DimFeatureBase::class.java)
            val feature = clazz.newInstance()
            feature.readFromNBT(nn.getCompoundTag("feature"))
            setFeature(feature)
        }
    }

    override fun toString(): String {
        val builder = StringBuilder()
        builder.append("[\n")
        for (ff in features.values) {
            builder.append(ff.javaClass.simpleName).append(": {").append(ff.toString()).append("}\n")
        }
        builder.append(']')
        return builder.toString()
    }

    companion object {
        fun fromNBT(nbt: NBTTagCompound, rand: Random) = DimensionDescription().apply {
            readFromNBT(nbt)
            for (f in FeatureRegistry.features) {
                if (f !in features) {
                    setDefaultFeature(rand, f.newInstance())
                }
            }
        }

        fun random(rand: Random) = DimensionDescription().apply {
            for (f in FeatureRegistry.features) {
                setDefaultFeature(rand, f.newInstance())
            }
            R.logger.info("Creating dimension $this")
        }

    }

}