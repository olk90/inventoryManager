package de.olk90.inventorymanager.view.common

import de.olk90.inventorymanager.logic.Config
import tornadofx.*

class WorkspaceViewModel : ViewModel() {

    val pathProperty = bind { Config.pathProperty }
    val identifierProperty = bind { Config.identifierProperty }

}