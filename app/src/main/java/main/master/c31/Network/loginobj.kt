package main.master.c31.Network


import com.google.gson.annotations.SerializedName

data class loginobj(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)