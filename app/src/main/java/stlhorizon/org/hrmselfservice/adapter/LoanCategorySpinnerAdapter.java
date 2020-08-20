package stlhorizon.org.hrmselfservice.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import stlhorizon.org.hrmselfservice.R;
import stlhorizon.org.hrmselfservice.model.spinners.LoanCategory;

public class LoanCategorySpinnerAdapter extends BaseAdapter implements AdapterView.OnItemSelectedListener {
    List<?> items;
    private Context context;
    private View headerView;
    private String selectedtype;
    private boolean flag=true;

    public LoanCategorySpinnerAdapter(List<?>  items, Context context){

        this.items=items;
        this.context=context;
    }
    @Override
    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        View myView= LayoutInflater.from(this.context).inflate(R.layout.drop_down_item,viewGroup,false);
        TextView headerText=myView.findViewById(R.id.item_title);
        headerText.setText(((LoanCategory.LoanCategoryModel)items.get(i)).getLoan_category());
        return myView;
    }


    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return this.items.size();
    }



    @Override
    public LoanCategory.LoanCategoryModel getItem(int position) {
        return (LoanCategory.LoanCategoryModel) items.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }



    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View myView= LayoutInflater.from(this.context).inflate(R.layout.spinner_header,viewGroup,false);
        this.headerView=myView;
        TextView textView=headerView.findViewById(R.id.item_title);

        if (this.selectedtype == null) {
            textView.setText("Select User Type");
        } else {
            textView.setText(selectedtype);
        }
        //textView.setText(((UserType)items.get(i)).getTypeName());
        return myView;
    }

    @Override
    public int getItemViewType(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(flag){
            TextView textView=headerView.findViewById(R.id.item_title);
            textView.setText(selectedtype==null?"Select User Type":selectedtype);
            flag=false;
        }else{
            selectedtype=(((LoanCategory.LoanCategoryModel)items.get(position))).getLoan_category();
            TextView textView=headerView.findViewById(R.id.item_title);
            textView.setText(selectedtype==null?"Select User Type":selectedtype);

        }



    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
