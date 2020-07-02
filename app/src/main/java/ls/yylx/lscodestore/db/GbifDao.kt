package ls.yylx.lscodestore.db

import androidx.paging.DataSource
import androidx.room.*

/**
 * Created by ls on 2017/12/12.
 */
@Dao
interface GbifDao {

    @Query("SELECT * FROM specie ORDER BY  `key` ASC")
    fun species(): DataSource.Factory<Int, Specie>

    @Query("SELECT * FROM specie")
    fun loadAll(): List<Specie>

    @Delete
    fun delete(vararg specie: Specie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg specie: Specie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: List<Specie>)


    @Update
    fun update(vararg searchs: Specie)

    @Query("DELETE FROM specie")
    fun deleteAll()

    @Query("SELECT COUNT(*) FROM specie ")
    fun countAll(): Int

}