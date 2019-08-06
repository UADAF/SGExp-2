package com.uadaf.sgexp.items

import com.uadaf.sgexp.R
import com.uadaf.sgexp.SGExp
import com.uadaf.sgexp.model.IModelProvider
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.item.Item
import net.minecraftforge.client.model.ModelLoader

open class ItemBase(regName: String) : Item(), IModelProvider{

    init {
        registryName = R.l(regName)
        translationKey = "${R.modid}.$regName"
        creativeTab = R.tab
    }

    override fun initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, ModelResourceLocation(registryName!!, "inventory"))
    }

}