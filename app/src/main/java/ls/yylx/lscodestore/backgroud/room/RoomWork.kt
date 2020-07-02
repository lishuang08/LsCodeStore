package ls.yylx.lscodestore.backgroud.room

import android.content.Context
import androidx.annotation.NonNull
import androidx.core.content.edit
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.orhanobut.logger.Logger
import ls.yylx.lscodestore.MyApp
import ls.yylx.lscodestore.network.SingleRetrofit.retrofitGbif

class RoomWork(@NonNull context: Context, @NonNull workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {

        val bookDao = MyApp.appDb.gbifDao()
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