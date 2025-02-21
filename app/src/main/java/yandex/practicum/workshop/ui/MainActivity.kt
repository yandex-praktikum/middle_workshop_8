package yandex.practicum.workshop.ui


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import yandex.practicum.workshop.WorkshopApplication

//@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                AppNavHost()
            }
        }
    }
}

@Composable
private fun AppNavHost() {
    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    val app = LocalContext.current.applicationContext as WorkshopApplication

    NavHost(navController = navController, startDestination = Destinations.Login.name) {
        composable(Destinations.Profile.name) {
            Screen("Профиль", navIcon = {
                IconButton(onClick = {
                    coroutineScope.launch {
                        navController.navigate(Destinations.Login.name)
                    }
                }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "")
                }
            }) {
                ProfileScreen(viewModel {
                    app.appComponent.profileComponentFactory().create().getViewModel()
                })
            }
        }
        composable(Destinations.Login.name) {
            Screen("Авторизация") {
                LoginScreen(viewModel {
                    app.appComponent.loginComponentFactory().create().getViewModel()
                }, navController)
            }
        }
    }
}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
private fun Screen(
    title: String,
    navIcon: @Composable () -> Unit = {},
    actions: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    Scaffold(topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
            title = { Text(title) },
            navigationIcon = { navIcon() },
            actions = { actions() },
        )
    }) {
        content()
    }
}

enum class Destinations {
    Profile, Login
}
