package servando.contreras.jimenez.thecheezery_contrerasjimenezservando.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import servando.contreras.jimenez.thecheezery_contrerasjimenezservando.R

@Composable
fun rememberProductImage(imageName: String?): Int {
    val context = LocalContext.current
    return remember(imageName) {
        val id = imageName?.let {
            context.resources.getIdentifier(it, "drawable", context.packageName)
        } ?: 0
        if (id != 0) id else R.drawable.ic_launcher_foreground
    }
}