package de.skycave.chestrefiller.commands

import de.skycave.chestrefiller.ChestRefiller
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import java.util.function.BiFunction

class ChestRefillTemplateSubcommand: BiFunction<CommandSender, Array<out String>, Boolean> {

    private val main = JavaPlugin.getPlugin(ChestRefiller::class.java)

    override fun apply(sender: CommandSender, args: Array<out String>): Boolean {
        when (args[1].lowercase()) {
            "create" -> {
                TODO("create subcommand")
            }
            "remove" -> {
                TODO("remove subcommand")
            }
            "list" -> {
                TODO("list subcommand")
            }
            "info" -> {
                TODO("info subcommand")
            }
            else -> {
                main.messages.get("command-unknown").send(sender)
                return true
            }
        }
    }

}