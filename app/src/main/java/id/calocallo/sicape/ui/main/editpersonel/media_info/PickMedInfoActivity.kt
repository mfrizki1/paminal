package id.calocallo.sicape.ui.main.editpersonel.media_info

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.MedInfoResp
import id.calocallo.sicape.model.AllPersonelModel1
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.calocallo.sicape.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_pick_med_info.*
import kotlinx.android.synthetic.main.item_2_text.view.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import kotlinx.android.synthetic.main.view_no_data.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PickMedInfoActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private lateinit var medInfoAdapter : ReusableAdapter<MedInfoResp>
    private lateinit var medInfoCallbak : AdapterCallback<MedInfoResp>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_med_info)

        sessionManager1 = SessionManager1(this)
        val detailPersonel = intent.extras?.getParcelable<AllPersonelModel1>("PERSONEL_DETAIL")
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = detailPersonel?.nama
        val hak = sessionManager1.fetchHakAkses()
        if (hak == "operator") {
            btn_add_single_med_info.gone()
        }

        ApiMedInfo()

        btn_add_single_med_info.setOnClickListener {
            val intent = Intent(this, AddSingleMedInfoActivity::class.java)
            intent.putExtra("PERSONEL", detailPersonel)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        medInfoAdapter = ReusableAdapter(this)
        medInfoCallbak = object :AdapterCallback<MedInfoResp>{
            override fun initComponent(itemView: View, data: MedInfoResp, itemIndex: Int) {
                itemView.txt_detail_1.text= data.sumber
                itemView.txt_detail_2.text= data.topik
            }

            override fun onItemClicked(itemView: View, data: MedInfoResp, itemIndex: Int) {
                val intent = Intent(this@PickMedInfoActivity, EditMedInfoActivity::class.java)
                intent.putExtra("PERSONEL", detailPersonel)
                intent.putExtra("MED_INFO", data)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

            }

        }

    }

    private fun ApiMedInfo() {
        rl_pb.visible()
        NetworkConfig().getServPers().showMedInfo(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            sessionManager1.fetchID().toString()
            ).enqueue(object : Callback<ArrayList<MedInfoResp>> {
            override fun onFailure(call: Call<ArrayList<MedInfoResp>>, t: Throwable) {
                Toast.makeText(this@PickMedInfoActivity, "$t", Toast.LENGTH_SHORT).show()
                rl_no_data.visible()
                rl_pb.gone()
            }

            override fun onResponse(
                call: Call<ArrayList<MedInfoResp>>,
                response: Response<ArrayList<MedInfoResp>>
            ) {
                if(response.isSuccessful){
                    rl_pb.gone()
                    rv_list_med_info.gone()
                    if(response.body()!!.isEmpty()){
                        rl_no_data.visible()
                        rv_list_med_info.gone()
                    }else{
                        rl_no_data.gone()
                        rv_list_med_info.visible()
                        medInfoAdapter.adapterCallback(medInfoCallbak)
                            .isVerticalView()
                            .setLayout(R.layout.item_2_text)
                            .addData(response.body()!!)
                            .build(rv_list_med_info)

                    }
                }else{
                    Toast.makeText(this@PickMedInfoActivity, "Error", Toast.LENGTH_SHORT).show()
                    rl_no_data.visible()
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        ApiMedInfo()
    }
}