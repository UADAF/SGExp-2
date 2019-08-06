package com.uadaf.sgexp.proxy

import com.uadaf.sgexp.model.IModelProvider
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent

open class CommonProxy {

    open fun preInit(e: FMLPreInitializationEvent) {

    }

    open fun init(e: FMLInitializationEvent) {

    }

    open fun postInit(e: FMLPostInitializationEvent) {

    }

    //MODEL FUNCTIONS BEGIN
    open fun regModel(provider: IModelProvider) {}

    open fun finishModelReg() {}
    //MODEL FUNCTIONS END
}