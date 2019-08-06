package com.uadaf.sgexp.blocks

import com.uadaf.sgexp.model.IModelProvider
import net.minecraft.block.Block
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.item.ItemBlock
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class ItemBlockBase(block: Block) : ItemBlock(block), IModelProvider {
    init {
        registryName = block.registryName
    }

    @SideOnly(Side.CLIENT)
    override fun initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, ModelResourceLocation(registryName!!, "normal"))
    }

    //Don't remove this! It will break everything just 'cos Item#getMetadata returns zero by default!
    override fun getMetadata(damage: Int) = damage
}