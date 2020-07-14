package io.horizontalsystems.indexwallet.core.managers

import io.horizontalsystems.indexwallet.core.ILocalStorage
import io.horizontalsystems.core.entities.AppVersion
import io.horizontalsystems.core.ISystemInfoManager
import java.util.*

class AppVersionManager(
        private val systemInfoManager: ISystemInfoManager,
        private val localStorage: ILocalStorage
) : BackgroundManager.Listener {

    override fun willEnterForeground() {
        val versions = localStorage.appVersions.toMutableList()
        val lastVersion = versions.lastOrNull()

        if (lastVersion == null || lastVersion.version != systemInfoManager.appVersion) {
            versions.add(AppVersion(systemInfoManager.appVersion, Date().time))
            localStorage.appVersions = versions
        }
    }

}
