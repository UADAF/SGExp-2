package com.uadaf.sgexp.commands

import com.uadaf.sgexp.structure.GateBuilder
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.server.MinecraftServer
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.TextComponentTranslation

object SGExpCommands : CommandBase() {
    override fun getName() = "sgexp"

    override fun getAliases() = listOf("sge")

    override fun execute(server: MinecraftServer, sender: ICommandSender, args: Array<String>) {
        if(args.isEmpty()) {
            sender.sendMessage(TextComponentTranslation(getUsage(sender)))
        } else {
            when(args[0]) {
                "help" -> sender.sendMessage(TextComponentTranslation("message.sgexp.help"))
                "gate" -> {
                    val ppos = sender.position
                    val x = parseCoordinate(ppos.x.toDouble(), args.getOrElse(1) { "~" }, true)
                    val y = parseCoordinate(ppos.y.toDouble(), args.getOrElse(2) { "~" }, -4096, 4096, false)
                    val z = parseCoordinate(ppos.z.toDouble(), args.getOrElse(3) { "~" }, true)
                    val pos = BlockPos(x.result, y.result, z.result)
                    GateBuilder.buildGate(sender.entityWorld, pos)
                }
            }
        }
    }

    override fun getUsage(sender: ICommandSender): String {
        return "message.sgexp.usage"
    }

    override fun getTabCompletions(server: MinecraftServer, sender: ICommandSender, args: Array<String>, targetPos: BlockPos?): List<String> {
        if (args.isEmpty()) {
            return listOf("gate", "help")
        }
        return emptyList()
    }
}