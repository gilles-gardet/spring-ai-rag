package com.ggardet.ai.tool.service

import com.ggardet.ai.tool.entity.Person
import com.ggardet.ai.tool.repository.PersonRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Optional

@Service
class PersonService(private val personRepository: PersonRepository) {

    fun findAll(): List<Person> = personRepository.findAll()

    fun findById(id: Long): Optional<Person> = personRepository.findById(id)

    fun findByEmail(email: String): Optional<Person> = personRepository.findByEmail(email)

    fun findByFirstNameAndLastName(firstName: String, lastName: String): List<Person> =
        personRepository.findByFirstNameAndLastName(firstName, lastName)

    @Transactional
    fun save(person: Person): Person = personRepository.save(person)

    @Transactional
    fun deleteById(id: Long) {
        personRepository.deleteById(id)
    }

    @Transactional
    fun delete(person: Person) {
        personRepository.delete(person)
    }
}
