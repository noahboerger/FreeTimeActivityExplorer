package org.dhbw.mosbach.ai.pizzafactory.articleservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "ingredients")
public class Ingredient
{
	private long id;
	private String name;
	private float weight;

	private int price;
	private Currency currency;

	public Ingredient(String name, float weight, int price)
	{
		super();
		this.name = name;
		this.weight = weight;
		this.price = price;
	}

	public Ingredient()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue
	@XmlTransient
	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	@Column(nullable = false, length = 256, unique = true)
	@XmlAttribute(required = true)
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public float getWeight()
	{
		return weight;
	}

	public void setWeight(float weight)
	{
		this.weight = weight;
	}

	public int getPrice()
	{
		return price;
	}

	public void setPrice(int price)
	{
		this.price = price;
	}

	@Enumerated(EnumType.ORDINAL)
	public Currency getCurrency()
	{
		return currency;
	}

	public void setCurrency(Currency currency)
	{
		this.currency = currency;
	}
}
