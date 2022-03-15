package org.wit.boitcrp.models.managers

interface Manager {
    fun findAll(): List<Any>
    fun create(objectToCreate: Any)
    fun update(objectToUpdate: Any)
    fun delete(objectToDelete: Any)
}