package org.olk90.inventorymanager.logic.controller

import com.beust.klaxon.KlaxonException
import com.beust.klaxon.json
import org.olk90.inventorymanager.logic.History
import org.olk90.inventorymanager.logic.HistoryEntry
import org.olk90.inventorymanager.model.InventoryItem
import org.olk90.inventorymanager.model.Person
import org.olk90.inventorymanager.view.inventory.InventoryDataFragment
import org.olk90.inventorymanager.view.inventory.InventoryView
import org.olk90.inventorymanager.view.person.PersonDataFragment
import org.olk90.inventorymanager.view.person.PersonView
import tornadofx.*

open class WorkspaceController : Controller() {

    // used for managing data containers
    val history = mutableListOf<HistoryEntry>().asObservable()

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

    fun openDataContainer(path: String) {
        TODO("Not yet implemented")
    }

    @Throws(KlaxonException::class)
    fun writeDcFile(identifier: String, persons: List<Person>, items: List<InventoryItem>): String {
        return json {
            obj(
                    "identifier" to identifier,
                    "persons" to array(persons),
                    "items" to array(items)
            )
        }.toJsonString(prettyPrint = true)
    }
}
