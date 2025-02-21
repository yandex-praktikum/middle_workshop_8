package yandex.practicum.workshop.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import yandex.practicum.workshop.data.User
import yandex.practicum.workshop.domain.GetUserUseCase
import yandex.practicum.workshop.user.AnalyticsManager
import javax.inject.Inject

//@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    analyticsManager: AnalyticsManager
) : ViewModel() {
    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    init {
        analyticsManager.logScreenEnter("profile")

        viewModelScope.launch {
            getUserUseCase().collect {
                _user.value = it
            }
        }
    }
}