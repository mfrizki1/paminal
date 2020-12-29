package id.calocallo.sicape.ui.main.editpersonel.pekerjaan

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.PekerjaanODinasReq
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.network.response.PekerjaanLuarResp
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.gone
import kotlinx.android.synthetic.main.activity_edit_tokoh.*
import kotlinx.android.synthetic.main.fragment_edit_pekerjaan.*
import kotlinx.android.synthetic.main.fragment_edit_pekerjaan.view.*
import kotlinx.android.synthetic.main.fragment_edit_pekerjaan_luar.*
import kotlinx.android.synthetic.main.fragment_edit_pekerjaan_luar.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class EditPekerjaanLuarFragment : Fragment() {
    private val pekerjaanLuarReq = PekerjaanODinasReq("", "", "", "", "", "")
    private lateinit var sessionManager: SessionManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_pekerjaan_luar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = activity?.let { SessionManager(it) }!!
        if (sessionManager.fetchHakAkses() == "operator") {
            view.btn_save_pekerjaan_luar_edit.gone()
            view.btn_delete_edit_pekerjaan.gone()
        }
        val pekerjaan = arguments?.getParcelable<PekerjaanLuarResp>("PEKERJAAN")
        edt_nama_pekerjaan_luar_edit.setText(pekerjaan?.pekerjaan)
        edt_thn_awal_pekerjaan_luar_edit.setText(pekerjaan?.tahun_awal.toString())
        edt_thn_akhir_pekerjaan_luar_edit.setText(pekerjaan?.tahun_akhir.toString())
        edt_instansi_pekerjaan_luar_edit.setText(pekerjaan?.instansi)
        edt_rangka_pekerjaan_luar_edit.setText(pekerjaan?.dalam_rangka)
        edt_ket_pekerjaan_luar_edit.setText(pekerjaan?.keterangan)

        btn_save_pekerjaan_luar_edit.attachTextChangeAnimator()
        bindProgressButton(btn_save_pekerjaan_luar_edit)
        btn_save_pekerjaan_luar_edit.setOnClickListener {
            if (pekerjaan != null) {
                doUpdatePekerjaan(pekerjaan)
            }
        }

        btn_delete_pekerjaan_luar_edit.setOnClickListener {
            this.activity!!.alert("Hapus Data", "Yakin Hapus?") {
                positiveButton("Iya") {
                    doDeletePekerjaan(pekerjaan)
                }
                negativeButton("Tidak") {
                }
            }.show()
        }

    }

    private fun doDeletePekerjaan(pekerjaan: PekerjaanLuarResp?) {
        NetworkConfig().getService().deletePekerjaanLuar(
            "Bearer ${sessionManager.fetchAuthToken()}",
            pekerjaan?.id.toString()
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                Toast.makeText(activity, "Error Koneksi", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if (response.isSuccessful) {
                    Toast.makeText(activity, "Data Sudah Berhasil Di Hapus", Toast.LENGTH_SHORT)
                        .show()
                    fragmentManager?.popBackStack()
                } else {
                    Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        })

    }

    private fun doUpdatePekerjaan(pekerjaan: PekerjaanLuarResp?) {
        val animatedDrawable =
            activity?.let { ContextCompat.getDrawable(it, R.drawable.animated_check) }!!
        //Defined bounds are required for your drawable
        val drawableSize = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)

        pekerjaanLuarReq.pekerjaan = edt_nama_pekerjaan_luar_edit.text.toString()
        pekerjaanLuarReq.tahun_awal = edt_thn_awal_pekerjaan_luar_edit.text.toString()
        pekerjaanLuarReq.tahun_akhir = edt_thn_akhir_pekerjaan_luar_edit.text.toString()
        pekerjaanLuarReq.instansi = edt_instansi_pekerjaan_luar_edit.text.toString()
        pekerjaanLuarReq.dalam_rangka = edt_rangka_pekerjaan_luar_edit.text.toString()
        pekerjaanLuarReq.keterangan = edt_ket_pekerjaan_luar_edit.text.toString()

        btn_save_pekerjaan_luar_edit.showProgress {
            progressColor = Color.WHITE
        }

        NetworkConfig().getService().updatePekerjaanLuar(
            "Bearer ${sessionManager.fetchAuthToken()}",
            pekerjaan?.id.toString(),
            pekerjaanLuarReq
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                Toast.makeText(activity, "Error Koneksi", Toast.LENGTH_SHORT).show()
                btn_save_pekerjaan_luar_edit.hideDrawable(R.string.save)

            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if (response.isSuccessful) {
                    Toast.makeText(activity, R.string.data_updated, Toast.LENGTH_SHORT)
                        .show()
                    btn_save_pekerjaan_luar_edit.showDrawable(animatedDrawable) {
                        buttonTextRes = R.string.data_updated
                        textMarginRes = R.dimen.space_10dp
                    }
                    Handler(Looper.getMainLooper()).postDelayed({
                        fragmentManager?.popBackStack()
                    }, 500)
                } else {
                    Handler(Looper.getMainLooper()).postDelayed({
                        btn_save_pekerjaan_luar_edit.hideDrawable(R.string.save)
                    },3000)
                    btn_save_pekerjaan_luar_edit.hideDrawable(R.string.not_update)
                }
            }
        }
        )
    }
}