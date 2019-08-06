package com.uadaf.sgexp.registry

import net.minecraft.item.ItemBlock
import kotlin.reflect.KClass

annotation class CustomItemBlock(val cls: KClass<out ItemBlock>)