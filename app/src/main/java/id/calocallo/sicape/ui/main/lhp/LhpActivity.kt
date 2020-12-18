package id.calocallo.sicape.ui.main.lhp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import id.calocallo.sicape.R
import id.calocallo.sicape.model.*
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_lhp.*
import kotlinx.android.synthetic.main.activity_personel.*
import kotlinx.android.synthetic.main.item_lhp.view.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback

class LhpActivity : BaseActivity() {
    private lateinit var list: ArrayList<LhpModel>
    private lateinit var listLidik : ArrayList<ListLidik>
    private lateinit var listSaksi : ArrayList<ListSaksi>
    private lateinit var listSurat : ArrayList<ListSurat>
    private lateinit var listPetunjuk : ArrayList<ListPetunjuk>
    private lateinit var listBukti : ArrayList<ListBukti>
    private lateinit var listAnalisa : ArrayList<ListAnalisa>
    private lateinit var listTerlapor : ArrayList<ListTerlapor>
//    private lateinit var adapterLhp: LhpAdapter
    private lateinit var adapterLhp: ReusableAdapter<LhpModel>
    private lateinit var adapterCallbackLhp: AdapterCallback<LhpModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lhp)

        list = ArrayList()
        listLidik = ArrayList()
        listSaksi = ArrayList()
        listSurat = ArrayList()
        listBukti = ArrayList()
        listPetunjuk = ArrayList()
        listAnalisa = ArrayList()
        listTerlapor = ArrayList()
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Laporan Hasil Penyelidikan"

        listLidik.add(ListLidik("Utuh", "Briptu", "987655123"))
        listLidik.add(ListLidik("Galuh", "Bripda", "1345678"))
        listLidik.add(ListLidik("Julak", "Jenderal", "2121124"))

        listSaksi.add(ListSaksi("Saksi 1", "Uraian Saksi 1"))
        listSaksi.add(ListSaksi("Saksi 2", "Uraian Saksi 2"))

        listSurat.add(ListSurat("Surat a"))
        listSurat.add(ListSurat("Surat B"))
        listSurat.add(ListSurat("Surat cdr"))

        listPetunjuk.add(ListPetunjuk("abc"))
        listPetunjuk.add(ListPetunjuk("detpvfdnssd"))
        listPetunjuk.add(ListPetunjuk("saduhasnfeslffojfd"))

        listBukti.add(ListBukti("asdv"))
        listBukti.add(ListBukti("acdftre"))

        listAnalisa.add(ListAnalisa("Analisa 1 dibuktikan bahwa pelaku melakukan abc dan meninggalkan tempat pada xxx "))

        listTerlapor.add(ListTerlapor("Hariyanto", "Melakukan Samting samting"))
        listTerlapor.add(ListTerlapor("Sri Wahyuni", "Sedang tidur pada posko"))

        list.add(
            LhpModel(
                "NO LHP 124/xx/2020/BIDPROPAM", "22", "INI PENGADUAN", listLidik, "",
                "", "", "", "",
                "", listSaksi, listSurat, "", listPetunjuk,
                listBukti, listTerlapor, listAnalisa, "", "", "Faisal",
                "", "", 0
            )
        )
        list.add(
            LhpModel(
                "124567", "22123", "INI ASDNJDSNJS", ArrayList(),
                "", "", "", "",
                "", "", ArrayList(), ArrayList(), "",
                ArrayList(), ArrayList(), ArrayList(), ArrayList(), "", "",
                "Rizki", "", "", 1
            )
        )

        list.add(
            LhpModel(
                "123/BIDPROPAM", "22123", "INI ASDNJDSNJS", ArrayList(),
                "", "", "", "",
                "", "", ArrayList(), ArrayList(), "",
                ArrayList(), ArrayList(), ArrayList(), ArrayList(), "", "",
                "Ahmad", "", "", 1
            )
        )


        //adapterLibrary
        adapterLhp = ReusableAdapter(this)
        adapterCallbackLhp = object :AdapterCallback<LhpModel>{
            override fun initComponent(itemView: View, data: LhpModel, itemIndex: Int) {
                var terbukti: String
                itemView.txt_no_lhp.text = data.no_lhp
                itemView.txt_ketua_tim.text = data.nama_ketua_tim
                if(data.isTerbukti == 0){
                    terbukti = "Terbukti"
                }else{
                    terbukti = "TIdak Terbukti"
                }
                itemView.txt_isTerbukti.text = terbukti
            }

            override fun onItemClicked(itemView: View, data: LhpModel, itemIndex: Int) {
                val intent = Intent(this@LhpActivity, DetailLhpActivity::class.java)
                intent.putExtra("LHP", data)
                startActivity(intent)
            }
        }
        adapterLhp.filterable()
            .adapterCallback(adapterCallbackLhp)
            .setLayout(R.layout.item_lhp)
            .isVerticalView()
            .addData(list)
            .build(rv_lhp)

        /*
        rv_lhp.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapterLhp = LhpAdapter(this, list, object : LhpAdapter.OnClickLhp {
            override fun onClick(position: Int) {
                val intent = Intent(this@LhpActivity, DetailLhpActivity::class.java)
                intent.putExtra("LHP", list[position])
                startActivity(intent)
            }
        })
        rv_lhp.adapter = adapterLhp

         */

        btn_lhp_add.setOnClickListener { 
            startActivity(Intent(this, AddLhpActivity::class.java))
        }

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