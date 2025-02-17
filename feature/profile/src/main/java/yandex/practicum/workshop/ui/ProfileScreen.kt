package yandex.practicum.workshop.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun ProfileScreen(viewModel: ProfileViewModel = hiltViewModel()) {
    Box(Modifier.fillMaxSize()) {
        Text(
            text = "Привет, пользователь!",
            modifier = Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.headlineMedium
        )
    }
}
