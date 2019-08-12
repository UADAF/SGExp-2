package com.uadaf.sgexp.dimensions

import net.minecraft.entity.Entity
import net.minecraft.world.World
import net.minecraftforge.common.util.ITeleporter

object DummyTeleporter : ITeleporter {
    override fun placeEntity(world: World?, entity: Entity?, yaw: Float) {

    }

}