package yandex.practicum.workshop.user

import javax.inject.Inject

class AnalyticsManagerImpl @Inject constructor() : AnalyticsManager {
    override fun logScreenEnter(name: String) = Unit
}