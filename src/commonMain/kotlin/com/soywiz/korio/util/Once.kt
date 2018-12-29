package com.soywiz.korio.util

import com.soywiz.korio.async.asyncImmediately
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.coroutineScope

class Once {
	var completed = false

	inline operator fun invoke(callback: () -> Unit) {
		if (!completed) {
			completed = true
			callback()
		}
	}
}

class AsyncOnce<T> {
	var promise: Deferred<T>? = null

	suspend operator fun invoke(callback: suspend () -> T): T {
		coroutineScope {
			if (promise == null) {
				promise = asyncImmediately { callback() }
			}
		}
		return promise!!.await()
	}
}