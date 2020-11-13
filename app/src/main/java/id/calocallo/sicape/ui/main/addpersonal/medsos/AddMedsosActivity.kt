package id.calocallo.sicape.ui.main.addpersonal.medsos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import id.calocallo.sicape.R
import id.calocallo.sicape.model.AnakModel
import id.calocallo.sicape.model.MedsosModel
import id.calocallo.sicape.model.ParentListAnak
import id.calocallo.sicape.model.ParentListMedsos
import id.calocallo.sicape.ui.main.addpersonal.AddFotoActivity
import id.calocallo.sicape.ui.main.addpersonal.AddRelasiActivity
import id.calocallo.sicape.ui.main.addpersonal.anak.AnakAdapter
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_medsos.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddMedsosActivity : BaseActivity() {
    private lateinit var list: ArrayList<MedsosModel>
    private lateinit var parentList: ParentListMedsos
    lateinit var adapter: MedSosAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_medsos)
        list = ArrayList()
        parentList = ParentListMedsos(list)

        intiRV()

        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Media Sosial"
        btn_next_medsos.setOnClickListener {
            startActivity(Intent(this, AddFotoActivity::class.java))
        }
    }

    private fun intiRV() {
        list.add(MedsosModel("", "", "", ""))
        list.add(MedsosModel("", "", "", ""))
        rv_medsos.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = MedSosAdapter(this, list, object : MedSosAdapter.OnClickMedsos {
            override fun onDelete(position: Int) {
                list.removeAt(position)
                adapter.notifyDataSetChanged()
            }
        })
        rv_medsos.adapter = adapter

        btn_add_medsos.setOnClickListener {
            list.add(MedsosModel("", "", "", ""))
            val position = if (list.isEmpty()) 0 else list.size - 1
            adapter.notifyItemInserted(position)
            adapter.notifyDataSetChanged()

        }
    }
}