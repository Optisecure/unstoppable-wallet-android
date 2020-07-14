package io.horizontalsystems.indexwallet.core.managers

import io.horizontalsystems.indexwallet.core.EosUnsupportedException
import io.horizontalsystems.indexwallet.core.IAccountCreator
import io.horizontalsystems.indexwallet.core.IAccountFactory
import io.horizontalsystems.indexwallet.core.IWordsManager
import io.horizontalsystems.indexwallet.entities.Account
import io.horizontalsystems.indexwallet.entities.AccountOrigin
import io.horizontalsystems.indexwallet.entities.AccountType
import io.horizontalsystems.indexwallet.entities.PredefinedAccountType

class AccountCreator(
        private val accountFactory: IAccountFactory,
        private val wordsManager: IWordsManager)
    : IAccountCreator {

    override fun newAccount(predefinedAccountType: PredefinedAccountType): Account {
        val accountType = accountType(predefinedAccountType)
        return accountFactory.account(accountType, AccountOrigin.Created, false)
    }

    override fun restoredAccount(accountType: AccountType): Account {
        return accountFactory.account(accountType, AccountOrigin.Restored, true)
    }

    private fun accountType(predefinedAccountType: PredefinedAccountType): AccountType {
        return when (predefinedAccountType) {
            is PredefinedAccountType.Standard -> createMnemonicAccountType(12)
            is PredefinedAccountType.Binance -> createMnemonicAccountType(24)
            is PredefinedAccountType.Eos -> throw EosUnsupportedException()
        }
    }

    private fun createMnemonicAccountType(wordsCount: Int): AccountType {
        val words = wordsManager.generateWords(wordsCount)
        return AccountType.Mnemonic(words, salt = null)
    }
}
