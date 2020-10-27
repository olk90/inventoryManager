package org.olk90.inventorymanager.view.person

import javafx.geometry.Pos
import javafx.scene.control.TableView
import org.olk90.inventorymanager.logic.controller.ObjectStore
import org.olk90.inventorymanager.logic.controller.PersonController
import org.olk90.inventorymanager.model.Person
import org.olk90.inventorymanager.view.common.align
import org.olk90.inventorymanager.view.common.messages
import tornadofx.*

class PersonView : View(messages("label.personOverview")) {

    private val controller: PersonController by inject()
    lateinit var table: TableView<Person>

    init {
        controller.tableItems.addAll(ObjectStore.persons)
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
                    columnResizePolicy = SmartResize.POLICY

                    column(messages("person.firstName"), Person::firstNameProperty).apply {
                        align(Pos.CENTER)
                        pctWidth(33.0)
                    }
                    column(messages("person.lastName"), Person::lastNameProperty).apply {
                        align(Pos.CENTER)
                        pctWidth(33.0)
                    }
                    column(messages("person.email"), Person::emailProperty).apply {
                        align(Pos.CENTER)
                        pctWidth(33.0)
                    }

                    contextMenu = PersonContextMenu(this)

                    // Update the person inside the view model on selection change
                    controller.model.rebindOnChange(this) {
                        item = it ?: Person()
                    }
                }

            }
        }
        right {
            add(PersonDataFragment())
        }
    }

}
