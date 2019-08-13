package com.uadaf.sgexp.items

import com.uadaf.sgexp.gui.AddressPlateGui
import com.uadaf.sgexp.network.PacketRegistry
import com.uadaf.sgexp.network.packets.SPacketSetAddress
import com.uadaf.sgexp.structure.GateBuilder
import gcewing.sg.tileentity.DHDTE
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

object AddressPlate : ItemBase("addressPlate") {


    override fun onItemRightClick(world: World, player: EntityPlayer, hand: EnumHand): ActionResult<ItemStack> {
        val stack = player.getHeldItem(hand)
        if (!player.isSneaking) {
            if (!world.isRemote && stack.tagCompound?.hasKey("address") != true) {
                val addr = GateBuilder.makeNewWorld().homeAddress
                stack.tagCompound = (stack.tagCompound ?: NBTTagCompound()).apply { setString("address", addr) }
                for (i in 0 until player.inventory.sizeInventory) {
                    if (player.inventory.getStackInSlot(i) === stack) {
                        PacketRegistry.network.sendTo(SPacketSetAddress(i, addr), player as EntityPlayerMP)
                        break
                    }
                }
            }
            if (world.isRemote) Minecraft.getMinecraft().displayGuiScreen(AddressPlateGui(stack))
        }
        return ActionResult(EnumActionResult.SUCCESS, stack)
    }

    override fun onItemUse(player: EntityPlayer, world: World, pos: BlockPos, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult {
        if (player.isSneaking && !world.isRemote) {
            val te = world.getTileEntity(pos)
            if (te is DHDTE) {
                val stack = player.getHeldItem(hand)
                if (stack.tagCompound?.hasKey("address") == true) {
                    val addr = stack.tagCompound!!.getString("address")
                    for (symb in addr) {
                        te.enterSymbol(player, symb)
                    }
                    te.linkedStargateTE.connectOrDisconnect(addr, player)
                }
            }
        }
        return EnumActionResult.PASS
    }


}