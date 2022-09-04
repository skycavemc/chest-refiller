package de.skycave.chestrefiller.listeners

import de.skycave.chestrefiller.ChestRefiller
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
        if (!main.chestSetMode.containsKey(player)) {
            return
        }
        TODO("Check if locations are overlapping")
        TODO("Create chest")
    }

}