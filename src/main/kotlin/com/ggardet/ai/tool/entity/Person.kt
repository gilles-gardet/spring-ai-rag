package com.ggardet.ai.tool.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "person")
class Person() {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(name = "first_name", nullable = false)
    var firstName: String? = null

    @Column(name = "last_name", nullable = false)
    var lastName: String? = null

    @Column(name = "birth_date")
    val birthDate: LocalDate? = null

    @Column(name = "email", unique = true, nullable = false)
    var email: String? = null

    @Column(name = "phone")
    val phone: String? = null

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now()

    @Column(name = "updated_at")
    val updatedAt: LocalDateTime = LocalDateTime.now()

    @JsonIgnore
    @OneToMany(mappedBy = "person", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    val addresses: MutableList<Address> = mutableListOf()

    constructor(firstName: String, lastName: String, email: String) : this() {
        this.firstName = firstName
        this.lastName = lastName
        this.email = email
    }

    override fun toString(): String {
        return "Person(id=$id, firstName=$firstName, lastName=$lastName, birthDate=$birthDate, email=$email, phone=$phone, createdAt=$createdAt, updatedAt=$updatedAt, addresses=$addresses)"
    }
}
