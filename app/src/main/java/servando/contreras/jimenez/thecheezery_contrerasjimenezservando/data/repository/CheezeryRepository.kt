package servando.contreras.jimenez.thecheezery_contrerasjimenezservando.data.repository

import android.content.Context
import androidx.room.withTransaction
import kotlinx.coroutines.flow.Flow
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.coldDrinks
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.data.Category
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.data.Product
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.data.database.AppDatabase
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.data.database.entity.ComboEntity
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.data.database.entity.ProductComboEntity
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.data.database.entity.ProductEntity
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.data.database.relation.ComboWithProducts
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.hotDrinks
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.salties
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.sweets

class CheezeryRepository(private val database: AppDatabase) {

    private val productDao = database.productDao()
    private val comboDao = database.comboDao()
    private val productComboDao = database.productComboDao()

    suspend fun insertProduct(product: ProductEntity): Long = productDao.insertProduct(product)

    suspend fun updateProduct(product: ProductEntity) = productDao.updateProduct(product)

    suspend fun deleteProduct(product: ProductEntity) = productDao.deleteProduct(product)

    fun getAllProducts(): Flow<List<ProductEntity>> = productDao.getAllProducts()

    fun getProductsByCategory(category: String): Flow<List<ProductEntity>> =
        productDao.getProductsByCategory(category)

    suspend fun getProductById(id: Int): ProductEntity? = productDao.getProductById(id)

    fun searchProducts(name: String): Flow<List<ProductEntity>> = productDao.searchProducts(name)

    fun getCombosWithProducts(): Flow<List<ComboWithProducts>> = comboDao.getCombosWithProducts()

    suspend fun createCombo(combo: ComboEntity, productIds: List<Int>) {
        database.withTransaction {
            val comboId = comboDao.insertCombo(combo).toInt()
            productComboDao.insertAll(
                productIds.map { ProductComboEntity(idProduct = it, idCombo = comboId) }
            )
        }
    }

    suspend fun deleteCombo(combo: ComboEntity) = comboDao.deleteCombo(combo)

    suspend fun seedIfEmpty(context: Context) {
        if (productDao.count() > 0) return
        val res = context.resources

        fun List<Product>.toEntities(category: Category) = map {
            ProductEntity(
                name = it.name,
                price = it.price,
                image = res.getResourceEntryName(it.image),
                description = it.description,
                category = category.name
            )
        }

        productDao.insertProducts(
            hotDrinks.toEntities(Category.HOT_DRINKS) +
                    coldDrinks.toEntities(Category.COLD_DRINKS) +
                    salties.toEntities(Category.SALTIES) +
                    sweets.toEntities(Category.SWEETS)
        )
    }
}