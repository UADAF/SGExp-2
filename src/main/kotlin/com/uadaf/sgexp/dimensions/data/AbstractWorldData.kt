package com.uadaf.sgexp.dimensions.data

import net.minecraft.world.World
import net.minecraft.world.storage.WorldSavedData
import net.minecraftforge.common.DimensionManager
import java.lang.reflect.InvocationTargetException
import java.util.HashMap

abstract class AbstractWorldData<T : AbstractWorldData<T>> protected constructor(name: String) : WorldSavedData(name) {

    fun save() {
        val world = DimensionManager.getWorld(0)
        world.setData(mapName, this)
        markDirty()
    }

    abstract fun clear()

    companion object {

        private val instances = HashMap<String, AbstractWorldData<*>>()

        fun clearInstances() {
            for (data in instances.values) {
                data.clear()
            }
            instances.clear()
        }

        val dataCount: Int
            get() = instances.size

        fun <T : AbstractWorldData<T>> getData(clazz: Class<T>, name: String): T {
            val world = DimensionManager.getWorld(0)
            return getData(world, clazz, name)
        }

        fun <T : AbstractWorldData<T>> getData(world: World, clazz: Class<T>, name: String): T {
            if (world.isRemote) {
                throw RuntimeException("Don't access this client-side!")
            }

            var data: T? = instances[name] as T?
            if (data != null) {
                return data
            }

            data = world.loadData(clazz, name) as T?
            if (data == null) {
                try {
                    data = clazz.getConstructor(String::class.java).newInstance(name)
                } catch (e: InstantiationException) {
                    throw RuntimeException(e)
                } catch (e: IllegalAccessException) {
                    throw RuntimeException(e)
                } catch (e: InvocationTargetException) {
                    throw RuntimeException(e)
                } catch (e: NoSuchMethodException) {
                    throw RuntimeException(e)
                }

            }
            instances[name] = data!!
            return data
        }
    }


}