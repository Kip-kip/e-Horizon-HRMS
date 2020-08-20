package stlhorizon.org.hrmselfservice.fragments.dashboard

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_payslip.*
import stlhorizon.org.hrmselfservice.R
import stlhorizon.org.hrmselfservice.activities.Payslip.PayslipItemActivity
import java.util.*


class PayslipFragment : Fragment() {

    var yearValue = 2019

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_payslip, container, false)
        val changeyear = root.findViewById<LinearLayout>(R.id.changeyear)

        val january = root.findViewById<LinearLayout>(R.id.january)
        val february = root.findViewById<LinearLayout>(R.id.february)
        val march = root.findViewById<LinearLayout>(R.id.march)
        val april = root.findViewById<LinearLayout>(R.id.april)
        val may = root.findViewById<LinearLayout>(R.id.may)
        val june = root.findViewById<LinearLayout>(R.id.june)
        val july = root.findViewById<LinearLayout>(R.id.july)
        val august = root.findViewById<LinearLayout>(R.id.august)
        val september = root.findViewById<LinearLayout>(R.id.september)
        val october = root.findViewById<LinearLayout>(R.id.october)
        val november = root.findViewById<LinearLayout>(R.id.november)
        val december = root.findViewById<LinearLayout>(R.id.december)


        changeyear.setOnClickListener {
            val c = Calendar.getInstance()
            val mYear = c[Calendar.YEAR]
            val mMonth = c[Calendar.MONTH]
            val mDay = c[Calendar.DAY_OF_MONTH]
            val datePickerDialog =
                DatePickerDialog(
                    context!!, AlertDialog.THEME_HOLO_DARK,
                    OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        tv_select_year.setText("" + year)
                        yearValue = year

                    }, mYear, mMonth, mDay
                )

            datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
            (datePickerDialog.datePicker as ViewGroup).findViewById<View>(
                Resources.getSystem().getIdentifier("day", "id", "android")
            ).visibility = View.GONE
            (datePickerDialog.datePicker as ViewGroup).findViewById<View>(
                Resources.getSystem().getIdentifier("month", "id", "android")
            ).visibility = View.GONE
            datePickerDialog.show()
        }


        january.setOnClickListener {
            val intent = Intent(context, PayslipItemActivity::class.java)
            val from_date = "1/1/$yearValue"
            val to_date = "31/1/$yearValue"
            intent.putExtra("from_date", from_date)
            intent.putExtra("to_date", to_date)
            startActivity(intent)
        }
        february.setOnClickListener {
            val intent = Intent(context, PayslipItemActivity::class.java)
            val from_date = "1/2/$yearValue"
            val to_date = "28/2/$yearValue"
            intent.putExtra("from_date", from_date)
            intent.putExtra("to_date", to_date)
            startActivity(intent)
        }
        march.setOnClickListener {
            val intent = Intent(context, PayslipItemActivity::class.java)
            val from_date = "1/3/$yearValue"
            val to_date = "31/3/$yearValue"
            intent.putExtra("from_date", from_date)
            intent.putExtra("to_date", to_date)
            startActivity(intent)
        }
        april.setOnClickListener {
            val intent = Intent(context, PayslipItemActivity::class.java)
            val from_date = "1/4/$yearValue"
            val to_date = "30/4/$yearValue"
            intent.putExtra("from_date", from_date)
            intent.putExtra("to_date", to_date)
            startActivity(intent)
        }
        may.setOnClickListener {
            val intent = Intent(context, PayslipItemActivity::class.java)
            val from_date = "1/5/$yearValue"
            val to_date = "31/5/$yearValue"
            intent.putExtra("from_date", from_date)
            intent.putExtra("to_date", to_date)
            startActivity(intent)
        }
        june.setOnClickListener {
            val intent = Intent(context, PayslipItemActivity::class.java)
            val from_date = "1/6/$yearValue"
            val to_date = "30/6/$yearValue"
            intent.putExtra("from_date", from_date)
            intent.putExtra("to_date", to_date)
            startActivity(intent)
        }
        july.setOnClickListener {
            val intent = Intent(context, PayslipItemActivity::class.java)
            val from_date = "1/7/$yearValue"
            val to_date = "31/7/$yearValue"
            intent.putExtra("from_date", from_date)
            intent.putExtra("to_date", to_date)
            startActivity(intent)
        }
        august.setOnClickListener {
            val intent = Intent(context, PayslipItemActivity::class.java)
            val from_date = "1/8/$yearValue"
            val to_date = "31/8/$yearValue"
            intent.putExtra("from_date", from_date)
            intent.putExtra("to_date", to_date)
            startActivity(intent)
        }
        september.setOnClickListener {
            val intent = Intent(context, PayslipItemActivity::class.java)
            val from_date = "1/9/$yearValue"
            val to_date = "30/9/$yearValue"
            intent.putExtra("from_date", from_date)
            intent.putExtra("to_date", to_date)
            startActivity(intent)
        }
        october.setOnClickListener {
            val intent = Intent(context, PayslipItemActivity::class.java)
            val from_date = "1/10/$yearValue"
            val to_date = "31/10/$yearValue"
            intent.putExtra("from_date", from_date)
            intent.putExtra("to_date", to_date)
            startActivity(intent)
        }
        november.setOnClickListener {
            val intent = Intent(context, PayslipItemActivity::class.java)
            val from_date = "1/11/$yearValue"
            val to_date = "30/11/$yearValue"
            intent.putExtra("from_date", from_date)
            intent.putExtra("to_date", to_date)
            startActivity(intent)
        }
        december.setOnClickListener {
            val intent = Intent(context, PayslipItemActivity::class.java)
            val from_date = "1/12/$yearValue"
            val to_date = "31/12/$yearValue"
            intent.putExtra("from_date", from_date)
            intent.putExtra("to_date", to_date)
            startActivity(intent)
        }





        return root
    }




}