package id.calocallo.sicape.ui.main.catpers

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonElement
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.NetworkDummy
import id.calocallo.sicape.network.request.FilterReq
import id.calocallo.sicape.network.response.CatpersLapbulResp
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.formatterTanggal
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_catpres.*
import kotlinx.android.synthetic.main.activity_detail_catpers.*
import kotlinx.android.synthetic.main.item_catpers.view.*
import kotlinx.android.synthetic.main.item_lapbul.view.*
import kotlinx.android.synthetic.main.item_lp_pidana.view.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import kotlinx.android.synthetic.main.view_no_data.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CatpersActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var adapterCatpers = ReusableAdapter<CatpersLapbulResp>(this)
    private var filterReq = FilterReq()
    private lateinit var callbackCatpers: AdapterCallback<CatpersLapbulResp>
    private val viewPool = RecyclerView.RecycledViewPool()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catpres)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "List Data Catatan Personel"
        sessionManager1 = SessionManager1(this)
        filterReq.tahun = "2020"
        apiListCatpers()
//        getListCatpers()
        btn_filter_catpers.setOnClickListener {
            filterReq.tahun = edt_tahun_catpers.text.toString()
            apiListCatpers()

        }
    }

    private fun apiListCatpers() {
        rl_pb.visible()
        NetworkConfig().getServCatpers().getListCatpers(
            "Bearer ${sessionManager1.fetchAuthToken()}", filterReq
        ).enqueue(
            object : Callback<ArrayList<CatpersLapbulResp>> {
                override fun onResponse(
                    call: Call<ArrayList<CatpersLapbulResp>>,
                    response: Response<ArrayList<CatpersLapbulResp>>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.isEmpty() == true) {
                            rl_pb.gone()
                            rl_no_data.visible()
                            rv_catpers.gone()
                        } else {
                            rl_pb.gone()
                            RV(response.body())
                        }

                    } else {
                        rl_pb.gone()
                        rl_no_data.visible()
                        rv_catpers.gone()
                        Toast.makeText(this@CatpersActivity, R.string.error, Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<ArrayList<CatpersLapbulResp>>, t: Throwable) {
                    rl_pb.gone()
                    rl_no_data.visible()
                    rv_catpers.gone()
                    Log.e("t", "$t")
                    Toast.makeText(this@CatpersActivity, "$t", Toast.LENGTH_SHORT).show()
                }

            })
    }

    /*  private fun getListCatpers() {
          rl_pb.visible()
          NetworkDummy().getService().getCatpers()
              .enqueue(object : Callback<ArrayList<CatpersLapbulResp>> {
                  override fun onFailure(call: Call<ArrayList<CatpersLapbulResp>>, t: Throwable) {
                      rl_no_data.visible()
                      rl_pb.gone()
                      rv_catpers.gone()
                  }

                  override fun onResponse(
                      call: Call<ArrayList<CatpersLapbulResp>>,
                      response: Response<ArrayList<CatpersLapbulResp>>
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
      }*/

    private fun RV(list: java.util.ArrayList<CatpersLapbulResp>?) {
        edt_tahun_catpers.setText(filterReq.tahun)
        callbackCatpers = object : AdapterCallback<CatpersLapbulResp> {
            @SuppressLint("SetTextI18n")
            override fun initComponent(itemView: View, data: CatpersLapbulResp, itemIndex: Int) {
                itemView.txt_nama_personel_catpers.text = "Nama: ${data.nama}"
                itemView.txt_jabatan_personel_catpers.text = "Jabatan: ${data.jabatan}"
                itemView.txt_pangkat_nrp_personel_catpers.text =
                    "Pangkat ${data.pangkat.toString().toUpperCase()} / ${data.nrp}"
                itemView.txt_kesatuan_personel_catpers.text =
                    "Kesatuan: ${data.kesatuan.toString().toUpperCase()}"
                itemView.txt_no_lp_catpers.text =
                    "No: ${data.no_lp}, Tanggal ${formatterTanggal(data.tanggal_buat_laporan)}"
                itemView.txt_uraian_pelanggaran_catper.text = data.uraian_pelanggaran
                itemView.txt_no_skhd_putkke_catpers.text =
                    "No ${data.putusan_hukuman?.surat} ${data.putusan_hukuman?.no_surat}, Tanggal ${
                        formatterTanggal(data.putusan_hukuman?.tanggal)
                    }"

                val mHukuman = data.putusan_hukuman?.hukuman
                if (mHukuman != null) {
                    itemView.txt_hukuman_catpers.text = "Hukuman\n"
                    for (i in 0 until mHukuman.size) {
                        Log.e("dataHuk", mHukuman[i])
                        itemView.txt_hukuman_catpers.append("${mHukuman[i]}\n")
                    }
                }
                itemView.txt_status_kasus_catpers.text = data.putusan_hukuman?.status
                itemView.rv_pasal_catpers.apply {
                    layoutManager = LinearLayoutManager(
                        itemView.rv_pasal_catpers.context,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    adapter = PasalOnCatpersAdapter(data)
                    setRecycledViewPool(viewPool)
                }

                /* itemView.txt_nama_personel_catpers.text = data.personel_terlapor?.nama
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
                 }*/

            }

            override fun onItemClicked(
                itemView: View,
                data: CatpersLapbulResp,
                itemIndex: Int
            ) {
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_bar, menu)
        val item = menu?.findItem(R.id.action_search)
        val searchView = item?.actionView as SearchView
        searchView.queryHint = "Cari Catpers"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterCatpers.filter.filter(newText)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }
}
