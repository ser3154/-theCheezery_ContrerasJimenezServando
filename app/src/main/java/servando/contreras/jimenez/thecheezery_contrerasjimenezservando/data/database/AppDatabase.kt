package servando.contreras.jimenez.thecheezery_contrerasjimenezservando.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.data.database.entity.ProductComboEntity
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.data.database.entity.ComboEntity
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.data.database.entity.ProductEntity
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.data.database.dao.ComboDao
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.data.database.dao.ProductComboDao
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.data.database.dao.ProductDao


@Database(
    entities = [
        ProductEntity::class,
        ComboEntity::class,
        ProductComboEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao
    abstract fun comboDao(): ComboDao
    abstract fun productComboDao(): ProductComboDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "cheezery.db"
                )
                    .fallbackToDestructiveMigration(true)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}