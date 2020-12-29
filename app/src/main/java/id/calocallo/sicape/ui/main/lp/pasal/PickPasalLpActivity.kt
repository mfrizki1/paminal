package id.calocallo.sicape.ui.main.lp.pasal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.ListIdPasalReq
import id.calocallo.sicape.network.response.LpPasalResp
import id.calocallo.sicape.ui.main.lp.saksi.PickSaksiLpActivity
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_pick_pasal_lp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback

class PickPasalLpActivity : BaseActivity() {
    companion object {
        const val LIST_PASAL = "LIST_PASAL"
    }

    private var selectedIdPasal: MutableList<LpPasalResp> = mutableListOf()
    private lateinit var sessionManager: SessionManager

    private lateinit var adapterPasal: PasalAdapter1

    private val listPasal = arrayListOf(
        LpPasalResp(1, "Pasal 1", "", "", ""),
        LpPasalResp(2, "Pasal 2", "", "", ""),
        LpPasalResp(3, "Pasal 3", "", "", "")
    )

    private var tracker: SelectionTracker<LpPasalResp>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_pasal_lp)
        sessionManager = SessionManager(this)
//        adapterPasal = ReusableAdapter(this)
        setupActionBarWithBackButton(toolbar)
//        supportActionBar?.title = "Pilih Pasal"
        when (sessionManager.getJenisLP()) {
            "pidana" -> supportActionBar?.title = "Tambah Data Laporan Pidana"
            "disiplin" -> supportActionBar?.title = "Tambah Data Laporan Disiplin"
            "kode_etik" -> supportActionBar?.title = "Tambah Data Laporan Kode Etik"
        }

        getPasal()
        btn_add_single_pasal.setOnClickListener {
            val intent = Intent(this@PickPasalLpActivity, AddPasalLpActivity::class.java)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            startActivity(intent)
        }
        btn_choose_pasal_lp.setOnClickListener {
            sessionManager.setListPasalLP(selectedIdPasal as ArrayList<LpPasalResp>)
            val intent = Intent(this@PickPasalLpActivity, PickSaksiLpActivity::class.java)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            startActivity(intent)
        }


    }

    private fun getPasal() {
        //        NetworkConfig().getService()

        adapterPasal = PasalAdapter1(this, pasalItems = listPasal)
        rv_list_pasal.adapter = adapterPasal
        rv_list_pasal.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        tracker = SelectionTracker.Builder<LpPasalResp>(
            "pasalSelection",
            rv_list_pasal,
            PasalItemKeyProvider(adapterPasal),
            PasalItemDetailsLookup(rv_list_pasal),
            StorageStrategy.createParcelableStorage(LpPasalResp::class.java)
        ).withSelectionPredicate(
            SelectionPredicates.createSelectAnything()
        ).build()
        adapterPasal.tracker = tracker

        tracker?.addObserver(
            object : SelectionTracker.SelectionObserver<Long>() {
                override fun onSelectionChanged() {
                    super.onSelectionChanged()
                    tracker?.let {
                        selectedIdPasal = it.selection.toMutableList()
                        Log.e("idPasal", "$selectedIdPasal")
                    }
                }
            }
        )
    }

    override fun onResume() {
        super.onResume()
        getPasal()
    }
}