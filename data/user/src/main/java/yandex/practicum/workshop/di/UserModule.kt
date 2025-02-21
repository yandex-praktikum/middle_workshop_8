package yandex.practicum.workshop.di

import dagger.Binds
import dagger.Module
import yandex.practicum.workshop.data.UserRepository
import yandex.practicum.workshop.data.UserRepositoryImpl

@Module
//@InstallIn(ViewModelComponent::class)
abstract class UserModule {

    @Binds
//    @ViewModelScoped
    abstract fun bindRepository(repositoryImpl: UserRepositoryImpl): UserRepository
}