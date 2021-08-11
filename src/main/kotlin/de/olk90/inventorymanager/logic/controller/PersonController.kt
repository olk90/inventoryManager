package de.olk90.inventorymanager.logic.controller

import de.olk90.inventorymanager.logic.ObjectStore
import de.olk90.inventorymanager.model.Person
import de.olk90.inventorymanager.view.addIndexColumn
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.TextField


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

    @FXML
    lateinit var commitButton: Button

    @FXML
    lateinit var rollbackButton: Button

    fun commit() {
        val person = personTable.selectionModel.selectedItem
        person.firstName = firstNameTextField.text
        person.lastName = lastNameTextField.text
        person.email = emailTextField.text
    }

    fun rollback() {
        val person = personTable.selectionModel.selectedItem
        setEditorFields(person)
    }

    fun initialize() {
        initializeColumns()
        reloadTableItems()

        configureEditor()
    }

    private fun initializeColumns() {
        personTable.addIndexColumn()

        configureFirstNameCol()
        configureLastNameCol()
        configureEmailCol()
    }

    private fun configureFirstNameCol() {
        firstNameCol.setCellValueFactory {
            it.value.firstNameProperty
        }
    }

    private fun configureLastNameCol() {
        lastNameCol.setCellValueFactory {
            it.value.lastNameProperty
        }
    }

    private fun configureEmailCol() {
        emailCol.setCellValueFactory {
            it.value.emailProperty
        }
    }

    private fun reloadTableItems() {
        personTable.items = ObjectStore.persons
    }

    private fun configureEditor() {
        personTable.selectionModel.selectedItemProperty().addListener { _, _, newValue: Person ->
                setEditorFields(newValue)
            }
    }

    private fun setEditorFields(person: Person) {
        firstNameTextField.text = person.firstName
        lastNameTextField.text = person.lastName
        emailTextField.text = person.email
    }
}