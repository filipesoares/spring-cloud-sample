package br.com.financer.users.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="authorities")
@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true, value= {"hibernateLazyInitializer"})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Authority implements GrantedAuthority, Serializable {
	
	private static final long serialVersionUID = -1147842946233711963L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false, unique=true)
	private String role;
	
	@JsonIgnore
	public Long getId() {
		return id;
	}

	@JsonIgnore
	public String getRole() {
		return role;
	}

	@Override
	public String getAuthority() {
		return this.role;
	}	
	
}

