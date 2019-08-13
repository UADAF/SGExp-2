package com.uadaf.sgexp.network.packets

import io.netty.buffer.ByteBuf
import net.minecraft.client.Minecraft
import net.minecraft.crash.CrashReport
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.ReportedException
import net.minecraftforge.fml.common.network.simpleimpl.IMessage
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext
import java.lang.IllegalArgumentException
import java.nio.charset.StandardCharsets

class SPacketSetAddress(var slot: Int, var addr: String) : IMessage {

    constructor() : this(0, "")

    override fun fromBytes(buf: ByteBuf) {
        slot = buf.readInt()
        addr = buf.readCharSequence(9, StandardCharsets.UTF_8).toString()
    }

    override fun toBytes(buf: ByteBuf) {
        if (addr.length != 9) {
            throw ReportedException(CrashReport.makeCrashReport(IllegalArgumentException("SPacketSetAddress excepted address length to be 9. Found ${addr.length}"), ""))
        }
        buf.writeInt(slot)
        buf.writeCharSequence(addr, StandardCharsets.UTF_8)
    }


    object Handler : IMessageHandler<SPacketSetAddress, IMessage> {

        override fun onMessage(p: SPacketSetAddress, ctx: MessageContext): IMessage? {
            val player = Minecraft.getMinecraft().player
            val stack = player.inventory.getStackInSlot(p.slot)
            stack.tagCompound = (stack.tagCompound ?: NBTTagCompound()).apply { setString("address", p.addr) }
            return null
        }

    }

}