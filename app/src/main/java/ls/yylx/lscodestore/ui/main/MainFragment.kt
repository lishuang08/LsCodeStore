package ls.yylx.lscodestore.ui.main

import android.os.Bundle
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.orhanobut.logger.Logger
import ls.yylx.lscodestore.PageAdapterJob
import ls.yylx.lscodestore.WaterView
import org.jetbrains.anko.custom.customView
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.nestedScrollView

class MainFragment : Fragment() {

    val sparseArray = SparseArray<String>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return UI {
            frameLayout {
//                frameLayout {
//                    recyclerView {
//                        adapter = PageAdapterJob().apply {
//                            items = listOf(
//                                "aaaaaaaa",
//                                "aaaaaaaa",
//                                "aaaaaaaa",
//                                "aaaaaaaa",
//                                "aaaaaaaa",
//                                "aaaaaaaa",
//                                "aaaaaaaa",
//                                "aaaaaaaa",
//                                "aaaaaaaa",
//                                "aaaaaaaa",
//                                "aaaaaaaa",
//                                "aaaaaaaa",
//                                "aaaaaaaa",
//                                "aaaaaaaa",
//                                "aaaaaaaa",
//                                "aaaaaaaa",
//                                "aaaaaaaa",
//                                "aaaaaaaa",
//                                "aaaaaaaa",
//                                "aaaaaaaa",
//                                "aaaaaaaa",
//                                "bbbbbbbb",
//                                "bbbbbbbb",
//                                "bbbbbbbb",
//                                "bbbbbbbb",
//                                "bbbbbbbb",
//                                "bbbbbbbb",
//                                "bbbbbbbb",
//                                "bbbbbbbb",
//                                "bbbbbbbb",
//                                "bbbbbbbb",
//                                "bbbbbbbb",
//                                "bbbbbbbb",
//                                "bbbbbbbb",
//                                "bbbbbbbb",
//                                "bbbbbbbb",
//                                "bbbbbbbb",
//                                "bbbbbbbb"
//                            )
//                        }
//                        layoutManager = LinearLayoutManager(requireContext())
//                    }.lparams(matchParent, matchParent)
//                    customView<WaterView> {
//                        post {
//                            setNewText("水印 2111")
//                        }
//                    }.lparams(matchParent, matchParent)
//                }
//
            }
        }.view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Logger.e("a")

    }


}
