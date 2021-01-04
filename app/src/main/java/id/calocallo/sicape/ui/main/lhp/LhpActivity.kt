package id.calocallo.sicape.ui.main.lhp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import id.calocallo.sicape.R
import id.calocallo.sicape.model.*
import id.calocallo.sicape.network.response.KetTerlaporLhpResp
import id.calocallo.sicape.network.response.PersonelPenyelidikResp
import id.calocallo.sicape.network.response.RefPenyelidikanResp
import id.calocallo.sicape.network.response.SaksiLhpResp
import id.calocallo.sicape.ui.main.lhp.DetailLhpActivity.Companion.DETAIL_LHP
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_lhp.*
import kotlinx.android.synthetic.main.activity_personel.*
import kotlinx.android.synthetic.main.item_lhp.view.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback

class LhpActivity : BaseActivity() {
    private lateinit var list: ArrayList<LhpModel>
    private var refPenyelidikan = ArrayList<RefPenyelidikanResp>()
    private var personelPenyelidikan = ArrayList<PersonelPenyelidikResp>()
    private var saksiLhp = ArrayList<SaksiLhpResp>()
    private var ketTerlaporLhpResp = ArrayList<KetTerlaporLhpResp>()

    //    private lateinit var adapterLhp: LhpAdapter
    private lateinit var adapterLhp: ReusableAdapter<LhpModel>
    private lateinit var adapterCallbackLhp: AdapterCallback<LhpModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lhp)

        list = ArrayList()
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Laporan Hasil Penyelidikan"

        getListLHP()

        btn_lhp_add.setOnClickListener {
            startActivity(Intent(this, AddLhpActivity::class.java))
        }

    }

    private fun getListLHP() {
        //list ref penyelidikan
        refPenyelidikan.add(RefPenyelidikanResp(1, 1, "LP/KODEETIK1/2020/BIDPROPAM"))
        refPenyelidikan.add(RefPenyelidikanResp(2, 4, "LP/KODEETIK4/2019/BIDPROPAM"))
        refPenyelidikan.add(RefPenyelidikanResp(3, 2, "LP/KODEETIK2/2019/BIDPROPAM"))

        //listpenyelidik
        personelPenyelidikan.add(
            PersonelPenyelidikResp(
                1, 4, "Faisal", "BRIPDA", "POLAIR",
                "1234567", 2, "Polresta Banjarmasin", "ketua_tim"
            )
        )
        personelPenyelidikan.add(
            PersonelPenyelidikResp(
                1, 5, "Rizki", "KOMBES", "POLAIR",
                "1234567", 2, "Polresta Banjarmasin", "anggota"
            )
        )
        personelPenyelidikan.add(
            PersonelPenyelidikResp(
                1, 12, "Ahmad", "IPDA", "POLAIR",
                "1234567", 2, "Polresta Banjarmasin", "anggota"
            )
        )

        //saksi LHP
        saksiLhp.add(
            SaksiLhpResp(
                1, "KETERANGAN INI\nadalah", "polisi",
                "Utuh", 10, "BRIPDA", "POLAIR",
                "123456", 2, "Polresta Banjarmasin",
                "", "", "", "", "", ""
            )
        )

        saksiLhp.add(
            SaksiLhpResp(
                1, "KETERANGAN INI\nadalah", "sipil", "Ghoni",
                null, "", "", "123456", null, "",
                "Banjarmasin", "12-12-2008", "Buruh", "Jl xxx",
                "", ""
            )
        )

        //keterangan terlapor
        ketTerlaporLhpResp.add(
            KetTerlaporLhpResp(
                1,
                20,
                "Faisal Rizki",
                "IPDA",
                "POLAIR",
                "09876",
                2,
                "Polresta Banjarmasin",
                "isi\nterlapor"
            )
        )

        //list lhp
        list.add(
            LhpModel(
                1, "LHP 1/xx/2020/BIDPROPAM", refPenyelidikan, personelPenyelidikan, saksiLhp,
                ketTerlaporLhpResp, "pengaduan\nisi", "SP/123", "pokok\ntugas",
                "permasalahan\npokok", "ahli", "kesimpulan", "rekomendasi",
                "Banjarbaru", "12-08-1999", "", "",
                "surat\nsurat1\nsurat2",
                "ptunjuk1\npetunjuk2\n3",
                "bukti1\nbukti2\n3",
                "analisa\nini adalah analisa", 1,
                "dari tanggal 12 agustus 2020 sampai 18 agustus 2020", "Banjarmasin"

            )
        )
        list.add(
            LhpModel(
                2, "LHP 2/xx/2020/BIDPROPAM", refPenyelidikan, personelPenyelidikan, saksiLhp,
                ketTerlaporLhpResp, "pengaduan\nisi", "SP/123", "pokok\ntugas",
                "permasalahan\npokok", "ahli", "kesimpulan", "rekomendasi",
                "Banjarbaru", "12-08-1999", "", "",
                "surat\nsurat1\nsurat2",
                "ptunjuk1\npetunjuk2\n3",
                "bukti1\nbukti2\n3",
                "analisa\nini adalah analisa", 0,
                "dari tanggal 12 agustus 2020 sampai 18 agustus 2020", "Banjarmasin"

            )
        )

        list.add(
            LhpModel(
                2, "LHP 3/xx/2020/BIDPROPAM", refPenyelidikan, personelPenyelidikan, saksiLhp,
                ketTerlaporLhpResp, "pengaduan\nisi", "SP/123", "pokok\ntugas",
                "permasalahan\npokok", "ahli", "kesimpulan", "rekomendasi",
                "Banjarbaru", "12-08-1999", "", "",
                "surat\nsurat1\nsurat2",
                "ptunjuk1\npetunjuk2\n3",
                "bukti1\nbukti2\n3",
                "analisa\nini adalah analisa", 1,
                "dari tanggal 12 agustus 2020 sampai 18 agustus 2020", "Banjarmasin"
            )
        )


        //adapterLibrary
        adapterLhp = ReusableAdapter(this)
        adapterCallbackLhp = object : AdapterCallback<LhpModel> {
            override fun initComponent(itemView: View, data: LhpModel, itemIndex: Int) {
//                val lidik = data.listLidik?.filter { it.status_penyelidik == "ketua_tim" }
                val lidik = data.personel_penyelidik?.find { it.is_ketua == "ketua_tim" }
                Log.e("lidik", "$lidik")
//                for(i in 0 ..data.listLidik?.size!!){
//                    if(lidik?.get(i)?.status_penyelidik == "ketua_tim"){
                itemView.txt_ketua_tim.text = lidik?.is_ketua
//                    }
//                }
                itemView.txt_no_lhp.text = data.no_lhp
                var terbukti: String = if (data.isTerbukti == 0) {
                    "TIdak Terbukti"
                } else {
                    "Terbukti"
                }
                itemView.txt_isTerbukti.text = terbukti
            }

            override fun onItemClicked(itemView: View, data: LhpModel, itemIndex: Int) {
                val intent = Intent(this@LhpActivity, DetailLhpActivity::class.java)
                intent.putExtra(DETAIL_LHP, data)
                startActivity(intent)
            }
        }
        adapterLhp.filterable()
            .adapterCallback(adapterCallbackLhp)
            .setLayout(R.layout.item_lhp)
            .isVerticalView()
            .addData(list)
            .build(rv_lhp)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_bar, menu)
        val item = menu?.findItem(R.id.action_search)
        val searchView = item?.actionView as SearchView
        searchView.queryHint = "Cari No LHP"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterLhp.filter.filter(newText)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }
}