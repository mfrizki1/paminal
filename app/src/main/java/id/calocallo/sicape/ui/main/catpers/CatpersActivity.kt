package id.calocallo.sicape.ui.main.catpers

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.CatpersModel
import id.calocallo.sicape.network.NetworkDummy
import id.calocallo.sicape.network.response.CatpersResp
import id.calocallo.sicape.ui.main.lp.LpPidanaPasalAdapter
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_catpres.*
import kotlinx.android.synthetic.main.activity_detail_catpers.*
import kotlinx.android.synthetic.main.item_catpers.view.*
import kotlinx.android.synthetic.main.item_lp_pidana.view.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import kotlinx.android.synthetic.main.view_no_data.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CatpersActivity : BaseActivity(){
    private var adapterCatpers = ReusableAdapter<CatpersResp>(this)
    private lateinit var callbackCatpers: AdapterCallback<CatpersResp>
    private val viewPool = RecyclerView.RecycledViewPool()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catpres)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "List Data Catatan Personel"
        getListCatpers()

    }

    private fun getListCatpers() {
        rl_pb.visible()
        NetworkDummy().getService().getCatpers().enqueue(object : Callback<ArrayList<CatpersResp>> {
            override fun onFailure(call: Call<ArrayList<CatpersResp>>, t: Throwable) {
                rl_no_data.visible()
                rl_pb.gone()
                rv_catpers.gone()
            }

            override fun onResponse(
                call: Call<ArrayList<CatpersResp>>,
                response: Response<ArrayList<CatpersResp>>
            ) {
                if (response.isSuccessful) {
                    rl_pb.gone()
                    RV(response.body())

                } else {
                    rl_no_data.visible()
                    rl_pb.gone()
                    rv_catpers.gone()
                }
            }
        })
    }

    private fun RV(list: java.util.ArrayList<CatpersResp>?) {
        callbackCatpers = object : AdapterCallback<CatpersResp> {
            override fun initComponent(itemView: View, data: CatpersResp, itemIndex: Int) {
                itemView.txt_nama_personel_catpers.text = data.personel_terlapor?.nama
                itemView.txt_jabatan_personel_catpers.text = data.personel_terlapor?.jabatan
                itemView.txt_kesatuan_personel_catpers.text =
                    data.personel_terlapor?.kesatuan.toString().toUpperCase()
                itemView.txt_nama_personel_catpers.text = data.personel_terlapor?.nama
                itemView.txt_pangkat_nrp_personel_catpers.text =
                    "Pangkat ${data.personel_terlapor?.pangkat.toString()
                        .toUpperCase()} / ${data.personel_terlapor?.nrp}"

                itemView.txt_no_lp_catpers.text = data.lp?.no_lp
                if (data.skhd?.id != null) {
                    itemView.txt_no_skhd_putkke_catpers.text = data.skhd?.no_skhd
                } else {
                    itemView.txt_no_skhd_putkke_catpers.text = data.putkke?.no_putkke
                }

                when (data?.status_kasus) {
                    "proses" -> { itemView.txt_status_kasus_catpers.text = "Proses" }
                    "dalam_masa_hukuman" -> { itemView.txt_status_kasus_catpers.text = "Dalam Masa Hukuman"}
                    "selesai" -> { itemView.txt_status_kasus_catpers.text = "Selesai"}
                }
                itemView.rv_pasal_catpers.apply {
                    layoutManager = LinearLayoutManager(
                        itemView.rv_pasal_catpers.context,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    adapter = PasalOnCatpersAdapter(data)
                    setRecycledViewPool(viewPool)
                }

            }

            override fun onItemClicked(itemView: View, data: CatpersResp, itemIndex: Int) {
                val intent = Intent(this@CatpersActivity, DetailCatpersActivity::class.java)
                intent.putExtra(DetailCatpersActivity.DETAIL_CATPERS, data)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }

        }
        list?.let {
            adapterCatpers.adapterCallback(callbackCatpers)
                .isVerticalView().filterable()
                .addData(it).setLayout(R.layout.item_catpers).build(rv_catpers)

        }
    }

}
