package de.skycave.chestrefiller.commands

import de.skycave.chestrefiller.ChestRefiller
import de.skycave.chestrefiller.enums.Message
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.util.StringUtil

class ChestRefillCommand(private val main: ChestRefiller): CommandExecutor, TabCompleter {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) {
            sendHelp(sender)
            return true
        }

        when (args[0].lowercase()) {
            "chest" -> {
                if (args.size < 2) {
                    sendHelp(sender)
                    return true
                }
                return ChestRefillChestSubcommand().apply(sender, args)
            }
            "template" -> {
                if (args.size < 2) {
                    sendHelp(sender)
                    return true
                }
                return ChestRefillTemplateSubcommand().apply(sender, args)
            }
            "help" -> sendHelp(sender)
            else -> Message.COMMAND_UNKNOWN.get().send(sender)
        }
        return true
    }

    private fun sendHelp(sender: CommandSender) {
        Message.CHEST_CREATE_HELP.get().send(sender)
        Message.CHEST_ABORT_HELP.get().send(sender)
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): MutableList<String> {
        var arguments = emptyList<String>()
        val completions = ArrayList<String>()

        if (args.size == 1) {
            arguments = listOf("chest", "template", "help")
            StringUtil.copyPartialMatches(args[0], arguments, completions)
        }
        if (args.size == 2) {
            when (args[0].lowercase()) {
                "chest" -> {
                    arguments = listOf("create", "abort", "list", "remove", "info")
                }
                "template" -> {
                    arguments = listOf("create", "list", "remove", "info")
                }
            }
            StringUtil.copyPartialMatches(args[1], arguments, completions)
        }
        // TODO remove and info database lookup

        completions.sort()
        return completions
    }

}