package br.com.financer.users.service;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.financer.users.model.User;
import br.com.financer.users.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService implements UserDetailsService {
		
		private UserRepository repository;
		
		public UserService(UserRepository repository) {
			this.repository = repository;	
		}

		@Override
		public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			Optional<User> user = repository.findByUsername(username);
			if (user.isPresent()) {
				return user.get();
			}
			throw new UsernameNotFoundException("User " + username + " not found");
		}
		
		public User fetch(final Long id) {
			return repository.findById(id).orElseThrow(EntityNotFoundException::new);
 		}

		public Optional<User> fetch(final String username) {
			return repository.findByUsername(username);
		}

		public Page<User> list(Pageable page) {
			return repository.findAll(page);
		}
		@Transactional
		public User create(User user) {
			user.setCreated(LocalDateTime.now());
			user.setEnabled(true);
			return repository.saveAndFlush(user);
		}
		@Transactional
		public User update(User user, Long id) {

			if (repository.existsById(id)) {
				user.setId(id);
				return repository.saveAndFlush(user);
			} else {
				throw new EntityNotFoundException();
			}
			
		}
		@Transactional
		public void remove(Long id) {
			if (repository.existsById(id)) {
				repository.deleteById(id);
			} else {
				throw new EntityNotFoundException();
			}
		}

}
