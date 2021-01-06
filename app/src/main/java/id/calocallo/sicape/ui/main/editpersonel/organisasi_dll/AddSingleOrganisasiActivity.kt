package id.calocallo.sicape.ui.main.editpersonel.organisasi_dll

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.OrganisasiReq
import id.calocallo.sicape.network.request.PenghargaanReq
import id.calocallo.sicape.network.request.PerjuanganCitaReq
import id.calocallo.sicape.model.PersonelModel
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_single_organisasi.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddSingleOrganisasiActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private val organisasiReq =
        OrganisasiReq()
    private val penghargaanReq =
        PenghargaanReq()
    private val perjuanganCitaReq =
        PerjuanganCitaReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_single_organisasi)

        sessionManager = SessionManager(this)
        val bundle = intent.extras
        val detail = bundle?.getParcelable<PersonelModel>("PERSONEL_DETAIL")
        val misc = bundle?.getString("MISC")
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = detail?.nama.toString()
        txt_tambah_data_org_dll.text = "Tambah Data"
        if (misc == "perjuangan") {
            viewPerjuangan()
        } else if (misc == "penghargaan") {
            viewPenghargaan()

        } else {
            viewOrganisasi()
        }
        btn_pick_organisasi.setOnClickListener {
            viewOrganisasi()

        }
        btn_pick_perjuangan.setOnClickListener {
            viewPerjuangan()
        }
        btn_pick_penghargaan.setOnClickListener {
            viewPenghargaan()
        }
        bindProgressButton(btn_save_single_organisasi)
        btn_save_single_organisasi.attachTextChangeAnimator()
        btn_save_single_organisasi.setOnClickListener {
            doOrganisasiApi()
        }

        bindProgressButton(btn_save_single_penghargaan)
        btn_save_single_penghargaan.attachTextChangeAnimator()
        btn_save_single_penghargaan.setOnClickListener {
            doPenghargaanApi()
        }

        bindProgressButton(btn_save_single_perjuangan)
        btn_save_single_perjuangan.attachTextChangeAnimator()
        btn_save_single_perjuangan.setOnClickListener {
            doPerjuanganApi()
        }
    }

    private fun viewOrganisasi() {
        txt_tambah_data_org_dll.text = "Tambah Data Organisasi"
        ll_organisasi.visible()
        ll_penghargaan.gone()
        ll_perjuangan.gone()
        btn_pick_organisasi.setBackgroundColor(resources.getColor(R.color.colorPrimary))
        btn_pick_organisasi.setTextColor(resources.getColor(R.color.white))

        btn_pick_perjuangan.setBackgroundColor(resources.getColor(R.color.white))
        btn_pick_perjuangan.typeface = Typeface.DEFAULT_BOLD
        btn_pick_perjuangan.setTextColor(resources.getColor(R.color.colorPrimary))


        btn_pick_penghargaan.setBackgroundColor(resources.getColor(R.color.white))
        btn_pick_penghargaan.typeface = Typeface.DEFAULT_BOLD
        btn_pick_penghargaan.setTextColor(resources.getColor(R.color.colorPrimary))
    }

    private fun viewPerjuangan() {
        txt_tambah_data_org_dll.text = "Tambah Data Perjuangan Mencapai Cita-Cita"
        ll_perjuangan.visible()
        ll_penghargaan.gone()
        ll_organisasi.gone()
        btn_pick_perjuangan.setBackgroundColor(resources.getColor(R.color.colorPrimary))
        btn_pick_perjuangan.setTextColor(resources.getColor(R.color.white))

        btn_pick_organisasi.setBackgroundColor(resources.getColor(R.color.white))
        btn_pick_organisasi.typeface = Typeface.DEFAULT_BOLD
        btn_pick_organisasi.setTextColor(resources.getColor(R.color.colorPrimary))

        btn_pick_penghargaan.setBackgroundColor(resources.getColor(R.color.white))
        btn_pick_penghargaan.typeface = Typeface.DEFAULT_BOLD
        btn_pick_penghargaan.setTextColor(resources.getColor(R.color.colorPrimary))
    }

    private fun viewPenghargaan() {
        txt_tambah_data_org_dll.text = "Tambah Data Penghargaan"
        ll_perjuangan.gone()
        ll_penghargaan.visible()
        ll_organisasi.gone()

        btn_pick_penghargaan.setBackgroundColor(resources.getColor(R.color.colorPrimary))
        btn_pick_penghargaan.setTextColor(resources.getColor(R.color.white))

        btn_pick_organisasi.setBackgroundColor(resources.getColor(R.color.white))
        btn_pick_organisasi.typeface = Typeface.DEFAULT_BOLD
        btn_pick_organisasi.setTextColor(resources.getColor(R.color.colorPrimary))

        btn_pick_perjuangan.setBackgroundColor(resources.getColor(R.color.white))
        btn_pick_perjuangan.typeface = Typeface.DEFAULT_BOLD
        btn_pick_perjuangan.setTextColor(resources.getColor(R.color.colorPrimary))
    }

    private fun doPerjuanganApi() {
        val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        //Defined bounds are required for your drawable
        val drawableSize = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)

        btn_save_single_perjuangan.showProgress {
            progressColor = Color.WHITE
        }

