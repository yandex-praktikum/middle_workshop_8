package yandex.practicum.workshop.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import yandex.practicum.workshop.analytics.AnalyticsManager
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    analyticsManager: AnalyticsManager
) : ViewModel() {
    init {
        analyticsManager.logScreenEnter("profile")
    }
}