package yandex.practicum.workshop.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import yandex.practicum.workshop.UserPrefsManager
import yandex.practicum.workshop.UserPrefsManagerImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UserPrefsModule {

    @Binds
    @Singleton
    abstract fun bindsManager(userPrefsManagerImpl: UserPrefsManagerImpl): UserPrefsManager
}