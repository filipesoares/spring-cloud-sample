package br.com.financer.cards.service;

import java.time.LocalDateTime;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import br.com.financer.cards.model.Card;
import br.com.financer.cards.repository.CardRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CardService {

	private CardRepository repository;
	private RestTemplate restTemplate;
	
	@Value("${users.service.uri}")
	private String userService;

	public CardService(CardRepository repository, RestTemplate restTemplate) {
		this.repository = repository;
		this.restTemplate = restTemplate;
	}
	
	public Page<Card> list(Pageable page) {
		return repository.findAll(page);
	}
	
	public Card fetch(final Long id) throws EntityNotFoundException {
		return repository.getOne(id);
	}
	
	@Transactional
	public Card create(Card card) throws EntityNotFoundException {
		card.setCreated(LocalDateTime.now());
		findUser(card.getUser());
		return repository.save(card);
	}
	
	@Transactional
	public Card update(Long id, Card card) throws EntityNotFoundException {
		
		if (repository.existsById(id)) {
			card.setId(id);
			return repository.save(card);
		} else 
			throw new EntityNotFoundException();
		
	}
	
	@Transactional
	public void delete(Long id) {
		repository.deleteById(id);
	}

	private void findUser(final Long userId) throws EntityNotFoundException {
		try {
			restTemplate.getForEntity(userService + "users/{id}", String.class, userId);
		} catch (HttpClientErrorException e) {
			log.info("User of id {0} not found!", userId);
			if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
				throw new EntityNotFoundException();
			}
		}
	}

}
