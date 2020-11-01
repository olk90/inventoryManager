package de.olk90.inventorymanager.logic.controller

import com.beust.klaxon.Klaxon
import com.beust.klaxon.KlaxonException
import com.beust.klaxon.json
import de.olk90.inventorymanager.logic.Config
import de.olk90.inventorymanager.logic.History
import de.olk90.inventorymanager.logic.HistoryEntry
import de.olk90.inventorymanager.model.DataContainer
import de.olk90.inventorymanager.model.InventoryItem
import de.olk90.inventorymanager.model.LendingHistoryRecord
import de.olk90.inventorymanager.model.Person
import de.olk90.inventorymanager.view.common.InventoryWorkspace
import de.olk90.inventorymanager.view.common.messages
import de.olk90.inventorymanager.view.inventory.InventoryDataFragment
import de.olk90.inventorymanager.view.inventory.InventoryView
import de.olk90.inventorymanager.view.person.PersonDataFragment
import de.olk90.inventorymanager.view.person.PersonView
import tornadofx.*
import java.io.File
import java.nio.file.Paths
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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

    fun deleteEntry(dockedComponent: UIComponent?) {
        when (dockedComponent) {
            is PersonView -> {
                val selectedItems = dockedComponent.table.selectionModel.selectedItems
                getPersonControllerInstance().delete(selectedItems)
            }
            is InventoryView -> {
                val selectedItems = dockedComponent.table.selectionModel.selectedItems
                getInventoryControllerInstance().delete(selectedItems)
            }
            else -> {
                error(messages("error.header.delete"), messages("error.content.view"))
            }
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
                error(messages("error.header.add"), messages("error.content.view"))
            }
        }
    }

    fun openDataContainer(documentPath: String) {
        try {
            val dc = Klaxon().parse<DataContainer>(File(documentPath))
            if (dc != null) {
                dc.items.forEach {
                    if (!it.lendingDateString.isNullOrEmpty()) {
                        val formatter = DateTimeFormatter.ISO_LOCAL_DATE
                        it.lendingDate = LocalDate.parse(it.lendingDateString, formatter)
                    }
                    if (!it.nextMotString.isNullOrEmpty()) {
                        val formatter = DateTimeFormatter.ISO_LOCAL_DATE
                        it.nextMot = LocalDate.parse(it.nextMotString, formatter)
                    }

                }
                dc.history.forEach {
                    if (!it.lendingDateString.isNullOrEmpty()) {
                        val formatter = DateTimeFormatter.ISO_LOCAL_DATE
                        it.lendingDate = LocalDate.parse(it.lendingDateString, formatter)
                    }
                    if (!it.returnDateString.isNullOrEmpty()) {
                        val formatter = DateTimeFormatter.ISO_LOCAL_DATE
                        it.returnDate = LocalDate.parse(it.returnDateString, formatter)
                    }
                }
                Config.model.identifierProperty.value = dc.identifier
                Config.model.pathProperty.value = documentPath
                ObjectStore.fillStore(dc.persons, dc.items, dc.history)
                updateHistory(documentPath)
            } else {
                error(messages("error.header.open"), messages("error.content.path", documentPath))
            }
        } catch (e: KlaxonException) {
            error(messages("error.header.exception"), e.stackTraceToString())
        }
    }

    @Throws(KlaxonException::class)
    fun buildDcFile(identifier: String, persons: List<Person>, items: List<InventoryItem>, history: List<LendingHistoryRecord>): String {
        return json {
            obj(
                    "identifier" to identifier,
                    "persons" to array(persons),
                    "items" to array(items),
                    "history" to array(history)
            )
        }.toJsonString(prettyPrint = true)
    }

    fun writeDcFile() {
        val fileContent = buildDcFile(
                Config.model.identifierProperty.value,
                ObjectStore.persons,
                ObjectStore.inventoryItems,
                ObjectStore.history
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

    fun getWorkspace(): InventoryWorkspace {
        return workspace as InventoryWorkspace
    }
}
