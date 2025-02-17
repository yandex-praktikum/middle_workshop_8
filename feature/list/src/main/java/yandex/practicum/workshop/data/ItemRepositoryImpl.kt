package yandex.practicum.workshop.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.math.min

class ItemRepositoryImpl @Inject constructor() : ItemRepository {
    private val _items = List(16) { Item(id = it, name = "Item $it") }

    override fun getItems(): Flow<List<Item>> = flow {
        emit(_items)
    }.flowOn(Dispatchers.IO)
}