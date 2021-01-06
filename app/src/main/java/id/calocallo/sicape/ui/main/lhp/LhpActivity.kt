package id.calocallo.sicape.ui.main.lhp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.*
import id.calocallo.sicape.network.response.KetTerlaporLhpResp
import id.calocallo.sicape.network.response.PersonelPenyelidikResp
import id.calocallo.sicape.network.response.RefPenyelidikanResp
import id.calocallo.sicape.network.response.SaksiLhpResp
import id.calocallo.sicape.ui.main.lhp.DetailLhpActivity.Companion.DETAIL_LHP
import id.calocallo.sicape.ui.main.lhp.add.AddLhpActivity
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_lhp.*
import kotlinx.android.synthetic.main.item_lhp.view.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback

class LhpActivity : BaseActivity() {
    private lateinit var list: ArrayList<LhpResp>
    private var refPenyelidikan = ArrayList<RefPenyelidikanResp>()
    private var personelPenyelidikan = ArrayList<PersonelPenyelidikResp>()
    private var saksiLhp = ArrayList<SaksiLhpResp>()
    private var ketTerlaporLhpResp = ArrayList<KetTerlaporLhpResp>()

    //    private lateinit var adapterLhp: LhpAdapter
    private lateinit var adapterLhp: ReusableAdapter<LhpResp>
    private lateinit var adapterCallbackLhp: AdapterCallback<LhpResp>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lhp)

        list = ArrayList()
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Laporan Hasil Penyelidikan"

        getListLHP()

        btn_lhp_add.setOnClickListener {
            startActivity(Intent(this, AddLhpActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
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
                "1234567", 2, "Polresta Banjarmasin", 1
            )
        )
        personelPenyelidikan.add(
            PersonelPenyelidikResp(
                1, 5, "Rizki", "KOMBES", "POLAIR",
                "1234567", 2, "Polresta Banjarmasin", 0
            )
        )
        personelPenyelidikan.add(
            PersonelPenyelidikResp(
                1, 12, "Ahmad", "IPDA", "POLAIR",
                "1234567", 2, "Polresta Banjarmasin", 0
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
            LhpResp(
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
            LhpResp(
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
            LhpResp(
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
        adapterCallbackLhp = object : AdapterCallback<LhpResp> {
            override fun initComponent(itemView: View, data: LhpResp, itemIndex: Int) {
                val lidik = data.personel_penyelidik?.find { it.is_ketua == 1 }
                if (lidik?.is_ketua == 1) itemView.txt_ketua_tim.text =
                    "Ketua Tim : ${lidik.nama}"
                itemView.txt_no_lhp.text = data.no_lhp
                var terbukti: String = if (data.isTerbukti == 0) {
                    "TIdak Terbukti"
                } else {
                    "Terbukti"
                }
                itemView.txt_isTerbukti.text = terbukti
            }

            override fun onItemClicked(itemView: View, data: LhpResp, itemIndex: Int) {
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