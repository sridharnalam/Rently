package com.ideahamster.rently.ui.main

data class TenantItem(
    val houseName: String = "",
    val tenantName: String = "",
    val dueAmount: Int = -1,
    val monthlyRentStatusList: List<MonthlyRentReport> = emptyList()
)

sealed class MonthlyRentReport {
    data class PreviousMonthRentReport(
        val month: String,
        val status: Boolean = false
    ) : MonthlyRentReport()

    data class CurrentMonthRentReport(
        val month: String,
        val status: Boolean = false
    ) : MonthlyRentReport()

    data class NextMonthRentReport(
        val month: String,
        val status: Boolean = false
    ) : MonthlyRentReport()

}
