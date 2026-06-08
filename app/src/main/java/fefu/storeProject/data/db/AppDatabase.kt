package fefu.storeProject.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [ProductEntity::class, CategoryEntity::class, CartItemEntity::class],
    version = 2,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun catalogDao(): CatalogDao

    abstract fun cartDao(): CartDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        private val migration1To2 =
            object : Migration(1, 2) {
                override fun migrate(db: SupportSQLiteDatabase) {
                    db.execSQL("DELETE FROM products")
                    db.execSQL("DELETE FROM categories")
                    db.execSQL(
                        "CREATE TABLE IF NOT EXISTS `cart_items` " +
                            "(`id` TEXT NOT NULL, `productId` TEXT NOT NULL, " +
                            "`sizeId` TEXT NOT NULL, `quantity` INTEGER NOT NULL, " +
                            "PRIMARY KEY(`id`))",
                    )
                }
            }

        fun getInstance(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "catalog_db",
                )
                    .addMigrations(migration1To2)
                    .build().also { instance = it }
            }
    }
}
