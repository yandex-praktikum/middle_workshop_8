package yandex.practicum.workshop.domain

import yandex.practicum.workshop.data.User
import yandex.practicum.workshop.data.UserRepository
import javax.inject.Inject

class SaveUserUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(user: User) {
        userRepository.saveUser(user)
    }
}