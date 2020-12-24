package io.horizontalsystems.indexwallet.modules.launcher

import io.horizontalsystems.indexwallet.core.IAccountManager
import io.horizontalsystems.core.IKeyStoreManager
import io.horizontalsystems.core.IPinComponent
import io.horizontalsystems.core.ISystemInfoManager

class LaunchInteractor(
        private val accountManager: IAccountManager,
        private val pinComponent: IPinComponent,
        private val systemInfoManager: ISystemInfoManager,
        private val keyStoreManager: IKeyStoreManager)
    : LaunchModule.IInteractor {

    var delegate: LaunchModule.IInteractorDelegate? = null

    override val isPinNotSet: Boolean
        get() = !pinComponent.isPinSet

    override val isAccountsEmpty: Boolean
        get() = accountManager.isAccountsEmpty

    override val isSystemLockOff: Boolean
        get() = systemInfoManager.isSystemLockOff

    override val isKeyInvalidated: Boolean
        get() = keyStoreManager.isKeyInvalidated

    override val isUserNotAuthenticated: Boolean
        get() = keyStoreManager.isUserNotAuthenticated

}