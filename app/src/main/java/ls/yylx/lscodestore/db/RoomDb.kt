package ls.yylx.lscodestore.db

import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context


@Database(entities = [Specie::class], version = 10)
@TypeConverters(Converters::class)
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
                instance = Room.databaseBuilder(context.applicationContext, RoomDb::class.java, "ls")
                        .addCallback(object : RoomDatabase.Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {

                            }
                        })
                        .allowMainThreadQueries()//允许主线程查询
//                        .addMigrations(MIGRATION_update)//更新策略
                        .fallbackToDestructiveMigration()//版本更新清空数据库
                        .build()
            }
            return instance!!
        }
    }

}