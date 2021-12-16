package com.ecampus.bem.data.model

import com.google.gson.annotations.SerializedName

data class BaseRespon<T>(
    @SerializedName("stts") var stts: Boolean? = null,
    @SerializedName("msg") var msg: String? = null,
    @SerializedName("data") var data: T? = null,
)

data class BaseResponList<T>(
    @SerializedName("stts") var stts: Boolean? = null,
    @SerializedName("msg") var msg: String? = null,
    @SerializedName("data") var data: T? = null,
)

data class BaseResponData(
    @SerializedName("stts") var stts: Boolean? = null,
    @SerializedName("msg") var msg: String? = null,
)

data class UserRespon(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("nim") var nim: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("no_phone") var no_phone: String? = null,
    @SerializedName("prodi") var prodi: String? = null,
    @SerializedName("image") var image: String? = null,
    @SerializedName("created_by") var created_by: String? = null,
)

data class PaslonRespon(
    @SerializedName("id") val id: String,
    @SerializedName("nim_presiden") val nim_presiden: String,
    @SerializedName("nama_presiden") val nama_presiden: String,
    @SerializedName("nim_wakil") val nim_wakil: String,
    @SerializedName("nama_wakil") val nama_wakil: String,
    @SerializedName("visi") val visi: String,
    @SerializedName("misi") val misi: String,
    @SerializedName("image") val image: String,
    @SerializedName("stts") val stts: String,
    @SerializedName("urutan") val urutan: String,
    @SerializedName("create") val create: Any,
)

data class QuesStts(
    @SerializedName("id") val id: String,
    @SerializedName("id_paslon") val id_paslon: String,
    @SerializedName("stts") val stts: String,
    @SerializedName("nim_presiden") val nim_presiden: String,
    @SerializedName("nama_presiden") val nama_presiden: String,
    @SerializedName("nim_wakil") val nim_wakil: String,
    @SerializedName("nama_wakil") val nama_wakil: String,
    @SerializedName("urutan") val urutan: String,
    @SerializedName("image") val image: String,
)

data class Ques(
    @SerializedName("id") val id: String,
    @SerializedName("id_paslon") val id_paslon: String,
    @SerializedName("id_user") val id_user: String,
    @SerializedName("soal") val soal: String,
    @SerializedName("stts") val stts: String,
    @SerializedName("name") val name: String,
    @SerializedName("nim") val nim: String,
    @SerializedName("prodi") val prodi: String,
)

data class Voting(

    @SerializedName("nama_presiden") val nama_presiden: String,
    @SerializedName("nama_wakil") val nama_wakil: String,
    @SerializedName("urutan") val urutan: String,
    @SerializedName("suara") val suara: String,
)


