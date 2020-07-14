package io.horizontalsystems.indexwallet.core.managers

import io.horizontalsystems.indexwallet.core.App
import io.horizontalsystems.indexwallet.core.IEthereumKitManager
import io.horizontalsystems.indexwallet.core.UnsupportedAccountException
import io.horizontalsystems.indexwallet.entities.AccountType
import io.horizontalsystems.indexwallet.entities.CommunicationMode
import io.horizontalsystems.indexwallet.entities.Wallet
import io.horizontalsystems.ethereumkit.core.EthereumKit
import io.horizontalsystems.ethereumkit.core.EthereumKit.*

class EthereumKitManager(
        private val infuraProjectId: String,
        private val infuraSecretKey: String,
        private val etherscanApiKey: String,
        private val testMode: Boolean
) : IEthereumKitManager {

    private var kit: EthereumKit? = null
    private var useCount = 0

    override val ethereumKit: EthereumKit?
        get() = kit

    override val statusInfo: Map<String, Any>?
        get() = ethereumKit?.statusInfo()

    override fun ethereumKit(wallet: Wallet, communicationMode: CommunicationMode?): EthereumKit {
        val account = wallet.account
        val accountType = account.type
        if (accountType is AccountType.Mnemonic && accountType.words.size == 12) {
            useCount += 1

            kit?.let { return it }
            val syncMode = WordsSyncMode.ApiSyncMode()
            val networkType = if (testMode) NetworkType.Ropsten else NetworkType.MainNet
            val rpcApi = when (communicationMode) {
                CommunicationMode.Infura -> RpcApi.Infura(InfuraCredentials(infuraProjectId, infuraSecretKey))
                CommunicationMode.Incubed -> RpcApi.Incubed()
                else -> throw Exception("Invalid communication mode for Ethereum: ${communicationMode?.value}")
            }
            kit = EthereumKit.getInstance(App.instance, accountType.words, syncMode, networkType, rpcApi, etherscanApiKey, account.id)
            kit?.start()

            return kit!!
        }

        throw UnsupportedAccountException()

    }

    override fun unlink() {
        useCount -= 1

        if (useCount < 1) {
            kit?.stop()
            kit = null
        }
    }
}
