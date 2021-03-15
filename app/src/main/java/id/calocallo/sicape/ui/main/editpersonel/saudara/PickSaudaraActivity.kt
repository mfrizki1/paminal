package id.calocallo.sicape.ui.main.editpersonel.saudara

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import id.calocallo.sicape.R
import id.calocallo.sicape.model.AllPersonelModel1
import id.calocallo.sicape.network.response.SaudaraResp
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.calocallo.sicape.ui.base.BaseActivity
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
    private lateinit var sessionManager1: SessionManager1
    private lateinit var saudaraAdapter: ReusableAdapter<SaudaraResp>
    private lateinit var saudaraCallback: AdapterCallback<SaudaraResp>

    private var namaPersonel = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_saudara)
        sessionManager1 = SessionManager1(this)
        saudaraAdapter = ReusableAdapter(this)
        val detailPersonel = intent.extras?.getParcelable<AllPersonelModel1>("PERSONEL_DETAIL")
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = detailPersonel?.nama
        namaPersonel = detailPersonel?.nama.toString()

        btn_add_pick_single_saudara.setOnClickListener {
            val intent = Intent(this, AddSingleSaudaraActivity::class.java)
            intent.putExtra("PERSONEL", detailPersonel)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        getSaudara()


    }

    private fun getSaudara() {
        rl_pb.visible()
        NetworkConfig().getServPers().showSaudara(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            sessionManager1.fetchID().toString()
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
                    if (response.body()!!.isEmpty()) {
                        rl_no_data.visible()
                        rv_status_saudara.gone()
                    } else {
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

                                startActivity(intent)
                                overridePendingTransition(
                                    R.anim.slide_in_right,
                                    R.anim.slide_out_left
                                )
                            }

                        }
                        saudaraAdapter.adapterCallback(saudaraCallback)
                            .isVerticalView()
                            .addData(sortSaudara(response.body()!!))
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

    private val roles: HashMap<String, Int> = hashMapOf(
        "kandung" to 0,
        "tiri" to 1,
        "angkat" to 2
    )

    fun sortSaudara(keluarga: ArrayList<SaudaraResp>): ArrayList<SaudaraResp> {
        val comparator = Comparator { o1: SaudaraResp, o2: SaudaraResp ->
            return@Comparator roles[o1.status_ikatan]!! - roles[o2.status_ikatan]!!
        }
        val copy = arrayListOf<SaudaraResp>().apply { addAll(keluarga) }
        copy.sortWith(comparator)
        return copy
    }

    override fun onResume() {
        super.onResume()
        getSaudara()
    }
}