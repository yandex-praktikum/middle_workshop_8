package yandex.practicum.workshop.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import yandex.practicum.workshop.WorkshopApplication
import yandex.practicum.workshop.data.UserRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DaggerHiltModule {

    @Singleton
    @Provides
    fun provideRepository(application: Application): UserRepository {
        return (application as WorkshopApplication).appComponent.userRepository()
    }
}