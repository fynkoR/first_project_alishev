package pet.project.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pet.project.dao.PersonDao;
import pet.project.entity.Person;
import pet.project.exception.NotFoundPersonException;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private PersonDao personDao;

    @Autowired
    public PeopleController(PersonDao personDao){
        this.personDao = personDao;
    }

    @GetMapping
    public String index(Model model){
        model.addAttribute("people", personDao.findAll());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("person", personDao.find(id).orElseThrow(() -> new NotFoundPersonException(id)));
        return "people/show";
    }
    @GetMapping("/new")
    public String newPerson(Model model){
        model.addAttribute("person", new Person());
        return "people/new";
    }

    @PostMapping
    public String create(@ModelAttribute("person") @Valid Person person,
                       BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "people/new";
        }
        personDao.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("person", personDao.find(id).orElseThrow(() -> new NotFoundPersonException(id)));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult, @PathVariable("id") int id){
        if(bindingResult.hasErrors()){
            return "people/edit";
        }
        personDao.update(id, person);
        return "redirect:/people";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        personDao.delete(id);
        return "redirect:/people";
    }
}

