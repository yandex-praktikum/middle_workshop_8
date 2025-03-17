package yandex.practicum.middle.android.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ServerScreen(viewModel: ServerViewModel = hiltViewModel()) {
    val isAdvertising by viewModel.isAdvertisingState.collectAsState()

    Column {
        Text("BLE Сервер", style = MaterialTheme.typography.headlineMedium)
        Button(onClick = { viewModel.toggleAdvertising() }) {
            Text("${if (isAdvertising) "Закончить" else "Начать"} Advertising")
        }
        Button(onClick = { viewModel.increaseCharacteristic() }) {
            Text("Изменить характеристику")
        }
        Button(onClick = { viewModel.increaseDescriptor() }) {
            Text("Изменить дескриптор")
        }
    }
}