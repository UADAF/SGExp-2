package com.uadaf.sgexp.structure

import com.uadaf.sgexp.R
import gcewing.sg.BaseOrientation
import gcewing.sg.SGCraft
import gcewing.sg.tileentity.DHDTE
import gcewing.sg.tileentity.SGBaseTE
import net.minecraft.block.properties.IProperty
import net.minecraft.block.state.IBlockState
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing
import net.minecraft.util.Rotation
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

object GateBuilder {

    private fun BlockPos.MutableBlockPos.madd(x: Int = 0, y: Int = 0, z: Int = 0): BlockPos.MutableBlockPos {
        setPos(getX() + x, getY() + y, getZ() + z)
        return this
    }

    private fun place(curBlock: BlockPos.MutableBlockPos, world: World, vararg row: IBlockState?) {
        for(b in row) {
            curBlock.madd(x = 1)
            if(b != null) {
                world.setBlockState(curBlock, b)
            } else {
                world.setBlockToAir(curBlock)
            }
        }
        curBlock.madd(x = -row.size)
    }

    fun buildGate(world: World, basePos: BlockPos = BlockPos(0, 0, 0)): SGBaseTE? {
        touchChunk(world)
        val curBlock = BlockPos.MutableBlockPos(basePos)
        while(world.getBlockState(curBlock).block != Blocks.AIR) {
            curBlock.madd(y = 1)
        }
        val gatePos = curBlock.madd(y = 1).toImmutable()
        curBlock.madd(x = -3) //Shift to beginning
        val ring = SGCraft.sgRingBlock.defaultState
        val chevron = SGCraft.sgRingBlock.getStateFromMeta(1)


        //From bottom to top:
        //CRCRC
        //RAAAR
        //CAAAC
        //RAAAR
        //CRBRC
        //C - chevron, R - ring, B - base, A - air
        place(curBlock, world, chevron, ring, SGCraft.sgBaseBlock.getStateFromMeta(2), ring, chevron)
        place(curBlock.move(EnumFacing.UP), world, ring, null, null, null, ring)
        place(curBlock.move(EnumFacing.UP), world, chevron, null, null, null, chevron)
        place(curBlock.move(EnumFacing.UP), world, ring, null, null, null, ring)
        place(curBlock.move(EnumFacing.UP), world, chevron, ring, chevron, ring, chevron)
        buildPlatform(world, gatePos)
        return tuneGate(world, gatePos)
    }

    private fun touchChunk(world: World) = world.chunkProvider.provideChunk(0, 0)


    private fun buildPlatform(world: World, gatePos: BlockPos) {

        val brick = Blocks.STONEBRICK.defaultState
        val stairs = Blocks.STONE_BRICK_STAIRS.defaultState.withRotation(Rotation.CLOCKWISE_180)

        @Suppress("UNCHECKED_CAST")
        val dhd = SGCraft.sgControllerBlock.defaultState.withProperty(BaseOrientation.Orient4WaysByState.FACING as IProperty<EnumFacing>, EnumFacing.SOUTH)
        val curBlock = BlockPos.MutableBlockPos(gatePos)
        curBlock.madd(x = -3, y = -1, z = 3)
        for(i in 1..8) place(curBlock.move(EnumFacing.NORTH), world, brick, brick, brick, brick, brick)
        place(curBlock.move(EnumFacing.NORTH), world, stairs, stairs, stairs, stairs, stairs)
        curBlock.madd(y = 1, z = 9)
        for(i in 1..2) place(curBlock.move(EnumFacing.NORTH), world, brick, brick, brick, brick, brick)
        curBlock.move(EnumFacing.NORTH) //Skip gate
        for(i in 1..2) place(curBlock.move(EnumFacing.NORTH), world, brick, brick, brick, brick, brick)
        place(curBlock.move(EnumFacing.NORTH), world, stairs, stairs, stairs, stairs, stairs)
        place(curBlock.move(EnumFacing.NORTH), world, null, null, null, null, null)
        place(curBlock.move(EnumFacing.NORTH), world, null, null, dhd, null, null)
        place(curBlock.move(EnumFacing.NORTH), world, null, null, null, null, null)
        curBlock.madd(y = 1, z = 9)
        for(i in 1..4) {
            for(j in 1..2) place(curBlock.move(EnumFacing.NORTH), world, null, null, null, null, null)
            curBlock.move(EnumFacing.NORTH) //Skip gate
            for(j in 1..2) place(curBlock.move(EnumFacing.NORTH), world, null, null, null, null, null)
            curBlock.madd(y = 1, z = 9)
        }
    }

    private fun tuneGate(world: World, gatePos: BlockPos): SGBaseTE? {
        val gate = world.getTileEntity(gatePos)
        if(gate == null || gate !is SGBaseTE) {
            R.logger.warn("Unable to find gate that was just placed at $gatePos")
        } else {
            SGCraft.sgBaseBlock.checkForVerticalMerge(world, gatePos)
            gate.update() //Ensure gate gets address
            gate.applyChevronUpgrade(ItemStack(SGCraft.sgChevronUpgrade), null)
            for(i in 0 until gate.sizeInventory) {
                gate.setInventorySlotContents(i, ItemStack(Blocks.STONEBRICK))
            }
        }
        val dhdPos = gatePos.north(5)
        val dhd = world.getTileEntity(dhdPos)
        if(dhd == null || dhd !is DHDTE) {
            R.logger.warn("Unable to find DHD that was just placed at $dhdPos")
        } else {
            dhd.linkToStargate(gate as SGBaseTE)
            dhd.setInventorySlotContents(0, ItemStack(SGCraft.naquadah))
            dhd.drawEnergyDouble(1.0) //Draw some energy, so naquadah can't be taken out
        }
        return gate as? SGBaseTE
    }
    
}