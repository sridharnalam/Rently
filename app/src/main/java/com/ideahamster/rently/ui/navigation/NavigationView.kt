import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ideahamster.rently.ui.house.AddHouseScreen
import com.ideahamster.rently.ui.main.HomeScreen
import com.ideahamster.rently.ui.rent.UpdateRentScreen
import com.ideahamster.rently.ui.tenant.AddTenantScreen

@Composable
fun NavigationView() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(Screen.AddTenant.route) {
            AddTenantScreen(navController)
        }
        composable(Screen.AddHouse.route) {
            AddHouseScreen(navController)
        }
        composable(Screen.UpdateRent.route) { navBackStackEntry ->
            /* Extracting the id from the route */
            val tenantId = navBackStackEntry.arguments?.getString("uId")
            /* We check if it's not null */
            tenantId?.let { id ->
                UpdateRentScreen(tenantId = id)
            }
        }
    }
}

sealed class Screen(val route: String) {
    object Home : Screen(route = "Home_Screen")

    object AddTenant : Screen(route = "Add_Tenant")

    object AddHouse : Screen(route = "Add_House")

    object UpdateRent : Screen(route = "Update_Rent/detail/{uId}")
}