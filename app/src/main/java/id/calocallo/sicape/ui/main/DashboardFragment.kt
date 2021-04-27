package id.calocallo.sicape.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import id.calocallo.sicape.R
import id.calocallo.sicape.model.FiturModel
import id.calocallo.sicape.ui.gelar.AddGelarActivity
import id.calocallo.sicape.ui.gelar.ListGelarActivity
import id.calocallo.sicape.ui.main.anev.AnevActivity
import id.calocallo.sicape.ui.main.catpers.CatpersActivity
import id.calocallo.sicape.ui.main.lapbul.ListLapbulActivity
import id.calocallo.sicape.ui.main.lhp.LhpActivity
import id.calocallo.sicape.ui.main.lp.PickLpActivity
import id.calocallo.sicape.ui.main.personel.KatPersonelActivity
import id.calocallo.sicape.ui.main.personel.PersonelActivity
import id.calocallo.sicape.ui.main.rehab.PickRehabActivity
import id.calocallo.sicape.ui.main.skhd.PickSkhdActivity
import id.calocallo.sicape.ui.main.skhp.ListSkhpActivity
import id.calocallo.sicape.ui.main.wanjak.ListWanjakActivity
import id.calocallo.sicape.ui.satker.KatSatkerActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.toast
import kotlinx.android.synthetic.main.fragment_dashboard.*


class DashboardFragment : Fragment(), FiturAdapter.FiturListener {
    private lateinit var sessionmanager: SessionManager1
    lateinit var list: ArrayList<FiturModel>
    val listFitur = listOf(
        FiturModel("Personel", R.drawable.ic_personel),
        FiturModel("Satuan Kerja", R.drawable.ic_personel),
        FiturModel("Laporan Polisi", R.drawable.ic_lp),
        FiturModel("Laporan Hasil Penyelidikan", R.drawable.ic_lhp),
        FiturModel("Surat Keputusan Hukuman Disiplin", R.drawable.ic_skhd),
//        FiturModel("Putusan Komisi Kode Etik", R.drawable.ic_putkke),
        FiturModel("Catatan Personel", R.drawable.ic_catpers),
        FiturModel("Surat Keterangan Hasil Penelitian", R.drawable.ic_skhp),
        FiturModel("Rehab", R.drawable.ic_rehab),
        FiturModel("Laporan Hasil Gelar", R.drawable.ic_gelar),
        FiturModel("Laporan Bulanan", R.drawable.ic_laporan),
        FiturModel("Analisa & Evaluasi", R.drawable.ic_analisa)
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
        sessionmanager = activity?.let { SessionManager1(it) }!!
        rv_fitur.setHasFixedSize(true)
        rv_fitur.layoutManager = GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false)
        val adapter = FiturAdapter(activity!!, ArrayList(listFitur), this)
        rv_fitur.adapter = adapter

//        sessionmanager.clearAllPers()
//        Log.e("session", "${sessionmanager.getUserLogin()}")


    }

    override fun onClick(position: Int) {
        val hak = sessionmanager.fetchHakAkses()
        if(hak == "operator"){
            when (listFitur[position].nameFitur) {
                "Personel" -> {
                    startActivity(Intent(activity, PersonelActivity::class.java))
                    activity!!.overridePendingTransition(
                        R.anim.slide_in_right,
                        R.anim.slide_out_left
                    )
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
                "Surat Keputusan Hukuman Disiplin" -> {
                    startActivity(Intent(activity, PickSkhdActivity::class.java))
                    activity!!.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                }
                "Catatan Personel" -> {
                    startActivity(Intent(activity, CatpersActivity::class.java))
                    activity!!.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                }
                "Rehab" -> {
                    startActivity(Intent(activity, PickRehabActivity::class.java))
                    activity!!.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                }
                "Laporan Hasil Gelar" -> {
                    startActivity(Intent(activity, ListGelarActivity::class.java))
                    activity!!.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                }
                else->{
                    activity?.toast("Fitur ini bisa dibuka Admin")
                }
            }
        }else{
        when (listFitur[position].nameFitur) {
            "Personel" -> {
                startActivity(Intent(activity, KatPersonelActivity::class.java))
                activity!!.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

            }
            "Satuan Kerja" -> {
                startActivity(Intent(activity, KatSatkerActivity::class.java))
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
            "Surat Keputusan Hukuman Disiplin" -> {
                startActivity(Intent(activity, PickSkhdActivity::class.java))
                activity!!.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
            /* "Putusan Komisi Kode Etik" -> {
                 startActivity(Intent(activity, PickSkhdActivity::class.java))
                 activity!!.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
             }*/
            "Surat Keterangan Hasil Penelitian" -> {
                startActivity(Intent(activity, ListSkhpActivity::class.java))
                activity!!.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
            "Catatan Personel" -> {
                startActivity(Intent(activity, CatpersActivity::class.java))
                activity!!.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
            "Rehab" -> {
                startActivity(Intent(activity, PickRehabActivity::class.java))
                activity!!.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
            "Laporan Hasil Gelar" -> {
                startActivity(Intent(activity, ListGelarActivity::class.java))
                activity!!.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
            "Laporan Bulanan" -> {
                startActivity(Intent(activity, ListLapbulActivity::class.java))
                activity!!.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
            "Analisa & Evaluasi" -> {
                startActivity(Intent(activity, AnevActivity::class.java))
                activity!!.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }

                else -> {
                }

            }
        }
    }
}