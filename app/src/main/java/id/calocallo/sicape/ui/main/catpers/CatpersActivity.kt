package id.calocallo.sicape.ui.main.catpers

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import id.calocallo.sicape.R
import id.calocallo.sicape.model.CatpersModel
import kotlinx.android.synthetic.main.activity_catpres.*

class CatpersActivity : AppCompatActivity(), CatpersAdapter.CatpersListener {
    lateinit var list: ArrayList<CatpersModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catpres)
        list = ArrayList<CatpersModel>()
        list.add(CatpersModel("Faisal", "LP / 01 / 01 / 2020 / SIPROGRAM", "BRIPDA / 123456", "Disiplin", "PASAL 6", "PATSUS", "MASA HUKUMAN"))
        list.add(CatpersModel("Rizki", "LP / 01 / 01 / 2020 / SIPROGRAM", "BRIPDA / 123456", "Disiplin", "PASAL 6", "PATSUS", "MASA HUKUMAN"))
        list.add(CatpersModel("Muhammad", "LP / 01 / 01 / 2020 / SIPROGRAM", "BRIPDA / 123456", "Disiplin", "PASAL 6", "PATSUS", "MASA HUKUMAN"))
        initAPI()
        btn_add.setOnClickListener {
            startActivity(Intent(this@CatpersActivity, AddCatpersActivity::class.java))
        }

}

    private fun initAPI() {
        rv_catpers.setHasFixedSize(true)
        rv_catpers.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val adapter = CatpersAdapter(this, list, this)
        rv_catpers.adapter =adapter
    }

    override fun onClick(position: Int) {
        Toast.makeText(this, list[position].name, Toast.LENGTH_SHORT).show()
    }
}
