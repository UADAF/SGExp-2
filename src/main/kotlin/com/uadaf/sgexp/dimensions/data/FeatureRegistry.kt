package com.uadaf.sgexp.dimensions.data

import com.uadaf.sgexp.dimensions.data.features.*

object FeatureRegistry {

    val features: MutableSet<Class<out DimFeatureBase>> = mutableSetOf()

    inline fun <reified T : DimFeatureBase> reg() = features.add(T::class.java)

    inline fun <reified T : DimFeatureBase> register() = features.add(T::class.java)

    inline fun <reified T : DimFeatureBase> unreg() = features.remove(T::class.java)

    inline fun <reified T : DimFeatureBase> isRegistered() = T::class.java in features


    fun init() {
        reg<DimFeatureBiome>()
        reg<DimFeatureEvaporation>()
        reg<DimFeatureEvaporation>()
        reg<DimFeatureRain>()
        reg<DimFeatureSkyColor>()
        reg<DimFeatureUndergroundGate>()
        reg<DimFeaturePegasusGate>()
    }
}