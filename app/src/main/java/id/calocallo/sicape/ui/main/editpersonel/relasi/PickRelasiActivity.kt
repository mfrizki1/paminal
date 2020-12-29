package id.calocallo.sicape.ui.main.editpersonel.relasi

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PersonelModel
import id.calocallo.sicape.network.response.RelasiResp
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_pick_relasi.*
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
    private lateinit var sessionManager: SessionManager
    private lateinit var adapterRelasi: ReusableAdapter<RelasiResp>
    private lateinit var callbackRelasi: AdapterCallback<RelasiResp>
    private var jenisRelasi: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_relasi)
        sessionManager = SessionManager(this)

        val detailPersonel = intent.extras?.getParcelable<PersonelModel>("PERSONEL_DETAIL")
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = detailPersonel?.nama

        val hak = sessionManager.fetchHakAkses()
        if(hak == "operator"){
            btn_add_single_relasi.gone()
        }

        adapterRelasi = ReusableAdapter(this)

        callbackRelasi = object : AdapterCallback<RelasiResp> {
            override fun initComponent(itemView: View, data: RelasiResp, itemIndex: Int) {
                itemView.txt_edit_pendidikan.text = data.nama

            }

            override fun onItemClicked(itemView: View, data: RelasiResp, itemIndex: Int) {
                val intent = Intent(this@PickRelasiActivity, EditRelasiActivity::class.java)
                intent.putExtra("PERSONEL", detailPersonel)
                intent.putExtra("RELASI", data)
                intent.putExtra("JENIS_RELASI", jenisRelasi)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                startActivity(intent)
            }
        }
        spinner_jenis_relasi.setText("Dalam Negeri")
        jenisRelasi = "dalam_negeri"

        val item = listOf("Dalam Negeri", "Luar Negeri")
        val adapter = ArrayAdapter(this, R.layout.item_spinner, item)
        spinner_jenis_relasi.setAdapter(adapter)
        spinner_jenis_relasi.setOnItemClickListener { parent, view, position, id ->
            when (position) {
                0 -> {
                    jenisRelasi = "dalam_negeri"
                    getListRelasi(jenisRelasi)

                }
                1 -> {
                    jenisRelasi = "luar_negeri"
                    getListRelasi(jenisRelasi)
                }
            }
        }

        getListRelasi(jenisRelasi)
        btn_add_single_relasi.setOnClickListener {
            val intent = Intent(this@PickRelasiActivity, AddSingleRelasiActivity::class.java)
            intent.putExtra("PERSONEL", detailPersonel)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            startActivity(intent)
        }

    }

    private fun getListRelasi(jenis: String?) {
        rl_pb.visible()
        rv_list_relasi.gone()
        NetworkConfig().getService().showRelasi(
            "Bearer ${sessionManager.fetchAuthToken()}",
            sessionManager.fetchID().toString(),
            jenis.toString()
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
                            .setLayout(R.layout.layout_edit_1_text)
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