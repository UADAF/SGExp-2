package com.uadaf.sgexp.registry

import com.uadaf.sgexp.SGExp
import com.uadaf.sgexp.blocks.TestBlock
import com.uadaf.sgexp.model.IModelProvider
import net.minecraft.block.Block
import net.minecraft.item.ItemBlock
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Mod.EventBusSubscriber
object BlockRegistry {

    val testBlock = TestBlock


    @SubscribeEvent
    @JvmStatic
    fun init(e: RegistryEvent.Register<Block>) {
        javaClass.declaredFields.forEach { f ->
            if (Block::class.java.isAssignableFrom(f.type)) {
                val block = (f.get(this) as? Block) ?: f.type.asSubclass(Block::class.java).newInstance().also { f.set(this, it) }
                e.registry.register(block)
                if (block is IModelProvider) {
                    SGExp.proxy.regModel(block)
                }
            }
        }
    }

}