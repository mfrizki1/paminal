package id.calocallo.sicape.ui.main.lp.pasal

import androidx.recyclerview.selection.ItemKeyProvider
import id.calocallo.sicape.network.response.LpPasalResp

class PasalItemKeyProvider(private val adapter: PasalAdapter1) :
    ItemKeyProvider<LpPasalResp>(SCOPE_CACHED) {
    override fun getKey(position: Int): LpPasalResp? {
        return adapter.getItem(position)
    }

    override fun getPosition(key: LpPasalResp): Int {
        return adapter.getPosition(key.nama_pasal!!)
    }
}