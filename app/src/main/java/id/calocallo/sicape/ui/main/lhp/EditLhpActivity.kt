package id.calocallo.sicape.ui.main.lhp

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.model.*
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.EditLhpReq
import id.calocallo.sicape.network.response.Base1Resp
import id.calocallo.sicape.network.response.LhpMinResp
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_lhp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditLhpActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private lateinit var adapterLidik: LidikAdapter
    private lateinit var adapterSaksi: SaksiAdapter
    private lateinit var adapterSurat: SuratAdapter
    private lateinit var adapterPetunjuk: PetunjukAdapter
    private lateinit var adapterBukti: BuktiAdapter
    private lateinit var adapterTerlapor: TerlaporAdapter
    private lateinit var adapterAnalisa: AnalisaAdapter
    private var editLhpReq = EditLhpReq()

    companion object {
        const val EDIT_LHP = "EDIT_LHP"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_lhp)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Edit Data Laporan Hasil Penyelidikan"
        sessionManager1 = SessionManager1(this)

        val bundle = intent.extras
        val editLHP = bundle?.getParcelable<LhpMinResp>(EDIT_LHP)

        apiDetailLhp(editLHP)
//        getViewLhpEdit(editLHP)
        val hak = sessionManager1.fetchHakAkses()
        if (hak == "operator") {
            btn_save_lhp_edit.gone()
        }

        bindProgressButton(btn_save_lhp_edit)
        btn_save_lhp_edit.attachTextChangeAnimator()
        btn_save_lhp_edit.setOnClickListener {
            updateLhp(editLHP)
        }

    }

    private fun apiDetailLhp(editLHP: LhpMinResp?) {
        NetworkConfig().getServLhp()
            .getLhpById("Bearer ${sessionManager1.fetchAuthToken()}", editLHP?.id).enqueue(
                object :
                    Callback<LhpResp> {
                    override fun onResponse(call: Call<LhpResp>, response: Response<LhpResp>) {
                        if (response.isSuccessful) {
                            getViewLhpEdit(response.body())
                        } else {
                            Toast.makeText(this@EditLhpActivity, R.string.error, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                    override fun onFailure(call: Call<LhpResp>, t: Throwable) {
                        Toast.makeText(this@EditLhpActivity, "$t", Toast.LENGTH_SHORT).show()
                    }
                })
    }

    private fun updateLhp(editLHP: LhpMinResp?) {
        btn_save_lhp_edit.showProgress {
            progressColor = Color.WHITE
        }
        editLhpReq.no_lhp = edt_no_lhp_edit.text.toString()
        editLhpReq.tentang = edt_isi_pengaduan_lhp_edit.text.toString()
        editLhpReq.no_surat_perintah_penyelidikan = edt_no_sp_lhp_edit.text.toString()
        editLhpReq.tanggal_mulai_penyelidikan = edt_waktu_penugasan_lhp_edit.text.toString()
//        editLhpReq.daerah_penyelidikan = edt_tempat_penyelidikan_lhp_edit.text.toString()
        editLhpReq.tugas_pokok = edt_tugas_pokok_lhp_edit.text.toString()
        editLhpReq.pokok_permasalahan = edt_pokok_permasalahan_lhp_edit.text.toString()
        editLhpReq.keterangan_ahli = edt_keterangan_ahli_lhp_edit.text.toString()
        editLhpReq.kesimpulan = edt_kesimpulan_lhp_edit.text.toString()
        editLhpReq.rekomendasi = edt_rekomendasi_lhp_edit.text.toString()
        editLhpReq.kota_buat_laporan = edt_kota_buat_edit_lhp.text.toString()
        editLhpReq.tanggal_buat_laporan = edt_tgl_buat_edit_lhp.text.toString()
        editLhpReq.surat = edt_surat_lhp_edit.text.toString()
        editLhpReq.petunjuk = edt_petunjuk_lhp_edit.text.toString()
        editLhpReq.barang_bukti = edt_barbukti_lhp_edit.text.toString()
        editLhpReq.analisa = edt_analisa_lhp_edit.text.toString()

        Log.e("updateLHP", "$editLhpReq")
        apiUpdLhp(editLHP)
    }

    private fun apiUpdLhp(editLHP: LhpMinResp?) {
        NetworkConfig().getServLhp().updLhp("Bearer ${sessionManager1.fetchAuthToken()}", editLHP?.id, editLhpReq).enqueue(
            object : Callback<Base1Resp<AddLhpResp>> {
                override fun onResponse(
                    call: Call<Base1Resp<AddLhpResp>>,
                    response: Response<Base1Resp<AddLhpResp>>
                ) {
                    if (response.body()?.message == "Data lhp updated succesfully") {
                        btn_save_lhp_edit.hideProgress(R.string.data_updated)
                        Handler(Looper.getMainLooper()).postDelayed({
                            finish()
                        },750)

                    } else {
                        btn_save_lhp_edit.hideProgress(R.string.not_update)
                    }
                }

                override fun onFailure(call: Call<Base1Resp<AddLhpResp>>, t: Throwable) {
                    Toast.makeText(this@EditLhpActivity, "$t", Toast.LENGTH_SHORT).show()
                    btn_save_lhp_edit.hideProgress(R.string.not_update)
                }
            })
    }

    private fun getViewLhpEdit(editLHP: LhpResp?) {
        edt_no_lhp_edit.setText(editLHP?.no_lhp)
        edt_isi_pengaduan_lhp_edit.setText(editLHP?.tentang)
        edt_no_sp_lhp_edit.setText(editLHP?.no_surat_perintah_penyelidikan)
        edt_waktu_penugasan_lhp_edit.setText(editLHP?.tanggal_mulai_penyelidikan)
        edt_tempat_penyelidikan_lhp_edit.setText(editLHP?.wilayah_hukum_penyelidikan)
        edt_tugas_pokok_lhp_edit.setText(editLHP?.tugas_pokok)
        edt_pokok_permasalahan_lhp_edit.setText(editLHP?.pokok_permasalahan)
        edt_keterangan_ahli_lhp_edit.setText(editLHP?.keterangan_ahli)
        edt_kesimpulan_lhp_edit.setText(editLHP?.kesimpulan)
        edt_rekomendasi_lhp_edit.setText(editLHP?.rekomendasi)
        edt_kota_buat_edit_lhp.setText(editLHP?.kota_buat_laporan)
        edt_tgl_buat_edit_lhp.setText(editLHP?.tanggal_buat_laporan)
        edt_surat_lhp_edit.setText(editLHP?.surat)
        edt_petunjuk_lhp_edit.setText(editLHP?.petunjuk)
        edt_barbukti_lhp_edit.setText(editLHP?.barang_bukti)
        edt_analisa_lhp_edit.setText(editLHP?.analisa)
          }

}