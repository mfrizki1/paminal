package id.calocallo.sicape.ui.main.lhp.add

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.RefPenyelidikanReq
import id.calocallo.sicape.network.response.LpCustomResp
import id.calocallo.sicape.ui.main.choose.lp.ChooseLpActivity
import id.calocallo.sicape.ui.main.lhp.add.AddLhpActivity.Companion.DATA_LP
import id.calocallo.sicape.utils.LhpDataManager
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_referensi_penyelidikan_lhp.*
import kotlinx.android.synthetic.main.layout_edit_1_text.view.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import kotlinx.android.synthetic.main.view_no_data.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback

class ReferensiPenyelidikanLhpActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private var adapteRefLpReq = ReusableAdapter<RefPenyelidikanReq>(this)
    private lateinit var callbackRefLpReq: AdapterCallback<RefPenyelidikanReq>
    private var currRefLp = ArrayList<RefPenyelidikanReq>()
    private var refLp = ArrayList<RefPenyelidikanReq>()
    private lateinit var lhpDataManager: LhpDataManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_referensi_penyelidikan_lhp)
        sessionManager = SessionManager(this)
        lhpDataManager = LhpDataManager(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Data Laporan Hasil Penyelidikan"

        //set button for pick and add lp on lhp
        btn_add_lp_lhp.setOnClickListener {
            val intent = Intent(this, ChooseLpActivity::class.java)
            startActivityForResult(intent, REQ_LP)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
//        Log.e("listbfr", "$currRefLp")
        //set button for next
        btn_next_lp_lhp.setOnClickListener {
            lhpDataManager.setListRefLp(currRefLp)
            val intent = Intent(this, PickPersonelPenyelidikActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        }
    }

    /*
    private fun getListRefLp() {
        if (refLp.isNullOrEmpty()) {
            rl_no_data.visible()
            rv_list_add_lp.gone()
        } else {
            rv_list_add_lp.visible()
            callbackRefLpResp = object : AdapterCallback<RefPenyelidikanResp> {
                override fun initComponent(
                    itemView: View,
                    data: RefPenyelidikanResp,
                    itemIndex: Int
                ) {
                    itemView.txt_edit_pendidikan.text = data.no_lp
                }

                override fun onItemClicked(
                    itemView: View,
                    data: RefPenyelidikanResp,
                    itemIndex: Int
                ) {
                }
            }
            adapteRefLpResp.adapterCallback(callbackRefLpResp)
                .isVerticalView()
                .addData(refResp)
                .setLayout(R.layout.layout_edit_1_text)
                .build(rv_list_add_lp)

        }
    }


     */
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
                    itemView.txt_edit_pendidikan.text = data.no_lp
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


    companion object {
        const val LHP_ADD = "LHP_ADD"
        const val REQ_LP = 200
        const val GET_LP_FROM_CHOOSE_LP = "GET_LP_FROM_CHOOSE_LP"
    }
}