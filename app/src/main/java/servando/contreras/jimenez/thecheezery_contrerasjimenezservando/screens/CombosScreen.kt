package servando.contreras.jimenez.thecheezery_contrerasjimenezservando.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.R
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.data.database.relation.ComboWithProducts
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.ui.theme.HelloBunny
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.ui.theme.Less_Purple
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.ui.theme.Pinky
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.ui.theme.Very_purple
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.viewmodel.CartViewModel
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.viewmodel.ComboViewModel

@Composable
fun CombosScreen(
    cartViewModel: CartViewModel,
    onAddComboClick: () -> Unit,
    viewModel: ComboViewModel = viewModel()
) {
    val combos by viewModel.combos.collectAsState()

    Column(Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        Text(
            "Combos",
            fontFamily = HelloBunny,
            fontSize = 88.sp,
            color = Pinky,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onAddComboClick,
            colors = ButtonDefaults.buttonColors(containerColor = Less_Purple),
            modifier = Modifier.fillMaxWidth()
        ) { Text("New combo") }

        Spacer(Modifier.height(12.dp))

        if (combos.isEmpty()) {
            Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text("No combos yet. Create one!", fontSize = 20.sp, textAlign = TextAlign.Center)
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(combos, key = { it.combo.id }) { item ->
                    ComboItem(
                        item = item,
                        onAddToCart = { cartViewModel.addCombo(item) },
                        onDelete = { viewModel.deleteCombo(item.combo) }
                    )
                }
            }
        }
    }
}

@Composable
private fun ComboItem(item: ComboWithProducts, onAddToCart: () -> Unit, onDelete: () -> Unit) {
    Card(Modifier.fillMaxWidth()) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text(item.combo.name, fontSize = 20.sp, fontWeight = FontWeight.Medium, color = Less_Purple)
                Text(item.products.joinToString(" + ") { it.name }, fontSize = 13.sp)
            }
            Text("\$${item.combo.price}", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Very_purple)
            IconButton(onClick = onAddToCart) {
                Icon(painterResource(R.drawable.add_cart), contentDescription = "Add to cart", tint = Very_purple)
            }
            IconButton(onClick = onDelete) {
                Icon(painterResource(R.drawable.delete), contentDescription = "Delete combo", tint = Pinky)
            }
        }
    }
}