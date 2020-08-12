package ls.yylx.lscodestore.basemodule.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Specie::class], version = 4)
//@TypeConverters(Converters::class)
abstract class RoomDb : RoomDatabase() {
    abstract fun gbifDao(): GbifDao

    fun deleteAll() {
        gbifDao().deleteAll()
    }

    companion object {
        private var instance: RoomDb? = null

        @Synchronized
        fun get(context: Context): RoomDb {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDb::class.java, "lscode.db"
                ).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance!!
        }
    }


}