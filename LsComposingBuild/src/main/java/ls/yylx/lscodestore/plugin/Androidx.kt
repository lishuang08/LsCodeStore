package ls.yylx.lscodestore.plugin

object Androidx {
    //基本库 - 最常使用
    val appcompat = "androidx.appcompat:appcompat:1.2.0-alpha01"
    val arch_version = "2.1.0"
    val testImplementation_arch_core_testing =
        "androidx.arch.core:core-testing:$arch_version" //testImplementation
    //    val arch_core = "androidx.arch.core:${Versions.arch}" //用于测试相关

    val browser = "androidx.browser:browser:1.3.0-alpha05"//用于启动默认浏览器
    val core = "androidx.core:core-ktx:1.3.1"
    val constraintlayout = "androidx.constraintlayout:constraintlayout:2.0.1" //约束布局
    val collection = "androidx.collection:collection-ktx:1.1.0" //集合库
    val cardview = "androidx.cardview:cardview:1.0.0" //cardview
    val customview = "androidx.customview:customview:1.1.0" //customview

    // camerax库
    val camerax_version = "1.0.0-beta08"
    val camera_core = "androidx.camera:camera-core:${camerax_version}" //相机库
    val camera_camera2 =
        "androidx.camera:camera-camera2:${camerax_version}"//可选，camera2，因为core间接的包含camera2
    val camera_lifecycle =
        "androidx.camera:camera-lifecycle:${camerax_version}"//可选，使用camera-lifecycle库
    val camera_view = "androidx.camera:camera-view:1.0.0-alpha15"//可选，使用 CameraX View
    val camera_extensions = "androidx.camera:camera-extensions:1.0.0-alpha15" //可选，使用  CameraX 扩展库

    val drawerlayout = "androidx.drawerlayout:drawerlayout:1.1.1"
    val dynamicanimation = "androidx.dynamicanimation:dynamicanimation-ktx:1.0.0-alpha03" //弹簧动画

    val fragment = "androidx.fragment:fragment-ktx:1.3.0-alpha08"

    val lifecycle_version = "2.3.0-alpha07"
    val lifecycle_viewmodel =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${lifecycle_version}"// ViewModel
    val lifecycle_livedata =
        "androidx.lifecycle:lifecycle-livedata-ktx:${lifecycle_version}" // LiveData
    val lifecycle_runtime =
        "androidx.lifecycle:lifecycle-runtime-ktx:${lifecycle_version}" // Lifecycles only (without ViewModel or LiveData)
    val lifecycle_savestate =
        "androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycle_version"   // Saved state module for ViewModel
    val kapt_lifecycle_compiler = "androidx.lifecycle:lifecycle-compiler:$lifecycle_version" //kapt
    val lifecycle_common = "androidx.lifecycle:lifecycle-common-java8:${lifecycle_version}"//Java8
    val lifecycle_service =
        "androidx.lifecycle:lifecycle-service:$lifecycle_version"   // optional - helpers for implementing LifecycleOwner in a Service
    val lifecycle_process =
        "androidx.lifecycle:lifecycle-process:$lifecycle_version"   // optional - helpers for implementing LifecycleOwner in a Service
    val lifecycle_reactivestreams =
        "androidx.lifecycle:lifecycle-reactivestreams-ktx:$lifecycle_version"   // optional - helpers for implementing LifecycleOwner in a Service

    val paging_version = "3.0.0-alpha06"
    val paging_runtime = "androidx.paging:paging-runtime:$paging_version"
    val testImplementation_paging_common =
        "androidx.paging:paging-common:$paging_version" // alternatively - without Android dependencies for tests
    val paging_rxjava2 =
        "androidx.paging:paging-rxjava2:$paging_version"// optional - RxJava2 support
    val paging_guava =
        "androidx.paging:paging-guava:$paging_version"// optional - Guava ListenableFuture support

    val media2_version = "1.1.0-alpha01"
    val media2_session =
        "androidx.media2:media2-session:$media2_version" // Interacting with MediaSessions
    val media2_widget =
        "androidx.media2:media2-widget:$media2_version"  // optional - UI widgets for VideoView and MediaControlView
    val media2_player =
        "androidx.media2:media2-player:$media2_version"   // optional - Implementation of a SessionPlayer

    val material = "com.google.android.material:material:1.3.0-alpha03"

    val nav_version = "2.3.0"
    val nav_fragment = "androidx.navigation:navigation-fragment-ktx:${nav_version}"
    val nav_ui = "androidx.navigation:navigation-ui-ktx:${nav_version}"

    val room_version = "2.3.0-alpha02"
    val room_runtime = "androidx.room:room-runtime:$room_version"
    val kapt_room_compiler = "androidx.room:room-compiler:$room_version"
    val room_ktx =
        "androidx.room:room-ktx:$room_version" // optional - Kotlin Extensions and Coroutines support for Room
    val testImplementation_room_testing =
        "androidx.room:room-testing:$room_version"// optional - Test helpers

    val recycleview = "androidx.recyclerview:recyclerview:1.2.0-alpha05"
    val recycleview_selection = "androidx.recyclerview:recyclerview-selection:1.2.0-alpha05"//用于控制触摸和鼠标驱动的选择

    val viewpager2 = "androidx.viewpager2:viewpager2:1.1.0-alpha01"//viewpager2


    val biometric = "androidx.biometric:biometric:1.0.1" //生物识别
    val startup = "androidx.startup:startup-runtime:1.0.0-beta01"//启动
    val asynclayoutinflater = "androidx.asynclayoutinflater:asynclayoutinflater:1.0.0"//异步布局填充
    val autofill = "androidx.autofill:autofill:1.0.0" //自动填写

    val work_version = "2.5.0-alpha01"
    val work_ktx = "androidx.work:work-runtime-ktx:$work_version"// Kotlin + coroutines
    val work_rxjava2 = "androidx.work:work-rxjava2:$work_version"   // optional - RxJava2 support
    val work_gcm = "androidx.work:work-gcm:$work_version" // optional - GCMNetworkManager support
    val androidTestImplementation_work_testing =
        "androidx.work:work-testing:$work_version"// optional - Test helpers


}