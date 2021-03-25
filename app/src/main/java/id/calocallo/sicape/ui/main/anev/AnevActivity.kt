package id.calocallo.sicape.ui.main.anev

import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.core.content.ContextCompat
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.chart.common.listener.Event
import com.anychart.chart.common.listener.ListenersInterface
import com.anychart.charts.Pie
import com.anychart.enums.Align
import com.anychart.enums.LegendLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.NetworkDummy
import id.calocallo.sicape.network.request.AnevReq
import id.calocallo.sicape.network.request.FilterReq
import id.calocallo.sicape.network.response.*
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.calocallo.sicape.ui.base.BaseActivity
import id.calocallo.sicape.utils.ext.toast
import kotlinx.android.synthetic.main.activity_anev.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import kotlinx.android.synthetic.main.view_no_data.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class AnevActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var filterJenis: String? = null
    private var filterRentang: String? = null
    private var filterSmstr: String? = null
    private var filterBulan: String? = null
    private var filterJenisKesatuan: String? = null
    private var filterReq = FilterReq()
    private lateinit var pie: Pie
    private var anevReq = AnevReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anev)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Analisa & Evaluasi"
        sessionManager1 = SessionManager1(this)
        pie = AnyChart.pie()
        pie.animation(true)
        btn_filter_anev.setOnClickListener {
            filterView()
        }

        default()


    }

    private fun default() {
        filterReq.tahun = "2021"
        filterReq.filter = "kesatuan"
        filterReq.rentang = "full"
        rl_pb.visible()
        NetworkConfig().getServCatpers()
            .getListAnev("Bearer ${sessionManager1.fetchAuthToken()}", filterReq)
            .enqueue(object : Callback<BaseAnev> {
                override fun onFailure(call: Call<BaseAnev>, t: Throwable) {
                    rl_pb.gone()
                    rl_no_data.visible()
                }

                override fun onResponse(
                    call: Call<BaseAnev>,
                    response: Response<BaseAnev>
                ) {
                    if (response.isSuccessful) {
                        rl_pb.gone()
                        defaultPie(response.body())
//                   tesPie()
                    } else {
                        rl_pb.gone()
                        rl_no_data.visible()
                    }
                }
            })
    }

    private fun filterView() {
        val layoutFilter = layoutInflater.inflate(R.layout.layout_filter_anev, null)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(layoutFilter)
        dialog.show()

        val layoutJenisKesatuan =
            layoutFilter.findViewById<LinearLayout>(R.id.ll_filter_jenis_kesatuan)
        val btnPoldaFilter = layoutFilter.findViewById<MaterialButton>(R.id.btn_polda_filter)
        val btnPolresFilter = layoutFilter.findViewById<MaterialButton>(R.id.btn_polres_filter)
        val btnPolsekFilter = layoutFilter.findViewById<MaterialButton>(R.id.btn_polsek_filter)

        val btnKesatuan = layoutFilter.findViewById<MaterialButton>(R.id.btn_kesatuan_filter)
        val btnPangkat = layoutFilter.findViewById<MaterialButton>(R.id.btn_pangkat_filter)
        val btnJenis = layoutFilter.findViewById<MaterialButton>(R.id.btn_jenis_pelanggaran_filter)
        val btnFullTahun = layoutFilter.findViewById<MaterialButton>(R.id.btn_full_year)
        val btn6Month = layoutFilter.findViewById<MaterialButton>(R.id.btn_6_month)
        val btn1Month = layoutFilter.findViewById<MaterialButton>(R.id.btn_1_month)
        val edtYear = layoutFilter.findViewById<TextInputEditText>(R.id.edt_year_anev)
        val llRentang = layoutFilter.findViewById<LinearLayout>(R.id.ll_rentang)
        val ll6Bln = layoutFilter.findViewById<LinearLayout>(R.id.ll_6_bln)
        val ll1Bln = layoutFilter.findViewById<LinearLayout>(R.id.ll_1_bln)
        val btnSmstrAwal = layoutFilter.findViewById<Button>(R.id.btn_smstr_awal)
        val btnSmstrAkhir = layoutFilter.findViewById<Button>(R.id.btn_smstr_akhir)
        val spinnerBulan = layoutFilter.findViewById<AutoCompleteTextView>(R.id.spinner_bulan_anev)
        val btnFilter = layoutFilter.findViewById<MaterialButton>(R.id.btn_save_filter)

        /*filter jenis*/
        btnKesatuan.setOnClickListener {
            btnKesatuan.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
            btnKesatuan.setTextColor(ContextCompat.getColor(this, R.color.white))

            btnPangkat.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            btnPangkat.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))

            btnJenis.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            btnJenis.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
            filterJenis = "kesatuan"
            layoutJenisKesatuan.visible()
        }
        btnPoldaFilter.setOnClickListener {
            btnPoldaFilter.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
            btnPoldaFilter.setTextColor(ContextCompat.getColor(this, R.color.white))
            btnPolresFilter.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            btnPolresFilter.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
            btnPolsekFilter.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            btnPolsekFilter.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
            filterJenisKesatuan = "subsatker_polda"
        }
        btnPolresFilter.setOnClickListener {
            btnPolresFilter.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
            btnPolresFilter.setTextColor(ContextCompat.getColor(this, R.color.white))
            btnPoldaFilter.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            btnPoldaFilter.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
            btnPolsekFilter.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            btnPolsekFilter.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
            filterJenisKesatuan = "polres"
        }
        btnPolsekFilter.setOnClickListener {
            btnPolsekFilter.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
            btnPolsekFilter.setTextColor(ContextCompat.getColor(this, R.color.white))
            btnPoldaFilter.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            btnPoldaFilter.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
            btnPolresFilter.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            btnPolresFilter.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
            filterJenisKesatuan = "polsek"
        }
        btnPangkat.setOnClickListener {
            btnPangkat.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
            btnPangkat.setTextColor(ContextCompat.getColor(this, R.color.white))

            btnKesatuan.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            btnKesatuan.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))

            btnJenis.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            btnJenis.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
            filterJenis = "pangkat"
            layoutJenisKesatuan.gone()

        }

        btnJenis.setOnClickListener {
            btnJenis.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
            btnJenis.setTextColor(ContextCompat.getColor(this, R.color.white))

            btnKesatuan.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            btnKesatuan.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))

            btnPangkat.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            btnPangkat.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
            filterJenis = "jenis_pelanggaran"
            layoutJenisKesatuan.gone()
        }

        /*filter rentang*/
        btn1Month.setOnClickListener {
            btn1Month.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
            btn1Month.setTextColor(ContextCompat.getColor(this, R.color.white))

            btn6Month.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            btn6Month.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))

            btnFullTahun.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            btnFullTahun.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
            filterRentang = "bulan"
            ll1Bln.visible()
            ll6Bln.gone()
        }
        btn6Month.setOnClickListener {
            btn6Month.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
            btn6Month.setTextColor(ContextCompat.getColor(this, R.color.white))

            btn1Month.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            btn1Month.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))

            btnFullTahun.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            btnFullTahun.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
