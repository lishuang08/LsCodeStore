package ls.yylx.lscodestore.basemodule.network

// Informs Dagger that this class should be constructed only once.
//@Singleton
//class UserRepository @Inject constructor(
//    private val webservice: Webservice,
//    private val userDao: UserDao
//) {
//    fun getUser(userId: String): LiveData<User> {
//        return object : NetworkBoundResource<User, User>() {
//            override fun saveCallResult(item: User) {
//                userDao.save(item)
//            }
//
//            override fun shouldFetch(data: User?): Boolean {
//                return rateLimiter.canFetch(userId) && (data == null || !isFresh(data))
//            }
//
//            override fun loadFromDb(): LiveData<User> {
//                return userDao.load(userId)
//            }
//
//            override fun createCall(): LiveData<ApiResponse<User>> {
//                return webservice.getUser(userId)
//            }
//        }.asLiveData()
//    }
//}