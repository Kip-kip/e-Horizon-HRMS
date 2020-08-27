package stlhorizon.org.hrmselfservice.model.spinners;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;


import java.util.List;

public class TrainingCourses {
    @Expose
    private String  success;
    @Expose
    private String  messsage;
    @Expose
    private String  title;
    @Expose
    private List<TrainingCoursesModel> data;

    public List<TrainingCoursesModel> getTrainingCoursesData() {
        return data;
    }

    public void setTrainingCoursesData(List<TrainingCoursesModel> data) {
        this.data = data;
    }

    public class TrainingCoursesModel {

        @Expose
        private String id;
        @Expose
        private String shortname;
        @Expose
        private String description;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getShortname() {
            return shortname;
        }

        public void setShortname(String shortname) {
            this.shortname = shortname;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
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

    public static TrainingCourses createTrainingCoursesFrom(String newsResponseString) {

        GsonBuilder gsonBuilder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        return gsonBuilder.create().fromJson(newsResponseString, TrainingCourses.class);
    }

    public static TrainingCourses.TrainingCoursesModel createTrainingCoursesModelFrom(String newsResponseString) {

        GsonBuilder gsonBuilder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        return gsonBuilder.create().fromJson(newsResponseString, TrainingCourses.TrainingCoursesModel.class);
    }

}
