package com.moengage.testapp.networking.annotations

import java.lang.reflect.Method

class AnnotationsUtility {
    companion object {
        fun getAnnotationData(method: Method?): AnnotationsResponse? {
            if (method != null) {
                val annotation = method.getAnnotation(GET::class.java)
                if (annotation != null) {
                    return AnnotationsResponse(endPoint = annotation.endPoint, requestType = "GET")
                }
            }
            return null
        }
    }

}

data class AnnotationsResponse(val endPoint: String, val requestType: String)