package com.e.project_faces_recogniton.model

import java.io.Serializable

data class People(var people_id : Int, var first_name : String, var last_name: String, var events: Set<Event>?): Serializable {
    constructor(people_id: Int, first_name: String, last_name: String) : this(people_id, first_name, last_name, null)

    fun getName(): String {
         if (first_name == "Unkown" || last_name == "Unkown") {
            return "Personne inconnue"
        } else {
            return "$first_name $last_name"
        }
    }
}
