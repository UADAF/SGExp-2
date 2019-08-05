package com.uadaf.sgexp

import net.minecraft.util.ResourceLocation

object R {

    const val modid = "sgexp"
    const val name = "Stargate Exploration"
    const val version = "1.0.0"
    val modAuthors = listOf("TheEvilRoot", "gt22")

    fun l(path: String) = ResourceLocation(modid, path)

}