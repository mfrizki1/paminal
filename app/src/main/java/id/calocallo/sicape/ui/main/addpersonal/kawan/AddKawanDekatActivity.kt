package id.calocallo.sicape.ui.main.addpersonal.kawan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import id.calocallo.sicape.R
import id.calocallo.sicape.model.KawanDekatModel
import id.calocallo.sicape.model.ParentListKawanDekat
import id.calocallo.sicape.ui.main.addpersonal.mediainfo.AddMedianfoActivity
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_kawan_dekat.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddKawanDekatActivity : BaseActivity() {
    private lateinit var list: ArrayList<KawanDekatModel>
    private lateinit var parentList: ParentListKawanDekat
    private lateinit var adapter: KawanAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_kawan_dekat)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Kawan Dekat"

        list = ArrayList()
        parentList = ParentListKawanDekat(list)
        initRV()

        btn_next_kawan.setOnClickListener {
            startActivity(Intent(this, AddMedianfoActivity::class.java))
        }
    }

    private fun initRV() {
        list.add(KawanDekatModel("", "", "", "", "", ""))
        list.add(KawanDekatModel("", "", "", "", "", ""))
        rv_kawan_dekat.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = KawanAdapter(this, list, object : KawanAdapter.OnClickKawan {
            override fun onDelete(position: Int) {
                list.removeAt(position)
                adapter.notifyDataSetChanged()
            }
        })
        rv_kawan_dekat.adapter = adapter

        btn_add_kawan.setOnClickListener {
            list.add(KawanDekatModel("", "", "", "", "", ""))
            val position = if (list.isEmpty()) 0 else list.size - 1
            adapter.notifyItemInserted(position)
            adapter.notifyDataSetChanged()
        }
    }
}