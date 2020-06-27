package com.mycanada.poc.model

import com.google.gson.annotations.SerializedName

data class InformationModel(@SerializedName("title")
                       val title: String = "",
                            @SerializedName("rows")
                       val rows: List<InformationChildModel>?)