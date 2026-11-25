package servando.contreras.jimenez.thecheezery_contrerasjimenezservando

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.data.database.AppDatabase
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.data.repository.CheezeryRepository
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.navigation.AppNavigation
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.ui.theme.TheCheezery_ContrerasJimenezServandoTheme
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.viewmodel.CartViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        lifecycleScope.launch {
            CheezeryRepository(AppDatabase.getInstance(applicationContext))
                .seedIfEmpty(applicationContext)
        }

        setContent {
            TheCheezery_ContrerasJimenezServandoTheme {
                val cartViewModel: CartViewModel = viewModel()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(cartViewModel, Modifier.padding(innerPadding))
                }
            }
        }
    }
}