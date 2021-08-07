package de.olk90.inventorymanager.logic.controller

import de.olk90.inventorymanager.logic.datahelpers.ObjectStore
import de.olk90.inventorymanager.model.InventoryItem
import de.olk90.inventorymanager.model.Person
import javafx.fxml.FXML
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.layout.GridPane
import se.alipsa.ymp.YearMonthPickerCombo
import java.time.YearMonth
import java.util.*


class InventoryController {

    @FXML
    lateinit var inventoryTable: TableView<InventoryItem>

    @FXML
    lateinit var editorGrid: GridPane

    @FXML
    lateinit var categoryCol: TableColumn<InventoryItem, String>

    @FXML
    lateinit var nameCol: TableColumn<InventoryItem, String>

    @FXML
    lateinit var availableCol: TableColumn<InventoryItem, String>

    @FXML
    lateinit var lendingDateCol: TableColumn<InventoryItem, Date>

    @FXML
    lateinit var lenderCol: TableColumn<InventoryItem, Person>

    @FXML
    lateinit var nextMotCol: TableColumn<InventoryItem, Date>

    fun initialize() {
        initializeColumns()
        initializeEditor()
        reloadTableItems()
    }

    private fun initializeColumns() {
        categoryCol.cellValueFactory = PropertyValueFactory("category")
        nameCol.cellValueFactory = PropertyValueFactory("name")
        availableCol.cellValueFactory = PropertyValueFactory("available")
        lendingDateCol.cellValueFactory = PropertyValueFactory("lendingDate")
        lenderCol.cellValueFactory = PropertyValueFactory("lender")
        nextMotCol.cellValueFactory = PropertyValueFactory("nextMot")
    }

    private fun initializeEditor() {
        val initial = YearMonth.now()
        val yearMonthPicker = YearMonthPickerCombo(
            initial,
            initial.plusYears(6),
            initial,
            Locale.getDefault(),
            "MMM/yyyy"
        )
        yearMonthPicker.maxWidth = Double.MAX_VALUE
        editorGrid.add(yearMonthPicker, 1, 2)
    }

    private fun reloadTableItems() {
        inventoryTable.items = ObjectStore.inventoryItems
    }

}