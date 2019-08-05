package com.example.offer_wall

import retrofit2.Call
import retrofit2.http.GET

interface AllowedService {
    @GET("allowed")
    fun getAllowedBoolean(): Call<AllowedBooleanObject>
}