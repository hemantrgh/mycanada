package com.mycanada.poc.repository

import androidx.lifecycle.LiveData
import com.mycanada.poc.AppExecutors
import com.mycanada.poc.api.InformationAPI
import com.mycanada.poc.model.InformationModel
import com.mycanada.poc.repository.repoutils.NetworkBoundResource
import com.mycanada.poc.repository.repoutils.Resource
import javax.inject.Inject

class InformationRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val informationAPI: InformationAPI) {

    fun getInformation(): LiveData<Resource<InformationModel>> {
        return object : NetworkBoundResource<InformationModel, InformationModel>(appExecutors) {
            override fun createCall() = informationAPI.getInformation()
        }.asLiveData()
    }

}