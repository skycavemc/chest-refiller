package de.skycave.chestrefiller.listeners

import com.mongodb.client.model.Filters
import de.skycave.chestrefiller.ChestRefiller
import de.skycave.chestrefiller.models.Chest
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot

class InteractListener(private val main: ChestRefiller): Listener {

    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        if (event.hand == EquipmentSlot.OFF_HAND) {
            return
        }
        if (event.action != Action.LEFT_CLICK_BLOCK && event.action != Action.RIGHT_CLICK_BLOCK) {
            return
        }

        val player = event.player
        val name = main.chestSetMode[player] ?: return
        val block = event.clickedBlock ?: return
        if (block.type != Material.CHEST && block.type != Material.TRAPPED_CHEST) {
            return
        }

        val location = block.location
        val overlappingChest = main.chests.find(Filters.eq("lcoation", location)).first()
        if (overlappingChest != null) {
            main.messages.get("chest-create-overlap").replace("%name", overlappingChest.name).send(player)
            return
        }

        val chest = Chest()
        chest.name = name
        chest.location = location
        main.chests.insertOne(chest)
        main.messages.get("chest-create-success").replace("%name", name).send(player)
        main.chestSetMode.remove(player)
    }

}