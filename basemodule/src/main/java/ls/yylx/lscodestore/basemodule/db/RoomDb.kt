package ls.yylx.lscodestore.basemodule.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ls.yylx.lscodestore.basemodule.base.BaseAppliacation
import ls.yylx.lscodestore.basemodule.base.BaseAppliacation.Companion.appCtx


@Database(entities = [Specie::class], version = 4)
//@TypeConverters(Converters::class)
abstract class RoomDb : RoomDatabase() {
    abstract fun gbifDao(): GbifDao

    fun deleteAll() {
        gbifDao().deleteAll()
    }


    companion object {
        val singleRoom: RoomDb by lazy {
            Room.databaseBuilder(
                appCtx,
                RoomDb::class.java, "lscode.db"
            ).allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }
    }

}