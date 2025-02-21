package yandex.practicum.workshop.domain

import yandex.practicum.workshop.data.User
import yandex.practicum.workshop.data.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val repository: UserRepository) {
    operator fun invoke(): Flow<User?> = repository.getUser()
}