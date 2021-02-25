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
import id.calocallo.sicape.model.AddSinglePendResp
import id.calocallo.sicape.model.DetailPendModel
import id.calocallo.sicape.model.PendidikanModel
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.SinglePendReq
import id.calocallo.sicape.network.response.Base1Resp
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.gone
import kotlinx.android.synthetic.main.fragment_edit_pend.*
import kotlinx.android.synthetic.main.fragment_edit_pend.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class EditPendFragment : Fragment() {
    private val singlePendReq = SinglePendReq()
    private lateinit var sessionManager1: SessionManager1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_pend, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager1 = activity?.let { SessionManager1(it) }!!

        if (sessionManager1.fetchHakAkses() == "operator") {
            view.btn_save_edit_pend.gone()
            view.btn_delete_edit_pend.gone()
        }
        val pendidikan = arguments?.getParcelable<PendidikanModel>("PENDIDIKAN")
        viewPendDetail(pendidikan)
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

    private fun viewPendDetail(pendidikan: PendidikanModel?) {
        NetworkConfig().getServPers().getDetailPend(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            pendidikan?.id.toString()
        ).enqueue(object : Callback<DetailPendModel> {
            override fun onFailure(call: Call<DetailPendModel>, t: Throwable) {
                Toast.makeText(activity, "Error Koneksi", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<DetailPendModel>,
                response: Response<DetailPendModel>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    edt_nama_edit_pend.setText(data?.pendidikan)
                    edt_thn_awal_edit_pend.setText(data?.tahun_awal)
                    edt_thn_akhir_edit_pend.setText(data?.tahun_akhir)
                    edt_tempat_edit_pend.setText(data?.kota)
                    edt_membiayai_edit_pend.setText(data?.yang_membiayai)
                    if (data?.keterangan.isNullOrBlank()) {
                        edt_ket_edit_pend.setText("")
                    } else {
                        edt_ket_edit_pend.setText(data?.keterangan)
                    }
                }
            }
        })
    }

    private fun doDelete(pendidikan: PendidikanModel?) {
        NetworkConfig().getServPers().deletePend(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            pendidikan?.id.toString()
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                Toast.makeText(activity, "Error Koneksi", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if (response.isSuccessful) {
                    if (response.body()?.message == "Data riwayat pendidikan personel removed succesfully") {
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
        singlePendReq.jenis = pendidikan?.jenis

        val animatedDrawable =
            activity?.let { ContextCompat.getDrawable(it, R.drawable.animated_check) }!!
        //Defined bounds are required for your drawable
        val drawableSize = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)

        view?.btn_save_edit_pend?.showProgress {
            progressColor = Color.WHITE
        }

        NetworkConfig().getServPers().updatePend(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            pendidikan?.id.toString(), //id
            singlePendReq
        ).enqueue(object : Callback<Base1Resp<AddSinglePendResp>> {
            override fun onFailure(call: Call<Base1Resp<AddSinglePendResp>>, t: Throwable) {
                Toast.makeText(activity, "Error Koneksi", Toast.LENGTH_SHORT).show()
                btn_save_edit_pend.hideDrawable(R.string.save)
            }

            override fun onResponse(
                call: Call<Base1Resp<AddSinglePendResp>>,
                response: Response<Base1Resp<AddSinglePendResp>>
            ) {
                if (response.isSuccessful) {
                    if (response.body()?.message == "Data riwayat pendidikan personel updated succesfully") {
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
                        }, 3000)
                        btn_save_edit_pend.hideDrawable(R.string.not_update)
//                        view?.btn_save_edit_pend?.hideDrawable(R.string.not_update)
                    }

                } else {
                    Handler(Looper.getMainLooper()).postDelayed({
                        btn_save_edit_pend.hideDrawable(R.string.save)
                    }, 3000)
                    btn_save_edit_pend.hideDrawable(R.string.not_update)
//                    view?.btn_save_edit_pend?.hideDrawable(R.string.not_update)
                }
            }
        })
    }
}