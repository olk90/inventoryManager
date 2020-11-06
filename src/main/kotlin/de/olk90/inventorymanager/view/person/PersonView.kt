package de.olk90.inventorymanager.view.person

import de.olk90.inventorymanager.logic.controller.PersonController
import de.olk90.inventorymanager.model.Person
import de.olk90.inventorymanager.view.common.align
import de.olk90.inventorymanager.view.common.messages
import javafx.geometry.Pos
import javafx.scene.control.TableView
import tornadofx.*

class PersonView : View(messages("label.personOverview")) {

    private val controller: PersonController by inject()
    lateinit var table: TableView<Person>

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
                    makeIndexColumn("#").apply {
                        align(Pos.CENTER)
                    }

                    tableMenuButtonVisibleProperty().set(true)
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

                    onSelectionChange {
                        if (it != null) {
                            (contextMenu as PersonContextMenu).enableEmail.set(it.email.isNotEmpty())
                        }
                    }

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
