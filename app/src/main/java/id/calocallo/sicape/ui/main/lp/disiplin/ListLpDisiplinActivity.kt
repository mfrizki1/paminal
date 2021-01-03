package id.calocallo.sicape.ui.main.lp.disiplin

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.LpDisiplinResp
import id.calocallo.sicape.network.response.LpPasalResp
import id.calocallo.sicape.ui.main.lp.AddLpActivity
import id.calocallo.sicape.ui.main.lp.disiplin.DetailLpDisiplinActivity.Companion.DETAIL_DISIPLIN
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_list_lp_disiplin.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class ListLpDisiplinActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var adapterListLDisiplin: LpDisiplinAdapter
    private var listDisiplin = arrayListOf<LpDisiplinResp>()
    private var listPasal = arrayListOf<LpPasalResp>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_lp_disiplin)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "List Data Laporan Polisi Disiplin"
        sessionManager = SessionManager(this)
        sessionManager.setJenisLP("disiplin")

        getListDisiplin()

        btn_add_lp_disiplin.setOnClickListener {
            val intent = Intent(this, AddLpActivity::class.java)
            intent.putExtra("JENIS_LP", "disiplin")
            startActivity(intent)
        }
    }

    private fun getListDisiplin() {
        listPasal.add(LpPasalResp(1, "Pasal 1", "LOREM IPSUM DOLOR", "", "", ""))
        listPasal.add(LpPasalResp(2, "Pasal 2", "LOREM IPSUM DOLOR", "", "", ""))
        listPasal.add(LpPasalResp(3, "Pasal 3", "LOREM IPSUM DOLOR", "", "", ""))

        listDisiplin.add(
            LpDisiplinResp(
                1, "LP/DISIPLIN1",
                "disiplin", 4, 5,
                "Banjarmasin", "12-01-20", "Budi",
                "IPDA", "87654321", "KOMBES",
                sessionManager.fetchUser()?.id, "macam_pelanggaran", "keterangan terlapor",
                "kronologis", "rincian", listPasal
            )
        )

        listDisiplin.add(
            LpDisiplinResp(
                2, "LP/DISIPLIN2",
                "disiplin", 4, 5,
                "Banjarmasin", "12-01-20", "Budi",
                "IPDA", "87654321", "KOMBES",
                sessionManager.fetchUser()?.id, "macam_pelanggaran", "keterangan terlapor",
                "kronologis", "rincian", listPasal
            )
        )

        listDisiplin.add(
            LpDisiplinResp(
                2, "LP/DISIPLIN1",
                "disiplin", 4, 5,
                "Banjarmasin", "12-01-20", "Budi",
                "IPDA", "87654321", "KOMBES",
                sessionManager.fetchUser()?.id, "macam_pelanggaran", "keterangan terlapor",
                "kronologis", "rincian", listPasal
            )
        )
        rv_lp_disiplin.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapterListLDisiplin = LpDisiplinAdapter(this, listDisiplin, object :LpDisiplinAdapter.OnClickDisiplin{
            override fun onClick(position: Int) {
                val intent = Intent(this@ListLpDisiplinActivity, DetailLpDisiplinActivity::class.java)
                intent.putExtra(DETAIL_DISIPLIN, listDisiplin[position])
                startActivity(intent)
            }
        })
        rv_lp_disiplin.adapter = adapterListLDisiplin
    }
}