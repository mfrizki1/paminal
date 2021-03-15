package id.calocallo.sicape.ui.main.editpersonel.alamat

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import id.calocallo.sicape.R
import id.calocallo.sicape.model.AllPersonelModel1
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.AlamatResp
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.calocallo.sicape.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_pick_alamat.*
import kotlinx.android.synthetic.main.layout_edit_1_text.view.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import kotlinx.android.synthetic.main.view_no_data.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PickAlamatActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private lateinit var adapterAlamat: ReusableAdapter<AlamatResp>
    private lateinit var callbackAlamat: AdapterCallback<AlamatResp>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_alamat)
        sessionManager1 = SessionManager1(this)
        val detailPersonel = intent.extras?.getParcelable<AllPersonelModel1>("PERSONEL_DETAIL")
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = detailPersonel?.nama

        ApiAlamat()

        adapterAlamat = ReusableAdapter(this)
        callbackAlamat = object : AdapterCallback<AlamatResp> {
            override fun initComponent(itemView: View, data: AlamatResp, itemIndex: Int) {
                itemView.txt_edit_pendidikan.text = data.alamat
            }

            override fun onItemClicked(itemView: View, data: AlamatResp, itemIndex: Int) {
                val intent = Intent(this@PickAlamatActivity, EditAlamatActivity::class.java)
                intent.putExtra("PERSONEL", detailPersonel)
                intent.putExtra("ALAMAT", data)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

            }
        }
        val hak = sessionManager1.fetchHakAkses()
        if (hak == "operator") {
            btn_add_edit_alamat.gone()
        }
        btn_add_edit_alamat.setOnClickListener {
            val intent = Intent(this, AddSingleAlamatActivity::class.java)
            intent.putExtra("PERSONEL", detailPersonel)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

    }

    private fun ApiAlamat() {
        rl_pb.visible()
        NetworkConfig().getServPers().showAlamat(
            "Bearer ${sessionManager1.fetchAuthToken()}",
//            "4"
            sessionManager1.fetchID().toString()
        ).enqueue(object : Callback<ArrayList<AlamatResp>> {
            override fun onFailure(call: Call<ArrayList<AlamatResp>>, t: Throwable) {
                Toast.makeText(this@PickAlamatActivity, "Error Koneksi", Toast.LENGTH_SHORT).show()
                rl_no_data.visible()
                rl_pb.gone()
            }

            override fun onResponse(
                call: Call<ArrayList<AlamatResp>>,
                response: Response<ArrayList<AlamatResp>>
            ) {
                if (response.isSuccessful) {
                    rl_pb.gone()
                    val alamatresponse = response.body()
                    if (alamatresponse!!.isEmpty()) {
                        rl_no_data.visible()
                        rv_list_alamat.gone()
                    } else {
                        rl_no_data.gone()
                        rv_list_alamat.visible()
                        adapterAlamat.adapterCallback(callbackAlamat)
                            .isVerticalView()
                            .setLayout(R.layout.layout_edit_1_text)
                            .addData(alamatresponse)
                            .build(rv_list_alamat)
                    }
                } else {
                    rl_no_data.visible()
                    Toast.makeText(this@PickAlamatActivity, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        ApiAlamat()
    }
}