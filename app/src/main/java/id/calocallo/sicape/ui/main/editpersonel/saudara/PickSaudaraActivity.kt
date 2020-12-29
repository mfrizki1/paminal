package id.calocallo.sicape.ui.main.editpersonel.saudara

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PersonelModel
import id.calocallo.sicape.network.response.SaudaraResp
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_pick_saudara.*
import kotlinx.android.synthetic.main.item_2_text.view.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import kotlinx.android.synthetic.main.view_no_data.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PickSaudaraActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var saudaraAdapter: ReusableAdapter<SaudaraResp>
    private lateinit var saudaraCallback: AdapterCallback<SaudaraResp>

    private var namaPersonel= ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_saudara)
        sessionManager = SessionManager(this)
        saudaraAdapter = ReusableAdapter(this)
        val detailPersonel = intent.extras?.getParcelable<PersonelModel>("PERSONEL_DETAIL")
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = detailPersonel?.nama
        namaPersonel =detailPersonel?.nama.toString()

        btn_add_pick_single_saudara.setOnClickListener {
            val intent = Intent(this, AddSingleSaudaraActivity::class.java)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            intent.putExtra("PERSONEL", detailPersonel)
            startActivity(intent)
        }

        getSaudara()


    }

    private fun getSaudara() {
        rl_pb.visible()
        NetworkConfig().getService().showSaudara(
            "Bearer ${sessionManager.fetchAuthToken()}",
            sessionManager.fetchID().toString()
        ).enqueue(object : Callback<ArrayList<SaudaraResp>> {
            override fun onFailure(call: Call<ArrayList<SaudaraResp>>, t: Throwable) {
                rl_pb.gone()
                rl_no_data.visible()
                Toast.makeText(this@PickSaudaraActivity, "Error Koneksi", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<ArrayList<SaudaraResp>>,
                response: Response<ArrayList<SaudaraResp>>
            ) {
                if (response.isSuccessful) {
                    rl_pb.gone()
                    rv_status_saudara.gone()
                    if(response.body()!!.isEmpty()){
                        rl_no_data.visible()
                        rv_status_saudara.gone()
                    }else {
                        rl_no_data.gone()
                        rv_status_saudara.visible()
                        saudaraCallback = object : AdapterCallback<SaudaraResp> {
                            override fun initComponent(
                                itemView: View,
                                data: SaudaraResp,
                                itemIndex: Int
                            ) {
                                itemView.txt_detail_1.text = data.nama
                                var stts = ""
                                when (data.status_ikatan) {
                                    "kandung" -> stts = "Kandung"
                                    "angkat" -> stts = "Angkat"
                                    "tiri" -> stts = "Tiri"
                                }
                                itemView.txt_detail_2.text = "Saudara $stts"

                            }

                            override fun onItemClicked(
                                itemView: View,
                                data: SaudaraResp,
                                itemIndex: Int
                            ) {
                                val intent =
                                    Intent(
                                        this@PickSaudaraActivity,
                                        EditSaudaraActivity::class.java
                                    )
                                intent.putExtra("NAMA_PERSONEL", namaPersonel)
                                intent.putExtra("SAUDARA", data)
                                overridePendingTransition(
                                    R.anim.slide_in_right,
                                    R.anim.slide_out_left
                                )
                                startActivity(intent)
                            }

                        }
                        saudaraAdapter.adapterCallback(saudaraCallback)
                            .isVerticalView()
                            .addData(response.body()!!)
                            .setLayout(R.layout.item_2_text)
                            .build(rv_status_saudara)
                    }
                } else {
                    rl_no_data.visible()
                    Toast.makeText(this@PickSaudaraActivity, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        getSaudara()
    }
}