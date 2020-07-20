package ls.yylx.lscodestore.ui.coroutines

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.orhanobut.logger.Logger
import kotlinx.coroutines.flow.StateFlow
import ls.yylx.lscodestore.base.BaseFragment
import ls.yylx.lscodestore.db.GbifDao
import ls.yylx.lscodestore.db.Specie
import ls.yylx.lscodestore.ui.coroutines.viewmodel.GbifRoomViewModel

class FlowFragment  :BaseFragment(){
    val flowVm by viewModels<GbifRoomViewModel> ()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenStarted {
            val flow = flowVm.gbifDao.loadFlowAll()
            flow.asLiveData().observe(viewLifecycleOwner, Observer {
            })
        }

    }
}