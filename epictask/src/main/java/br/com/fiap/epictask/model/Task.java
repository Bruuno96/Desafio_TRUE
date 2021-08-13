package br.com.fiap.epictask.model;

import javax.persistence.*;

import lombok.*;

@Data
@Entity
@Table(name="TB_TASK")
public class Task {

	@Id
	@Column(name="id_task")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="nm_titutlo")
	private String title;
	
	@Column(name="ds_descricao")
	private String description;
	
	@Column(name="qt_pontos")
	private int points;
	
	@ManyToOne
	@JoinColumn(name = "id_user", nullable = false)
	private Usuario user;
	
	
}
