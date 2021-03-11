package id.calocallo.sicape.ui.main.addpersonal.pekerjaan

import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.AddSinglePekerjaanReq
import id.calocallo.sicape.utils.SessionManager1
import kotlinx.android.synthetic.main.fragment_pekerjaan.*


class PekerjaanFragment : Fragment() {
    private lateinit var sessionManager1: SessionManager1
    private lateinit var list: ArrayList<AddSinglePekerjaanReq>
    private lateinit var adapter: PekerjaanAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pekerjaan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager1 = activity?.let { SessionManager1(it) }!!
        list = ArrayList()
        initRecycler()

        btn_next_pekerjaan.setOnClickListener {
            if(list.size == 0){
                list.clear()
            }
            sessionManager1.setPekerjaan(list)
            val pkrjanOutDinas = PekerjaanOutDinasFragment()
                .apply {
                    enterTransition = Slide(Gravity.END)
                    exitTransition = Slide(Gravity.START)
                }
            val ft: FragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
            ft.replace(R.id.fl_pekerjaan, pkrjanOutDinas)
            ft.addToBackStack(null)
            ft.commit()
        }

    }


    private fun initRecycler() {
        rv_pekerjaan.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        val pekerjaanCreated = sessionManager1.getPekerjaan()
        if (pekerjaanCreated.size == 0) {
            list.add(
                AddSinglePekerjaanReq()
            )
        }

        adapter = activity?.let {
            PekerjaanAdapter(it, list, object : PekerjaanAdapter.OnCLickPekerjaan {
                override fun onDelete(position: Int) {
                    list.removeAt(position)
                    adapter.notifyItemRemoved(position)
                }
            })
        }!!
        rv_pekerjaan.adapter = adapter

        btn_add_pekerjaan.setOnClickListener {
            val position = if (list.isEmpty()) 0 else list.size - 1
            list.add(
                AddSinglePekerjaanReq()
            )
            adapter.notifyItemChanged(position)
            adapter.notifyItemInserted(position)

        }
    }

    override fun onResume() {
        super.onResume()
        val pekerjaan = sessionManager1.getPekerjaan()
        for (i in 0 until pekerjaan.size) {
            list.add(
                i, AddSinglePekerjaanReq(
                    pekerjaan[i].pekerjaan,
                    pekerjaan[i].golongan,
                    pekerjaan[i].instansi,
                    pekerjaan[i].berapa_tahun,
                    pekerjaan[i].keterangan
                )
            )
        }
    }
}
