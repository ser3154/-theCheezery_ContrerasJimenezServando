package servando.contreras.jimenez.thecheezery_contrerasjimenezservando.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.data.database.AppDatabase
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.data.database.entity.ComboEntity
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.data.database.entity.ProductEntity
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.data.database.relation.ComboWithProducts
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.data.repository.CheezeryRepository

class ComboViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CheezeryRepository(AppDatabase.getInstance(application))

    val combos: StateFlow<List<ComboWithProducts>> =
        repository.getCombosWithProducts()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val products: StateFlow<List<ProductEntity>> =
        repository.getAllProducts()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun createCombo(name: String, price: Float, productIds: List<Int>, onDone: () -> Unit) {
        viewModelScope.launch {
            repository.createCombo(ComboEntity(name = name.trim(), price = price), productIds)
            onDone()
        }
    }

    fun deleteCombo(combo: ComboEntity) {
        viewModelScope.launch { repository.deleteCombo(combo) }
    }
}