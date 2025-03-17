package yandex.practicum.middle.android.mock

import android.bluetooth.BluetoothGattService
import no.nordicsemi.android.kotlin.ble.core.MockServerDevice
import no.nordicsemi.android.kotlin.ble.core.data.BleGattPermission
import no.nordicsemi.android.kotlin.ble.core.data.BleGattProperty
import no.nordicsemi.android.kotlin.ble.core.data.util.DataByteArray
import no.nordicsemi.android.kotlin.ble.core.wrapper.MockBluetoothGattCharacteristic
import no.nordicsemi.android.kotlin.ble.core.wrapper.MockBluetoothGattDescriptor
import no.nordicsemi.android.kotlin.ble.core.wrapper.MockBluetoothGattService
import no.nordicsemi.android.kotlin.ble.mock.MockEngine
import no.nordicsemi.android.kotlin.ble.server.mock.MockServerAPI
import java.util.UUID
import javax.inject.Inject

class MockServer @Inject constructor() {
    private val characteristic = MockBluetoothGattCharacteristic(
        uuid = UUID.randomUUID(),
        properties = BleGattProperty.toInt(
            listOf(
                BleGattProperty.PROPERTY_READ,
                BleGattProperty.PROPERTY_WRITE,
            )
        ),
        permissions = BleGattPermission.toInt(
            listOf(
                BleGattPermission.PERMISSION_READ,
                BleGattPermission.PERMISSION_WRITE
            )
        ),
        value = DataByteArray(byteArrayOf())
    )

    private val descriptor = MockBluetoothGattDescriptor(
        uuid = UUID.randomUUID(),
        permissions = BleGattPermission.toInt(
            listOf(
                BleGattPermission.PERMISSION_READ,
                BleGattPermission.PERMISSION_WRITE
            )
        ),
        characteristic = characteristic,
        value = DataByteArray(byteArrayOf())
    )

    private val service = MockBluetoothGattService(
        uuid = UUID.randomUUID(),
        type = BluetoothGattService.SERVICE_TYPE_PRIMARY,
        _characteristics = listOf(characteristic)
    )

    val device = MockServerDevice()

    val api = MockServerAPI(MockEngine, device, 100)

    init {
        characteristic.addDescriptor(descriptor)
        MockEngine.registerServer(api, device, listOf(service))
    }
}