package de.olk90.inventorymanager.view.inventory

import javafx.scene.control.ContextMenu
import javafx.scene.control.TableView
import de.olk90.inventorymanager.logic.controller.getInventoryControllerInstance
import de.olk90.inventorymanager.logic.controller.getWorkspaceControllerInstance
import de.olk90.inventorymanager.model.InventoryItem
import de.olk90.inventorymanager.view.common.messages
import tornadofx.*

class InventoryContextMenu(private val table: TableView<InventoryItem>) : ContextMenu() {

    private val controller = getInventoryControllerInstance()

    init {
        item(messages("contextmenu.lend")).apply {
            action {
                val fragment = LendingFragment(table)
                getWorkspaceControllerInstance().getWorkspace().openInternalWindow(fragment, closeButton = false)
            }
//            disableWhen {
//                val notAvailable = table.selectionModel.selectedItems.filter { !it.available }.asObservable()
//                notAvailable.sizeProperty.greaterThan(0)
//            }
        }
        item(messages("contextmenu.returned")).apply {
            action {
                val items = table.selectionModel.selectedItems
                controller.returnItems(items)
            }
        }
        item(messages("contextmenu.history")).apply {
            action {
                val view = controller.setupView(table.selectedItem!!)
                view.openModal()
//                getWorkspaceControllerInstance().getWorkspace().openInternalWindow(view, closeButton = true)
            }
            enableWhen(table.selectionModel.selectedItems.sizeProperty.eq(1))
        }
    }
}