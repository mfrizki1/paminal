package id.calocallo.sicape.ui.main.pelanggaran

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PersonelModel
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.PelanggaranResp
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_pick_pelanggaran.*
import kotlinx.android.synthetic.main.layout_1_text_clickable.view.*
import kotlinx.android.synthetic.main.layout_edit_1_text.view.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback

class PickPelanggaranActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var adapterPelanggaran: ReusableAdapter<PelanggaranResp>
    private lateinit var callbackPelanggaran: AdapterCallback<PelanggaranResp>
    private val listPelanggaran = listOf(
        PelanggaranResp(1, "Pencabulan", "", "", ""),
        PelanggaranResp(2, "Lalai", "", "", ""),
        PelanggaranResp(3, "Obat-obatan", "", "", "")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_pelanggaran)
        sessionManager = SessionManager(this)
        adapterPelanggaran = ReusableAdapter(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Jenis Pelanggaran"

        val hak = sessionManager.fetchHakAkses()
        if (hak == "operator") {
            btn_add_single_pelanggaran.gone()
        }

        btn_add_single_pelanggaran.setOnClickListener {

        }

        getPelanggaran()
    }

    private fun getPelanggaran() {
//        NetworkConfig().getService()
        callbackPelanggaran = object :AdapterCallback<PelanggaranResp>{
            override fun initComponent(itemView: View, data: PelanggaranResp, itemIndex: Int) {
                itemView.txt_1_clickable.text = data.nama_pelanggaran
            }

            override fun onItemClicked(itemView: View, data: PelanggaranResp, itemIndex: Int) {
                itemView.img_clickable.visible()
                val intent = Intent()
                intent.putExtra("PELANGGARAN",data)
                setResult(RESULT_OK, intent)
                finish()
            }
        }
        adapterPelanggaran.adapterCallback(callbackPelanggaran)
            .isVerticalView()
            .addData(listPelanggaran)
            .setLayout(R.layout.layout_1_text_clickable)
            .build(rv_list_pelanggaran)
    }
}