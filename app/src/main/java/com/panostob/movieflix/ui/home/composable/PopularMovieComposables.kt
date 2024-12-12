package com.panostob.movieflix.ui.home.composable

import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import coil3.compose.AsyncImage
import com.panostob.movieflix.R
import com.panostob.movieflix.ui.home.model.PopularMovieUiItem
import com.panostob.movieflix.ui.theme.MovieFlixTheme
import com.panostob.movieflix.ui.theme.StarRatingFilled
import com.panostob.movieflix.util.composable.LoadingProgress
import com.panostob.movieflix.util.composable.SpacingDefault_16dp
import com.panostob.movieflix.util.composable.SpacingHalf_8dp
import com.panostob.movieflix.util.composable.SpacingTriple_48dp
import com.panostob.movieflix.util.composable.clickableWithLifecycle
import com.panostob.movieflix.util.composable.errorDrawable
import com.panostob.movieflix.util.composable.noRippleClickable
import com.panostob.movieflix.util.composable.rememberImageRequester

@Composable
fun PopularMovieItems(
    popularMovies: LazyPagingItems<PopularMovieUiItem>,
    onMovieItemClick: (Int) -> Unit,
    onFavoriteMovieClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = SpacingHalf_8dp, vertical = SpacingDefault_16dp),
        verticalArrangement = Arrangement.spacedBy(SpacingDefault_16dp),
    ) {
        items(
            count = popularMovies.itemCount,
            contentType = popularMovies.itemContentType { "popular_movie" },
        ) { index ->
            popularMovies[index]?.let { movie ->
                PopularMovieItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.3f)
                        .clip(MaterialTheme.shapes.medium)
                        .clickableWithLifecycle { onMovieItemClick(movie.id) },
                    movie = movie,
                    onFavoriteMovieClick = onFavoriteMovieClick
                )
            }
        }

        popularMoviesAppend(
            appendLoadState = popularMovies.loadState.append,
            onRetryClick = { popularMovies.retry() }
        )
    }
}

private fun LazyListScope.popularMoviesAppend(
    appendLoadState: LoadState,
    onRetryClick: () -> Unit,
) {
    when (appendLoadState) {
        is LoadState.Loading -> item {
            LoadingProgress(
                modifier = Modifier.fillMaxWidth(),
                loaderSize = SpacingTriple_48dp
            )
        }

        is LoadState.Error -> item {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth()
                    .clip(MaterialTheme.shapes.small)
                    .clickable { onRetryClick() }
                    .padding(horizontal = SpacingDefault_16dp, vertical = SpacingHalf_8dp),
                text = stringResource(id = R.string.popular_movies_failed_to_load_more),
                style = MaterialTheme.typography.labelMedium
            )
        }

        else -> Unit
    }
}

@Composable
fun PopularMovieItem(
    modifier: Modifier,
    movie: PopularMovieUiItem,
    onFavoriteMovieClick: (Int) -> Unit
) {
    Box(
        modifier
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize(),
            model = rememberImageRequester()
                .data(data = movie.imagePath)
                .errorDrawable(R.drawable.tmdb_attribution_logo)
                .build(),
            contentScale = ContentScale.Crop,
            contentDescription = "",
        )
        Column(
            Modifier
                .fillMaxWidth()
                .aspectRatio(5f)
                .background(Color.Black)
                .align(Alignment.BottomCenter)
                .padding(SpacingDefault_16dp)
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f, true),
                    text = movie.title,
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Black
                )
                Image(
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f, true)
                        .noRippleClickable { onFavoriteMovieClick(movie.id) },
                    painter = painterResource(if (movie.isFavorite.value) R.drawable.ic_favorite else R.drawable.ic_not_favorite),
                    contentDescription = ""
                )
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(SpacingHalf_8dp)
            ) {
                if (movie.starRating != null)
                    MovieStarRating(
                        modifier = Modifier.fillMaxHeight(),
                        starRating = movie.starRating
                    )
                Text(
                    modifier = Modifier.weight(1f, true),
                    text = movie.date,
                    color = Color.White,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}

@Composable
fun MovieStarRating(
    modifier: Modifier,
    starRating: Float
) {
    Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        repeat(5) {
            Image(
                modifier = Modifier
                    .fillMaxHeight(0.7f)
                    .aspectRatio(1f)
                    .drawWithCache {
                        onDrawWithContent {
                            drawContent()
                            if (starRating != null) {
                                with(drawContext.canvas.nativeCanvas) {
                                    val paint = android.graphics
                                        .Paint()
                                        .apply {
                                            color = StarRatingFilled.toArgb()
                                            xfermode = PorterDuffXfermode(PorterDuff.Mode.OVERLAY)
                                        }
                                    drawRect(
                                        0f,
                                        0f,
                                        size.width * (starRating - it).coerceIn(0f, 1f),
                                        size.height,
                                        paint
                                    )
                                }
                            }
                        }
                    },
                colorFilter = ColorFilter.tint(color = Color.Gray),
                painter = painterResource(R.drawable.img_star),
                contentDescription = ""
            )
        }
    }
}

@Preview
@Composable
fun PopularMovieItemPreview() {
    MovieFlixTheme {
        PopularMovieItem(
            modifier = Modifier,
            movie = PopularMovieUiItem(
                id = 912649,
                title = "Venom Collection",
                imagePath = "",
                date = "2024-10-22",
                starRating = 3.35f,
            ),
            onFavoriteMovieClick = {}
        )
    }
}