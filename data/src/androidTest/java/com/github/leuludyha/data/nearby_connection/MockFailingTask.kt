package com.github.leuludyha.data.nearby_connection

import android.app.Activity
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import java.util.concurrent.Executor

class MockFailingTask<TResult>(
    private val result: TResult?
) : Task<TResult>() {

    override fun addOnFailureListener(p0: OnFailureListener): Task<TResult> {
        p0.onFailure(java.lang.Exception("error"))
        return this
    }

    override fun addOnFailureListener(p0: Activity, p1: OnFailureListener): Task<TResult> {
        p1.onFailure(java.lang.Exception("error"))
        return this
    }

    override fun addOnFailureListener(p0: Executor, p1: OnFailureListener): Task<TResult> {
        p1.onFailure(java.lang.Exception("error"))
        return this
    }

    override fun getException(): Exception? = java.lang.Exception("error")

    override fun getResult(): TResult = result!!

    override fun <X : Throwable?> getResult(p0: Class<X>): TResult = result!!

    override fun isCanceled(): Boolean = false

    override fun isComplete(): Boolean = true

    override fun isSuccessful(): Boolean = false

    override fun addOnSuccessListener(
        p0: Executor,
        p1: OnSuccessListener<in TResult>
    ): Task<TResult> {
        p1.onSuccess(result)
        return this
    }

    override fun addOnSuccessListener(
        p0: Activity,
        p1: OnSuccessListener<in TResult>
    ): Task<TResult> {
        p1.onSuccess(result)
        return this
    }

    override fun addOnSuccessListener(p0: OnSuccessListener<in TResult>): Task<TResult> {
        p0.onSuccess(result)
        return this
    }
}