package ls.yylx.lscodestore.basemodule

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.disklrucache.DiskLruCache
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.module.AppGlideModule


@GlideModule
class MyAppGlideModule : AppGlideModule(){
    //    //Glide设置cookie用
//    var LazyHeadersbuilder = LazyHeaders.Builder()

    //
//    fun getGlideUrl(url: String): GlideUrl {
//        LazyHeadersbuilder.setHeader("Cookie", Token)
//        return GlideUrl(url, LazyHeadersbuilder.build())
//    }

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)
        builder.setMemoryCache(LruResourceCache(100 * 1024 * 1024))
    }

}