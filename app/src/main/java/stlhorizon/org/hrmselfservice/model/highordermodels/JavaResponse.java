package stlhorizon.org.hrmselfservice.model.highordermodels;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public class JavaResponse {
    @Expose
    protected String success;


    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }



    public static JavaResponse createResponseFrom(String newsResponseString){

        GsonBuilder gsonBuilder= new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        return  gsonBuilder.create().fromJson(newsResponseString, JavaResponse.class);
    }

    @Override
    public String toString() {
        GsonBuilder gsonBuilder= new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        return  gsonBuilder.create().toJson(this);

    }
    public boolean isAResponseASuccess(){
        if(success==null){
            try {
                throw  new Exception("Response not initialized");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(success.equalsIgnoreCase("1")){
            return true;
        }
        return false;
    }
}
