package ls.yylx.lscodestore.ffmpeg

class FFmpegCmdBuilder {

//    val cmd = "-y -i ${file1.path} -vf scale=1280:1920*1280/1080,crop=1280:720 -threads 5 -preset ultrafast -strict -2 ${file2.path}"

    var cmd = ""


    fun build(): String {

        return cmd
    }
}