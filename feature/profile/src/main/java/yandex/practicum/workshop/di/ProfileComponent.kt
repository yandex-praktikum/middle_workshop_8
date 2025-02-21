package yandex.practicum.workshop.di

import dagger.Subcomponent
import yandex.practicum.workshop.ui.ProfileViewModel

@ProfileScope
@Subcomponent(modules = [ProfileModule::class, UserModule::class])
interface ProfileComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): ProfileComponent
    }

    fun getViewModel(): ProfileViewModel
}