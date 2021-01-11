package id.calocallo.sicape.ui.main.choose.lhp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.LhpResp
import id.calocallo.sicape.network.response.KetTerlaporLhpResp
import id.calocallo.sicape.network.response.PersonelPenyelidikResp
import id.calocallo.sicape.network.response.RefPenyelidikanResp
import id.calocallo.sicape.network.response.SaksiLhpResp
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.toggleVisibility
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_choose_lhp.*
import kotlinx.android.synthetic.main.layout_1_text_clickable.view.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback

class ChooseLhpActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private var list = arrayListOf<LhpResp>()
    private var refPenyelidikan = ArrayList<RefPenyelidikanResp>()
    private var personelPenyelidikan = ArrayList<PersonelPenyelidikResp>()
    private var saksiLhp = ArrayList<SaksiLhpResp>()
    private var ketTerlaporLhpResp = ArrayList<KetTerlaporLhpResp>()
    private var adapterChooseLhp = ReusableAdapter<LhpResp>(this)
    private lateinit var callbackChooseLhp: AdapterCallback<LhpResp>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_lhp)
        sessionManager = SessionManager(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Pilih Data LHP"
        getListLhp()
    }

    private fun getListLhp() {
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

        callbackChooseLhp = object :AdapterCallback<LhpResp>{
            override fun initComponent(itemView: View, data: LhpResp, itemIndex: Int) {
                itemView.txt_1_clickable.text = data.no_lhp
            }

            override fun onItemClicked(itemView: View, data: LhpResp, itemIndex: Int) {
                itemView.img_clickable.toggleVisibility()
                val intent = Intent()
                intent.putExtra("CHOOSE_LHP", data)
                setResult(RESULT_OK, intent)
                finish()}

        }
        adapterChooseLhp.adapterCallback(callbackChooseLhp)
            .isVerticalView()
            .addData(list)
            .setLayout(R.layout.layout_1_text_clickable)
            .build(rv_choose_lhp)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_bar, menu)
        val item = menu?.findItem(R.id.action_search)
        val searchView = item?.actionView as SearchView
        searchView.queryHint = "Cari LHP"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterChooseLhp.filter.filter(newText)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }
}