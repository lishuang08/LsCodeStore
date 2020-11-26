package ls.yylx.lscodestore.basemodule.network

import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonToken
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import okio.Buffer
import retrofit2.Converter
import retrofit2.Retrofit
import java.io.IOException
import java.io.OutputStreamWriter
import java.io.Writer
import java.lang.reflect.Type
import java.nio.charset.Charset


class MyConverterFactory(val gson: Gson) : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type?, annotations: Array<Annotation?>?, retrofit: Retrofit?
    ): Converter<ResponseBody, *> {
        val adapter: TypeAdapter<*> = gson.getAdapter(TypeToken.get(type))
        return MyResponseBodyConverter(gson, adapter)
    }

    override fun requestBodyConverter(
        type: Type?,
        parameterAnnotations: Array<Annotation?>?,
        methodAnnotations: Array<Annotation?>?,
        retrofit: Retrofit?
    ): Converter<*, RequestBody> {
        val adapter: TypeAdapter<*> = gson.getAdapter(TypeToken.get(type))
        return GsonRequestBodyConverter(gson, adapter)
    }

    companion object {
        @JvmOverloads  // Guarding public API nullability.
        fun create(gson: Gson? = Gson()): MyConverterFactory {
            if (gson == null) throw NullPointerException("gson == null")
            return MyConverterFactory(gson)
        }
    }

}


private class MyResponseBodyConverter<T>(
    private val gson: Gson,
    private val adapter: TypeAdapter<T>
) :
    Converter<ResponseBody, T> {
    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T {
        val jsonReader = gson.newJsonReader(value.charStream())
        return try {
            val result = adapter.read(jsonReader)
            if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
                throw JsonIOException("JSON document was not fully consumed.")
            }
            result
        } finally {
            value.close()
        }
    }
}

private class GsonRequestBodyConverter<T>(private val gson: Gson, private val adapter: TypeAdapter<T>) :
    Converter<T, RequestBody> {
    @Throws(IOException::class)
    override fun convert(value: T): RequestBody {
        val buffer = Buffer()
        val writer: Writer = OutputStreamWriter(buffer.outputStream(), Charset.forName("UTF-8"))
        val jsonWriter = gson.newJsonWriter(writer)
        adapter.write(jsonWriter, value)
        jsonWriter.close()
        return buffer.readByteString().toRequestBody(  "application/json; charset=UTF-8".toMediaType())
    }
}
