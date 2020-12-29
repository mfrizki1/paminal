package id.calocallo.sicape.ui.main.lp.pasal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.LpPasalResp
import id.calocallo.sicape.network.response.LpResp
import id.calocallo.sicape.ui.main.lp.EditLpActivity
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_pick_pasal_lp_edit.*
import kotlinx.android.synthetic.main.layout_edit_1_text.view.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback

class PickPasalLpEditActivity : BaseActivity() {
    private val listPasal = arrayListOf(
        LpPasalResp(1, "Pasal 1", "", "", ""),
        LpPasalResp(2, "Pasal 2", "", "", ""),
        LpPasalResp(3, "Pasal 3", "", "", "")
    )
    private lateinit var sessionManager: SessionManager
    private lateinit var adapterPasalEdit: ReusableAdapter<LpPasalResp>
    private lateinit var callbackPasalEdit: AdapterCallback<LpPasalResp>
    private var jenisPelanggaran: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_pasal_lp_edit)
        sessionManager = SessionManager(this)
        adapterPasalEdit = ReusableAdapter(this)
        setupActionBarWithBackButton(toolbar)
        val detailLp = intent.extras?.getParcelable<LpResp>(EditLpActivity.EDIT_LP)
        jenisPelanggaran = detailLp?.jenis

        when (detailLp?.jenis) {
            "pidana" -> supportActionBar?.title = "Edit Data Laporan Pidana"
            "disiplin" -> supportActionBar?.title = "Edit Data Laporan Disiplin"
            "kode_etik" -> supportActionBar?.title = "Edit Data Laporan Kode Etik"
        }

        getPasalEdit()
        btn_add_single_pasal_edit.setOnClickListener {
            val intent = Intent(this, AddPasalLpActivity::class.java)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            startActivity(intent)
        }

    }

    private fun getPasalEdit() {
        callbackPasalEdit = object : AdapterCallback<LpPasalResp> {
            override fun initComponent(itemView: View, data: LpPasalResp, itemIndex: Int) {
                itemView.txt_edit_pendidikan.text = data.nama_pasal
            }

            override fun onItemClicked(itemView: View, data: LpPasalResp, itemIndex: Int) {
                val intent = Intent(this@PickPasalLpEditActivity, EditPasalLpActivity::class.java)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                intent.putExtra("NAMA_JENIS", jenisPelanggaran)
                intent.putExtra("DETAIL_EDIT_PASAL", data)
                startActivity(intent)

            }
        }
        adapterPasalEdit.adapterCallback(callbackPasalEdit)
            .isVerticalView()
            .addData(listPasal)
            .setLayout(R.layout.layout_edit_1_text)
            .build(rv_list_pasal_edit)
    }

    override fun onResume() {
        super.onResume()
        getPasalEdit()
    }
}