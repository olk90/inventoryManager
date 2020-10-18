package org.olk90.inventorymanager.view.inventory

import javafx.geometry.Pos
import javafx.scene.control.cell.CheckBoxTableCell
import org.olk90.inventorymanager.logic.controller.InventoryController
import org.olk90.inventorymanager.logic.controller.ObjectStore
import org.olk90.inventorymanager.model.InventoryItem
import org.olk90.inventorymanager.view.common.align
import tornadofx.*
import tornadofx.controlsfx.columnfilter

class InventoryView : View("Inventory Overview") {

    private val controller: InventoryController by inject()

    override val root = borderpane {
        center {
            tableview(ObjectStore.inventoryItems) {
                columnResizePolicy = SmartResize.POLICY

                column("Name", InventoryItem::nameProperty).apply {
                    align(Pos.CENTER)
                    pctWidth(25.0)
                }
                column("Available", InventoryItem::availableProperty).apply {
                    align(Pos.CENTER)
                    pctWidth(25.0)
                    setCellFactory { CheckBoxTableCell() }
                }
                column("Lending Date", InventoryItem::lendingDateProperty).apply {
                    align(Pos.CENTER)
                    pctWidth(25.0)
                }
                column("Lender", InventoryItem::lenderNameProperty).apply {
                    align(Pos.CENTER)
                    pctWidth(25.0)
                    columnfilter {}
                }

                // Update the person inside the view model on selection change
                controller.model.rebindOnChange(this) {
                    item = it ?: InventoryItem()
                }
            }
        }
        right {
            add(InventoryDataFragment())
        }
    }

}
