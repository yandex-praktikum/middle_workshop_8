package yandex.practicum.workshop.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.migration.DisableInstallInCheck
import yandex.practicum.workshop.ui.LoginViewModel

@Module
@DisableInstallInCheck
abstract class LoginModule {

    @Binds
    abstract fun bindsViewModel(viewModel: LoginViewModel): ViewModel
}

