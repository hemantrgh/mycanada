package com.mycanada.poc.api

import androidx.lifecycle.LiveData
import com.mycanada.poc.model.InformationModel
import retrofit2.http.GET

interface InformationAPI {

    @GET("/s/2iodh4vg0eortkl/facts.json")
    fun getInformation(): LiveData<ApiResponse<InformationModel>>
}