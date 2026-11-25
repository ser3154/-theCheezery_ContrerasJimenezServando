package servando.contreras.jimenez.thecheezery_contrerasjimenezservando.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.data.database.entity.ProductComboEntity

@Dao
interface ProductComboDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductCombo(productCombo: ProductComboEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(productCombos: List<ProductComboEntity>)

    @Query("DELETE FROM ProductsCombo WHERE idCombo = :comboId")
    suspend fun deleteByCombo(comboId: Int)
}