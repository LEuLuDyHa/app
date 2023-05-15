package com.github.leuludyha.data.nearby_connection

import android.app.Activity
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import java.util.concurrent.Executor

class MockTask<TResult> : Task<TResult>() {

    override fun addOnFailureListener(p0: OnFailureListener): Task<TResult> {
        throw UnsupportedOperationException()
    }

    override fun addOnFailureListener(p0: Activity, p1: OnFailureListener): Task<TResult> {
        throw UnsupportedOperationException()
    }

    override fun addOnFailureListener(p0: Executor, p1: OnFailureListener): Task<TResult> {
        throw UnsupportedOperationException()
    }

    override fun getException(): Exception? {
        throw UnsupportedOperationException()
    }

    override fun getResult(): TResult {
        throw UnsupportedOperationException()
    }

    override fun <X : Throwable?> getResult(p0: Class<X>): TResult {
        throw UnsupportedOperationException()
    }

    override fun isCanceled(): Boolean {
        throw UnsupportedOperationException()
    }

    override fun isComplete(): Boolean {
        throw UnsupportedOperationException()
    }

    override fun isSuccessful(): Boolean {
        throw UnsupportedOperationException()
    }

    override fun addOnSuccessListener(
        p0: Executor,
        p1: OnSuccessListener<in TResult>
    ): Task<TResult> {
        throw UnsupportedOperationException()
    }

    override fun addOnSuccessListener(
        p0: Activity,
        p1: OnSuccessListener<in TResult>
    ): Task<TResult> {
        throw UnsupportedOperationException()
    }

    override fun addOnSuccessListener(p0: OnSuccessListener<in TResult>): Task<TResult> {
        throw UnsupportedOperationException()
    }
}