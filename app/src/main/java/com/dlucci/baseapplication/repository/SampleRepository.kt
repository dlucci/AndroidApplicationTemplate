package com.dlucci.baseapplication.repository

import com.dlucci.baseapplication.model.SampleResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SampleRepository(sampleService: SampleService) {

    val getNewsStories : Flow<Result<List<SampleResponse>>> = flow {
        val latestStories = sampleService.getFoo()
        if(latestStories.isSuccessful) {
            latestStories.body()?.let {
                emit(Result.success(it))
            }
        } else {
            emit(Result.failure(Throwable("${latestStories.code()}: ${latestStories.message()}")))

        }
    }

}