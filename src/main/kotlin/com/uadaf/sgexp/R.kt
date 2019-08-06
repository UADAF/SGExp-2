package com.uadaf.sgexp

import net.minecraft.creativetab.CreativeTabs
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import org.apache.logging.log4j.Logger

object R {

    const val modid = "sgexp"
    const val name = "Stargate Exploration"
    const val version = "1.0.0"
    val modAuthors = listOf("TheEvilRoot", "gt22")

    lateinit var logger: Logger

    var tab: CreativeTabs = object : CreativeTabs("modTab") {
        override fun createIcon(): ItemStack {
            return ItemStack(Items.APPLE)
        }
    }

    fun l(path: String) = ResourceLocation(modid, path)

}