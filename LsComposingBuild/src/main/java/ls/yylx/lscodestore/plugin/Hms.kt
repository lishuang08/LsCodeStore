package ls.yylx.lscodestore.plugin

/**
 *
 *
 * ml @see https://developer.huawei.com/consumer/cn/doc/development/HMSCore-Guides-V5/overview-sdk-0000001051070278-V5
 * */
object Hms {
    val scan = "com.huawei.hms:scan:1.2.3.300"



    // 文本识别 -引入基础SDK
    val ml_orc =  "com.huawei.hms:ml-computer-vision-ocr:2.0.1.300"

    val ml_card_icr_cn =  "com.huawei.hms:ml-computer-card-icr-cn:2.0.3.300"  // 引入身份证plugin与识别能力集合包。
    val ml_card_bcr =  "com.huawei.hms:ml-computer-card-bcr:2.0.3.300"    // 引入银行卡plugin与识别能力集合包。
    val ml_card_gcr =  "com.huawei.hms:ml-computer-card-gcr-plugin:2.0.1.300" // 引入通用卡证识别plugin包

    val ml_cloud=  "com.huawei.hms:ml-computer-vision-cloud:2.0.1.300"//文档识别服务

    val ml_orc_latin_model = "com.huawei.hms:ml-computer-vision-ocr-latin-model:2.0.1.300"    // 引入拉丁语文字识别模型包
    val ml_orc_jk_model = "com.huawei.hms:ml-computer-vision-ocr-jk-model:2.0.1.300" // 引入日韩语文字识别模型包
    val ml_orc_cn_model = "com.huawei.hms:ml-computer-vision-ocr-cn-model:2.0.1.300"// 引入中英文文字识别模型包



    // 翻译 -引入基础SDK
    val ml_translate =  "com.huawei.hms:ml-computer-translate:2.0.3.300"
    val _ml_translate_model =  "com.huawei.hms:ml-computer-translate-model:2.0.3.300"// 引入文本翻译算法包

}