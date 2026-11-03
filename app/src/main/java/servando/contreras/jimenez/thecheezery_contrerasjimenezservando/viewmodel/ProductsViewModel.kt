package servando.contreras.jimenez.thecheezery_contrerasjimenezservando.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.data.Category
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.data.ProductsRepository

class ProductsViewModel(savedStateHandle: SavedStateHandle): ViewModel(){
    private val category = Category.valueOf(checkNotNull(savedStateHandle.get<String>("Category")))

    val title: String = category.title
    val products = ProductsRepository.getByCategory(category)
}