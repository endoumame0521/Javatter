package org.javatter.javatter.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "tweets")
public class Tweet {
	@Id //PKにつける
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id;
	private String title;
	private String body;
}
