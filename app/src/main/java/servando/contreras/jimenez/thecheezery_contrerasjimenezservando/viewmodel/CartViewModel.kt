package servando.contreras.jimenez.thecheezery_contrerasjimenezservando.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.data.database.entity.ProductEntity
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.data.database.relation.ComboWithProducts

class CartViewModel : ViewModel() {
    private val _items = MutableStateFlow<List<ProductEntity>>(emptyList())
    val items: StateFlow<List<ProductEntity>> = _items.asStateFlow()

    val total: StateFlow<Float> = _items
        .map { list -> list.sumOf { it.price.toDouble() }.toFloat() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0f)

    fun addProduct(product: ProductEntity) = _items.update { it + product }

    fun addCombo(combo: ComboWithProducts) = addProduct(
        ProductEntity(name = "Combo: ${combo.combo.name}", price = combo.combo.price, category = "COMBO")
    )

    fun removeAt(index: Int) = _items.update { list ->
        list.toMutableList().also { it.removeAt(index) }
    }
}