/* By: Ryan Chiu
 * RPG Game
 * Date Created: February 17, 2020
 * Version 1.0
 */

import arc.*;
import java.awt.*;
import java.awt.image.*;

public class rpgMethods {
	public static void main (String[] args) {
		Console con = new Console();
		
	}
	
	public static String[][] loadMap (String strFileName) {
		
		// Declaring string variables 
		TextInputFile mapFile = new TextInputFile(strFileName);
		String strLine; 
		
		// Delcaring integer variable
		int intCount = 0;

		while (mapFile.eof() == false) {
			strLine = mapFile.readLine(); // reads the next line of the input file  
			intCount++; // counter for how many rows will be in the array 
		}
		mapFile.close(); // close the file so it doesn't read beyond the end of the input file when used again 
		
		String strMap[][] = new String[intCount][20]; // declaring a 2D array called strMap
		String strSplit[]; // creating a 1D array called strSplit 
		
		mapFile = new TextInputFile(strFileName); // re-open the input file  		
		for (int intRow = 0; intRow < intCount; intRow++) { // for loop that runs so long as intRow is less than intCount
			strLine = mapFile.readLine(); // stores the read lines into strLine
			strSplit = strLine.split(","); // splits the lines from strLine based on commas and stores data into the 1D array
			for (int i = 0; i < 20; i++) {
				strMap[intRow][i] = strSplit[i]; // loads the data pieces from strSplit into the 2D array 
			}
		}
		mapFile.close(); 
		
		return strMap; 
	} // close method loadMap 
	
	public static void statUpdate(Console cons, int intHeroHP, int intHeroATK, int intHeroDEF, int intEnemyHP, int intEnemyATK, int intEnemyDEF, int intCountATK, int intCountDEF) {
		
		// Loading wood background & cyan circle for stats display 
		BufferedImage wood = cons.loadImage("woodBkgd.png"); 
		BufferedImage cyanCirc = cons.loadImage("cyanCirc.png");
		
		// Loading the items 
		BufferedImage swordItem1 = cons.loadImage("swordItem1.png");
		BufferedImage armorItem1 = cons.loadImage("armorItem1.png"); 
		
		// Setting text font and color 
		Font statReset = cons.loadFont("markerFeltThin.ttf", 25); 
		cons.setDrawFont(statReset); 
		
		// Drawing and painting the hero's stats in cyan color 
		cons.setDrawColor(Color.CYAN);
		cons.drawImage(wood, 500,0); 
		cons.drawString("--------- Hero ---------", 545, 60); 
		cons.drawString("HP: " + intHeroHP, 540, 95);
		cons.drawString("ATK: " + intHeroATK, 540, 130); 
		cons.drawString("DEF: " + intHeroDEF, 540,165);
		
		// Drawing and painting item holders and respective amounts  
		cons.drawImage(swordItem1, 705, 95); 
		cons.drawImage(armorItem1, 705, 155);
		cons.drawString(intCountATK + "x", 675, 100); 
		cons.drawString(intCountDEF + "x", 675, 160); 
		
		// Drawing and painting the enemy's stats in magenta color 
		cons.setDrawColor(Color.MAGENTA);
		cons.drawString("--- Enemy ---", 590, 280); 
		cons.drawString("HP : " + intEnemyHP, 590, 315);
		cons.drawString("ATK: " + intEnemyATK, 590, 350);
		cons.drawString("DEF: " + intEnemyDEF, 590, 385); 
		cons.repaint();	
	}
	
	public static Boolean lose(Console cons, Boolean blnMenu) {
		
		// Loading "game over" graphics 
		BufferedImage gameOver = cons.loadImage("gameOver.png");
		BufferedImage gameOverRect = cons.loadImage("gameOverRect.png");
		
		// Declaring mouse variables 
		int intButton = 0;
		int intMouseX;
		int intMouseY; 
		
		cons.drawImage(gameOver, 0,0); // draws the game over page 
		cons.drawImage(gameOverRect, 0, 450); // fills in the rest of the uncovered screen with the color black 
		cons.repaint();	
		
		while (intButton == 0) {
			intButton = cons.currentMouseButton();
			intMouseX = cons.currentMouseX();
			intMouseY = cons.currentMouseY();
			
			if (intMouseX >= 333 && intMouseX <= 373 && intMouseY >= 322 && intMouseY <= 331 && intButton == 1) { // checks to see if user clicked within range of 'yes' button 
				blnMenu = true; // allows for the main menu to be repainted
			} else if (intMouseX >= 422 && intMouseX <= 454 && intMouseY >= 322 && intMouseY <= 331 && intButton == 1) { // checks to see if user clicked within range of 'no' button  
				blnMenu = false; // disallows for the main menu to be repainted 
				cons.closeConsole(); // closes the console 
			} else { 
				intButton = 0; // sets intButton = 0 if none of the above conditions are met so user can keep on clicking until they click on a button
			}
		} // close intButton while loop 
		
		return blnMenu; 
	}

