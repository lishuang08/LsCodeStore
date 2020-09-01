package ls.yylx.lscodestore.secondmodule.base


sealed class HomeState(val name: String)

data class State_Home(var argHome: HashMap<String, String>) : HomeState("主页")
data class State_Page0(var argPage0: HashMap<String, String>) : HomeState("page 0")
data class State_Page1(var argPage1: HashMap<String, String>) : HomeState("page 1")
data class State_Page2(var argPage2: HashMap<String, String>) : HomeState("page 2")
data class State_Page3(var argPage3: HashMap<String, String>) : HomeState("page 3")
