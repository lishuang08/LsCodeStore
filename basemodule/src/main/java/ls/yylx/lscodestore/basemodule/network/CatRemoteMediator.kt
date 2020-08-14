package ls.yylx.lscodestore.basemodule.network

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.LoadType.*
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import ls.yylx.lscodestore.basemodule.db.RoomDb
import ls.yylx.lscodestore.basemodule.db.Specie
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class CatRemoteMediator : RemoteMediator<Int, Specie>() {
    val bookDao by lazy {
        RoomDb.singleRoom.gbifDao()
    }

    var pageNum = bookDao.countAll() / 1000

    var endOfRecords = false


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Specie>
    ): MediatorResult {
        try {
            // Get the closest item from PagingState that we want to load data around.
            val loadKey = when (loadType) {
                REFRESH -> null
                PREPEND -> return RemoteMediator.MediatorResult.Success(endOfPaginationReached = true)
                APPEND -> {


                }
            }
            val back = SingleRetrofit.retrofitGbif.getSpecies(pageNum * 1000, 1000).execute()
            if (back.isSuccessful) {
                val backList = back.body()?.results ?: emptyList()
                if (backList.isNotEmpty()) {
                    endOfRecords = back.body()?.endOfRecords ?: true
                    if (!endOfRecords) pageNum++
                    bookDao.insert(backList)
                }
            } else {

            }




            return RemoteMediator.MediatorResult.Success(endOfPaginationReached = back.body()?.results.isNullOrEmpty())
        } catch (e: IOException) {
            return RemoteMediator.MediatorResult.Error(e)
        } catch (e: HttpException) {
            return RemoteMediator.MediatorResult.Error(e)
        }
    }


}
