package de.olk90.inventorymanager.view.inventory.mot

import de.olk90.inventorymanager.logic.controller.getInventoryControllerInstance
import javafx.beans.property.LongProperty
import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleLongProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.control.SpinnerValueFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalUnit

/**
 * This implementation is based on SpinnerValueFactory.LocalDateValueFactory
 */
class NextMotSpinnerValueFactory : SpinnerValueFactory<LocalDate>() {

    init {
        val controller = getInventoryControllerInstance()
        valueProperty().addListener { _, _, _ ->
            if (value != null) {
                controller.model.nextMotString.set(value.format(DateTimeFormatter.ISO_LOCAL_DATE))
            } else {
                controller.model.nextMotString.set("")
            }
        }
    }

    private val min = SimpleObjectProperty(this, "min", LocalDate.now())
    private val max = SimpleObjectProperty(this, "max", LocalDate.MAX)

    private val temporalUnit: ObjectProperty<TemporalUnit> = SimpleObjectProperty(this, "temporalUnit", ChronoUnit.MONTHS)

    private fun getTemporalUnit(): TemporalUnit {
        return temporalUnit.get()
    }

    private val amountToStepBy: LongProperty = SimpleLongProperty(this, "amountToStepBy", 1)

    private fun getAmountToStepBy(): Long {
        return amountToStepBy.get()
    }

    private fun getMin(): LocalDate = min.get()

    private fun getMax(): LocalDate = max.get()

    override fun decrement(steps: Int) {
        val currentValue = if (value != null) value else LocalDate.now()
        val min = getMin()

        var newValue = currentValue.minus(getAmountToStepBy() * steps, getTemporalUnit())

        if (newValue.isBefore(min)) {
            newValue = getMin()
        }

        value = newValue

    }

    override fun increment(steps: Int) {
        val currentValue = if (value != null) value else LocalDate.now()
        val max = getMax()
        var newValue = currentValue.plus(getAmountToStepBy() * steps, getTemporalUnit())

        if (newValue.isAfter(max)) {
            newValue = getMax()
        }
        value = newValue
    }

}