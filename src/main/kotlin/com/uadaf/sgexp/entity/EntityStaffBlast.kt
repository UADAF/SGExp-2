package com.uadaf.sgexp.entity

import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.projectile.EntitySmallFireball
import net.minecraft.world.World

class EntityStaffBlast : EntitySmallFireball {

    init {
        setSize(0.00003125f, 0.00003125f)
        motionX += accelerationX * 10
        motionY += accelerationY * 10
        motionZ += accelerationZ * 10
    }

    override fun isFireballFiery() = false

    constructor(world: World) : super(world)

    constructor(world: World, shooter: EntityLivingBase, ax: Double, ay: Double, az: Double) : super(world, shooter, ax, ay, az)

}