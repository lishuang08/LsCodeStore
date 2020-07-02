package ls.yylx.lscodestore.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class Converters {

    @TypeConverter
    fun toList(str: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(str, listType)

    }

    @TypeConverter
    fun fromList(date: List<String>): String {
        val gson = Gson()
        return gson.toJson(date)
    }












}