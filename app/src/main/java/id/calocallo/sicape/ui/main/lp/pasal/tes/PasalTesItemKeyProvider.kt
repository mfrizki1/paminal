package id.calocallo.sicape.ui.main.lp.pasal.tes

import androidx.recyclerview.selection.ItemKeyProvider
import id.calocallo.sicape.network.response.PasalResp
import id.calocallo.sicape.network.response.PersonelMinResp
import id.calocallo.sicape.ui.main.choose.multiple.PersonelMultipleAdapter
import id.calocallo.sicape.ui.main.lp.pasal.PasalMultipleAdapter

class PasalTesItemKeyProvider(private val adapter: PasalTesAdapter) :
    ItemKeyProvider<PasalResp>(SCOPE_CACHED) {
    override fun getKey(position: Int): PasalResp? {
        return adapter.getItem(position)
    }

    override fun getPosition(key: PasalResp): Int {
        return adapter.getPosition(key.nama_pasal!!)
    }
}