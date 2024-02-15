package com.example.shareworldz.Models

class User {
    var image:String?=null
    var name:String?=null
    var email:String?=null
    var passwoed:String?=null
    constructor()
    constructor(image: String?, name: String?, email: String?, passwoed: String?) {
        this.image = image
        this.name = name
        this.email = email
        this.passwoed = passwoed
    }

    constructor(name: String?, email: String?, passwoed: String?) {
        this.name = name
        this.email = email
        this.passwoed = passwoed
    }

    constructor(email: String?, passwoed: String?) {
        this.email = email
        this.passwoed = passwoed
    }


}