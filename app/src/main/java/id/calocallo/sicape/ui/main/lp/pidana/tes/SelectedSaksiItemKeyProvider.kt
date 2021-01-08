package id.calocallo.sicape.ui.main.lp.pidana.tes

import androidx.recyclerview.selection.ItemKeyProvider
import id.calocallo.sicape.network.response.LpSaksiResp


class SelectedSaksiItemKeyProvider(private val tesAdapter: SelectedSaksiAdapter) :
    ItemKeyProvider<LpSaksiResp>(SCOPE_CACHED) {
    override fun getKey(position: Int): LpSaksiResp? {
        return tesAdapter.getItem(position)
    }

    override fun getPosition(key: LpSaksiResp): Int {
        return tesAdapter.getPosition(key.nama!!)
    }
}