package ls.yylx.lscodestore.ui.coroutines.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ls.yylx.lscodestore.MyApp
import ls.yylx.lscodestore.db.Specie

@ExperimentalCoroutinesApi
class GbifRoomViewModel(app: Application) : AndroidViewModel(app),
    CoroutineScope by CoroutineScope(Dispatchers.IO) {
    val gbifDao = MyApp.appDb.gbifDao()

    val dataLiveD = MutableLiveData<List<Specie>>()
    val dataFlow = MutableStateFlow<List<Specie>>(emptyList())

    fun getAll() {
        launch {
           val list=  gbifDao.loadAll()
            dataLiveD.postValue(list )


        }
    }


}