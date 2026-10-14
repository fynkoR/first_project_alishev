package pet.project.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import pet.project.entity.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> findAll(){
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Optional<Person> find(int id){
        return jdbcTemplate.query("SELECT * FROM person WHERE id = ?", new BeanPropertyRowMapper<>(Person.class), id)
                .stream().findAny();
    }
    public void save(Person person){
        jdbcTemplate.update("INSERT INTO person (name, year) VALUES (?,?)",
                person.getName(), person.getYear());
    }

    public void update(int id, Person person){
        jdbcTemplate.update("UPDATE person SET name = ?, year = ? WHERE id = ?",
                person.getName(), person.getYear(), id);
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM person WHERE id = ?", id);
    }

}
