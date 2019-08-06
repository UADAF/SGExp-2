package com.uadaf.sgexp.proxy

import com.uadaf.sgexp.registry.ModelRegistry
import com.uadaf.sgexp.model.IModelProvider
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import java.lang.IllegalStateException

class ClientProxy : CommonProxy() {

    private var modelRegistry: ModelRegistry? = ModelRegistry()

    override fun preInit(e: FMLPreInitializationEvent) {

    }

    override fun init(e: FMLInitializationEvent) {

    }

    override fun postInit(e: FMLPostInitializationEvent) {

    }

    override fun regModel(provider: IModelProvider) {
        if (modelRegistry == null) {
            throw IllegalStateException("Model were already initialized")
        }
        modelRegistry!!.reg(provider)
    }

    override fun finishModelReg() {
        modelRegistry = null
    }

}