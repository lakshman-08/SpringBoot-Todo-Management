package com.springboot.todomanagement.controller;


import com.springboot.todomanagement.dto.TodoDto;
import com.springboot.todomanagement.service.TodoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/todos")
@AllArgsConstructor
public class TodoController {

    private TodoService todoService;


    //build add todo rest api
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TodoDto> addTodo(@RequestBody TodoDto todoDto)
    {
        TodoDto savedTodo=todoService.addTodo(todoDto);

        return new ResponseEntity<>(savedTodo, HttpStatus.CREATED);
    }

    //build get Todo REST API
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("{id}")
    public ResponseEntity<TodoDto> getTodo(@PathVariable("id") Long todoId)
    {
        TodoDto todoDto=todoService.getTodo(todoId);
        return new ResponseEntity<>(todoDto,HttpStatus.OK);
    }

    //Build Get All Todos REST API
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public ResponseEntity<List<TodoDto>> getAllTodos()
    {
        List<TodoDto> todos =todoService.getAllTodos();
        return ResponseEntity.ok(todos);
    }

    //build update Todo REST API
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<TodoDto> updateTodo(@RequestBody TodoDto todoDto,@PathVariable("id") Long todoId)
    {
     TodoDto updatedTodo = todoService.updateTodo(todoDto,todoId);
     return ResponseEntity.ok(updatedTodo);
    }

    //build delete todo rest api
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable("id") Long todoId)
    {
      todoService.deleteTodo(todoId);
      return ResponseEntity.ok("Todo deleted successfully!.");
    }

    //build complete todo rest api
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PatchMapping("id/complete")
    public ResponseEntity<TodoDto> completeTodo(@PathVariable("id") Long todoId)
    {
        TodoDto updatedTodo=todoService.completeTodo(todoId);
        return ResponseEntity.ok(updatedTodo);
    }

    //build in complete todo rest api
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PatchMapping("{id}/in-complete")
    public ResponseEntity<TodoDto> inCompleteTodo(Long todoId)
    {
        TodoDto updatedTodo=todoService.inCompleteTodo(todoId);
        return ResponseEntity.ok(updatedTodo);
    }

}
