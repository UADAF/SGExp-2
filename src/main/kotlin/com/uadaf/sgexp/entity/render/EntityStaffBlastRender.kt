package com.uadaf.sgexp.entity.render

import com.uadaf.sgexp.entity.EntityStaffBlast
import net.minecraft.client.renderer.entity.Render
import net.minecraft.client.renderer.entity.RenderManager
import net.minecraft.util.ResourceLocation

class EntityStaffBlastRender(man: RenderManager) : Render<EntityStaffBlast>(man) {
    override fun getEntityTexture(entity: EntityStaffBlast): ResourceLocation? = null
}