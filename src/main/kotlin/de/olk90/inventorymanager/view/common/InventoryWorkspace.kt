package de.olk90.inventorymanager.view.common

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.octicons.OctIcon
import de.olk90.inventorymanager.logic.Config
import de.olk90.inventorymanager.logic.controller.WorkspaceController
import de.olk90.inventorymanager.view.inventory.InventoryView
import de.olk90.inventorymanager.view.person.PersonView
import javafx.beans.property.SimpleBooleanProperty
import tornadofx.*

class InventoryWorkspace : Workspace() {

    val controller:  WorkspaceController by inject()

    val enablePersonView = SimpleBooleanProperty(true)
    private val enableInventoryView = SimpleBooleanProperty(true)

    init {
        // remove default buttons
        forwardButton.hide()
        backButton.hide()
        refreshButton.hide()
        saveButton.hide()

        // enable/disable does not work -> implement ohn buttons
        createButton.hide()
        deleteButton.hide()

        button {
            addClass("icon-only")
            graphic = icon(FontAwesomeIcon.PLUS_CIRCLE)
            tooltip(messages("tooltip.insertData"))
            shortcut(ADD)
            action {
                controller.openCreateDialog()
            }
            enableWhen(controller.dataContainerOpen)
        }

        button {
            addClass("icon-only")
            graphic = icon(FontAwesomeIcon.MINUS_CIRCLE)
            tooltip(messages("tooltip.deleteData"))
            shortcut(DELETE)
            action {
                controller.deleteEntry(dockedComponent)
            }
            enableWhen(controller.dataContainerOpen)
        }

        // custom buttons
        button {
            tooltip(messages("tooltip.usedContainers"))
            addClass("icon-only")
            graphic = icon(OctIcon.HISTORY)
            shortcut(HISTORY)
            action {
                openInternalWindow(HistoryFragment::class, closeButton = false)
            }
            disableWhen(controller.history.sizeProperty.eq(0))
        }

        button {
            tooltip(messages("tooltip.newContainer"))
            addClass("icon-only")
            graphic = icon(OctIcon.DATABASE)
            shortcut(NEW_CONTAINER)
            action {
                openInternalWindow(DataContainerFragment::class, closeButton = false)
            }
        }

        button {
            tooltip(messages("tooltip.personOverview"))
            addClass("icon-only")
            graphic = icon(OctIcon.PERSON)
            shortcut(PERSON)
            action {
                if (dockedComponent !is PersonView) {
                    dock<PersonView>()
                    enablePersonView.set(false)
                    enableInventoryView.set(true)
                }
            }
            enableWhen(enablePersonView)
        }

        button {
            tooltip(messages("tooltip.inventoryOverview"))
            addClass("icon-only")
            graphic = icon(OctIcon.TOOLS)
            shortcut(INVENTORY)
            action {
                if (dockedComponent !is InventoryView) {
                    dock<InventoryView>()
                    enablePersonView.set(true)
                    enableInventoryView.set(false)
                }
            }
            enableWhen(enableInventoryView)
        }

        label(Config.model.identifierProperty)
    }
}