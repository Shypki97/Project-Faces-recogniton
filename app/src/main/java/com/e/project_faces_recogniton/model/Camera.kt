package com.e.project_faces_recogniton.model

import java.io.Serializable

class Camera(var camera_id: Int?, var camera_name: String, var location: String, var rtsp_flux: String,var events: Set<Event>?) : Serializable {
    constructor(camera_id: Int, camera_name: String, location: String, rtsp_flux: String) : this(camera_id, camera_name, location, rtsp_flux, null)
}