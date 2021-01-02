package id.calocallo.sicape.ui.main.lp.pasal.tes

import androidx.recyclerview.selection.ItemKeyProvider
import com.zanyastudios.test.PasalItem


class PasalItemKeyProvider(private val adapter: PasalAdapter) :
    ItemKeyProvider<PasalItem>(SCOPE_CACHED) {
    override fun getKey(position: Int): PasalItem? {
        return adapter.getItem(position)
    }

    override fun getPosition(key: PasalItem): Int {
        return adapter.getPosition(key.nama_pasal!!)
    }
}