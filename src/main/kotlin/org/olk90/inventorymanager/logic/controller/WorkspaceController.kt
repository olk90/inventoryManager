package org.olk90.inventorymanager.logic.controller

import com.beust.klaxon.Klaxon
import com.beust.klaxon.KlaxonException
import com.beust.klaxon.json
import org.olk90.inventorymanager.logic.Config
import org.olk90.inventorymanager.logic.History
import org.olk90.inventorymanager.logic.HistoryEntry
import org.olk90.inventorymanager.model.DataContainer
import org.olk90.inventorymanager.model.InventoryItem
import org.olk90.inventorymanager.model.Person
import org.olk90.inventorymanager.view.inventory.InventoryDataFragment
import org.olk90.inventorymanager.view.inventory.InventoryView
import org.olk90.inventorymanager.view.person.PersonDataFragment
import org.olk90.inventorymanager.view.person.PersonView
import tornadofx.*
import java.io.File
import java.nio.file.Paths

fun getWorkspaceControllerInstance(): WorkspaceController {
    return find(WorkspaceController::class)
}

class WorkspaceController : Controller() {

    // used for managing data containers
    val history = mutableListOf<HistoryEntry>().asObservable()

    private fun setHistory(h: History) {
        history.clear()
        history.addAll(h.history)
        history.sortByDescending { it.lastAccessTime }
        if (history.isNotEmpty()) {
            Config.pathProperty.value = history.first().filePath
            writeHistory(history)
        }
    }

    fun openCreateDialog() {
        when (workspace.dockedComponent) {
            is PersonView -> {
                workspace.openInternalWindow(PersonDataFragment(true), closeButton = false)
            }
            is InventoryView -> {
                workspace.openInternalWindow(InventoryDataFragment(true), closeButton = false)
            }
            else -> {
                error("Cannot add data", "Please open the view that shall be addressed")
            }
        }
    }

    fun openDataContainer(documentPath: String) {
        try {
            val dc = Klaxon().parse<DataContainer>(File(documentPath))
            if (dc != null) {
                Config.model.identifierProperty.value = dc.identifier
                Config.model.pathProperty.value = documentPath
                ObjectStore.fillStore(dc.persons, dc.items)
                updateHistory(documentPath)
            } else {
                error("Cannot open data container", "Please check file $documentPath")
            }
        } catch (e: KlaxonException) {
            error("Something went terribly wrong", e.stackTraceToString())
        }
    }

    @Throws(KlaxonException::class)
    fun buildDcFile(identifier: String, persons: List<Person>, items: List<InventoryItem>): String {
        return json {
            obj(
                    "identifier" to identifier,
                    "persons" to array(persons),
                    "items" to array(items)
            )
        }.toJsonString(prettyPrint = true)
    }

    fun writeDcFile() {
        val fileContent = buildDcFile(
                Config.model.identifierProperty.value,
                ObjectStore.persons,
                ObjectStore.inventoryItems
        )
        File(Config.model.pathProperty.value).writeText(fileContent)
    }

    @Throws(KlaxonException::class)
    fun loadConfigFile() {
        if (!Config.configDirectory.exists()) {
            Config.configDirectory.mkdirs()
        }
        val configFile = Paths.get(System.getProperty("user.home"), ".inventoryManager", "config.json").toFile()

        // initial setup of the file
        if (!configFile.exists()) {
            writeHistory()
        } else {
            // load contents from existing file
            try {
                val history = Klaxon().parse<History>(configFile)
                if (history != null) {
                    setHistory(history)
                }
            } catch (e: KlaxonException) {
                e.printStackTrace()
            }
        }
    }

    private fun writeHistory(entries: List<HistoryEntry> = emptyList()) {
        val configFile = Paths.get(System.getProperty("user.home"), ".inventoryManager", "config.json").toFile()
        val fileContent = json {
            obj("history" to array(entries))
        }.toJsonString(prettyPrint = true)
        configFile.writeText(fileContent)
    }

    fun updateHistory(filePath: String) {
        val entry = history.find { it.filePath == filePath }
        if (entry != null) {
            entry.lastAccessTime = System.currentTimeMillis()
        } else {
            history.add(HistoryEntry(filePath, System.currentTimeMillis()))
        }
        writeHistory(history)

    }
}
