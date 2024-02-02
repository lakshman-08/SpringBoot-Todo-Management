package com.springboot.todomanagement.service.impl;

import com.springboot.todomanagement.dto.TodoDto;
import com.springboot.todomanagement.entity.Todo;
import com.springboot.todomanagement.exception.ResourceNotFoundException;
import com.springboot.todomanagement.repository.TodoRepository;
import com.springboot.todomanagement.service.TodoService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TodoServiceimpl implements TodoService {

    private TodoRepository todoRepository;
    private ModelMapper modelMapper;
    @Override
    public TodoDto addTodo(TodoDto todoDto) {

        //convert TodoDto into Todo JPA entity
        Todo todo=modelMapper.map(todoDto,Todo.class);

        //saving the data in database of Todo Jpa Entity
        Todo savedTodo=todoRepository.save(todo);

        //convert saved todo jpa entitty object into TodoDto object
        TodoDto savedTodoDto=modelMapper.map(savedTodo,TodoDto.class);

        return savedTodoDto;
    }

    @Override
    public TodoDto getTodo(Long id) {

        Todo todo = todoRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Todo not found with id:"+ id));
        return modelMapper.map(todo,TodoDto.class);
    }

    @Override
    public List<TodoDto> getAllTodos() {

        List<Todo> todos = todoRepository.findAll();

        return todos.stream().map((todo)-> modelMapper.map(todo,TodoDto.class))
                .collect(Collectors.toList());

    }

    @Override
    public TodoDto updateTodo(TodoDto todoDto, Long id) {

      Todo todo=  todoRepository.findById(id).
              orElseThrow(()-> new ResourceNotFoundException("Todo not found with id : "+id));
      todo.setTitle(todoDto.getTitle());
      todo.setDescription(todoDto.getDescription());
      todo.setCompleted(todoDto.isCompleted());

      Todo updatedTodo=todoRepository.save(todo);
      return modelMapper.map(updatedTodo,TodoDto.class);
    }

    @Override
    public void deleteTodo(Long id) {
       Todo todo =todoRepository.findById(id).
               orElseThrow(()-> new ResourceNotFoundException("Todo not found with id: " + id));

       todoRepository.deleteById(id);

    }

    @Override
    public TodoDto completeTodo(Long id) {

       Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo not found with id : " + id));

        todo.setCompleted(Boolean.TRUE);

        Todo updatedTodo=todoRepository.save(todo);
        return modelMapper.map(updatedTodo,TodoDto.class);
    }

    @Override
    public TodoDto inCompleteTodo(Long id) {

        Todo todo=todoRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Todo not found with id : "+ id));

        todo.setCompleted(Boolean.FALSE);

        Todo updatedTodo=todoRepository.save(todo);

        return modelMapper.map(updatedTodo,TodoDto.class);
    }
}
