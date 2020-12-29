package id.calocallo.sicape.ui.main.editpersonel.pendidikan

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
import id.calocallo.sicape.model.PendidikanModel
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.SinglePendReq
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.gone
import kotlinx.android.synthetic.main.fragment_add_single_pend.*
import kotlinx.android.synthetic.main.fragment_edit_pend.*
import kotlinx.android.synthetic.main.fragment_edit_pend.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class EditPendFragment : Fragment() {
    private val singlePendReq = SinglePendReq()
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_pend, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = activity?.let { SessionManager(it) }!!

        if (sessionManager.fetchHakAkses() == "operator") {
            view.btn_save_edit_pend.gone()
            view.btn_delete_edit_pend.gone()
        }
        val pendidikan = arguments?.getParcelable<PendidikanModel>("PENDIDIKAN")
        edt_nama_edit_pend.setText(pendidikan?.pendidikan)
        edt_thn_awal_edit_pend.setText(pendidikan?.tahun_awal)
        edt_thn_akhir_edit_pend.setText(pendidikan?.tahun_akhir)
        edt_membiayai_edit_pend.setText(pendidikan?.yang_membiayai)
        edt_tempat_edit_pend.setText(pendidikan?.kota)
        edt_ket_edit_pend.setText(pendidikan?.keterangan)
        view.btn_save_edit_pend.attachTextChangeAnimator()
        bindProgressButton(view.btn_save_edit_pend)
        view.btn_save_edit_pend.setOnClickListener {
            doUpdate(pendidikan)
        }
        view.btn_delete_edit_pend.setOnClickListener {
            this.activity!!.alert("Hapus Data", "Yakin Hapus?") {
                positiveButton("Iya") {
                    doDelete(pendidikan)
                }
                negativeButton("Tidak") {
                }
            }.show()
        }
    }

    private fun doDelete(pendidikan: PendidikanModel?) {
        NetworkConfig().getService().deletePend(
            "Bearer ${sessionManager.fetchAuthToken()}",
            pendidikan?.id.toString()
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                Toast.makeText(activity, "Error Koneksi", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if (response.isSuccessful) {
                    if (response.body()?.message == "Data riwayat pendidikan removed succesfully") {
                        Toast.makeText(activity, "Berhasil Hapus", Toast.LENGTH_SHORT).show()
//                        activity?.finish()
                        fragmentManager?.popBackStack()
                    } else {
                        Toast.makeText(activity, "Gagal Hapus", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(activity, "Gagal Hapus", Toast.LENGTH_SHORT).show()
                }
            }
        })

    }

    private fun doUpdate(pendidikan: PendidikanModel?) {
        singlePendReq.pendidikan = edt_nama_edit_pend.text.toString()
        singlePendReq.kota = edt_tempat_edit_pend.text.toString()
        singlePendReq.tahun_awal = edt_thn_awal_edit_pend.text.toString()
        singlePendReq.tahun_akhir = edt_thn_akhir_edit_pend.text.toString()
        singlePendReq.keterangan = edt_ket_edit_pend.text.toString()
        singlePendReq.yang_membiayai = edt_membiayai_edit_pend.text.toString()

        val animatedDrawable =
            activity?.let { ContextCompat.getDrawable(it, R.drawable.animated_check) }!!
        //Defined bounds are required for your drawable
        val drawableSize = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)

        view?.btn_save_edit_pend?.showProgress {
            progressColor = Color.WHITE
        }

        NetworkConfig().getService().updatePend(
            "Bearer ${sessionManager.fetchAuthToken()}",
            pendidikan?.id.toString(), //id
            singlePendReq
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                Toast.makeText(activity, "Error Koneksi", Toast.LENGTH_SHORT).show()
                btn_save_edit_pend.hideDrawable(R.string.save)
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if (response.isSuccessful) {
                    if (response.body()?.message == "Data riwayat pendidikan updated succesfully") {
                        Toast.makeText(activity, R.string.data_updated, Toast.LENGTH_SHORT).show()
                        view?.btn_save_edit_pend?.showDrawable(animatedDrawable) {
                            buttonTextRes = R.string.data_updated
                            textMarginRes = R.dimen.space_10dp
                        }
                        Handler(Looper.getMainLooper()).postDelayed({
                            fragmentManager?.popBackStack()
                        }, 500)
//                        activity?.finish()

                    } else {
                        Handler(Looper.getMainLooper()).postDelayed({
                            btn_save_edit_pend.hideDrawable(R.string.save)
                        },3000)
                        btn_save_edit_pend.hideDrawable(R.string.not_update)
//                        view?.btn_save_edit_pend?.hideDrawable(R.string.not_update)
                    }

                } else {
                    Handler(Looper.getMainLooper()).postDelayed({
                        btn_save_edit_pend.hideDrawable(R.string.save)
                    },3000)
                    btn_save_edit_pend.hideDrawable(R.string.not_update)
//                    view?.btn_save_edit_pend?.hideDrawable(R.string.not_update)
                }
            }
        })
    }
}