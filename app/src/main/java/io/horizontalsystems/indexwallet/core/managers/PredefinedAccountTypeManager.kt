package io.horizontalsystems.indexwallet.core.managers

import io.horizontalsystems.indexwallet.core.IAccountCreator
import io.horizontalsystems.indexwallet.core.IAccountManager
import io.horizontalsystems.indexwallet.core.IPredefinedAccountTypeManager
import io.horizontalsystems.indexwallet.entities.Account
import io.horizontalsystems.indexwallet.entities.AccountType
import io.horizontalsystems.indexwallet.entities.PredefinedAccountType

class PredefinedAccountTypeManager(private val accountManager: IAccountManager, private val accountCreator: IAccountCreator)
    : IPredefinedAccountTypeManager {

    override val allTypes: List<PredefinedAccountType>
        get() = listOf(PredefinedAccountType.Standard, PredefinedAccountType.Binance, PredefinedAccountType.Eos)

    override fun account(predefinedAccountType: PredefinedAccountType): Account? {
        return accountManager.accounts.find { predefinedAccountType.supports(it.type) }
    }

    override fun predefinedAccountType(type: AccountType): PredefinedAccountType? {
        return allTypes.firstOrNull { it.supports(type) }
    }
}
