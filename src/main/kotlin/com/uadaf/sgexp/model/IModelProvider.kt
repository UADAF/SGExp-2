package com.uadaf.sgexp.model

import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

interface IModelProvider {

    @SideOnly(Side.CLIENT)
    fun initModel()

}