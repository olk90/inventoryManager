package org.olk90.inventorymanager.view.inventory

import javafx.geometry.Pos
import javafx.scene.control.TableView
import javafx.scene.control.cell.CheckBoxTableCell
import org.olk90.inventorymanager.logic.controller.InventoryController
import org.olk90.inventorymanager.logic.controller.ObjectStore
import org.olk90.inventorymanager.model.InventoryItem
import org.olk90.inventorymanager.view.common.PersonConverter
import org.olk90.inventorymanager.view.common.align
import tornadofx.*

class InventoryView : View("Inventory Overview") {

    private val controller: InventoryController by inject()
    lateinit var table: TableView<InventoryItem>

    init {
        controller.tableItems.addAll(ObjectStore.inventoryItems)
    }

    override val root = borderpane {
        center {
            vbox {
                textfield {
                    promptText = "Search"
                    controller.configureFilterListener(this)
                }
                tableview(controller.tableItems) {
                    table = this
                    fitToParentSize()
                    multiSelect(true)
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
                    column("Lender", InventoryItem::lenderProperty).apply {
                        converter(PersonConverter())
                        align(Pos.CENTER)
                        pctWidth(25.0)
                    }

                    contextMenu = InventoryContextMenu(this)

                    // Update the person inside the view model on selection change
                    controller.model.rebindOnChange(this) {
                        item = it ?: InventoryItem()
                    }
                }
            }
        }
        right {
            add(InventoryDataFragment())
        }
    }

}
