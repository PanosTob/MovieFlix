package com.panostob.movieflix

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.panostob.movieflix.ui.app.AppViewModel
import com.panostob.movieflix.ui.app.composable.AppScreen
import com.panostob.movieflix.ui.app.model.AppUiState
import com.panostob.movieflix.ui.theme.MovieFlixTheme
import com.panostob.movieflix.util.ext.restartApp
import com.panostob.movieflix.util.livedata.LoadingLiveData
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull

class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val navController: NavHostController = rememberNavController()
            val viewModel: AppViewModel = hiltViewModel()
            val uiState = viewModel.uiState.collectAsStateWithLifecycle()

            LoadingLiveData.observe(this) { isLoading -> viewModel.handleLoading(isLoading) }

            AppScreenSideEffects(
                uiState = uiState,
                onApplicationRestart = { restartApp() }
            )

            AppScreen(
                navController = navController,
                uiState = uiState
            )
        }
    }
}

@Composable
private fun AppScreenSideEffects(
    uiState: State<AppUiState>,
    onApplicationRestart: () -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(key1 = Unit) {
        snapshotFlow { uiState.value.restartApplication.value }
            .filterNotNull()
            .flowWithLifecycle(lifecycleOwner.lifecycle)
            .collectLatest { onApplicationRestart() }
    }
}