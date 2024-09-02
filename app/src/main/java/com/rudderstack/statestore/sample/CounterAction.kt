package com.rudderstack.statestore.sample

import com.rudderstack.statestore.statemanagement.Action

sealed class CounterAction : Action {
    data object IncrementAction : CounterAction()
    data object DecrementAction : CounterAction()
}
