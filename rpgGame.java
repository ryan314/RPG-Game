/* By: Ryan Chiu
 * RPG Game
 * Date Created: February 17, 2020
 * Version 1.0
 */

import arc.*;
import java.awt.*;
import java.awt.image.*;

public class rpgGame {
	public static void main (String[] args) {
		Console con = new Console(800,500); // setting the console side to 800 px by 500 px 
		
		// ---- VARIABLE DECLARATIONS ---- 
	
		// Map of the world CSV & array 
		String strFile = "mapOfWorld.csv";
		String strGameMap[][] = new String[20][20]; // creates an array of 20 rows by 20 columns 
	
		// Loading main menu content 
		BufferedImage mainMenu = con.loadImage("mainMenu.png");
		BufferedImage helpPage = con.loadImage("helpPage.png");
		
		// Loading hero sprite  
		BufferedImage hero = con.loadImage("hero.png"); 
		
		// Loading enemy sprites 
		BufferedImage minion = con.loadImage("minion.png");
		BufferedImage bat = con.loadImage("bat.png");
		BufferedImage guard = con.loadImage("guard.png");
		BufferedImage boss = con.loadImage("boss.png");		
		
		// Loading tiles for the world map 
		BufferedImage building = con.loadImage("building.png");
		BufferedImage grass = con.loadImage("grass.png");
		BufferedImage chest = con.loadImage("hiddenChest.png");
		BufferedImage tree = con.loadImage("tree.png");
		BufferedImage water = con.loadImage("water.png");
		
		// Loading item sprites 
		BufferedImage swordItem1 = con.loadImage("swordItem1.png"); 
		BufferedImage armorItem1 = con.loadImage("armorItem1.png"); 
		
		// Loading minion battle images 
		BufferedImage minionBattle0 = con.loadImage("minionBattle0.png"); 	
		BufferedImage minionBattle1 = con.loadImage("minionBattle1.png");
		BufferedImage minionBattle2 = con.loadImage("minionBattle2.png");
		BufferedImage minionBattle3 = con.loadImage("minionBattle3.png");
		BufferedImage minionBattle4 = con.loadImage("minionBattle4.png"); 
		
		// Loading bat battle images  
		BufferedImage batBattle0 = con.loadImage("batBattle0.png"); 	
		BufferedImage batBattle1 = con.loadImage("batBattle1.png"); 	
		BufferedImage batBattle2 = con.loadImage("batBattle2.png"); 	
		BufferedImage batBattle3 = con.loadImage("batBattle3.png"); 	
		BufferedImage batBattle4 = con.loadImage("batBattle4.png"); 	
		
		// Loading guard battle images  
		BufferedImage guardBattle0 = con.loadImage("guardBattle0.png"); 	
		BufferedImage guardBattle1 = con.loadImage("guardBattle1.png"); 	
		BufferedImage guardBattle2 = con.loadImage("guardBattle2.png"); 	
		BufferedImage guardBattle3 = con.loadImage("guardBattle3.png"); 	
		BufferedImage guardBattle4 = con.loadImage("guardBattle4.png"); 
		
		// Loading boss battle images
		BufferedImage bossBattle0 = con.loadImage("bossBattle0.png"); 	
		BufferedImage bossBattle1 = con.loadImage("bossBattle1.png"); 	
		BufferedImage bossBattle2 = con.loadImage("bossBattle2.png"); 	
		BufferedImage bossBattle3 = con.loadImage("bossBattle3.png"); 	
		BufferedImage bossBattle4 = con.loadImage("bossBattle4.png"); 
		
		// Loading victory screens  
		BufferedImage victory = con.loadImage("victory.png"); 
		BufferedImage youWin = con.loadImage("youWin.png"); 		
		
		// Loading black screen and cyan circle png's 
		BufferedImage blackScreen = con.loadImage("blackScreen.png"); 
		BufferedImage cyanCirc = con.loadImage("cyanCirc.png");
	
		// Declaring boolean variables  
		Boolean blnMainMenu = true; 
		Boolean blnAlive = true; 
		Boolean blnMap; 
		Boolean blnRekey;
		
		// Declaring hero position 
		int intHeroX = 25;
		int intHeroY = 25; 

		// Declaring hero stats variables  
		int intHP; 
		int intATK; 
		int intDEF; 
		
		// Declaring enemy stats 
		int intHPe = 0;
		int intATKe = 0;
		int intDEFe = 0;
		
		// Declaring key and mouse variables 
		int intMouseButton = 0;
		int intMouseX = 0;
		int intMouseY = 0;
		char chrKey; 
		
		// Declaring counters for item holders
		int intCountATK = 0 ; // counter for sword items
		int intCountDEF = 0; // counter for armor items 
		
		while (blnMainMenu) { // allows for the main menu to be repainted 
			
			// Restting the hero stats for when user restarts the game  
			intHP = 50; 
			intATK = 10;
			intDEF = 5;  
			
			// Resetting enemy stats for when user restarts the game
			intHPe = 0;
			intATKe = 0;
			intDEFe = 0;
			
			// Resetting count values for sword and armor items picked up when user restarts the game 
			intCountATK = 0; 
			intCountDEF = 0; 
			
			// Drawing the main menu 
			con.drawImage(mainMenu, 0, 0);
			con.repaint();
			
			// Exits out of this while loop when intMouseButton is equal to 1 (so when the user has cliked within range of a button) 
			intMouseButton = 0; 
			while (intMouseButton == 0) { // checks to see if intMouseButton is equal to 0 
				intMouseButton = con.currentMouseButton(); // allows for mouse input 
				intMouseX = con.currentMouseX(); // retrieves the x coordinate of the click  
				intMouseY = con.currentMouseY(); // retrieves the y coordinate of the click  
			} // close intMouseButton while loop 
			
			if (intMouseX >= 53 && intMouseX <= 180 && intMouseY >= 52 && intMouseY <= 90 && intMouseButton == 1) { // checks to see if user has clicked within range of the "play" button 				
				blnMainMenu  = false; // prevents main menu from being repainted 
				blnMap = true; // enables for the map to be loaded into an array and painted 
				
				while (blnMap) { // allows for map to be reloaded and repainted    
					
					// Resetting hero's position 
					intHeroX = 25;
					intHeroY = 25;
					
					rpgMethods.fade(con); // activate fade transition / animation 
					strGameMap = rpgMethods.loadMap(strFile); // loads csv file into an array and returns the array 
										
					// Drawing the entire map   
					for (int intRow = 0; intRow < 20; intRow++) {
						for (int intCol = 0; intCol < 20; intCol++) {
							if (strGameMap[intRow][intCol].equals("b")) { // checking to see if element is equal to building 
								con.drawImage(building, intCol*25, intRow*25); 
							} else if (strGameMap[intRow][intCol].equals("e1")) { // checking to see if element is equal to enemy level 1, the minion
								con.drawImage(minion, intCol*25, intRow*25);
							} else if (strGameMap[intRow][intCol].equals("e2")) { // checking to see if element is equal to enemy level 2, the bat
								con.drawImage(bat, intCol*25, intRow*25);
							} else if (strGameMap[intRow][intCol].equals("e3")) { // checking to see if element is equal to enemy level 3, the guard
								con.drawImage(guard, intCol*25, intRow*25);
							} else if (strGameMap[intRow][intCol].equals("e4")) { // chekcing to see if element is equal to enemy level 4, the boss 
								con.drawImage(boss, intCol*25, intRow*25);
							} else if (strGameMap[intRow][intCol].equals("g")) { // checking to see if element is equal to grass
								con.drawImage(grass, intCol*25, intRow*25);
							} else if (strGameMap[intRow][intCol].equals("t")) { // checking to see if element is equal to tree
								con.drawImage(tree, intCol*25, intRow*25);
							} else if (strGameMap[intRow][intCol].equals("w")) { // checking to see if element is equal to water 
								con.drawImage(water, intCol*25, intRow*25);
							} else if (strGameMap[intRow][intCol].equals("cATK") || strGameMap[intRow][intCol].equals("cDEF")) { // checking to see if element is equal to attack or defense chest 
								con.drawImage(chest, intCol*25, intRow*25);
							} 
						} // close intCol for loop
					} // close intRow for loop 
					
					// Drawing the hero sprite 
					con.drawImage(hero , intHeroX, intHeroY);  
					con.repaint(); // paints the map and hero onto the console window 
					
					rpgMethods.statUpdate(con, intHP, intATK, intDEF, intHPe, intATKe, intDEFe, intCountATK, intCountDEF); // draws and paints the intiial stats to the console window 
					
					blnRekey = true; 
					while (blnRekey) { // allows for repeated key input from user 
						chrKey = con.getChar(); // obtains the character pressed by user without user pressing the "enter" key 
						
						if (chrKey == 'w') { // checks to see if user has pressed 'w' key 
							
							if (strGameMap[intHeroY/25 - 1][intHeroX/25].equals("b")) { // checks to see if the incoming tile is a building 
								intHP += 10; // if so, the hero gains 10 health for using the building  
								rpgMethods.statUpdate(con, intHP, intATK, intDEF, 0, 0, 0, intCountATK, intCountDEF);  // updates the stats of the hero, enemy, and item count  
								
								// Making the building so that the hero can only use it once
								strGameMap[intHeroY/25 - 1][intHeroX/25] = "g"; // sets the element equal to g
								con.drawImage(grass, intHeroY - 25, intHeroX); // paints a grass tile in the position of the building used 
							} else if (strGameMap[intHeroY/25 - 1][intHeroX/25].equals("e1") || strGameMap[intHeroY/25 - 1][intHeroX/25].equals("e2") || strGameMap[intHeroY/25 - 1][intHeroX/25].equals("e3") || strGameMap[intHeroY/25 - 1][intHeroX/25].equals("e4")) { // checks to see if the incoming tile is an enemy 								
								
								if (strGameMap[intHeroY/25 - 1][intHeroX/25].equals("e1")) {
									
									// Setting the stats of the enemy minion  
									intHPe = 15;
									intATKe = 5;
									intDEFe = 2;
									
									rpgMethods.statUpdate(con, intHP, intATK, intDEF, intHPe, intATKe, intDEFe, intCountATK, intCountDEF); // updating and painting the stats to console window 
									intHP = rpgMethods.battle(con, minionBattle0, minionBattle1, minionBattle2, minionBattle3, minionBattle4, intHP, intATK, intDEF, intHPe, intATKe, intDEFe, intCountATK, intCountDEF); /* enables the battle to take place on the console window 
																																																						   * method return is set equal to intHP so that program can decide what to do based on that info
																																																						   */
								} else if (strGameMap[intHeroY/25 - 1][intHeroX/25].equals("e2")) {
									
									// Setting the stats of the enemy bat 
									intHPe = 20;
									intATKe = 10; 
									intDEFe = 4;
									
									rpgMethods.statUpdate(con, intHP, intATK, intDEF, intHPe, intATKe, intDEFe, intCountATK, intCountDEF); 
									intHP = rpgMethods.battle(con, batBattle0, batBattle1, batBattle2, batBattle3, batBattle4, intHP, intATK, intDEF, intHPe, intATKe, intDEFe, intCountATK, intCountDEF); 
								
								} else if (strGameMap[intHeroY/25 - 1][intHeroX/25].equals("e3")) {
									
									// Setting the stats of the enemy guard 
									intHPe = 25;
									intATKe = 15;
									intDEFe = 6;
									
									rpgMethods.statUpdate(con, intHP, intATK, intDEF, intHPe, intATKe, intDEFe, intCountATK, intCountDEF); 
									intHP = rpgMethods.battle(con, guardBattle0, guardBattle1, guardBattle2, guardBattle3, guardBattle4, intHP, intATK, intDEF, intHPe, intATKe, intDEFe, intCountATK, intCountDEF); 
								
								} else if (strGameMap[intHeroY/25 - 1][intHeroX/25].equals("e4")) {
									
									// Setting the stats of the enemy boss 
									intHPe = 100;
									intATKe = 45;
									intDEFe = 20; 
									
									rpgMethods.statUpdate(con, intHP, intATK, intDEF, intHPe, intATKe, intDEFe, intCountATK, intCountDEF); 
									intHP = rpgMethods.battle(con, bossBattle0, bossBattle1, bossBattle2, bossBattle3, bossBattle4, intHP, intATK, intDEF, intHPe, intATKe, intDEFe, intCountATK, intCountDEF); 									
								} 
								
								// Checking to see if any of the conditions below are met before displaying victory screen 
								if (intHP <= 0) { // checks to see if hero's health is equal to or below 0  
									blnRekey = false; // disallows user key input 
									blnMap = false; // disallows map to be reloaded and repainted  
									blnAlive = false; // disallows for the map to be repainted after user key input 
									blnMainMenu = rpgMethods.lose(con, blnMainMenu); // enables "game over" screen 
								} else if (intHP > 0 && strGameMap[intHeroY/25 - 1][intHeroX/25].equals("e4")) { // checking to see if hero won the battle against the boss enemy 
									rpgMethods.youWin(con, youWin); // calls method to display the "You Win" screen 
								} else {
									blnAlive = true; // sets blnAlive to true meaning that the hero has not battled the boss nor have they died 
								} 
								
								con.drawImage(victory, 0, 0); // displaying victory screen when they destroy an enemy 
								con.sleep(500); // delays the fade effect									
								rpgMethods.fade(con); // prompts the fade effect 
								
								// Resets enemy's stats after battle 
								rpgMethods.statUpdate(con, intHP, intATK, intDEF, 0, 0, 0, intCountATK, intCountDEF); 
								
								// Set the enemy equal to a grass when the hero defeats them and draws a grass tile in their place 
								strGameMap[intHeroY/25 - 1][intHeroX/25] = "g"; 
								con.drawImage(grass, intHeroY - 25, intHeroX); 
														
							} else if (strGameMap[intHeroY/25 - 1][intHeroX/25].equals("g")) { // checks to see if the incoming tile is grass
								con.drawImage(grass, intHeroX, intHeroY); 
								con.sleep(20); // slows down the movement of the hero 
								con.drawImage(hero, intHeroX, intHeroY -= 25); // the hero moves 25px upwards if they press 'w'
								con.repaint();
							
							} else if (strGameMap[intHeroY/25 - 1][intHeroX/25].equals("t")) { // checks to see if the incoming tile is a tree 
								
							} else if (strGameMap[intHeroY/25 - 1][intHeroX/25].equals("w")) { // checks to see if the incoming tile is water 
								intHP = 0; // if so, set hero's HP equal to 0 because they ran into water 
								
								if (intHP <= 0) { // checks to see if hero died 
									blnRekey = false; // prevents user from inputting more key data 
									blnMap = false; // prevents map from being reloaded and painted 
									blnMainMenu = rpgMethods.lose(con, blnMainMenu); // calling lose method since they walked into water 
								}
								
							} else if (strGameMap[intHeroY/25 - 1][intHeroX/25].equals("cATK")) { // checking to see if the incoming tile is a hidden ATK chest 							
								intATK += 10; // gives hero 10 extra ATK
								intCountATK++; // updates the ATK item counter 
								rpgMethods.statUpdate(con, intHP, intATK, intDEF, 0, 0, 0, intCountATK, intCountDEF); 
								
								// Making the attack chest only accessible once
								strGameMap[intHeroY/25 - 1][intHeroX/25] = "g";
								con.drawImage(grass, intHeroY - 25, intHeroX); 
								
							} else if (strGameMap[intHeroY/25 - 1][intHeroX/25].equals("cDEF")) { // checking to see if the incoming tile is a hidden DEF chest 							
								intDEF += 10; // gives hero 10 extra DEF
								intCountDEF++; // updates the DEF item counter 
								rpgMethods.statUpdate(con, intHP, intATK, intDEF, 0, 0, 0, intCountATK, intCountDEF);
								
								// Make the defense chest only accessible once
								strGameMap[intHeroY/25 - 1][intHeroX/25] = "g";
								con.drawImage(grass, intHeroY - 25, intHeroX); 
								
							} 
						} // close w if statement 
						
						if (chrKey == 'a') { // checks to see if user pressed 'a' key 
							
							if (strGameMap[intHeroY/25][intHeroX/25 - 1].equals("b")) {
								intHP += 10; 
								rpgMethods.statUpdate(con, intHP, intATK, intDEF, 0, 0, 0, intCountATK, intCountDEF); 
								
								strGameMap[intHeroY/25][intHeroX/25 - 1] = "g"; 
								con.drawImage(grass, intHeroY, intHeroX - 25); 
							
							} else if (strGameMap[intHeroY/25][intHeroX/25 - 1].equals("e1") || strGameMap[intHeroY/25][intHeroX/25 - 1].equals("e2") || strGameMap[intHeroY/25][intHeroX/25 - 1].equals("e3") || strGameMap[intHeroY/25][intHeroX/25 - 1].equals("e4")) {
													
								if (strGameMap[intHeroY/25][intHeroX/25 - 1].equals("e1")) { 								
									intHPe = 15; 
									intATKe = 5; 
									intDEFe = 2; 
									rpgMethods.statUpdate(con, intHP, intATK, intDEF, intHPe, intATKe, intDEFe, intCountATK, intCountDEF); 
									intHP = rpgMethods.battle(con, minionBattle0, minionBattle1, minionBattle2, minionBattle3, minionBattle4, intHP, intATK, intDEF, intHPe, intATKe, intDEFe, intCountATK, intCountDEF); 
								
								} else if (strGameMap[intHeroY/25][intHeroX/25 - 1].equals("e2")) {
									intHPe = 20; 
									intATKe = 10; 
									intDEFe = 4; 
									rpgMethods.statUpdate(con, intHP, intATK, intDEF, intHPe, intATKe, intDEFe, intCountATK, intCountDEF); 
									intHP = rpgMethods.battle(con, batBattle0, batBattle1, batBattle2, batBattle3, batBattle4, intHP, intATK, intDEF, intHPe, intATKe, intDEFe, intCountATK, intCountDEF); 
								
								} else if (strGameMap[intHeroY/25][intHeroX/25 - 1].equals("e3")) { 
									intHPe = 25;
									intATKe = 15;
									intDEFe = 6;
									rpgMethods.statUpdate(con, intHP, intATK, intDEF, intHPe, intATKe, intDEFe, intCountATK, intCountDEF); 
									intHP = rpgMethods.battle(con, guardBattle0, guardBattle1, guardBattle2, guardBattle3, guardBattle4, intHP, intATK, intDEF, intHPe, intATKe, intDEFe, intCountATK, intCountDEF); 
								
								} else if (strGameMap[intHeroY/25][intHeroX/25 - 1].equals("e4")) {
									intHPe = 100;
									intATKe = 45;
									intDEFe = 20;
									rpgMethods.statUpdate(con, intHP, intATK, intDEF, intHPe, intATKe, intDEFe, intCountATK, intCountDEF); 
									intHP = rpgMethods.battle(con, bossBattle0, bossBattle1, bossBattle2, bossBattle3, bossBattle4, intHP, intATK, intDEF, intHPe, intATKe, intDEFe, intCountATK, intCountDEF); 
								} 
								
								if (intHP <= 0) {
									blnRekey = false; 
									blnMap = false; 
									blnAlive = false; 
									blnMainMenu = rpgMethods.lose(con, blnMainMenu);
								} else if (intHP > 0 && strGameMap[intHeroY/25][intHeroX/25 - 1].equals("e4")) {
									rpgMethods.youWin(con, youWin); 
								} else {
									blnAlive = true; 
								}
								
								con.drawImage(victory, 0, 0); 
								con.sleep(500); 								
								
								rpgMethods.fade(con); 
								rpgMethods.statUpdate(con, intHP, intATK, intDEF, 0, 0, 0, intCountATK, intCountDEF); // reseth enemy stats to 0 after battle 
								
								strGameMap[intHeroY/25][intHeroX/25 - 1] = "g";
								con.drawImage(grass, intHeroY, intHeroX - 25); 
								
							} else if (strGameMap[intHeroY/25][intHeroX/25 - 1].equals("g")) {
								con.drawImage(grass, intHeroX, intHeroY); 
								con.sleep(20); 
								con.drawImage(hero, intHeroX -= 25, intHeroY); 
								con.repaint(); 	
							
							} else if (strGameMap[intHeroY/25][intHeroX/25 - 1].equals("t")) {
								
							} else if (strGameMap[intHeroY/25][intHeroX/25 - 1].equals("w")) {
								intHP = 0; 
								
								if (intHP <= 0) {
									blnRekey = false; 
									blnMap = false; 
									blnMainMenu = rpgMethods.lose(con, blnMainMenu);
								}
							
							} else if (strGameMap[intHeroY/25][intHeroX/25 - 1].equals("cATK")) {								
								intCountATK++; 
								intATK += 10; 
								rpgMethods.statUpdate(con, intHP, intATK, intDEF, 0, 0, 0, intCountATK, intCountDEF); 
															
								strGameMap[intHeroY/25][intHeroX/25 - 1] = "g";
								con.drawImage(grass, intHeroY, intHeroX - 25); 
							
							} else if (strGameMap[intHeroY/25][intHeroX/25 - 1].equals("cDEF")) {								
								intCountDEF++; 
								intDEF += 10;
								rpgMethods.statUpdate(con, intHP, intATK, intDEF, 0, 0, 0, intCountATK, intCountDEF); 
										
								strGameMap[intHeroY/25][intHeroX/25 - 1] = "g";
								con.drawImage(grass, intHeroY, intHeroX - 25); 
							} 
						} // close a if statement 
						
						if (chrKey == 's') { // checks to see if user pressed 's' key 
							
							if (strGameMap[intHeroY/25 + 1][intHeroX/25].equals("b")) {
								intHP += 10; 
								rpgMethods.statUpdate(con, intHP, intATK, intDEF, 0, 0, 0, intCountATK, intCountDEF); 
								
								strGameMap[intHeroY/25 + 1][intHeroX/25] = "g";  
								con.drawImage(grass, intHeroY + 25, intHeroX);
							
							} else if (strGameMap[intHeroY/25 + 1][intHeroX/25].equals("e1") || strGameMap[intHeroY/25 + 1][intHeroX/25].equals("e2") || strGameMap[intHeroY/25 + 1][intHeroX/25].equals("e3") || strGameMap[intHeroY/25 + 1][intHeroX/25].equals("e4")) {	
															
								if (strGameMap[intHeroY/25 + 1][intHeroX/25].equals("e1")) {
									intHPe = 15;
									intATKe = 5;
									intDEFe = 2;
									rpgMethods.statUpdate(con, intHP, intATK, intDEF, intHPe, intATKe, intDEFe, intCountATK, intCountDEF); 
									intHP = rpgMethods.battle(con, minionBattle0, minionBattle1, minionBattle2, minionBattle3, minionBattle4, intHP, intATK, intDEF, intHPe, intATKe, intDEFe, intCountATK, intCountDEF); 
								
								} else if (strGameMap[intHeroY/25 + 1][intHeroX/25].equals("e2")) {
									intHPe = 20; 
									intATKe = 10;
									intDEFe = 4;
									rpgMethods.statUpdate(con, intHP, intATK, intDEF, intHPe, intATKe, intDEFe, intCountATK, intCountDEF); 
									intHP = rpgMethods.battle(con, batBattle0, batBattle1, batBattle2, batBattle3, batBattle4, intHP, intATK, intDEF, intHPe, intATKe, intDEFe, intCountATK, intCountDEF); 
								
								} else if (strGameMap[intHeroY/25 + 1][intHeroX/25].equals("e3")) {
									intHPe = 25;
									intATKe = 15;
									intDEFe = 6;
									rpgMethods.statUpdate(con, intHP, intATK, intDEF, intHPe, intATKe, intDEFe, intCountATK, intCountDEF); 
									intHP = rpgMethods.battle(con, guardBattle0, guardBattle1, guardBattle2, guardBattle3, guardBattle4, intHP, intATK, intDEF, intHPe, intATKe, intDEFe, intCountATK, intCountDEF); 
								
								} else if (strGameMap[intHeroY/25 + 1][intHeroX/25].equals("e4")) {
									intHPe = 100;
									intATKe = 45;
									intDEFe = 20;
									rpgMethods.statUpdate(con, intHP, intATK, intDEF, intHPe, intATKe, intDEFe, intCountATK, intCountDEF); 
									intHP = rpgMethods.battle(con, bossBattle0, bossBattle1, bossBattle2, bossBattle3, bossBattle4, intHP, intATK, intDEF, intHPe, intATKe, intDEFe, intCountATK, intCountDEF); 
								} 
								
								if (intHP <= 0) {
									blnRekey = false; 
									blnMap = false; 
									blnAlive = false; 
									blnMainMenu = rpgMethods.lose(con, blnMainMenu);
								} else if (intHP > 0 && strGameMap[intHeroY/25 + 1][intHeroX/25].equals("e4")) {
									rpgMethods.youWin(con, youWin); 
								} else {
									blnAlive = true; 
								}
								
								con.drawImage(victory, 0, 0); 
								con.sleep(500); 								
								
								rpgMethods.fade(con); 
								rpgMethods.statUpdate(con, intHP, intATK, intDEF, 0, 0, 0, intCountATK, intCountDEF); 	
												
								strGameMap[intHeroY/25 + 1][intHeroX/25] = "g";
								con.drawImage(grass, intHeroY + 25, intHeroX); 				
															
							} else if (strGameMap[intHeroY/25 + 1][intHeroX/25].equals("g")) {
								con.drawImage(grass, intHeroX, intHeroY); 
								con.sleep(20);
								con.drawImage(hero, intHeroX, intHeroY += 25); 
								con.repaint(); 
							
							} else if (strGameMap[intHeroY/25 + 1][intHeroX/25].equals("t")) {
								
							} else if (strGameMap[intHeroY/25 + 1][intHeroX/25].equals("w")) {
								intHP = 0; 
								
								if (intHP <= 0) {
									blnRekey = false; 
									blnMap = false; 
									blnMainMenu = rpgMethods.lose(con, blnMainMenu);
								}
							
							} else if (strGameMap[intHeroY/25 + 1][intHeroX/25].equals("cATK")) {								
								intCountATK++; 
								intATK += 10;
								rpgMethods.statUpdate(con, intHP, intATK, intDEF, 0, 0, 0, intCountATK, intCountDEF);
								
								strGameMap[intHeroY/25 + 1][intHeroX/25] = "g";
								con.drawImage(grass, intHeroY + 25, intHeroX); 
							
							} else if (strGameMap[intHeroY/25 + 1][intHeroX/25].equals("cDEF")) {								
								intCountDEF++; 
								intDEF += 10;
								rpgMethods.statUpdate(con, intHP, intATK, intDEF, 0, 0, 0, intCountATK, intCountDEF);
								
								strGameMap[intHeroY/25 +1][intHeroX/25] = "g"; 
								con.drawImage(grass, intHeroY + 25, intHeroX); 
							} 
						} // close s if statement 
						
						if (chrKey == 'd') { // checks to see if user pressed 'd' key 
							
							if (strGameMap[intHeroY/25][intHeroX/25 + 1].equals("b")) {
								intHP += 10; 
								rpgMethods.statUpdate(con, intHP, intATK, intDEF, 0, 0, 0, intCountATK, intCountDEF); 
								
								strGameMap[intHeroY/25][intHeroX/25 + 1] = "g";
								con.drawImage(grass, intHeroY, intHeroX + 25); 
							
							} else if (strGameMap[intHeroY/25][intHeroX/25 + 1].equals("e1") || strGameMap[intHeroY/25][intHeroX/25 + 1].equals("e2") || strGameMap[intHeroY/25][intHeroX/25 + 1].equals("e3") || strGameMap[intHeroY/25][intHeroX/25 + 1].equals("e4")) {		
														
								if (strGameMap[intHeroY/25][intHeroX/25 + 1].equals("e1")) { 
									intHPe = 15;
									intATKe = 5;
									intDEFe = 2;
									rpgMethods.statUpdate(con, intHP, intATK, intDEF, intHPe, intATKe, intDEFe, intCountATK, intCountDEF); 
									intHP = rpgMethods.battle(con, minionBattle0, minionBattle1, minionBattle2, minionBattle3, minionBattle4, intHP, intATK, intDEF, intHPe, intATKe, intDEFe, intCountATK, intCountDEF); 
								
								} else if (strGameMap[intHeroY/25][intHeroX/25 + 1].equals("e2")) {
									intHPe = 20;
									intATKe = 10;
									intDEFe = 4;
									rpgMethods.statUpdate(con, intHP, intATK, intDEF, intHPe, intATKe, intDEFe, intCountATK, intCountDEF); 
									intHP = rpgMethods.battle(con, batBattle0, batBattle1, batBattle2, batBattle3, batBattle4, intHP, intATK, intDEF, intHPe, intATKe, intDEFe, intCountATK, intCountDEF); 
								
								} else if (strGameMap[intHeroY/25][intHeroX/25 + 1].equals("e3")) {
									intHPe = 25;
									intATKe = 15;
									intDEFe = 6;
									rpgMethods.statUpdate(con, intHP, intATK, intDEF, intHPe, intATKe, intDEFe, intCountATK, intCountDEF); 
									intHP = rpgMethods.battle(con, guardBattle0, guardBattle1, guardBattle2, guardBattle3, guardBattle4, intHP, intATK, intDEF, intHPe, intATKe, intDEFe, intCountATK, intCountDEF); 
								
								} else if (strGameMap[intHeroY/25][intHeroX/25 + 1].equals("e4")) {
									intHPe = 100;
									intATKe = 45;
									intDEFe = 20; 
									rpgMethods.statUpdate(con, intHP, intATK, intDEF, intHPe, intATKe, intDEFe, intCountATK, intCountDEF); 
									intHP = rpgMethods.battle(con, bossBattle0, bossBattle1, bossBattle2, bossBattle3, bossBattle4, intHP, intATK, intDEF, intHPe, intATKe, intDEFe, intCountATK, intCountDEF); 
								} 
								
								if (intHP <= 0) {
									blnRekey = false; 
									blnMap = false; 
									blnAlive = false; 
									blnMainMenu = rpgMethods.lose(con, blnMainMenu);
								} else if (intHP > 0 && strGameMap[intHeroY/25][intHeroX/25 + 1].equals("e4")) {
									rpgMethods.youWin(con, youWin); 
								} else {
									blnAlive = true; 
								}
								
								con.drawImage(victory, 0, 0); 
								con.sleep(500); 								
								
								rpgMethods.fade(con); 
								rpgMethods.statUpdate(con, intHP, intATK, intDEF, 0, 0, 0, intCountATK, intCountDEF); 
								
								strGameMap[intHeroY/25][intHeroX/25 + 1] = "g";
								con.drawImage(grass, intHeroY, intHeroX + 25); 
								
								if (intHP <= 0) {
									blnRekey = false; 
									blnMap = false; 
									blnMainMenu = rpgMethods.lose(con, blnMainMenu);
								} 
							
							} else if (strGameMap[intHeroY/25][intHeroX/25 + 1].equals("g")) {
								con.drawImage(grass, intHeroX, intHeroY); 
								con.sleep(20);
								con.drawImage(hero, intHeroX += 25, intHeroY); 
								con.repaint();
							
							} else if (strGameMap[intHeroY/25][intHeroX/25 + 1].equals("t")) {
								
							} else if (strGameMap[intHeroY/25][intHeroX/25 + 1].equals("w")) {
								intHP = 0; 
								
								if (intHP <= 0) {
									blnRekey = false; 
									blnMap = false;  
									blnMainMenu = rpgMethods.lose(con, blnMainMenu);
								}
							
							} else if (strGameMap[intHeroY/25][intHeroX/25 + 1].equals("cATK")) {								
								intCountATK++; 
								intATK += 10;
								rpgMethods.statUpdate(con, intHP, intATK, intDEF, 0, 0, 0, intCountATK, intCountDEF);	
														
								strGameMap[intHeroY/25][intHeroX/25 + 1] = "g";
								con.drawImage(grass, intHeroY, intHeroX + 25); 
							
							} else if (strGameMap[intHeroY/25][intHeroX/25 + 1].equals("cDEF")) {								
								intCountDEF++; 
								intDEF += 10;
								rpgMethods.statUpdate(con, intHP, intATK, intDEF, 0, 0, 0, intCountATK, intCountDEF);
								
								strGameMap[intHeroY/25][intHeroX/25 + 1] = "g"; 
								con.drawImage(grass, intHeroY, intHeroX + 25); 
							} 
						} // close d if statement 
						
						while (blnAlive) { // runs if blnAlive is true, meaning it runs only when hero is still alive 
							
							// Redrawing the world map  
							for (int intRow = 0; intRow < 20; intRow++) {
								for (int intCol = 0; intCol < 20; intCol++) {
									if (strGameMap[intRow][intCol].equals("b")) {
										con.drawImage(building, intCol*25, intRow*25); 
									} else if (strGameMap[intRow][intCol].equals("e1")) {
										con.drawImage(minion, intCol*25, intRow*25);
									} else if (strGameMap[intRow][intCol].equals("e2")) {
										con.drawImage(bat, intCol*25, intRow*25);
									} else if (strGameMap[intRow][intCol].equals("e3")) {
										con.drawImage(guard, intCol*25, intRow*25);
									} else if (strGameMap[intRow][intCol].equals("e4")) {
										con.drawImage(boss, intCol*25, intRow*25);
									} else if (strGameMap[intRow][intCol].equals("g")) {
										con.drawImage(grass, intCol*25, intRow*25);
									} else if (strGameMap[intRow][intCol].equals("t")) {
										con.drawImage(tree, intCol*25, intRow*25);
									} else if (strGameMap[intRow][intCol].equals("w")) {
										con.drawImage(water, intCol*25, intRow*25);
									} else if (strGameMap[intRow][intCol].equals("cATK") || strGameMap[intRow][intCol].equals("cDEF")) {  
										con.drawImage(chest, intCol*25, intRow*25);
									}
								} // close intCol for loop
							} // close intRow for loop
							
							// Redrawing the hero sprite in its current position 
							con.drawImage(hero , intHeroX, intHeroY); 
							con.repaint(); // repainting the world map and hero sprite 
							
							rpgMethods.statUpdate(con, intHP, intATK, intDEF, 0, 0, 0, intCountATK, intCountDEF); // update the stats 	
							blnAlive = false; // prevents blnAlive while loop from looping without programmer's command	
						} // close blnAlive while loop 		
					} // close blnRekey while loop 
				} // close blnMap while loop 
			
			} else if (intMouseX >= 53 && intMouseX <= 180 && intMouseY >= 109 && intMouseY <= 147 && intMouseButton == 1) { // checks to see if the user clicks within range of the 'help' button, they will be directed to the help page 
				con.drawImage(helpPage, 0, 0); // draws the help page onto console window 
				con.repaint();
				
				intMouseButton = 0; 
				while (intMouseButton == 0) {
					intMouseButton = con.currentMouseButton();
					intMouseX = con.currentMouseX(); 
					intMouseY = con.currentMouseY(); 
					
					if (intMouseX >= 654 && intMouseX <= 782 && intMouseY >= 442 && intMouseY <= 482 && intMouseButton == 1) { // checks to see if the user presses within range of the 'exit' button
						con.drawImage(mainMenu, 0, 0); // if so, the main menu is redrawn 
					} else {
						intMouseButton = 0;
					}
				} // close intMouseButton while loop
			} else if (intMouseX >= 53 && intMouseX <= 180 && intMouseY >= 165 && intMouseY <= 207 && intMouseButton == 1) { // checks to see if user is clicked within range of "quit" button 
				con.closeConsole(); // if so, close the console 
			} 
		} // close blnMainMenu  while loop 
	} // close main method  
} // close rpgGame class 
		
		
