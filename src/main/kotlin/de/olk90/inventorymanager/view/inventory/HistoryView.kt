package de.olk90.inventorymanager.view.inventory

import de.olk90.inventorymanager.logic.controller.InventoryController
import de.olk90.inventorymanager.model.InventoryItem
import de.olk90.inventorymanager.model.LendingHistoryRecord
import de.olk90.inventorymanager.view.common.PersonConverter
import de.olk90.inventorymanager.view.common.align
import de.olk90.inventorymanager.view.common.messages
import javafx.geometry.Pos
import tornadofx.*

class HistoryView(item: InventoryItem) : View() {

    val controller: InventoryController by inject()

    override val root = borderpane {

        prefWidth = 800.0
        prefHeight = 600.0

        top {
            form {
                fieldset {
                    field(messages("inventoryItem.name") + ":") {
                        label(item.name)
                    }
                }
            }
        }
        center {
            vbox {
                tableview(controller.historyItems) {
                    fitToParentSize()
                    multiSelect(true)
                    makeIndexColumn("#").apply {
                        align(Pos.CENTER)
                    }

                    tableMenuButtonVisibleProperty().set(true)
                    columnResizePolicy = SmartResize.POLICY

                    column(messages("history.lendingDate"), LendingHistoryRecord::lendingDate).apply {
                        align(Pos.CENTER)
                        pctWidth(33.0)
                    }
                    column(messages("history.returnDate"), LendingHistoryRecord::returnDate).apply {
                        align(Pos.CENTER)
                        pctWidth(33.0)
                    }
                    column(messages("history.lender"), LendingHistoryRecord::lenderProperty).apply {
                    converter(PersonConverter())
                    align(Pos.CENTER)
                    pctWidth(33.0)
                }
                }
            }
        }
    }
}
