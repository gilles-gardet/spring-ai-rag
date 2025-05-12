package com.ggardet.ai.tool.service

import com.ggardet.ai.tool.entity.Person
import com.ggardet.ai.tool.repository.PersonRepository
import org.springframework.ai.tool.annotation.Tool
import org.springframework.ai.tool.annotation.ToolParam
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class PersonTool(private val personRepository: PersonRepository) {

    @Tool(description = "Find all the persons")
    fun findAllPersons(): List<Person> = personRepository.findAll()

    @Tool(description = "Find person by his/her email address")
    fun findByEmail(
        @ToolParam(
            required = true,
            description = "The email of the person to find"
        ) email: String
    ): Person? = personRepository.findByEmail(email)

    @Tool(description = "Create a new person")
    @Transactional
    fun save(
        @ToolParam(
            required = true,
            description = "The first name of the person to create"
        ) firstName: String,
        @ToolParam(
            required = true,
            description = "The last name of the person to create"
        )
        lastName: String,
        @ToolParam(
            required = true,
            description = "The birth date of the person to create"
        )
        birthDate: LocalDate,
        @ToolParam(
            required = true,
            description = "The email of the person to create"
        )
        email: String,
        @ToolParam(
            required = true,
            description = "The phone number of the person to create"
        )
        phone: String
    ): Person {
        val person = Person(
            firstName = firstName,
            lastName = lastName,
            email = email
        )
        return personRepository.save(person)
    }

    @Tool(description = "Delete a person by his/her email address")
    @Transactional
    fun deleteByEmail(
        @ToolParam(
            required = true,
            description = "The email of the person to delete"
        ) email: String
    ) = personRepository.deleteByEmail(email)
}
