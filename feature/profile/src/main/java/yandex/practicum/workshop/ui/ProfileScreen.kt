package yandex.practicum.workshop.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel
) {
    val user by viewModel.user.collectAsState()

    Box(Modifier.fillMaxSize()) {
        Text(
            text = "Привет, ${user?.name}!",
            modifier = Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.headlineMedium
        )
    }
}
