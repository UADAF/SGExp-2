package com.uadaf.sgexp.dimensions.data

import net.minecraft.nbt.NBTTagCompound
import net.minecraft.nbt.NBTTagList
import net.minecraft.world.World
import net.minecraftforge.common.util.Constants

class DimensionStorage(name: String) : AbstractWorldData<DimensionStorage>(name) {

    val dims = mutableListOf<Int>()

    override fun clear() {
        dims.clear()
    }

    fun addDimension(id: Int) {
        dims.add(id)
    }

    fun removeDimension(id: Int) {
        dims.remove(id)
    }

    override fun readFromNBT(tagCompound: NBTTagCompound) {
        dims.clear()
        val lst = tagCompound.getTagList("dimensions", Constants.NBT.TAG_COMPOUND)
        for (i in 0 until lst.tagCount()) {
            val tc = lst.getCompoundTagAt(i)
            val id = tc.getInteger("id")
            dims.add(id)
        }
    }

    override fun writeToNBT(tagCompound: NBTTagCompound): NBTTagCompound {
        val lst = NBTTagList()
        for (id in dims) {
            val tc = NBTTagCompound()
            tc.setInteger("id", id)
            lst.appendTag(tc)
        }
        tagCompound.setTag("dimensions", lst)
        return tagCompound
    }

    companion object {

        private val DIMSTORAGE_NAME = "RFToolsDimensionStorage"

        private var clientInstance: DimensionStorage? = null

        fun getDimensionStorage(world: World): DimensionStorage {
            if (world.isRemote) {
                if (clientInstance == null) {
                    clientInstance = DimensionStorage(DIMSTORAGE_NAME)
                }
                return clientInstance!!
            }
            return getData(world, DimensionStorage::class.java, DIMSTORAGE_NAME)
        }
    }
}