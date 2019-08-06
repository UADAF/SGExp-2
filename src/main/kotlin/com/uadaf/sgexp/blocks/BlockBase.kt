package com.uadaf.sgexp.blocks

import com.uadaf.sgexp.R
import com.uadaf.sgexp.model.IModelProvider
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.block.statemap.StateMap
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

open class BlockBase(regName: String, mat: Material) : Block(mat), IModelProvider {

    init {
        registryName = R.l(regName)
        translationKey = "${R.modid}.$regName"
        creativeTab = R.tab
    }

    @SideOnly(Side.CLIENT)
    override fun initModel() {
        val bld = StateMap.Builder()
        for (prop in getBlockState().properties) {
            bld.ignore(prop)
        }
        ModelLoader.setCustomStateMapper(this, bld.build())
    }
}