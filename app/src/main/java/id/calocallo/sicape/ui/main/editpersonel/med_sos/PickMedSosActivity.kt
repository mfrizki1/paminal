package id.calocallo.sicape.ui.main.editpersonel.med_sos

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.MedSosResp
import id.calocallo.sicape.model.PersonelModel
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_pick_med_sos.*
import kotlinx.android.synthetic.main.item_2_text.view.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import kotlinx.android.synthetic.main.view_no_data.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PickMedSosActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var adapterMedsos: ReusableAdapter<MedSosResp>
    private lateinit var callbackMedsos: AdapterCallback<MedSosResp>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_med_sos)
        val detailPersonel = intent.extras?.getParcelable<PersonelModel>("PERSONEL_DETAIL")
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = detailPersonel?.nama

        sessionManager = SessionManager(this)
        adapterMedsos = ReusableAdapter(this)

        callbackMedsos = object : AdapterCallback<MedSosResp> {
            override fun initComponent(itemView: View, data: MedSosResp, itemIndex: Int) {
                itemView.txt_detail_1.text = data.nama_medsos
                itemView.txt_detail_2.text = data.nama_akun
            }

            override fun onItemClicked(itemView: View, data: MedSosResp, itemIndex: Int) {
                val intent = Intent(this@PickMedSosActivity, EditMedSosActivity::class.java)
                intent.putExtra("PERSONEL", detailPersonel)
                intent.putExtra("MEDSOS", data)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                startActivity(intent)
            }
        }

        ApiMedsos()

        btn_add_single_med_sos.setOnClickListener {
            val intent = Intent(this@PickMedSosActivity, AddSingleMedSosActivity::class.java)
            intent.putExtra("PERSONEL", detailPersonel)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            startActivity(intent)
        }
    }

    private fun ApiMedsos() {
        rl_pb.visible()
        NetworkConfig().getService().showMedSos(
            "Bearer ${sessionManager.fetchAuthToken()}",
            sessionManager.fetchID().toString()
        ).enqueue(object : Callback<ArrayList<MedSosResp>> {
            override fun onFailure(call: Call<ArrayList<MedSosResp>>, t: Throwable) {
                Toast.makeText(this@PickMedSosActivity, R.string.error_conn, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<ArrayList<MedSosResp>>,
                response: Response<ArrayList<MedSosResp>>
            ) {
                if(response.isSuccessful){
                    rl_pb.gone()
                    if(response.body()!!.isEmpty()){
                        rl_no_data.visible()
                        rv_list_med_sos.gone()
                    }else{
                        rl_no_data.gone()
                        rv_list_med_sos.visible()
                        adapterMedsos.adapterCallback(callbackMedsos)
                            .isVerticalView()
                            .addData(response.body()!!)
                            .setLayout(R.layout.item_2_text)
                            .build(rv_list_med_sos)
                    }
                }else{
                    rl_no_data.visible()
                    rv_list_med_sos.gone()
                    Toast.makeText(this@PickMedSosActivity, R.string.error, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        ApiMedsos()
    }
}