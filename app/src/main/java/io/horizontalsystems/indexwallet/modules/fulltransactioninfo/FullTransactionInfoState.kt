package io.horizontalsystems.indexwallet.modules.fulltransactioninfo

import io.horizontalsystems.indexwallet.entities.FullTransactionRecord
import io.horizontalsystems.indexwallet.entities.Wallet

class FullTransactionInfoState(override val wallet: Wallet, override val transactionHash: String)
    : FullTransactionInfoModule.State {

    override var transactionRecord: FullTransactionRecord? = null
}
