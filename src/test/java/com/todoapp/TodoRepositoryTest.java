package com.todoapp;

import com.todoapp.entity.Todo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class TodoRepositoryTest {
    @Autowired private TodoRepository repo;

    @Test
    public void testAddNew(){
        Todo todo = new Todo();
        todo.setDescription("Go for a shopping for presents for mother BD");
        todo.setStatus("In progress");
        todo.setDueDate("2023-06-23");
        todo.setPriority("High");

        Todo savedTodo = repo.save(todo);

        Assertions.assertThat(savedTodo).isNotNull();
        Assertions.assertThat(savedTodo.getId()).isGreaterThan(0);

    }

    @Test
    public void testListAll(){
        Iterable<Todo> todos = repo.findAll();
        Assertions.assertThat(todos).hasSizeGreaterThan(0);

        for (Todo todo : todos){
            System.out.println(todo);
        }
    }

    @Test
    public void testUpdate(){
        Integer todoId = 1;
        Optional <Todo> optionalTodo = repo.findById(todoId);

        Todo todo = optionalTodo.get();
        todo.setPriority("Low");
        repo.save(todo);

        Todo updatedTodo = repo.findById(todoId).get();
        Assertions.assertThat(updatedTodo.getPriority()).isEqualTo("Low");
    }

    @Test
    public void testGet(){
        Integer todoId = 1;
        Optional <Todo> optionalTodo = repo.findById(todoId);

        Assertions.assertThat(optionalTodo).isPresent();
        System.out.println(optionalTodo.get());
    }

    @Test
    public void testDelete(){
        Integer todoId = 1;
        repo.deleteById(todoId);

        Optional <Todo> optionalTodo = repo.findById(todoId);
        Assertions.assertThat(optionalTodo).isNotPresent();
    }

}
