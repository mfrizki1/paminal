package id.calocallo.sicape.ui.main.catpers

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.FilterReq
import id.calocallo.sicape.network.response.Base1Resp
import id.calocallo.sicape.network.response.CatpersLapbulResp
import id.calocallo.sicape.network.response.DokCatpersResp
import id.calocallo.sicape.network.response.DokResp
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.*
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_catpres.*
import kotlinx.android.synthetic.main.activity_detail_catpers.*
import kotlinx.android.synthetic.main.activity_detail_rps.*
import kotlinx.android.synthetic.main.activity_list_lapbul.*
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
    private lateinit var mAlerDBuilder: MaterialAlertDialogBuilder
    private lateinit var filterAlertD: View
    private val viewPool = RecyclerView.RecycledViewPool()
    private lateinit var downloadID: Any
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catpres)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "List Data Catatan Personel"
        sessionManager1 = SessionManager1(this)
        registerReceiver(onDownloadComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

        filterReq.tahun = "2021"
        txt_tahun_bulan_filter_catpers.text = "Tahun 2021"
        apiListCatpers()
//        getListCatpers()
        mAlerDBuilder = MaterialAlertDialogBuilder(this, R.style.AlertDialogTheme)
        btn_filter_catpers.setOnClickListener {
            filterAlertD = LayoutInflater.from(this)
                .inflate(R.layout.item_filter_year_month, null, false)

            val edtTahunFilter = filterAlertD.findViewById<TextInputEditText>(R.id.edt_tahun_filter)
            val spMonthFilter =
                filterAlertD.findViewById<TextInputLayout>(R.id.text_layout_bulan)
            spMonthFilter.gone()
            /*  val adapterMonth =
                  ArrayAdapter(this, R.layout.item_spinner, resources.getStringArray(R.array.month))
              var getMonth = ""
              spMonthFilter.setAdapter(adapterMonth)
              spMonthFilter.setOnItemClickListener { parent, view, position, id ->
                  when (position) {
                      0 -> getMonth = "01"
                      1 -> getMonth = "02"
                      2 -> getMonth = "03"
                      3 -> getMonth = "04"
                      4 -> getMonth = "05"
                      5 -> getMonth = "06"
                      6 -> getMonth = "07"
                      7 -> getMonth = "08"
                      8 -> getMonth = "09"
                      9 -> getMonth = "10"
                      10 -> getMonth = "11"
                      11 -> getMonth = "12"
                  }
              }*/
            mAlerDBuilder.setView(filterAlertD)
                .setTitle("Filter Bulan & Tahun")
                .setPositiveButton("Selesai") { dialog, _ ->
                    if (!edtTahunFilter.text.isNullOrBlank()) {
                        filterCatpers(edtTahunFilter.text.toString())
                    } else {
                        Toast.makeText(this, "Tahun Harus Diisi", Toast.LENGTH_SHORT).show()
                    }
                    /*  if (!edtTahunFilter.text.isNullOrBlank() && !getMonth.isNullOrBlank()) {
                          filterCatpers(edtTahunFilter.text.toString(), getMonth)
                      } else if (!edtTahunFilter.text.isNullOrBlank()) {
                          filterCatpers(edtTahunFilter.text.toString(), null)
                      }*/
                    /*else {
                        Toast.makeText(this, "Bulan Harus Diisi", Toast.LENGTH_SHORT).show()
                    }*/


                }
                .setNegativeButton("Batal") { dialog, _ -> }
                .show()

            /* filterReq.tahun = edt_tahun_catpers.text.toString()
             apiListCatpers()
             rv_catpers.gone()*/

        }
        btn_generate_catpers.attachTextChangeAnimator()
        bindProgressButton(btn_generate_catpers)
        btn_generate_catpers.setOnClickListener {
            btn_generate_catpers.showProgress {
                progressColor = Color.WHITE
            }
            apiDocCatpers()
        }
    }

    private fun apiDocCatpers() {
        NetworkConfig().getServCatpers()
            .dokCatpers("Bearer ${sessionManager1.fetchAuthToken()}", filterReq).enqueue(
                object : Callback<Base1Resp<DokCatpersResp>> {
                    override fun onResponse(
                        call: Call<Base1Resp<DokCatpersResp>>,
                        response: Response<Base1Resp<DokCatpersResp>>
                    ) {
                        if (response.body()?.message == "Document catpers generated successfully") {
                            btn_generate_catpers.hideProgress(R.string.success_generate_doc)
                            alert(R.string.download) {
                                positiveButton(R.string.iya) {
                                    downloadCatpers(response.body()?.data?.catpers)
                                }
                                negativeButton(R.string.tidak) {
                                    btn_generate_catpers.hideProgress(R.string.generate_dokumen)
                                }
                            }.show()
                        } else {
                            btn_generate_catpers.hideProgress(R.string.failed_generate_doc)
                        }
                    }

                    override fun onFailure(call: Call<Base1Resp<DokCatpersResp>>, t: Throwable) {
                        Toast.makeText(this@CatpersActivity, "$t", Toast.LENGTH_SHORT).show()
                        btn_generate_catpers.hideProgress(R.string.failed_generate_doc)
                    }
                })
    }

    private fun downloadCatpers(catpers: DokResp?) {
        val url = catpers?.url
        val filename: String = "Catpers-${filterReq.tahun}.${catpers?.jenis}"
        val request: DownloadManager.Request = DownloadManager.Request(Uri.parse(url))
            .setTitle(filename)
            .setDescription("Downloading")
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename)

        val manager: DownloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadID = manager.enqueue(request)
    }
    private val onDownloadComplete: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val completedId = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            if (completedId == downloadID) {
                btn_generate_catpers.hideProgress(R.string.generate_dokumen)
                btn_generate_catpers.showSnackbar(R.string.success_download_doc) { action(R.string.action_ok) {
                    btn_generate_catpers.hideProgress(R.string.generate_dokumen)
                } }
            }
        }
    }

    private fun filterCatpers(year: String?) {
        filterReq.tahun = year
        apiListCatpers()

        /*   var bulan :String? = null
           when (month) {
               "01" -> bulan = "Januari"
               "02" -> bulan = "Februari"
               "03" -> bulan = "Maret"
               "04" -> bulan = "April"
               "05" -> bulan = "Mei"
               "06" -> bulan = "Juni"
               "07" -> bulan = "Juli"
               "08" -> bulan = "Agustus"
               "09" -> bulan = "September"
               "10" -> bulan = "Oktober"
               "11" -> bulan = "November"
               "12" -> bulan = "Desember"
               else-> bulan = null
           }*/

        /* if (bulan == null) {
             txt_tahun_bulan_filter_catpers.text = "Tahun ${filterReq.tahun}"
         } else {*/
        txt_tahun_bulan_filter_catpers.text = "Tahun ${filterReq.tahun}"
//        }
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
                            rv_catpers.visible()
                            rl_no_data.gone()
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
//        edt_tahun_catpers.setText(filterReq.tahun)
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
                if (data.putusan_hukuman?.status == null) {
                    itemView.txt_no_skhd_putkke_catpers.text = "Tidak Ada"
                } else {
                    itemView.txt_no_skhd_putkke_catpers.text =
                        "No ${data.putusan_hukuman?.surat} ${data.putusan_hukuman?.no_surat}, Tanggal ${
                            formatterTanggal(data.putusan_hukuman?.tanggal)
                        }"
                }
                val mHukuman = data.putusan_hukuman?.hukuman
                if (mHukuman?.isNotEmpty() == true) {
                    itemView.txt_hukuman_catpers.text = "Hukuman\n"
                    for (i in 0 until mHukuman.size) {
                        Log.e("dataHuk", mHukuman[i])
                        itemView.txt_hukuman_catpers.append("${mHukuman[i]}\n")
                    }
                } else {
                    itemView.txt_hukuman_catpers.gone()
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
