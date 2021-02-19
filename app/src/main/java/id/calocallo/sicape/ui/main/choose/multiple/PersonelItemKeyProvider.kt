package id.calocallo.sicape.ui.main.choose.multiple

import androidx.recyclerview.selection.ItemKeyProvider
import id.calocallo.sicape.model.AllPersonelModel
import id.calocallo.sicape.network.response.PersonelMinResp

class PersonelItemKeyProvider(private val adapter: PersonelMultipleAdapter) :
    ItemKeyProvider<PersonelMinResp>(SCOPE_CACHED) {
    override fun getKey(position: Int): PersonelMinResp? {
        return adapter.getItem(position)
    }

    override fun getPosition(key: PersonelMinResp): Int {
        return adapter.getPosition(key.nama!!)
    }
}