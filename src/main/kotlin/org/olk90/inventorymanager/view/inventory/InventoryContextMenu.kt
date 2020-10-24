package org.olk90.inventorymanager.view.inventory

import javafx.scene.control.ContextMenu
import javafx.scene.control.TableView
import org.olk90.inventorymanager.logic.controller.getInventoryControllerInstance
import org.olk90.inventorymanager.logic.controller.getWorkspaceControllerInstance
import org.olk90.inventorymanager.model.InventoryItem
import tornadofx.*

class InventoryContextMenu(private val table: TableView<InventoryItem>) : ContextMenu() {

    private val controller = getInventoryControllerInstance()

    init {
        item("Lend").apply {
            action {
                val fragment = LendingFragment(table)
                getWorkspaceControllerInstance().getWorkspace().openInternalWindow(fragment, closeButton = false)
            }
        }
        item("Returned").apply {
            action {
                val item = table.selectedItem
                if (item != null) {
                    controller.returnItem(item)
                }
            }
        }
    }
}