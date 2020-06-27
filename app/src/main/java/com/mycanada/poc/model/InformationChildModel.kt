package com.mycanada.poc.model

import com.google.gson.annotations.SerializedName

data class InformationChildModel(@SerializedName("imageHref")
                           val imageHref: String = "",
                                 @SerializedName("description")
                           val description: String = "",
                                 @SerializedName("title")
                           val title: String = "")