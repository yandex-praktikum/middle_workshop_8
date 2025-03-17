package yandex.practicum.middle.android.ui

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import no.nordicsemi.android.kotlin.ble.client.main.service.ClientBleGattService
import no.nordicsemi.android.kotlin.ble.core.ServerDevice
import javax.inject.Inject

sealed interface ScanResult {
    data object Initial : ScanResult
    data object Timeout : ScanResult
    data class ScannedDevice(val device: ServerDevice) : ScanResult
}

@HiltViewModel
class ClientViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
) : ViewModel() {

    private val _isScanningState = MutableStateFlow(false)
    val isScanningState = _isScanningState.asStateFlow()
    private val _scanResult = MutableStateFlow<ScanResult>(ScanResult.Initial)
    val scanResult = _scanResult.asStateFlow()
    private val _service = MutableStateFlow<ClientBleGattService?>(null)
    val service = _service.asStateFlow()
    private val _characteristicValue = MutableStateFlow<Int?>(null)
    val characteristicValue = _characteristicValue.asStateFlow()
    private val _descriptorValue = MutableStateFlow<Int?>(null)
    val descriptorValue = _descriptorValue.asStateFlow()

    fun toggleScanning() {
        TODO()
    }

    fun discoverService() {
        TODO()
    }

    fun readCharacteristic() {
        TODO()
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    fun writeCharacteristic(value: Int) {
        TODO()
    }

    fun readDescriptor() {
        TODO()
    }

    fun writeDescriptor(value: Int) {
        TODO()
    }
}