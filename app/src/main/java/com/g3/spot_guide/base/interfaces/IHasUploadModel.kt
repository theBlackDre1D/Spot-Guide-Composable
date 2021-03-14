package com.g3.spot_guide.base.interfaces

import java.io.Serializable

interface IHasUploadModel : Serializable {
    fun toUploadModel(): HashMap<String, Any?>
}