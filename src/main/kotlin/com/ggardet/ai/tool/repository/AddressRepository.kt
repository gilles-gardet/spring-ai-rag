package com.ggardet.ai.tool.repository

import com.ggardet.ai.tool.entity.Address
import com.ggardet.ai.tool.entity.Person
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AddressRepository : JpaRepository<Address, Long> {
    fun findByPerson(person: Person): List<Address>
    fun findByPersonAndIsPrimaryTrue(person: Person): Address?
    fun findByPersonIdAndIsPrimaryTrue(personId: Long): Address?
}
