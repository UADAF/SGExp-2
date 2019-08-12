package com.uadaf.sgexp.proxy

import com.uadaf.sgexp.model.IModelProvider
import com.uadaf.sgexp.network.PacketRegistry
import com.uadaf.sgexp.registry.WorldRegistry
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent

open class CommonProxy {

    open fun preInit(e: FMLPreInitializationEvent) {
        WorldRegistry.init()
    }

    open fun init(e: FMLInitializationEvent) {
        PacketRegistry.init()
    }

    open fun postInit(e: FMLPostInitializationEvent) {

    }

    //MODEL FUNCTIONS BEGIN
    open fun regModel(provider: IModelProvider) {}

    open fun finishModelReg() {}
    //MODEL FUNCTIONS END
}