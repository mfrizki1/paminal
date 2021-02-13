package id.calocallo.sicape.ui.main.anev

import android.os.Bundle
import android.util.Log
import android.widget.*
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
import id.calocallo.sicape.network.NetworkDummy
import id.calocallo.sicape.network.request.AnevReq
import id.calocallo.sicape.network.response.AnevResp
import id.calocallo.sicape.network.response.FilterPangkatResp
import id.calocallo.sicape.network.response.FilterPelanggaranResp
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_anev.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import kotlinx.android.synthetic.main.view_no_data.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AnevActivity : BaseActivity() {
    private var filterJenis: String? = null
    private var filterRentang: String? = null
    private var filterSmstr: String? = null
    private var filterBulan: String? = null
    private var filterJenisKesatuan: String? = null
    private lateinit var pie: Pie
    private var anevReq = AnevReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anev)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Analisa & Evaluasi"
        pie = AnyChart.pie()
        btn_filter_anev.setOnClickListener {
            filterView()
        }

        default()


    }

    private fun default() {
        rl_pb.visible()
        NetworkDummy().getService().getAnev().enqueue(object : Callback<ArrayList<AnevResp>> {
            override fun onFailure(call: Call<ArrayList<AnevResp>>, t: Throwable) {
                rl_pb.gone()
                rl_no_data.visible()
            }

            override fun onResponse(
                call: Call<ArrayList<AnevResp>>,
                response: Response<ArrayList<AnevResp>>
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
            btnKesatuan.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            btnKesatuan.setTextColor(resources.getColor(R.color.white))

            btnPangkat.setBackgroundColor(resources.getColor(R.color.white))
            btnPangkat.setTextColor(resources.getColor(R.color.colorPrimary))

            btnJenis.setBackgroundColor(resources.getColor(R.color.white))
            btnJenis.setTextColor(resources.getColor(R.color.colorPrimary))
            filterJenis = "kesatuan"
            layoutJenisKesatuan.visible()
        }
        btnPoldaFilter.setOnClickListener {
            btnPoldaFilter.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            btnPoldaFilter.setTextColor(resources.getColor(R.color.white))
            btnPolresFilter.setBackgroundColor(resources.getColor(R.color.white))
            btnPolresFilter.setTextColor(resources.getColor(R.color.colorPrimary))
            btnPolsekFilter.setBackgroundColor(resources.getColor(R.color.white))
            btnPolsekFilter.setTextColor(resources.getColor(R.color.colorPrimary))
            filterJenisKesatuan = "polda"
        }
        btnPolresFilter.setOnClickListener {
            btnPolresFilter.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            btnPolresFilter.setTextColor(resources.getColor(R.color.white))
            btnPoldaFilter.setBackgroundColor(resources.getColor(R.color.white))
            btnPoldaFilter.setTextColor(resources.getColor(R.color.colorPrimary))
            btnPolsekFilter.setBackgroundColor(resources.getColor(R.color.white))
            btnPolsekFilter.setTextColor(resources.getColor(R.color.colorPrimary))
            filterJenisKesatuan = "polres"
        }
        btnPolsekFilter.setOnClickListener {
            btnPolsekFilter.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            btnPolsekFilter.setTextColor(resources.getColor(R.color.white))
            btnPoldaFilter.setBackgroundColor(resources.getColor(R.color.white))
            btnPoldaFilter.setTextColor(resources.getColor(R.color.colorPrimary))
            btnPolresFilter.setBackgroundColor(resources.getColor(R.color.white))
            btnPolresFilter.setTextColor(resources.getColor(R.color.colorPrimary))
            filterJenisKesatuan = "polsek"
        }
        btnPangkat.setOnClickListener {
            btnPangkat.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            btnPangkat.setTextColor(resources.getColor(R.color.white))

            btnKesatuan.setBackgroundColor(resources.getColor(R.color.white))
            btnKesatuan.setTextColor(resources.getColor(R.color.colorPrimary))

            btnJenis.setBackgroundColor(resources.getColor(R.color.white))
            btnJenis.setTextColor(resources.getColor(R.color.colorPrimary))
            filterJenis = "pangkat"
            layoutJenisKesatuan.gone()

        }

        btnJenis.setOnClickListener {
            btnJenis.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            btnJenis.setTextColor(resources.getColor(R.color.white))

            btnKesatuan.setBackgroundColor(resources.getColor(R.color.white))
            btnKesatuan.setTextColor(resources.getColor(R.color.colorPrimary))

            btnPangkat.setBackgroundColor(resources.getColor(R.color.white))
            btnPangkat.setTextColor(resources.getColor(R.color.colorPrimary))
            filterJenis = "pelanggaran"
            layoutJenisKesatuan.gone()
        }

        /*filter rentang*/
        btn1Month.setOnClickListener {
            btn1Month.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            btn1Month.setTextColor(resources.getColor(R.color.white))

            btn6Month.setBackgroundColor(resources.getColor(R.color.white))
            btn6Month.setTextColor(resources.getColor(R.color.colorPrimary))

            btnFullTahun.setBackgroundColor(resources.getColor(R.color.white))
            btnFullTahun.setTextColor(resources.getColor(R.color.colorPrimary))
            filterRentang = "bulan"
            ll1Bln.visible()
            ll6Bln.gone()
        }
        btn6Month.setOnClickListener {
            btn6Month.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            btn6Month.setTextColor(resources.getColor(R.color.white))

            btn1Month.setBackgroundColor(resources.getColor(R.color.white))
            btn1Month.setTextColor(resources.getColor(R.color.colorPrimary))

            btnFullTahun.setBackgroundColor(resources.getColor(R.color.white))
            btnFullTahun.setTextColor(resources.getColor(R.color.colorPrimary))
            filterRentang = "semester"
            ll6Bln.visible()
            ll1Bln.gone()

        }
        btnFullTahun.setOnClickListener {
            btnFullTahun.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            btnFullTahun.setTextColor(resources.getColor(R.color.white))

            btn1Month.setBackgroundColor(resources.getColor(R.color.white))
            btn1Month.setTextColor(resources.getColor(R.color.colorPrimary))

            btn6Month.setBackgroundColor(resources.getColor(R.color.white))
            btn6Month.setTextColor(resources.getColor(R.color.colorPrimary))
            filterRentang = "tahun"
            ll6Bln.gone()
            ll1Bln.gone()
        }

        /*filter semester*/
        btnSmstrAwal.setOnClickListener {
            btnSmstrAwal.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            btnSmstrAwal.setTextColor(resources.getColor(R.color.white))

            btnSmstrAkhir.setBackgroundColor(resources.getColor(R.color.white))
            btnSmstrAkhir.setTextColor(resources.getColor(R.color.colorPrimary))
            filterSmstr = "awal"
        }
        btnSmstrAkhir.setOnClickListener {
            btnSmstrAkhir.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            btnSmstrAkhir.setTextColor(resources.getColor(R.color.white))

            btnSmstrAwal.setBackgroundColor(resources.getColor(R.color.white))
            btnSmstrAwal.setTextColor(resources.getColor(R.color.colorPrimary))
            filterSmstr = "akhir"
        }

        /*filter 1 bulan*/
        val adapter =
            ArrayAdapter(this, R.layout.item_spinner, resources.getStringArray(R.array.month))
        spinnerBulan.setAdapter(adapter)
        spinnerBulan.setOnItemClickListener { parent, view, position, id ->
            filterBulan = parent.getItemAtPosition(position).toString()
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
        jenis: String?, rentang: String?, tahun: String?,
        semester: String?, bulan: String?, jenisKesatuan: String?
    ) {
//        Log.e("semesterFilter", "$semester")
//        Log.e("tahunFilter", "$tahun")
        Log.e("jenisKesatuan", "$jenisKesatuan")
        anevReq.berdasarkan = jenis
        anevReq.rentang = rentang
        anevReq.tahun = tahun
        anevReq.semester = semester
        anevReq.bulan = bulan
        when (jenis) {
            "kesatuan" -> {
                Log.e("ini kesatuan", jenis)
                filterKesatuan(anevReq)
            }
            "pangkat" -> {
                Log.e("ini pangkat", jenis)
                filterPangkat(anevReq)
            }
            "pelanggaran" -> {
                Log.e("ini pelanggaran", jenis)
                filterPelanggaran(anevReq)
//                tesPie()
            }
        }
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
                                    response.body()!![i].jenis_pelanggaran.toString().toUpperCase(),
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

    private fun filterKesatuan(req: AnevReq) {
        rl_pb.visible()
        NetworkDummy().getService().getFilterKesatuan()
            .enqueue(object : Callback<ArrayList<AnevResp>> {
                override fun onFailure(call: Call<ArrayList<AnevResp>>, t: Throwable) {
                    rl_pb.gone()
                    rl_no_data.visible()
                }

                override fun onResponse(
                    call: Call<ArrayList<AnevResp>>,
                    response: Response<ArrayList<AnevResp>>
                ) {
                    if (response.isSuccessful) {
                        rl_pb.gone()
                        pie_anev.clear()
                        val totalKasus = arrayListOf<DataEntry>()
                        for (i in 0 until response.body()?.size!!) {
                            totalKasus.add(
                                ValueDataEntry(
                                    response.body()!![i].kesatuan,
                                    response.body()!![i].jumlah_kasus
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
                        pie.title("Kesatuan")
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
//                   tesPie()
                    } else {
                        rl_pb.gone()
                        rl_no_data.visible()
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


    private fun defaultPie(list: ArrayList<AnevResp>?) {
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
        val totalKasus = arrayListOf<DataEntry>()
        for (i in 0 until list?.size!!) {
            totalKasus.add(ValueDataEntry(list[i].kesatuan, list[i].jumlah_kasus))
        }
        pie.data(totalKasus)

        pie.innerRadius(5)
        pie.connectorLength(10)
        pie.hover(2)
        pie.title("Statistika Analisis")
        pie.labels().position("outside")
        pie.title().fontColor("#101010").fontSize(18).align(Align.LEFT)

        pie.legend().title().enabled(true)
        pie.legend().title()
            .text("Kesatuan").fontSize(16)
            .padding(0.0, 0.0, 10.0, 0.0)
            .fontColor("#101010")

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
//        val pieDataSet = PieDataSet(totalKasus, "")
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
//        pie_anev.description.isEnabled = true
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