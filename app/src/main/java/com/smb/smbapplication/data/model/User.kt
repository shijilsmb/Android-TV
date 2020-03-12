package com.smb.smbapplication.data.model

/**
 * Created by Shijil Kadambath on 03/08/2018
 * for NewAgeSMB
 * Email : shijil@newagesmb.com
 */
import androidx.annotation.NonNull
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(primaryKeys = ["id"])
  data class User(



        @NonNull
        @field:SerializedName("spec_id")
        var id: Int = 0,

        @field:SerializedName("speciality")
        var name: String? = null

)