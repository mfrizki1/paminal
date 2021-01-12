
package id.calocallo.sicape.ui.main.lp.disiplin

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PersonelLapor
import id.calocallo.sicape.network.response.LpDisiplinResp
import id.calocallo.sicape.network.response.LpPasalResp
import id.calocallo.sicape.network.response.SatKerResp
import id.calocallo.sicape.ui.main.lp.AddLpActivity
import id.calocallo.sicape.ui.main.lp.LpDisiplinPasalAdapter
import id.calocallo.sicape.ui.main.lp.disiplin.DetailLpDisiplinActivity.Companion.DETAIL_DISIPLIN
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_list_lp_disiplin.*
import kotlinx.android.synthetic.main.item_lp_kke.view.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback

class ListLpDisiplinActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private var listDisiplin = arrayListOf<LpDisiplinResp>()
    private var listPasal = arrayListOf<LpPasalResp>()
    private var personelTerLapor = PersonelLapor()
    private var personelPeLapor = PersonelLapor()
    private var satKerResp = SatKerResp()
    private var adapterLpDisiplin = ReusableAdapter<LpDisiplinResp>(this)
    private lateinit var callbackLpDisiplin: AdapterCallback<LpDisiplinResp>
    private val viewPool = RecyclerView.RecycledViewPool()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_lp_disiplin)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "List Data Laporan Polisi Disiplin"
        sessionManager = SessionManager(this)
        sessionManager.setJenisLP("disiplin")

        getListDisiplin()

        btn_add_lp_disiplin.setOnClickListener {
            val intent = Intent(this, AddLpActivity::class.java)
            intent.putExtra("JENIS_LP", "disiplin")
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun getListDisiplin() {
        satKerResp = SatKerResp(1, "POLDA", "ALAMAT", "081210812", "", "", "", "")
        personelTerLapor = PersonelLapor(1, "faisal", "bripda", "jabatan", "1234",1, "polda kalsel")
        personelPeLapor =
            PersonelLapor(2, "utuh", "ipda", "jabatan", "0987",1, "polresta banjarmasin")

        listPasal.add(LpPasalResp(1, "Pasal 1", "LOREM IPSUM DOLOR", "", "", ""))
        listPasal.add(LpPasalResp(2, "Pasal 2", "LOREM IPSUM DOLOR", "", "", ""))
        listPasal.add(LpPasalResp(3, "Pasal 3", "LOREM IPSUM DOLOR", "", "", ""))

        listDisiplin.add(
            LpDisiplinResp(
                1, "LP/DISIPLIN1",
                "disiplin", personelTerLapor, personelPeLapor,
                "Banjarmasin", "12-01-20", "Budi",
                "IPDA", "87654321", "KOMBES","POLDA kALSEL",
                "macam_pelanggaran", "keterangan terlapor",
                "kronologis", "rincian", listPasal, satKerResp, "", ""
            )
        )

        listDisiplin.add(
            LpDisiplinResp(
                2, "LP/DISIPLIN2",
                "disiplin", personelTerLapor, personelPeLapor,
                "Banjarmasin", "12-01-20", "Budi",
                "IPDA", "87654321", "KOMBES","POLDA kALSEL",
                "macam_pelanggaran", "keterangan terlapor",
                "kronologis", "rincian", listPasal, satKerResp, "", ""
            )
        )

        listDisiplin.add(
            LpDisiplinResp(
                3, "LP/DISIPLIN3",
                "disiplin", personelTerLapor, personelPeLapor,
                "Banjarmasin", "12-01-20", "Budi",
                "IPDA", "87654321", "KOMBES","POLDA kALSEL",
                "macam_pelanggaran", "keterangan terlapor",
                "kronologis", "rincian", listPasal, satKerResp, "", ""
            )
        )

        callbackLpDisiplin = object : AdapterCallback<LpDisiplinResp> {
            override fun initComponent(itemView: View, data: LpDisiplinResp, itemIndex: Int) {
                itemView.ll_personel_pelapor.visible()
                itemView.txt_no_lp_kke.text = data.no_lp
                itemView.txt_nama_lp_kke_pelapor.text = "Nama : ${data.personel_pelapor?.nama}"
                itemView.txt_nrp_pangkat_lp_kke_pelapor.text =
                    "Pangkat : ${data.personel_pelapor?.pangkat.toString().toUpperCase()}, NRP : ${data.personel_pelapor?.nrp}"
                itemView.txt_jabatan_lp_kke_pelapor.text =
                    "Jabatan : ${data.personel_pelapor?.jabatan}"
                itemView.txt_kesatuan_lp_kke_pelapor.text =
                    "Kesatuan : ${data.personel_pelapor?.kesatuan.toString().toUpperCase()}"

                itemView.txt_nama_lp_kke_terlapor.text =
                    "Nama : ${data.personel_terlapor?.nama}"
                itemView.txt_nrp_pangkat_lp_kke_terlapor.text =
                    "Pangkat : ${data.personel_terlapor?.pangkat.toString().toUpperCase()}, NRP : ${data.personel_terlapor?.nrp}"
                itemView.txt_jabatan_lp_kke_terlapor.text =
                    "Jabatan : ${data.personel_terlapor?.jabatan}"
                itemView.txt_kesatuan_lp_kke_terlapor.text =
                    "Kesatuan : ${data.personel_terlapor?.kesatuan.toString().toUpperCase()}"

                itemView.rv_pasal_lp_kke.apply {
                    layoutManager = LinearLayoutManager(
                        itemView.rv_pasal_lp_kke.context,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    adapter = LpDisiplinPasalAdapter(data)
                    setRecycledViewPool(viewPool)

                }
            }

            override fun onItemClicked(itemView: View, data: LpDisiplinResp, itemIndex: Int) {
                val intent =
                    Intent(this@ListLpDisiplinActivity, DetailLpDisiplinActivity::class.java)
                intent.putExtra(DETAIL_DISIPLIN, data)
                startActivity(intent)
            }
        }
        adapterLpDisiplin.adapterCallback(callbackLpDisiplin)
            .filterable()
            .isVerticalView().addData(listDisiplin)
            .setLayout(R.layout.item_lp_kke)
            .build(rv_lp_disiplin)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_bar, menu)
        val item = menu?.findItem(R.id.action_search)
        val searchView = item?.actionView as SearchView
        searchView.queryHint = "Cari LP Disiplin"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterLpDisiplin.filter.filter(newText)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

}