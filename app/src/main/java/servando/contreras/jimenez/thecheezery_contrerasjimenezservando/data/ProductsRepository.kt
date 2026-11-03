package servando.contreras.jimenez.thecheezery_contrerasjimenezservando.data

import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.coldDrinks
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.hotDrinks
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.salties
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.sweets

object ProductsRepository {
    fun getByCategory(category: Category): List<Product> = when (category) {
        Category.HOT_DRINKS -> hotDrinks
        Category.COLD_DRINKS -> coldDrinks
        Category.SALTIES -> salties
        Category.SWEETS -> sweets
    }

}