package yandex.practicum.workshop.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.hilt.migration.DisableInstallInCheck
import yandex.practicum.workshop.data.UserRepository
import javax.inject.Singleton


@Module(
    subcomponents = [ProfileComponent::class]
)
@DisableInstallInCheck
object SubcomponentsModule

@Component(modules = [SubcomponentsModule::class, UserPrefsModule::class, UserModule::class])
@Singleton
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }

    fun profileComponentFactory(): ProfileComponent.Factory
    fun userRepository(): UserRepository
}