
import android.app.Activity
import com.app.utils.Constant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

  var BASE_URL = "https://api.openai.com/v1/"

    private var retrofit: Retrofit? = null

    fun getClient(context: Activity): ApiInterface {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .connectTimeout(Constant.connectionTimeOut, TimeUnit.SECONDS)
            .addInterceptor(ConnectivityInterceptor(context))
            .addInterceptor(logging)
            .readTimeout(Constant.readTimeOut, TimeUnit.SECONDS).build()

        if (retrofit == null) {
            // <-- this is the important line!
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        return retrofit!!.create(ApiInterface::class.java)


    }

}
