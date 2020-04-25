package com.project.list.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.list.exception.ResourceNotFoundException;
import com.project.list.model.Users;
import com.project.list.repository.UsersRepository;

@RestController
@RequestMapping("/api/v1/")
public class UsersController {

	@Autowired
	private UsersRepository userRepository;
	
	@GetMapping("users")
	public List<Users> getAllUsers(){
		return this.userRepository.findAll();
	}
	
	@GetMapping("/users/{id}")
	public ResponseEntity<Users> getUserbyId(@PathVariable(value = "id") Long userId)
		throws ResourceNotFoundException {
		Users user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));
		return ResponseEntity.ok().body(user);
	}
	
	@PostMapping("users")
	public Users createUser(@RequestBody Users user) {
		return this.userRepository.save(user);
	}
	
	@PutMapping("users/{id}")
	public ResponseEntity<Users> updateUser(@PathVariable(value = "id") Long userId,
			@Valid @RequestBody Users userDetails) throws ResourceNotFoundException{
		
		Users user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));
		
		user.setActive(userDetails.getActive());
		user.setUser_name(userDetails.getUser_name());
		user.setRoles(userDetails.getRoles());
		user.setPassword(userDetails.getPassword());
		
		return ResponseEntity.ok(this.userRepository.save(user));
	}
	
	@DeleteMapping("users/{id}")
	public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId) throws ResourceNotFoundException{
		Users user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));
		
		this.userRepository.delete(user);
		
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		
		return response;
		
	}
}
