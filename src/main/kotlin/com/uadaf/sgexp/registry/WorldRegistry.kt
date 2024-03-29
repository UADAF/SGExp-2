package com.uadaf.sgexp.registry

import com.uadaf.sgexp.SGExp
import com.uadaf.sgexp.dimensions.data.DimensionStorage
import com.uadaf.sgexp.dimensions.TestWorldProvider
import com.uadaf.sgexp.network.PacketRegistry
import com.uadaf.sgexp.network.packets.SPacketRegisterDimension
import net.minecraft.world.DimensionType
import net.minecraft.world.World
import net.minecraft.world.WorldProvider
import net.minecraftforge.common.DimensionManager
import java.lang.IllegalStateException
import com.uadaf.sgexp.R
import com.uadaf.sgexp.dimensions.data.DimensionDescription
import net.minecraft.world.WorldServer
import kotlin.random.asKotlinRandom


object WorldRegistry {

    val overworld: World
        get() = DimensionManager.getWorld(0)

    lateinit var testDimType: DimensionType

    var lastId = findFreeId()

    fun init() {
        with(SGExp.cfg) {
            testDimType = reg<TestWorldProvider>(minId)
        }

    }

    fun initDims() {
        val storage = DimensionStorage.getDimensionStorage(overworld)
        for ((id, _) in storage.dims) {
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
        val allowNeg = SGExp.cfg.allowNegative
        for (i in SGExp.cfg.minId..Int.MAX_VALUE) {
            if (!DimensionManager.isDimensionRegistered(i)) {
                return i
            } else if(allowNeg && !DimensionManager.isDimensionRegistered(-i)) {
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

    private fun touchSpawnChank(world: WorldServer) {
        val providerServer = world.chunkProvider
        if (!providerServer.chunkExists(0, 0)) {
            try {
                providerServer.provideChunk(0, 0)
                providerServer.chunkGenerator.populate(0, 0)
            } catch (e: Exception) {
                R.logger.error("Something went wrong during creation of the dimension!")
                e.printStackTrace()
                // We catch this exception to make sure our dimension tab is at least ok.
            }
        }
    }

    fun addWorld(): WorldServer? {
        if (!overworld.isRemote) {
            lastId = findFreeId()
            regDim(lastId)
            val newWorld = overworld.minecraftServer!!.getWorld(lastId)
            val storage = DimensionStorage.getDimensionStorage(overworld)
            storage.addDimension(lastId, DimensionDescription.random(newWorld.rand.asKotlinRandom()))
            touchSpawnChank(newWorld)
            storage.save()
            return newWorld
        }
        return null
    }

}