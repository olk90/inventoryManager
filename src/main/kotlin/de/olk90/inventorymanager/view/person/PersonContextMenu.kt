package de.olk90.inventorymanager.view.person

import de.olk90.inventorymanager.logic.controller.getInventoryControllerInstance
import de.olk90.inventorymanager.logic.controller.getPersonControllerInstance
import de.olk90.inventorymanager.logic.controller.getWorkspaceControllerInstance
import de.olk90.inventorymanager.model.Person
import de.olk90.inventorymanager.view.common.messages
import javafx.beans.property.SimpleBooleanProperty
import javafx.scene.control.ContextMenu
import javafx.scene.control.TableView
import tornadofx.*

class PersonContextMenu(private val table: TableView<Person>) : ContextMenu() {

    private val controller = getPersonControllerInstance()

    val enableEmail = SimpleBooleanProperty(false)

    init {
        item(messages("contextmenu.lendDevices")).apply {
            action {
                val fragment = MultiLendingFragment()
                getInventoryControllerInstance().multiLendingModel.lender.value = table.selectedItem!!
                getWorkspaceControllerInstance().getWorkspace().openInternalWindow(fragment, closeButton = false)
            }
            disableWhen(table.selectionModel.selectedItemProperty().isNull)
        }
        item(messages("contextmenu.emailTo")).apply {
            action {
                val person = table.selectedItem
                if (person != null) {
                    controller.emailTo(person)
                }
            }
            enableWhen(enableEmail)
        }
    }
}