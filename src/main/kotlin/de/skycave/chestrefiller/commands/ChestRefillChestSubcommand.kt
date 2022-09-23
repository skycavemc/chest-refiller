package de.skycave.chestrefiller.commands

import com.mongodb.client.model.Filters
import de.skycave.chestrefiller.ChestRefiller
import de.skycave.chestrefiller.enums.Message
import de.skycave.chestrefiller.utils.FormattingUtils
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class ChestRefillChestSubcommand: java.util.function.BiFunction<CommandSender, Array<out String>, Boolean> {

    private val main = JavaPlugin.getPlugin(ChestRefiller::class.java)

    override fun apply(sender: CommandSender, args: Array<out String>): Boolean {
        when (args[1].lowercase()) {
            "create" -> {
                if (sender !is Player) {
                    Message.PLAYER_ONLY.get().send(sender)
                    return true
                }
                if (main.chestSetMode.containsKey(sender)) {
                    Message.CHEST_ABORT.get().send(sender)
                    main.chestSetMode.remove(sender)
                    return true
                }
                if (args.size < 3) {
                    Message.CHEST_CREATE_SYNTAX.get().send(sender)
                    return true
                }
                val name = args[2].lowercase()
                val chest = main.chests.find(Filters.eq("name", name)).first()
                if (chest != null) {
                    Message.CHEST_CREATE_EXISTS.get().replace("%name", name).send(sender)
                    return true
                }
                main.chestSetMode[sender] = name
                Message.CHEST_CREATE_START.get().send(sender)
                return true
            }
            "abort" -> {
                if (sender !is Player) {
                    Message.PLAYER_ONLY.get().send(sender)
                    return true
                }
                if (main.chestSetMode.containsKey(sender)) {
                    Message.CHEST_ABORT.get().send(sender)
                    main.chestSetMode.remove(sender)
                    return true
                }
                Message.CHEST_ABORT_ERROR.get().send(sender)
                return true
            }
            "list" -> {
                val chests = main.chests.find()
                for (chest in chests) {
                    Message.CHEST_LIST_ENTRY.get()
                        .replace("%name", chest.name)
                        .replace("%location", FormattingUtils.locationAsString(chest.location))
                        .send(sender)
                }
                return true
            }
            "remove" -> {
                TODO("remove command")
            }
            "refill" -> {
                TODO("refill command")
            }
            else -> {
                Message.COMMAND_UNKNOWN.get().send(sender)
                return true
            }
        }
    }

}