package com.example.sqlitedemo

class Customer {

    var firstName: String = ""
    var lastName: String = ""
    var age: Int = 0
    var isActive: Boolean = false
    var id: Int = 0

    constructor(firstName: String?, lastName: String?, age: Int?, isActive: Boolean?) {
        this.firstName = firstName ?: ""
        this.lastName = lastName ?: ""
        this.age = age ?: 0
        this.isActive = isActive ?: false
    }

    constructor(
        id: Int,
        firstName: String?,
        lastName: String?,
        age: Int?,
        isActive: Boolean?
    ) : this(firstName, lastName, age, isActive) {
        this.id = id
    }

    override fun toString(): String {
        return "Customer(id=$id, firstName='$firstName', lastName='$lastName', age=$age, isActive=$isActive)"
    }

}