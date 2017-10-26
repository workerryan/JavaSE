package com.dragon.javase8.optional.model;

import java.util.Optional;

public class NewMan {
	private Optional<Godness> godness = Optional.empty();  //将可能为空的放在Optional中

	public NewMan() {
		
	}
	public NewMan(Optional<Godness> godness) {
		super();
		this.godness = godness;
	}
	public Optional<Godness> getGodness() {
		return godness;
	}
	public void setGodness(Optional<Godness> godness) {
		this.godness = godness;
	}
	
	@Override
	public String toString() {
		return "NewMan [godness=" + godness + "]";
	}
	
}
