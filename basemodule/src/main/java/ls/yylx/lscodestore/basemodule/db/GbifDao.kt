package ls.yylx.lscodestore.basemodule.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagingSource
import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * Created by ls on 2017/12/12.
 */
@Dao
interface GbifDao {

    @Query("SELECT * FROM specie ORDER BY  `key` ASC")
    fun species(): DataSource.Factory<Int, Specie>

    @Query("SELECT * FROM specie")
    fun loadFlowAll(): Flow<List<Specie>>

    @Query("SELECT * FROM specie")
    fun loadLiveAll(): LiveData<List<Specie>>

    @Query("SELECT * FROM specie")
    fun loadAll(): PagingSource<Int, Specie>

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