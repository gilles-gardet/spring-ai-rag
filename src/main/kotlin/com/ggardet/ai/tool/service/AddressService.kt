package com.ggardet.ai.tool.service

import com.ggardet.ai.tool.entity.Address
import com.ggardet.ai.tool.entity.Person
import com.ggardet.ai.tool.repository.AddressRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Optional

@Service
class AddressService(private val addressRepository: AddressRepository) {

    fun findAll(): List<Address> = addressRepository.findAll()

    fun findById(id: Long): Optional<Address> = addressRepository.findById(id)

    fun findByPerson(person: Person): List<Address> = addressRepository.findByPerson(person)

    fun findPrimaryAddress(person: Person): Address? = addressRepository.findByPersonAndIsPrimaryTrue(person)

    fun findPrimaryAddressByPersonId(personId: Long): Address? = addressRepository.findByPersonIdAndIsPrimaryTrue(personId)

    @Transactional
    fun save(address: Address): Address = addressRepository.save(address)

    @Transactional
    fun deleteById(id: Long) {
        addressRepository.deleteById(id)
    }

    @Transactional
    fun delete(address: Address) {
        addressRepository.delete(address)
    }
}
