package com.uadaf.sgexp.proxy

import com.uadaf.sgexp.R
import com.uadaf.sgexp.model.IModelProvider
import com.uadaf.sgexp.registry.ModelRegistry
import net.minecraftforge.client.model.obj.OBJLoader
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent

class ClientProxy : CommonProxy() {

    private var modelRegistry: ModelRegistry? = ModelRegistry()

    override fun preInit(e: FMLPreInitializationEvent) {
        super.preInit(e)
        OBJLoader.INSTANCE.addDomain(R.modid)
    }

    override fun init(e: FMLInitializationEvent) {
        super.init(e)
    }

    override fun postInit(e: FMLPostInitializationEvent) {
        super.postInit(e)
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