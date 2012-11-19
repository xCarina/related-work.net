package net.relatedwork.shared;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import net.relatedwork.shared.dto.Author;

import com.gwtplatform.dispatch.shared.Result;

public class DisplayAuthorResult implements Result {

	private ArrayList<Author> similarAuthors = new ArrayList<Author>();
	private ArrayList<Author> coAuthors = new ArrayList<Author>();
	private ArrayList<Author> citedAuthors = new ArrayList<Author>();
	private ArrayList<Author> citedByAuthors = new ArrayList<Author>();
	private String name;
	
	public DisplayAuthorResult() {
	}
	
	public void addSimilarAuthor(Author author){
		similarAuthors.add(author);
	}

	public void addCoAuthor(Author author){
		coAuthors.add(author);
	}
	public void addCitedAuthor(Author author){
		citedAuthors.add(author);
	}
	public void addCitedByAuthor(Author author){
		citedByAuthors.add(author);
	}

	public ArrayList<Author> getSimilarAuthors(int k) {
		return getSortedTopKAuthors(similarAuthors, k);
	}
	
	public ArrayList<Author> getCoAuthors(int k) {
		return getSortedTopKAuthors(coAuthors, k);
	}

	public ArrayList<Author> getCitedByAuthors(int k) {
		return getSortedTopKAuthors(citedByAuthors, k);
	}

	public ArrayList<Author> getCitedAuthors(int k) {
		return getSortedTopKAuthors(citedAuthors, k);
	}

	
	private ArrayList<Author> getSortedTopKAuthors(ArrayList<Author> list, int k){
		Collections.sort(list, new Comparator<Author>() {
			@Override
			public int compare(Author a1, Author a2) {
				return -a1.getScore().compareTo(a2.getScore());
			}
		});
		return new ArrayList<Author>(list.subList(0, Math.min(k, list.size())));		
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

}