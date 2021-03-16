package id.calocallo.sicape.ui.main.editpersonel.keluarga

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import id.calocallo.sicape.R
import id.calocallo.sicape.model.AllPersonelModel1
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.PasanganMinResp
import id.calocallo.sicape.ui.main.addpersonal.pasangan.AddPasanganActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.calocallo.sicape.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_pick_pasangan.*
import kotlinx.android.synthetic.main.layout_edit_1_text.view.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import kotlinx.android.synthetic.main.view_no_data.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PickPasanganActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var adapterPasangan = ReusableAdapter<PasanganMinResp>(this)
    private lateinit var callbackPasangan: AdapterCallback<PasanganMinResp>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_pasangan)
        setupActionBarWithBackButton(toolbar)
        sessionManager1 = SessionManager1(this)
        val personel = intent.getParcelableExtra<AllPersonelModel1>("PERSONEL_DETAIL")
        supportActionBar?.title = personel?.nama
        getPasangan(personel)

        rv_list_pasangan
        btn_add_single_pasangan.setOnClickListener {
            val intent = Intent(this, AddPasanganActivity::class.java).apply {
                this.putExtra("ADD_PASANGAN", true)
                this.putExtra("PERSONEL_DETAIL", personel)
            }
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun getPasangan(personel: AllPersonelModel1?) {
        rl_pb.visible()
        NetworkConfig().getServPers()
            .showPasangan("Bearer ${sessionManager1.fetchAuthToken()}", personel?.id.toString())
            .enqueue(object : Callback<ArrayList<PasanganMinResp>> {
                override fun onFailure(call: Call<ArrayList<PasanganMinResp>>, t: Throwable) {
                    Toast.makeText(this@PickPasanganActivity, "$t", Toast.LENGTH_SHORT).show()
                    rl_pb.gone()
                    rl_no_data.visible()
                    rv_list_pasangan.gone()
                }

                override fun onResponse(
                    call: Call<ArrayList<PasanganMinResp>>,
                    response: Response<ArrayList<PasanganMinResp>>
                ) {
                    if (response.isSuccessful) {
                        rl_pb.gone()
                        listPasangan(response.body())
                    } else {
                        rl_pb.gone()
                        rl_no_data.visible()
                        rv_list_pasangan.gone()
                    }
                }
            })
    }

    private fun listPasangan(list: ArrayList<PasanganMinResp>?) {
        callbackPasangan = object : AdapterCallback<PasanganMinResp> {
            override fun initComponent(itemView: View, data: PasanganMinResp, itemIndex: Int) {
                itemView.txt_edit_pendidikan.text = data.nama
            }

            override fun onItemClicked(itemView: View, data: PasanganMinResp, itemIndex: Int) {
                val personel = intent.getParcelableExtra<AllPersonelModel1>("PERSONEL_DETAIL")
                val intent =
                    Intent(this@PickPasanganActivity, EditPasanganActivity::class.java).apply {
                        this.putExtra("PASANGAN_DETAIL", data)
                        this.putExtra("PERSONEL_DETAIL", personel)
                    }
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
        }
        list?.let {
            adapterPasangan.adapterCallback(callbackPasangan)
                .isVerticalView()
                .addData(it)
                .setLayout(R.layout.layout_edit_1_text)
                .build(rv_list_pasangan)
        }
    }

    override fun onResume() {
        super.onResume()
        val personel = intent.getParcelableExtra<AllPersonelModel1>("PERSONEL_DETAIL")
        getPasangan(personel)
    }
}