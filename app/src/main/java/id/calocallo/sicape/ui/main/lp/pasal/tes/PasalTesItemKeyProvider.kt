package id.calocallo.sicape.ui.main.lp.pasal.tes

import androidx.recyclerview.selection.ItemKeyProvider
import id.calocallo.sicape.network.response.PasalResp


class PasalTesItemKeyProvider(private val tesAdapter: PasalTesAdapter) :
    ItemKeyProvider<PasalResp>(SCOPE_CACHED) {
    override fun getKey(position: Int): PasalResp? {
        return tesAdapter.getItem(position)
    }

    override fun getPosition(key: PasalResp): Int {
        return tesAdapter.getPosition(key.nama_pasal!!)
    }
}