//            filterRentang = "semester"
            ll6Bln.visible()
            ll1Bln.gone()

        }
        btnFullTahun.setOnClickListener {
            btnFullTahun.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
            btnFullTahun.setTextColor(ContextCompat.getColor(this, R.color.white))

            btn1Month.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            btn1Month.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))

            btn6Month.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            btn6Month.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
            filterRentang = "full"
            ll6Bln.gone()
            ll1Bln.gone()
        }

        /*filter semester*/
        btnSmstrAwal.setOnClickListener {
            btnSmstrAwal.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
            btnSmstrAwal.setTextColor(ContextCompat.getColor(this, R.color.white))

            btnSmstrAkhir.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            btnSmstrAkhir.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
            filterRentang = "semester_1"
        }
        btnSmstrAkhir.setOnClickListener {
            btnSmstrAkhir.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
            btnSmstrAkhir.setTextColor(ContextCompat.getColor(this, R.color.white))

            btnSmstrAwal.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            btnSmstrAwal.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
            filterRentang = "semester_2"
        }

        /*filter 1 bulan*/
        val adapter =
            ArrayAdapter(this, R.layout.item_spinner, resources.getStringArray(R.array.month))
        spinnerBulan.setAdapter(adapter)
        spinnerBulan.setOnItemClickListener { parent, view, position, id ->
            when(position){
                0-> filterBulan = "01"
                1-> filterBulan = "02"
                2-> filterBulan = "03"
                3-> filterBulan = "04"
                4-> filterBulan = "05"
                5-> filterBulan = "06"
                6-> filterBulan = "07"
                7-> filterBulan = "08"
                8-> filterBulan = "09"
                9-> filterBulan = "10"
                10-> filterBulan = "11"
                11-> filterBulan = "12"
            }
        }

        btnFilter.setOnClickListener {
            Log.e("jenisOnButton", "$filterJenis")
            Log.e("rentangOnButton", "$filterRentang")
            Log.e("tahunOnButton", edtYear.text.toString())
            Log.e("semesterOnButton", "$filterSmstr")
            Log.e("bulanOnButton", "${filterBulan.toString().toLowerCase()}")
            when {
                filterJenis.isNullOrBlank() -> {
                    Toast.makeText(
                        this,
                        "Harus Pilih Salah Satu Diantara Jenis Filter",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                edtYear.text.isNullOrBlank() -> {
                    Toast.makeText(this, "Tahun Tidak Boleh Kosong", Toast.LENGTH_SHORT).show()
                }
                filterRentang.isNullOrEmpty()->{
                    toast("Piih Salah Satu dari Rentang Waktu")
                }
                else -> {

                    doFilterPie(
                        filterJenis, filterRentang,
                        edtYear.text.toString(), filterSmstr, filterBulan.toString().toLowerCase(),
                        filterJenisKesatuan
                    )
                    dialog.dismiss()
                    filterJenis = null
                    filterRentang = null
                    filterSmstr = null
                    filterBulan = null
                    edtYear.text = null
                }
            }
        }


    }

    private fun doFilterPie(
        filter: String?, rentang: String?, tahun: String?,
        semester: String?, bulan: String?, jenisKesatuan: String?
    ) {
//        Log.e("semesterFilter", "$semester")
//        Log.e("tahunFilter", "$tahun")
        Log.e("jenisKesatuan", "$jenisKesatuan")
        filterReq.filter = filter
        filterReq.rentang = rentang
        filterReq.tahun = tahun
        filterReq.jenis = jenisKesatuan
//        filterReq.semester = semester
        filterReq.bulan = bulan
        filter?.let { apiFilter(filterReq, it) }

        /* when (filter) {
             "kesatuan" -> {
                 Log.e("ini kesatuan", filter)
                 apiFilter(filterReq, filter)
             }
             "pangkat" -> {
                 Log.e("ini pangkat", filter)
                 filterPangkat(filterRentang)
             }
             "pelanggaran" -> {
                 Log.e("ini pelanggaran", filter)
                 filterPelanggaran(anevReq)
 //                tesPie()
             }
         }*/
    }

    private fun filterPelanggaran(req: AnevReq) {
        rl_pb.visible()
        NetworkDummy().getService().getFilterPelanggaran()
            .enqueue(object : Callback<ArrayList<FilterPelanggaranResp>> {
                override fun onFailure(call: Call<ArrayList<FilterPelanggaranResp>>, t: Throwable) {
                    rl_pb.gone()
                    rl_no_data.visible()
                }

                override fun onResponse(
                    call: Call<ArrayList<FilterPelanggaranResp>>,
                    response: Response<ArrayList<FilterPelanggaranResp>>
                ) {
                    if (response.isSuccessful) {
                        rl_pb.gone()
                        pie_anev.clear()
                        val pelanggaran = arrayListOf<DataEntry>()
                        for (i in 0 until response.body()?.size!!) {
                            pelanggaran.add(
                                ValueDataEntry(
                                    response.body()!![i].jenis_pelanggaran.toString().toUpperCase(
                                        Locale.ROOT),
                                    response.body()!![i].jumlah_kasus
                                )
                            )
                        }
                        pie.data(pelanggaran)
                        pie.title("Jenis Pelanggaran")
                        pie.labels().position("outside")
                        pie.title().fontColor("#101010").fontSize(18).align(Align.LEFT)
                        when {
                            req.tahun != null -> {
                                when {
                                    req.rentang == "tahun" -> {
                                        pie.legend().title().enabled(true)
                                        pie.legend().title()
                                            .text("Tahun ${req.tahun}").fontSize(16)
                                            .padding(0.0, 0.0, 10.0, 0.0)
                                            .fontColor("#101010")
//                        pie_anev.setChart(pie)
                                    }
                                    req.semester != null -> {
                                        pie.legend().title().enabled(true)
                                        pie.legend().title()
                                            .text("Tahun ${req.tahun}, Semester ${req.semester}")
                                            .fontSize(16)
                                            .padding(0.0, 0.0, 10.0, 0.0)
                                            .fontColor("#101010")
//                        pie_anev.setChart(pie)
                                    }
                                    req.rentang == "bulan" -> {
                                        pie.legend().title().enabled(true)
                                        pie.legend().title()
                                            .text("Tahun ${req.tahun}, Bulan ${req.bulan}")
                                            .fontSize(16)
                                            .padding(0.0, 0.0, 10.0, 0.0)
                                            .fontColor("#101010")
//                        pie_anev.setChart(pie)
                                    }
                                }
                            }

                        }

                        pie_anev.setChart(pie)

                    } else {
                        rl_pb.gone()
                        rl_no_data.visible()
                    }
                }
            })

    }

    private fun filterPangkat(req: AnevReq) {
        rl_pb.visible()
        NetworkDummy().getService().getFilterPangkat()
            .enqueue(object : Callback<ArrayList<FilterPangkatResp>> {
                override fun onFailure(call: Call<ArrayList<FilterPangkatResp>>, t: Throwable) {
                    rl_pb.gone()
                }

                override fun onResponse(
                    call: Call<ArrayList<FilterPangkatResp>>,
                    response: Response<ArrayList<FilterPangkatResp>>
                ) {
                    if (response.isSuccessful) {
                        rl_pb.gone()
                        pie_anev.clear()
                        val pangkat = arrayListOf<DataEntry>()
                        for (i in 0 until response.body()?.size!!) {
                            pangkat.add(
                                ValueDataEntry(
                                    response.body()!![i].pangkat,
                                    response.body()!![i].jumlah_kasus
                                )
                            )
                        }
                        pie.data(pangkat)
                        pie.title("Pangkat")
                        pie.labels().position("outside")
                        pie.title().fontColor("#101010").fontSize(18).align(Align.LEFT)
                        when {
                            req.tahun != null -> {
                                when {
                                    req.rentang == "tahun" -> {
                                        pie.legend().title().enabled(true)
                                        pie.legend().title()
                                            .text("Tahun ${req.tahun}").fontSize(16)
                                            .padding(0.0, 0.0, 10.0, 0.0)
                                            .fontColor("#101010")
//                        pie_anev.setChart(pie)
                                    }
                                    req.semester != null -> {
                                        pie.legend().title().enabled(true)
                                        pie.legend().title()
                                            .text("Tahun ${req.tahun}, Semester ${req.semester}")
                                            .fontSize(16)
                                            .padding(0.0, 0.0, 10.0, 0.0)
                                            .fontColor("#101010")
//                        pie_anev.setChart(pie)
                                    }
                                    req.rentang == "bulan" -> {
                                        pie.legend().title().enabled(true)
                                        pie.legend().title()
                                            .text("Tahun ${req.tahun}, Bulan ${req.bulan}")
                                            .fontSize(16)
                                            .padding(0.0, 0.0, 10.0, 0.0)
                                            .fontColor("#101010")
//                        pie_anev.setChart(pie)
                                    }
                                }
                            }
                        }
                        pie_anev.setChart(pie)

                    } else {
                        rl_no_data.visible()
                        rl_pb.gone()
                    }

                }
            })


    }

    private fun apiFilter(req: FilterReq, filter: String) {
        rl_pb.visible()
        NetworkConfig().getServCatpers()
            .getListAnev("Bearer ${sessionManager1.fetchAuthToken()}", filterReq)
            .enqueue(object : Callback<BaseAnev> {
                override fun onFailure(call: Call<BaseAnev>, t: Throwable) {
                    toast("$t")
                    rl_pb.gone()
                    rl_no_data.visible()
                }

                override fun onResponse(
                    call: Call<BaseAnev>,
                    response: Response<BaseAnev>
                ) {
                    if (response.isSuccessful) {
                        if(response.body()?.grand_total == 0){
                            Log.e("null", "NULL")
                            rl_pb.gone()
                            rl_no_data.visible()
                            pie_anev.clear()
                            pie_anev.gone()
                        }else{
                            rl_pb.gone()
                            rl_no_data.gone()
                            pie_anev.visible()
                            pie_anev.clear()
                            val data = response.body()
                            when (filter) {
                                "kesatuan" -> pieKesatuan(data, req)
                                "pangkat" -> piePangkat(data, req)
                                "jenis_pelanggaran" -> piePelanggaran(data, req)
                            }
                        }

                    } else {
                        rl_pb.gone()
                        rl_no_data.visible()
                        pie_anev.gone()
                        pie_anev.clear()
                    }
                }
            })

/*
        val data: MutableList<DataEntry> = ArrayList()
        data.add(ValueDataEntry("Apples", 6371664))
        data.add(ValueDataEntry("Pears", 789622))
        data.add(ValueDataEntry("Bananas", 7216301))
        data.add(ValueDataEntry("Grapes", 1486621))
        data.add(ValueDataEntry("Oranges", 1200000))
 */
    }

    private fun piePelanggaran(anevResp: BaseAnev?, req: FilterReq) {
        convertMonth(req)
        val totalKasus = arrayListOf<DataEntry>()
        for (i in 0 until anevResp?.data?.size!!) {
            totalKasus.add(
                ValueDataEntry(
                    anevResp.data?.get(i)?.jenis_pelanggaran.toString().toUpperCase(),
                    anevResp.data?.get(i)?.total
                )
            )
        }
        pie.data(totalKasus)
        pie.setOnClickListener(object :
            ListenersInterface.OnClickListener(arrayOf("x", "value")) {
            override fun onClick(event: Event) {
                Toast.makeText(
                    this@AnevActivity,
                    event.data["x"].toString() + ":" + event.data["value"],
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        /*pangkat*/
        pie.title("Statistika Analisis Pelanggaran")
        pie.labels().position("outside")
        pie.title().fontColor("#101010").fontSize(18).align(Align.LEFT)
        when {
            req.tahun != null -> {
                when {
                    req.rentang == "tahun" -> {
                        pie.legend().title().enabled(true)
                        pie.legend().title()
                            .text("Tahun ${req.tahun}").fontSize(16)
                            .padding(0.0, 0.0, 10.0, 0.0)
                            .fontColor("#101010")
//                        pie_anev.setChart(pie)
                    }
                    /*  req.semester != null -> {
                          pie.legend().title().enabled(true)
                          pie.legend().title()
                              .text("Tahun ${req.tahun}, Semester ${req.semester}")
                              .fontSize(16)
                              .padding(0.0, 0.0, 10.0, 0.0)
                              .fontColor("#101010")
//                        pie_anev.setChart(pie)*/
                    req.rentang == "bulan" -> {
                        pie.legend().title().enabled(true)
                        pie.legend().title()
                            .text("Tahun ${req.tahun}, Bulan ${convertMonth(req)}")
                            .fontSize(16)
                            .padding(0.0, 0.0, 10.0, 0.0)
                            .fontColor("#101010")
                    }
                    req.rentang == "semester_1" -> {
                        pie.legend().title().enabled(true)
                        pie.legend().title()
                            .text("Tahun ${req.tahun}, Semester Awal")
                            .fontSize(16)
                            .padding(0.0, 0.0, 10.0, 0.0)
                            .fontColor("#101010")
                    }
                    req.rentang == "semester_2" -> {
                        pie.legend().title().enabled(true)
                        pie.legend().title()
                            .text("Tahun ${req.tahun}, Semester Akhir")
                            .fontSize(16)
                            .padding(0.0, 0.0, 10.0, 0.0)
                            .fontColor("#101010")
                    }
                }
            }

        }
        pie_anev.setChart(pie)

    }

    private fun piePangkat(anevResp: BaseAnev?, req: FilterReq) {
        val totalKasus = arrayListOf<DataEntry>()
        for (i in 0 until anevResp?.data?.size!!) {
            totalKasus.add(
                ValueDataEntry(
                    anevResp.data?.get(i)?.pangkat,
                    anevResp.data?.get(i)?.total
                )
            )
        }
        pie.data(totalKasus)
        pie.setOnClickListener(object :
            ListenersInterface.OnClickListener(arrayOf("x", "value")) {
            override fun onClick(event: Event) {
                Toast.makeText(
                    this@AnevActivity,
                    event.data["x"].toString() + ":" + event.data["value"],
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        /*pangkat*/
        pie.title("Statistika Analisis Pangkat")
        pie.labels().position("outside")
        pie.title().fontColor("#101010").fontSize(18).align(Align.LEFT)
        when {
            req.tahun != null -> {
                when {
                    req.rentang == "tahun" -> {
                        pie.legend().title().enabled(true)
                        pie.legend().title()
                            .text("Tahun ${req.tahun}").fontSize(16)
                            .padding(0.0, 0.0, 10.0, 0.0)
                            .fontColor("#101010")
//                        pie_anev.setChart(pie)
                    }
                    /*  req.semester != null -> {
                          pie.legend().title().enabled(true)
                          pie.legend().title()
                              .text("Tahun ${req.tahun}, Semester ${req.semester}")
                              .fontSize(16)
                              .padding(0.0, 0.0, 10.0, 0.0)
                              .fontColor("#101010")
//                        pie_anev.setChart(pie)*/
                    req.rentang == "bulan" -> {
                        pie.legend().title().enabled(true)
                        pie.legend().title()
                            .text("Tahun ${req.tahun}, Bulan ${convertMonth(req)}")
                            .fontSize(16)
                            .padding(0.0, 0.0, 10.0, 0.0)
                            .fontColor("#101010")
                    }
                    req.rentang == "semester_1" -> {
                        pie.legend().title().enabled(true)
                        pie.legend().title()
                            .text("Tahun ${req.tahun}, Semester Awal")
                            .fontSize(16)
                            .padding(0.0, 0.0, 10.0, 0.0)
                            .fontColor("#101010")
                    }
                    req.rentang == "semester_2" -> {
                        pie.legend().title().enabled(true)
                        pie.legend().title()
                            .text("Tahun ${req.tahun}, Semester Akhir")
                            .fontSize(16)
                            .padding(0.0, 0.0, 10.0, 0.0)
                            .fontColor("#101010")
                    }
                }
            }

        }
        pie_anev.setChart(pie)


    }

    private fun pieKesatuan(anevResp: BaseAnev?, req: FilterReq) {
        val totalKasus = arrayListOf<DataEntry>()
        for (i in 0 until anevResp?.data?.size!!) {
            totalKasus.add(
                ValueDataEntry(
                    anevResp.data?.get(i)?.kesatuan,
                    anevResp.data?.get(i)?.total
                )
            )
        }
        pie.data(totalKasus)
        pie.setOnClickListener(object :
            ListenersInterface.OnClickListener(arrayOf("x", "value")) {
            override fun onClick(event: Event) {
                Toast.makeText(
                    this@AnevActivity,
                    event.data["x"].toString() + ":" + event.data["value"],
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        /*kesatuan*/
        pie.title("Statistika Analisis Kesatuan")
        pie.labels().position("outside")
        pie.title().fontColor("#101010").fontSize(18).align(Align.LEFT)
        when {
            req.tahun != null -> {
                when {
                    req.rentang == "tahun" -> {
                        pie.legend().title().enabled(true)
                        pie.legend().title()
                            .text("Tahun ${req.tahun}").fontSize(16)
                            .padding(0.0, 0.0, 10.0, 0.0)
                            .fontColor("#101010")
//                        pie_anev.setChart(pie)
                    }
                    /*  req.semester != null -> {
                          pie.legend().title().enabled(true)
                          pie.legend().title()
                              .text("Tahun ${req.tahun}, Semester ${req.semester}")
                              .fontSize(16)
                              .padding(0.0, 0.0, 10.0, 0.0)
                              .fontColor("#101010")
//                        pie_anev.setChart(pie)*/
                    req.rentang == "bulan" -> {
                        pie.legend().title().enabled(true)
                        pie.legend().title()
                            .text("Tahun ${req.tahun}, Bulan ${convertMonth(req)}")
                            .fontSize(16)
                            .padding(0.0, 0.0, 10.0, 0.0)
                            .fontColor("#101010")
                    }
                    req.rentang == "semester_1" -> {
                        pie.legend().title().enabled(true)
                        pie.legend().title()
                            .text("Tahun ${req.tahun}, Semester Awal")
                            .fontSize(16)
                            .padding(0.0, 0.0, 10.0, 0.0)
                            .fontColor("#101010")
                    }
                    req.rentang == "semester_2" -> {
                        pie.legend().title().enabled(true)
                        pie.legend().title()
                            .text("Tahun ${req.tahun}, Semester Akhir")
                            .fontSize(16)
                            .padding(0.0, 0.0, 10.0, 0.0)
                            .fontColor("#101010")
                    }
                }
            }

        }

        pie_anev.setChart(pie)
    }

    /**/  private fun defaultPie(resp: BaseAnev?) {
        pie = AnyChart.pie()

        pie.setOnClickListener(object :
            ListenersInterface.OnClickListener(arrayOf("x", "value")) {
            override fun onClick(event: Event) {
                Toast.makeText(
                    this@AnevActivity,
                    event.data["x"].toString() + ":" + event.data["value"],
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        /*set for data entry pie*/
//        val totalKasus = arrayListOf<PieEntry>()
//        Log.e("respSize", "${resp?.data}")
        val totalKasus = arrayListOf<DataEntry>()
        for (i in 0 until resp?.data!!.size) {
            totalKasus.add(ValueDataEntry(resp.data?.get(i)?.kesatuan, resp.data?.get(i)?.total))
        }
        pie.data(totalKasus)

        pie.innerRadius(5)
        pie.connectorLength(10)
        pie.hover(2)
        pie.title("Statistika Analisis Kesatuan")
        pie.labels().position("outside")
        pie.title().fontColor("#101010").fontSize(18).align(Align.LEFT)

        pie.legend().title().enabled(true)
        if (resp.bulan == null) {
            pie.legend().title()
                .text("Tahun ${resp.tahun}").fontSize(16)
                .padding(0.0, 0.0, 10.0, 0.0)
                .fontColor("#101010")
        } else {
            pie.legend().title()
                .text("Tahun ${resp.tahun}, Bulan ${resp.bulan}").fontSize(16)
                .padding(0.0, 0.0, 10.0, 0.0)
                .fontColor("#101010")
        }

        pie.legend()
            .position("bottom")
            .itemsLayout(LegendLayout.HORIZONTAL_EXPANDABLE)
            .height(150)
            .width(350)
            .fontSize(14)
            .fontColor("#101010")
        pie.palette(
            arrayOf(
                "#d7303b",
                "#0a7f26",
                "#41ffff",
                "#aead0f",
                "#b01693",
                "#009aab",
                "#093737",
                "#e8ff00",
                "#18ff2a",
                "#269e0d",
                "#ff2e2c",
                "#87f1be",
                "#000000",
                "#a4c1fc",
                "#faaebd",
                "#f1c54b",
                "#0cff00",
                "#ff9830",
                "#fff2ff",
                "#f1d463",
                "#00b3c1",
                "#a57d79"
            )
        )
        pie_anev.setChart(pie)
/*//        val pieDataSet = PieDataSet(totalKasus, "")
//        pieDataSet.valueTextSize = 20f
//        pieDataSet.valueTextColor = Color.WHITE

//        val colorSet = java.util.ArrayList<Int>()
//        colorSet.add(Color.rgb(255, 107, 107))  //red
//        colorSet.add(Color.rgb(173, 232, 244))  // blue
//        pieDataSet.setColors(colorSet)
//        val pieData = PieData(pieDataSet)
//        pie_anev.data = pieData

//        pie_anev.description.text = "Pie chart"
//        pie_anev.description.textSize = 20f
//        pie_anev.centerTextRadiusPercent = 0f
//        pie_anev.isDrawHoleEnabled = true
//        pie_anev.legend.isEnabled = false
//        pie_anev.description.isEnabled = true*/
    }

    private fun convertMonth(req: FilterReq):String {
        var bulan = ""
        when(req.bulan){
            "01"->bulan = "Januari"
            "02"->bulan = "Februari"
            "03"->bulan = "Maret"
            "04"->bulan = "April"
            "05"->bulan = "Mei"
            "06"->bulan = "Juni"
            "07"->bulan = "Juli"
            "08"->bulan = "Agustus"
            "09"->bulan = "September"
            "10"->bulan = "Oktober"
            "11"->bulan = "November"
            "12"->bulan = "Desember"
        }
        return bulan
    }

    private fun tesPie() {
        pie_anev.clear()

        pie.setOnClickListener(object :
            ListenersInterface.OnClickListener(arrayOf("x", "value")) {
            override fun onClick(event: Event) {
                Toast.makeText(
                    this@AnevActivity,
                    event.getData().get("x").toString() + ":" + event.getData().get("value"),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
        val data: MutableList<DataEntry> = ArrayList()
        data.add(ValueDataEntry("Apples", 6371664))
        data.add(ValueDataEntry("Pears", 789622))
        data.add(ValueDataEntry("Bananas", 7216301))
        data.add(ValueDataEntry("Grapes", 1486621))
        data.add(ValueDataEntry("Oranges", 1200000))

        pie.data(data)

        pie.title("Fruits imported in 2015 (in kg)")

        pie.labels().position("outside")

        pie.legend().title().enabled(true)
        pie.legend().title()
            .text("Retail channels")
            .padding(0.0, 0.0, 10.0, 0.0)

        pie.legend()
            .position("center-bottom")
            .itemsLayout(LegendLayout.HORIZONTAL)
            .align(Align.CENTER)

        pie_anev.setChart(pie)
    }
}