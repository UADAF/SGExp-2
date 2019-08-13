package com.uadaf.sgexp.items

import com.uadaf.sgexp.dimensions.DummyTeleporter
import com.uadaf.sgexp.registry.WorldRegistry
import com.uadaf.sgexp.structure.GateBuilder
import gcewing.sg.tileentity.SGBaseTE
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.common.DimensionManager

object TestItem : ItemBase("test_item") {

    override fun onItemRightClick(world: World, player: EntityPlayer, handIn: EnumHand): ActionResult<ItemStack> {
        if (!world.isRemote && player.isSneaking) {
            val newWorld = WorldRegistry.addWorld()!!
            println(GateBuilder.buildGate(newWorld, BlockPos.ORIGIN)?.homeAddress)
        }
//        player.changeDimension(if (world.provider.dimension == 0) WorldRegistry.lastId else 0, DummyTeleporter)

        return super.onItemRightClick(world, player, handIn)
    }

    override fun onItemUse(player: EntityPlayer, worldIn: World, pos: BlockPos, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult {
        val te = worldIn.getTileEntity(pos)
        if (te is SGBaseTE) {
            println("Home: ${te.homeAddress}")
            println("Dialed: ${te.dialledAddress}")
        }
        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ)
    }

}