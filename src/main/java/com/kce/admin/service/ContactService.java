package com.kce.admin.service;

import com.kce.admin.model.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactService {
    List<Contact> getAllContacts();
    Optional<Contact> getContactById(String id);
    Contact createContact(Contact contact);
    Contact updateContact(String id, Contact contact);
    void deleteContact(String id);
}