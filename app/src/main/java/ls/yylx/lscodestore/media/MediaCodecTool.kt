package ls.yylx.lscodestore.media

object MediaCodecTool {

//    private val SAMPLE: String = Environment
//        .getExternalStorageDirectory().toString() + "/DCIM/Camera/20140506_174959.mp4"
//
//    private val OUTPUT_PATH: String = Environment
//        .getExternalStorageDirectory()
//        .toString() + "/DCIM/Camera/20140506_174959_REC.mp4"
//
//    private val extractor by lazy {
//        MediaExtractor()
//    }
//    private var decoder: MediaCodec? = null
//    private var encoder: MediaCodec? = null
//    var mime: String? = null
//
//    private const val MIME_TYPE = "video/avc"
//
//    fun extractMediaFile() {
//
//        // work plan
//        // locate media file
//        // extract media file using Media Extractor
//        // retrieve decoded frames
//        try {
//            extractor.setDataSource(SAMPLE)
//        } catch (e: IOException) {
//            // TODO Auto-generated catch block
//            // file not found
//            e.printStackTrace()
//        }
//
//        // add decoded frames
//        for (i in 0 until extractor.getTrackCount()) {
//            val format: MediaFormat = extractor.getTrackFormat(i)
//            mime = format.getString(MediaFormat.KEY_MIME)
//            if (mime!!.startsWith("video/")) {
//                extractor.selectTrack(i)
//                decoder = MediaCodec.createDecoderByType(mime!!)
//                decoder?.configure(format, null, null, 0)
//                break
//            }
//        }
//        if (decoder == null) {
//            Log.e("DecodeActivity", "Can't find video info!")
//            return
//        }
//
//        // - start decoder -
//        decoder?.start()
//        extractor.selectTrack(0)
//
//        // - decoded frames can obtain in here -
//    }
//
//    private fun createsEncoder() {
//
//        // creates media encoder to set formats
//        encoder = MediaCodec.createDecoderByType(MIME_TYPE)
//
//        // init media format
//        val mediaFormat: MediaFormat = MediaFormat.createVideoFormat(
//            MIME_TYPE,  /* 640 */
//            320,  /* 480 */240
//        )
//        mediaFormat.setInteger(MediaFormat.KEY_BIT_RATE, 400000)
//        mediaFormat.setInteger(MediaFormat.KEY_FRAME_RATE, 25)
//        mediaFormat.setInteger(
//            MediaFormat.KEY_COLOR_FORMAT,
//            MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420SemiPlanar
//        )
//        mediaFormat.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 5)
//        encoder?.configure(
//            mediaFormat, null, null,
//            MediaCodec.CONFIGURE_FLAG_ENCODE
//        )
//        encoder?.start()
//
//        // - encoded data format is avaiable in here
//    }
//
//    private fun createMuxer() {
//
//        // creates media muxer - media muxer will be used to write the final
//        // strem in to a desired file :)
//        try {
//            val muxer = MediaMuxer(
//                OUTPUT_PATH,
//                MUXER_OUTPUT_MPEG_4
//            )
//            val videoTrackIndex: Int = muxer.addTrack(encoder!!.outputFormat)
//
//            //muxer.writeSampleData(videoTrackIndex, inputBuffers, bufferInfo);
//            muxer.start()
//        } catch (e: IOException) {
//            // TODO Auto-generated catch block
//            e.printStackTrace()
//        }
//    }

}