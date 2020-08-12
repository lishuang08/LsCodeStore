package ls.yylx.lscodestore.basemodule.network

import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.MediaType
import okhttp3.Response
import okhttp3.ResponseBody
import okio.*
import java.io.IOException

class DownloadProgressInterceptor(val progressListener: DownloadProgressListener) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        return originalResponse.newBuilder()
            .body(
                DownloadProgressResponseBody(
                    originalResponse.body,
                    progressListener
                )
            )
            .build()
    }

    class DownloadProgressResponseBody(
        private val responseBody: ResponseBody?,
        private val progressListener: DownloadProgressListener
    ) : ResponseBody() {
        private var bufferedSource: BufferedSource? = null

        override fun contentType(): MediaType? {
            return responseBody!!.contentType()
        }

        override fun contentLength(): Long {
            return responseBody!!.contentLength()
        }

        override fun source(): BufferedSource {
            if (bufferedSource == null) {
                bufferedSource = source(responseBody!!.source()).buffer()
            }

            return bufferedSource!!
        }

        private fun source(source: Source) = object : ForwardingSource(source) {
            var totalBytesRead = 0L
            override fun read(sink: Buffer, byteCount: Long): Long {
                val bytesRead = super.read(sink, byteCount)

                totalBytesRead += if (bytesRead != -1L) bytesRead else 0

                progressListener.update(
                    totalBytesRead,
                    responseBody!!.contentLength(),
                    bytesRead == -1L
                )
                return bytesRead
            }
        }
    }

    interface DownloadProgressListener {
        fun update(bytesRead: Long, contentLength: Long, done: Boolean)
    }

}