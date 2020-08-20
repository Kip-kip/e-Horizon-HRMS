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


        loadLoanCategory();

        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyLoan();
            }
        });

    }


    private void loadLoanCategory() {

        String token ="eyJpYXQiOjE1OTY0NDU1MzUsImlzcyI6ImhybXM1LnN0bC1ob3Jpem9uLmNvbSIsIm5iZiI6MTU5NjQ0NTUzNSwiZXhwIjoxNTk2NDQ1NTQ1LCJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiIsImtpZCI6Ijg1ZjFjNTQ4Y2VlNWI2ODNmYWE0MGNjNjJhYTA1YWJjIn0.eyJ1c2VyX2lkIjoyMzEsInVzZXJuYW1lIjoiQ3lydXMiLCJmdWxsX25hbWUiOiJDeXJ1cyAgS2lwcm90aWNoIiwicGFydHlfaWQiOiIxNDg4MDgxIiwiZGF0ZV9vZl9iaXJ0aCI6IjE5OTQtMDktMTkiLCJnZW5kZXIiOiJNQUxFIiwiY2l0eSI6Ik5BSVJPQkkiLCJjb3VudHJ5IjoiS0UiLCJhcHBvaW50X2lkIjoiMTQ4ODA4NSIsImVudGl0eV9pZCI6IjEwMCIsImVudGl0eV9uYW1lIjoiU09GVFdBUkUgVEVDSE5PTE9HSUVTIExJTUlURUQiLCJwZXJubyI6IlNUTDEzNCIsImNvZGUiOiJIUjUwMDEiLCJpbWFnZSI6bnVsbH0.rDnJfGiTVFSjNtTGqTIw9iv-XI64_yg2PrHnrzRyGGo";


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

        String token ="eyJpYXQiOjE1OTY0NDU1MzUsImlzcyI6ImhybXM1LnN0bC1ob3Jpem9uLmNvbSIsIm5iZiI6MTU5NjQ0NTUzNSwiZXhwIjoxNTk2NDQ1NTQ1LCJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiIsImtpZCI6Ijg1ZjFjNTQ4Y2VlNWI2ODNmYWE0MGNjNjJhYTA1YWJjIn0.eyJ1c2VyX2lkIjoyMzEsInVzZXJuYW1lIjoiQ3lydXMiLCJmdWxsX25hbWUiOiJDeXJ1cyAgS2lwcm90aWNoIiwicGFydHlfaWQiOiIxNDg4MDgxIiwiZGF0ZV9vZl9iaXJ0aCI6IjE5OTQtMDktMTkiLCJnZW5kZXIiOiJNQUxFIiwiY2l0eSI6Ik5BSVJPQkkiLCJjb3VudHJ5IjoiS0UiLCJhcHBvaW50X2lkIjoiMTQ4ODA4NSIsImVudGl0eV9pZCI6IjEwMCIsImVudGl0eV9uYW1lIjoiU09GVFdBUkUgVEVDSE5PTE9HSUVTIExJTUlURUQiLCJwZXJubyI6IlNUTDEzNCIsImNvZGUiOiJIUjUwMDEiLCJpbWFnZSI6bnVsbH0.rDnJfGiTVFSjNtTGqTIw9iv-XI64_yg2PrHnrzRyGGo";

        JSONObject jsonObject = new JSONObject();
        try {
            LoanCategory.LoanCategoryModel loanCategoryModel=(LoanCategory.LoanCategoryModel)spinner.getSelectedItem();

            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            Toast.makeText(this, date, Toast.LENGTH_SHORT).show();

            jsonObject.put("staff_id", edtstaffid.getText().toString());
            jsonObject.put("repayment_amount", edtrepaymentamount.getText().toString());
            jsonObject.put("interest_rate", edtinterestrate.getText().toString());
            jsonObject.put("applied_amount", edtappliedamount.getText().toString());
            jsonObject.put("category_id",loanCategoryModel.getId());
            jsonObject.put("applied_on", date);
            jsonObject.put("repayment_duration", edtrepaymentduration.getText().toString());



        } catch (JSONException e) {

        }

        NetworkConnection.makeAPostRequest("https://hrms5.stl-horizon.com/api/web/api/apply-loan?token=" + token, jsonObject.toString(), null, new OnReceivingResult() {
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
