package com.pokemonarena.move;

import com.pokemonarena.Poke;
import com.pokemonarena.Pokemon;

public class Move implements Poke {
	private String name;
	private int pp;
	private int power;
	private int cat;

	public Move(String data){
		String[] info = data.split(",");
		name = info[0];
		pp = Integer.parseInt(info[1]);
		power = Integer.parseInt(info[3]);
		cat = Integer.parseInt(info[5]);
		
	}

	public boolean useMove(Pokemon attacking, Pokemon defending){
			if(cat!=8){
				int damage = calculateDamage(attacking, defending);
				defending.setStats(HP, defending.getStats(HP) - damage);
				if(defending.getStats(HP)<0){
					defending.setFainted();
					return true;
	
}
				return false;
	}
	return false;
}

	private int calculateDamage(Pokemon attacking, Pokemon defending){
		int damage = ((2 * attacking.getLevel()) / 5) + 2;
		damage *= power;
		if (cat == 6){
			damage *= (attacking.getStats(ATTACK) / defending.getStats(DEFENSE));
		}
		else if (cat == 7){
			damage *= (attacking.getStats(SATTACK) / defending.getStats(SDEFENSE));
		}
		damage /= 50;
		damage += 2;
		damage *= 2;
		return damage;
		
	}
	public String getName(){
		return name;
	}

	public int getPp() {
		return pp;
	}

	public int getPower() {
		return power;
	}
}
