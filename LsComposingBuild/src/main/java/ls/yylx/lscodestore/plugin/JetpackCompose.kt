package ls.yylx.lscodestore.plugin

object JetpackCompose {

    val ui = "androidx.compose.ui:ui:${Versions.compose_version}"
    val ui_tooling =
        "androidx.ui:ui-tooling:${Versions.compose_version}"  // Tooling support (Previews, etc.)
    val foundation =
        "androidx.compose.foundation:foundation:${Versions.compose_version}"  // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)

    val material = "androidx.compose.material:material:${Versions.compose_version}" // Material Design

    val material_core =  "androidx.compose.material:material-icons-core:${Versions.compose_version}"// Material design icons
    val material_extended =  "androidx.compose.material:material-icons-extended:${Versions.compose_version}"

    val rxjava2=   "androidx.compose.runtime:runtime-rxjava2:${Versions.compose_version}" // Integration with observables
    val livedata = "androidx.compose.runtime:runtime-livedata:${Versions.compose_version}"

    val androidTestImplementation_test =  "androidx.ui:ui-test:${Versions.compose_version}"  // UI Tests
}