package stlhorizon.org.hrmselfservice.model.loan;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
public class LoanApplication{
    @Expose
    private String success;
    @Expose
    private String message;
    @Expose
    private String title;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static LoanApplication createLoanApplicationFrom(String message){
        GsonBuilder gsonBuilder= new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        return  gsonBuilder.create().fromJson(message, LoanApplication.class);

    }

}
