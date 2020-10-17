package org.olk90.inventorymanager.view.person

import de.jensd.fx.glyphs.octicons.OctIcon
import org.olk90.inventorymanager.logic.controller.PersonController
import org.olk90.inventorymanager.view.common.icon
import tornadofx.*

class PersonDataFragment(private val create: Boolean = false) : Fragment() {

    val controller: PersonController by inject()

    override val root = borderpane {
        center {
            form {
                val title = if (create) "Add user" else "Edit user"
                fieldset(title) {
                    field("First name") {
                        textfield(controller.model.firstName)
                    }
                    field("Last name") {
                        textfield(controller.model.lastName)
                    }
                    field("Email") {
                        textfield(controller.model.email)
                    }
                }

                fieldset {
                    buttonbar {
                        button {
                            tooltip("Save")
                            addClass("icon-only")
                            graphic = icon(OctIcon.CHECK)
                            enableWhen(controller.model.dirty)
                            action {
                                if (create) {
                                    controller.add()
                                    close()
                                } else {
                                    controller.save()
                                }
                            }
                        }
                        button {
                            tooltip("Reset")
                            addClass("icon-only")
                            graphic = icon(OctIcon.X)
                            enableWhen(controller.model.dirty)
                            action {
                                controller.model.rollback()
                            }
                        }
                    }
                }
            }

        }

    }
}
