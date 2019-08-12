package com.uadaf.sgexp.items

import com.uadaf.sgexp.dimensions.DummyTeleporter
import com.uadaf.sgexp.registry.WorldRegistry
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumHand
import net.minecraft.world.World

object TestItem : ItemBase("test_item") {

    override fun onItemRightClick(world: World, player: EntityPlayer, handIn: EnumHand): ActionResult<ItemStack> {
        if (player.isSneaking) {
            WorldRegistry.addWorld()
        }
        player.changeDimension(if (world.provider.dimension == 0) WorldRegistry.lastId else 0, DummyTeleporter)
        return super.onItemRightClick(world, player, handIn)
    }

}