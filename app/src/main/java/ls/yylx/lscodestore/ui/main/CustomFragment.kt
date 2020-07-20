package ls.yylx.lscodestore.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ls.yylx.lscodestore.base.BaseFragment
import ls.yylx.lscodestore.ui.splittiesview.ViewCustom
import splitties.views.dsl.core.*


class CustomFragment : BaseFragment() {
    val ui by lazy(LazyThreadSafetyMode.NONE) {
        ViewCustom(requireContext())
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ui.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}