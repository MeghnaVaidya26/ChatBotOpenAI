
import com.app.model.ChatMessageModel
import com.app.model.ParamModel
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    //TODO: All Auth Module Api
    @POST("completions")
    fun loadMessage(
        @Header("Authorization") authorization: String,
       @Body param:ParamModel
    ): Call<ChatMessageModel>


}



