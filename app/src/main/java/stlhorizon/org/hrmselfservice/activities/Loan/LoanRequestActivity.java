package stlhorizon.org.hrmselfservice.activities.Loan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import stlhorizon.org.hrmselfservice.R;
import stlhorizon.org.hrmselfservice.adapter.LoanCategorySpinnerAdapter;
import stlhorizon.org.hrmselfservice.helper.SQLiteHandler;
import stlhorizon.org.hrmselfservice.helper.SessionManager;
import stlhorizon.org.hrmselfservice.model.loan.LoanApplication;
import stlhorizon.org.hrmselfservice.model.spinners.LoanCategory;
import stlhorizon.org.hrmselfservice.utils.network.local.NetworkConnection;
import stlhorizon.org.hrmselfservice.utils.network.local.OnReceivingResult;
import stlhorizon.org.hrmselfservice.utils.network.local.RemoteResponse;
import stlhorizon.org.uniscoo.adapter.LoanCategorySpinnerAdapterkt;

public class LoanRequestActivity extends AppCompatActivity {

    private Spinner spinner;
    private LoanCategorySpinnerAdapter loanCategorySpinnerAdapter;
    TextView edtstaffid,edtappliedamount,edtrepaymentamount,edtinterestrate,edtrepaymentduration;
    Button btnRequest;
    //private LinearLayout tocalender;
    private SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loanrequest);

        spinner = findViewById(R.id.loanCategory);
        edtstaffid=findViewById(R.id.edtstaffid);
        edtappliedamount=findViewById(R.id.edtappliedamount);
        edtrepaymentamount=findViewById(R.id.edtrepaymentamount);
        edtrepaymentduration=findViewById(R.id.edtrepaymentduration);
        edtinterestrate=findViewById(R.id.edtinterestrate);
        btnRequest=findViewById(R.id.btnRequestLOan);
        // session manager
        session = new SessionManager(getApplicationContext());

        loadLoanCategory();

        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyLoan();
            }
        });

    }


    private void loadLoanCategory() {

        String token = session.getToken();

        NetworkConnection.makeAGetRequest("https://hrms5.stl-horizon.com/api/web/api/loan-category?token=" + token, null, new OnReceivingResult() {
            @Override
            public void onErrorResult(IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onReceiving100SeriesResponse(RemoteResponse remoteResponse) {

            }

            @Override
            public void onReceiving200SeriesResponse(RemoteResponse remoteResponse) {
                NetworkConnection.remoteResponseLogger(remoteResponse);
                JSONObject response = remoteResponse.getMessangeAsJSON();
                try {
                    if (response.getString("success").equalsIgnoreCase("1")) {

                        LoanCategory loanCategory = LoanCategory.createLoanCategoryFrom(remoteResponse.getMessage());
                        List<LoanCategory.LoanCategoryModel> loanCategoryModel=loanCategory.getLoanCategoryData();

                        spinner.setAdapter(loanCategorySpinnerAdapter = new LoanCategorySpinnerAdapter(loanCategoryModel, LoanRequestActivity.this));

                        spinner.setOnItemSelectedListener(loanCategorySpinnerAdapter);

                        return;


                    } else {


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onReceiving300SeriesResponse(RemoteResponse remoteResponse) {

            }

            @Override
            public void onReceiving400SeriesResponse(RemoteResponse remoteResponse) {

            }

            @Override
            public void onReceiving500SeriesResponse(RemoteResponse remoteResponse) {

            }

            @Override
            public void onAnyEvent() {

            }
        });
    }


    public void applyLoan() {

        String token = session.getToken();

        JSONObject jsonObject = new JSONObject();
        JSONObject headers = new JSONObject();
        try {
            headers.put("Content-Type", "multipart/form-data");
            LoanCategory.LoanCategoryModel loanCategoryModel=(LoanCategory.LoanCategoryModel)spinner.getSelectedItem();

            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

            jsonObject.put("staff_id", "1");
            jsonObject.put("repayment_amount","2");
            jsonObject.put("interest_rate","3");
            jsonObject.put("applied_amount","5225");
            jsonObject.put("category_id",loanCategoryModel.getId());
            jsonObject.put("applied_on", date);
            jsonObject.put("repayment_duration", "25");

            Toast.makeText(LoanRequestActivity.this, loanCategoryModel.getId(), Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {

        }
        NetworkConnection.makeAPostRequestFormData("https://hrms5.stl-horizon.com/api/web/api/apply-loan?token=" + token, jsonObject, headers, new OnReceivingResult() {
            @Override
            public void onErrorResult(IOException e) {
                e.printStackTrace();


            }

            @Override
            public void onReceiving100SeriesResponse(RemoteResponse remoteResponse) {
            }

            @Override
            public void onReceiving200SeriesResponse(RemoteResponse remoteResponse) {
                NetworkConnection.remoteResponseLogger(remoteResponse);
                JSONObject response = remoteResponse.getMessangeAsJSON();
                LoanApplication loanApplication=LoanApplication.createLoanApplicationFrom(remoteResponse.getMessage());
                try {
                    if (response.getString("success").equalsIgnoreCase("1")) {

                        Toast.makeText(LoanRequestActivity.this, loanApplication.getMessage(), Toast.LENGTH_SHORT).show();

                        return;


                    }
                    else{
                        Toast.makeText(LoanRequestActivity.this, loanApplication.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onReceiving300SeriesResponse(RemoteResponse remoteResponse) {
            }

            @Override
            public void onReceiving400SeriesResponse(RemoteResponse remoteResponse) {
            }

            @Override
            public void onReceiving500SeriesResponse(RemoteResponse remoteResponse) {
            }

            @Override
            public void onAnyEvent() {

            }
        });

    }



}
