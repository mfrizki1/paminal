package id.calocallo.sicape.ui.main.lp.pasal

import androidx.recyclerview.selection.ItemKeyProvider
import id.calocallo.sicape.network.response.PasalDilanggarResp

class PasalItemKeyProvider(private val adapter: PasalAdapter1) :
    ItemKeyProvider<PasalDilanggarResp>(SCOPE_CACHED) {
    override fun getKey(position: Int): PasalDilanggarResp? {
        return adapter.getItem(position)
    }

    override fun getPosition(key: PasalDilanggarResp): Int {
        return adapter.getPosition(key.pasal?.nama_pasal!!)
    }
}