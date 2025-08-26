package com.kce.admin.service;

import com.kce.admin.model.Contact;
import com.kce.admin.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    @Override
    public Optional<Contact> getContactById(String id) {
        return contactRepository.findById(id);
    }

    @Override
    public Contact createContact(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public Contact updateContact(String id, Contact contact) {
        Optional<Contact> existing = contactRepository.findById(id);
        if (existing.isPresent()) {
            Contact toUpdate = existing.get();
            toUpdate.setAddress(contact.getAddress());
            toUpdate.setPhone(contact.getPhone());
            toUpdate.setEmail(contact.getEmail());
            toUpdate.setBusinessHours(contact.getBusinessHours());
            return contactRepository.save(toUpdate);
        } else {
            throw new RuntimeException("Contact not found with ID: " + id);
        }
    }

    @Override
    public void deleteContact(String id) {
        contactRepository.deleteById(id);
    }
}
