package com.mycanada.poc.repository.repoutils

import android.util.Log
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.mycanada.poc.AppExecutors
import com.mycanada.poc.api.ApiEmptyResponse
import com.mycanada.poc.api.ApiErrorResponse
import com.mycanada.poc.api.ApiResponse
import com.mycanada.poc.api.ApiSuccessResponse

/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 *
 *
 * You can read more about it in the [Architecture
 * Guide](https://developer.android.com/arch).
 * @param <ResultType>
 * @param <RequestType>
</RequestType></ResultType> */
abstract class NetworkBoundResource<ResultType, RequestType>
@MainThread constructor(private val appExecutors: AppExecutors) {

    private val TAG = NetworkBoundResource::class.java.name
    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value =
            Resource.loading(null)
        @Suppress("LeakingThis")
        fetchFromNetwork()
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    private fun fetchFromNetwork() {
        val apiResponse = createCall()
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            when (response) {
                is ApiSuccessResponse -> {
                    appExecutors.diskIO().execute {
                        appExecutors.mainThread().execute {
                            setValue(
                                Resource.success(
                                    processResponse(response)
                                ) as Resource<ResultType>
                            )
                        }
                    }
                }
                is ApiEmptyResponse -> {
                    appExecutors.mainThread().execute {
                        setValue(
                            Resource.error(
                                "No data found",
                                null
                            )
                        )
                    }
                }
                is ApiErrorResponse -> {
                    appExecutors.mainThread().execute { setValue(
                        Resource.error(
                            response.errorMessage,
                            null
                        )
                    ) }
                    onFetchFailed()
                    Log.d("NetworkBoundResource", " Error :  "+response.errorMessage)
                }
            }
        }
    }

    protected open fun onFetchFailed() {}

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    @WorkerThread
    protected open fun processResponse(response: ApiSuccessResponse<RequestType>) = response.body

    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>
}
