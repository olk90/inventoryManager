package org.olk90.inventorymanager.view.common

import org.olk90.inventorymanager.logic.Config
import tornadofx.*

class WorkspaceViewModel : ViewModel() {

    val pathProperty = bind { Config.pathProperty }

}