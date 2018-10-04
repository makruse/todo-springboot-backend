package com.example.todospringbootbackend.controller;

import com.example.todospringbootbackend.model.Todo;
import com.example.todospringbootbackend.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
//@SessionAttributes("name")
public class TodoController {

    @Autowired
    TodoService todoService;

    @InitBinder
    public void initBinder(WebDataBinder binder){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyy");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    //TodoController redirects to list-todos.jsp
    @RequestMapping(value="/list-todos", method=RequestMethod.GET)
    public String showListTodos(ModelMap model) {
        String name = getLoggedInUserName(model);
        model.put("todos", todoService.retrieveTodos(name));
        return "list-todos";
    }

    @RequestMapping(value="/add-todo", method=RequestMethod.GET)
    public String showAddTodoPage(ModelMap model){
        model.addAttribute("todo", new Todo(0, getLoggedInUserName(model), "", new Date(), false));
        return "todo";
    }

    //will make a new todoitem and redirect to todolist
    @RequestMapping(value = "/add-todo", method = RequestMethod.POST)
    public String addTodo(ModelMap model, @Valid Todo todo, BindingResult result) {
        if(result.hasErrors()){
            return "todo";
        }
        todoService.addTodo(getLoggedInUserName(model), todo.getDesc(), todo.getTargetDate(),
                false);
        return "redirect:/list-todos";
    }

    @RequestMapping(value="/delete-todo", method=RequestMethod.GET)
    public String deleteTodo(@RequestParam int id){
        todoService.deleteTodo(id);
        return "redirect:/list-todos";
    }

    @RequestMapping(value="/update-todo", method=RequestMethod.GET)
    public String updateTodo(ModelMap model, @RequestParam int id){
        Todo todo = todoService.retrieveTodo(id);
        model.put("todo", todo);
        return "todo";
    }

    @RequestMapping(value = "/update-todo", method = RequestMethod.POST)
    public String updateTodo(ModelMap model, @Valid Todo todo, BindingResult result) {
        if (result.hasErrors()) {
            return "todo";
        }
        todo.setUser(getLoggedInUserName(model));
        todoService.updateTodo(todo);
        return "redirect:/list-todos";
    }

    private String getLoggedInUserName(ModelMap model) {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        //userDetails is a Spring Security class to store user details
        if (principal instanceof UserDetails)
            return ((UserDetails) principal).getUsername();

        return principal.toString();
    }

//    private String getLoggedInUserName(ModelMap model) {
//        return (String) model.get("name");
//    }

}
