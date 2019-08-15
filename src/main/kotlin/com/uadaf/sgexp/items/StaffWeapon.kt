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

    override fun initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, ModelResourceLocation(R.l("staff_weapon"), "inventory"))
    }

    override fun onItemRightClick(world: World, player: EntityPlayer, hand: EnumHand): ActionResult<ItemStack> {
        if (hand == EnumHand.MAIN_HAND) {
            if (!world.isRemote) {
                val look = player.lookVec
                val acc = 10.0
                val proj = EntityStaffBlast(world, player, acc * look.x, acc * look.y, acc * look.z)
                proj.posY = player.posY + (player.height / 2.0f).toDouble() - 0.2
                proj.posX += -0.5 * look.z + 1 * look.x
                proj.posY += 0.1 * look.y
                proj.posZ += 0.5 * look.x + 1 * look.z

                world.spawnEntity(proj)
            }
        }
        return ActionResult(
                if(hand == EnumHand.MAIN_HAND) EnumActionResult.SUCCESS else EnumActionResult.PASS,
                player.getHeldItem(hand))
    }

}