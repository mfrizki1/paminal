package id.calocallo.sicape.ui.main.lp.pasal.tes

import androidx.recyclerview.selection.ItemKeyProvider
import com.zanyastudios.test.PasalItem


class PasalTesItemKeyProvider(private val tesAdapter: PasalTesAdapter) :
    ItemKeyProvider<PasalItem>(SCOPE_CACHED) {
    override fun getKey(position: Int): PasalItem? {
        return tesAdapter.getItem(position)
    }

    override fun getPosition(key: PasalItem): Int {
        return tesAdapter.getPosition(key.nama_pasal!!)
    }
}