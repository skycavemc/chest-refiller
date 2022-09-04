package de.skycave.chestrefiller.models

import com.google.common.io.Files
import com.google.common.io.Resources
import de.skycave.chestrefiller.annotations.CreateDataFolder
import de.skycave.chestrefiller.annotations.Prefix
import org.apache.commons.lang3.Validate
import org.bukkit.command.CommandExecutor
import org.bukkit.command.PluginCommand
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.IOException

abstract class SkyCavePlugin : JavaPlugin() {

    var prefix = ""
        private set

    override fun onEnable() {
        val clazz: Class<out SkyCavePlugin?> = this@SkyCavePlugin.javaClass
        if (clazz.isAnnotationPresent(CreateDataFolder::class.java)) {
            if (!dataFolder.isDirectory) {
                dataFolder.mkdirs()
            }
        }
        if (clazz.isAnnotationPresent(Prefix::class.java)) {
            prefix = clazz.getAnnotation(Prefix::class.java).value
        }
    }

    fun registerCommand(command: String, executor: CommandExecutor?) {
        val cmd: PluginCommand? = getCommand(command)
        if (cmd == null) {
            logger.severe("No entry for the command $command found in the plugin.yml.")
            return
        }
        cmd.setExecutor(executor)
    }

    fun registerEvents(vararg events: Listener) {
        Validate.notNull(events)
        for (event in events) {
            server.pluginManager.registerEvents(event, this)
        }
    }

    @Suppress("UnstableApiUsage")
    fun copyResource(resourceName: String): Boolean {
        Validate.notNull(resourceName)
        val destination = File(dataFolder, resourceName)
        val resource = javaClass.classLoader.getResource(resourceName)
        if (resource == null) {
            logger.severe("The resource $resourceName does not exist.")
            return false
        }
        if (destination.exists()) {
            logger.info("The file $resourceName already exists.")
            return true
        }
        try {
            destination.createNewFile()
            Resources.asByteSource(resource).copyTo(Files.asByteSink(destination))
            logger.info("The file $resourceName has been created.")
            return true
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return false
    }
}