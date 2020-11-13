package id.calocallo.sicape.ui.main.addpersonal.mediainfo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import id.calocallo.sicape.R
import id.calocallo.sicape.model.AnakModel
import id.calocallo.sicape.model.MedInfoModel
import id.calocallo.sicape.model.ParentListAnak
import id.calocallo.sicape.model.ParentListMedInfo
import id.calocallo.sicape.ui.main.addpersonal.anak.AnakAdapter
import id.calocallo.sicape.ui.main.addpersonal.medsos.AddMedsosActivity
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_media_informasi.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddMedianfoActivity : BaseActivity() {
    private lateinit var list: ArrayList<MedInfoModel>
    private lateinit var parentList: ParentListMedInfo
    lateinit var adapter: MediaInfoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_media_informasi)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Media Informasi"
        list = ArrayList()
        parentList = ParentListMedInfo(list)

        initRV()

        btn_next_med_info.setOnClickListener {
            startActivity(Intent(this, AddMedsosActivity::class.java))
        }
    }

    private fun initRV() {
        list.add(MedInfoModel("", "", "", ""))
        list.add(MedInfoModel("", "", "", ""))
        rv_med_info.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = MediaInfoAdapter(this, list, object : MediaInfoAdapter.OnClickMedInfo {
            override fun onDelete(position: Int) {
                list.removeAt(position)
                adapter.notifyDataSetChanged()
            }
        })
        rv_med_info.adapter = adapter

        btn_add_med_info.setOnClickListener {
            list.add(MedInfoModel("", "", "", ""))
            val position = if (list.isEmpty()) 0 else list.size - 1
            adapter.notifyItemInserted(position)
            adapter.notifyDataSetChanged()
        }

    }
}