package id.calocallo.sicape.ui.main.lp.pidana

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PersonelLapor
import id.calocallo.sicape.network.response.LpPasalResp
import id.calocallo.sicape.network.response.LpPidanaResp
import id.calocallo.sicape.network.response.SatKerResp
import id.calocallo.sicape.ui.main.lp.AddLpActivity
import id.calocallo.sicape.ui.main.lp.pidana.DetailLpPidanaActivity.Companion.DETAIL_PIDANA
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.gone
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_list_lp_pidana.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class ListLpPidanaActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var adapterListPidana: LpPidanaAdapter
    private var personelTerLapor = PersonelLapor()
    private var personelPeLapor = PersonelLapor()
    private var satKerResp = SatKerResp()

    //    private lateinit var adapterListPidana: ReusableAdapter<LpPidanaResp>
//    private lateinit var callbackListPidana: ReusableAdapter<LpPidanaResp>
    private var listPidana = arrayListOf<LpPidanaResp>()
    private var listPasal = arrayListOf<LpPasalResp>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_lp_pidana)
        sessionManager = SessionManager(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "List Data Laporan Polisi Pidana"
        sessionManager.setJenisLP("pidana")

        val hak = sessionManager.fetchHakAkses()
        if (hak == "operator") {
            btn_add_lp_pidana.gone()
        }

        btn_add_lp_pidana.setOnClickListener {
            val intent = Intent(this, AddLpActivity::class.java)
            intent.putExtra("JENIS_LP", "pidana")
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        getList()
    }

    private fun getList() {
        satKerResp = SatKerResp(1, "POLDA", "ALAMAT", "081210812", "", "", "", "")
        personelTerLapor = PersonelLapor(1, "faisal", "bripda", "jabatan", "1234", "polda kalsel")
        personelPeLapor =
            PersonelLapor(2, "utuh", "ipda", "jabatan", "0987", "polresta banjarmasin")


        listPasal.add(LpPasalResp(1, "Pasal 1", "LOREM IPSUM DOLOR", "", "", ""))
        listPasal.add(LpPasalResp(2, "Pasal 2", "LOREM IPSUM DOLOR", "", "", ""))
        listPasal.add(LpPasalResp(3, "Pasal 3", "LOREM IPSUM DOLOR", "", "", ""))
        listPidana.add(
            LpPidanaResp(
                1, "LP/PIDANA/2020/BIDPROPAM",satKerResp, personelTerLapor, "Uraian Pelanggaran",
                "Banjarmasin", "12-12-2000",
                "Rojak Ahmad", "Kombes", "12345678",
                "Polair",  "polisi",
                "", "", "", "",
                "", "", "", personelPeLapor,
                resources.getString(R.string.paragraf), resources.getString(R.string.paragraf),
                listPasal, "","",""
            )
        )

        listPidana.add(
            LpPidanaResp(
                2, "LP/PIDANA2/2020/BIDPROPAM",satKerResp, personelTerLapor, "Uraian Pelanggaran",
                "Banjarmasin", "12-12-2000",
                "Rojak Ahmad", "Kombes", "12345678",
                "Polair",  "sipil",
                "sipil", "islam", "pekerjaan", "Indonesia",
                "jl xxx", "081212", "123456", null,
                resources.getString(R.string.paragraf), resources.getString(R.string.paragraf),
                listPasal, "","",""
            )
        )

        listPidana.add(
            LpPidanaResp(
                3, "LP/PIDANA3/2020/BIDPROPAM",satKerResp, personelTerLapor, "Uraian Pelanggaran",
                "Banjarmasin", "12-12-2000",
                "Rojak Ahmad", "Kombes", "12345678",
                "Polair",  "polisi",
                "", "", "", "",
                "", "", "", personelPeLapor,
                resources.getString(R.string.paragraf), resources.getString(R.string.paragraf),
                listPasal, "","",""
            )
        )

        rv_list_pidana.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapterListPidana =
            LpPidanaAdapter(this, listPidana, object : LpPidanaAdapter.OnClickLpPidana {
                override fun onClick(position: Int) {
                    val intent =
                        Intent(this@ListLpPidanaActivity, DetailLpPidanaActivity::class.java)
                    intent.putExtra(DETAIL_PIDANA, listPidana[position])
                    startActivity(intent)
                }
            })
        rv_list_pidana.adapter = adapterListPidana
    }
}