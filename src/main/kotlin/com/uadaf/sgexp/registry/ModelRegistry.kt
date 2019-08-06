package com.uadaf.sgexp.registry

import com.uadaf.sgexp.SGExp
import com.uadaf.sgexp.model.IModelProvider
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

@SideOnly(Side.CLIENT)
class ModelRegistry {

    init {
        MinecraftForge.EVENT_BUS.register(this)
    }

    private val toInit: MutableList<IModelProvider> = mutableListOf()

    fun reg(provider: IModelProvider) {
        toInit.add(provider)
    }

    @SubscribeEvent
    fun ModelRegistryEvent.init() {
        toInit.forEach(IModelProvider::initModel)
        MinecraftForge.EVENT_BUS.unregister(this)
        SGExp.proxy.finishModelReg()
    }

}