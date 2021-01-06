package id.calocallo.sicape.ui.main.editpersonel.pernah_dihukum

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.PernahDihukumResp
import id.calocallo.sicape.model.PersonelModel
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_pick_pernah_dihukum.*
import kotlinx.android.synthetic.main.layout_edit_1_text.view.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import kotlinx.android.synthetic.main.view_no_data.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PickPernahDihukumActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var adapterDihukum: ReusableAdapter<PernahDihukumResp>
    private lateinit var callbackDihukum: AdapterCallback<PernahDihukumResp>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_pernah_dihukum)
        sessionManager = SessionManager(this)

        val detailPersonel = intent.extras?.getParcelable<PersonelModel>("PERSONEL_DETAIL")
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = detailPersonel?.nama
        adapterDihukum = ReusableAdapter(this)
        getPernahDihukumList()
        val hak = sessionManager.fetchHakAkses()
        if(hak == "operator"){
            btn_add_single_pernah_dihukum.gone()
        }

        callbackDihukum = object : AdapterCallback<PernahDihukumResp> {
            override fun initComponent(itemView: View, data: PernahDihukumResp, itemIndex: Int) {
                itemView.txt_edit_pendidikan.text = data.perkara
            }

            override fun onItemClicked(itemView: View, data: PernahDihukumResp, itemIndex: Int) {
                val intent =
                    Intent(this@PickPernahDihukumActivity, EditPernahDihukumActivity::class.java)
                intent.putExtra("PERSONEL", detailPersonel)
                intent.putExtra("DIHUKUM", data)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
        }

        btn_add_single_pernah_dihukum.setOnClickListener {
            val intent = Intent(this, AddSinglePernahDihukumActivity::class.java)
            intent.putExtra("PERSONEL", detailPersonel)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun getPernahDihukumList() {
        rl_pb.visible()
        rv_list_pernah_dihukum.gone()

        NetworkConfig().getService().showDihukum(
            "Bearer ${sessionManager.fetchAuthToken()}",
            sessionManager.fetchID().toString()
        ).enqueue(object : Callback<ArrayList<PernahDihukumResp>> {
            override fun onFailure(call: Call<ArrayList<PernahDihukumResp>>, t: Throwable) {
                Toast.makeText(
                    this@PickPernahDihukumActivity,
                    R.string.error_conn,
                    Toast.LENGTH_SHORT
                ).show()
                rl_no_data.visible()
            }

            override fun onResponse(
                call: Call<ArrayList<PernahDihukumResp>>,
                response: Response<ArrayList<PernahDihukumResp>>
            ) {
                if (response.isSuccessful) {
                    rl_pb.gone()
                    if (response.body()!!.isEmpty()) {
                        rl_no_data.visible()
                        rv_list_pernah_dihukum.gone()

                    } else {
                        rl_no_data.gone()
                        rv_list_pernah_dihukum.visible()
                        adapterDihukum.adapterCallback(callbackDihukum)
                            .isVerticalView()
                            .addData(response.body()!!)
                            .setLayout(R.layout.layout_edit_1_text)
                            .build(rv_list_pernah_dihukum)
                    }
                } else {
                    Toast.makeText(
                        this@PickPernahDihukumActivity,
                        R.string.error,
                        Toast.LENGTH_SHORT
                    ).show()
                    rl_no_data.visible()
                    rv_list_pernah_dihukum.gone()
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        getPernahDihukumList()
    }
}