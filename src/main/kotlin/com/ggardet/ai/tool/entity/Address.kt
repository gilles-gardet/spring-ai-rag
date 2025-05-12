package com.ggardet.ai.tool.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "address")
class Address() {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(name = "street", nullable = false)
    lateinit var street: String

    @Column(name = "city", nullable = false)
    lateinit var city: String

    @Column(name = "state")
    val state: String? = null

    @Column(name = "postal_code", nullable = false)
    lateinit var postalCode: String

    @Column(name = "country", nullable = false)
    lateinit var country: String

    @Column(name = "is_primary")
    val isPrimary: Boolean = false

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now()

    @Column(name = "updated_at")
    val updatedAt: LocalDateTime = LocalDateTime.now()

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    var person: Person? = null

    override fun toString(): String {
        return "Address(id=$id, street='$street', city='$city', state=$state, postalCode='$postalCode', country='$country', isPrimary=$isPrimary, createdAt=$createdAt, updatedAt=$updatedAt, person=$person)"
    }
}
