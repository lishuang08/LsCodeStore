package ls.yylx.lscodestore.basemodule.db

class CatApiSearchBack : ArrayList<CatApiSearchBackItem>()


data class CatApiSearchBackItem(
    val breeds: List<Any>,
    val categories: List<Category>,
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
)


data class Category(
    val id: Int,
    val name: String
)