package ls.yylx.lscodestore.plugin

object Versions {

    val support_lib = "27.0.2"
    val retrofit = "2.3.0"
    val rxjava = "2.1.9"
    val kotlin_stdlib_jdk7 = "1.3.41"

    val constraintlayout = "1.1.3"
    val junit = "4.12"
    val junitExt = "1.1.1"
    val espressoCore = "3.2.0"
}




object Deps {
    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val kotlinStdlibJdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin_stdlib_jdk7}"

    val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"
    val junit = "junit:junit:${Versions.junit}"
    val junitExt = "androidx.test.ext:junit:${Versions.junitExt}"
    val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"


}
