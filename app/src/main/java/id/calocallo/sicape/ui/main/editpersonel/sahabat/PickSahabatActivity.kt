package id.calocallo.sicape.ui.main.editpersonel.sahabat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PersonelModel
import id.calocallo.sicape.model.SahabatResp
import id.calocallo.sicape.model.TokohResp
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.ui.main.editpersonel.tokoh.EditTokohActivity
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_pick_sahabat.*
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

class PickSahabatActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var sahabatAdapter: ReusableAdapter<SahabatResp>
    private lateinit var sahabatCallback: AdapterCallback<SahabatResp>
    private var namaPersonel = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_sahabat)

        sessionManager = SessionManager(this)
        sahabatAdapter = ReusableAdapter(this)
        val detailPersonel = intent.extras?.getParcelable<PersonelModel>("PERSONEL_DETAIL")
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = detailPersonel?.nama
        namaPersonel = detailPersonel?.nama.toString()
        val hak = sessionManager.fetchHakAkses()
        if (hak == "operator") {
            btn_add_single_sahabat.gone()
        }

        sahabatCallback = object : AdapterCallback<SahabatResp> {
            override fun initComponent(itemView: View, data: SahabatResp, itemIndex: Int) {
                itemView.txt_edit_pendidikan.text = data.nama
            }

            override fun onItemClicked(itemView: View, data: SahabatResp, itemIndex: Int) {
                val intent = Intent(this@PickSahabatActivity, EditSahabatActivity::class.java)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                intent.putExtra("NAMA_PERSONEL", namaPersonel)
                intent.putExtra("SAHABAT", data)
                startActivity(intent)
            }
        }
        btn_add_single_sahabat.setOnClickListener {
            val intent = Intent(this@PickSahabatActivity, AddSingleSahabatActivity::class.java)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            intent.putExtra("PERSONEL", detailPersonel)
            startActivity(intent)
        }

        ApiSahabat()
    }

    private fun ApiSahabat() {
        rl_pb.visible()
        rv_list_sahabat.gone()
        NetworkConfig().getService().showSahabat(
            "Bearer ${sessionManager.fetchAuthToken()}",
            sessionManager.fetchID().toString()
        ).enqueue(object : Callback<ArrayList<SahabatResp>> {
            override fun onFailure(call: Call<ArrayList<SahabatResp>>, t: Throwable) {
                Toast.makeText(this@PickSahabatActivity, "Error Koneksi", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<ArrayList<SahabatResp>>,
                response: Response<ArrayList<SahabatResp>>
            ) {
                if (response.isSuccessful) {
                    rl_pb.gone()
                    if (response.body()!!.isEmpty()) {
                        rl_no_data.visible()
                        rv_list_sahabat.gone()
                    } else {
                        rv_list_sahabat.visible()
                        rl_no_data.gone()
                        sahabatAdapter.adapterCallback(sahabatCallback)
                            .isVerticalView()
                            .addData(response.body()!!)
                            .setLayout(R.layout.layout_edit_1_text)
                            .build(rv_list_sahabat)
                    }
                } else {
                    rl_no_data.visible()
                    Toast.makeText(this@PickSahabatActivity, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        ApiSahabat()
    }
}