package id.calocallo.sicape.ui.main.lp.pidana

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.LpMinResp
import id.calocallo.sicape.ui.main.lp.AddLpActivity
import id.calocallo.sicape.ui.main.lp.pidana.DetailLpPidanaActivity.Companion.DETAIL_PIDANA
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.calocallo.sicape.ui.base.BaseActivity
import id.calocallo.sicape.ui.main.lp.KatAddLpActivity
import kotlinx.android.synthetic.main.activity_list_lp_pidana.*
import kotlinx.android.synthetic.main.layout_edit_1_text.view.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import kotlinx.android.synthetic.main.view_no_data.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListLpPidanaActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var adapterListPidana = ReusableAdapter<LpMinResp>(this)
    private lateinit var callbackListPidana: AdapterCallback<LpMinResp>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_lp_pidana)
        sessionManager1 = SessionManager1(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "List Data Laporan Polisi Pidana"
        sessionManager1.setJenisLP("pidana")

        val hak = sessionManager1.fetchHakAkses()
//        if (hak == "operator") {
//            btn_add_lp_pidana.gone()
//        }

        btn_add_lp_pidana.setOnClickListener {
//            val intent = Intent(this, AddLpActivity::class.java)
            val intent = Intent(this, KatAddLpActivity::class.java)
            intent.putExtra("JENIS_LP", "pidana")
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        apiListPidana()
//        getList()
    }

    override fun onResume() {
        super.onResume()
        apiListPidana()
    }

    private fun apiListPidana() {
        rl_pb.visible()
        NetworkConfig().getServLp().getLpByJenis("Bearer ${sessionManager1.fetchAuthToken()}", "pidana").enqueue(object :
            Callback<ArrayList<LpMinResp>>{
            override fun onFailure(call: Call<ArrayList<LpMinResp>>, t: Throwable) {
                rl_pb.gone()
                rl_no_data.visible()
                rv_list_pidana.gone()
                Toast.makeText(this@ListLpPidanaActivity, "Error Koneksi", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onResponse(
                call: Call<ArrayList<LpMinResp>>,
                response: Response<ArrayList<LpMinResp>>
            ) {
                rl_pb.gone()
               if(response.isSuccessful){
                    getListPidana(response.body())
               }else{
                   rl_pb.gone()
                   rl_no_data.visible()
                   rv_list_pidana.gone()
                   Toast.makeText(this@ListLpPidanaActivity, "Error Koneksi", Toast.LENGTH_SHORT)
                       .show()
               }
            }
        })
    }

    private fun getListPidana(list: ArrayList<LpMinResp>?) {
        callbackListPidana = object : AdapterCallback<LpMinResp> {
            override fun initComponent(itemView: View, data: LpMinResp, itemIndex: Int) {
                itemView.txt_edit_pendidikan.text = data.no_lp
                /*
                if (data.status_pelapor == "sipil") {
//                    itemView.ll_sipil_pidana.visible()
//                    itemView.ll_personel_pelapor.gone()
                } else if (data.status_pelapor == "polisi") {
//                    itemView.ll_sipil_pidana.gone()
//                    itemView.ll_personel_pelapor.visible()
                }
                itemView.txt_no_lp_pidana.text = data.no_lp

                itemView.txt_agama_lp_sipil_pidana.text = data.agama_pelapor
                itemView.txt_alamat_lp_sipil_pidana.text = data.alamat_pelapor
                itemView.txt_kwg_lp_sipil_pidana.text = data.kewarganegaraan_pelapor
                itemView.txt_nama_lp_sipil_pidana.text = data.nama_pelapor
                itemView.txt_nik_lp_sipil_pidana.text = data.nik_ktp_pelapor
                itemView.txt_no_telp_lp_sipil_pidana.text = data.no_telp_pelapor
                itemView.txt_pekerjaan_lp_sipil_pidana.text = data.pekerjaan_pelapor

                itemView.txt_nama_lp_pidana_pelapor.text =
                    "Nama : ${data.personel_pelapor?.nama}"
                itemView.txt_nrp_pangkat_lp_pidana_pelapor.text =
                    "Pangkat : ${data.personel_pelapor?.pangkat}, NRP : ${data.personel_pelapor?.nrp}"
                itemView.txt_jabatan_lp_pidana_pelapor.text =
                    "Jabatan : ${data.personel_pelapor?.jabatan.toString().toUpperCase()}"
                itemView.txt_kesatuan_lp_pidana_pelapor.text =
                    "Kesatuan : ${data.personel_pelapor?.kesatuan.toString().toUpperCase()}"

                //personel terlapor
                itemView.txt_nama_lp_pidana_terlapor.text =
                    "Nama : ${data.personel_terlapor?.nama}"
                itemView.txt_nrp_pangkat_lp_pidana_terlapor.text =
                    "Pangkat : ${data.personel_terlapor?.pangkat}, NRP : ${data.personel_terlapor?.nrp}"
                itemView.txt_jabatan_lp_pidana_terlapor.text =
                    "Jabatan : ${data.personel_terlapor?.jabatan.toString().toUpperCase()}"
                itemView.txt_kesatuan_lp_pidana_terlapor.text =
                    "Kesatuan : ${data.personel_terlapor?.kesatuan.toString().toUpperCase()}"

                //set pasal Layout and adapter
                itemView.rv_pasal_lp_pidana.apply {
                    layoutManager = LinearLayoutManager(
                        itemView.rv_pasal_lp_pidana.context,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    adapter = LpPidanaPasalAdapter(data)
                    setRecycledViewPool(viewPool)

                }

                 */
            }

            override fun onItemClicked(itemView: View, data: LpMinResp, itemIndex: Int) {
                val intent =
                    Intent(this@ListLpPidanaActivity, DetailLpPidanaActivity::class.java)
                intent.putExtra(DETAIL_PIDANA, data)
                startActivity(intent)
            }
        }
        list?.let {
            adapterListPidana.adapterCallback(callbackListPidana)
                .isVerticalView()
                .addData(it)
                .setLayout(R.layout.layout_edit_1_text)
                .build(rv_list_pidana)
                .filterable()
        }
    }

   /* private fun getList() {
      *//*  satKerResp = SatKerResp(1, "POLDA", "ALAMAT", "081210812", "")
        personelTerLapor = PersonelLapor(
            1, "faisal", "bripda", "jabatan", "1234", 1, "polda kalsel", "Jl Banjarmasin",
            "islam",
            "laki_laki",
            "Batola", "12-01-2000", "081212"
        )
        personelPeLapor =
            PersonelLapor(
                2, "utuh", "ipda", "jabatan", "0987", 1, "polresta banjarmasin", "Jl Banjarmasin",
                "islam",
                "laki_laki",
                "Batola", "12-01-2000", "081212"
            )


        listPasal.add(LpPasalResp(1, "Pasal 1", "LOREM IPSUM DOLOR", "", "", ""))
        listPasal.add(LpPasalResp(2, "Pasal 2", "LOREM IPSUM DOLOR", "", "", ""))
        listPasal.add(LpPasalResp(3, "Pasal 3", "LOREM IPSUM DOLOR", "", "", ""))
        listPidana.add(
            LpPidanaResp(
                1, "LP/PIDANA/2020/BIDPROPAM", satKerResp, personelTerLapor, "Uraian Pelanggaran",
                "Banjarmasin", "12-12-2000",
                "Rojak Ahmad", "Kombes", "12345678",
                "Polair", "polda Kalsel", "polisi",
                "", "", "", "",
                "", "", "", personelPeLapor,
                resources.getString(R.string.paragraf), resources.getString(R.string.paragraf),
                listPasal, "", "", ""
            )
        )

        listPidana.add(
            LpPidanaResp(
                2, "LP/PIDANA2/2020/BIDPROPAM", satKerResp, personelTerLapor, "Uraian Pelanggaran",
                "Banjarmasin", "12-12-2000",
                "Rojak Ahmad", "polda Kalsel", "Kombes", "12345678",
                "Polair", "sipil",
                "sipil", "islam", "pekerjaan", "Indonesia",
                "jl xxx", "081212", "123456", null,
                resources.getString(R.string.paragraf), resources.getString(R.string.paragraf),
                listPasal, "", "", ""
            )
        )

        listPidana.add(
            LpPidanaResp(
                3, "LP/PIDANA3/2020/BIDPROPAM", satKerResp, personelTerLapor, "Uraian Pelanggaran",
                "Banjarmasin", "12-12-2000",
                "Rojak Ahmad", "Kombes", "12345678",
                "Polair", "polda Kalsel", "polisi",
                "", "", "", "",
                "", "", "", personelPeLapor,
                resources.getString(R.string.paragraf), resources.getString(R.string.paragraf),
                listPasal, "", "", ""
            )
        )

//        rv_list_pidana.layoutManager =
//            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//        adapterListPidana =
//            LpPidanaAdapter(this, listPidana, object : LpPidanaAdapter.OnClickLpPidana {
//                override fun onClick(position: Int) {
//                    val intent =
//                        Intent(this@ListLpPidanaActivity, DetailLpPidanaActivity::class.java)
//                    intent.putExtra(DETAIL_PIDANA, listPidana[position])
//                    startActivity(intent)
//                }
//            })
//        rv_list_pidana.adapter = adapterListPidana*//*

        callbackListPidana = object : AdapterCallback<LpPidanaResp> {
            override fun initComponent(itemView: View, data: LpPidanaResp, itemIndex: Int) {
                itemView.txt_edit_pendidikan.text = data.no_lp
                *//*
                if (data.status_pelapor == "sipil") {
//                    itemView.ll_sipil_pidana.visible()
//                    itemView.ll_personel_pelapor.gone()
                } else if (data.status_pelapor == "polisi") {
//                    itemView.ll_sipil_pidana.gone()
//                    itemView.ll_personel_pelapor.visible()
                }
                itemView.txt_no_lp_pidana.text = data.no_lp

                itemView.txt_agama_lp_sipil_pidana.text = data.agama_pelapor
                itemView.txt_alamat_lp_sipil_pidana.text = data.alamat_pelapor
                itemView.txt_kwg_lp_sipil_pidana.text = data.kewarganegaraan_pelapor
                itemView.txt_nama_lp_sipil_pidana.text = data.nama_pelapor
                itemView.txt_nik_lp_sipil_pidana.text = data.nik_ktp_pelapor
                itemView.txt_no_telp_lp_sipil_pidana.text = data.no_telp_pelapor
                itemView.txt_pekerjaan_lp_sipil_pidana.text = data.pekerjaan_pelapor

                itemView.txt_nama_lp_pidana_pelapor.text =
                    "Nama : ${data.personel_pelapor?.nama}"
                itemView.txt_nrp_pangkat_lp_pidana_pelapor.text =
                    "Pangkat : ${data.personel_pelapor?.pangkat}, NRP : ${data.personel_pelapor?.nrp}"
                itemView.txt_jabatan_lp_pidana_pelapor.text =
                    "Jabatan : ${data.personel_pelapor?.jabatan.toString().toUpperCase()}"
                itemView.txt_kesatuan_lp_pidana_pelapor.text =
                    "Kesatuan : ${data.personel_pelapor?.kesatuan.toString().toUpperCase()}"

                //personel terlapor
                itemView.txt_nama_lp_pidana_terlapor.text =
                    "Nama : ${data.personel_terlapor?.nama}"
                itemView.txt_nrp_pangkat_lp_pidana_terlapor.text =
                    "Pangkat : ${data.personel_terlapor?.pangkat}, NRP : ${data.personel_terlapor?.nrp}"
                itemView.txt_jabatan_lp_pidana_terlapor.text =
                    "Jabatan : ${data.personel_terlapor?.jabatan.toString().toUpperCase()}"
                itemView.txt_kesatuan_lp_pidana_terlapor.text =
                    "Kesatuan : ${data.personel_terlapor?.kesatuan.toString().toUpperCase()}"

                //set pasal Layout and adapter
                itemView.rv_pasal_lp_pidana.apply {
                    layoutManager = LinearLayoutManager(
                        itemView.rv_pasal_lp_pidana.context,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    adapter = LpPidanaPasalAdapter(data)
                    setRecycledViewPool(viewPool)

                }

                 *//*
            }

            override fun onItemClicked(itemView: View, data: LpPidanaResp, itemIndex: Int) {
                val intent =
                    Intent(this@ListLpPidanaActivity, DetailLpPidanaActivity::class.java)
                intent.putExtra(DETAIL_PIDANA, data)
                startActivity(intent)
            }
        }
        adapterListPidana.adapterCallback(callbackListPidana)
            .isVerticalView()
            .addData(listPidana)
            .setLayout(R.layout.layout_edit_1_text)
            .build(rv_list_pidana)
            .filterable()
    }*/

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_bar, menu)
        val item = menu?.findItem(R.id.action_search)
        val searchView = item?.actionView as SearchView
        searchView.queryHint = "Cari LP Pidana"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterListPidana.filter.filter(newText)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

}