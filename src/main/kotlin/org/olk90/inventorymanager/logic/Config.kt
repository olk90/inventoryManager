package org.olk90.inventorymanager.logic

import com.beust.klaxon.Klaxon
import com.beust.klaxon.KlaxonException
import com.beust.klaxon.json
import org.olk90.inventorymanager.logic.controller.WorkspaceController
import tornadofx.*
import java.io.IOException
import java.nio.file.Paths

object Config {

    private val configDirectory = Paths.get(System.getProperty("user.home"), ".inventoryManager").toFile()

    @Throws(KlaxonException::class)
    fun loadConfigFile() {
        if (!configDirectory.exists()) {
            configDirectory.mkdirs()
        }
        val configFile = Paths.get(System.getProperty("user.home"), ".inventoryManager", "config.json").toFile()

        // initial setup of the file
        if (!configFile.exists()) {
            val fileContent = json {
                obj("history" to array())
            }.toJsonString(prettyPrint = true)
            configFile.writeText(fileContent)
        } else {
            // load contents from existing file
            try {
                val history = Klaxon().parse<History>(configFile)
                if (history != null) {
                    find(WorkspaceController::class).setHistory(history)
                }
            } catch (e: KlaxonException) {
                e.printStackTrace()
            }
        }
    }
}

// helpers to keep track of the used data containers
data class HistoryEntry(val filePath: String, val lastAccessTime: Long)
data class History(val history: ArrayList<HistoryEntry>)