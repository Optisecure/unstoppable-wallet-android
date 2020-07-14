package io.horizontalsystems.indexwallet.modules.ratelist

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.horizontalsystems.indexwallet.R
import io.horizontalsystems.views.inflate

class SourceAdapter : RecyclerView.Adapter<SourceAdapter.ViewHolderSource>() {

    override fun getItemCount() = 1

    override fun onBindViewHolder(holder: ViewHolderSource, position: Int) = Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderSource {
        return ViewHolderSource(inflate(parent, R.layout.view_holder_coin_list_source, false))
    }

    class ViewHolderSource(containerView: View) : RecyclerView.ViewHolder(containerView)

}
