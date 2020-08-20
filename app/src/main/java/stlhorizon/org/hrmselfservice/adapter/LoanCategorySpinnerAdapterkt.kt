package stlhorizon.org.uniscoo.adapter

import android.content.Context
import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.BaseAdapter
import android.widget.TextView
import stlhorizon.org.hrmselfservice.R
import stlhorizon.org.hrmselfservice.model.Leave.LoanCategorykt

class LoanCategorySpinnerAdapterkt(
    var items: List<*>,
    private var context: Context? = null
) :
    BaseAdapter(), OnItemSelectedListener {
    private var headerView: View? = null
    private var selectedtype: String? = null
    private var flag = true
    override fun getDropDownView(
        i: Int,
        view: View,
        viewGroup: ViewGroup
    ): View {
        val myView: View =
            LayoutInflater.from(context).inflate(R.layout.drop_down_item, viewGroup, false)
        val headerText = myView.findViewById<TextView>(R.id.item_title)
        headerText.setText((items[i] as LoanCategorykt.LoanCategoryModel).loan_category)
        return myView
    }

    override fun registerDataSetObserver(dataSetObserver: DataSetObserver) {}
    override fun unregisterDataSetObserver(dataSetObserver: DataSetObserver) {}
    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): LoanCategorykt.LoanCategoryModel {
        return items[position] as LoanCategorykt.LoanCategoryModel
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getView(
        i: Int,
        view: View,
        viewGroup: ViewGroup
    ): View {
        val myView: View =
            LayoutInflater.from(context).inflate(R.layout.spinner_header, viewGroup, false)
        headerView = myView
        val textView = headerView!!.findViewById<TextView>(R.id.item_title)
        if (selectedtype == null) {
            textView.text = "Select User Type"
        } else {
            textView.text = selectedtype
        }
        //textView.setText(((UserType)items.get(i)).getTypeName());
        return myView
    }

    override fun getItemViewType(i: Int): Int {
        return 0
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun isEmpty(): Boolean {
        return false
    }

    override fun onItemSelected(
        parent: AdapterView<*>?,
        view: View,
        position: Int,
        id: Long
    ) {
        if (flag) {
            val textView = headerView!!.findViewById<TextView>(R.id.item_title)
            textView.text = if (selectedtype == null) "Select User Type" else selectedtype
            flag = false
        } else {
            selectedtype = (items[position] as LoanCategorykt.LoanCategoryModel).loan_category
            val textView = headerView!!.findViewById<TextView>(R.id.item_title)
            textView.text = if (selectedtype == null) "Select User Type" else selectedtype
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

}