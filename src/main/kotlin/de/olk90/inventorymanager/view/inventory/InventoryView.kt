package de.olk90.inventorymanager.view.inventory

import de.olk90.inventorymanager.logic.controller.InventoryController
import de.olk90.inventorymanager.model.InventoryItem
import de.olk90.inventorymanager.view.common.PersonConverter
import de.olk90.inventorymanager.view.common.align
import de.olk90.inventorymanager.view.common.messages
import de.olk90.inventorymanager.view.inventory.mot.MotConverter
import javafx.geometry.Pos
import javafx.scene.control.TableView
import javafx.scene.control.cell.CheckBoxTableCell
import javafx.scene.paint.Color
import tornadofx.*
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

class InventoryView : View(messages("label.inventoryOverview")) {

    private val controller: InventoryController by inject()
    lateinit var table: TableView<InventoryItem>

    init {
        controller.reloadTableItems()
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
                    makeIndexColumn("#").apply {
                        align(Pos.CENTER)
                    }

                    tableMenuButtonVisibleProperty().set(true)
                    columnResizePolicy = SmartResize.POLICY

                    column(messages("inventoryItem.category"), InventoryItem::categoryProperty).apply {
                        align(Pos.CENTER)
                        pctWidth(20.0)
                    }
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
                                alignment = Pos.CENTER
                                text = it.format(DateTimeFormatter.ofPattern("MMM/yyyy"))
                                borderColor += box(
                                        top = Color.GRAY,
                                        right = Color.GRAY,
                                        left = Color.GRAY,
                                        bottom = Color.GRAY
                                )
                                val totalMonths = Period.between(LocalDate.now(), it).toTotalMonths()
                                when {
                                    totalMonths <= 12L && totalMonths > 6L -> {
                                        backgroundColor += Color.DARKGREEN
                                        textFill = Color.WHITE
                                    }
                                    totalMonths <= 6L && totalMonths > 3L -> {
                                        backgroundColor += Color.YELLOW
                                        textFill = Color.BLACK
                                    }
                                    totalMonths <= 3L && totalMonths > 1L -> {
                                        backgroundColor += Color.ORANGE
                                        textFill = Color.BLACK
                                    }
                                    totalMonths == 1L -> {
                                        backgroundColor += Color.ORANGERED
                                        textFill = Color.BLACK
                                    }
                                    totalMonths <= 0L -> {
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

                    onSelectionChange {
                        if (it != null) {
                            controller.selectedItemAvailable.set(it.available)
                        }
                    }

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
