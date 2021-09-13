package ru.finex.core.templates;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @project finex-server
 * @author finfan: 13.09.2021
 */
@Entity
@Table(name = "game_object_templates")
@Data
public class GameObjectTemplate implements ru.finex.core.model.entity.Entity {
	@Id
	@Column(name = "id")
	@SequenceGenerator(name = "game_object_templates_id_seq", sequenceName = "game_object_templates_id_seq", allocationSize = 1)
	@GeneratedValue(generator = "game_object_templates_id_seq", strategy = GenerationType.SEQUENCE)
	private int persistenceId;
	@Column(name = "name", unique = true, nullable = false)
	private String name;
}
