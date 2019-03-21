package org.dhbw.mosbach.ai.pizzafactory.articleservice.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.google.common.collect.Lists;


@Entity
@Table(name = "articles")
@XmlRootElement
public class Article
{
	private long id;
	private String name;

	private int price;
	private Currency currency;

	private List<Ingredient> ingredients = Lists.newArrayList();

	private List<Review> reviews = Lists.newArrayList();

	public Article()
	{
		super();
	}

	public Article(String name, int price)
	{
		this.name = name;
		this.price = price;
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

	@XmlElement(name = "ingredient")
	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	public List<Ingredient> getIngredients()
	{
		return ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients)
	{
		this.ingredients = ingredients;
	}

	@XmlElement(name = "review")
	// Specify attribute in Review that holds the foreign key
	@OneToMany(mappedBy = "article", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<Review> getReviews()
	{
		return reviews;
	}

	public void setReviews(List<Review> reviews)
	{
		this.reviews = reviews;
	}

	@Override
	public String toString()
	{
		return String.format("Article [name='%s', price=%d, currency=%s]", name, Integer.valueOf(price), currency);
	}
}
