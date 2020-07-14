package io.horizontalsystems.indexwallet.core.factories

import io.horizontalsystems.indexwallet.core.IAccountFactory
import io.horizontalsystems.indexwallet.entities.Account
import io.horizontalsystems.indexwallet.entities.AccountOrigin
import io.horizontalsystems.indexwallet.entities.AccountType
import java.util.*

class AccountFactory : IAccountFactory {

    override fun account(type: AccountType, origin: AccountOrigin, backedUp: Boolean): Account {
        val id = UUID.randomUUID().toString()

        return Account(
                id = id,
                name = id,
                type = type,
                origin = origin,
                isBackedUp = backedUp
        )
    }
}
