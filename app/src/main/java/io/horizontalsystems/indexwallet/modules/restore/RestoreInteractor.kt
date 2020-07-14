package io.horizontalsystems.indexwallet.modules.restore

import io.horizontalsystems.indexwallet.core.IAccountCreator
import io.horizontalsystems.indexwallet.core.IAccountManager
import io.horizontalsystems.indexwallet.core.IBlockchainSettingsManager
import io.horizontalsystems.indexwallet.core.IWalletManager
import io.horizontalsystems.indexwallet.entities.Account
import io.horizontalsystems.indexwallet.entities.AccountType
import io.horizontalsystems.indexwallet.entities.CoinType
import io.horizontalsystems.indexwallet.entities.Wallet

class RestoreInteractor(
        private val accountCreator: IAccountCreator,
        private val accountManager: IAccountManager,
        private val walletManager: IWalletManager,
        private val blockchainSettingsManager: IBlockchainSettingsManager
) : RestoreModule.IInteractor {


    override fun createAccounts(accounts: List<Account>) {
        accounts.forEach {
            accountManager.save(it)
        }
    }

    override fun saveWallets(wallets: List<Wallet>) {
        walletManager.save(wallets)
    }

    override fun initializeSettings(coinType: CoinType) {
        blockchainSettingsManager.initializeSettings(coinType)
    }

    @Throws
    override fun account(accountType: AccountType): Account {
        return accountCreator.restoredAccount(accountType)
    }

    override fun create(account: Account) {
        accountManager.save(account)
    }

}
