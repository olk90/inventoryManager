package org.olk90.inventorymanager.view.inventory

import javafx.geometry.Pos
import javafx.scene.control.TableView
import javafx.scene.control.cell.CheckBoxTableCell
import javafx.scene.paint.Color
import org.olk90.inventorymanager.logic.controller.InventoryController
import org.olk90.inventorymanager.logic.controller.ObjectStore
import org.olk90.inventorymanager.model.InventoryItem
import org.olk90.inventorymanager.view.common.PersonConverter
import org.olk90.inventorymanager.view.common.align
import org.olk90.inventorymanager.view.common.messages
import tornadofx.*
import java.time.LocalDate
import java.time.Period
import java.time.chrono.ChronoLocalDate
import java.time.format.DateTimeFormatter
import javax.swing.text.DateFormatter

class InventoryView : View(messages("label.inventoryOverview")) {

    private val controller: InventoryController by inject()
    lateinit var table: TableView<InventoryItem>

    init {
        controller.tableItems.addAll(ObjectStore.inventoryItems)
    }

    override val root = borderpane {
        center {
            vbox {
                textfield {
                    promptText = messages("label.search")
                    controller.configureFilterListener(this)
                }
                tableview(controller.tableItems) {
                    table = this
                    fitToParentSize()
                    multiSelect(true)
                    columnResizePolicy = SmartResize.POLICY

                    column(messages("inventoryItem.name"), InventoryItem::nameProperty).apply {
                        align(Pos.CENTER)
                        pctWidth(20.0)
                    }
                    column(messages("inventoryItem.available"), InventoryItem::availableProperty).apply {
                        align(Pos.CENTER)
                        pctWidth(20.0)
                        setCellFactory { CheckBoxTableCell() }
                    }
                    column(messages("inventoryItem.lendingDate"), InventoryItem::lendingDateProperty).apply {
                        align(Pos.CENTER)
                        pctWidth(20.0)
                    }
                    column(messages("inventoryItem.lender"), InventoryItem::lenderProperty).apply {
                        converter(PersonConverter())
                        align(Pos.CENTER)
                        pctWidth(20.0)
                    }
                    column(messages("inventoryItem.nextMot"), InventoryItem::nextMotProperty).apply {
                        converter(MotConverter())
                        pctWidth(20.0)
                        cellFormat {
                            style {

                                val period = Period.between(LocalDate.now(), it)
                                alignment = Pos.CENTER
                                text = it.format(DateTimeFormatter.ofPattern("MMM/yyyy"))
                                when {
                                    period.months == 2 -> {
                                        backgroundColor += Color.YELLOW
                                        textFill = Color.BLACK
                                    }
                                    period.months == 1 -> {
                                        backgroundColor += Color.ORANGE
                                        textFill = Color.BLACK
                                    }
                                    period.months == 0 -> {
                                        backgroundColor += Color.DARKRED
                                        textFill = Color.WHITE
                                    }
                                    else -> {
                                        backgroundColor += Color.TRANSPARENT
                                        textFill = Color.BLACK
                                    }
                                }
                            }
                        }
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
