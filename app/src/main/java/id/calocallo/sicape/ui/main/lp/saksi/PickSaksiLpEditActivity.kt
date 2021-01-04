package id.calocallo.sicape.ui.main.lp.saksi

import android.content.Intent
import android.os.Bundle
import android.view.View
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.LpSaksiResp
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_pick_saksi_lp_edit.*
import kotlinx.android.synthetic.main.layout_edit_1_text.view.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback

class PickSaksiLpEditActivity : BaseActivity() {
    companion object {
        const val EDIT_SAKSI_KKE = "EDIT_SAKSI_KKE"
    }

    private val listSaksi = arrayListOf(
        LpSaksiResp(1, "Utuh", "bjm", "12-12-2000", "polisi", "jl xxx", "korban", "", "", ""),
        LpSaksiResp(2, "Galuh", "bjm", "20-02-2002", "konfing", "jl avx","saksi","","",""),
        LpSaksiResp(3, "Dulak", "bjb", "20-02-2002", "cafe", "jl 123","saksi","","","")
    )
    private lateinit var sessionManager: SessionManager
    private lateinit var adapterSaksiEdit: ReusableAdapter<LpSaksiResp>
    private lateinit var callbackSaksiEdit: AdapterCallback<LpSaksiResp>
    private var jenisPelanggaran: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_saksi_lp_edit)
        sessionManager = SessionManager(this)
        adapterSaksiEdit = ReusableAdapter(this)
//        val detailLp = intent.extras?.getParcelable<LpResp>(EditLpActivity.EDIT_LP)
//        jenisPelanggaran = detailLp?.jenis
        jenisPelanggaran =sessionManager.getJenisLP()
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Edit Data Laporan Kode Etik"
//        when (detailLp?.jenis) {
//            "pidana" -> supportActionBar?.title = "Edit Data Laporan Pidana"
//            "disiplin" -> supportActionBar?.title = "Edit Data Laporan Disiplin"
//            "kode_etik" -> supportActionBar?.title = "Edit Data Laporan Kode Etik"


        getSaksiEdit()

        btn_add_single_saksi_edit.setOnClickListener {
            val intent = Intent(this, AddSaksiLpActivity::class.java)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            startActivity(intent)
        }

    }

    private fun getSaksiEdit() {
//        NetworkConfig().getService()
        callbackSaksiEdit = object : AdapterCallback<LpSaksiResp> {
            override fun initComponent(itemView: View, data: LpSaksiResp, itemIndex: Int) {
                itemView.txt_edit_pendidikan.text = data.nama
            }

            override fun onItemClicked(itemView: View, data: LpSaksiResp, itemIndex: Int) {
                val intent = Intent(this@PickSaksiLpEditActivity, EditSaksiLpActivity::class.java)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                intent.putExtra("NAMA_JENIS", jenisPelanggaran)
                intent.putExtra("SAKSI_EDIT", data)
                startActivity(intent)
            }
        }
        adapterSaksiEdit.adapterCallback(callbackSaksiEdit)
            .isVerticalView().addData(listSaksi).setLayout(R.layout.layout_edit_1_text)
            .build(rv_list_saksi_edit)
    }

}