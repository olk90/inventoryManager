package de.olk90.inventorymanager.model

import java.time.LocalDate

class LendingHistoryRecord(
    var lendingDate: LocalDate? = null,
    var lendingDateString: String? = null,
    var returnDate: LocalDate? = null,
    var returnDateString: String? = null
) {

    var lender = -1
    var item = -1
}