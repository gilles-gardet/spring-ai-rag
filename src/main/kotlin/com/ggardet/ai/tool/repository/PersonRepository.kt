package com.ggardet.ai.tool.repository

import com.ggardet.ai.tool.entity.Person
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface PersonRepository : JpaRepository<Person, Long> {
    fun findByEmail(email: String): Optional<Person>
    fun findByFirstNameAndLastName(firstName: String, lastName: String): List<Person>
}
