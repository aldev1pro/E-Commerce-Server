package com.example

import io.ktor.serialization.kotlinx.cbor.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.serialization.kotlinx.xml.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.cbor.Cbor
import kotlinx.serialization.json.Json
import nl.adaptivity.xmlutil.XmlDeclMode
import nl.adaptivity.xmlutil.serialization.XML
import org.json.simple.JSONObject

fun Application.configureSerialization(){
    install(ContentNegotiation){

     //   json()
   //     xml()
 //       cbor()
        json(Json{
            prettyPrint = true
            ignoreUnknownKeys = true

      })
//        xml(XML{
//            xmlDeclMode = XmlDeclMode.Minimal
//        })
//        cbor(Cbor{
//            //ignoreUnknownKeys = true
//        })
    }
}