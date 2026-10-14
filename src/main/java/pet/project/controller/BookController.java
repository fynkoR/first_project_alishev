package pet.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pet.project.dao.BookDao;
import pet.project.exception.NotFoundBookException;

@Controller
@RequestMapping("/books")
public class BookController {
    private BookDao bookDao;

    @Autowired
    public BookController(BookDao bookDao){
        this.bookDao = bookDao;
    }

    @GetMapping
    public String index(Model model){
        model.addAttribute("books", bookDao.findAll());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("book", bookDao.find(id).orElseThrow(() -> new NotFoundBookException(id)));
        return "books/show";
    }
}
