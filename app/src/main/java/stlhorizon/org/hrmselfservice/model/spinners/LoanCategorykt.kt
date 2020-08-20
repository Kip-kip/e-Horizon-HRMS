package stlhorizon.org.hrmselfservice.model.Leave

import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import stlhorizon.org.hrmselfservice.model.highordermodels.Response


class LoanCategorykt : Response() {
    @Expose
    private var data: List<LoanCategoryModel>? =
        null

    var loanCategoryData: List<LoanCategoryModel>?
        get() = data
        set(data) {
            this.data = data
        }

    inner class LoanCategoryModel {
        @Expose
        var id: String? = null

        @Expose
        var loan_category: String? = null

        @Expose
        var interest_rate: String? = null

        @Expose
        var interest_type: String? = null

        @Expose
        var max_interest_spread: String? = null

        @Expose
        var emi_computation_formula: String? = null



        override fun toString(): String {
            val gsonBuilder = GsonBuilder().excludeFieldsWithoutExposeAnnotation()
            return gsonBuilder.create().toJson(this)
        }
    }

    override fun toString(): String {
        val gsonBuilder = GsonBuilder().excludeFieldsWithoutExposeAnnotation()
        return gsonBuilder.create().toJson(this)
    }

    companion object {
        fun createLoanCategoryFrom(newsResponseString: String?): LoanCategorykt {
            val gsonBuilder = GsonBuilder().excludeFieldsWithoutExposeAnnotation()
            return gsonBuilder.create().fromJson(newsResponseString, LoanCategorykt::class.java)
        }

        fun createLoanCategoryModelFrom(newsResponseString: String?): LoanCategoryModel {
            val gsonBuilder = GsonBuilder().excludeFieldsWithoutExposeAnnotation()
            return gsonBuilder.create().fromJson(
                newsResponseString,
                LoanCategoryModel::class.java
            )
        }
    }
}