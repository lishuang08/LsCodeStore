package ls.yylx.lscodestore.basemodule.backgroud

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

class Works {

    class MyWorker0(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {
        override fun doWork(): Result {
            println("""
                work   000
            """.trimIndent())
            return Result.success()
        }
    }

    class MyWorker1(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {
        override fun doWork(): Result {
            println("""
                work   111
            """.trimIndent())
            return Result.success()
        }
    }


    class kotlinWork(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {
        override suspend fun doWork(): Result {

            val map = workDataOf(
                    "a" to 0,
                    "b" to 2,
                    "c" to "A"
            )
            return  Result.success(map)
        }

    }


    class MyWorker2(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {
        override fun doWork(): Result {
            println("""
                work   222
            """.trimIndent())
            return Result.success()
        }
    }

    /**循环任务*/
    fun periodWork() {
        val periodicWorkRequest = PeriodicWorkRequest.Builder(MyWorker0::class.java, 20, TimeUnit.SECONDS).build()
        WorkManager.getInstance()?.enqueue(periodicWorkRequest)
    }

    /**单次任务*/
    fun oneTimeWork() {
        val list = listOf( MyWorker0::class.java, MyWorker1::class.java)
        val oneTimeWorkRequest = OneTimeWorkRequest.from(list)
//        UpdateSpecies.getInstance().beginWith(oneTimeWorkRequest).enqueue()
        WorkManager.getInstance()?.enqueue(oneTimeWorkRequest)

    }

    /**链式任务,chain0和chain1执行完毕后，才能执行chain2*/
    fun chainWork() {
        val oneTimeWorkRequest0 = OneTimeWorkRequest.from(MyWorker0::class.java)
        val oneTimeWorkRequest1 = OneTimeWorkRequest.from(MyWorker1::class.java)
        val oneTimeWorkRequest2 = OneTimeWorkRequest.from(MyWorker2::class.java)

        val chain0 = WorkManager.getInstance().beginWith(oneTimeWorkRequest0)
        val chain1 = WorkManager.getInstance().beginWith(oneTimeWorkRequest1)

        val list= arrayListOf<WorkContinuation >(chain0,chain1)

        val chain2 = WorkContinuation.combine(list).then(oneTimeWorkRequest2)

        chain2.enqueue()
    }


}