package com.ichsnn.core.executor

import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject

class AppExecutor constructor(
    private val diskIO: Executor,
) {
    @Inject
    constructor() : this(
        Executors.newSingleThreadExecutor(),
    )

    fun diskIO(): Executor = diskIO

}