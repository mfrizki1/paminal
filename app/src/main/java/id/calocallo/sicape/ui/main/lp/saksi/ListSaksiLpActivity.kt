package id.calocallo.sicape.ui.main.lp.saksi

import android.content.Intent
import android.os.Bundle
import android.view.View
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.LpMinResp
import id.calocallo.sicape.network.response.LpResp
import id.calocallo.sicape.network.response.LpSaksiResp
import id.calocallo.sicape.ui.main.lp.pasal.PickPasalActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.calocallo.sicape.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_list_saksi_lp.*
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

class ListSaksiLpActivity : BaseActivity() {
    companion object {
        const val EDIT_SAKSI = "EDIT_SAKSI"
    }

    private lateinit var sessionManager1: SessionManager1
    private lateinit var adapterSaksiEdit: ReusableAdapter<LpSaksiResp>
    private lateinit var callbackSaksiEdit: AdapterCallback<LpSaksiResp>
    private var jenisPelanggaran: String? = null
    private var saksiLp: LpResp? = null
    private var dataLpFull: LpResp? = null
    private var tempIdLp: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_saksi_lp)
        sessionManager1 = SessionManager1(this)
        adapterSaksiEdit = ReusableAdapter(this)
        jenisPelanggaran = sessionManager1.getJenisLP()
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Edit Data Laporan Kode Etik"
        saksiLp = intent.getParcelableExtra<LpResp>(EDIT_SAKSI)
        dataLpFull = intent.getParcelableExtra<LpResp>(PickPasalActivity.DATA_LP)
        if (saksiLp != null) {
            apiListSaksiEdit(saksiLp?.id)
            tempIdLp = saksiLp?.id
        } else if (dataLpFull != null) {
            apiListSaksiEdit(dataLpFull?.id)
            tempIdLp =dataLpFull?.id
        }else{
            val idLpOnAddSaksLp = intent.getIntExtra(AddSaksiLpActivity.ID_LP, 0)
            apiListSaksiEdit(idLpOnAddSaksLp)
            tempIdLp =idLpOnAddSaksLp
        }

        btn_add_single_saksi_edit.setOnClickListener {
            val intent = Intent(this, AddSaksiLpActivity::class.java)
            intent.putExtra("ADD_SINGLE_SAKSI", true)
            intent.putExtra("DATA_LP", saksiLp)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            startActivity(intent)
        }

    }

    private fun apiListSaksiEdit(idLp: Int?) {
        rl_pb.visible()
        idLp?.let {
            NetworkConfig().getServLp().getSaksiAllByLp(
                "Bearer ${sessionManager1.fetchAuthToken()}", it
            ).enqueue(object : Callback<ArrayList<LpSaksiResp>> {
                override fun onFailure(call: Call<ArrayList<LpSaksiResp>>, t: Throwable) {
                    rl_pb.gone()
                    rl_no_data.visible()
                    rv_list_saksi_edit.gone()
                }

                override fun onResponse(
                    call: Call<ArrayList<LpSaksiResp>>,
                    response: Response<ArrayList<LpSaksiResp>>
                ) {
                    if (response.isSuccessful) {
                        rl_pb.gone()
                        getSaksiEdit(response.body())
                    } else {
                        rl_pb.gone()
                        rl_no_data.visible()
                        rv_list_saksi_edit.gone()
                    }
                }
            })
        }
    }

    private fun getSaksiEdit(body: ArrayList<LpSaksiResp>?) {
        callbackSaksiEdit = object : AdapterCallback<LpSaksiResp> {
            override fun initComponent(itemView: View, data: LpSaksiResp, itemIndex: Int) {
                if(data.status_saksi == "personel"){
                    itemView.txt_detail_1.text = data.personel?.nama
                    itemView.txt_detail_2.text = "Saksi"
                }else{
                    itemView.txt_detail_1.text = data.nama
                    if(data.is_korban == 0){
                        itemView.txt_detail_2.text = "Saksi"
                    }else{
                        itemView.txt_detail_2.text = "Korban"
                    }
                }
            }

            override fun onItemClicked(itemView: View, data: LpSaksiResp, itemIndex: Int) {
                val intent = Intent(this@ListSaksiLpActivity, EditSaksiLpActivity::class.java)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                intent.putExtra("NAMA_JENIS", jenisPelanggaran)
                intent.putExtra("SAKSI_EDIT", data)
                startActivity(intent)
            }
        }
        body?.let {
            adapterSaksiEdit.adapterCallback(callbackSaksiEdit)
                .isVerticalView().addData(it).setLayout(R.layout.item_2_text)
                .build(rv_list_saksi_edit)
        }
    }

    override fun onResume() {
        super.onResume()
            apiListSaksiEdit(tempIdLp)
    }
}