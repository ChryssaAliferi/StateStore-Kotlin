package com.rudderstack.statestore.sample

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.rudderstack.statestore.R
import com.rudderstack.statestore.statemanagement.SingleThreadStore
import com.rudderstack.statestore.statemanagement.Unsubscribe

class MainActivity : AppCompatActivity() {

    private lateinit var storeUnsubscriber: Unsubscribe
    private lateinit var store: SingleThreadStore<MainState, CounterAction>
    private lateinit var counter: TextView
    private lateinit var incrementButton: Button
    private lateinit var decrementButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        counter = findViewById(R.id.counter)
        incrementButton = findViewById(R.id.incrementButton)
        decrementButton = findViewById(R.id.decrementButton)

        store = SingleThreadStore(
            state = MainState(count = 0),
            reducer = MainReducer(),
            middleware = listOf(LoggingMiddleware())
        )
    }

    override fun onResume() {
        super.onResume()

        storeUnsubscriber = store.subscribe { currentState, dispatch ->
            counter.text = "Count: ${currentState.count}"

            incrementButton.setOnClickListener {
                dispatch(CounterAction.IncrementAction)
            }

            decrementButton.setOnClickListener {
                dispatch(CounterAction.DecrementAction)
            }
        }
    }

    private fun mainStateUpdate(mainState: MainState) {
        counter.text = "Count: ${mainState.count}"
    }

    override fun onPause() {
        storeUnsubscriber.invoke()
        super.onPause()
    }
}
