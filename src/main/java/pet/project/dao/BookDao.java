package pet.project.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import pet.project.entity.Book;

import java.util.List;
import java.util.Optional;

@Component
public class BookDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> findAll(){
        return jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Optional<Book> find(int id){
        return jdbcTemplate.query("SELECT * FROM book WHERE id = ?", new BeanPropertyRowMapper<>(Book.class), id)
                .stream().findAny();
    }

    public void save(Book book){
        jdbcTemplate.update("INSERT INTO book (name, author, year, person) VALUES (?,?,?,?)",
                book.getName(), book.getAuthor(), book.getYear(), book.getPerson());
    }

    public void update(int id, Book book){
        jdbcTemplate.update("UPDATE book SET name = ?, author = ?, year = ?, person = ? WHERE id = ?",
                book.getName(), book.getAuthor(), book.getYear(), book.getPerson(), id);
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM book WHERE id = ?", id);
    }
}
