package com.example.itmotry

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.itmo_mega_olymp_app.ui.theme.ItmoTryTheme
import com.example.itmotry.di.initKoin
import com.example.itmotry.ui.main.MainScreen
import com.example.itmotry.ui.main.MainScreenViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.getValue

class MainActivity : AppCompatActivity() {

    private val viewModel: MainScreenViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initKoin {
            androidContext(this@MainActivity)
        }
        viewModel.load()
        setContent {
            MaterialTheme {
                MainScreen(
                    uiState = viewModel.uiState.collectAsState().value,
                    retry = { viewModel.retry() },
                    load = { viewModel.load() },
                    loadMore = { viewModel.loadMore() },
                    find = { viewModel.search(it) },
                    onNewsClicked = { viewModel.showNewsDialog(it) },
                    closeDialog = { viewModel.closeNewsDialog() },
                )
            }
        }
    }
}
