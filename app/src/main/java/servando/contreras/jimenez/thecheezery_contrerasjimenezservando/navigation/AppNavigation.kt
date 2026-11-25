package servando.contreras.jimenez.thecheezery_contrerasjimenezservando.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.screens.*
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.viewmodel.CartViewModel

object Routes {
    const val WELCOME = "welcome"
    const val LOGIN = "login"
    const val MENU = "menu"
    const val PRODUCTS = "products/{category}"
    const val CART = "cart"
    const val COMBOS = "combos"
    const val ADD_COMBO = "add_combo"
    const val ADD_PRODUCT = "add_product"
    fun products(category: String) = "products/$category"
}

@Composable
fun AppNavigation(cartViewModel: CartViewModel, modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = Routes.WELCOME, modifier = modifier) {
        composable(Routes.WELCOME) {
            welcomeScreen(onGetStarted = { navController.navigate(Routes.LOGIN) })
        }
        composable(Routes.LOGIN) {
            LoginScreen(onLoginSuccess = {
                navController.navigate(Routes.MENU) {
                    popUpTo(Routes.WELCOME) { inclusive = true }
                }
            })
        }
        composable(Routes.MENU) {
            MenuScreen(
                onCategoryClick = { navController.navigate(Routes.products(it.name)) },
                onCartClick = { navController.navigate(Routes.CART) },
                onCombosClick = { navController.navigate(Routes.COMBOS) },
                onAddProductClick = { navController.navigate(Routes.ADD_PRODUCT) }
            )
        }
        composable(
            route = Routes.PRODUCTS,
            arguments = listOf(navArgument("category") { type = NavType.StringType })
        ) {
            ProductsScreen(cartViewModel = cartViewModel)
        }
        composable(Routes.CART) {
            CartScreen(cartViewModel = cartViewModel)
        }
        composable(Routes.COMBOS) {
            CombosScreen(
                cartViewModel = cartViewModel,
                onAddComboClick = { navController.navigate(Routes.ADD_COMBO) }
            )
        }
        composable(Routes.ADD_COMBO) {
            AddComboScreen(onSaved = { navController.popBackStack() })
        }
        composable(Routes.ADD_PRODUCT) {
            AddProductScreen(onSaved = { navController.popBackStack() })
        }
    }
}