package com.panostob.movieflix.ui.moviedetails.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import coil3.compose.AsyncImage
import com.panostob.movieflix.R
import com.panostob.movieflix.domain.movies.entity.MovieAuthor
import com.panostob.movieflix.domain.movies.entity.MovieReview
import com.panostob.movieflix.ui.home.composable.MovieStarRating
import com.panostob.movieflix.ui.moviedetails.model.MovieDetailsUiItem
import com.panostob.movieflix.ui.moviedetails.model.MovieDetailsUiState
import com.panostob.movieflix.ui.theme.MovieFlixTheme
import com.panostob.movieflix.util.composable.NoInternetConnectionItem
import com.panostob.movieflix.util.composable.SpacingDefault_16dp
import com.panostob.movieflix.util.composable.SpacingDouble_32dp
import com.panostob.movieflix.util.composable.SpacingHalf_8dp
import com.panostob.movieflix.util.composable.errorDrawable
import com.panostob.movieflix.util.composable.noRippleClickable
import com.panostob.movieflix.util.composable.rememberImageRequester
import com.panostob.movieflix.util.navigation.NavigationRoute
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull

@Composable
fun MovieDetailsScreen(
    uiState: State<MovieDetailsUiState>,
    navigateToRoute: (NavigationRoute) -> Unit,
) {
    if (uiState.value is MovieDetailsUiState.Success) {
        val successState = (uiState.value as MovieDetailsUiState.Success)
        val scrollState = rememberScrollState()

        MovieDetailsScreenSideEffects(
            uiState = successState,
            scrollState = scrollState,
            navigateToRoute = navigateToRoute
        )

        MovieContent(
            movie = successState.movieDetailsUiItem,
            scrollState = scrollState,
            onFavoriteMovieClick = { successState.onFavoriteMovieClick(it) },
            onSimilarMovieClick = { successState.onSimilarMovieClick(it) }
        )
    }

    NoInternetConnectionItem(
        showInternetDialog = uiState.value.showNoInternetConnectionView
    )
}

@Composable
private fun MovieDetailsScreenSideEffects(
    uiState: MovieDetailsUiState.Success,
    scrollState: ScrollState,
    navigateToRoute: (NavigationRoute) -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(key1 = uiState.movieDetailsUiItem) {
        scrollState.scrollTo(0)
    }

    LaunchedEffect(key1 = Unit) {
        snapshotFlow { uiState.navigateToRoute.value }
            .filterNotNull()
            .flowWithLifecycle(lifecycleOwner.lifecycle)
            .collectLatest { route -> navigateToRoute(route) }
    }
}

@Composable
fun MovieContent(
    movie: MovieDetailsUiItem,
    scrollState: ScrollState,
    onFavoriteMovieClick: (MovieDetailsUiItem) -> Unit,
    onSimilarMovieClick: (Int) -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(SpacingDefault_16dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f),
            model = rememberImageRequester()
                .data(data = movie.imagePath)
                .errorDrawable(R.drawable.tmdb_attribution_logo)
                .build(),
            contentScale = ContentScale.Crop,
            contentDescription = "",
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(SpacingDefault_16dp)
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
                verticalArrangement = Arrangement.spacedBy(SpacingDefault_16dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    text = movie.title,
                    color = Color.Black,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    text = movie.genres,
                    color = Color.Gray,
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = movie.releaseDate,
                    color = Color.Red,
                    style = MaterialTheme.typography.titleSmall
                )
                if (movie.starRating != null)
                    MovieStarRating(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(7f),
                        starRating = movie.starRating
                    )

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Runtime",
                    color = Color.Black,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = movie.runtime,
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Description",
                    color = Color.Black,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = movie.description,
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Cast",
                    color = Color.Black,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = movie.cast,
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Reviews",
                    color = Color.Black,
                    style = MaterialTheme.typography.titleMedium
                )
                movie.reviews.forEachIndexed { index, it ->
                    Column(Modifier.fillMaxWidth()) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Review No$index with ${it.rating ?: "no"} rating",
                            color = Color.Red,
                            style = MaterialTheme.typography.titleSmall
                        )
                        Spacer(Modifier.height(SpacingHalf_8dp))
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "${it.authorDetails.username} wrote:",
                            color = Color.Gray,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = it.content,
                            color = Color.Gray,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Similar Movies",
                    color = Color.Black,
                    style = MaterialTheme.typography.titleMedium
                )

                Row(
                    Modifier
                        .fillMaxWidth()
                        .aspectRatio(3f)
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(SpacingDefault_16dp)
                ) {
                    movie.similarMoviesImagePaths.forEach { pair ->
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxHeight()
                                .aspectRatio(0.7f, true)
                                .clip(MaterialTheme.shapes.medium)
                                .clickable { onSimilarMovieClick(pair.first) },
                            model = rememberImageRequester()
                                .data(data = pair.second)
                                .errorDrawable(R.drawable.tmdb_attribution_logo)
                                .build(),
                            contentScale = ContentScale.Crop,
                            contentDescription = "",
                        )
                    }
                }
            }
            Image(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(SpacingDouble_32dp)
                    .noRippleClickable { onFavoriteMovieClick(movie) },
                painter = painterResource(if (movie.isFavorite.value) R.drawable.ic_favorite else R.drawable.ic_not_favorite_black),
                contentDescription = ""
            )
        }
    }
}

@Preview
@Composable
fun MovieContentPreview() {
    MovieFlixTheme {
        MovieContent(
            movie = MovieDetailsUiItem(
                id = 912649,
                title = "Venom Collection",
                imagePath = "",
                genres = "Science Fiction, Adventure, Action",
                releaseDate = "2024-10-22",
                runtime = "1h 45min",
                description = "Eddie and Venom are on the run. Hunted by both of their worlds and with the net closing in, the duo are forced into a devastating decision that will bring the curtains down on Venom and Eddie's last dance.",
                cast = "Brie Larson, Teyonah Paris, Iman Vellani, Zawe Ashton",
                reviews = listOf(
                    MovieReview(
                        id = "1",
                        content = "A solid enough send off for this <em>'Venom'</em> trilogy.\\r\\n\\r\\n<em>'Venom: The Last Dance'</em> is decently amusing and produces enough entertainment with its plot. I didn't personally find it overly funny, though the person a few seats across from me had an absolute blast - never a bad thing seeing people enjoy themselves!\\r\\n\\r\\nTom Hardy remains the key element of these films, they would be far less enjoyable without his presence. There's a nice montage of sorts towards the end, it admittedly didn't 'hit' all that much for me but I imagine it's effective for proper fans of the series. Rhys Ifans and Chiwetel Ejiofor stick out most from the other characters.",
                        url = "https://www.themoviedb.org/review/671be28e9ff681d9e0a410bd",
                        authorDetails = MovieAuthor(
                            name = "john",
                            username = "john",
                            authorImage = ""
                        ),
                        rating = 7.0,
                    )
                ),
                starRating = 3.5f,
                isFavorite = remember { mutableStateOf(false) },
                similarMoviesImagePaths = listOf()
            ),
            onFavoriteMovieClick = {},
            onSimilarMovieClick = {},
            scrollState = rememberScrollState()
        )
    }
}