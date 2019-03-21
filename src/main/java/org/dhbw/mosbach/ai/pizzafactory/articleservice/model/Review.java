package org.dhbw.mosbach.ai.pizzafactory.articleservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "reviews")
public class Review
{
	private long id;
	private Article article;
	private String comment;
	private int grade;

	public Review()
	{
		super();
	}

	public Review(Article article, String comment, int grade)
	{
		this.article = article;
		this.comment = comment;
		this.grade = grade;
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

	// set to transient for marshalling!
	@XmlTransient
	@ManyToOne
	public Article getArticle()
	{
		return article;
	}

	public void setArticle(Article article)
	{
		this.article = article;
	}

	@Lob
	public String getComment()
	{
		return comment;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}

	public int getGrade()
	{
		return grade;
	}

	public void setGrade(int grade)
	{
		this.grade = grade;
	}
}
