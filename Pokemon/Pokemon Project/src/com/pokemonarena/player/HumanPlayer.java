package com.pokemonarena.player;

import com.pokemonarena.Poke;
import com.pokemonarena.Pokemon;

import java.util.*;

public class HumanPlayer implements Poke
{
	protected Pokemon[] party;
	private boolean whiteout;
	protected Pokemon activePok;
	protected Scanner sc;

	public HumanPlayer()
	{
		party = new Pokemon[5];
		for (int i = 0; i < 5; i++)
		{
			party[i] = new Pokemon();
		}
		whiteout = false;
		sc = new Scanner(System.in);
	}

	public void setRandomParty(ArrayList<Pokemon> pokedex)
	{
		for (int i = 0; i < 5; i++)
		{
			party[i] = pokedex.get(i);
		}
		
		Random r1 = new Random();
		int c1 = r1.nextInt(4);
		activePok = party[c1];
}

	public void displayPokemon()
	{
		for (int i = 0; i < 5; i++)
		{
			if (!party[i].isFainted()) System.out.println((i + 1) + ". " + party[i]);
		}
	}

	public Pokemon getActivePok()
	{
		return activePok;
	}

	public Pokemon[] getParty(){
		return party;
	}

	public void setActivePok(Pokemon newActive){
		activePok = newActive;
	}

	public void setWhiteOut()
	{
		int count = 0;
		for (int i = 0; i < 5; i++)
		{
			if (!party[i].isFainted()) count++;
		}

		if (count == 5) whiteout = true;
	}

	public boolean isOut()
	{
		return whiteout;
	}
}
