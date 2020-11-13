package id.calocallo.sicape.ui.main.personel

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import android.view.View
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PersonelModel
import id.calocallo.sicape.ui.main.addpersonal.AddPersonelActivity
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_personel.*
import kotlinx.android.synthetic.main.item_personel.view.*
import kotlinx.android.synthetic.main.layout_toolbar_white.toolbar
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback

class PersonelActivity : BaseActivity() {

    private lateinit var adapter: ReusableAdapter<PersonelModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personel)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Personel"
        setupActionBarWithBackButton(toolbar)


        //set adapter
        adapter = ReusableAdapter(this)

        //list data
        val listPersonel = listOf(
            PersonelModel("Nama", "L", "JABATAN", "BRIPTU", "1234567890", "POLDA BJM"),
            PersonelModel("Faisal", "L", "JABATAN", "BRIPTU", "1234567890", " POLDA BJB"),
            PersonelModel("Intan", "P", "JABATAN", "BRIPTU", "1234567890", "POLDA BJM"),
            PersonelModel("Muhammad", "L", "JABATAN", "BRIPTU", "1234567890", "POLDA TAPIN"),
            PersonelModel("Ridho", "L", "JABATAN", "BRIPTU", "1234567890", "POLDA HSS"),
            PersonelModel("Sulaiman", "L", "JABATAN", "BRIPTU", "1234567890", "POLDA BJB")
        )

        //create adapter callback
        val adapterCallback = object : AdapterCallback<PersonelModel> {
            override fun initComponent(itemView: View, data: PersonelModel, itemIndex: Int) {
                itemView.txt_personel_nama.text = data.nama_lengkap
                itemView.txt_personel_nrp.text = data.nrp
                itemView.txt_personel_pangkat.text = data.pangkat
            }

            override fun onItemClicked(itemView: View, data: PersonelModel, itemIndex: Int) {
                startActivity(Intent(this@PersonelActivity, DetailPersonelActivity::class.java))
            }
        }

        adapter.filterable()
            .adapterCallback(adapterCallback)
            .setLayout(R.layout.item_personel)
            .isVerticalView()
            .addData(listPersonel)
            .build(rv_personel)

        btn_personel_add.setOnClickListener {
            startActivity(Intent(this, AddPersonelActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_bar, menu)
        val item = menu?.findItem(R.id.action_search)
        val searchView = item?.actionView as SearchView
        searchView.queryHint = "Cari NRP"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }
}