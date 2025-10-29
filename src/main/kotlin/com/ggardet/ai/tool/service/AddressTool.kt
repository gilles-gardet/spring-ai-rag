package com.ggardet.ai.tool.service

import com.ggardet.ai.tool.entity.Address
import com.ggardet.ai.tool.repository.AddressRepository
import org.springframework.ai.tool.annotation.Tool
import org.springframework.ai.tool.annotation.ToolParam
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class AddressTool(private val addressRepository: AddressRepository) {

    @Tool(description = "Find all addresses")
    fun findAllAddresses(): List<Address> = addressRepository.findAll()

    @Tool(description = "Find a person's address by the person last name")
    fun findPrimaryAddressByPersonName(
        @ToolParam(
            required = true,
            description = "The lastname of the person's address to find"
        ) lastname: String
    ): Address? = addressRepository.findByPerson_LastName(lastname)
}
