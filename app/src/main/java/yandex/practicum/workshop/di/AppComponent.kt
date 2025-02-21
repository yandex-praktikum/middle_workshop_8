package yandex.practicum.workshop.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.hilt.migration.DisableInstallInCheck
import yandex.practicum.workshop.user.di.AnalyticsModule
import javax.inject.Singleton


@Module(
    subcomponents = [ProfileComponent::class, LoginComponent::class]
)
@DisableInstallInCheck
object SubcomponentsModule

@Component(modules = [SubcomponentsModule::class, AnalyticsModule::class])
@Singleton
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }

    fun profileComponentFactory(): ProfileComponent.Factory
    fun loginComponentFactory(): LoginComponent.Factory
}