package com.rudderstack.statestore.sample

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.rudderstack.statestore.R
import com.rudderstack.statestore.statemanagement.SingleThreadStore
import com.rudderstack.statestore.statemanagement.Subscription

class MainActivity : AppCompatActivity() {

    private lateinit var store: SingleThreadStore<MainState, CounterAction>
    private lateinit var counter: TextView
    private lateinit var incrementButton: Button
    private lateinit var decrementButton: Button
    private val subscription: Subscription<MainState, CounterAction> = { state, _ ->
        counter.text = "Count: ${state.count}"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        counter = findViewById(R.id.counter)
        incrementButton = findViewById(R.id.incrementButton)
        decrementButton = findViewById(R.id.decrementButton)

        store = SingleThreadStore(
            initialState = MainState(count = 0),
            reducer = MainReducer(),
            middleware = listOf(LoggingMiddleware())
        )
        incrementButton.setOnClickListener {
            store.dispatch(CounterAction.IncrementAction)
        }

        decrementButton.setOnClickListener {
            store.dispatch(CounterAction.DecrementAction)
        }
    }


    override fun onResume() {
        super.onResume()
        store.subscribe(subscription)
    }

    override fun onPause() {
        store.unsubscribe(subscription)
        super.onPause()
    }
}
