package de.olk90.inventorymanager.logic.controller

import de.olk90.inventorymanager.logic.datahelpers.ObjectStore
import de.olk90.inventorymanager.model.Person
import de.olk90.inventorymanager.view.addIndexColumn
import javafx.fxml.FXML
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.TextField
import javafx.scene.control.cell.PropertyValueFactory


class PersonController {

    @FXML
    lateinit var personTable: TableView<Person>

    @FXML
    lateinit var firstNameCol: TableColumn<Person, String>

    @FXML
    lateinit var lastNameCol: TableColumn<Person, String>

    @FXML
    lateinit var emailCol: TableColumn<Person, String>

    @FXML
    lateinit var firstNameTextField: TextField

    @FXML
    lateinit var lastNameTextField: TextField

    @FXML
    lateinit var emailTextField: TextField

    fun initialize() {
        initializeColumns()
        reloadTableItems()
        configureEditor()
    }

    private fun initializeColumns() {
        personTable.addIndexColumn()

        firstNameCol.cellValueFactory = PropertyValueFactory("firstName")
        lastNameCol.cellValueFactory = PropertyValueFactory("lastName")
        emailCol.cellValueFactory = PropertyValueFactory("email")
    }

    private fun reloadTableItems() {
        personTable.items = ObjectStore.persons
    }

    private fun configureEditor() {
        personTable.selectionModel.selectedItemProperty().addListener { _, _, newValue: Person ->
                prepareEditor(newValue)
            }
    }

    private fun prepareEditor(person: Person) {
        firstNameTextField.text = person.firstName
        lastNameTextField.text = person.lastName
        emailTextField.text = person.email
    }
}