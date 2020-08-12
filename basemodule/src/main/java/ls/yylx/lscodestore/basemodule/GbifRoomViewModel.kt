package ls.yylx.lscodestore.basemodule

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import ls.yylx.lscodestore.basemodule.db.RoomDb
import ls.yylx.lscodestore.basemodule.db.Specie

@ExperimentalCoroutinesApi
class GbifRoomViewModel(app: Application) : AndroidViewModel(app),
    CoroutineScope by CoroutineScope(Dispatchers.IO) {
    val gbifDao = RoomDb.get(app).gbifDao()

    val dataLiveD = MutableLiveData<List<Specie>>()
    val dataFlow = MutableStateFlow<List<Specie>>(emptyList())


    val list by lazy(LazyThreadSafetyMode.NONE) {
        gbifDao.loadAll()
    }

}