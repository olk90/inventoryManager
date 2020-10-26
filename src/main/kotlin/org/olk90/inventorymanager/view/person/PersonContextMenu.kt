package org.olk90.inventorymanager.view.person

import javafx.scene.control.ContextMenu
import javafx.scene.control.TableView
import org.olk90.inventorymanager.logic.controller.getInventoryControllerInstance
import org.olk90.inventorymanager.logic.controller.getPersonControllerInstance
import org.olk90.inventorymanager.logic.controller.getWorkspaceControllerInstance
import org.olk90.inventorymanager.model.Person
import tornadofx.*
import tornadofx.FX.Companion.messages

class PersonContextMenu(private val table: TableView<Person>) : ContextMenu() {

    private val controller = getPersonControllerInstance()

    init {
        item(messages["contextmenu.lendDevices"]).apply {
            action {
                val fragment = MultiLendingFragment()
                getInventoryControllerInstance().multiLendingModel.lender.value = table.selectedItem!!
                getWorkspaceControllerInstance().getWorkspace().openInternalWindow(fragment, closeButton = false)
            }
            disableWhen(table.selectionModel.selectedItemProperty().isNull)
        }
        item(messages["contextmenu.emailTo"]).apply {
            action {
                val person = table.selectedItem
                if (person != null) {
                    controller.emailTo(person)
                }
            }
        }
    }
}