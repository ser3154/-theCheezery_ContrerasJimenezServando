package servando.contreras.jimenez.thecheezery_contrerasjimenezservando.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.data.Category
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.data.database.AppDatabase
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.data.database.entity.ProductEntity
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.data.repository.CheezeryRepository

class ProductsViewModel(
    application: Application,
    savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {

    private val category = Category.valueOf(checkNotNull(savedStateHandle.get<String>("category")))
    private val repository = CheezeryRepository(AppDatabase.getInstance(application))

    val title: String = category.title

    val products: StateFlow<List<ProductEntity>> =
        repository.getProductsByCategory(category.name)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}