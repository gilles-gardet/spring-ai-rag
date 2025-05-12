package com.ggardet.ai.tool.repository

import com.ggardet.ai.tool.entity.Person
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PersonRepository : JpaRepository<Person, Long> {
    fun findByEmail(email: String): Person?

    fun deleteByEmail(email: String)
}
