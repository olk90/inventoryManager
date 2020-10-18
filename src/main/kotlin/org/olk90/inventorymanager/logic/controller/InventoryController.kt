package org.olk90.inventorymanager.logic.controller

import org.olk90.inventorymanager.model.InventoryItem
import org.olk90.inventorymanager.model.InventoryItemModel
import tornadofx.*

class InventoryController : Controller() {

    val model = InventoryItemModel(InventoryItem())

    fun save() {
        model.commit()

        val item = model.item
        val i = ObjectStore.findInventoryById(item.id)!!
        i.name = item.name
        i.available = item.available
        i.lender = item.lender
        i.lendingDate = item.lendingDate
        i.lenderNameProperty.value = item.lender.getFullName()

        getWorkspaceControllerInstance().writeDcFile()
    }

    fun add() {
        model.commit()
        val item = InventoryItem(model.name.value, model.available.value, model.lender.value, model.lendingDate.value)
        item.lenderNameProperty.value =  model.lenderName.value
        ObjectStore.inventoryItems.add(item)
        getWorkspaceControllerInstance().writeDcFile()
    }

}