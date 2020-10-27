package org.olk90.inventorymanager.view.inventory

import javafx.scene.control.ContextMenu
import javafx.scene.control.TableView
import org.olk90.inventorymanager.logic.controller.getInventoryControllerInstance
import org.olk90.inventorymanager.logic.controller.getWorkspaceControllerInstance
import org.olk90.inventorymanager.model.InventoryItem
import org.olk90.inventorymanager.view.common.messages
import tornadofx.*

class InventoryContextMenu(private val table: TableView<InventoryItem>) : ContextMenu() {

    private val controller = getInventoryControllerInstance()

    init {
        item(messages("contextmenu.lend")).apply {
            action {
                val fragment = LendingFragment(table)
                getWorkspaceControllerInstance().getWorkspace().openInternalWindow(fragment, closeButton = false)
            }
        }
        item(messages("contextmenu.returned")).apply {
            action {
                val items = table.selectionModel.selectedItems
                controller.returnItems(items)
            }
        }
    }
}