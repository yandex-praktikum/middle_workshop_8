package yandex.practicum.workshop.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import yandex.practicum.workshop.ui.ProfileViewModel

@Module
abstract class ProfileModule {

    @Binds
    abstract fun bindsViewModel(viewModel: ProfileViewModel): ViewModel
}

