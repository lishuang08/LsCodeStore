package ls.yylx.lscodestore.basemodule.backgroud.room

import android.content.Context
import androidx.annotation.NonNull
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.orhanobut.logger.Logger
import ls.yylx.lscodestore.basemodule.db.RoomDb.Companion.singleRoom
import ls.yylx.lscodestore.basemodule.network.SingleRetrofit.retrofitGbif

class RoomWork(@NonNull context: Context, @NonNull workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val bookDao = singleRoom.gbifDao()

        var pageNum = bookDao.countAll() / 1000

        var endOfRecords = false

        while (!endOfRecords && pageNum <= 100) {
            try {
                val back = retrofitGbif.getSpecies(pageNum * 1000, 1000).execute()
                if (back.isSuccessful) {
                    val backList = back.body()?.results ?: emptyList()
                    if (backList.isNotEmpty()) {
                        endOfRecords = back.body()?.endOfRecords ?: true
                        if (!endOfRecords) pageNum++
                        bookDao.insert(backList)
                    }
                } else {
                    return Result.failure()
                }
            } catch (e: Exception) {
                Logger.e(e.toString())
                return Result.failure()
            }
        }
        return Result.success()
    }
}