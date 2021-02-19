package id.calocallo.sicape.ui.gelar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import id.calocallo.sicape.R
import id.calocallo.sicape.model.TanggPesertaModel
import id.calocallo.sicape.utils.GelarDataManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_gelar2.*
import kotlinx.android.synthetic.main.activity_add_gelar3.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddGelar3Activity : BaseActivity() {
    private lateinit var gelarDataManager: GelarDataManager
    private lateinit var adapterGelar3: AddGelar3Adapter
    private var listTanggapan = ArrayList<TanggPesertaModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_gelar3)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tanggapan Peserta Gelar"
        gelarDataManager = GelarDataManager(this)
        Logger.addLogAdapter(AndroidLogAdapter())
        setAdapterGelar3()

        btn_next_tanggapan_peserta_gelar.setOnClickListener {
            saveTanggapanPeserta()
            startActivity(Intent(this, AddGelar4Activity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun setAdapterGelar3() {
        listTanggapan.add(TanggPesertaModel())
        listTanggapan.add(TanggPesertaModel())

        rv_tanggapan_peserta_gelar.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        adapterGelar3 =
            AddGelar3Adapter(this, listTanggapan, object : AddGelar3Adapter.OnClickTanggPeserta {
                override fun onDelete(position: Int) {
                    listTanggapan.removeAt(position)
                    adapterGelar3.notifyDataSetChanged()
                }
            })
        rv_tanggapan_peserta_gelar.adapter = adapterGelar3
        btn_add_item_tanggapan_peserta_gelar.setOnClickListener {
            val position = if (listTanggapan.isEmpty()) 0 else listTanggapan.size - 1
            listTanggapan.add(TanggPesertaModel())
            adapterGelar3.notifyItemChanged(position)
            adapterGelar3.notifyDataSetChanged()
        }
    }

    private fun saveTanggapanPeserta() {
        if (listTanggapan[0].nama_peserta == null) {
            listTanggapan.clear()
        } else {
//            (listTanggapan as ArrayList<TanggPesertaModel>).trimToSize()
            gelarDataManager.setTanggPesertaGelar(listTanggapan)
        }

        Logger.e("${gelarDataManager.getTanggPesertaGelar()}")
    }
}