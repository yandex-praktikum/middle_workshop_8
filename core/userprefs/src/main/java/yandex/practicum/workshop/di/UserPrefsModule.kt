package yandex.practicum.workshop.di

import dagger.Binds
import dagger.Module
import yandex.practicum.workshop.UserPrefsManager
import yandex.practicum.workshop.UserPrefsManagerImpl
import javax.inject.Singleton

@Module
abstract class UserPrefsModule {

    @Binds
    @Singleton
    abstract fun bindsManager(userPrefsManagerImpl: UserPrefsManagerImpl): UserPrefsManager
}