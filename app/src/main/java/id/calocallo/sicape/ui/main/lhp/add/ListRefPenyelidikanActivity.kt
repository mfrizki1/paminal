package id.calocallo.sicape.ui.main.lhp.add

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.RefPenyelidikanReq
import id.calocallo.sicape.ui.main.lhp.edit.ref_penyelidikan.AddRefPenyelidikActivity
import id.calocallo.sicape.utils.LhpDataManager
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.calocallo.sicape.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_list_ref_penyelidikan.*
import kotlinx.android.synthetic.main.layout_edit_1_text.view.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import kotlinx.android.synthetic.main.view_no_data.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback

class ListRefPenyelidikanActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var adapteRefLpReq = ReusableAdapter<RefPenyelidikanReq>(this)
    private lateinit var callbackRefLpReq: AdapterCallback<RefPenyelidikanReq>
    private var currRefLp = ArrayList<RefPenyelidikanReq>()
    private var refLp = mutableListOf<RefPenyelidikanReq>()
    private lateinit var lhpDataManager: LhpDataManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_ref_penyelidikan)
        sessionManager1 = SessionManager1(this)
        lhpDataManager = LhpDataManager(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Data Laporan Hasil Penyelidikan"

        //set button for pick and add lp on lhp
        btn_add_lp_lhp.setOnClickListener {
            val intent = Intent(this, AddRefPenyelidikActivity::class.java)
            startActivityForResult(intent, ADD_REF)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
//        Log.e("listbfr", "$currRefLp")
        //set button for next
        btn_next_lp_lhp.setOnClickListener {
            lhpDataManager.setListRefLp(refLp as ArrayList<RefPenyelidikanReq>)
            val intent = Intent(this, PickPersonelPenyelidikActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        }
    }


    private fun getListRefLp(listRef: MutableList<RefPenyelidikanReq>) {
        Log.e("listRef", "$listRef")
        callbackRefLpReq = object : AdapterCallback<RefPenyelidikanReq> {
            override fun initComponent(
                itemView: View, data: RefPenyelidikanReq, itemIndex: Int
            ) {
                itemView.txt_edit_pendidikan.text = data.no_lp
            }

            override fun onItemClicked(
                itemView: View, data: RefPenyelidikanReq, itemIndex: Int
            ) {}
        }
        adapteRefLpReq.adapterCallback(callbackRefLpReq)
            .isVerticalView()
            .addData(refLp)
            .setLayout(R.layout.layout_edit_1_text)
            .build(rv_list_add_lp)

    }

    private fun addLpOnLhp(currRefLp: ArrayList<RefPenyelidikanReq>) {
        if (currRefLp.isNullOrEmpty()) {
            rl_no_data.visible()
            rv_list_add_lp.gone()
        } else {
            callbackRefLpReq = object : AdapterCallback<RefPenyelidikanReq> {
                override fun initComponent(
                    itemView: View,
                    data: RefPenyelidikanReq,
                    itemIndex: Int
                ) {
                    itemView.txt_edit_pendidikan.text = data.detail_keterangan_terlapor
                }

                override fun onItemClicked(
                    itemView: View,
                    data: RefPenyelidikanReq,
                    itemIndex: Int
                ) {
                }

            }
            adapteRefLpReq.adapterCallback(callbackRefLpReq)
                .isVerticalView()
                .addData(currRefLp)
                .setLayout(R.layout.layout_edit_1_text)
                .build(rv_list_add_lp)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_REF && resultCode == AddRefPenyelidikActivity.RES_LP_ON_REF) {
            val dataRef =
                data?.getParcelableExtra<RefPenyelidikanReq>(AddRefPenyelidikActivity.DATA_REF_PENYELIDIK)
            Log.e("dataRef", "$dataRef")
            getListRefLp(refLp)
            if (dataRef != null) {
                refLp.add(dataRef)
            }
        }
    }

/* override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val idLP = data?.getParcelableExtra<RefPenyelidikanReq>(DATA_LP)
        when (resultCode) {
            Activity.RESULT_OK -> {
                when (requestCode) {
                    REQ_LP -> {
//                        idLP?.let { refLp.add(it) }
                        //add dinamic to
//                        currRefLp = refLp
//                        addLpOnLhp(currRefLp)
                        val getLp = data?.getParcelableExtra<RefPenyelidikanReq>(GET_LP_FROM_CHOOSE_LP)
                        getLp?.let { refLp.add(it) }
                        currRefLp = refLp
                        addLpOnLhp(currRefLp)
                    }

                }
            }
        }
    }
*/

    companion object {
        const val LHP_ADD = "LHP_ADD"
        const val ADD_REF = 1
        const val GET_LP_FROM_CHOOSE_LP ="GET_LP_FROM_CHOOSE_LP"
    }
}