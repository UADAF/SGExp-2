package com.uadaf.sgexp.items

import com.uadaf.sgexp.R
import com.uadaf.sgexp.entity.EntityStaffBlast
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.projectile.EntitySmallFireball
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumHand
import net.minecraft.world.World
import net.minecraftforge.client.model.ModelLoader

object StaffWeapon : ItemBase("staff_weapon") {


    override fun isFull3D() = true

    override fun onItemRightClick(world: World, player: EntityPlayer, hand: EnumHand): ActionResult<ItemStack> {
        if (hand == EnumHand.MAIN_HAND) {
            if (!world.isRemote) {
                val proj = EntityStaffBlast(world, player)
                proj.shoot(player, player.rotationPitch, player.rotationYaw, 0f, 3f, 0f)
                world.spawnEntity(proj)
            }
        }
        return ActionResult(
                if(hand == EnumHand.MAIN_HAND) EnumActionResult.SUCCESS else EnumActionResult.PASS,
                player.getHeldItem(hand))
    }

}