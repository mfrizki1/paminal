package id.calocallo.sicape.ui.main.lp.kke

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.LpKkeResp
import id.calocallo.sicape.network.response.LpPasalResp
import id.calocallo.sicape.network.response.LpSaksiResp
import id.calocallo.sicape.ui.main.lp.AddLpActivity
import id.calocallo.sicape.ui.main.lp.kke.DetailLpKkeActivity.Companion.DETAIL_KKE
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.gone
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_list_lp_kode_etik.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class ListLpKodeEtikActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var adapterLpKKe: LpKkeAdapter
    private var listKke = arrayListOf<LpKkeResp>()
    private var listPasal = arrayListOf<LpPasalResp>()
    private var listSaksi = arrayListOf<LpSaksiResp>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_lp_kode_etik)
        setupActionBarWithBackButton(toolbar)
        sessionManager = SessionManager(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "List Data Laporan Polisi Kode Etik"
        sessionManager.setJenisLP("kode_etik")
        val hak = sessionManager.fetchHakAkses()
        if (hak == "operator") {
            btn_add_lp_kke.gone()
        }

        btn_add_lp_kke.setOnClickListener {
            val intent = Intent(this, AddLpActivity::class.java)
            intent.putExtra("JENIS_LP", "kode_etik")
            startActivity(intent)
        }
        getListKke()

    }

    private fun getListKke() {
        listPasal.add(LpPasalResp(1, "Pasal 1", "LOREM IPSUM DOLOR", "", "", ""))
        listPasal.add(LpPasalResp(2, "Pasal 2", "LOREM IPSUM DOLOR", "", "", ""))
        listPasal.add(LpPasalResp(3, "Pasal 3", "LOREM IPSUM DOLOR", "", "", ""))

        listSaksi.add(LpSaksiResp(1, "Galuh", "korban", "","","","","","",""))
        listSaksi.add(LpSaksiResp(2, "Akbar", "saksi", "","","","","","",""))
        listSaksi.add(LpSaksiResp(3, "Wahyu", "saksi", "","","","","","",""))
        listKke.add(
            LpKkeResp(
                1, "LP/KKE1/2019/BIDPROPAM", "kode_etik", 4,
                5, "Banjarbaru", "12-12-2000", "Budi",
                "IPDA", "9090", "KOMBES",
                sessionManager.fetchUser()?.id, "Alat Bukti\nbaju\nsenjata", "isi Laporan",
                listPasal, listSaksi
            )
        )

        listKke.add(
            LpKkeResp(
                2, "LP/KKE2/2019/BIDPROPAM", "kode_etik", 4,
                5, "Banjarbaru", "12-12-2000", "Budi",
                "IPDA", "9090", "KOMBES",
                sessionManager.fetchUser()?.id, "Alat Bukti\nbaju\nsenjata", "isi Laporan",
                listPasal, listSaksi
            )
        )
        listKke.add(
            LpKkeResp(
                3, "LP/KKE2/2019/BIDPROPAM", "kode_etik", 4,
                5, "Banjarbaru", "12-12-2000", "Budi",
                "IPDA", "9090", "KOMBES",
                sessionManager.fetchUser()?.id, "Alat Bukti\nbaju\nsenjata", "isi Laporan",
                listPasal, listSaksi
            )
        )
        rv_lp_kke.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapterLpKKe = LpKkeAdapter(this, listKke, object : LpKkeAdapter.OnCLickKke{
            override fun onClick(position: Int) {
                val intent = Intent(this@ListLpKodeEtikActivity, DetailLpKkeActivity::class.java)
                intent.putExtra(DETAIL_KKE, listKke[position])
                startActivity(intent)
            }
        })
        rv_lp_kke.adapter = adapterLpKKe
    }
}