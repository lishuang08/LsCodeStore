package ls.yylx.lscodestore.dataclass


data class PreviewItem(
    var image  : Boolean = true ,
    var path :String = "" ,
    var name :String ="" ,
    var ext :String ="" ,
    var size :Long = 0L
)