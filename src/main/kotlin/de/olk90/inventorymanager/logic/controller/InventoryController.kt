package de.olk90.inventorymanager.logic.controller

import de.olk90.inventorymanager.logic.Config
import de.olk90.inventorymanager.logic.ObjectStore
import de.olk90.inventorymanager.model.InventoryItem
import de.olk90.inventorymanager.view.addIndexColumn
import javafx.beans.property.SimpleStringProperty
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.layout.GridPane
import se.alipsa.ymp.YearMonthPickerCombo
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
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
    lateinit var availableCol: TableColumn<InventoryItem, Boolean>

    @FXML
    lateinit var lendingDateCol: TableColumn<InventoryItem, String>

    @FXML
    lateinit var lenderCol: TableColumn<InventoryItem, String>

    @FXML
    lateinit var nextMotCol: TableColumn<InventoryItem, String>

    @FXML
    lateinit var nameTextField: TextField

    @FXML
    lateinit var availableCheckBox: CheckBox

    lateinit var motCombo: YearMonthPickerCombo

    @FXML
    lateinit var categoryTextField: TextField

    @FXML
    lateinit var infoTextArea: TextArea

    @FXML
    lateinit var commitButton: Button

    @FXML
    lateinit var rollbackButton: Button

    fun commit() {

    }

    fun rollback() {

    }

    fun initialize() {
        initializeColumns()
        initializeEditor()
        reloadTableItems()

        configureEditor()
    }

    private fun configureEditor() {
        inventoryTable.selectionModel.selectedItemProperty().addListener { _, _, newValue: InventoryItem ->
            setEditorFields(newValue)
        }
    }

    private fun setEditorFields(item: InventoryItem) {
        nameTextField.text = item.name
        availableCheckBox.isSelected = item.available

        selectMotComboItem(item)

        categoryTextField.text = item.category
        infoTextArea.text = item.info
    }

    private fun selectMotComboItem(item: InventoryItem) {
        val date = item.nextMot.date
        if (date.year != 1970) {
            val yearMonth = YearMonth.of(date.year, date.month)
            motCombo.selectionModel.select(yearMonth)
        } else {
            motCombo.disarm()
        }
    }

    private fun initializeColumns() {
        inventoryTable.addIndexColumn()

        configureCategoryCol()
        configureNameCol()
        configureAvailableCol()
        configureLendingDateCol()
        configureLenderCol()
        configureNextMotCol()
    }

    private fun configureCategoryCol() {
        categoryCol.setCellValueFactory {
            it.value.categoryProperty
        }
    }

    private fun configureNameCol() {
        nameCol.setCellValueFactory {
            it.value.nameProperty
        }
    }

    private fun configureAvailableCol() {
        availableCol.setCellValueFactory {
            it.value.availableProperty
        }
    }

    private fun configureLendingDateCol() {
        lendingDateCol.setCellValueFactory {
            val lendingDate = it.value.lendingDate
            val date = lendingDate.date
            val dateString = if (date.year == 1970) {
                ""
            } else {
                val localizedDate = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
                date.format(localizedDate)
            }
            SimpleStringProperty(dateString)
        }
    }

    private fun configureLenderCol() {
        lenderCol.setCellValueFactory {
            val lender = ObjectStore.persons.firstOrNull { p -> p.id == it.value.lender }
            val lenderName = lender?.getFullName() ?: ""
            SimpleStringProperty(lenderName)
        }
    }

    private fun configureNextMotCol() {
        nextMotCol.setCellValueFactory {
            val nextMot = it.value.nextMot
            val date = nextMot.date
            val motString = if (date.year == 1970) {
                ""
            } else {
                val formatter = DateTimeFormatter.ofPattern(Config.MOT_PATTERN)
                date.format(formatter)
            }
            SimpleStringProperty(motString)
        }
    }

    private fun initializeEditor() {
        val initial = YearMonth.now()
        val yearMonthPicker = YearMonthPickerCombo(
            initial,
            initial.plusYears(6),
            initial,
            Locale.getDefault(),
            Config.MOT_PATTERN
        )
        yearMonthPicker.maxWidth = Double.MAX_VALUE
        motCombo = yearMonthPicker
        editorGrid.add(motCombo, 1, 2)
    }

    private fun reloadTableItems() {
        inventoryTable.items = ObjectStore.inventoryItems
    }

}