package yandex.practicum.workshop.user.di

import dagger.Binds
import dagger.Module
import yandex.practicum.workshop.user.AnalyticsManager
import yandex.practicum.workshop.user.AnalyticsManagerImpl

@Module
abstract class AnalyticsModule {

    @Binds
    abstract fun bindsAnalyticsManager(analyticsManagerImpl: AnalyticsManagerImpl): AnalyticsManager
}