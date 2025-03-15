package com.dlucci.baseapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class SampleResponse(
    @PrimaryKey val foo : String = "test"
)
