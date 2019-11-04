package br.com.financer.cards.service;

import java.time.LocalDateTime;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.financer.cards.model.Card;
import br.com.financer.cards.repository.CardRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CardService {

	private CardRepository repository;

	public CardService(CardRepository repository) {
		this.repository = repository;
	}
	
	public Page<Card> list(Pageable page) {
		return repository.findAll(page);
	}
	
	public Card fetch(final Long id) throws EntityNotFoundException {
		return repository.getOne(id);
	}
	
	@Transactional
	public Card create(Card card) {
		card.setCreated(LocalDateTime.now());
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

}
