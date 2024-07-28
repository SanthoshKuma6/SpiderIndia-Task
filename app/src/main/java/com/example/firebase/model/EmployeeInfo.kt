package com.example.firebase.model

import java.io.Serializable



data class EmployeeInfo (
    var employeeId: String? = null,
    var studentName: String? = null,
    var studentClass: String? = null,
    var studentSection : String? = null,
    var schoolName: String? = null,
    var gender: String? = null,
    var dateOfBirth: String? = null,
    var bloodGroup: String? = null,
    var fatherName: String? = null,
    var matherName: String? = null,
    var parentContactNumber: String? = null,
    var address1: String? = null,
    var address2: String? = null,
    var city: String? = null,
    var state: String? = null,
    var zip: String? = null,
    var emergencyContactNumber: String? = null,

    ): Serializable
