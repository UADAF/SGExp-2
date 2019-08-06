package com.uadaf.sgexp.items

import com.uadaf.sgexp.R
import com.uadaf.sgexp.model.IModelProvider
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.item.Item
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

open class ItemBase(regName: String) : Item(), IModelProvider {

    init {
        registryName = R.l(regName)
        translationKey = "${R.modid}.$regName"
        creativeTab = R.tab
    }

    @SideOnly(Side.CLIENT)
    override fun initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, ModelResourceLocation(registryName!!, "inventory"))
    }

}