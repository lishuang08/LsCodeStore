package ls.yylx.lscodestore.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call


class MainViewModel(app: Application) : AndroidViewModel(app) {


//    val list = MutableLiveData<List<BookItem>>()
//    val success = SingleLiveEvent<Boolean>()
//
//    lateinit var call: Call<BooksVolumes>
//
//    lateinit var job: Job
//
//    override fun onCleared() {
//        super.onCleared()
//        println("MainViewModel   onCleared ")
//        if (this::job.isInitialized) job.cancel() else println("job  no Lateinit")
//        if (this::call.isInitialized) call.cancel() else println("call  no Lateinit")
//
//    }


//    fun getData(query: String, max: Int = 40) = async {
//
//        var backState = false
//        try {
//            val back = retrofitBook.get(query, max).execute()
//            if (back.isSuccessful) {
//                val backList = back.body()?.items ?: emptyList()
//                if (backList.isNotEmpty()) {
//                    list.postValue(backList)
//                    launch {
//                        val bookDao = roomdb.bookDao()
//                        backList.forEach {
//                            bookDao.insert(it)
//                        }
//                    }
//                    roomdb.searchDao().insert(SearchHistory(query, System.currentTimeMillis()))
//                    backState = true
//                }
//            }
//        } catch (e: Exception) {
//            Logger.e(e.toString())
//        }
//
//
//        println()
//        backState
//    }


//    fun getData(query: String, max: Int = 40) {
//
//        job = GlobalScope. launch {
//
//            var backState = false
//            try {
//                call = retrofitBook.get(query, max)
//                val back = call.execute()
//
//                if (back.isSuccessful) {
//                    val backList = back.body()?.items ?: emptyList()
//                    if (backList.isNotEmpty()) {
//                        list.postValue(backList)
//                        launch {
//                            val bookDao = roomdb.bookDao()
//                            backList.forEach {
//                                bookDao.insert(it)
//                            }
//                        }
//                        roomdb.searchDao().insert(SearchHistory(query, System.currentTimeMillis()))
//                        backState = true
//                    }
//                }
//            } catch (e: Exception) {
//                Logger.e(e.toString())
//            }
//
//
//            println()
//            success.postValue(backState)
//        }
//    }

}