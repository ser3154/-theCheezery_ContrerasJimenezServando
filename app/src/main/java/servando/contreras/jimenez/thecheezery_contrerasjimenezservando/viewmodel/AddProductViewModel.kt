package servando.contreras.jimenez.thecheezery_contrerasjimenezservando.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.data.database.AppDatabase
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.data.database.entity.ProductEntity
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.data.repository.CheezeryRepository

class AddProductViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CheezeryRepository(AppDatabase.getInstance(application))

    fun saveProduct(
        name: String,
        price: Float,
        description: String,
        category: String,
        onDone: () -> Unit
    ) {
        viewModelScope.launch {
            repository.insertProduct(
                ProductEntity(
                    name = name.trim(),
                    price = price,
                    description = description.trim().ifBlank { null },
                    category = category
                )
            )
            onDone()
        }
    }
}