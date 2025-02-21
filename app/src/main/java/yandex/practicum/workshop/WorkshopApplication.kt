package yandex.practicum.workshop

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import yandex.practicum.workshop.di.AppComponent
import yandex.practicum.workshop.di.DaggerAppComponent

@HiltAndroidApp
class WorkshopApplication : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}