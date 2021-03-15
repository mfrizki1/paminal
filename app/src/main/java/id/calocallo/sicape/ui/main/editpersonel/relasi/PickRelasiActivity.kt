package id.calocallo.sicape.ui.main.editpersonel.relasi

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import id.calocallo.sicape.R
import id.calocallo.sicape.model.AllPersonelModel1
import id.calocallo.sicape.network.response.RelasiResp
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.calocallo.sicape.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_pick_relasi.*
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

class PickRelasiActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private lateinit var adapterRelasi: ReusableAdapter<RelasiResp>
    private lateinit var callbackRelasi: AdapterCallback<RelasiResp>
    private var jenisRelasi: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_relasi)
        sessionManager1 = SessionManager1(this)

        val detailPersonel = intent.extras?.getParcelable<AllPersonelModel1>("PERSONEL_DETAIL")
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = detailPersonel?.nama

        val hak = sessionManager1.fetchHakAkses()
        if (hak == "operator") {
            btn_add_single_relasi.gone()
        }

        adapterRelasi = ReusableAdapter(this)

        callbackRelasi = object : AdapterCallback<RelasiResp> {
            override fun initComponent(itemView: View, data: RelasiResp, itemIndex: Int) {
                itemView.txt_detail_1.text = data.nama
                when (data.lokasi) {
                    "dalam_negeri" -> itemView.txt_detail_2.text = "Dalam Negeri"
                    "luar_negeri" -> itemView.txt_detail_2.text = "Luar Negeri"
                }


            }

            override fun onItemClicked(itemView: View, data: RelasiResp, itemIndex: Int) {
                val intent = Intent(this@PickRelasiActivity, EditRelasiActivity::class.java)
                intent.putExtra("RELASI", data)
//                intent.putExtra("PERSONEL", detailPersonel)
//                intent.putExtra("JENIS_RELASI", jenisRelasi)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
        }

        getListRelasi(jenisRelasi)
        btn_add_single_relasi.setOnClickListener {
            val intent = Intent(this@PickRelasiActivity, AddSingleRelasiActivity::class.java)
            intent.putExtra("PERSONEL", detailPersonel)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

    }

    private fun getListRelasi(jenis: String?) {
        rl_pb.visible()
        rv_list_relasi.gone()
        NetworkConfig().getServPers().showRelasi(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            sessionManager1.fetchID().toString()
        ).enqueue(object : Callback<ArrayList<RelasiResp>> {
            override fun onFailure(call: Call<ArrayList<RelasiResp>>, t: Throwable) {
                rl_no_data.visible()
                rl_pb.gone()
                Toast.makeText(this@PickRelasiActivity, R.string.error_conn, Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onResponse(
                call: Call<ArrayList<RelasiResp>>,
                response: Response<ArrayList<RelasiResp>>
            ) {
                if (response.isSuccessful) {
                    rl_pb.gone()
                    if (response.body()!!.isEmpty()) {
                        rl_no_data.visible()
                        rv_list_relasi.gone()

                    } else {
                        rl_no_data.gone()
                        rv_list_relasi.visible()
                        adapterRelasi.adapterCallback(callbackRelasi)
                            .isVerticalView()
                            .addData(response.body()!!)
                            .setLayout(R.layout.item_2_text)
                            .build(rv_list_relasi)
                    }
                } else {
                    rl_no_data.visible()
                    rv_list_relasi.gone()
                    Toast.makeText(this@PickRelasiActivity, R.string.error, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        getListRelasi(jenisRelasi)
    }
}