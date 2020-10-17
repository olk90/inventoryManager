package org.olk90.inventorymanager.view.inventory

import javafx.geometry.Pos
import org.olk90.inventorymanager.logic.Config
import org.olk90.inventorymanager.logic.controller.InventoryController
import org.olk90.inventorymanager.logic.controller.ObjectStore
import org.olk90.inventorymanager.model.InventoryItem
import org.olk90.inventorymanager.view.common.PersonConverter
import org.olk90.inventorymanager.view.common.align
import tornadofx.*

class InventoryView : View("${Config.identifierProperty.value}: Inventory") {

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
                }
                column("Lending Date", InventoryItem::lendingDateProperty).apply {
                    align(Pos.CENTER)
                    pctWidth(25.0)
                }
                column("Lender", InventoryItem::lenderProperty).apply {
                    align(Pos.CENTER)
                    pctWidth(25.0)
                    converter(PersonConverter())
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
