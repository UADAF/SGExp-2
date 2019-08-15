package com.uadaf.sgexp.items

import com.uadaf.sgexp.R
import com.uadaf.sgexp.items.ItemBase
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraftforge.client.model.ModelLoader

object StaffWeapon : ItemBase("staff_weapon") {


    override fun isFull3D() = true

    override fun initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, ModelResourceLocation(R.l("staff_weapon"), "inventory"))

    }


}