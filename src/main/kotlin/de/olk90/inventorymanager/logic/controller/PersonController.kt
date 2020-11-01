package de.olk90.inventorymanager.logic.controller

import javafx.collections.ObservableList
import javafx.scene.control.TextField
import de.olk90.inventorymanager.model.Person
import de.olk90.inventorymanager.model.PersonModel
import de.olk90.inventorymanager.view.common.messages
import tornadofx.*

fun getPersonControllerInstance(): PersonController {
    return find(PersonController::class)
}

class PersonController : Controller() {

    var model = PersonModel(Person())

    private val filteredData = SortedFilteredList(ObjectStore.persons)
    val tableItems = mutableListOf<Person>().asObservable()

    fun save() {
        model.commit()
        val person = model.item
        val p = ObjectStore.findPersonById(person.id)!!
        p.firstName = person.firstName
        p.lastName = person.lastName
        p.email = person.email

        // write data to file
        getWorkspaceControllerInstance().writeDcFile()
    }

    fun add() {
        val person = Person(model.firstName.value, model.lastName.value, model.email.value)
        insertPerson(person)
        getWorkspaceControllerInstance().writeDcFile()
    }

    private fun insertPerson(person: Person) {
        ObjectStore.persons.add(person)
        tableItems.add(person)
    }

    fun configureFilterListener(textField: TextField) {
        textField.textProperty().addListener { _, _, newValue ->
            tableItems.clear()
            filteredData.predicate = {
                // If filter text is empty, display all persons.
                val filterTextEmpty = newValue == null || newValue.isEmpty()

                // Compare first name and last name of every person with filter text.
                val lowerCaseFilter = newValue.toLowerCase()
                val firstNameMatch = it.firstName.toLowerCase().indexOf(lowerCaseFilter) != -1
                val lastNameMatch = it.lastName.toLowerCase().indexOf(lowerCaseFilter) != -1

                filterTextEmpty || firstNameMatch || lastNameMatch
            }
            tableItems.addAll(filteredData)
        }

    }

    fun delete(selectedPersons: ObservableList<Person>) {
        selectedPersons.forEach {
            val items = ObjectStore.inventoryItems.filter { item -> item.lender == it.id }
            if (items.isNotEmpty()) {
                if (items.size > 1) {
                    error(messages("error.header.delete"), messages("error.content.multipleItemsLent", it.getFullName(), items.size))
                } else {
                    error(messages("error.header.delete"), messages("error.content.singleItemLent", it.getFullName()))
                }
            } else {
                ObjectStore.persons.remove(it)
                tableItems.remove(it)
            }
        }
        getWorkspaceControllerInstance().writeDcFile()
    }

    fun emailTo(person: Person) {
        val mailto = "mailto:" + person.email
        var cmd = ""
        val os = System.getProperty("os.name").toLowerCase()
        if (os.contains("win")) {
            cmd = "cmd.exe /c start $mailto"
        } else if (os.contains("osx")) {
            cmd = "open $mailto"
        } else if (os.contains("nix") || os.contains("aix") || os.contains("nux")) {
            // NOTE: this does cause an error in KDE environment
            cmd = "xdg-open $mailto"
        } else {
            error(messages("error.header.email"), messages("error.content.osUnknown"))
        }
        if (cmd.isNotEmpty()) {
            Runtime.getRuntime().exec(cmd)
        }
    }
}