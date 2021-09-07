package com.chopshop166.chopshoplib

// Compilation test
fun testDashboard() {
    dashboard {
        "Foo" displays 1234.0
        "Bar" displays 5678
        "Baz" displays "Hello, world!"
    }
    dashboard["asdf"] = "qwerty"
}

fun testShuf() {
    shuffleboard("tabName") {
        "Foo" { 1234.0 }
        "Bar" { 5678 }
        "Baz" { "Hello, world!" }.withSize(2, 1)
        layout("x") {
            "y" { "z" }
        }
    }
}