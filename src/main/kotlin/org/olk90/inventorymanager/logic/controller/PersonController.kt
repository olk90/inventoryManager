package org.olk90.inventorymanager.logic.controller

import javafx.scene.control.TableView
import org.olk90.inventorymanager.model.Person
import org.olk90.inventorymanager.model.PersonModel
import tornadofx.*

class PersonController : Controller() {

    var personTable: TableView<Person> by singleAssign()
    val model = PersonModel(Person())

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
        model.commit()
        val person = Person(model.firstName.value, model.lastName.value, model.email.value)
        ObjectStore.persons.add(person)
        getWorkspaceControllerInstance().writeDcFile()
    }

}