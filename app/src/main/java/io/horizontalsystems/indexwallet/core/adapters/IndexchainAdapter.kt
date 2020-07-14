package io.horizontalsystems.indexwallet.core.adapters

import io.horizontalsystems.indexwallet.core.App
import io.horizontalsystems.indexwallet.core.ISendBitcoinAdapter
import io.horizontalsystems.indexwallet.core.UnsupportedAccountException
import io.horizontalsystems.indexwallet.entities.*
import io.horizontalsystems.bitcoincore.BitcoinCore
import io.horizontalsystems.bitcoincore.models.BalanceInfo
import io.horizontalsystems.bitcoincore.models.BlockInfo
import io.horizontalsystems.bitcoincore.models.TransactionInfo
import io.horizontalsystems.indexchainkit.IndexChainKit
import io.horizontalsystems.indexchainkit.IndexChainKit.NetworkType
import java.math.BigDecimal

class IndexchainAdapter(
        override val kit: IndexChainKit,
        derivation: AccountType.Derivation?,
        syncMode: SyncMode?
) : BitcoinBaseAdapter(kit, derivation, syncMode), IndexChainKit.Listener, ISendBitcoinAdapter {

    constructor(wallet: Wallet, derivation: AccountType.Derivation?, syncMode: SyncMode?, testMode: Boolean) : this(createKit(wallet, derivation, syncMode, testMode), derivation, syncMode)

    init {
        kit.listener = this
    }

    //
    // BitcoinBaseAdapter
    //

    override val satoshisInBitcoin: BigDecimal = BigDecimal.valueOf(Math.pow(10.0, decimal.toDouble()))

    override fun getReceiveAddressType(wallet: Wallet): String? {
        return derivation?.addressType()
    }

    //
    // IndexChainKit Listener
    //

    override fun onBalanceUpdate(balance: BalanceInfo) {
        balanceUpdatedSubject.onNext(Unit)
    }

    override fun onLastBlockInfoUpdate(blockInfo: BlockInfo) {
        lastBlockUpdatedSubject.onNext(Unit)
    }

    override fun onKitStateUpdate(state: BitcoinCore.KitState) {
        setState(state)
    }

    override fun onTransactionsUpdate(inserted: List<TransactionInfo>, updated: List<TransactionInfo>) {
        val records = mutableListOf<TransactionRecord>()

        for (info in inserted) {
            records.add(transactionRecord(info))
        }

        for (info in updated) {
            records.add(transactionRecord(info))
        }

        transactionRecordsSubject.onNext(records)
    }

    override fun onTransactionsDelete(hashes: List<String>) {
        // ignored for now
    }

    companion object {

        private fun getNetworkType(testMode: Boolean) =
                if (testMode) NetworkType.TestNet else NetworkType.MainNet

        private fun createKit(wallet: Wallet, derivation: AccountType.Derivation?, syncMode: SyncMode?, testMode: Boolean): IndexChainKit {
            val account = wallet.account
            val accountType = account.type
            if (accountType is AccountType.Mnemonic && accountType.words.size == 12) {
                return IndexChainKit(context = App.instance,
                        words = accountType.words,
                        walletId = account.id,
                        syncMode = getSyncMode(syncMode),
                        networkType = getNetworkType(testMode),
                        confirmationsThreshold = defaultConfirmationsThreshold,
                        bip = getBip(derivation))
            }

            throw UnsupportedAccountException()
        }

        fun clear(walletId: String, testMode: Boolean) {
            IndexChainKit.clear(App.instance, getNetworkType(testMode), walletId)
        }
    }
}
