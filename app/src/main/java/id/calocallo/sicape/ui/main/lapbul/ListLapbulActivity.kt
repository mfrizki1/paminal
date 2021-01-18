package id.calocallo.sicape.ui.main.lapbul

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkDummy
import id.calocallo.sicape.network.response.LapBulResp
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_list_lapbul.*
import kotlinx.android.synthetic.main.item_catpers.view.*
import kotlinx.android.synthetic.main.item_lapbul.view.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import kotlinx.android.synthetic.main.view_no_data.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListLapbulActivity : BaseActivity() {
    private var adapterLapbul = ReusableAdapter<LapBulResp>(this)
    private lateinit var callbackLapbul: AdapterCallback<LapBulResp>
    private lateinit var mAlerDBuilder: MaterialAlertDialogBuilder
    private lateinit var filterAlertD: View
    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_lapbul)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "List Laporan Bulanan"

        getListLapbul()

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
                getMonth = parent.getItemAtPosition(position).toString()
            }
            mAlerDBuilder.setView(filterAlertD)
                .setTitle("Filter Bulan & Tahun")
                .setPositiveButton("Selesai") { dialog, _ ->
                    if (!edtTahunFilter.text.isNullOrBlank() && !getMonth.isNullOrBlank()) {
                        filterMonth(edtTahunFilter.text.toString(), getMonth)
                    } else if (!edtTahunFilter.text.isNullOrBlank()) {
                        filterYear(edtTahunFilter.text.toString())
                    }


                }
                .setNegativeButton("Batal") { dialog, _ -> }
                .show()
        }
    }

    private fun getListLapbul() {
        rl_pb.visible()
        NetworkDummy().getService().getLapbul().enqueue(object : Callback<ArrayList<LapBulResp>> {
            override fun onFailure(call: Call<ArrayList<LapBulResp>>, t: Throwable) {
                rl_pb.gone()
                rl_no_data.visible()
                rv_list_lapbul.gone()

            }

            override fun onResponse(
                call: Call<ArrayList<LapBulResp>>,
                response: Response<ArrayList<LapBulResp>>
            ) {
                if (response.isSuccessful) {
                    rl_pb.gone()
                    callbackLapbul = object : AdapterCallback<LapBulResp> {
                        @SuppressLint("SetTextI18n")
                        override fun initComponent(
                            itemView: View,
                            data: LapBulResp,
                            itemIndex: Int
                        ) {
                            itemView.txt_nama_personel_lapbul.text =
                                "Nama : ${data.personel_terlapor?.nama}"
                            itemView.txt_pangkat_nrp_personel_lapbul.text =
                                "Pangkat : ${data.personel_terlapor?.pangkat.toString()
                                    .toUpperCase()} / ${data?.personel_terlapor?.nrp}"
                            itemView.txt_jabatan_personel_lapbul.text =
                                "Jabatan : ${data?.personel_terlapor?.jabatan}"
                            itemView.txt_kesatuan_personel_lapbul.text =
                                "Kesatuan : ${data?.personel_terlapor?.kesatuan.toString()
                                    .toUpperCase()}"
                            itemView.txt_no_lp_lapbul.text = "No LP : ${data?.lp?.no_lp}"

                            if (data.skhd?.id != null) {
                                itemView.txt_no_skhd_putkke_lapbul.text =
                                    "No SKHD : ${data.skhd?.no_skhd}"
                            } else {
                                itemView.txt_no_skhd_putkke_lapbul.text =
                                    "No PUTKKE : ${data.putkke?.no_putkke}"
                            }
                            itemView.txt_no_rps_lapbul.text =
                                "No RPS : ${if (data.rps?.no_rps.isNullOrBlank()) "Tidak Ada" else data.rps?.no_rps}"
                            itemView.txt_no_rpph_lapbul.text =
                                "No RPPH : ${if (data.rpph?.no_rpph.isNullOrBlank()) "Tidak Ada" else data.rpph?.no_rpph}"
                            itemView.txt_no_sktb_lapbul.text =
                                "No SKTB : ${if (data.sktb?.no_sktb.isNullOrBlank()) "Tidak Ada" else data.sktb?.no_sktb}"
                            itemView.txt_no_sktt_lapbul.text =
                                "NO SKTT : ${if (data.sktt?.no_sktt.isNullOrBlank()) "Tidak Ada" else data.sktt?.no_sktt} "
                            itemView.txt_no_s4_lapbul.text =
                                "NO SP3 : ${if (data.sp4?.no_sp4.isNullOrBlank()) "Tidak Ada" else data.sp4?.no_sp4}"
                            itemView.txt_stts_kasus_lapbul.text =
                                "Status Kasus : ${data.status_kasus}"
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

                        override fun onItemClicked(
                            itemView: View,
                            data: LapBulResp,
                            itemIndex: Int
                        ) {
                        }
                    }
                    response.body()?.let {
                        adapterLapbul.adapterCallback(callbackLapbul)
                            .isVerticalView().filterable()
                            .addData(it)
                            .setLayout(R.layout.item_lapbul)
                            .build(rv_list_lapbul)
                    }
                } else {
                    rl_pb.gone()
                    rl_no_data.visible()
                    rv_list_lapbul.gone()
                }
            }
        })

    }

    private fun filterYear(year: String) {
        Log.e("year", year)
    }

    private fun filterMonth(year: String, month: String) {
        Log.e("month", "$year, ${month.toUpperCase()}")
    }
}
