package id.calocallo.sicape.ui.main.putkke

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.model.LpOnSkhd
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.PutKkeReq
import id.calocallo.sicape.network.response.AddPutKkeResp
import id.calocallo.sicape.network.response.Base1Resp
import id.calocallo.sicape.ui.main.choose.lhp.ChooseLhpActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.action
import id.calocallo.sicape.utils.ext.showSnackbar
import id.calocallo.sicape.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_put_kke.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddPutKkeActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var idLhp: Int? = null
    private var idLp: Int? = null
    private var putKkeReq = PutKkeReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_put_kke)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Data Putusan Kode Etik"
        sessionManager1 = SessionManager1(this)

        btn_save_put_kke_add.attachTextChangeAnimator()
        bindProgressButton(btn_save_put_kke_add)
        btn_save_put_kke_add.setOnClickListener {
            addPutKke()
        }

        /*      btn_pick_lhp_put_kke_add.setOnClickListener {
                  val intent = Intent(this, ChooseLhpActivity::class.java)
                  startActivityForResult(intent, REQ_CHOOSE_LHP)
                  overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
              }*/
        btn_pick_lp_put_kke_add.setOnClickListener {
//            val intent = Intent(this, ChooseLpSkhdActivity::class.java)
            val intent = Intent(this, ChooseLhpActivity::class.java).apply {
                this.putExtra(IS_PUTTKE, true)
            }
//            intent.putExtra(IDLHP_PUTKKE, idLhp)
            startActivityForResult(intent, REQ_CHOOSE_LP)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun addPutKke() {
        putKkeReq.id_lp = idLp

        putKkeReq.no_berkas_pemeriksaan_pendahuluan =
            edt_no_berkas_pemeriksaan_put_kke_add.text.toString()

        putKkeReq.tanggal_berkas_pemeriksaan_pendahuluan =
            edt_tanggal_penetapan_put_kke_add.text.toString()

        putKkeReq.no_keputusan_kapolda_kalsel =
            edt_no_keputusan_kapolda_put_kke_add.text.toString()

        putKkeReq.tanggal_keputusan_kapolda_kalsel =
            edt_tgl_keputusan_kapolda_put_kke_add.text.toString()

        putKkeReq.no_surat_persangkaan_pelanggaran_kode_etik =
            edt_no_surat_persangkaan_put_kke_add.text.toString()

        putKkeReq.tanggal_surat_persangkaan_pelanggaran_kode_etik =
            edt_tgl_surat_persangkaan_put_kke_add.text.toString()

        putKkeReq.no_tuntutan_pelanggaran_kode_etik =
            edt_no_tuntunan_pelanggaran_put_kke_add.text.toString()

        putKkeReq.tanggal_tuntutan_pelanggaran_kode_etik =
            edt_tgl_tuntunan_pelanggaran_put_kke_add.text.toString()

        putKkeReq.sanksi_hasil_keputusan = edt_sanksi_hasil_putusan_put_kke_add.text.toString()
        putKkeReq.lokasi_sidang = edt_lokasi_sidang_put_kke_add.text.toString()
        putKkeReq.tanggal_putusan = edt_tanggal_penetapan_put_kke_add.text.toString()

        putKkeReq.nama_ketua_komisi = edt_nama_ketua_komisi_put_kke_add.text.toString()
        putKkeReq.pangkat_ketua_komisi =
            edt_pangkat_ketua_komisi_put_kke_add.text.toString().toUpperCase()

        putKkeReq.nrp_ketua_komisi = edt_nrp_ketua_komisi_put_kke_add.text.toString()

        putKkeReq.jabatan_ketua_komisi =
            edt_jabatan_ketua_komisi_put_kke_add.text.toString()

        putKkeReq.kesatuan_ketua_komisi = edt_kesatuan_ketua_komisi_put_kke_add.text.toString()

        putKkeReq.nama_wakil_ketua_komisi = edt_nama_wakil_ketua_komisi_put_kke_add.text.toString()

        putKkeReq.pangkat_wakil_ketua_komisi =
            edt_pangkat_wakil_ketua_komisi_put_kke_add.text.toString().toUpperCase()

        putKkeReq.nrp_wakil_ketua_komisi = edt_nrp_wakil_ketua_komisi_put_kke_add.text.toString()
        putKkeReq.jabatan_wakil_ketua_komisi =
            edt_jabatan_wakil_ketua_komisi_put_kke_add.text.toString()

        putKkeReq.kesatuan_wakil_ketua_komisi =
            edt_kesatuan_wakil_ketua_komisi_put_kke_add.text.toString()

        putKkeReq.nama_anggota_komisi = edt_nama_anggota_komisi_put_kke_add.text.toString()
        putKkeReq.pangkat_anggota_komisi =
            edt_pangkat_anggota_komisi_put_kke_add.text.toString().toUpperCase()

        putKkeReq.nrp_anggota_komisi = edt_nrp_anggota_komisi_put_kke_add.text.toString()
        putKkeReq.jabatan_anggota_komisi =
            edt_jabatan_anggota_komisi_put_kke_add.text.toString()

        putKkeReq.kesatuan_anggota_komisi =
            edt_kesatuan_anggota_komisi_put_kke_add.text.toString()
        /*  putKkeReq.no_putkke = edt_no_put_kke_add.text.toString()
          putKkeReq.pembukaan_putusan = edt_pembukan_putusan_add.text.toString()
          putKkeReq.menimbang_p2 = edt_berkas_pemeriksaan_put_kke_add.text.toString()
          putKkeReq.mengingat_p4 = edt_keputusan_kapolda_put_kke_add.text.toString()
          putKkeReq.memperhatikan_p1 = edt_memperhatikan_p1_put_kke_add.text.toString()
          putKkeReq.memperhatikan_p3 = edt_memperhatikan_p3_put_kke_add.text.toString()
          putKkeReq.memperhatikan_p5 = edt_memperhatikan_p5_put_kke_add.text.toString()
          putKkeReq.memutuskan_p1 = edt_memutuskan_p1_put_kke_add.text.toString()
          putKkeReq.sanksi_rekomendasi = edt_sanksi_rekomendasi_put_kke_add.text.toString()*/
        /*putKkeReq.id_lhp = idLhp*/
        /*   putKkeReq.kota_putusan = edt_kota_penetapan_put_kke_add.text.toString()*/
        Log.e("add PUTKKE", "$putKkeReq")

        btn_save_put_kke_add.showProgress {
            progressColor = Color.WHITE
        }
        apiAddPutKke()
    }

    private fun apiAddPutKke() {
        NetworkConfig().getServSkhd()
            .addPutKke("Bearer ${sessionManager1.fetchAuthToken()}", putKkeReq).enqueue(
                object :
                    Callback<Base1Resp<AddPutKkeResp>> {
                    override fun onResponse(
                        call: Call<Base1Resp<AddPutKkeResp>>,
                        response: Response<Base1Resp<AddPutKkeResp>>
                    ) {
                        if (response.body()?.message == "Data putkke saved succesfully") {
                            btn_save_put_kke_add.hideProgress(R.string.data_saved)
                            btn_save_put_kke_add.showSnackbar(R.string.data_saved){
                                action(R.string.next){
                                    val intent = Intent(this@AddPutKkeActivity, ListPutKkeActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                                }
                                startActivity(intent)
                            }
                        } else {
                            btn_save_put_kke_add.hideProgress(R.string.not_save)
                        }
                    }

                    override fun onFailure(call: Call<Base1Resp<AddPutKkeResp>>, t: Throwable) {
                        Toast.makeText(this@AddPutKkeActivity, "$t", Toast.LENGTH_SHORT).show()
                        btn_save_put_kke_add.hideProgress(R.string.not_save)
                    }
                })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_CHOOSE_LP) {
            when (resultCode) {
                ChooseLhpActivity.RES_LP_CHOSE_LHP -> {
                    val lpKke =
                        data?.getParcelableExtra<LpOnSkhd>(ChooseLhpActivity.DATA_LP)
                    idLp = lpKke?.lp?.id
                    txt_lp_put_kke_add.text = lpKke?.lp?.no_lp
                }
            }
        }
    }

    companion object {
        const val REQ_CHOOSE_LHP = 1
        const val REQ_CHOOSE_LP = 1
        const val IDLHP_PUTKKE = "IDLHP_PUTKKE"
        const val DATA_KKE = "DATA_KKE"
        const val IS_PUTTKE = "IS_PUTTKE"
    }
}