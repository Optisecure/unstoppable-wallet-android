package io.horizontalsystems.indexwallet.modules.guides

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.horizontalsystems.indexwallet.core.App
import io.horizontalsystems.indexwallet.core.managers.GuidesManager

object GuidesModule {

    class Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {

            return GuidesViewModel(GuidesManager, App.connectivityManager) as T
        }
    }
}