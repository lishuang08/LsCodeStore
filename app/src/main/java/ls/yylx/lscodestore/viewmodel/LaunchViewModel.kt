package ls.yylx.lscodestore.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import com.orhanobut.logger.Logger
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async


class LaunchViewModel : ViewModel() {

//    val list = MutableLiveData<List<BookItem>>()
//    val success = SingleLiveEvent<Boolean>()
//
//    lateinit var select: BookItem
//
//    fun getData() = GlobalScope.async<List<BookItem>> {
//        roomdb.bookDao().loadAll()
//
//    }
//
//
//    fun submitData() =
//        LivePagedListBuilder(roomdb.bookDao().books(), pagingConfig)
//                .build()
//
//
//    fun getShelf() =
//        GlobalScope.async<Boolean> {
//            var success = false
//            try {
//                val back = retrofitBook.getMyBookShelf().execute()
//                if (back.isSuccessful) {
//                    Logger.e(back.body().toString())
//                    success = true
//                }
//            } catch (e: Exception) {
//                Logger.e(e.toString())
//            }
//
//            success
//        }
//

}


