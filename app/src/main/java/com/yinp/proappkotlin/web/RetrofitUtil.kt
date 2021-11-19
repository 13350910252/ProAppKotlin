
import com.google.gson.Gson
import com.lp.myapplication.net.Interceptor
import com.yinp.proappkotlin.web.ApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitUtil {
    companion object {
        val okHttpClient by lazy {
            OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(Interceptor().loggingInterceptor())//OkHttp进行添加拦截器loggingInterceptor
                .addNetworkInterceptor(Interceptor().headerInterceptor())
                .build()
        }

        val retrofitUtil by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            RetrofitUtil()
        }

        /**
         * wandroid的专属
         */
        val wandroidApiService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            (Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) //设置gson转换器,将返回的json数据转为实体（自定义factory解密）
                .baseUrl("https://www.wanandroid.com") //设置CallAdapter
                .client(okHttpClient)
                .build()).create(ApiService::class.java)
        }
    }

    /**
     * 获取Retrofit
     * */
    fun getVariableApiService(url: String): ApiService {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) //设置gson转换器,将返回的json数据转为实体（自定义factory解密）
            .baseUrl(url) //设置CallAdapter
            .client(okHttpClient)
            .build()
        return retrofit.create(ApiService::class.java)
    }

    /**
     * 取消网络请求
     */
    fun cancelCallsWithTag(tag: Any?) {
        if (tag == null) {
            return
        }
        synchronized(okHttpClient.dispatcher.javaClass) {
            for (call in okHttpClient.dispatcher.queuedCalls()) {
                if (tag == call.request().tag()) call.cancel()
            }
            for (call in okHttpClient.dispatcher.runningCalls()) {
                if (tag == call.request().tag()) call.cancel()
            }
        }
    }

    fun toJson(json: String): RequestBody {
        return json.toRequestBody("application/json".toMediaTypeOrNull())
    }
}
