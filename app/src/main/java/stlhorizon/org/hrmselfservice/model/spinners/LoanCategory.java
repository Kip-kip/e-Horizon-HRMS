package stlhorizon.org.hrmselfservice.model.spinners;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;


import java.util.List;

public class LoanCategory {
    @Expose
    private String  success;
    @Expose
    private String  messsage;
    @Expose
    private String  title;
    @Expose
    private List<LoanCategoryModel> data;

    public List<LoanCategoryModel> getLoanCategoryData() {
        return data;
    }

    public void setLoanCategoryData(List<LoanCategoryModel> data) {
        this.data = data;
    }

    public class LoanCategoryModel {

        @Expose
        private String id;
        @Expose
        private String loan_category;
        @Expose
        private String interest_rate;

        @Expose
        private String interest_type;
        @Expose
        private String max_interest_spread;
        @Expose
        private String emi_computation_formula;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLoan_category() {
            return loan_category;
        }

        public void setLoan_category(String loan_category) {
            this.loan_category = loan_category;
        }

        public String getInterest_rate() {
            return interest_rate;
        }

        public void setInterest_rate(String interest_rate) {
            this.interest_rate = interest_rate;
        }

        public String getInterest_type() {
            return interest_type;
        }

        public void setInterest_type(String interest_type) {
            this.interest_type = interest_type;
        }

        public String getMax_interest_spread() {
            return max_interest_spread;
        }

        public void setMax_interest_spread(String max_interest_spread) {
            this.max_interest_spread = max_interest_spread;
        }

        public String getEmi_computation_formula() {
            return emi_computation_formula;
        }

        public void setEmi_computation_formula(String emi_computation_formula) {
            this.emi_computation_formula = emi_computation_formula;
        }

        @Override
        public String toString() {
            GsonBuilder gsonBuilder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
            return gsonBuilder.create().toJson(this);

        }

    }

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        return gsonBuilder.create().toJson(this);

    }

    public static LoanCategory createLoanCategoryFrom(String newsResponseString) {

        GsonBuilder gsonBuilder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        return gsonBuilder.create().fromJson(newsResponseString, LoanCategory.class);
    }

    public static LoanCategory.LoanCategoryModel createLoanCategoryModelFrom(String newsResponseString) {

        GsonBuilder gsonBuilder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        return gsonBuilder.create().fromJson(newsResponseString, LoanCategory.LoanCategoryModel.class);
    }

}
