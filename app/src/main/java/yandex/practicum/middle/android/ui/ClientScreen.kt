package yandex.practicum.middle.android.ui

import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@SuppressLint("MissingPermission")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ClientScreen(viewModel: ClientViewModel = hiltViewModel()) {
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        val isScanning by viewModel.isScanningState.collectAsState()
        val scanResult by viewModel.scanResult.collectAsState()
        val permissionsState = rememberMultiplePermissionsState(
            permissions = listOf(
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.BLUETOOTH_SCAN
            )
        )

        Text("BLE Клиент", style = MaterialTheme.typography.headlineMedium)
        Button(onClick = {
            if (permissionsState.allPermissionsGranted) {
                viewModel.toggleScanning()
            } else {
                permissionsState.launchMultiplePermissionRequest()
            }
        }) {
            Text("${if (isScanning) "Закончить" else "Начать"} сканирование")
        }
        when (val result = scanResult) {
            is ScanResult.Initial -> return
            is ScanResult.Timeout -> {
                Text("Завершено по таймауту")
                return
            }

            is ScanResult.ScannedDevice -> Text("Найден: ${result.device.name}")
        }

        if (!DiscoverService(permissionsState, viewModel))
            return

        ReadCharacteristic(permissionsState, viewModel)

        WriteCharacteristic(permissionsState, viewModel)

        ReadDescriptor(permissionsState, viewModel)

        WriteDescriptor(permissionsState, viewModel)
    }
}

@SuppressLint("MissingPermission")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun WriteDescriptor(
    permissionsState: MultiplePermissionsState,
    viewModel: ClientViewModel
) {
    var newDescriptorValue by remember { mutableIntStateOf(0) }
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            modifier = Modifier.width(64.dp),
            value = "$newDescriptorValue",
            onValueChange = {
                if (it.isNotEmpty())
                    newDescriptorValue = it.toInt()
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Button(onClick = {
            if (permissionsState.allPermissionsGranted) {
                viewModel.writeDescriptor(newDescriptorValue)
            } else {
                permissionsState.launchMultiplePermissionRequest()
            }
        }) {
            Text("Записать дескриптор")
        }
    }
}

@SuppressLint("MissingPermission")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun ReadDescriptor(
    permissionsState: MultiplePermissionsState,
    viewModel: ClientViewModel,
) {
    val descriptorValue by viewModel.descriptorValue.collectAsState()
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(onClick = {
            if (permissionsState.allPermissionsGranted) {
                viewModel.readDescriptor()
            } else {
                permissionsState.launchMultiplePermissionRequest()
            }
        }) {
            Text("Считать дескриптор")
        }
        descriptorValue?.let { Text("$it", maxLines = 1) }
    }
}

@SuppressLint("MissingPermission")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun WriteCharacteristic(
    permissionsState: MultiplePermissionsState,
    viewModel: ClientViewModel
) {
    var newCharacteristicValue by remember { mutableIntStateOf(0) }
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            modifier = Modifier.width(64.dp),
            value = "$newCharacteristicValue",
            onValueChange = {
                if (it.isNotEmpty())
                    newCharacteristicValue = it.toInt()
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Button(onClick = {
            if (permissionsState.allPermissionsGranted) {
                viewModel.writeCharacteristic(newCharacteristicValue)
            } else {
                permissionsState.launchMultiplePermissionRequest()
            }
        }) {
            Text("Записать характеристику")
        }
    }
}

@SuppressLint("MissingPermission")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun ReadCharacteristic(
    permissionsState: MultiplePermissionsState,
    viewModel: ClientViewModel,
) {
    val characteristicValue by viewModel.characteristicValue.collectAsState()
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(onClick = {
            if (permissionsState.allPermissionsGranted) {
                viewModel.readCharacteristic()
            } else {
                permissionsState.launchMultiplePermissionRequest()
            }
        }) {
            Text("Считать характеристику")
        }
        characteristicValue?.let { Text("$it", maxLines = 1) }
    }
}

@SuppressLint("MissingPermission")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun DiscoverService(
    permissionsState: MultiplePermissionsState,
    viewModel: ClientViewModel,
): Boolean {
    val service by viewModel.service.collectAsState()
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(onClick = {
            if (permissionsState.allPermissionsGranted) {
                viewModel.discoverService()
            } else {
                permissionsState.launchMultiplePermissionRequest()
            }
        }) {
            Text("Найти сервис")
        }
        service?.let { Text("${it.uuid}", maxLines = 1) }
    }
    return (service != null)
}