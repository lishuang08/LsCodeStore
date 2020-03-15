package ls.yylx.lscodestore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.kaipuyun.basemodule.BuildConfig

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!resources.getBoolean(R.bool.isModule)){
            setContentView(R.layout.main_activity)
        }else {
            setContentView(R.layout.main_activity_relase)
        }



    }
}
