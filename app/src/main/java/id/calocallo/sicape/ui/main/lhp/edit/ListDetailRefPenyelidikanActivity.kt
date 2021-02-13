package id.calocallo.sicape.ui.main.lhp.edit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import id.calocallo.sicape.R
import id.calocallo.sicape.model.LhpResp
import id.calocallo.sicape.network.request.RefPenyelidikanReq
import id.calocallo.sicape.network.response.RefPenyelidikanResp
import id.calocallo.sicape.ui.main.choose.lp.ChooseLpActivity
import id.calocallo.sicape.ui.main.lhp.DetailLhpActivity
import id.calocallo.sicape.ui.main.lhp.add.AddLhpActivity
import id.calocallo.sicape.utils.SessionManager1
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_list_detail_ref_penyelidikan.*
import kotlinx.android.synthetic.main.layout_edit_1_text.view.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback

class ListDetailRefPenyelidikanActivity : BaseActivity() {
    private var adapterRefPenyelidikan = ReusableAdapter<RefPenyelidikanResp>(this)
    private lateinit var callbackRefPenyelidikan: AdapterCallback<RefPenyelidikanResp>
    private lateinit var sessionManager1: SessionManager1
    private var refLpReq = RefPenyelidikanReq()
    private var lhpResp = LhpResp()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_detail_ref_penyelidikan)
        sessionManager1 = SessionManager1(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "List Data Referensi Penyelidikan"

        /*set button for add single*/
        btn_add_single_ref_penyelidikan.setOnClickListener {
            val intent = Intent(this, ChooseLpActivity::class.java)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            startActivityForResult(intent, REQ_REF_LP_FOR_DETAIL)
        }
        lhpResp = intent.extras?.getParcelable<LhpResp>(DetailLhpActivity.DETAIL_REF)!!
        Log.e("list", "$lhpResp")
        if (refLpReq.id_lp == null && refLpReq.no_lp == null) {
            getListOfRefLP(lhpResp)
        } else {
            addRefPenyelidikanSingle(lhpResp, refLpReq)
        }
    }

    private fun getListOfRefLP(lhpResp: LhpResp?) {
        callbackRefPenyelidikan = object : AdapterCallback<RefPenyelidikanResp> {
            override fun initComponent(itemView: View, data: RefPenyelidikanResp, itemIndex: Int) {
                itemView.txt_edit_pendidikan.text = data.no_lp
            }

            override fun onItemClicked(itemView: View, data: RefPenyelidikanResp, itemIndex: Int) {
                //TODO CREATE EDIT REF LP
                val intent = Intent(
                    this@ListDetailRefPenyelidikanActivity,
                    EditDetailRefPenyelidikanActivity::class.java
                )
                intent.putExtra(REQ_DATA_LHP_FOR_DETAIL, data)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                startActivity(intent)
            }
        }
        lhpResp?.referensi_penyelidikan?.let {
            adapterRefPenyelidikan.adapterCallback(callbackRefPenyelidikan)
                .isVerticalView()
                .addData(it)
                .setLayout(R.layout.layout_edit_1_text)
                .build(rv_list_ref_penyelidikan)
        }
    }

    override fun onResume() {
        super.onResume()
//        getListOfRefLP()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //TODO ADD API ON THIS ACTIVITY
        if(requestCode == REQ_REF_LP_FOR_DETAIL){
            if (resultCode == Activity.RESULT_OK) {
                refLpReq = data?.getParcelableExtra<RefPenyelidikanReq>(AddLhpActivity.DATA_LP)!!
                addRefPenyelidikanSingle(lhpResp, refLpReq)
            }
        }

    }

    private fun addRefPenyelidikanSingle(lhpResp: LhpResp, refLpReq: RefPenyelidikanReq?) {
        Log.e("add DetailRef", "$refLpReq")
    }

    companion object {
        const val REQ_REF_LP_FOR_DETAIL = 123
        const val REQ_DATA_LHP_FOR_DETAIL = "REQ_DATA_LHP_FOR_DETAIL"
    }
}