	public static int battle(Console cons, BufferedImage imgBattle, BufferedImage img1, BufferedImage img2, BufferedImage img3, BufferedImage img4, int intHeroHP, int intHeroATK, int intHeroDEF, int intEnemyHP, int intEnemyATK, int intEnemyDEF, int intCountATK, int intCountDEF ) {
	
		// Declaring mouse variables 
		int intMouseX;
		int intMouseY;
		int intButton; 
		
		while (intEnemyHP > 0 && intHeroHP > 0) { // while loop only runs when hero and enemy HP are above 0
			cons.drawImage(imgBattle, 0, 0); // draws the battle background screen  		
			
			intButton = 0;
			while (intButton == 0) {
				intButton = cons.currentMouseButton();
				intMouseX = cons.currentMouseX();
				intMouseY = cons.currentMouseY();
				
				if (intMouseX >= 186 && intMouseX <= 313 && intMouseY >= 427 && intMouseY <= 465 && intButton == 1) { // checks to see if user clicked within range of 'ATTACK' button
					cons.drawImage(img1, 0, 0); // draws and paints the first sword image 
					cons.repaint();
					
					cons.sleep(750); 
					cons.drawImage(img1, 0, 0); // draws and paints the first sword image for the second time (because it won't show up otherwise) 
					cons.repaint();
					
					cons.drawImage(img2, 0, 0); // draws and paints the second sword image 
					cons.sleep(750);
					cons.repaint();

					cons.drawImage(img3, 0, 0); // draws and paints the third sword image 
					cons.sleep(750);
					cons.repaint();
					
					cons.drawImage(img4, 0, 0); // draws and paints the fourth sword image 
					cons.sleep(750); 
					cons.repaint();
					
					if (intHeroATK > intEnemyDEF) { // checks to see if hero's attack is greater than enemy's 
						intEnemyHP = intEnemyHP - (intHeroATK - intEnemyDEF); // if so, enemy takes damage equal to the difference between hero attack and enemy defense 
						
						if (intEnemyHP < 0) { // checks to see if enemy HP is less than 0 
							intEnemyHP = 0; // if so, enemy HP is set to 0 to prevent the display of negative enemy HP 
						}
					} else if (intHeroATK <= intEnemyDEF) { // checks to see if the hero's attack is less than or equal to enemy defense
						intEnemyHP = intEnemyHP; // if so, damage is negated 
					}
					statUpdate(cons, intHeroHP, intHeroATK, intHeroDEF, intEnemyHP, intEnemyATK, intEnemyDEF, intCountATK, intCountDEF); // updating stats after hero attacks 
					
					if (intEnemyATK > intHeroDEF) { // checking to see if enemy attack is greater than hero's defense
						intHeroHP = intHeroHP - (intEnemyATK - intHeroDEF); // if so, hero takes takes damage equal to the difference bewteen the enemy's attack and his/her defense 
						
						if (intHeroHP < 0) { // checks to see if hero's HP dropped below 0
							intHeroHP = 0; // if so, the hero's HP is set equal to 0 to prevent display of negative hero HP 
						}
					} else if (intEnemyATK <= intHeroDEF) { // checking to see if enemy's attack is less than or equal to hero's defense
						intHeroHP = intHeroHP; // if so, damage is negated 
					} 
					statUpdate(cons, intHeroHP, intHeroATK, intHeroDEF, intEnemyHP, intEnemyATK, intEnemyDEF, intCountATK, intCountDEF); // updating stats after enemy attacks 
					
				} else { // if user did not click within range of 'attack' button
					intButton = 0; // set intButton = 0 so user can keep on clicking until they click on 'ATTACK' button 
				}
			} // close intButton while loop 
		} // close intEnemyHealth while loop 
		return intHeroHP; 
	} // close battle method 
	
	public static void fade(Console cons) {
		
		// Loading shades of black 
		BufferedImage black1 = cons.loadImage("black1.png");
		BufferedImage black2 = cons.loadImage("black2.png");
		BufferedImage black3 = cons.loadImage("black3.png");
		BufferedImage black4 = cons.loadImage("black4.png");
		BufferedImage black5 = cons.loadImage("black5.png");
		BufferedImage black6 = cons.loadImage("black6.png");
		BufferedImage black7 = cons.loadImage("black7.png");
		BufferedImage black8 = cons.loadImage("black8.png");
		BufferedImage black9 = cons.loadImage("black9.png");
		BufferedImage black10 = cons.loadImage("black10.png");
		BufferedImage black11 = cons.loadImage("black11.png");
		BufferedImage black12 = cons.loadImage("black12.png");
		BufferedImage black13 = cons.loadImage("black13.png");
		BufferedImage black14 = cons.loadImage("black14.png");
		BufferedImage black15 = cons.loadImage("black15.png");
		
		// Fading from a dark to light shade of black  
		cons.drawImage(black14, 0, 0); 
		cons.repaint();
		cons.sleep(100);
		
		cons.drawImage(black13, 0, 0); 
		cons.repaint();
		cons.sleep(100);
		
		cons.drawImage(black12, 0, 0); 
		cons.repaint();
		cons.sleep(100);
		
		cons.drawImage(black11, 0, 0); 
		cons.repaint();
		cons.sleep(100);
		
		cons.drawImage(black10, 0, 0); 
		cons.repaint();
		cons.sleep(100);
		
		cons.drawImage(black9, 0, 0); 
		cons.repaint();
		cons.sleep(100);
		
		cons.drawImage(black8, 0, 0); 
		cons.repaint();
		cons.sleep(100);
		
		cons.drawImage(black7, 0, 0); 
		cons.repaint();
		cons.sleep(100);
		
	} // close fade method 
	
	public static void youWin (Console cons, BufferedImage winImg) {
		cons.drawImage(winImg, 0, 0); // draw the you win screen 
		cons.sleep(1000); // pause the program for 1000 milliseconds 
		cons.closeConsole(); // close the console 
	}
} // close rpgMethods class 
