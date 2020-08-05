package ls.yylx.lscodestore.ui.coroutines


import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.orhanobut.logger.Logger
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import ls.yylx.lscodestore.R
import ls.yylx.lscodestore.base.BaseFragment
import splitties.views.dsl.core.Ui
import splitties.views.dsl.core.frameLayout
import kotlin.system.measureTimeMillis


class CoroutinesFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = requireContext().frameLayout {  }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlobalScope.launch(Dispatchers.Main) {
            launch {

            }


            runBlocking { delay(30000) }

        }

        GlobalScope.launch {

            val squares = produceSquares()
            squares.consumeEach { println(it) }
            println("Done!")

            /**10秒后超时，抛出异常*/
            try {
                withTimeout(10 * 1000L) {
                    repeat(20) {
                        delay(1000)
                        Logger.e("tiems   $it ")
                    }
                }
            } catch (e: Exception) {
                Logger.e(e.toString())
            }

            /**切换线程*/
            withContext(Dispatchers.Main) {
                println(Looper.myLooper()?.thread?.name)
            }


            runBlocking<Unit> {
                val counter = counterActor() // create the actor
                massiveRun {
                    counter.send(IncCounter)
                }

                // send a message to get a counter value from an actor
                val response = CompletableDeferred<Int>()
                counter.send(GetCounter(response))
                println("Counter = ${response.await()}")
                counter.close() // shutdown the actor
            }


            val fibonacciSeq = sequence {
                var a = 0
                var b = 1

                yield(1)

                while (true) {
                    yield(a + b)

                    val tmp = a + b
                    a = b
                    b = tmp
                }
            }
        }


    }

    /**发送消息*/
    fun produceSquares() = GlobalScope.produce {
        for (x in 1..5) send(x * x)
    }


    // This function launches a new counter actor
    fun counterActor() = GlobalScope.actor<CounterMsg> {
        var counter = 0 // actor state
        for (msg in channel) { // iterate over incoming messages
            when (msg) {
                is IncCounter -> counter++
                is GetCounter -> msg.response.complete(counter)
            }
        }
    }

    suspend fun massiveRun(action: suspend () -> Unit) {
        val n = 1000 // number of coroutines to launch
        val k = 1000 // times an action is repeated by each coroutine
        val time = measureTimeMillis {
            val jobs = List(n) {
                GlobalScope.launch(Dispatchers.Main) {
                    repeat(k) { action() }
                }
            }
            jobs.forEach { it.join() }
        }
        println("Completed ${n * k} actions in $time ms")
    }

}

// Message types for counterActor
sealed class CounterMsg

object IncCounter : CounterMsg() // one-way message to increment counter
class GetCounter(val response: CompletableDeferred<Int>) : CounterMsg() // a request with reply