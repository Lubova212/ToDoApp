package com.todoapp.controllers;

import com.todoapp.entity.Todo;
import com.todoapp.services.TodoNotFoundException;
import com.todoapp.services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class TodoController {
    @Autowired private TodoService todoService;

    @GetMapping("/alltodo")
    public String showTodoList(Model model){
        List <Todo> listTodo = todoService.listAll();
        model.addAttribute("listTodo", listTodo);

        return "alltodo";
    }

    @GetMapping("/todo/new")
    public String showInputForm(Model model){
        model.addAttribute("todo", new Todo());
        model.addAttribute("pageTitle", "Add new Todo");
        return "input_form";
    }

    @PostMapping("/todo/save")
    public String saveTodo(Todo todo, RedirectAttributes ra){
        todoService.save(todo);
        ra.addFlashAttribute("message", "Todo is saved successfully");
        return "redirect:/alltodo";
    }

    @GetMapping("/todo/update/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra){
        try {
            Todo todo = todoService.get(id);
            model.addAttribute("todo", todo);
            model.addAttribute("pageTitle", "Update Todo (ID: "+ id + ")");
            return "input_form";
        } catch (TodoNotFoundException e) {
            ra.addFlashAttribute("message", "Todo was not found");
            return "redirect:/alltodo";
        }
    }

    @GetMapping("/todo/delete/{id}")
    public String deleteTodo(@PathVariable("id") Integer id, RedirectAttributes ra)  {
        try {
            todoService.delete(id);
            ra.addFlashAttribute("message", "Todo is deleted successfully");
            return "redirect:/alltodo";
        } catch (TodoNotFoundException e) {
            ra.addFlashAttribute("message", "Todo is not found");
            return "redirect:/alltodo";
        }
    }

}
