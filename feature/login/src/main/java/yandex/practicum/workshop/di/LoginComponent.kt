package yandex.practicum.workshop.di

import dagger.Subcomponent
import yandex.practicum.workshop.ui.LoginViewModel

@LoginScope
@Subcomponent(modules = [LoginModule::class])
interface LoginComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): LoginComponent
    }

    fun getViewModel(): LoginViewModel
}