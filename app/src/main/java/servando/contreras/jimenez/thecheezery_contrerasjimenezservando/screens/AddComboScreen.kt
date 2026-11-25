package servando.contreras.jimenez.thecheezery_contrerasjimenezservando.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.ui.theme.HelloBunny
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.ui.theme.Less_Purple
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.ui.theme.Pinky
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.viewmodel.ComboViewModel

@Composable
fun AddComboScreen(
    onSaved: () -> Unit,
    viewModel: ComboViewModel = viewModel()
) {
    val products by viewModel.products.collectAsState()

    var name by remember { mutableStateOf("") }
    var priceText by remember { mutableStateOf("") }
    var selectedIds by remember { mutableStateOf(setOf<Int>()) }
    var error by remember { mutableStateOf<String?>(null) }

    Column(Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        Text(
            "New combo",
            fontFamily = HelloBunny,
            fontSize = 64.sp,
            color = Pinky,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it; error = null },
            label = { Text("Combo name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = priceText,
            onValueChange = { priceText = it; error = null },
            label = { Text("Price") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        Text("Select the products", fontSize = 16.sp, color = Less_Purple)

        LazyColumn(Modifier.weight(1f)) {
            items(products, key = { it.id }) { product ->
                val checked = product.id in selectedIds
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = checked,
                        onCheckedChange = {
                            selectedIds = if (it) selectedIds + product.id else selectedIds - product.id
                            error = null
                        }
                    )
                    Text(product.name, modifier = Modifier.weight(1f))
                    Text("\$${product.price}")
                }
            }
        }

        error?.let { Text(it, color = MaterialTheme.colorScheme.error) }

        Button(
            onClick = {
                val price = priceText.toFloatOrNull()
                when {
                    name.isBlank() -> error = "The name can't be empty"
                    price == null || price <= 0f -> error = "Enter a valid price"
                    selectedIds.size < 2 -> error = "Select at least 2 products"
                    else -> viewModel.createCombo(name, price, selectedIds.toList(), onSaved)
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Less_Purple),
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
        ) { Text("Save combo") }
    }
}