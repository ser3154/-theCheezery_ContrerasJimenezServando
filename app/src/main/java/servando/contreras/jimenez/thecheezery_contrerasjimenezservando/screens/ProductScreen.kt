package servando.contreras.jimenez.thecheezery_contrerasjimenezservando.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.R
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.data.Product
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.ui.theme.*
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.viewmodel.CartViewModel
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.viewmodel.ProductsViewModel


@Composable
fun ProductsScreen(
    cartViewModel: CartViewModel,
    viewModel: ProductsViewModel = viewModel()
) {
    Column(Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        Text(
            text = viewModel.title,
            fontFamily = HelloBunny,
            fontSize = 88.sp,
            color = Pinky,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(viewModel.products) { product ->
                ProductItem(product = product, onAddClick = { cartViewModel.addProduct(product) })
            }
        }
    }
}

@Composable
private fun ProductItem(product: Product, onAddClick: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(product.image),
            contentDescription = product.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(80.dp).clip(CircleShape)
        )
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(
                product.name,
                fontFamily = FontFamily.SansSerif,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Less_Purple
            )
            Text(
                product.description ?: "",
                fontSize = 13.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(Modifier.width(8.dp))
        Text("\$${product.price}", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Very_purple)
        Spacer(Modifier.width(8.dp))
        FilledIconButton(
            onClick = onAddClick,
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = Very_purple,
                contentColor = Color.White,
                disabledContainerColor = DisabledGrey,
                disabledContentColor = PurpleGrey40
            )
        ) {
            Icon(painterResource(R.drawable.add_cart), contentDescription = "Agregar al carrito", tint = Color.White)
        }
    }
}