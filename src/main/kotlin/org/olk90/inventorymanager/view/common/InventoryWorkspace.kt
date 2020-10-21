package org.olk90.inventorymanager.view.common

import de.jensd.fx.glyphs.octicons.OctIcon
import org.olk90.inventorymanager.logic.Config
import org.olk90.inventorymanager.logic.controller.WorkspaceController
import org.olk90.inventorymanager.view.inventory.InventoryView
import org.olk90.inventorymanager.view.person.PersonView
import tornadofx.*

class InventoryWorkspace : Workspace() {

    val controller:  WorkspaceController by inject()

    init {
        // remove obsolete buttons
        forwardButton.hide()
        backButton.hide()
        refreshButton.hide()
        saveButton.hide()

        createButton.apply {
            tooltip("Insert new data")
            action {
                controller.openCreateDialog()
            }
        }

        deleteButton.apply {
            tooltip("Delete selected data")
            action {
                controller.deleteEntry(dockedComponent)
            }
        }

        // custom buttons
        button {
            tooltip("Recently used data containers")
            addClass("icon-only")
            graphic = icon(OctIcon.HISTORY)
            action {
                openInternalWindow(HistoryFragment::class, closeButton = false)
            }
        }

        button {
            tooltip("Create new data container")
            addClass("icon-only")
            graphic = icon(OctIcon.DATABASE)
            action {
                openInternalWindow(DataContainerFragment::class, closeButton = false)
            }
        }

        button {
            tooltip("Open person overview")
            addClass("icon-only")
            graphic = icon(OctIcon.PERSON)
            action {
                if (dockedComponent !is PersonView) {
                    dock<PersonView>()
                }
            }
        }

        button {
            tooltip("Open inventory overview")
            addClass("icon-only")
            graphic = icon(OctIcon.TOOLS)
            action {
                if (dockedComponent !is InventoryView) {
                    dock<InventoryView>()
                }
            }
        }

        label(Config.model.identifierProperty)
    }
}