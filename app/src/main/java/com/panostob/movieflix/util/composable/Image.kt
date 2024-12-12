package com.panostob.movieflix.util.composable

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import coil3.asImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest

@Composable
fun rememberImageRequester(
    context: Context = LocalContext.current,
): ImageRequest.Builder {
    return remember {
        ImageRequest.Builder(context).memoryCachePolicy(CachePolicy.ENABLED)
    }
}

fun ImageRequest.Builder.errorDrawable(@DrawableRes drawableResId: Int?) =
    error { factory ->
        drawableResId?.let { res ->
            factory.context.getDrawableCompat(res).asImage()
        }
    }

private fun Context.getDrawableCompat(@DrawableRes resId: Int): Drawable {
    return checkNotNull(AppCompatResources.getDrawable(this, resId)) { "Invalid resource ID: $resId" }
}