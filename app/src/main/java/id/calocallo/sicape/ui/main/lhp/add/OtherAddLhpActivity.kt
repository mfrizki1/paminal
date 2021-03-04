package id.calocallo.sicape.ui.main.lhp.add

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.model.AddLhpResp
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.LhpReq
import id.calocallo.sicape.network.response.Base1Resp
import id.calocallo.sicape.ui.main.MainActivity
import id.calocallo.sicape.ui.main.lhp.LhpActivity
import id.calocallo.sicape.utils.LhpDataManager
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.action
import id.calocallo.sicape.utils.ext.showSnackbar
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_relasi.*
import kotlinx.android.synthetic.main.activity_other_add_lhp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OtherAddLhpActivity : BaseActivity() {
    private lateinit var lhpDataManager: LhpDataManager
    private lateinit var sessionManager1: SessionManager1
    private var lhpReq = LhpReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_add_lhp)
        setupActionBarWithBackButton(toolbar)
        sessionManager1 = SessionManager1(this)
        supportActionBar?.title = "Tambah Data Laporan Hasil Penyelidikan"
        lhpDataManager = LhpDataManager(this)

        btn_add_all_lhp.attachTextChangeAnimator()
        bindProgressButton(btn_add_all_lhp)
        btn_add_all_lhp.setOnClickListener {
            addAllLHP()

        }

    }

    private fun addAllLHP() {
        btn_add_all_lhp.showProgress {
            progressColor = Color.WHITE
        }
        lhpReq.no_lhp = lhpDataManager.getNoLHP()
        lhpReq.tentang = lhpDataManager.getTentangLHP()
        lhpReq.no_surat_perintah_penyelidikan = lhpDataManager.getSPLHP()
        lhpReq.tanggal_mulai_penyelidikan = lhpDataManager.getWaktuLHP()
//        lhpReq.wilayah_hukum_penyelidikan = lhpDataManager.getDaerahLHP()
        lhpReq.pokok_permasalahan = lhpDataManager.getPokokPermasalahanLHP()
        lhpReq.keterangan_ahli = lhpDataManager.getKetAhliLHP()
        lhpReq.kesimpulan = lhpDataManager.getKesimpulanLHP()
        lhpReq.rekomendasi = lhpDataManager.getRekomendasiLHP()
        lhpReq.tugas_pokok = lhpDataManager.getTugasPokokLHP()
        lhpReq.isTerbukti = lhpDataManager.getIsTerbukti()
        lhpReq.referensi_penyelidikan = lhpDataManager.getListRefLp()
        lhpReq.personel_penyelidik = lhpDataManager.getListLidikLHP()
        lhpReq.saksi = lhpDataManager.getListSaksiLHP()
//        lhpReq.keterangan_terlapor = lhpDataManager.getListTerlaporLHP()
        lhpReq.barang_bukti = edt_barang_bukti_lhp_add.text.toString()
        lhpReq.analisa = edt_analisa_lhp_add.text.toString()
        lhpReq.surat = edt_surat_lhp_add.text.toString()
        lhpReq.petunjuk = edt_petunjuk_lhp_add.text.toString()
        lhpReq.kota_buat_laporan = edt_kota_buat_add_lhp.text.toString()
        lhpReq.tanggal_buat_laporan = edt_tgl_buat_add_lhp.text.toString()
        Log.e("add all LHP", "$lhpReq")

        apiAddLhp()
    }

    private fun apiAddLhp() {
        NetworkConfig().getServLhp().addLhp("Bearer ${sessionManager1.fetchAuthToken()}", lhpReq).enqueue(
            object :
                Callback<Base1Resp<AddLhpResp>> {
                override fun onResponse(
                    call: Call<Base1Resp<AddLhpResp>>,
                    response: Response<Base1Resp<AddLhpResp>>
                ) {
                    if (response.body()?.message == "Data lhp saved succesfully") {
                        btn_add_all_lhp.showSnackbar(R.string.data_saved) {
                            action(R.string.next) {
                                lhpDataManager.clearLHP()
                                val intent = Intent(this@OtherAddLhpActivity, LhpActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                startActivity(intent)
                                finish()
                            }
                        }
                    } else {
                        btn_add_all_lhp.hideProgress(R.string.data_saved)
                    }
                }

                override fun onFailure(call: Call<Base1Resp<AddLhpResp>>, t: Throwable) {
                    Toast.makeText(this@OtherAddLhpActivity, "$t", Toast.LENGTH_SHORT).show()
                    btn_add_all_lhp.hideProgress(R.string.data_saved)
                }
            })
    }
}