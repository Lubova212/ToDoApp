package com.todoapp;

import com.todoapp.entity.Todo;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.repository.CrudRepository;

public interface TodoRepository extends CrudRepository <Todo, Integer> {
    public Long countById(Integer id);
}
