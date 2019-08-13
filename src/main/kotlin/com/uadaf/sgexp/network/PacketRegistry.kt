package com.uadaf.sgexp.network

import com.uadaf.sgexp.R
import com.uadaf.sgexp.network.packets.SPacketRegisterDimension
import com.uadaf.sgexp.network.packets.SPacketSetAddress
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraftforge.fml.common.network.NetworkRegistry
import net.minecraftforge.fml.common.network.simpleimpl.IMessage
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper
import net.minecraftforge.fml.relauncher.Side

object PacketRegistry {

    val network = SimpleNetworkWrapper(R.modid)

    private var id = 0
    fun init() {
        reg(SPacketRegisterDimension.Handler, Side.CLIENT)
        reg(SPacketSetAddress.Handler, Side.CLIENT)
    }


    private inline fun <reified REQ : IMessage> reg(handler: IMessageHandler<REQ, *>, receiver: Side) {
        network.registerMessage(handler, REQ::class.java, id++, receiver)
    }

    fun sendTo(packet: IMessage, player: EntityPlayerMP) {
        network.sendTo(packet, player)
    }

    fun sendToAll(packet: IMessage) {
        network.sendToAll(packet)
    }

    fun sendToDimension(packet: IMessage, dimId: Int) {
        network.sendToDimension(packet, dimId)
    }

    fun sendToAllAround(packet: IMessage, target: NetworkRegistry.TargetPoint) {
        network.sendToAllAround(packet, target)
    }

    fun sendToAllTracking(packet: IMessage, target: NetworkRegistry.TargetPoint) {
        network.sendToAllTracking(packet, target)
    }

    fun sendToAllTracking(packet: IMessage, entity: Entity) {
        network.sendToAllTracking(packet, entity)
    }

}