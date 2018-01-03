package com.djdapz.stubhub.config

@FunctionalInterface
interface SecurityConfig {
    fun getToken(): String
}
