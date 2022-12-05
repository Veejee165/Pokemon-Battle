package com.pokemonarena.graphics;

import com.pokemonarena.Poke;
import com.pokemonarena.Pokemon;
import com.pokemonarena.move.Move;
import com.pokemonarena.player.HumanPlayer;
import javafx.application.Application;
import javafx.geometry.Insets; 
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class PokemonArena extends Application implements Poke {

    private HumanPlayer p1, p2;
    int turn;  
    private ArrayList<Pokemon> pokedex;    
    private Button[] moveButtons = new Button[4];
    private Button[] switchButtons = new Button[6];

    private Image background;
    private ImageView imageViewActiveClose, imageViewActiveFar, imageViewBackground;

    private ProgressBar pbClose, pbFar;

    private Label closeNameLabel, farNameLabel, movesLabel, switchLabel;

    private Group battleGraphics;

    private TextArea battleInfo;

    private Separator sep;

    private HBox movesLayout, switchLayout;

    private VBox layout;

    @Override
    public void start(Stage window) throws Exception {
        loadPokemon();
        p1 = new HumanPlayer();
        p2 = new HumanPlayer();

        p1.setRandomParty(pokedex);
        p2.setRandomParty(pokedex);
        turn=1;

        window.setTitle("Pokemon Arena");
        loadGraphics();
        loadLayout();

        Scene scene = new Scene(layout, 800, 790);
        window.setScene(scene);
        window.show();
    }
        String musicf = "Pokemon Project/src/com/pokemonarena/graphics/abc.mp3";
        Media media = new Media(Paths.get(musicf).toUri().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
      
    public void musicvic(){
        String musicf = "Pokemon Project/src/com/pokemonarena/graphics/12 Victory! (Trainer Battle).mp3";
        Media media = new Media(Paths.get(musicf).toUri().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    public void loadGraphics() throws Exception {
        loadBattleGraphics();
        loadMoveButtons();
        updateMoveButtons();
        loadSwitchButtons();
        updateSwitchButtons();
        loadSeparator();
        mediaPlayer.play();
    }
    public void loadLayout() {
        layout = new VBox();
        layout.setAlignment(Pos.TOP_CENTER);
        layout.getChildren().add(battleGraphics);
        layout.getChildren().add(movesLabel);
        layout.getChildren().add(movesLayout);
        layout.getChildren().add(sep);
        layout.getChildren().add(switchLabel);
        layout.getChildren().add(switchLayout);
    }

    private void loadPokemon() {
        Scanner inputStream = null;
        pokedex = new ArrayList<>();
        try {
            inputStream = new Scanner(new FileInputStream("Pokemon Project/Pokemon-data.txt"));
            while (inputStream.hasNextLine()) {
                String[] data = new String[5];
                for (int i = 0; i < 5; i++) {
                    data[i] = inputStream.nextLine();
                }
                pokedex.add(new Pokemon(data));
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        inputStream.close();
    }

    public void loadBackground() throws FileNotFoundException {
        background = new Image(new FileInputStream("Pokemon Project/images/background/staduim.jpg"));
        imageViewBackground = new ImageView(background);
        battleGraphics.getChildren().add(imageViewBackground);
        imageViewBackground.setX(0);
        imageViewBackground.setY(0);

    }

    public void loadActivePokemon() throws Exception{
        imageViewActiveClose = new ImageView(p1.getActivePok().getBack());
        imageViewActiveFar = new ImageView(p2.getActivePok().getFront());

        battleGraphics.getChildren().add(imageViewActiveClose);
        battleGraphics.getChildren().add(imageViewActiveFar);

        closeNameLabel = new Label(p1.getActivePok().getName()+" lvl."+p1.getActivePok().getLevel());

        pbClose = new ProgressBar(p1.getActivePok().getStats(HP)/p1.getActivePok().getMaxHP());

        farNameLabel = new Label(p2.getActivePok().getName()+" lvl."+p2.getActivePok());

        pbFar = new ProgressBar(p2.getActivePok().getStats(HP)/p2.getActivePok().getMaxHP());
        
    }

    public void updateActivePokemon(){
        //-----------------close---------------------------------------
        HumanPlayer t;
        HumanPlayer t2;
        if(turn==1){
            t=p1;
            t2=p2;
        }
        else{
            t=p2;
            t2=p1;
        }
        imageViewActiveClose.setImage(t.getActivePok().getBack());
        imageViewActiveClose.setScaleX(2);
        imageViewActiveClose.setScaleY(2);
        
        //position
        imageViewActiveClose.setX(100);
        imageViewActiveClose.setY(300);
        
        //label
        closeNameLabel.setText(t.getActivePok().getName()+" lvl."+t.getActivePok().getLevel());
        closeNameLabel.setLayoutX(90);
        closeNameLabel.setLayoutY(196);
        //HPbar
        double prog = (double)t.getActivePok().getStats(HP)/t.getActivePok().getMaxHP();
        pbClose.setProgress(prog);
        pbClose.setMinWidth(180);
        pbClose.setLayoutX(80);
        pbClose.setLayoutY(220);

        //---------------------far-----------------------------------------
        imageViewActiveFar.setImage(t2.getActivePok().getFront());
        imageViewActiveFar.setScaleX(2);
        imageViewActiveFar.setScaleY(2);

        //position
        imageViewActiveFar.setX(550);
        imageViewActiveFar.setY(100);

        //label
        farNameLabel.setText(t2.getActivePok().getName()+" lvl."+t2.getActivePok().getLevel());
        farNameLabel.setLayoutX(550);
        farNameLabel.setLayoutY(16);
        //Hpbar
        double prog2 = (double)t2.getActivePok().getStats(HP)/t2.getActivePok().getMaxHP();
        pbFar.setProgress(prog2);
        pbFar.setLayoutX(530);
        pbFar.setLayoutY(40);
        pbFar.setMinWidth(180);
    }

    public void loadBattleText(){
        //textarea
        battleInfo = new TextArea();
        battleInfo.setEditable(false);
        battleInfo.appendText("Battle started");
        battleInfo.setMaxHeight(120);
        battleInfo.setLayoutY(360);
        battleInfo.setMinWidth(800);        
        battleGraphics.getChildren().add(battleInfo);
    }

    public void loadBattleGraphics() throws Exception{
        battleGraphics = new Group();

        loadBackground();
        loadActivePokemon();
        updateActivePokemon();
        loadBattleText();
        battleGraphics.getChildren().add(closeNameLabel);
        battleGraphics.getChildren().add(pbClose);
        battleGraphics.getChildren().add(farNameLabel);
        battleGraphics.getChildren().add(pbFar);
    }
    public void updateMoveButtons(){ 
        HumanPlayer t;
        if(turn==1){
            t=p1;
        }
        else{
            t=p2;
        }
        for(int i=0; i<4; i++){
            Move currentMove = t.getActivePok().getMoves(i);
            moveButtons[i].setText(currentMove.getName()+"\nPP: "+currentMove.getPp()+"/");
            String message = "\n"+t.getActivePok().getName()+" Used "+currentMove.getName()+"!";
            moveButtons[i].setOnAction(e -> attack(currentMove,message));
            Tooltip p = new Tooltip(""+currentMove);
            Tooltip.install(moveButtons[i], p);
        }
    }

    public void loadMoveButtons(){
        movesLabel = new Label("Moves");
        movesLabel.setMinSize(100, 50);
        movesLabel.setAlignment(Pos.CENTER);
        movesLabel.setLineSpacing(100);
        
        //Creating horizontal layout to hold moves
        movesLayout = new HBox();
        movesLayout.setMinWidth(800);
        movesLayout.setAlignment(Pos.CENTER);
        movesLayout.setSpacing(30);

        //intialize buttons
        for(int i=0; i<4; i++){
            moveButtons[i] = new Button();
            moveButtons[i].setMinHeight(50);
            moveButtons[i].setMinWidth(100);
            movesLayout.getChildren().add(moveButtons[i]);
        }
    }

    public void loadSeparator(){
        sep = new Separator();
        sep.setOrientation(Orientation.HORIZONTAL);
        sep.setPadding(new Insets(30, 30, 0, 30));
    }

    public void loadSwitchButtons(){
        HumanPlayer t;
        if(turn==1){
            t=p1;
        }
        else{
            t=p2;
        }
        //switch pokemon heading
        switchLabel = new Label("Pokemon");
        switchLabel.setMinSize(100, 50);
        switchLabel.setAlignment(Pos.CENTER);
        switchLabel.setLineSpacing(100);

        //creating horizontal layout to hold switch buttons
        switchLayout = new HBox();
        switchLayout.setMinWidth(800);
        switchLayout.setAlignment(Pos.CENTER);

        for(int i=0; i<t.getParty().length; i++){
            ImageView buttonImage = new ImageView(t.getParty()[i].getFront());
            buttonImage.setFitHeight(25);
            buttonImage.setFitWidth(25);
            switchButtons[i] = new Button(t.getParty()[i].getName(), buttonImage);
            switchButtons[i].setMinHeight(50);
            switchButtons[i].setMinWidth(100);
            Tooltip t1 = new Tooltip(""+t.getParty()[i]);
            Tooltip.install(switchButtons[i], t1);

            String message = "\nSwitched to "+t.getParty()[i].getName();
            Pokemon newPokemon = t.getParty()[i];
            switchButtons[i].setOnAction(e -> {
                if(t.getActivePok() == newPokemon){
                    battleInfo.appendText("\n"+newPokemon.getName()+" is already active!");
                }
                else{
                    switchPokemon(message, newPokemon);
                }
            });
            switchLayout.getChildren().add( switchButtons[i]);
        }
        //switchButtons[0].setDisable(true);
        switchLayout.setSpacing(20);
    }

    /**makes sure the the active Pokemons button has a blue border and makes sure all fainted pokemons buttons are disabled.*/
    public void updateSwitchButtons(){
        HumanPlayer t;
        if(turn==1){
            t=p1;
        }
        else{
            t=p2;
        }
        for(int i=0; i<t.getParty().length; i++){
            if(t.getParty()[i] == t.getActivePok()){
                switchButtons[i].setId("active-button");
            }
            else{
                switchButtons[i].setId("button");
                if(t.getParty()[i].isFainted()){
                    switchButtons[i].setDisable(true);
                }
                else{
                    switchButtons[i].setDisable(false);
                }
            }
        }
    }
    

    public void attack(Move m1, String message){
        HumanPlayer t;
        HumanPlayer t2;
        if(turn==1){
            t=p1;
            t2=p2;
        }
        else{
            t=p2;
            t2=p1;
        }
        Pokemon defending = t2.getActivePok();
        Pokemon attacking = t.getActivePok();
        battleInfo.appendText(message);
        // calculateDamage
        boolean flag = m1.useMove(attacking, defending);
        boolean wipe = true;
        if(turn==1){
            turn=2;
        }
        else{
            turn=1;
        }if(flag){
            String message3 ="\n"+defending.getName()+" has fainted";
            battleInfo.appendText(message3);
            for(int i=0; i<t.getParty().length; i++){
            if(!t.getParty()[i].isFainted() && t.getParty()[i]!= attacking){
            String message2 = "\nSwitched to "+t.getParty()[i].getName();
            Pokemon newPokemon = t.getParty()[i];
            switchPokemon(message2, newPokemon);
            wipe = false;
        break;
    }

}
        if(wipe){
            mediaPlayer.stop();;
            musicvic();
            String message4= "\nParty Fainted, The Attacking player wins ";
            battleInfo.appendText(message4);
            String message5= "\nExiting ";
            battleInfo.appendText(message5);
            new java.util.Timer().schedule( 
        new java.util.TimerTask() {
            @Override
            public void run() {
                System.exit(STATUS);
            }
        }, 
        6000 
);
            
        }
        }
        try {
            updateActivePokemon();
            updateSwitchButtons();
            updateMoveButtons();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    public void switchPokemon(String message, Pokemon newP){
        HumanPlayer t;
        if(turn==1){
            t=p1;
        }
        else{
            t=p2;
        }
        battleInfo.appendText(message);
         t.setActivePok(newP);

        try{
            updateActivePokemon();
            updateMoveButtons();
            updateSwitchButtons();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}