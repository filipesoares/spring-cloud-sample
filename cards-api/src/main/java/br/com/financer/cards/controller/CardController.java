package br.com.financer.cards.controller;

import static org.springframework.data.domain.Sort.Direction.ASC;

import javax.persistence.EntityNotFoundException;
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

import br.com.financer.cards.model.Card;
import br.com.financer.cards.service.CardService;

@RestController
@RequestMapping(value = "/cards", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CardController {
	
	private CardService service;
	
	public CardController(CardService service) {
		this.service = service;
	}
	
	@GetMapping
	public ResponseEntity<Page<Card>> list(@PageableDefault(sort = "id", direction = ASC) Pageable page) {
		return new ResponseEntity<Page<Card>>(service.list(page), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Card> fetch(@PathVariable("id") Long id){
		try {			
			return new ResponseEntity<Card>(service.fetch(id), HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", e);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "An error occured", e);
		}
	}

	@PostMapping
	@Transactional
	public ResponseEntity<Void> create(@RequestBody Card card, UriComponentsBuilder ucBuilder) {
		try {
			service.create(card);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(ucBuilder.path("/cards/{id}").buildAndExpand(card.getId()).toUri());
			return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "An error occured", e);
		}
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<Card> update(@PathVariable("id") Long id, @RequestBody Card card, HttpServletRequest request) {
		try {			
			return new ResponseEntity<Card>(service.update(id, card), HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", e);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "An error occured", e);
		}
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<Card> delete(@PathVariable("id") Long id) {
		service.delete(id);
		return new ResponseEntity<Card>(HttpStatus.NO_CONTENT);
	}

}
