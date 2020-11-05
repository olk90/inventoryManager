package de.olk90.inventorymanager.view.inventory

import javafx.scene.control.ContextMenu
import javafx.scene.control.TableView
import de.olk90.inventorymanager.logic.controller.getInventoryControllerInstance
import de.olk90.inventorymanager.logic.controller.getWorkspaceControllerInstance
import de.olk90.inventorymanager.model.InventoryItem
import de.olk90.inventorymanager.view.common.messages
import javafx.beans.property.SimpleBooleanProperty
import tornadofx.*

class InventoryContextMenu(private val table: TableView<InventoryItem>) : ContextMenu() {

    private val controller = getInventoryControllerInstance()

    val selectedItemAvailable = SimpleBooleanProperty(false)

    init {
        val selectionModel = table.selectionModel
        item(messages("contextmenu.lend")).apply {
            action {
                val fragment = LendingFragment(table)
                getWorkspaceControllerInstance().getWorkspace().openInternalWindow(fragment, closeButton = false)
            }
            enableWhen(selectionModel.selectedItems.sizeProperty.eq(1).and(selectedItemAvailable))
        }
        item(messages("contextmenu.returned")).apply {
            action {
                val items = selectionModel.selectedItems
                controller.returnItems(items)
            }
            enableWhen(selectionModel.selectedItems.sizeProperty.eq(1).and(selectedItemAvailable.not()))
        }
        item(messages("contextmenu.history")).apply {
            action {
                val view = controller.setupView(table.selectedItem!!)
                view.openModal()
            }
            enableWhen(selectionModel.selectedItems.sizeProperty.eq(1))
        }
    }
}