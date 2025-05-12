package com.ggardet.ai.tool.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "address")
data class Address(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "street", nullable = false)
    val street: String,

    @Column(name = "city", nullable = false)
    val city: String,

    @Column(name = "state")
    val state: String? = null,

    @Column(name = "postal_code", nullable = false)
    val postalCode: String,

    @Column(name = "country", nullable = false)
    val country: String,

    @Column(name = "is_primary")
    val isPrimary: Boolean = false,

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    val updatedAt: LocalDateTime = LocalDateTime.now(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    var person: Person? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Address
        return id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Address(id=$id, street='$street', city='$city', postalCode='$postalCode', country='$country')"
    }
}
