package id.calocallo.sicape.ui.main.lp.kke

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PersonelLapor
import id.calocallo.sicape.network.response.LpKkeResp
import id.calocallo.sicape.network.response.LpPasalResp
import id.calocallo.sicape.network.response.LpSaksiResp
import id.calocallo.sicape.ui.main.lp.AddLpActivity
import id.calocallo.sicape.ui.main.lp.kke.DetailLpKkeActivity.Companion.DETAIL_KKE
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_list_lp_kode_etik.*
import kotlinx.android.synthetic.main.layout_edit_1_text.view.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback

class ListLpKodeEtikActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var listKke = arrayListOf<LpKkeResp>()
    private var listPasal = arrayListOf<LpPasalResp>()
    private var listSaksi = arrayListOf<LpSaksiResp>()
    private var personelTerLapor = PersonelLapor()
    private var personelPeLapor = PersonelLapor()
    private var adapterLpKke = ReusableAdapter<LpKkeResp>(this)
    private lateinit var callbackLpKke: AdapterCallback<LpKkeResp>
    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_lp_kode_etik)
        setupActionBarWithBackButton(toolbar)
        sessionManager1 = SessionManager1(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "List Data Laporan Polisi Kode Etik"
        sessionManager1.setJenisLP("kode_etik")
        val hak = sessionManager1.fetchHakAkses()
        if (hak == "operator") {
            btn_add_lp_kke.gone()
        }

        btn_add_lp_kke.setOnClickListener {
            val intent = Intent(this, AddLpActivity::class.java)
            intent.putExtra("JENIS_LP", "kode_etik")
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        getListKke()

    }

    private fun getListKke() {
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

        listSaksi.add(LpSaksiResp(1, "Galuh", "korban", "", "", "", 1, "", "", ""))
        listSaksi.add(LpSaksiResp(2, "Akbar", "saksi", "", "", "", 0, "", "", ""))
        listSaksi.add(LpSaksiResp(3, "Wahyu", "saksi", "", "", "", 0, "", "", ""))
        listKke.add(
            LpKkeResp(
                1, "LP/KKE1/2019/BIDPROPAM", "kode_etik", personelTerLapor,
                personelPeLapor, "Banjarbaru", "12-12-2000", "Budi",
                "IPDA", "9090", "KOMBES", "POLRES BANJAR",
                sessionManager1.fetchUserPersonel()?.id, "Alat Bukti\nbaju\nsenjata", "isi Laporan",
                listPasal, listSaksi, "", "", ""
            )
        )

        listKke.add(
            LpKkeResp(
                2, "LP/KKE2/2019/BIDPROPAM", "kode_etik", personelTerLapor,
                personelPeLapor, "Banjarbaru", "12-12-2000", "Budi",
                "IPDA", "9090", "KOMBES", "POLRES BANJAR",
                sessionManager1.fetchUserPersonel()?.id, "Alat Bukti\nbaju\nsenjata", "isi Laporan",
                listPasal, listSaksi, "", "", ""
            )
        )
        listKke.add(
            LpKkeResp(
                3, "LP/KKE2/2019/BIDPROPAM", "kode_etik", personelTerLapor,
                personelPeLapor, "Banjarbaru", "12-12-2000", "Budi",
                "IPDA", "9090", "KOMBES", "POLRES BANJAR",
                sessionManager1.fetchUserPersonel()?.id, "Alat Bukti\nbaju\nsenjata", "isi Laporan",
                listPasal, listSaksi, "", "", ""
            )
        )


        callbackLpKke = object : AdapterCallback<LpKkeResp> {
            override fun initComponent(itemView: View, data: LpKkeResp, itemIndex: Int) {
                /*
                itemView.txt_no_lp_kke.text = data.no_lp
                itemView.txt_nama_lp_kke_pelapor.text =
                    "Nama : ${data.personel_pelapor?.nama}"
                itemView.txt_nrp_pangkat_lp_kke_pelapor.text =
                    "Pangkat : ${data.personel_pelapor?.pangkat.toString()
                        .toUpperCase()}, NRP : ${data.personel_pelapor?.nrp}"
                itemView.txt_jabatan_lp_kke_pelapor.text =
                    "Jabatan : ${data.personel_pelapor?.jabatan}"
                itemView.txt_kesatuan_lp_kke_pelapor.text =
                    "Kesatuan : ${data.personel_pelapor?.kesatuan.toString().toUpperCase()}"

                //personel terlapor
                itemView.txt_nama_lp_kke_terlapor.text =
                    "Nama : ${data.personel_terlapor?.nama}"
                itemView.txt_nrp_pangkat_lp_kke_terlapor.text =
                    "Pangkat : ${data.personel_terlapor?.pangkat.toString()
                        .toUpperCase()}, NRP : ${data.personel_terlapor?.nrp}"
                itemView.txt_jabatan_lp_kke_terlapor.text =
                    "Jabatan : ${data.personel_terlapor?.jabatan}"
                itemView.txt_kesatuan_lp_kke_terlapor.text =
                    "Kesatuan : ${data.personel_terlapor?.kesatuan.toString().toUpperCase()}"

                //set pasal layout and adapter
                itemView.rv_pasal_lp_kke.apply {
                    layoutManager = LinearLayoutManager(
                        itemView.rv_pasal_lp_kke.context,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    adapter = LpKkePasalAdapter(data)
                    setRecycledViewPool(viewPool)
                }

                 */
                itemView.txt_edit_pendidikan.text = data.no_lp
            }

            override fun onItemClicked(itemView: View, data: LpKkeResp, itemIndex: Int) {
                val intent = Intent(this@ListLpKodeEtikActivity, DetailLpKkeActivity::class.java)
                intent.putExtra(DETAIL_KKE, data)
                startActivity(intent)
            }
        }
        adapterLpKke.adapterCallback(callbackLpKke)
            .isVerticalView().addData(listKke)
            .setLayout(R.layout.layout_edit_1_text)
            .build(rv_lp_kke)
            .filterable()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_bar, menu)
        val item = menu?.findItem(R.id.action_search)
        val searchView = item?.actionView as SearchView
        searchView.queryHint = "Cari LP Kode Etik"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterLpKke.filter.filter(newText)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

}