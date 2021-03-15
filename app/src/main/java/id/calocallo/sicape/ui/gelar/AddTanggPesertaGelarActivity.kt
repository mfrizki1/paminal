package id.calocallo.sicape.ui.gelar

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.LhgReq
import id.calocallo.sicape.network.response.AddLhgResp
import id.calocallo.sicape.network.response.Base1Resp
import id.calocallo.sicape.network.response.PesertaLhgResp
import id.calocallo.sicape.utils.GelarDataManager
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_tangg_peserta_gelar.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddTanggPesertaGelarActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private lateinit var gelarDataManager: GelarDataManager
    private lateinit var adapterTanggPeserta: AddTanggPesertaAdapter
    private var listTanggapan = ArrayList<PesertaLhgResp>()
    private var lhgReq = LhgReq()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_tangg_peserta_gelar)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tanggapan Peserta Gelar"
        sessionManager1 = SessionManager1(this)
        gelarDataManager = GelarDataManager(this)
        Logger.addLogAdapter(AndroidLogAdapter())
        setAdapterGelar3()

        val mLhgReq = gelarDataManager.getGelar1()
        lhgReq.id_lp = mLhgReq.id_lp
        lhgReq.pasal_kuh_pidana = mLhgReq.pasal_kuh_pidana
        lhgReq.dugaan = mLhgReq.dugaan
        lhgReq.pangkat_yang_menangani = mLhgReq.pangkat_yang_menangani
        lhgReq.nama_yang_menangani = mLhgReq.nama_yang_menangani
        lhgReq.dasar = mLhgReq.dasar
        lhgReq.tanggal = mLhgReq.tanggal
        lhgReq.waktu_selesai = mLhgReq.waktu_selesai
        lhgReq.waktu_mulai = mLhgReq.waktu_mulai
        lhgReq.tempat = mLhgReq.tempat
        lhgReq.pangkat_pimpinan = mLhgReq.pangkat_pimpinan
        lhgReq.nama_pimpinan = mLhgReq.nama_pimpinan
        lhgReq.nrp_pimpinan = mLhgReq.nrp_pimpinan
        lhgReq.pangkat_pemapar = mLhgReq.pangkat_pemapar
        lhgReq.nama_pemapar = mLhgReq.nama_pemapar
        lhgReq.kronologis_kasus = mLhgReq.kronologis_kasus
        lhgReq.no_surat_perintah_penyidikan = mLhgReq.no_surat_perintah_penyidikan
        lhgReq.nama_notulen = mLhgReq.nama_notulen
        lhgReq.pangkat_notulen = mLhgReq.pangkat_notulen
        lhgReq.nrp_notulen = mLhgReq.nrp_notulen
        lhgReq.peserta_gelar = listTanggapan

        btn_next_tanggapan_peserta_gelar.attachTextChangeAnimator()
        bindProgressButton(btn_next_tanggapan_peserta_gelar)
        btn_next_tanggapan_peserta_gelar.setOnClickListener {
            /* saveTanggapanPeserta()
             startActivity(Intent(this, AddGelar4Activity::class.java))
             overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)*/
            btn_next_tanggapan_peserta_gelar.showProgress {
                progressColor = Color.WHITE
            }
            apiAddGelar()
        }
    }

    private fun apiAddGelar() {
        NetworkConfig().getServLhg().addLhg("Bearer ${sessionManager1.fetchAuthToken()}", lhgReq)
            .enqueue(
                object :
                    Callback<Base1Resp<AddLhgResp>> {
                    override fun onResponse(
                        call: Call<Base1Resp<AddLhgResp>>,
                        response: Response<Base1Resp<AddLhgResp>>
                    ) {
                        if (response.body()?.message == "Data lhg saved succesfully") {
                            btn_next_tanggapan_peserta_gelar.hideProgress(R.string.data_saved)
                            val intent =
                                Intent(
                                    this@AddTanggPesertaGelarActivity,
                                    ListGelarActivity::class.java
                                )
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                            startActivity(intent)
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                            gelarDataManager.clearGelar()
                            finish()
                        } else {
                            btn_next_tanggapan_peserta_gelar.hideProgress(R.string.not_save)

                        }
                    }

                    override fun onFailure(call: Call<Base1Resp<AddLhgResp>>, t: Throwable) {
                        btn_next_tanggapan_peserta_gelar.hideProgress(R.string.not_save)
                        Toast.makeText(this@AddTanggPesertaGelarActivity, "$t", Toast.LENGTH_SHORT)
                            .show()
                    }
                })
    }

    private fun setAdapterGelar3() {
        listTanggapan.add(PesertaLhgResp())
        listTanggapan.add(PesertaLhgResp())

        rv_tanggapan_peserta_gelar.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        adapterTanggPeserta =
            AddTanggPesertaAdapter(
                this,
                listTanggapan,
                object : AddTanggPesertaAdapter.OnClickTanggPeserta {
                    override fun onDelete(position: Int) {
                        listTanggapan.removeAt(position)
                        adapterTanggPeserta.notifyItemRemoved(position)
                    }
                })
        rv_tanggapan_peserta_gelar.adapter = adapterTanggPeserta
        btn_add_item_tanggapan_peserta_gelar.setOnClickListener {
            val position = if (listTanggapan.isEmpty()) 0 else listTanggapan.size - 1
            listTanggapan.add(PesertaLhgResp())
            adapterTanggPeserta.notifyItemChanged(position)
            adapterTanggPeserta.notifyItemInserted(position)
        }
    }

    /*private fun saveTanggapanPeserta() {
        if (listTanggapan[0].nama_peserta == null) {
            listTanggapan.clear()
        } else {
//            (listTanggapan as ArrayList<TanggPesertaModel>).trimToSize()
            gelarDataManager.setTanggPesertaGelar(listTanggapan)
        }

        Logger.e("${gelarDataManager.getTanggPesertaGelar()}")
    }*/
}