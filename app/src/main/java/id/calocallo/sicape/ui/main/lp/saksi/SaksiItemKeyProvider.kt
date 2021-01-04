package id.calocallo.sicape.ui.main.lp.saksi

import androidx.recyclerview.selection.ItemKeyProvider
import id.calocallo.sicape.network.response.LpSaksiResp

class SaksiItemKeyProvider(private val adapter: SaksiLpAdapter):
ItemKeyProvider<LpSaksiResp>(SCOPE_CACHED){
    override fun getKey(position: Int): LpSaksiResp? {
        return adapter.getItem(position)
    }

    override fun getPosition(key: LpSaksiResp): Int {
        return adapter.getPosition(key.nama!!)
    }
}