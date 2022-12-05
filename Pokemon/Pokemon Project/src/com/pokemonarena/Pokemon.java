package com.pokemonarena;

import com.pokemonarena.move.Move;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class Pokemon implements Poke
{
    private String name;
    private int type;
    private boolean fainted;
    private int status;
    private int level;
    private int[] stats;
    private Image front;
    private Image back;
    private Move[] moves;
    private String[] oldMoves;
    private int maxHP;

    public Pokemon(){}

    public Pokemon(String[] data) throws FileNotFoundException {
        stats = new int[6];
        moves = new Move[4];
        //Pikachu,11,100,6 stuff after
        String[] info = data[0].split(",");
        name = info[0];
        type = Integer.parseInt(info[1]);
        status = 37;
        //level = Integer.parseInt(info[1]);
        level = 100;
        maxHP = Integer.parseInt(info[2]);
        stats[HP] = Integer.parseInt(info[2]);
        stats[ATTACK] = Integer.parseInt(info[3]);
        stats[DEFENSE] = Integer.parseInt(info[4]);
        stats[SATTACK] = Integer.parseInt(info[5]);
        stats[SDEFENSE] = Integer.parseInt(info[6]);
        fainted = false;

        moves = new Move[4];
        moves[0] = new Move(data[1]);
        moves[1] = new Move(data[2]);
        moves[2] = new Move(data[3]);
        moves[3] = new Move(data[4]);

        //loading sprites
        front = new Image("https://img.pokemondb.net/sprites/black-white/anim/normal/"+name+".gif", true);
        back = new Image("https://img.pokemondb.net/sprites/black-white/anim/back-normal/"+name+".gif", true);

        front = new Image(new FileInputStream("Pokemon Project/images/pokemon sprites/"+name.toLowerCase()+".gif"));
        back = new Image(new FileInputStream("Pokemon Project/images/pokemon sprites/"+name.toLowerCase()+" (1).gif"));
    }
	public void setStats(Pokemon pok)
	{
		this.name = pok.getName();
		this.type = pok.getType();
		this.fainted = pok.isFainted();
		this.status = pok.getStatus();
		this.level = pok.getLevel();
		for (int i = 0; i < 6; i++)
		{
			this.stats[i] = pok.getStats(i);
		}

		this.front = pok.getFront();
		this.back = pok.getBack();
		for (int i = 0; i < 4; i++)
		{
			this.moves[i] = pok.getMoves(i);
		}
	}

	public void displayMoves()
	{
		for (int i = 0; i < 4; i++)
		{
			System.out.println((i + 1) + ". " + moves[i]);
		}
	}
    public String getName()
    {
        return name;
    }

    public int getType()
    {
        return type;
    }

    public boolean isFainted()
    {
        return fainted;
    }

    public int getStatus()
    {
        return status;
    }

    public int getLevel()
    {
        return level;
    }

    public int getStats(int i)
    {
        return stats[i];
    }

    public Image getFront()
    {
        return front;
    }

    public Image getBack()
    {
        return back;
    }

    public Move getMoves(int i)
    {
        return moves[i];
    }

    public int getMaxHP(){
        return maxHP;
    }

    public void setFainted(){
        fainted = true;
    }

    public void setStats(int i, int value){
        stats[i] = value;
    }

    @Override
    public String toString() {
        return  "Type: " + type +
                "\nLevel: " + level +
                "\nstats: " + Arrays.toString(stats) +
                "\nmoves: " + Arrays.toString(moves);
    }
}

