package com.ryanmhub.fitnessapp.android_client.common.state


//Todo: Below are a bunch of Ideas on how to start building a universal state machine
data class State<out T>(val code : Int?, val message : T?)

data class DFA(
    val states : Set<State<Any>>,

)

//todo: should the generic be more specified
open class BaseAPIState<out T: Any>(){
    companion object {
        val Loading = BaseAPIState<Nothing>() //Todo: Should I initialize this differently
    }
    //class Loading<T> :
    class Loading : BaseAPIState<Nothing>()
    class Success<out T : Any>(val data : T?) : BaseAPIState<T>()
    class Failed<out T : Any>(val data : T?) : BaseAPIState<T>()
    class Error(val message: String?) : BaseAPIState<Nothing>()
}