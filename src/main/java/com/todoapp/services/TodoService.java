package com.todoapp.services;

import com.todoapp.TodoRepository;
import com.todoapp.entity.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {
    @Autowired private TodoRepository repo;

    public List<Todo> listAll(){
        return (List<Todo>) repo.findAll();
    }

    public void save(Todo todo) {
        repo.save(todo);
    }

    public Todo get(Integer id) throws TodoNotFoundException {
        Optional <Todo> result = repo.findById(id);

        if (result.isPresent()){
            return result.get();
        }
        throw new TodoNotFoundException("Could not find any Todo with ID " + id);
    }

    public void delete(Integer id) throws TodoNotFoundException {
       Long count = repo.countById(id);
        if (count == null|| count == 0){
            throw new TodoNotFoundException("Could not find any Todo with ID " + id);
        }
        repo.deleteById(id);
    }


}
