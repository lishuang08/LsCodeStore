package ls.yylx.lscodestore.plugin


object Other {
    val mobile_ffmpeg = "com.arthenica:mobile-ffmpeg-full-gpl:4.4"

    val opencv = "com.quickbirdstudios:opencv:4.1.0-contrib"
    val oboe = "com.google.oboe:oboe:1.4.3" //oboe 低延迟音频ndk

    val circleimageview = "de.hdodenhof:circleimageview:3.1.0" //圆形图片库
    val photoview = "com.github.chrisbanes:PhotoView:2.3.0" //图片缩放

    val live_event_bus_x = "com.jeremyliao:live-event-bus-x:1.5.1"//替代eventbus，基于livedata的通讯库，可以跨进程
    val qrcode_zbar = "cn.bingoogolapple:bga-qrcode-zbar:1.3.6"//高效二维码库，基于zbar
    val sttusbar = "com.jaeger.statusbarutil:library:1.5.1" //statusbar库
    val logger = "com.orhanobut:logger:2.2.0" //log打印库
    val gson = "com.google.code.gson:gson:2.8.6" // gson
    val kotson = "com.github.salomonbrys.kotson:kotson:2.5.0" //gson 库的kotlin版本
    val mmkv = "com.tencent:mmkv-static:1.2.1" // 替代sp的库，高效，稳定，基于protobuf的本地储存库
    val agentweb = "com.just.agentweb:agentweb:4.1.3" // webview库
    val brvah = "com.github.CymChad:BaseRecyclerViewAdapterHelper:3.0.4" //recycleview的adapter库
    val jsoup = "org.jsoup:jsoup:1.13.1" //解析html数据的库

    //下拉刷新库

    val srl_kernel = "com.scwang.smart:refresh-layout-kernel:${Versions.srl_version}"      //核心必须依赖
    val srl_classics = "com.scwang.smart:refresh-header-classics:${Versions.srl_version}"    //经典刷新头
    val srl_material = "com.scwang.smart:refresh-header-material:${Versions.srl_version}"    //谷歌刷新头
    val srl_footer = "com.scwang.smart:refresh-footer-classics:${Versions.srl_version}"    //经典加载
    val srl_radar = "com.scwang.smart:refresh-header-radar:{Versions.srl_version}"       //雷达刷新头
    val srl_falsify = "com.scwang.smart:refresh-header-falsify:{Versions.srl_version}"     //虚拟刷新头
    val srl_two_level =
        "com.scwang.smart:refresh-header-two-level:${Versions.srl_version}"   //二级刷新头
    val srl_ball = "com.scwang.smart:refresh-footer-ball:${Versions.srl_version}"        //球脉冲加载


    //material design风格的dialog库

    val material_dialog = "com.afollestad.material-dialogs:core:${Versions.md_version}" //核心库
    val material_lifecycle =
        "com.afollestad.material-dialogs:lifecycle:${Versions.md_version}"//lifecycle支持
    val material_datetime = "com.afollestad.material-dialogs:datetime:${Versions.md_version}"//日期选择酷
    val material_files = "com.afollestad.material-dialogs:files:${Versions.md_version}"//文件选择库
    val material_input = "com.afollestad.material-dialogs:input:${Versions.md_version}" //输入框样式


    val rxffmpeg  = "com.github.microshow:RxFFmpeg:4.8.0"

    val junit = "junit:junit:${Versions.junit}"
    val junitExt = "androidx.test.ext:junit:${Versions.junitExt}"
    val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
}
