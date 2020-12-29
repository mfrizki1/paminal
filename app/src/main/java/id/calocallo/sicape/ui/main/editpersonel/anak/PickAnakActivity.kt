package id.calocallo.sicape.ui.main.editpersonel.anak

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.AnakResp
import id.calocallo.sicape.model.PersonelModel
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_pick_anak.*
import kotlinx.android.synthetic.main.item_2_text.view.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import kotlinx.android.synthetic.main.view_no_data.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PickAnakActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var anakAdapter: ReusableAdapter<AnakResp>
    private lateinit var anakCallback: AdapterCallback<AnakResp>
    private var tempStts = "kandung"
    private var tempList: List<AnakResp>? = null
    private var namaPersonel= ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_anak)
        sessionManager = SessionManager(this)
        anakAdapter = ReusableAdapter(this)
        val detailPersonel = intent.extras?.getParcelable<PersonelModel>("PERSONEL_DETAIL")
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = detailPersonel?.nama
        namaPersonel = detailPersonel?.nama.toString()

        btn_add_single_anak.setOnClickListener {
            val intent = Intent(this, AddSingleAnakActivity::class.java)
            intent.putExtra("PERSONEL", detailPersonel)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            startActivity(intent)
        }


        doFilterStatusAnak()

    }

    private fun doSP(list: ArrayList<AnakResp>?) {
        val item = listOf("Kandung", "Angkat", "Tiri")
        val adapter = ArrayAdapter(this, R.layout.item_spinner, item)

    }

    private fun doFilterStatusAnak() {
        /*
        val listFilterAnak = list?.filter { it.status_ikatan == name }
//        tempList = listFilterAnak
        if (listFilterAnak != null) {
            if (listFilterAnak.isEmpty()) {
                rv_status_anak.gone()
                rl_no_data.visible()
            } else {
                rv_status_anak.visible()
                rl_no_data.gone()
            }
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        }

         */
        rl_pb.visible()
        NetworkConfig().getService().showAnak(
            "Bearer ${sessionManager.fetchAuthToken()}",
            sessionManager.fetchID().toString()
        ).enqueue(object : Callback<ArrayList<AnakResp>> {
            override fun onFailure(call: Call<ArrayList<AnakResp>>, t: Throwable) {
                rl_pb.gone()
                rl_no_data.visible()
                Toast.makeText(this@PickAnakActivity, "Error Koneksi", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<ArrayList<AnakResp>>,
                response: Response<ArrayList<AnakResp>>
            ) {
                if (response.isSuccessful) {
                    rl_pb.gone()
                    rv_status_anak.gone()
                    if(response.body()!!.isEmpty()){
                        rl_no_data.visible()
                        rv_status_anak.gone()
                    }else {
                        rv_status_anak.visible()
                        rl_no_data.gone()
                        anakCallback = object : AdapterCallback<AnakResp> {
                            override fun initComponent(
                                itemView: View,
                                data: AnakResp,
                                itemIndex: Int
                            ) {
                                itemView.txt_detail_1.text = data.nama
                                var stts = ""
                                when (data.status_ikatan) {
                                    "kandung" -> stts = "Kandung"
                                    "angkat" -> stts = "Angkat"
                                    "tiri" -> stts = "Tiri"
                                }
                                itemView.txt_detail_2.text = "Anak $stts"
                            }

                            override fun onItemClicked(
                                itemView: View,
                                data: AnakResp,
                                itemIndex: Int
                            ) {
                                val intent =
                                    Intent(this@PickAnakActivity, EditAnakActivity::class.java)
                                overridePendingTransition(
                                    R.anim.slide_in_right,
                                    R.anim.slide_out_left
                                )
                                intent.putExtra("NAMA_PERSONEL", namaPersonel)
                                intent.putExtra("ANAK", data)
                                startActivity(intent)
                            }
                        }
                        anakAdapter.adapterCallback(anakCallback)
                            .isVerticalView()
                            .addData(response.body()!!)
                            .setLayout(R.layout.item_2_text)
                            .build(rv_status_anak)

                    }
                } else {
                    Toast.makeText(this@PickAnakActivity, "Error", Toast.LENGTH_SHORT).show()
                    rl_no_data.visible()
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        doFilterStatusAnak()
//        tempStts?.let { tempList?.let { it1 -> doFilterStatusAnak(it, it1) } }
    }
}