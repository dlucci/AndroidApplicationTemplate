package com.dlucci.baseapplication.repository

import com.dlucci.baseapplication.model.SampleResponse
import retrofit2.Response
import retrofit2.http.GET

interface SampleService {

    @GET("foo")
    suspend fun getFoo() : Response<List<SampleResponse>>

}