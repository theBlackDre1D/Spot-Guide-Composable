package co.bluecrystal.core.interfaces

import java.io.Serializable

interface IHasUploadModel : Serializable {
    fun toUploadModel(): HashMap<String, Any?>
}