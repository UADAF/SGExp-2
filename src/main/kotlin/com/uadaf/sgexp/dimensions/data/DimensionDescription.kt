package com.uadaf.sgexp.dimensions.data

import com.uadaf.sgexp.R
import com.uadaf.sgexp.dimensions.data.features.*
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.nbt.NBTTagList
import net.minecraftforge.common.util.Constants
import java.lang.IllegalStateException
import java.lang.StringBuilder
import kotlin.random.Random

class DimensionDescription {

    val features: MutableMap<DimFeatureType, MutableList<DimFeatureBase>> = mutableMapOf()

    inline fun <reified T : DimFeatureBase> getFeature(type: DimFeatureType) =
            features[type]?.firstOrNull() as? T ?: throw IllegalStateException("Feature with featureType $type and class ${T::class.simpleName}")

    fun addFeature(f: DimFeatureBase) {
        features.computeIfAbsent(f.type) { mutableListOf() }.add(f)
    }

    fun addRandomFeature(rand: Random, f: DimFeatureBase) {
        f.fillRandom(rand)
        addFeature(f)
    }

    fun writeToNBT(nbt: NBTTagCompound) = nbt.apply {
        val lst = NBTTagList()
        setTag("features", lst)
        for ((_, ff) in features) {
            for (f in ff) {
                val tag = NBTTagCompound()
                tag.setString("class", f.javaClass.name)
                tag.setTag("feature", f.writeToNBT(NBTTagCompound()))
                lst.appendTag(tag)
            }
        }
    }

    fun toNBT() = writeToNBT(NBTTagCompound())

    fun readFromNBT(nbt: NBTTagCompound) {
        nbt.getTagList("features", Constants.NBT.TAG_COMPOUND).forEach {
            val nn = it as NBTTagCompound
            val clazz = Class.forName(nn.getString("class")).asSubclass(DimFeatureBase::class.java)
            val feature = clazz.newInstance()
            feature.readFromNBT(nn.getCompoundTag("feature"))
            features.computeIfAbsent(feature.type) { mutableListOf() }.add(feature)
        }
    }

    override fun toString(): String {
        val builder = StringBuilder()
        builder.append("[\n")
        for ((type, ff) in features) {
            builder.append(type).append(": {").append(ff.joinToString(",")).append("}\n")
        }
        builder.append(']')
        return builder.toString()
    }

    companion object {
        fun fromNBT(nbt: NBTTagCompound) = DimensionDescription().apply { readFromNBT(nbt) }

        fun random(rand: Random) = DimensionDescription().apply {
            addRandomFeature(rand, DimFeatureBiome())
            addRandomFeature(rand, DimFeatureSkyColor())
            addRandomFeature(rand, DimFeatureEvaporation())
            addRandomFeature(rand, DimFeatureRain())
            R.logger.info("Creating dimension $this")
        }

    }

}