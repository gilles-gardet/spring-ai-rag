package com.ggardet.ai.tool.repository

import com.ggardet.ai.tool.entity.Address
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AddressRepository : JpaRepository<Address, Long> {
    fun findByPerson_LastName(lastName: String): Address?
}
