package id.calocallo.sicape.ui.main.catpers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.android.material.datepicker.MaterialDatePicker
import id.calocallo.sicape.R
import kotlinx.android.synthetic.main.activity_add_catpers.*
import java.util.*

class AddCatpersActivity : AppCompatActivity() {
    var llDetail1: LinearLayout? = null
    var llDetail2: LinearLayout? = null
    var llDetail3: LinearLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_catpers)

        llDetail1 = findViewById(R.id.layout_detail_1)
        llDetail2 = findViewById(R.id.layout_detail_2)
        llDetail3 = findViewById(R.id.layout_detail_3)

        initSpinner(txt_jk, txt_agama, txt_pangkat, txt_jabatan, txt_kesatuan)

        txt_tanggal_personel.setOnClickListener {
            initDatePicker()
        }

        initCBa(cb_pidana, cb_kkep, cb_disiplin)
    }

    private fun initSpinner(
        txtJk: AutoCompleteTextView?,
        txtAgama: AutoCompleteTextView?,
        txtPangkat: AutoCompleteTextView?,
        txtJabatan: AutoCompleteTextView?,
        txtKesatuan: AutoCompleteTextView?
    ) {

        //JK
        val jkItems = listOf("Laki-Laki", "Perempuan")
        val adapterJk = ArrayAdapter(this, R.layout.item_spinner, jkItems)
        txtJk?.setAdapter(adapterJk)
        txtJk?.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(this, parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT)
                .show()
        }

        //AGAMA
        val agamaItems = listOf("Islam", "Kristen", "Buddha", "Hindu")
        val adapterAgaman = ArrayAdapter(this, R.layout.item_spinner, agamaItems)
        txt_agama.setAdapter(adapterAgaman)
        txt_agama.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(this, parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT)
                .show()
        }

        //PANGKAT
        val pangkatItems =
            listOf(
                "Jenderal", "Komjem Pol", "Irjen Pol",
                "Bridgen POl", "Kombes", "AKBP",
                "AKBP", "Kompol", "AKP",
                "Iptu", "Ipda", "Aiptu",
                "Aipda", "Bripka", "Brippol",
                "Briptu", " Bripda"
            )
        val adapterPangkat = ArrayAdapter(this, R.layout.item_spinner, pangkatItems)
        txt_pangkat.setAdapter(adapterPangkat)
        txt_pangkat.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(this, parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT)
                .show()
        }

        //JABATAN
        val jabatanItems = listOf(
            "BANIT SAT POLAIR", "JABATAN 2", "JABATAN 3"
        )
        val adapterJabatan = ArrayAdapter(this, R.layout.item_spinner, jabatanItems)
        txt_jabatan.setAdapter(adapterJabatan)
        txt_jabatan.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(this, parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT)
                .show()
        }
        //KESATUAN
        val kesatuanItems = listOf("POLRES TANAH LAUT", "POLRES BANJARBARU", "POLRES BANJARMASIN")
        val adapterKesatuan = ArrayAdapter(this, R.layout.item_spinner, kesatuanItems)
        txt_kesatuan.setAdapter(adapterKesatuan)
        txt_kesatuan.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(this, parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT)
                .show()
        }
    }


    private fun initCBa(cb: CheckBox?, cb2: CheckBox?, cb3: CheckBox?) {
        cb?.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                llDetail1?.visibility = View.VISIBLE
            } else {
                llDetail1?.visibility = View.GONE
            }
        }
        cb2?.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                llDetail2?.visibility = View.VISIBLE
            } else {
                llDetail2?.visibility = View.GONE
            }
        }
        cb3?.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                llDetail3?.visibility = View.VISIBLE
            } else {
                llDetail3?.visibility = View.GONE
            }
        }
    }


    private fun initDatePicker() {
        val builder = MaterialDatePicker.Builder.datePicker()

        val datePicker = builder.build()

        datePicker.addOnPositiveButtonClickListener {
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            calendar.time = Date(it)
            txt_tanggal_personel.setText(
                "${calendar.get(Calendar.DAY_OF_MONTH)}- " +
                        "${calendar.get(Calendar.MONTH) + 1}-${calendar.get(Calendar.YEAR)}"
            )

        }

        datePicker.show(supportFragmentManager, "")
    }
}
