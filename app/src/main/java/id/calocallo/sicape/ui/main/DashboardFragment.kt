package id.calocallo.sicape.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import id.calocallo.sicape.R
import id.calocallo.sicape.model.FiturModel
import id.calocallo.sicape.ui.main.catpers.CatpersActivity
import id.calocallo.sicape.ui.main.lhp.LhpActivity
import id.calocallo.sicape.ui.main.lp.PickLpActivity
import id.calocallo.sicape.ui.main.personel.PersonelActivity
import id.calocallo.sicape.ui.main.rehab.PickRehabActivity
import id.calocallo.sicape.ui.main.skhd.PickSkhdActivity
import id.calocallo.sicape.ui.main.skhd.SkhdActivity
import id.calocallo.sicape.ui.main.skhp.ListSkhpActivity
import id.calocallo.sicape.ui.main.wanjak.ListWanjakActivity
import id.calocallo.sicape.utils.SessionManager
import kotlinx.android.synthetic.main.fragment_dashboard.*


class DashboardFragment : Fragment(), FiturAdapter.FiturListener {
    private lateinit var sessionmanager: SessionManager
    lateinit var list: ArrayList<FiturModel>
    val listFitur = listOf(
        FiturModel("Personel", R.drawable.ic_personel),
        FiturModel("Laporan Polisi", R.drawable.ic_lp),
        FiturModel("Laporan Hasil Penyelidikan", R.drawable.ic_lhp),
        FiturModel("SKHD", R.drawable.ic_skhd),
        FiturModel("CATPERS", R.drawable.ic_catpers),
        FiturModel("SKHP", R.drawable.ic_skhp),
        FiturModel("REHAB", R.drawable.ic_rehab),
        FiturModel("WANJAK", R.drawable.ic_wanjak),
        FiturModel("Laporan Bulanan", R.drawable.ic_laporan),
        FiturModel("Analisa", R.drawable.ic_analisa)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionmanager = activity?.let { SessionManager(it) }!!
        rv_fitur.setHasFixedSize(true)
        rv_fitur.layoutManager = GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false)
        val adapter = FiturAdapter(activity!!, ArrayList(listFitur), this)
        rv_fitur.adapter = adapter

//        sessionmanager.clearAllPers()
//        Log.e("session", "${sessionmanager.getUserLogin()}")


    }

    override fun onClick(position: Int) {
        when (listFitur[position].nameFitur) {
            "Personel" -> {
                startActivity(Intent(activity, PersonelActivity::class.java))
                activity!!.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

            }
            "Laporan Polisi" -> {
                startActivity(Intent(activity, PickLpActivity::class.java))
//                Toast.makeText(activity, listFitur[position].nameFitur, Toast.LENGTH_SHORT).show()
                activity!!.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
            "Laporan Hasil Penyelidikan" -> {
                startActivity(Intent(activity, LhpActivity::class.java))
                activity!!.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
            "SKHD" -> {
                startActivity(Intent(activity, PickSkhdActivity::class.java))
                activity!!.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }"SKHP" -> {
            startActivity(Intent(activity, ListSkhpActivity::class.java))
            activity!!.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
            "CATPERS" -> {
                startActivity(Intent(activity, CatpersActivity::class.java))
                Toast.makeText(activity, listFitur[position].nameFitur, Toast.LENGTH_SHORT).show()
                activity!!.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
            "REHAB" -> {
                startActivity(Intent(activity, PickRehabActivity::class.java))
                activity!!.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
            "WANJAK" -> {
                startActivity(Intent(activity, ListWanjakActivity::class.java))
                activity!!.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
            "Laporan Bulanan" -> {
                Toast.makeText(activity, listFitur[position].nameFitur, Toast.LENGTH_SHORT).show()
                activity!!.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
            "Analisa" -> {
                Toast.makeText(activity, listFitur[position].nameFitur, Toast.LENGTH_SHORT).show()
                activity!!.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }

            else -> {
            }

        }
    }
}