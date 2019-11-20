package io.zzl.app.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import io.zzl.app.model.data.Beauty
import io.zzl.app.model.local.converter.DateConverter
import io.zzl.app.model.local.dao.BeautyDAO

@Database(entities = [Beauty::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun beautyDao(): BeautyDAO

    companion object {

/*
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
                }
*/

        private var INSTANCE: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase =
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java, "app.db")
                        .addCallback(CALLBACK)
                        .build()

        private val CALLBACK = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                /*CoroutineScope(Dispatchers.IO).launch {
                    val getAnnotation = AppDatabase.javaClass.getAnnotation(Database::class.java)
                    getAnnotation.entities.map {
                        db.execSQL("ALTER TABLE $it.java.toString() ADD COLUMN timestamp_zzl TIMESTAMP" +
                                " NOT NULL DEFAULT (datetime('now','localtime'))" +
                                " ON UPDATE (datetime('now','localtime'))")
                        db.execSQL("CREATE TRIGGER UpdateLastTime UPDATE OF" +
                                "   field1, field2, fieldN ON Package" +
                                " BEGIN" +
                                "   UPDATE Package SET LastUpdate=CURRENT_TIMESTAMP" +
                                "       WHERE ActionId=ActionId;" +
                                " END;")
                    }
                }*/
                return
            }
        }
    }
}