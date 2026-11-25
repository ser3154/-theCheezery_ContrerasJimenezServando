package servando.contreras.jimenez.thecheezery_contrerasjimenezservando.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.data.Category
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.ui.theme.HelloBunny
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.ui.theme.Less_Purple
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.ui.theme.Pinky
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.viewmodel.AddProductViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AddProductScreen(
    onSaved: () -> Unit,
    viewModel: AddProductViewModel = viewModel()
) {
    var name by remember { mutableStateOf("") }
    var priceText by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf(Category.HOT_DRINKS) }
    var error by remember { mutableStateOf<String?>(null) }

    Column(Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        Text(
            "New product",
            fontFamily = HelloBunny,
            fontSize = 64.sp,
            color = Pinky,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = name, onValueChange = { name = it; error = null },
            label = { Text("Name") }, singleLine = true, modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = priceText, onValueChange = { priceText = it; error = null },
            label = { Text("Price") }, singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = description, onValueChange = { description = it },
            label = { Text("Description") }, modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Category.entries.forEach {
                FilterChip(
                    selected = category == it,
                    onClick = { category = it },
                    label = { Text(it.title) }
                )
            }
        }

        error?.let { Text(it, color = MaterialTheme.colorScheme.error) }

        Spacer(Modifier.weight(1f))
        Button(
            onClick = {
                val price = priceText.toFloatOrNull()
                when {
                    name.isBlank() -> error = "The name can't be empty"
                    price == null || price <= 0f -> error = "Enter a valid price"
                    else -> viewModel.saveProduct(name, price, description, category.name, onSaved)
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Less_Purple),
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
        ) { Text("Save product") }
    }
}