package com.hsd.upc.hack.softeyes2

import com.google.gson.annotations.SerializedName
import java.util.*

data class MultipredictCaptionModel(
    var response: Response? = null,
    var error: String? = null,
    var time: String? = null,
    @SerializedName("correlation_id")
    var correlationId: String? = null,
    var version: String? = null,
) {

    override fun toString(): String {
        return response?.solutions?.caption?.description ?: ""
    }

    data class Response(var solutions: Solutions? = null) {
        data class Solutions(
            @SerializedName("re_roomtype_global_v2")
            var roomType: ReRoomtypeGlobalV2? = null,

            @SerializedName("re_features_v3")
            var features: ReFeaturesV3? = null,
            var caption: Caption? = null
        ) {
            data class ReRoomtypeGlobalV2(var predictions: List<Prediction>? = null) {
                data class Prediction(
                    var label: String? = null,
                    var confidence: Double? = null
                ) {

                }
            }

            data class ReFeaturesV3(var detections: List<Detection>? = null) {

                data class Detection(
                    @SerializedName("center_point")
                    var centerPoint: CenterPoint? = null,
                    var label: String? = null
                ) {
                    data class CenterPoint(
                        var x: Double? = null,
                        var y: Double? = null
                    ) {

                    }
                }
            }

            data class Caption(var description: String? = null) {

            }
        }
    }


}
