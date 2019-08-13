package com.uadaf.sgexp.coremod.asm

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ
import com.uadaf.sgexp.coremod.Hooks
import net.minecraft.launchwrapper.IClassTransformer
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.tree.*

class SGExpASM : IClassTransformer {

    val hooks = Hooks::class.java.name.replace('.', '/')

    override fun transform(name: String?, transformedName: String?, basicClass: ByteArray?): ByteArray? {
        if (name == null || transformedName == null || basicClass == null) {
            return null
        }
        val obfuscated = name != transformedName
        return when (transformedName) {
            "gcewing.sg.tileentity.SGBaseTE" -> transformSGBase(basicClass)
            else -> basicClass
        }
    }


    /**
     * SGCraft doesn't check dimension by default.
     * This works for vanilla dims (likely because it only checks trackedEntities, and they are cleared every time),
     * but for some reason causes weird behaviour when applied to SGExp dimensions.
     * This transformation adds dimension check to sg
     */
    fun transformSGBase(basicClass: ByteArray): ByteArray {
        val node = makeNode(basicClass)

        for (method in node.methods) {
            if (method.name == "entityInPortal") {
                with(method.instructions) {
                    for (insn in this) {
                        if (insn is JumpInsnNode && insn.opcode == IF_ACMPNE) {
                            val lastLoad = insertLoads(insn, ALOAD to 1, ALOAD to 0)
                            val invoke = MethodInsnNode(INVOKESTATIC, hooks, "matchDims", "(Lnet/minecraft/entity/Entity;Lnet/minecraft/tileentity/TileEntity;)Z", false)
                            val jump = JumpInsnNode(IFEQ, insn.label)
                            insert(lastLoad, invoke)
                            insert(invoke, jump)
                            break
                        }
                    }
                }
                break
            }
        }

        return writeNode(node)
    }

    private fun InsnList.insertLoads(after: AbstractInsnNode, vararg specs: Pair<Int, Int>): AbstractInsnNode {
        var cur = after
        for ((opcode, slot) in specs) {
            val next = VarInsnNode(opcode, slot)
            insert(cur, next)
            cur = next
        }
        return cur
    }

    private fun InsnList.clearBetween(start: AbstractInsnNode, end: AbstractInsnNode) {
        var cur = start.next
        while (cur != end) {
            val next = cur.next
            remove(cur)
            cur = next
        }
    }

    private fun makeNode(data: ByteArray): ClassNode {
        val node = ClassNode()
        val reader = ClassReader(data)
        reader.accept(node, 0)
        return node
    }


    private fun writeNode(node: ClassNode): ByteArray {
        val writer = ClassWriter(ClassWriter.COMPUTE_MAXS or ClassWriter.COMPUTE_FRAMES)
        node.accept(writer)
        return writer.toByteArray()
    }
}