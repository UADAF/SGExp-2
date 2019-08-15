package com.uadaf.sgexp.entity

import com.uadaf.sgexp.registry.SourceRegistry
import com.uadaf.sgexp.registry.createDirect
import com.uadaf.sgexp.registry.createIndirect
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.monster.EntityEnderman
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.entity.projectile.EntityArrow
import net.minecraft.init.SoundEvents
import net.minecraft.item.ItemStack
import net.minecraft.network.play.server.SPacketChangeGameState
import net.minecraft.util.EnumParticleTypes
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.RayTraceResult
import net.minecraft.world.World

class EntityStaffBlast : EntityArrow {

    val damage = 3f
    
    override fun getArrowStack(): ItemStack = ItemStack.EMPTY

    init {
        setNoGravity(true)
    }

    constructor(world: World) : super(world)

    constructor(world: World, shooter: EntityLivingBase) : super(world, shooter)

    override fun onUpdate() {
        super.onUpdate()
        if (inGround) {
            setDead()
        }
        val r = world.rand
        val randomFactor = 0.05
        for (i in 0..25) {
            world.spawnParticle(EnumParticleTypes.FLAME, posX + r.nextGaussian() * randomFactor, posY + r.nextGaussian() * randomFactor, posZ + + r.nextGaussian() * randomFactor, 0.0, 0.01, 0.0)
        }
    }

    override fun onHit(ray: RayTraceResult) {
        val entity = ray.entityHit ?: return
        val base = SourceRegistry.staffBlast
        val source = if (shootingEntity == null) base.createDirect(this) else base.createIndirect(this, shootingEntity)
        if (entity.attackEntityFrom(source, damage)) {
            entity.setFire(30)
            if (entity is EntityLivingBase) {

                if (!world.isRemote) {
                    entity.arrowCountInEntity++
                }

                if (shootingEntity is EntityLivingBase) {
                    EnchantmentHelper.applyThornEnchantments(entity, shootingEntity)
                    EnchantmentHelper.applyArthropodEnchantments(shootingEntity as EntityLivingBase, entity)
                }

                arrowHit(entity)

                if (shootingEntity != null && entity !== shootingEntity && entity is EntityPlayer && shootingEntity is EntityPlayerMP) {
                    (shootingEntity as EntityPlayerMP).connection.sendPacket(SPacketChangeGameState(6, 0.0f))
                }
            }

            playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0f, 1.2f / (rand.nextFloat() * 0.2f + 0.9f))
        }
        setDead()
    }

}