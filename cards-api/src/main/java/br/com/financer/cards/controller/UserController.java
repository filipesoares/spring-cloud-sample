package br.com.financer.cards.controller;

import static org.springframework.data.domain.Sort.Direction.ASC;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.financer.cards.model.User;
import br.com.financer.cards.service.UserService;

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserController {
	
	private UserService service;
	
	public UserController(UserService service) {
		this.service = service;
	}
	
	@GetMapping
	public ResponseEntity<Page<User>> list(@PageableDefault(sort = "id", direction = ASC) Pageable page) {
		return new ResponseEntity<Page<User>>(service.list(page), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> fetch(@PathVariable("id") Long id){
		return new ResponseEntity<User>(service.fetch(id), HttpStatus.OK);
	}

	@PostMapping
	@Transactional
	public ResponseEntity<Void> create(@RequestBody User user, UriComponentsBuilder ucBuilder) {
		try {
			service.create(user);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(ucBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri());
			return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "An error occured", e);
		}
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<User> update(@PathVariable("id") Long id, @RequestBody User user, HttpServletRequest request) {
		return new ResponseEntity<User>(service.update(user, id), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<User> delete(@PathVariable("id") Long id) {
		service.remove(id);
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}

}
