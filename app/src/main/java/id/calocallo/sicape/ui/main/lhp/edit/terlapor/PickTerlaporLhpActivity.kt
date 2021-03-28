package id.calocallo.sicape.ui.main.lhp.edit.terlapor

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import id.calocallo.sicape.R
import id.calocallo.sicape.model.LhpResp
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.PersonelPenyelidikResp
import id.calocallo.sicape.ui.main.lhp.EditLhpActivity
import id.calocallo.sicape.ui.main.lhp.edit.terlapor.EditTerlaporLhpActivity.Companion.EDIT_TERLAPOR
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.ui.base.BaseActivity
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import kotlinx.android.synthetic.main.activity_pick_terlapor_lhp.*
import kotlinx.android.synthetic.main.item_2_text.view.*
import kotlinx.android.synthetic.main.layout_edit_1_text.view.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import kotlinx.android.synthetic.main.view_no_data.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PickTerlaporLhpActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private lateinit var adapterTerlapor: ReusableAdapter<PersonelPenyelidikResp>
    private lateinit var callbackTerlapor: AdapterCallback<PersonelPenyelidikResp>

        private var detailLhp :LhpResp? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_terlapor_lhp)
        sessionManager1 = SessionManager1(this)
        adapterTerlapor = ReusableAdapter(this)
        detailLhp = intent.extras?.getParcelable<LhpResp>(EditLhpActivity.EDIT_LHP)
        Log.e("lhp", "${detailLhp}")
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Pilih Data Terlapor"
        apiListTerlapor(detailLhp)
//        rl_pb.visible()
//        if (detailLhp?.personel_terlapor?.isEmpty() == true) {
//            rl_no_data.visible()
//            rl_pb.gone()
//            rv_list_terlapor.gone()
//        }else{
//            rl_pb.gone()
//            getListTerlapor(detailLhp)
//
//        }
        btn_add_single_terlapor.setOnClickListener {
            val intent = Intent(this, AddTerlaporLhpActivity::class.java)
            intent.putExtra(ID_LHP, detailLhp)
            startActivity(intent)
        }
    }

    private fun apiListTerlapor(detailLhp: LhpResp?) {
        rl_pb.visible()
        NetworkConfig().getServLhp().listTerlapor("Bearer ${sessionManager1.fetchAuthToken()}", detailLhp?.id).enqueue(object :
            Callback<ArrayList<PersonelPenyelidikResp>> {
            override fun onResponse(
                call: Call<ArrayList<PersonelPenyelidikResp>>,
                response: Response<ArrayList<PersonelPenyelidikResp>>
            ) {
                if(response.isSuccessful){
                    rl_pb.gone()
                    getListTerlapor(response.body())
                }else{
                    rl_pb.gone()
                    rl_no_data.visible()
                    rv_list_terlapor.gone()
                }
            }

            override fun onFailure(call: Call<ArrayList<PersonelPenyelidikResp>>, t: Throwable) {
                rl_pb.gone()
                rl_no_data.visible()
                rv_list_terlapor.gone()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        apiListTerlapor(detailLhp)
    }

    private fun getListTerlapor(detailLhp: ArrayList<PersonelPenyelidikResp>?) {
        callbackTerlapor = object : AdapterCallback<PersonelPenyelidikResp> {
            override fun initComponent(
                itemView: View,
                data: PersonelPenyelidikResp,
                itemIndex: Int
            ) {
                itemView.txt_edit_pendidikan.text = data.personel?.nama
            }

            override fun onItemClicked(
                itemView: View,
                data: PersonelPenyelidikResp,
                itemIndex: Int
            ) {
                val intent =
                    Intent(this@PickTerlaporLhpActivity, EditTerlaporLhpActivity::class.java)
                intent.putExtra(EDIT_TERLAPOR, data)
                startActivity(intent)
            }
        }
        detailLhp?.let {
            adapterTerlapor.adapterCallback(callbackTerlapor)
                .isVerticalView().addData(it).setLayout(R.layout.layout_edit_1_text)
                .build(rv_list_terlapor)
        }
    }

    companion object {
        const val ID_LHP = "ID_LHP"
    }
}