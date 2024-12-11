package com.panostob.movieflix.ui.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.snapshotFlow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.panostob.movieflix.ui.app.composable.AppScreen
import com.panostob.movieflix.ui.app.model.AppUiState
import com.panostob.movieflix.util.ext.restartApp
import com.panostob.movieflix.util.livedata.LoadingLiveData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull

@AndroidEntryPoint
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