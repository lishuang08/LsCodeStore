package ls.yylx.lscodestore.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.work.*
import com.orhanobut.logger.Logger
import ls.yylx.lscodestore.basemodule.backgroud.room.RoomWork
import ls.yylx.lscodestore.basemodule.network.SingleRetrofit.retrofitGbif
import java.util.concurrent.TimeUnit


class GbifViewModel(app: Application) : AndroidViewModel(app) {
    val constraints by lazy {
        Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
    }
    val workRequest by lazy {
        OneTimeWorkRequest.Builder(RoomWork::class.java)
                .setInitialDelay(0, TimeUnit.SECONDS)
                .addTag("getGbifSpecie")
                .setInputData(workDataOf(

                ))
                .setConstraints(constraints)
                .build()
    }


    val list = MutableLiveData<String>()
//
//    fun submitSpecieData() =
//        LivePagedListBuilder(MyApp.appDb.gbifDao().species(), pagingConfig)
//                .build()


//    val objectBoxWork = ObjectBoxDataSource.Factory(MyApp.boxStore.boxFor(SpecieBox::class.java).query().order(SpecieBox_.key).build())
//
//    val paginglist   by  lazy {
//        LivePagedListBuilder(objectBoxWork, pagingConfig).build()
//    }



//    internal class ItemDataSource : PositionalDataSource<SpecieBox>() {
//        private fun computeCount(): Int {
//            // actual count code here
//        }
//
//        private fun loadRangeInternal(startPosition: Int, loadCount: Int): List<SpecieBox> {
//            // actual load code here
//        }
//
//        override fun loadInitial(params: LoadInitialParams,
//                                 callback: LoadInitialCallback<SpecieBox>) {
//            val totalCount = computeCount()
//            val position = computeInitialLoadPosition(params, totalCount)
//            val loadSize = computeInitialLoadSize(params, position, totalCount)
//            callback.onResult(loadRangeInternal(position, loadSize), position, totalCount)
//        }
//
//        override fun loadRange(params: LoadRangeParams,
//                               callback: LoadRangeCallback<SpecieBox>) {
//            callback.onResult(loadRangeInternal(params.startPosition, params.loadSize))
//        }
//    }

    fun getSpeciesRoot(datasetKey: String): Boolean {

        var success = false
        try {
            val back = retrofitGbif.getSpeciesRoot(datasetKey).execute()
            if (back.isSuccessful) {
                Logger.e(back.body().toString())

                success = true
            }
        } catch (e: Exception) {
            Logger.e(e.toString())
        }

        return success
    }


    fun updateSpecies() =
        WorkManager.getInstance(getApplication()).apply {
            beginUniqueWork("getGbifSpecie", ExistingWorkPolicy.REPLACE, workRequest).enqueue()
        }


    val singleSpcie by lazy(LazyThreadSafetyMode.NONE) {
        MutableLiveData<String>()
    }

    fun singleSpcie(key: String): Boolean {
        var success = false
        try {
            val back = retrofitGbif.getSingleSpecie(key).execute()
            if (back.isSuccessful) {
                Logger.e(back.body().toString())
                singleSpcie.postValue(back.body().toString())
                success = true
            }
        } catch (e: Exception) {
            Logger.e(e.toString())
        }

        return success
    }

}
