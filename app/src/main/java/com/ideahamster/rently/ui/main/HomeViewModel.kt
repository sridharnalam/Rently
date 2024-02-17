package com.ideahamster.rently.ui.main

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    val tenantList = mutableStateOf((emptyList<TenantItem>()))
    val tenant = mutableStateOf(TenantItem())

    init {
        tenantList.value = getDummyList()
    }

    private fun getDummyList(): List<TenantItem> {
        return listOf(
            TenantItem(
                houseName = "1F-101",
                tenantName = "Sri Ram",
                dueAmount = 0,
                monthlyRentStatusList = listOf(
                    MonthlyRentReport.PreviousMonthRentReport(
                        month = "Jun",
                        status = true
                    ),
                    MonthlyRentReport.PreviousMonthRentReport(
                        month = "Jul",
                        status = true
                    ),
                    MonthlyRentReport.CurrentMonthRentReport(
                        month = "Aug",
                        status = false
                    ),
                    MonthlyRentReport.NextMonthRentReport(
                        month = "Sep",
                        status = false
                    ),
                )
            ),
            TenantItem(
                houseName = "1F-102",
                tenantName = "Lakshman",
                dueAmount = 0,
                monthlyRentStatusList = listOf(
                    MonthlyRentReport.PreviousMonthRentReport(
                        month = "Jun",
                        status = true
                    ),
                    MonthlyRentReport.PreviousMonthRentReport(
                        month = "Jul",
                        status = true
                    ),
                    MonthlyRentReport.CurrentMonthRentReport(
                        month = "Aug",
                        status = false
                    ),
                    MonthlyRentReport.NextMonthRentReport(
                        month = "Sep",
                        status = false
                    ),
                )
            ),
            TenantItem(
                houseName = "2F-201",
                tenantName = "Bharath",
                dueAmount = 0,
                monthlyRentStatusList = listOf(
                    MonthlyRentReport.PreviousMonthRentReport(
                        month = "Jun",
                        status = true
                    ),
                    MonthlyRentReport.PreviousMonthRentReport(
                        month = "Jul",
                        status = true
                    ),
                    MonthlyRentReport.CurrentMonthRentReport(
                        month = "Aug",
                        status = false
                    ),
                    MonthlyRentReport.NextMonthRentReport(
                        month = "Sep",
                        status = false
                    ),
                )
            ),
            TenantItem(
                houseName = "2F-202",
                tenantName = "Sathrugnu",
                dueAmount = 7000,
                monthlyRentStatusList = listOf(
                    MonthlyRentReport.PreviousMonthRentReport(
                        month = "Jun",
                        status = true
                    ),
                    MonthlyRentReport.PreviousMonthRentReport(
                        month = "Jul",
                        status = false
                    ),
                    MonthlyRentReport.CurrentMonthRentReport(
                        month = "Aug",
                        status = false
                    ),
                    MonthlyRentReport.NextMonthRentReport(
                        month = "Sep",
                        status = false
                    ),
                )
            ),
        )
    }

    fun onTenantNameChanged(it: String) {
        tenantList.value = emptyList()
        tenant.value = tenant.value.copy(houseName = "1F 101")
    }
}