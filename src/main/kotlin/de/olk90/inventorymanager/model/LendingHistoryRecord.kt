package de.olk90.inventorymanager.model

import com.beust.klaxon.Json
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import java.time.LocalDate

class LendingHistoryRecord(
        lendingDate: LocalDate? = null,
        lendingDateString: String? = null,
        returnDate: LocalDate? = null,
        returnDateString: String? = null
) {

    @Json(ignored = true)
    val lendingDateProperty = SimpleObjectProperty<LocalDate>(this, "lendingDate", lendingDate)

    @Json(ignored = true)
    val lendingDateStringProperty = SimpleStringProperty(this, "lendingDateString", lendingDateString)

    @Json(ignored = true)
    val returnDateProperty = SimpleObjectProperty<LocalDate>(this, "returnDate", returnDate)

    @Json(ignored = true)
    val returnDateStringProperty = SimpleStringProperty(this, "returnDateString", returnDateString)

    @Json(ignored = true)
    val lenderProperty = SimpleIntegerProperty(this, "lender", -1)

    @Json(ignored = true)
    val itemProperty = SimpleIntegerProperty(this, "item", -1)

    @Json(ignored = true)
    var lendingDate by lendingDateProperty

    @Json(name = "lendingDate")
    var lendingDateString by lendingDateStringProperty

    @Json(ignored = true)
    var returnDate by returnDateProperty

    @Json(name = "returnDate")
    var returnDateString by returnDateStringProperty

    var lender by lenderProperty
    var item by itemProperty

}

//class LendingHistoryRecordModel(record: LendingHistoryRecord) : ItemViewModel<LendingHistoryRecord>(record) {
//    val lender = bind(LendingHistoryRecord::lenderProperty)
//    val lendingDate = bind(LendingHistoryRecord::lendingDateProperty)
//    val lendingDateString = bind(LendingHistoryRecord::lendingDateStringProperty)
//    val returnDate = bind(LendingHistoryRecord::returnDateProperty)
//    val returnDateString = bind(LendingHistoryRecord::returnDateStringProperty)
//}