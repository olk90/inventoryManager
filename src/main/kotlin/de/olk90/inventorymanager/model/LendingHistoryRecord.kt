package de.olk90.inventorymanager.model

import com.beust.klaxon.Json
import java.time.LocalDate

class LendingHistoryRecord(
    @Json(ignored = true)
    var lendingDate: LocalDate? = null,
    @Json(name = "lendingDate")
    var lendingDateString: String = "",
    @Json(ignored = true)
    var returnDate: LocalDate? = null,
    @Json(name = "returnDate")
    var returnDateString: String = ""
) {

    var lender = -1
    var item = -1
}