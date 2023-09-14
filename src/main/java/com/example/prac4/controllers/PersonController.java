package com.example.prac4.controllers;

import com.example.prac4.models.Address;
import com.example.prac4.models.Person;
import com.example.prac4.repositories.AddressRepository;
import com.example.prac4.repositories.BookRepository;
import com.example.prac4.repositories.PersonRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("")
    public String getAllPersons(Model model) {
        model.addAttribute("persons", personRepository.findAll());
        return "persons/list";
    }

    @GetMapping("/create")
    public String showPersonForm(Model model) {
        model.addAttribute("person", new Person());
        model.addAttribute("addresses", addressRepository.findAll());
        model.addAttribute("books", bookRepository.findAll());
        return "persons/create";
    }
    @PostMapping("/create")
    public String createPerson(@Valid Person person) {
        Address address = addressRepository.findById(person.getAddress().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid address ID: " + person.getAddress().getId()));
        person.setAddress(address);
        personRepository.save(person);
        return "redirect:/persons";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid person ID: " + id));
        model.addAttribute("person", person);
        model.addAttribute("addresses", addressRepository.findAll());
        model.addAttribute("books", bookRepository.findAll());
        return "persons/edit";
    }

    @PostMapping("/{id}/edit")
    public String updatePerson(@PathVariable Long id, @Valid Person person) {
        person.setId(id);
        personRepository.save(person);
        return "redirect:/persons";
    }

    @GetMapping("/{id}/delete")
    public String deletePerson(@PathVariable Long id) {
        personRepository.deleteById(id);
        return "redirect:/persons";
    }
}
