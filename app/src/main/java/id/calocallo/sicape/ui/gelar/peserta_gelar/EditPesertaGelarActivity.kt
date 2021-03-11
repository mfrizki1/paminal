package id.calocallo.sicape.ui.gelar.peserta_gelar

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.AddPesertaLhgResp
import id.calocallo.sicape.network.response.Base1Resp
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.network.response.PesertaLhgResp
import id.calocallo.sicape.utils.SessionManager1
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_peserta_gelar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.function.LongBinaryOperator

class EditPesertaGelarActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var pesertaLhgResp = PesertaLhgResp()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_peserta_gelar)
        sessionManager1 = SessionManager1(this)
        val dataPeserta =
            intent.getParcelableExtra<PesertaLhgResp>(ListPesertaGelarActivity.DATA_PESERTA)
        Log.e("dataPeserta", "$dataPeserta")

        /*viewDetail*/
        edt_nama_peserta_edit_single_peserta.editText?.setText(dataPeserta?.nama_peserta)
        edt_pendapat_peserta_edit_single_peserta.editText?.setText(dataPeserta?.pendapat)

        btn_save_single_peserta_edit.attachTextChangeAnimator()
        bindProgressButton(btn_save_single_peserta_edit)
        btn_save_single_peserta_edit.setOnClickListener {
            btn_save_single_peserta_edit.showProgress { progressColor = Color.WHITE }

            pesertaLhgResp.nama_peserta =
                edt_nama_peserta_edit_single_peserta.editText?.text.toString()

            pesertaLhgResp.pendapat =
                edt_pendapat_peserta_edit_single_peserta.editText?.text.toString()

            apiUpPeserta(dataPeserta)
        }
        btn_delete_single_peserta_edit.attachTextChangeAnimator()
        bindProgressButton(btn_delete_single_peserta_edit)
        btn_delete_single_peserta_edit.setOnClickListener {
            btn_delete_single_peserta_edit.showProgress { progressColor = Color.WHITE }
            apiDelPeserta(dataPeserta)
        }
    }

    private fun apiUpPeserta(dataPeserta: PesertaLhgResp?) {
        NetworkConfig().getServLhg().updPesertaLhg(
            "Bearer ${sessionManager1.fetchAuthToken()}", dataPeserta?.id, pesertaLhgResp
        ).enqueue(object :
            Callback<Base1Resp<AddPesertaLhgResp>> {
            override fun onResponse(
                call: Call<Base1Resp<AddPesertaLhgResp>>,
                response: Response<Base1Resp<AddPesertaLhgResp>>
            ) {
                if (response.body()?.message == "Data peserta gelar updated succesfully") {
                    btn_save_single_peserta_edit.hideProgress(R.string.data_updated)
                    Handler(Looper.getMainLooper()).postDelayed({
                        finish()
                    }, 750)
                } else {
                    btn_save_single_peserta_edit.hideProgress(R.string.not_update)
                }
            }

            override fun onFailure(call: Call<Base1Resp<AddPesertaLhgResp>>, t: Throwable) {
                Toast.makeText(this@EditPesertaGelarActivity, "$t", Toast.LENGTH_SHORT).show()
                btn_save_single_peserta_edit.hideProgress(R.string.not_update)
            }
        })
    }

    private fun apiDelPeserta(dataPeserta: PesertaLhgResp?) {
        NetworkConfig().getServLhg()
            .delPesertaLhg("Bearer ${sessionManager1.fetchAuthToken()}", dataPeserta?.id).enqueue(
                object : Callback<BaseResp> {
                    override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                        if (response.body()?.message == "Data peserta gelar removed succesfully") {
                            btn_delete_single_peserta_edit.hideProgress(R.string.data_deleted)
                            Handler(Looper.getMainLooper()).postDelayed({
                             finish()
                            },750)
                        } else {
                            btn_delete_single_peserta_edit.hideProgress(R.string.failed_deleted)
                        }
                    }

                    override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                        Toast.makeText(this@EditPesertaGelarActivity, "$t", Toast.LENGTH_SHORT)
                            .show()
                        btn_delete_single_peserta_edit.hideProgress(R.string.failed_deleted)
                    }
                })
    }
}