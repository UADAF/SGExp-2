package com.uadaf.sgexp.registry

import com.uadaf.sgexp.SGExp
import com.uadaf.sgexp.blocks.ItemBlockBase
import com.uadaf.sgexp.items.TestItem
import com.uadaf.sgexp.model.IModelProvider
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Mod.EventBusSubscriber
object ItemRegistry {


    val testItem = TestItem

    private val itemBlocks = mutableMapOf<Block, ItemBlock>()

    @SubscribeEvent
    @JvmStatic
    fun init(e: RegistryEvent.Register<Item>) {
        javaClass.declaredFields.forEach { f ->
            if (Item::class.java.isAssignableFrom(f.type)) {
                val item = (f.get(this) as? Item)
                        ?: f.type.asSubclass(Item::class.java).newInstance().also { f.set(this, it) }
                e.registry.register(item)
                if (item is IModelProvider) {
                    SGExp.proxy.regModel(item)
                }
            }
        }
        BlockRegistry.javaClass.declaredFields.forEach { f ->
            f.isAccessible = true
            if (Block::class.java.isAssignableFrom(f.type)) {
                val block = f.get(BlockRegistry) as Block
                val anno = f.annotations.find { it is CustomItemBlock }
                val ib = anno?.javaClass?.getConstructor(Block::class.java)?.newInstance(block) as? ItemBlock
                        ?: ItemBlockBase(block)
                itemBlocks[block] = ib
                e.registry.register(ib)
                if (ib is IModelProvider) {
                    SGExp.proxy.regModel(ib)
                }
            }
        }
    }

    fun getItemBlock(block: Block) = itemBlocks[block]

}