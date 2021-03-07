package id.calocallo.sicape.ui.main.lhp.edit.RefPenyelidikan

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.RefPenyelidikanReq
import id.calocallo.sicape.network.response.LhpMinResp
import id.calocallo.sicape.network.response.RefPenyelidikanResp
import id.calocallo.sicape.ui.main.lhp.DetailLhpActivity
import id.calocallo.sicape.ui.main.lhp.add.AddLhpActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_list_detail_ref_penyelidikan.*
import kotlinx.android.synthetic.main.layout_edit_1_text.view.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import kotlinx.android.synthetic.main.view_no_data.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListDetailRefPenyelidikanActivity : BaseActivity() {
    private var adapterRefPenyelidikan = ReusableAdapter<RefPenyelidikanResp>(this)
    private lateinit var callbackRefPenyelidikan: AdapterCallback<RefPenyelidikanResp>
    private lateinit var sessionManager1: SessionManager1
    private var refLpReq = RefPenyelidikanReq()
    private var dataLhp = LhpMinResp()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_detail_ref_penyelidikan)
        sessionManager1 = SessionManager1(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "List Data Referensi Penyelidikan"
        dataLhp = intent.extras?.getParcelable<LhpMinResp>(DetailLhpActivity.DETAIL_REF)!!
        Log.e("list", "$dataLhp")

        /*set button for add single*/
        btn_add_single_ref_penyelidikan.setOnClickListener {
            /*val intent = Intent(this, PickJenisLpActivity::class.java)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            startActivityForResult(intent, REQ_REF_LP_FOR_DETAIL)*/

            val intent = Intent(this, AddRefPenyelidikActivity::class.java).apply {
                this.putExtra(AddRefPenyelidikActivity.SINGLE_ADD, true)
                this.putExtra(AddRefPenyelidikActivity.DATA_LHP, dataLhp)
            }
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        apiListRefLidik(dataLhp)
        /* if (refLpReq.id_lp == null) {
             getListOfRefLP(lhpResp)
         } else {
             addRefPenyelidikanSingle(lhpResp, refLpReq)
         }*/
    }

    private fun apiListRefLidik(dataLhp: LhpMinResp) {
        rl_pb.visible()
        NetworkConfig().getServLhp()
            .getRefPenyelidikan("Bearer ${sessionManager1.fetchAuthToken()}", dataLhp.id).enqueue(
                object :
                    Callback<ArrayList<RefPenyelidikanResp>> {
                    override fun onResponse(
                        call: Call<ArrayList<RefPenyelidikanResp>>,
                        response: Response<ArrayList<RefPenyelidikanResp>>
                    ) {
                        if (response.isSuccessful) {
                            rl_pb.gone()
                            getListOfRefLP(response.body())
                        } else {
                            rl_pb.gone()
                            rl_no_data.visible()
                            rv_list_ref_penyelidikan.gone()
                        }
                    }

                    override fun onFailure(
                        call: Call<ArrayList<RefPenyelidikanResp>>,
                        t: Throwable
                    ) {
                        Toast.makeText(
                            this@ListDetailRefPenyelidikanActivity, "$t", Toast.LENGTH_SHORT
                        )
                            .show()
                        rl_pb.gone()
                        rl_no_data.visible()
                        rv_list_ref_penyelidikan.gone()
                    }
                })
    }

    private fun getListOfRefLP(dataRefLidik: ArrayList<RefPenyelidikanResp>?) {
        callbackRefPenyelidikan = object : AdapterCallback<RefPenyelidikanResp> {
            override fun initComponent(itemView: View, data: RefPenyelidikanResp, itemIndex: Int) {
                itemView.txt_edit_pendidikan.text = data.lp?.no_lp
            }

            override fun onItemClicked(itemView: View, data: RefPenyelidikanResp, itemIndex: Int) {
                val intent = Intent(
                    this@ListDetailRefPenyelidikanActivity,
                    EditDetailRefPenyelidikanActivity::class.java
                )
                intent.putExtra(REQ_DATA_LHP_FOR_DETAIL, data)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                startActivity(intent)
            }
        }
        dataRefLidik?.let {
            adapterRefPenyelidikan.adapterCallback(callbackRefPenyelidikan)
                .filterable()
                .isVerticalView()
                .addData(it)
                .setLayout(R.layout.layout_edit_1_text)
                .build(rv_list_ref_penyelidikan)
        }
        /*  lhpResp?.referensi_penyelidikan?.let {
                adapterRefPenyelidikan.adapterCallback(callbackRefPenyelidikan)
                    .isVerticalView()
                    .addData(it)
                    .setLayout(R.layout.layout_edit_1_text)
                    .build(rv_list_ref_penyelidikan)
            }*/

    }

    override fun onResume() {
        super.onResume()
        apiListRefLidik(dataLhp)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_REF_LP_FOR_DETAIL) {
            if (resultCode == RES_REF_LP_FOR_DETAIL) {
//                val dataLp = data?.getParcelableExtra<LpMinResp>()
                refLpReq = data?.getParcelableExtra<RefPenyelidikanReq>(AddLhpActivity.DATA_LP)!!
                addRefPenyelidikanSingle(dataLhp, refLpReq)
            }
        }

    }

    private fun addRefPenyelidikanSingle(lhpResp: LhpMinResp, refLpReq: RefPenyelidikanReq?) {
        Log.e("add DetailRef", "$refLpReq")
    }


    companion object {
        const val REQ_REF_LP_FOR_DETAIL = 123
        const val RES_REF_LP_FOR_DETAIL = 321
        const val REQ_DATA_LHP_FOR_DETAIL = "REQ_DATA_LHP_FOR_DETAIL"
    }
}