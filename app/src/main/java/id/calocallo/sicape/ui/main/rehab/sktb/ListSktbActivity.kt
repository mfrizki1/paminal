package id.calocallo.sicape.ui.main.rehab.sktb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkDummy
import id.calocallo.sicape.network.response.SktbResp
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_list_sktb.*
import kotlinx.android.synthetic.main.layout_edit_1_text.view.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import kotlinx.android.synthetic.main.view_no_data.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListSktbActivity : BaseActivity() {
    private var adapterSktb = ReusableAdapter<SktbResp>(this)
    private lateinit var callbackSktb: AdapterCallback<SktbResp>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_sktb)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "List Data SKTB"

        btn_add_single_sktb.setOnClickListener {
            startActivity(Intent(this, AddSktbActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        getListSktb()
    }

    private fun getListSktb() {
        rl_pb.visible()
        NetworkDummy().getService().getSktb().enqueue(object : Callback<ArrayList<SktbResp>> {
            override fun onFailure(call: Call<ArrayList<SktbResp>>, t: Throwable) {
                rl_no_data.visible()
                rv_list_sktb.gone()
            }

            override fun onResponse(
                call: Call<ArrayList<SktbResp>>,
                response: Response<ArrayList<SktbResp>>
            ) {
                rl_pb.gone()
                if (response.isSuccessful) {
                    callbackSktb = object : AdapterCallback<SktbResp> {
                        override fun initComponent(itemView: View, data: SktbResp, itemIndex: Int) {
                            itemView.txt_edit_pendidikan.text = data.no_sktb
                        }

                        override fun onItemClicked(itemView: View, data: SktbResp, itemIndex: Int) {
                            val intent =
                                Intent(this@ListSktbActivity, DetailSktbActivity::class.java)
                            intent.putExtra(DetailSktbActivity.DETAIL_SKTB, data)
                            startActivity(intent)
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                        }
                    }
                    response.body()?.let {
                        adapterSktb.adapterCallback(callbackSktb)
                            .isVerticalView().filterable()
                            .addData(it)
                            .setLayout(R.layout.layout_edit_1_text)
                            .build(rv_list_sktb)
                    }
                } else {
                    rl_no_data.visible()
                    rv_list_sktb.gone()
                }
            }
        })


    }
}