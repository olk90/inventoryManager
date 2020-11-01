package de.olk90.inventorymanager.view.common

import de.jensd.fx.glyphs.octicons.OctIcon
import de.olk90.inventorymanager.logic.Config
import de.olk90.inventorymanager.logic.controller.WorkspaceController
import de.olk90.inventorymanager.view.inventory.InventoryView
import de.olk90.inventorymanager.view.person.PersonView
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
            tooltip(messages("tooltip.insertData"))
            action {
                controller.openCreateDialog()
            }
        }

        deleteButton.apply {
            tooltip(messages("tooltip.deleteData"))
            action {
                controller.deleteEntry(dockedComponent)
            }
        }

        // custom buttons
        button {
            tooltip(messages("tooltip.usedContainers"))
            addClass("icon-only")
            graphic = icon(OctIcon.HISTORY)
            action {
                openInternalWindow(HistoryFragment::class, closeButton = false)
            }
        }

        button {
            tooltip(messages("tooltip.newContainer"))
            addClass("icon-only")
            graphic = icon(OctIcon.DATABASE)
            action {
                openInternalWindow(DataContainerFragment::class, closeButton = false)
            }
        }

        button {
            tooltip(messages("tooltip.personOverview"))
            addClass("icon-only")
            graphic = icon(OctIcon.PERSON)
            action {
                if (dockedComponent !is PersonView) {
                    dock<PersonView>()
                }
            }
        }

        button {
            tooltip(messages("tooltip.inventoryOverview"))
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