package de.skycave.chestrefiller.commands

import com.mongodb.client.model.Filters
import de.skycave.chestrefiller.ChestRefiller
import de.skycave.chestrefiller.enums.Message
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

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
            else -> {
                Message.COMMAND_UNKNOWN.get().send(sender)
                return true
            }
        }
    }

    private fun sendHelp(sender: CommandSender) {
        TODO("send help")
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>?
    ): MutableList<String>? {
        TODO("Not yet implemented")
    }

}