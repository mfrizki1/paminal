package id.calocallo.sicape.ui.main.addpersonal.mediainfo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.MedInfoReq
import id.calocallo.sicape.model.ParentListMedInfo
import id.calocallo.sicape.ui.main.addpersonal.medsos.AddMedsosActivity
import id.calocallo.sicape.utils.SessionManager1
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_media_informasi.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddMedianfoActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private lateinit var list: ArrayList<MedInfoReq>
    private lateinit var parentList: ParentListMedInfo
    lateinit var adapter: MediaInfoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_media_informasi)
        sessionManager1 = SessionManager1(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Media Informasi"
        list = ArrayList()
        parentList = ParentListMedInfo(list)

        initRV()

        btn_next_med_info.setOnClickListener {
            if (list.size == 1 && list[0].sumber == "") {
                list.clear()
            }
            sessionManager1.setMediaInfo(list)
            Log.e("size Media Info", "${sessionManager1.getMediaInfo()}")
            startActivity(Intent(this, AddMedsosActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun initRV() {
        val media = sessionManager1.getMediaInfo()
        if(media.size == 1){
            for (i in 0 until media.size){
                list.add(i, MedInfoReq(
                    media[i].sumber,
                    media[i].topik,
                    media[i].alasan,
                    media[i].keterangan
                )
                )
            }
        }else {
            list.add(
                MedInfoReq(
                    "",
                    "",
                    "",
                    ""
                )
            )
        }
        rv_med_info.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = MediaInfoAdapter(this, list, object : MediaInfoAdapter.OnClickMedInfo {
            override fun onDelete(position: Int) {
                list.removeAt(position)
                adapter.notifyDataSetChanged()
            }
        })
        rv_med_info.adapter = adapter

        btn_add_med_info.setOnClickListener {
            list.add(
                MedInfoReq(
                    "",
                    "",
                    "",
                    ""
                )
            )
            val position = if (list.isEmpty()) 0 else list.size - 1
            adapter.notifyItemInserted(position)
            adapter.notifyDataSetChanged()
        }

    }
}