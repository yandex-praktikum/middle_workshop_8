package yandex.practicum.middle.android.ui

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.timeout
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import no.nordicsemi.android.kotlin.ble.client.main.callback.ClientBleGatt
import no.nordicsemi.android.kotlin.ble.client.main.service.ClientBleGattService
import no.nordicsemi.android.kotlin.ble.core.ServerDevice
import no.nordicsemi.android.kotlin.ble.core.data.util.IntFormat
import no.nordicsemi.android.kotlin.ble.scanner.BleScanner
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

sealed interface ScanResult {
    data object Initial : ScanResult
    data object Timeout : ScanResult
    data class ScannedDevice(val device: ServerDevice) : ScanResult
}

@HiltViewModel
class ClientViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
) : ViewModel() {

    private val scanner by lazy { BleScanner(context) }
    private val scanningScope = CoroutineScope(SupervisorJob(viewModelScope.coroutineContext.job))
    private val characteristic by lazy { _service.value?.characteristics?.get(0) }

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

    @OptIn(FlowPreview::class)
    @RequiresPermission(allOf = [Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT])
    fun toggleScanning() {
        if (isScanningState.value) {
            stopScanning()
            return
        }

        scanner
            .scan()
            .timeout(5.seconds).catch {
                if (it is TimeoutCancellationException) {
                    _scanResult.value = ScanResult.Timeout
                    _isScanningState.value = false
                }
            }
            .onEach {
                _scanResult.value = ScanResult.ScannedDevice(it.device)
                stopScanning()
            }
            .launchIn(scanningScope)
        _isScanningState.value = true
    }

    private fun stopScanning() {
        scanningScope.coroutineContext.cancelChildren()
        _isScanningState.value = false
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    fun discoverService() {
        scanResult.value.let {
            if (it is ScanResult.ScannedDevice) {
                viewModelScope.launch {
                    val connection = ClientBleGatt.connect(context, it.device, viewModelScope)
                    val services = connection.discoverServices().services

                    if (services.isNotEmpty()) {
                        _service.value = services[0]
                    }
                }
            }
        }
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    fun readCharacteristic() {
        viewModelScope.launch {
            val data = characteristic?.read()
            _characteristicValue.value = data?.getIntValue(IntFormat.FORMAT_UINT8, 0)
        }
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