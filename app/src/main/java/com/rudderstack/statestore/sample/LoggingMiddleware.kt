package com.rudderstack.statestore.sample

import android.util.Log
import com.rudderstack.statestore.statemanagement.Dispatch
import com.rudderstack.statestore.statemanagement.Middleware
import com.rudderstack.statestore.statemanagement.Next

class LoggingMiddleware : Middleware<MainState, CounterAction> {
    override fun invoke(
        state: MainState,
        action: CounterAction,
        dispatch: Dispatch<CounterAction>,
        next: Next<MainState, CounterAction>
    ): CounterAction {
        Log.d("Redux Sample App Log", "Action: $action")
        return next(state, action, dispatch)
    }
}
