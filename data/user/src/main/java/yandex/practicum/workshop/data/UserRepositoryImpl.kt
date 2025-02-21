package yandex.practicum.workshop.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor() : UserRepository {
    private var user: User? = null

    override fun getUser(): Flow<User?> = flow {
        emit(user)
    }.flowOn(Dispatchers.IO)

    override suspend fun saveUser(user: User) {
        this.user = user
    }
}