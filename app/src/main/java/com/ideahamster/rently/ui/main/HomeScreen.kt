package com.ideahamster.rently.ui.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ideahamster.rently.ui.main.MonthlyRentReport.CurrentMonthRentReport
import com.ideahamster.rently.ui.main.MonthlyRentReport.NextMonthRentReport
import com.ideahamster.rently.ui.main.MonthlyRentReport.PreviousMonthRentReport

@Composable
fun HomeScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: HomeViewModel = hiltViewModel()
) {
    val tenantItems by rememberSaveable { viewModel.tenantList }
    HomeScreenComposable(tenantItems) { tenantItem: TenantItem ->
        navController.navigate(Screen.UpdateRent.route)
        navController.navigate(
            "detail/{uId}" //Just modify your route accordingly
                .replace(
                    oldValue = "{uId}",
                    newValue = "1"
                )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenComposable(
    tenantItemList: List<TenantItem>,
    onItemClick: (TenantItem) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Rently",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search"
                        )
                    }
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        content = { innerPadding ->
            LazyColumn(
                contentPadding = innerPadding,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(count = tenantItemList.size) {
                    TenantItemCompose(tenantItemList[it], onItemClick)
                }
            }
        }
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TenantItemCompose(
    tenantItem: TenantItem,
    onItemClick: (TenantItem) -> Unit
) {
    Card(
        onClick = { onItemClick(tenantItem) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Box {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp)
            ) {
                // Create references for the composables to constrain
                val (houseLabelText, dueAmountText, tenantNameText, monthRow) = createRefs()

                Text(
                    text = tenantItem.houseName,
                    modifier = Modifier
                        .constrainAs(houseLabelText) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        }
                )
                Text(
                    text = "Rs. ${tenantItem.dueAmount}",
                    modifier = Modifier
                        .constrainAs(dueAmountText) {
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                        }
                )
                Text(
                    text = tenantItem.tenantName,
                    Modifier
                        .constrainAs(tenantNameText) {
                            top.linkTo(houseLabelText.bottom, margin = 4.dp)
                        }
                )
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .constrainAs(monthRow) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(tenantNameText.bottom, margin = 4.dp)
                        bottom.linkTo(parent.bottom)
                    }
                ) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(4),

                        content = {
                            items(tenantItem.monthlyRentStatusList.size) { index ->
                                MonthlyRentReportCompose(tenantItem, index)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun MonthlyRentReportCompose(tenantItem: TenantItem, index: Int) {
    var month = ""
    var color = Color.LightGray
    when (tenantItem.monthlyRentStatusList[index]) {
        is PreviousMonthRentReport -> {
            val previousMonthRentReport =
                tenantItem.monthlyRentStatusList[index] as PreviousMonthRentReport
            month = previousMonthRentReport.month
            color = if (previousMonthRentReport.status)
                Color.Green
            else
                Color.Red
        }

        is CurrentMonthRentReport -> {
            val currentMonthRentReport =
                tenantItem.monthlyRentStatusList[index] as CurrentMonthRentReport
            month = currentMonthRentReport.month
            color = if (currentMonthRentReport.status)
                Color.Green
            else
                Color.Yellow
        }

        is NextMonthRentReport -> {
            val nextMonthRentReport =
                tenantItem.monthlyRentStatusList[index] as NextMonthRentReport
            month = nextMonthRentReport.month
            color = if (nextMonthRentReport.status)
                Color.Green
            else
                Color.Yellow
        }
    }
    Box {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = color
            ),
            modifier = Modifier
                .padding(end = 4.dp)
                .fillMaxWidth()
                .align(Alignment.Center),
        ) {
            Text(
                text = month,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            )
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        navController = rememberNavController(),
        viewModel = HomeViewModel()
    )
}