//        rl_pb.visible()
//        ll_perjuangan.gone()
        perjuanganCitaReq.peristiwa = edt_single_peristiwa.text.toString()
        perjuanganCitaReq.lokasi = edt_tempat_single_peristiwa.text.toString()
        perjuanganCitaReq.tahun_awal = edt_single_thn_awal_perjuangan.text.toString()
        perjuanganCitaReq.tahun_akhir = edt_single_thn_akhir_perjuangan.text.toString()
        perjuanganCitaReq.dalam_rangka = edt_single_rangka_perjuangan.text.toString()
        perjuanganCitaReq.keterangan = edt_ket_single_perjuangan.text.toString()
        NetworkConfig().getService().addPerjuanganSingle(
            "Bearer ${sessionManager.fetchAuthToken()}",
//            "4",
            sessionManager.fetchID().toString(),
            perjuanganCitaReq
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                btn_save_single_perjuangan.hideDrawable(R.string.save)
                Toast.makeText(
                    this@AddSingleOrganisasiActivity,
                    "Error Koneksi",
                    Toast.LENGTH_SHORT
                ).show()
                rl_pb.gone()
                ll_perjuangan.visible()
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if (response.isSuccessful) {
                    btn_save_single_perjuangan.showDrawable(animatedDrawable) {
                        buttonTextRes = R.string.data_saved
                        textMarginRes = R.dimen.space_10dp
                    }
                    rl_pb.gone()
                    ll_perjuangan.visible()
                    Handler(Looper.getMainLooper()).postDelayed({
                        finish()
                    }, 500)
                } else {
                    Handler(Looper.getMainLooper()).postDelayed({
                        btn_save_single_perjuangan.hideDrawable(R.string.save)
                    }, 3000)
                    btn_save_single_perjuangan.hideDrawable(R.string.not_save)
                    rl_pb.gone()
                    ll_perjuangan.visible()
                }
            }
        })
    }

    private fun doPenghargaanApi() {
        val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        //Defined bounds are required for your drawable
        val drawableSize = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)

        btn_save_single_penghargaan.showProgress {
            progressColor = Color.WHITE
        }

//        rl_pb.visible()
//        ll_penghargaan.gone()
        penghargaanReq.penghargaan = edt_nama_single_penghargaan.text.toString()
        penghargaanReq.diterima_dari = edt_diterima_single_penghargaan.text.toString()
        penghargaanReq.dalam_rangka = edt_rangka_single_penghargaan.text.toString()
        penghargaanReq.tahun = edt_thn_single_penghargaan.text.toString()
        penghargaanReq.keterangan = edt_ket_single_penghargaan.text.toString()
        NetworkConfig().getService().addPenghargaanSingle(
            "Bearer ${sessionManager.fetchAuthToken()}",
//            "4",
            sessionManager.fetchID().toString(),
            penghargaanReq
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                btn_save_single_penghargaan.hideDrawable(R.string.save)
                Toast.makeText(
                    this@AddSingleOrganisasiActivity,
                    "Error Koneksi",
                    Toast.LENGTH_SHORT
                ).show()
                rl_pb.gone()
                ll_penghargaan.visible()

            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if (response.isSuccessful) {
                    btn_save_single_penghargaan.showDrawable(animatedDrawable) {
                        buttonTextRes = R.string.data_saved
                        textMarginRes = R.dimen.space_10dp
                    }

                    rl_pb.gone()
                    ll_penghargaan.visible()
                    Handler(Looper.getMainLooper()).postDelayed({
                        finish()
                    }, 500)
                } else {
                    Handler(Looper.getMainLooper()).postDelayed({
                        btn_save_single_penghargaan.hideDrawable(R.string.save)
                    }, 3000)
                    btn_save_single_penghargaan.hideDrawable(R.string.not_save)

                    rl_pb.gone()
                    ll_penghargaan.visible()
                }
            }

        })
    }

    private fun doOrganisasiApi() {
        val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        //Defined bounds are required for your drawable
        val drawableSize = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)

//        rl_pb.visible()
//        ll_organisasi.gone()
        organisasiReq.organisasi = edt_single_organisasi.text.toString()
        organisasiReq.tahun_awal = edt_thn_awal_single_organisasi.text.toString()
        organisasiReq.tahun_akhir = edt_thn_akhir_single_organisasi.text.toString()
        organisasiReq.jabatan = edt_kedudukan_single_organisasi.text.toString()
        organisasiReq.tahun_bergabung = edt_thn_ikut_single_organisasi.text.toString()
        organisasiReq.alamat = edt_alamat_single_organisasi.text.toString()
        organisasiReq.keterangan = edt_ket_single_organisasi.text.toString()
        btn_save_single_organisasi.showProgress {
            progressColor = Color.WHITE
        }

        NetworkConfig().getService().addOrganisasiSingle(
            "Bearer ${sessionManager.fetchAuthToken()}",
//            "4",
            sessionManager.fetchID().toString(),
            organisasiReq
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                btn_save_single_organisasi.hideDrawable(R.string.save)
                Toast.makeText(
                    this@AddSingleOrganisasiActivity,
                    "Error Koneksi",
                    Toast.LENGTH_SHORT
                ).show()
                rl_pb.gone()
                ll_organisasi.visible()

            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if (response.isSuccessful) {
                    btn_save_single_organisasi.showDrawable(animatedDrawable) {
                        buttonTextRes = R.string.data_saved
                        textMarginRes = R.dimen.space_10dp
                    }
                    rl_pb.gone()
                    ll_organisasi.visible()
                    Handler(Looper.getMainLooper()).postDelayed({
                        finish()
                    }, 500)

                } else {
                    Handler(Looper.getMainLooper()).postDelayed({
                        btn_save_single_organisasi.hideDrawable(R.string.save)
                    }, 3000)
                    btn_save_single_organisasi.hideDrawable(R.string.not_save)

                    rl_pb.gone()
                    ll_organisasi.visible()
                }
            }
        })
    }
}