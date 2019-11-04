package br.com.financer.cards.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.financer.cards.model.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, Long>{

}
