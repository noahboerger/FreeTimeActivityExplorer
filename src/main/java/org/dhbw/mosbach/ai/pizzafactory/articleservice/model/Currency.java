package org.dhbw.mosbach.ai.pizzafactory.articleservice.model;

import javax.xml.bind.annotation.XmlEnum;

@XmlEnum
public enum Currency
{
	EUR("€"), Franken("CHF"), Pfund("GBP"), Dollar("$");

	private String symbol;

	private Currency(String symbol)
	{
		this.symbol = symbol;
	}

	public String getSymbol()
	{
		return symbol;
	}
}
