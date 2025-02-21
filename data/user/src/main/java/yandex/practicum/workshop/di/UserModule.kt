package yandex.practicum.workshop.di

import dagger.Binds
import dagger.Module
import yandex.practicum.workshop.data.UserRepository
import yandex.practicum.workshop.data.UserRepositoryImpl
import javax.inject.Singleton

@Module
//@InstallIn(ViewModelComponent::class)
abstract class UserModule {

    @Binds
//    @ViewModelScoped
    @Singleton
    abstract fun bindRepository(repositoryImpl: UserRepositoryImpl): UserRepository
}