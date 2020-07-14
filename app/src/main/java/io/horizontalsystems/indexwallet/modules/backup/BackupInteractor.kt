package io.horizontalsystems.indexwallet.modules.backup

import io.horizontalsystems.indexwallet.core.IBackupManager
import io.horizontalsystems.indexwallet.core.IPredefinedAccountTypeManager
import io.horizontalsystems.indexwallet.entities.AccountType
import io.horizontalsystems.indexwallet.entities.PredefinedAccountType
import io.horizontalsystems.core.IPinComponent

class BackupInteractor(
        private val backupManager: IBackupManager,
        private val pinComponent: IPinComponent,
        private val predefinedAccountTypeManager: IPredefinedAccountTypeManager
)
    : BackupModule.Interactor {

    var delegate: BackupModule.InteractorDelegate? = null

    override val isPinSet: Boolean
        get() = pinComponent.isPinSet

    override fun setBackedUp(accountId: String) {
        backupManager.setIsBackedUp(accountId)
    }

    override fun predefinedAccountType(accountType: AccountType): PredefinedAccountType? {
        return predefinedAccountTypeManager.predefinedAccountType(accountType)
    }
}
