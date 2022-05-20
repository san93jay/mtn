package com.sanjay.mtn.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="line_item")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LineItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="quantity")
	private int quantity;
	
	@Column(name="description")
	private String description;
	
	@Column(name="unit_price")
	private float unitPrice;

}
