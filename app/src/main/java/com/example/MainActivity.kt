package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.LoginScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.ParkinsonViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: ParkinsonViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ParkinsonApp(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun ParkinsonApp(viewModel: ParkinsonViewModel) {
    val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
    val session by viewModel.session.collectAsStateWithLifecycle()
    val currentSection by viewModel.currentSection.collectAsStateWithLifecycle()
    val formState by viewModel.formState.collectAsStateWithLifecycle()
    val fieldErrors by viewModel.fieldErrors.collectAsStateWithLifecycle()
    val formErrorMessage by viewModel.formErrorMessage.collectAsStateWithLifecycle()
    val isPredicting by viewModel.isPredicting.collectAsStateWithLifecycle()
    val currentResult by viewModel.currentResult.collectAsStateWithLifecycle()
    val historyList by viewModel.historyList.collectAsStateWithLifecycle()
    val totalCount by viewModel.totalCount.collectAsStateWithLifecycle()
    val detectedCount by viewModel.detectedCount.collectAsStateWithLifecycle()

    AnimatedContent(
        targetState = currentScreen,
        transitionSpec = {
            fadeIn() togetherWith fadeOut()
        },
        label = "AppScreenTransition"
    ) { screen ->
        when (screen) {
            AppScreen.LOGIN -> {
                LoginScreen(
                    onLoginSuccess = { emailOrUsername, rememberMe ->
                        viewModel.loginUser(emailOrUsername, rememberMe)
                    }
                )
            }
            AppScreen.DASHBOARD -> {
                DashboardScreen(
                    session = session,
                    currentSection = currentSection,
                    formState = formState,
                    fieldErrors = fieldErrors,
                    formErrorMessage = formErrorMessage,
                    isPredicting = isPredicting,
                    currentResult = currentResult,
                    historyList = historyList,
                    totalCount = totalCount,
                    detectedCount = detectedCount,
                    onNavigateSection = { viewModel.setSection(it) },
                    onFieldChange = { key, value -> viewModel.updateField(key, value) },
                    onLoadSample = { viewModel.loadSample(it) },
                    onClearForm = { viewModel.clearForm() },
                    onPredict = { viewModel.predict() },
                    onViewResult = { viewModel.viewResult(it) },
                    onDeleteResult = { viewModel.deletePrediction(it) },
                    onClearAllHistory = { viewModel.clearAllHistory() },
                    onLogout = { viewModel.logoutUser() }
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    androidx.compose.material3.Text(text = "Hello $name!", modifier = modifier)
}

