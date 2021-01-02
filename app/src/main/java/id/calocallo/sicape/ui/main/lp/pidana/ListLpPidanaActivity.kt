package id.calocallo.sicape.ui.main.lp.pidana

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.LpPasalResp
import id.calocallo.sicape.network.response.LpPidanaResp
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

        val hak = sessionManager.fetchHakAkses()
        if(hak =="operator"){
            btn_add_lp_pidana.gone()
        }

        btn_add_lp_pidana.setOnClickListener {
            val intent = Intent(this, AddLpActivity::class.java)
            intent.putExtra("JENIS_LP", "pidana")
            startActivity(intent)
        }

        getList()
    }

    private fun getList() {
        listPasal.add(LpPasalResp(1, "Pasal 1", "LOREM IPSUM DOLOR", "", "", ""))
        listPasal.add(LpPasalResp(2, "Pasal 2", "LOREM IPSUM DOLOR", "", "", ""))
        listPasal.add(LpPasalResp(3, "Pasal 3", "LOREM IPSUM DOLOR", "", "", ""))
        listPidana.add(
            LpPidanaResp(
                1, "LP/PIDANA/2020/BIDPROPAM", "pidana", 4,
                "Banjarmasin", "12-12-2000",
                "Rojak Ahmad", "Kombes", "12345678",
                "Polair", sessionManager.fetchUser()?.id, "polisi",
                "", "", "", "",
                "", "", "", 5,
                "LOREM IPSUM DOLOR LOREM IPSUM DOLOR LOREM IPSUM DOLOR LOREM IPSUM DOLOR LOREM IPSUM DOLOR LOREM IPSUM DOLORLOREM IPSUM DOLORLOREM IPSUM DOLORLOREM IPSUM DOLORLOREM IPSUM DOLOR",
                "LOREM IPSUM DOLOR LOREM IPSUM DOLORLOREM IPSUM DOLORLOREM IPSUM DOLORLOREM IPSUM DOLORLOREM IPSUM DOLOR",
                listPasal
            )
        )

        listPidana.add(
            LpPidanaResp(
                2, "LP/PIDANA_2/2020/BIDPROPAM", "pidana",
                4, "Banjarbaru", "08-12-2012",
                "Ahmad", "Kombes", "12345678",
                "Polair", sessionManager.fetchUser()?.id, "sipil",
                "Faisal", "Islam", "Koding",
                "Indonesia", "Jl Bumi", "08121212",
                "123123123", 0,
                "LOREM IPSUM DOLOR LOREM IPSUM DOLOR LOREM IPSUM DOLOR LOREM IPSUM DOLOR LOREM IPSUM DOLOR LOREM IPSUM DOLORLOREM IPSUM DOLORLOREM IPSUM DOLORLOREM IPSUM DOLORLOREM IPSUM DOLOR",
                "LOREM IPSUM DOLOR LOREM IPSUM DOLORLOREM IPSUM DOLORLOREM IPSUM DOLORLOREM IPSUM DOLORLOREM IPSUM DOLOR",
                listPasal
            )
        )

        listPidana.add(
            LpPidanaResp(
                3, "LP/PIDANA_3/2020/BIDPROPAM", "pidana", 4,
                "Banjarbaru", "08-12-2012",
                "Ahmad", "Kombes", "12345678",
                "Polair", sessionManager.fetchUser()?.id, "polisi",
                "", "", "", "", "",
                "", "", 4,
                "LOREM IPSUM DOLOR LOREM IPSUM DOLOR LOREM IPSUM DOLOR LOREM IPSUM DOLOR LOREM IPSUM DOLOR LOREM IPSUM DOLORLOREM IPSUM DOLORLOREM IPSUM DOLORLOREM IPSUM DOLORLOREM IPSUM DOLOR",
                "LOREM IPSUM DOLOR LOREM IPSUM DOLORLOREM IPSUM DOLORLOREM IPSUM DOLORLOREM IPSUM DOLORLOREM IPSUM DOLOR",
                listPasal
            )
        )

        rv_list_pidana.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapterListPidana = LpPidanaAdapter(this, listPidana, object : LpPidanaAdapter.OnClickLpPidana{
            override fun onClick(position: Int) {
                val intent = Intent(this@ListLpPidanaActivity, DetailLpPidanaActivity::class.java)
                intent.putExtra(DETAIL_PIDANA, listPidana[position])
                startActivity(intent)
            }
        })
        rv_list_pidana.adapter = adapterListPidana
    }
}