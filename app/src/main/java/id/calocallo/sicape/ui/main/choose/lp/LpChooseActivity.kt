package id.calocallo.sicape.ui.main.choose.lp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.LpResp
import id.calocallo.sicape.ui.main.lhp.AddLhpActivity.Companion.DATA_LP
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_lp_choose.*
import kotlinx.android.synthetic.main.layout_1_text_clickable.view.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback

class LpChooseActivity : BaseActivity() {
    companion object {
        const val JENIS_LP_CHOOSE = "JENIS_LP_CHOOSE"
    }
    private var listLp: MutableList<LpResp> = mutableListOf()

    private lateinit var sessionManager: SessionManager
    private lateinit var adapterLpChoose: ReusableAdapter<LpResp>
    private lateinit var callbackLpChoose: AdapterCallback<LpResp>
    private var tempJenis: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lp_choose)

        sessionManager = SessionManager(this)
        adapterLpChoose = ReusableAdapter(this)
        setupActionBarWithBackButton(toolbar)
        val jenis = intent.extras?.getString(JENIS_LP_CHOOSE)
        when (jenis) {
            "Pidana" -> {
                supportActionBar?.title = "Pilih Data Laporan Pidana"
                tempJenis = "pidana"
            }
            "Kode Etik" -> {
                supportActionBar?.title = "Pilih Data Laporan Kode Etik"
                tempJenis = "kode_etik"
            }
            "Disiplin" -> {
                tempJenis = "disiplin"
                supportActionBar?.title = "Pilih Data Laporan Disiplin"
            }
        }

        getListLpChoose(tempJenis)
//        listLP()
    }

//    private fun listLP(): MutableList<LpResp> {
//
//        return listLp
//    }

    private fun getListLpChoose(jenis: String?) {
        listLp.add(
            LpResp(
                1, "pidana", "LP/PIDANA/2020/BIDPROPAM", 4,
                4, 1, sessionManager.getAlatBukiLP(), sessionManager.getListPasalLP(),
                sessionManager.getListSaksi(), "KET", "", "", ""
            )
        )
        listLp.add(
            LpResp(
                2, "pidana", "LP/PIDANA2/2020/BIDPROPAM", 4,
                5, 2, sessionManager.getAlatBukiLP(), sessionManager.getListPasalLP(),
                sessionManager.getListSaksi(), "KET", "", "", ""

            )
        )
        listLp.add(
            LpResp(
                3, "kode_etik", "LP/KODE_ETIK/2020/BIDPROPAM", 4,
                5, 2, sessionManager.getAlatBukiLP(), sessionManager.getListPasalLP(),
                sessionManager.getListSaksi(), "KET", "", "", ""

            )
        )
        Log.e("listLp", "$listLp")
        Log.e("jenis", "$jenis")

        val filteredList = listLp.filter { it.jenis == jenis }
        Log.e("filteredList", "$filteredList")
        callbackLpChoose = object :AdapterCallback<LpResp>{
            override fun initComponent(itemView: View, data: LpResp, itemIndex: Int) {
                itemView.txt_1_clickable.text = data.no_lp
                itemView.setOnClickListener {
                }
            }

            override fun onItemClicked(itemView: View, data: LpResp, itemIndex: Int) {
                itemView.img_clickable.visible()
                val intent = Intent()
                intent.putExtra(DATA_LP, data)
                setResult(RESULT_OK, intent)
                finish()

            }
        }
        adapterLpChoose.adapterCallback(callbackLpChoose)
            .isVerticalView()
            .addData(filteredList)
            .setLayout(R.layout.layout_1_text_clickable)
            .build(rv_list_lp_choose)
    }
}