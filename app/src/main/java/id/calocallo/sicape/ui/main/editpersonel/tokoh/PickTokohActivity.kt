package id.calocallo.sicape.ui.main.editpersonel.tokoh

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import id.calocallo.sicape.R
import id.calocallo.sicape.model.AllPersonelModel1
import id.calocallo.sicape.network.response.TokohResp
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_pick_tokoh.*
import kotlinx.android.synthetic.main.layout_edit_1_text.view.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import kotlinx.android.synthetic.main.view_no_data.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PickTokohActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private lateinit var tokohAdapter: ReusableAdapter<TokohResp>
    private lateinit var tokohCallback: AdapterCallback<TokohResp>
    private var namaPersonel = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_tokoh)

        sessionManager1 = SessionManager1(this)
        tokohAdapter = ReusableAdapter(this)
        val detailPersonel = intent.extras?.getParcelable<AllPersonelModel1>("PERSONEL_DETAIL")
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = detailPersonel?.nama
        namaPersonel =  detailPersonel?.nama.toString()
        val hak = sessionManager1.fetchHakAkses()
        if(hak =="operator"){
            btn_add_single_tokoh.gone()
        }

        tokohCallback = object :AdapterCallback<TokohResp>{
            override fun initComponent(itemView: View, data: TokohResp, itemIndex: Int) {
                itemView.txt_edit_pendidikan.text = data.nama
            }

            override fun onItemClicked(itemView: View, data: TokohResp, itemIndex: Int) {
                val intent = Intent(this@PickTokohActivity, EditTokohActivity::class.java)
                intent.putExtra("NAMA_PERSONEL", namaPersonel)
                intent.putExtra("TOKOH", data)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
        }
        btn_add_single_tokoh.setOnClickListener {
            val intent = Intent(this@PickTokohActivity, AddSingleTokohActivity::class.java)
            intent.putExtra("PERSONEL", detailPersonel)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        ApiTokoh()
    }

    private fun ApiTokoh() {
        rl_pb.visible()
        rv_list_tokoh.gone()
        NetworkConfig().getServPers().showTokoh(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            sessionManager1.fetchID().toString()
        ).enqueue(object : Callback<ArrayList<TokohResp>> {
            override fun onFailure(call: Call<ArrayList<TokohResp>>, t: Throwable) {
                Toast.makeText(this@PickTokohActivity, "Error Koneksi", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<ArrayList<TokohResp>>,
                response: Response<ArrayList<TokohResp>>
            ) {
                if(response.isSuccessful){
                    rl_pb.gone()
                    if(response.body()!!.isEmpty()){
                        rl_no_data.visible()
                        rv_list_tokoh.gone()
                    }else{
                        rv_list_tokoh.visible()
                        rl_no_data.gone()
                        tokohAdapter.adapterCallback(tokohCallback)
                            .isVerticalView()
                            .addData(response.body()!!)
                            .setLayout(R.layout.layout_edit_1_text)
                            .build(rv_list_tokoh)
                    }
                }else{
                    Toast.makeText(this@PickTokohActivity, "Error", Toast.LENGTH_SHORT).show()
                    rl_no_data.visible()
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        ApiTokoh()
    }
}