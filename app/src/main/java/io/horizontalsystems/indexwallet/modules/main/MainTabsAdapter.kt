package io.horizontalsystems.indexwallet.modules.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import io.horizontalsystems.indexwallet.modules.balance.BalanceFragment
import io.horizontalsystems.indexwallet.modules.guides.GuidesFragment
import io.horizontalsystems.indexwallet.modules.settings.main.MainSettingsFragment
import io.horizontalsystems.indexwallet.modules.transactions.TransactionsFragment

class MainTabsAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    var currentItem = 0

    override fun getCount(): Int = 4

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> BalanceFragment()
        1 -> TransactionsFragment()
        2 -> GuidesFragment()
        else -> MainSettingsFragment()
    }

}
