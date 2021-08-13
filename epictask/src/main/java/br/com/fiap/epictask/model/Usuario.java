package br.com.fiap.epictask.model;

import java.util.List;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name="TB_USUARIO")
public class Usuario {
	
	@Id
	@Column(name="id_user")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="nm_nome")
	private String name;
	
	@Column(name="ds_email")
	private String email;
	
	@Column(name="ds_password")
	private String password;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Task> tasks;

}
