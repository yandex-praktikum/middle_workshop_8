package yandex.practicum.workshop.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.migration.DisableInstallInCheck
import yandex.practicum.workshop.ui.ProfileViewModel

@Module
@DisableInstallInCheck
abstract class ProfileModule {

    @Binds
    abstract fun bindsViewModel(viewModel: ProfileViewModel): ViewModel
}

