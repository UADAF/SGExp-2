package com.uadaf.sgexp.registry

import com.uadaf.sgexp.R
import com.uadaf.sgexp.entity.EntityStaffBlast
import com.uadaf.sgexp.entity.render.EntityStaffBlastRender
import net.minecraft.client.renderer.entity.Render
import net.minecraft.client.renderer.entity.RenderManager
import net.minecraft.entity.Entity
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.client.registry.IRenderFactory
import net.minecraftforge.fml.client.registry.RenderingRegistry
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.registry.EntityEntry
import net.minecraftforge.fml.common.registry.EntityEntryBuilder
import net.minecraftforge.registries.IForgeRegistry

@Mod.EventBusSubscriber
object EntityRegistry {

    val staffBlastName = R.l("entity_staff_blast")

    val staffBlast = EntityEntryBuilder.create<EntityStaffBlast>()
            .entity(EntityStaffBlast::class.java)
            .id(staffBlastName, 1)
            .name(staffBlastName.path)
            .tracker(64, 1, false)
            .build()



    @SubscribeEvent
    @JvmStatic
    fun init(e: RegistryEvent.Register<EntityEntry>) {
        with(e.registry) {
            register(staffBlast)
        }
    }

    fun initRenderers() {
        r(::EntityStaffBlastRender)
    }

    private inline fun <reified T : Entity> r(noinline factory: (RenderManager) -> Render<in T>) {
        RenderingRegistry.registerEntityRenderingHandler(T::class.java, IRenderFactory(factory))
    }

}