package com.uadaf.sgexp.registry

import com.uadaf.sgexp.SGExp
import com.uadaf.sgexp.dimensions.data.DimensionStorage
import com.uadaf.sgexp.dimensions.TestWorldProvider
import com.uadaf.sgexp.dimensions.TestWorldType
import com.uadaf.sgexp.network.PacketRegistry
import com.uadaf.sgexp.network.packets.SPacketRegisterDimension
import net.minecraft.world.DimensionType
import net.minecraft.world.World
import net.minecraft.world.WorldProvider
import net.minecraftforge.common.DimensionManager
import java.lang.IllegalStateException

object WorldRegistry {

    val overworld: World
        get() = DimensionManager.getWorld(0)

    lateinit var testDimType: DimensionType
    lateinit var testWorldType: TestWorldType

    var lastId = findFreeId()

    fun init() {
        with(SGExp.cfg) {
            testDimType = reg<TestWorldProvider>(testDimId)
            testWorldType = TestWorldType()
        }

    }

    fun initDims() {
        val storage = DimensionStorage.getDimensionStorage(overworld)
        for (id in storage.dims) {
            regDim(id)
            PacketRegistry.network.sendToAll(SPacketRegisterDimension(id))
        }
    }

    fun regDim(id: Int) {
        if (!DimensionManager.isDimensionRegistered(id)) {
            DimensionManager.registerDimension(id, testDimType)
        }
    }

    private inline fun <reified T : WorldProvider> reg(keepLoaded: Boolean = false): DimensionType {
        val id = findFreeId()
        return reg<T>(id, keepLoaded)
    }

    private fun findFreeId(): Int {
        for (i in 2..Int.MAX_VALUE) {
            if (!DimensionManager.isDimensionRegistered(i)) {
                return i
            } else if(!DimensionManager.isDimensionRegistered(-i)) {
                return -i
            }
        }
        throw IllegalStateException("Unable to find free dimension id! What the hell you've been doing")
    }

    private inline fun <reified T : WorldProvider> reg(id: Int, keepLoaded: Boolean = false): DimensionType {
        val name = T::class.simpleName!!.toLowerCase().removeSuffix("Provider")
        return reg<T>(name, id, keepLoaded)
    }

    private inline fun <reified T : WorldProvider> reg(name: String, id: Int, keepLoaded: Boolean = false): DimensionType {
        val dtype = DimensionType.register(name, "_$name", id, T::class.java, keepLoaded)
        return dtype
    }

    fun addWorld() {
        if (!overworld.isRemote) {
            lastId = findFreeId()
            regDim(lastId)
            val storage = DimensionStorage.getDimensionStorage(overworld)
            storage.addDimension(lastId)
            storage.save()
        }
    }

}