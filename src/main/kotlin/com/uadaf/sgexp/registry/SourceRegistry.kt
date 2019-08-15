package com.uadaf.sgexp.registry

import net.minecraft.entity.Entity
import net.minecraft.util.DamageSource
import net.minecraft.util.EntityDamageSource
import net.minecraft.util.EntityDamageSourceIndirect

fun DamageSource.copyOn(source: DamageSource) = source.also {
    if (isDamageAbsolute) it.setDamageIsAbsolute()
    if (isDifficultyScaled) it.setDifficultyScaled()
    if (isExplosion) it.setExplosion()
    if (isFireDamage) it.setFireDamage()
    if (isMagicDamage) it.setMagicDamage()
    if (isProjectile) it.setProjectile()
    if (isUnblockable) it.setDamageBypassesArmor()
    if (canHarmInCreative()) it.setDamageAllowedInCreativeMode()
}

fun DamageSource.createDirect(attacker: Entity?) = copyOn(EntityDamageSource(damageType, attacker))


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
fun DamageSource.createIndirect(directAttacker: Entity?, indirectAttacker: Entity?) =
        copyOn(EntityDamageSourceIndirect(damageType, directAttacker, indirectAttacker))

fun DamageSource.isFrom(source: DamageSource) = damageType == source.damageType

object SourceRegistry {

    val staffBlast = DamageSource("staffBlast").setFireDamage().setProjectile()

}