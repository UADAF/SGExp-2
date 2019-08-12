package com.uadaf.sgexp.network.packets

import com.uadaf.sgexp.registry.WorldRegistry
import io.netty.buffer.ByteBuf
import net.minecraftforge.fml.common.network.simpleimpl.IMessage
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext

class SPacketRegisterDimension(var dimid: Int) : IMessage {

    constructor() : this(0)

    override fun fromBytes(buf: ByteBuf) {
        dimid = buf.readInt()
    }

    override fun toBytes(buf: ByteBuf) {
        buf.writeInt(dimid)
    }

    companion object Handler : IMessageHandler<SPacketRegisterDimension, IMessage> {

        override fun onMessage(p: SPacketRegisterDimension, ctx: MessageContext): IMessage? {
            WorldRegistry.regDim(p.dimid)
            return null
        }

    }

}