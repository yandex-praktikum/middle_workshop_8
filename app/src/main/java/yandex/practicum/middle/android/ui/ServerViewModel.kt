package yandex.practicum.middle.android.ui

import android.bluetooth.BluetoothGattService
import android.content.Context
import android.os.ParcelUuid
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.job
import no.nordicsemi.android.kotlin.ble.advertiser.BleAdvertiser
import no.nordicsemi.android.kotlin.ble.core.MockServerDevice
import no.nordicsemi.android.kotlin.ble.core.advertiser.BleAdvertisingConfig
import no.nordicsemi.android.kotlin.ble.core.advertiser.BleAdvertisingData
import no.nordicsemi.android.kotlin.ble.core.data.BleGattConsts
import no.nordicsemi.android.kotlin.ble.core.data.BleGattPermission
import no.nordicsemi.android.kotlin.ble.core.data.BleGattProperty
import no.nordicsemi.android.kotlin.ble.core.data.util.DataByteArray
import no.nordicsemi.android.kotlin.ble.core.data.util.IntFormat
import no.nordicsemi.android.kotlin.ble.core.wrapper.MockBluetoothGattCharacteristic
import no.nordicsemi.android.kotlin.ble.core.wrapper.MockBluetoothGattDescriptor
import no.nordicsemi.android.kotlin.ble.core.wrapper.MockBluetoothGattService
import no.nordicsemi.android.kotlin.ble.mock.MockEngine
import no.nordicsemi.android.kotlin.ble.server.api.ServerGattEvent
import no.nordicsemi.android.kotlin.ble.server.mock.MockServerAPI
import java.util.UUID
import javax.inject.Inject


@HiltViewModel
class ServerViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val server: MockServer
) : ViewModel() {

    private val advertiser by lazy { BleAdvertiser.create(context) }
    private val advertisingScope = CoroutineScope(SupervisorJob(viewModelScope.coroutineContext.job))
    private var characteristicValue = 0
    private var descriptorValue = 0

    private val _isAdvertisingState = MutableStateFlow(false)
    val isAdvertisingState = _isAdvertisingState.asStateFlow()

    init {
        server.api.event.onEach {
            when (it) {
                is ServerGattEvent.ClientConnectionStateChanged -> {
                    MockEngine.connect(it.device)
                }

                is ServerGattEvent.CharacteristicReadRequest -> {
                    server.api.sendResponse(
                        it.device,
                        it.requestId,
                        0,
                        0,
                        DataByteArray.from(characteristicValue.toByte())
                    )
                }

                is ServerGattEvent.CharacteristicWriteRequest -> {
                    characteristicValue = it.value.getIntValue(IntFormat.FORMAT_UINT8, 0)!!
                    server.api.sendResponse(
                        it.device,
                        it.requestId,
                        0,
                        0,
                        null
                    )
                }

                is ServerGattEvent.DescriptorReadRequest -> {
                    server.api.sendResponse(
                        it.device,
                        it.requestId,
                        0,
                        0,
                        DataByteArray.from(descriptorValue.toByte())
                    )

                }

                is ServerGattEvent.DescriptorWriteRequest -> {
                    descriptorValue = it.value.getIntValue(IntFormat.FORMAT_UINT8, 0)!!
                    server.api.sendResponse(
                        it.device,
                        it.requestId,
                        0,
                        0,
                        DataByteArray.from(descriptorValue.toByte())
                    )
                }

                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    fun increaseCharacteristic() {
        characteristicValue = (characteristicValue + 1) and 0xFF
    }

    fun increaseDescriptor() {
        descriptorValue = (descriptorValue + 1) and 0xFF
    }

    fun toggleAdvertising() {
        if (isAdvertisingState.value) {
            advertisingScope.coroutineContext.cancelChildren()
            _isAdvertisingState.value = false
            return
        }

        val advertiserConfig = BleAdvertisingConfig(
            advertiseData = BleAdvertisingData(
                ParcelUuid(UUID.randomUUID())
            )
        )
        advertiser
            .advertise(advertiserConfig, MockServerDevice())
            .launchIn(advertisingScope)

        _isAdvertisingState.value = true
    }
}

class MockServer @Inject constructor() {
    val characteristic = MockBluetoothGattCharacteristic(
        uuid = UUID.randomUUID(),
        properties = BleGattProperty.toInt(
            listOf(
                BleGattProperty.PROPERTY_READ,
                BleGattProperty.PROPERTY_WRITE,
                BleGattProperty.PROPERTY_WRITE_NO_RESPONSE,
                BleGattProperty.PROPERTY_NOTIFY,
            )
        ),
        permissions = BleGattPermission.toInt(
            listOf(
                BleGattPermission.PERMISSION_READ,
                BleGattPermission.PERMISSION_WRITE
            )
        ),
        value = DataByteArray("value".toByteArray())
    )

    val descriptor = MockBluetoothGattDescriptor(
        uuid = BleGattConsts.NOTIFICATION_DESCRIPTOR,
//                uuid = UUID.randomUUID(),
        permissions = BleGattPermission.toInt(
            listOf(
                BleGattPermission.PERMISSION_READ,
                BleGattPermission.PERMISSION_WRITE
            )
        ),
        characteristic = characteristic,
        value = DataByteArray("value".toByteArray())
    )

    val service = MockBluetoothGattService(
        uuid = UUID.randomUUID(),
        type = BluetoothGattService.SERVICE_TYPE_PRIMARY,
        _characteristics = listOf(characteristic)
    )

    val device = MockServerDevice()

    val api = MockServerAPI(MockEngine, device, 100)

//    var clientDevice: ClientDevice = MockClientDevice()

    init {
        characteristic.addDescriptor(descriptor)
        MockEngine.registerServer(api, device, listOf(service))
    }
}
