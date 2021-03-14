package id.calocallo.sicape.ui.main.lapbul

import android.annotation.SuppressLint
import android.os.Bundle
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.FilterReq
import id.calocallo.sicape.network.response.CatpersLapbulResp
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.formatterTanggal
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_list_lapbul.*
import kotlinx.android.synthetic.main.item_catpers.view.*
import kotlinx.android.synthetic.main.item_lapbul.*
import kotlinx.android.synthetic.main.item_lapbul.view.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import kotlinx.android.synthetic.main.view_no_data.*
import kotlinx.android.synthetic.main.view_no_data.view.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListLapbulActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var adapterLapbul = ReusableAdapter<CatpersLapbulResp>(this)
    private lateinit var callbackLapbul: AdapterCallback<CatpersLapbulResp>
    private lateinit var mAlerDBuilder: MaterialAlertDialogBuilder
    private lateinit var filterAlertD: View
    private val viewPool = RecyclerView.RecycledViewPool()
    private val viewPool1 = RecyclerView.RecycledViewPool()
    private var filterReq = FilterReq()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_lapbul)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "List Laporan Bulanan"
        sessionManager1 = SessionManager1(this)
        filterReq.tahun = "2021"
//         filterReq.bulan = "01"*/
//        getListLapbul()
       /*init Lapbul*/
        apiLapbul()
        txt_tahun_bulan_filter_lapbul.text = "Tahun ${filterReq.tahun}"


        mAlerDBuilder = MaterialAlertDialogBuilder(this, R.style.AlertDialogTheme)
        btn_filter.setOnClickListener {
            filterAlertD = LayoutInflater.from(this)
                .inflate(R.layout.item_filter_year_month, null, false)

            val edtTahunFilter = filterAlertD.findViewById<TextInputEditText>(R.id.edt_tahun_filter)
            val spMonthFilter =
                filterAlertD.findViewById<AutoCompleteTextView>(R.id.spinner_month_filter)
            val adapterMonth =
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
            }
            mAlerDBuilder.setView(filterAlertD)
                .setTitle("Filter Bulan & Tahun")
                .setPositiveButton("Selesai") { dialog, _ ->
                    if (!edtTahunFilter.text.isNullOrBlank() && !getMonth.isNullOrBlank()) {
                        filterLapbul(edtTahunFilter.text.toString(), getMonth)
                    } else if (!edtTahunFilter.text.isNullOrBlank()) {
                        filterLapbul(edtTahunFilter.text.toString(), null)
                    }
                    /*else {
                        Toast.makeText(this, "Bulan Harus Diisi", Toast.LENGTH_SHORT).show()
                    }*/


                }
                .setNegativeButton("Batal") { dialog, _ -> }
                .show()
        }
    }

    private fun apiLapbul() {
        rl_pb.visible()
        NetworkConfig().getServCatpers()
            .getListLapbul("Bearer ${sessionManager1.fetchAuthToken()}", filterReq).enqueue(
                object : Callback<ArrayList<CatpersLapbulResp>> {
                    override fun onResponse(
                        call: Call<ArrayList<CatpersLapbulResp>>,
                        response: Response<ArrayList<CatpersLapbulResp>>
                    ) {
                        if (response.isSuccessful) {
                            txt_tahun_bulan_filter_lapbul.visible()
                            rl_pb.gone()

                            if (response.body()?.isEmpty() == true) {
                                rl_pb.gone()
                                rl_no_data.visible()
                                rv_list_lapbul.gone()
                            } else {
                                rv_list_lapbul.visible()
                                rl_no_data.gone()
                                listLapbul(response.body())
                            }
                        } else {
                            rl_pb.gone()
                            rl_no_data.visible()
                            rv_list_lapbul.gone()
                            Toast.makeText(
                                this@ListLapbulActivity,
                                R.string.error,
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }

                    override fun onFailure(
                        call: Call<ArrayList<CatpersLapbulResp>>,
                        t: Throwable
                    ) {
                        Toast.makeText(this@ListLapbulActivity, "$t", Toast.LENGTH_SHORT).show()
                        rl_pb.gone()
                        rl_no_data.visible()
                        rv_list_lapbul.gone()
                    }

                })
    }

    private fun listLapbul(list: ArrayList<CatpersLapbulResp>?) {
        callbackLapbul = object : AdapterCallback<CatpersLapbulResp> {
            @SuppressLint("SetTextI18n")
            override fun initComponent(
                itemView: View, data: CatpersLapbulResp, itemIndex: Int
            ) {
                val mHukuman = data.hukuman
                if (mHukuman != null) {
                    itemView.txt_hukuman_putusan_lapbul.text = "Hukuman\n"
                    for (i in 0 until mHukuman.size) {
                        Log.e("dataHuk", mHukuman[i])
                        itemView.txt_hukuman_putusan_lapbul.append("${mHukuman[i]}\n")
                    }
                } else {
                    itemView.txt_hukuman_putusan_lapbul.text = "Hukuman: Tidak Ada"

                }

                itemView.txt_nama_personel_lapbul.text = "Nama: ${data.nama}"

                itemView.txt_pangkat_nrp_personel_lapbul.text =
                    "Pangkat: ${data.pangkat.toString().toUpperCase()}/ NRP: ${data.nrp}"

                itemView.txt_jabatan_personel_lapbul.text = "Jabatan: ${data.jabatan}"
                itemView.txt_kesatuan_personel_lapbul.text = "Kesatuan: ${data.kesatuan}"

                itemView.txt_no_lp_lapbul.text =
                    "No LP: ${data.no_lp},\nTanggal: ${formatterTanggal(data.tanggal_buat_laporan)}"

                itemView.txt_uraian_pelanggaran_lapbul.text = data.uraian_pelanggaran
                if (data.tanggal_putusan == null) {
                    itemView.txt_no_skhd_putkke_lapbul.text = "Tidak Ada"
                } else {
                    itemView.txt_no_skhd_putkke_lapbul.text = "No: ${data.no_putusan},\n" +
                            "Tanggal: ${formatterTanggal(data.tanggal_putusan)}"
                }


                itemView.txt_stts_kasus_lapbul.text = "Keterangan: ${data.keterangan}"

                if (data.surat_rehab?.isEmpty()!!) {
                    itemView.txt_no_data_lapbul.visible()
                    itemView.rv_rehab_lapbul.gone()
                } else {
                    itemView.rv_rehab_lapbul.visible()
                    itemView.rv_rehab_lapbul.apply {
                        layoutManager = LinearLayoutManager(
                            itemView.rv_rehab_lapbul.context,
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                        adapter = SuratRehabAdapter(data)
                        setRecycledViewPool(viewPool1)
                    }
                }

                    if (data.pasal_dilanggar?.isEmpty()!!) {
                        itemView.txt_no_data_pasal_lapbul.visible()
                        itemView.rv_pasal_lapbul.gone()
                    } else {
                        itemView.rv_pasal_lapbul.apply {
                            layoutManager = LinearLayoutManager(
                                itemView.rv_pasal_lapbul.context,
                                LinearLayoutManager.VERTICAL,
                                false
                            )
                            adapter = PasalLapbulAdapter(data)
                            setRecycledViewPool(viewPool)
                        }
                    }
            }

            override fun onItemClicked(
                itemView: View,
                data: CatpersLapbulResp,
                itemIndex: Int
            ) {
                Toast.makeText(this@ListLapbulActivity, "KLICK", Toast.LENGTH_SHORT).show()
            }
        }
        list?.let {
            adapterLapbul.adapterCallback(callbackLapbul)
                .isVerticalView().filterable()
                .addData(it).setLayout(R.layout.item_lapbul).build(rv_list_lapbul)
        }
    }


    @SuppressLint("SetTextI18n")
    private fun filterLapbul(year: String, month: String?) {
        Log.e("month", "$year, ${month?.toUpperCase()}")
        filterReq.bulan = month
        filterReq.tahun = year
        apiLapbul()

        var bulan = ""
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
        }
        if (month == null) {
            txt_tahun_bulan_filter_lapbul.text = "Tahun ${filterReq.tahun}"
        } else {
            txt_tahun_bulan_filter_lapbul.text = "Tahun ${filterReq.tahun}, Bulan $bulan"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_bar, menu)
        val item = menu?.findItem(R.id.action_search)
        val searchView = item?.actionView as SearchView
        searchView.queryHint = "Cari Data Lapbul"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterLapbul.filter.filter(newText)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }
}
