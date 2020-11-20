package com.e.project_faces_recogniton.model

import java.io.Serializable
import java.sql.Timestamp

data class Event( var event_id : Int, var timestamp : String, var camera: Camera, var people: People ) : Serializable {

    init {
        timestamp = dateReformate(timestamp)
    }
    fun dateReformate(stringS: String): String {
        if(!stringS.equals(" ")) {
            val splitS = stringS.split("-")
            val stringN = splitS[1] + "-" + splitS[0] + " " + splitS[2]
            val splitN = stringN.split(" ")
            val stringH = splitN[1] + "-" + splitN[0] + " " + splitN[2]
            return stringH
        }else{
            return stringS
        }
    }

}
