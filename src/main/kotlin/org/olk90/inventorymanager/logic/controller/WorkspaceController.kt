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
import java.io.IOException

fun getWorkspaceControllerInstance(): WorkspaceController {
    return find(WorkspaceController::class)
}

class WorkspaceController : Controller() {

    // used for managing data containers
    val history = mutableListOf<HistoryEntry>().asObservable()

    // currently opened file

    fun setHistory(h: History) {
        history.clear()
        history.addAll(h.history)
        history.sortByDescending { it.lastAccessTime }
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

    @Throws(KlaxonException::class, IOException::class)
    fun openDataContainer(documentPath: String) {
        val dc = Klaxon().parse<DataContainer>(documentPath)
        if (dc != null) {
            Config.model.pathProperty.value = documentPath
            ObjectStore.fillStore(dc.persons, dc.items)
        } else {
            throw IOException()
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
                "",
                ObjectStore.persons,
                ObjectStore.inventoryItems
        )
        File(Config.model.pathProperty.value).writeText(fileContent)
    }
}
