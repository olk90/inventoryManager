package org.olk90.inventorymanager.controller

import javafx.scene.control.TableView
import org.olk90.inventorymanager.model.Person
import org.olk90.inventorymanager.model.PersonModel
import tornadofx.*

class PersonController : WorkspaceController() {

    var personTable: TableView<Person> by singleAssign()
    val model = PersonModel(Person())

    fun save() {
        model.commit()
        val person = model.item
        // TODO persist data
    }
}