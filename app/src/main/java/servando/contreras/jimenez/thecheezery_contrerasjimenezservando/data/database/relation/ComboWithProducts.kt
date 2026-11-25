package servando.contreras.jimenez.thecheezery_contrerasjimenezservando.data.database.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.data.database.entity.ComboEntity
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.data.database.entity.ProductEntity
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.data.database.entity.ProductComboEntity



data class ComboWithProducts(
    @Embedded
    val combo: ComboEntity,

    @Relation(
        parentColumn = "idCombo",
        entityColumn = "idProduct",
        associateBy = Junction(
            value = ProductComboEntity::class,
            parentColumn = "idCombo",
            entityColumn = "idProduct"
        )
    )
    val products: List<ProductEntity>
)