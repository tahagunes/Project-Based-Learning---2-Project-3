package aa;
import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.io.IOException;
import java.util.Scanner; // Import the Scanner class to read text files
import java.io.FileWriter;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import enigma.console.TextAttributes;
import enigma.core.Enigma;
import enigma.event.TextMouseEvent;
import enigma.event.TextMouseListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main {
	public static enigma.console.Console cn = Enigma.getConsole("DEU PUZZLE", 150, 40, 13, 0);// Enigma.getConsole(name,winxsize,winysize,fontsize,fontno);
	public static TextMouseListener tmlis;
	public static KeyListener klis;
	public static int mousepr; // mouse pressed?
	public static int mousex, mousey; // mouse text coords.
	public static int keypr; // key pressed?
	public static int rkey; // key (for press/release)
	public static TextAttributes text = new TextAttributes(Color.white, Color.black);
	public static String Letters = "A-B-C-D-E-F-G-H-I-J-K-L-M-N-O-P-Q-R-S-T-U-V-W-X-Y-Z";
	public static String letter[] = Letters.split("-");
	public static String[][] searchWord(Board searchtogo) {// scan the Board which fill from file
		int wordSTARTindex = 0, // to send word's start index
				wordENDindex = 0, // to send word's end index
				wordXorY = 0;// if word is horizontal send Y index , else send X index
		String horizontalORvertical = "";// to send word's direction
		int savexandcolumn = 0, saveyandrow = 0, count = 0;
		String[][] worddetect = new String[20][5];
		String worddetectletterbyletter = "";
		Board saveboard = searchtogo;// to save the Board to be scanned
		boolean letterFlag = true;
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				if (!(saveboard.getElement(i, j).equals("█") || saveboard.getElement(i, j).equals("1"))) {// if a letter
					worddetectletterbyletter = "";
					savexandcolumn = j;
					wordSTARTindex = savexandcolumn;
					horizontalORvertical = "horizontal";
					saveyandrow = i;
					/* if word goes to right */if (savexandcolumn < 14// limited board border
							&& !saveboard.getElement(saveyandrow, savexandcolumn + 1).equals("█")// if right side not a
																									// wall
							&& (savexandcolumn < 13
									&& !saveboard.getElement(saveyandrow, savexandcolumn + 2).equals("█"))) {// to
																												// seperate
																												// behind
																												// 2
																												// vertical
																												// word
						wordXorY = saveyandrow;
						while (savexandcolumn < 15 && !saveboard.getElement(saveyandrow, savexandcolumn).equals("█")) {
							worddetectletterbyletter = worddetectletterbyletter
									+ saveboard.getElement(saveyandrow, savexandcolumn);
							if (saveyandrow < 14 && saveboard.getElement(saveyandrow + 1, savexandcolumn).equals("█")) {// if
																														// word's
																														// under
																														// is
																														// wall
								saveboard.setElement(saveyandrow, savexandcolumn, "█");// deleting
							} else if (saveyandrow < 13
									&& !saveboard.getElement(saveyandrow + 1, savexandcolumn).equals("█")
									&& saveboard.getElement(saveyandrow + 2, savexandcolumn).equals("█")) {// if
																											// horizontal
																											// word
																											// under the
																											// word
								saveboard.setElement(saveyandrow, savexandcolumn, "█");
							} else if (saveyandrow < 14 && savexandcolumn < 14
									&& saveboard.getElement(saveyandrow, savexandcolumn + 1).equals("█")
									&& saveboard.getElement(saveyandrow + 1, savexandcolumn).equals("█")) {
								saveboard.setElement(saveyandrow, savexandcolumn, "█");
							} else {
								if (letterFlag) {
									j = savexandcolumn - 1;
									letterFlag = false;
								}
							}

							savexandcolumn++;
						}

						wordENDindex = savexandcolumn - 1;
					}

					else if (savexandcolumn < 15 && saveyandrow < 14
							&& !saveboard.getElement(saveyandrow + 1, savexandcolumn).equals("█")) {
						wordXorY = savexandcolumn;
						wordSTARTindex = saveyandrow;
						horizontalORvertical = "vertical";
						while (saveyandrow < 15 && !saveboard.getElement(saveyandrow, savexandcolumn).equals("█")) {
							worddetectletterbyletter = worddetectletterbyletter
									+ saveboard.getElement(saveyandrow, savexandcolumn);
							if ((savexandcolumn < 14
									&& saveboard.getElement(saveyandrow, savexandcolumn + 1).equals("█"))
									&& (savexandcolumn > 1
											&& saveboard.getElement(saveyandrow, savexandcolumn - 1).equals("█"))) {
								saveboard.setElement(saveyandrow, savexandcolumn, "█");
							} else if (savexandcolumn == 0
									&& saveboard.getElement(saveyandrow, savexandcolumn + 1).equals("█")) {
								saveboard.setElement(saveyandrow, savexandcolumn, "█");
							} else if (savexandcolumn < 14
									&& !saveboard.getElement(saveyandrow, savexandcolumn + 1).equals("█")) {
								if (savexandcolumn < 13
										&& saveboard.getElement(saveyandrow, savexandcolumn + 2).equals("█")) {
									saveboard.setElement(saveyandrow, savexandcolumn, "█");
								}
							} else if (savexandcolumn == 14
									&& saveboard.getElement(saveyandrow, savexandcolumn - 1).equals("█")) {
								saveboard.setElement(saveyandrow, savexandcolumn, "█");
							}
							saveyandrow++;
						}
						wordENDindex = saveyandrow - 1;
					}
					letterFlag = true;
					if (!worddetectletterbyletter.equals("")) {
						worddetect[count][0] = worddetectletterbyletter;// word
						worddetect[count][1] = horizontalORvertical;
						worddetect[count][2] = Integer.toString(wordSTARTindex);
						worddetect[count][3] = Integer.toString(wordENDindex);
						worddetect[count][4] = Integer.toString(wordXorY);
						count++;
					}
				}

			}
		}

		return worddetect;
	}

	public static void showWordList(MultiLinkedList MLL,String[] completedword) {// show wordlist screen
		cn.setTextAttributes(new TextAttributes(Color.white, Color.black));
		int texty = 3,textx=20;
		for (int i = 0; i < 15; i++) {
			cn.getTextWindow().setCursorPosition(textx, texty);
			for (int j = 0; j < 100; j++) {
				System.out.print(" ");
			}
			texty++;
		}
		texty = 3;textx=20;
		for (int i = 0; i < completedword.length; i++) {
			if(completedword[i]!=null) {
				cn.getTextWindow().setCursorPosition(textx, texty);
				System.out.print("[X]"+completedword[i]);
				texty++;
				if(texty==17) {texty=3;textx=textx+15;}
			}
		}
		for (int i = 0; i < letter.length; i++) {
			String[] showWordList=MLL.LETTERwords(MLL, letter[i]);
			for (int j = 0; j < showWordList.length; j++) {
				cn.getTextWindow().setCursorPosition(textx, texty);
				if(showWordList[j]!=null) {System.out.print("[ ]"+showWordList[j]);texty++;}
				if(texty==17) {texty=3;textx=textx+15;}
			}
		}
		cn.getTextWindow().setCursorPosition(19, 2);
		System.out.print("+-WORD-LIST");
		for (int i = 0; i < textx-10; i++) {
			System.out.print("-");
		}
		System.out.println("+");
		int a=19,b=3,c=textx+20;
		for (int i = 0; i < 14; i++) {
			cn.getTextWindow().setCursorPosition(a, b);System.out.print("|");
			cn.getTextWindow().setCursorPosition(c, b);System.out.print("|");
			b++;
		}
		cn.getTextWindow().setCursorPosition(19, 17);
		System.out.print("+");
		for (int i = 0; i < textx; i++) {
			System.out.print("-");
		}
		System.out.print("+");
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		int solutionindex = 0, wordHV = 2, wordSI = 0, wordEI = 0, VorH = 0, solutionindex1 = 0, wordHV1 = 2,
				wordSI1 = 0, wordEI1 = 0, VorH1 = 0, solutionindex2 = 0, wordHV2 = 2, wordSI2 = 0, wordEI2 = 0,
				VorH2 = 0, wordcount = 0, checkletter = 0,cwCOUNT=0;
		String wall = "█", whoseturn = "player1";
		boolean wordcomplete = false, newWORD = true, newWORD2 = false, DOORisopen = true, DOORisopen2 = false,
				player2firstword = true,game=true;
		String[] completedwords=new String[30];
		Board saveboard = new Board(), saveboard2 = new Board(), boardsolution = new Board(), gameboard = new Board();
		String[][] saveword = new String[1][5], saveword1 = new String[1][5], saveword2 = new String[1][5];
		MultiLinkedList wordlistFromSLL = new MultiLinkedList();
		for (int i = 0; i < letter.length; i++) {
			wordlistFromSLL.AddLetter(letter[i]);// for seperate words depend on first letter
		}
		SingleLinkedList wordlistSLL = new SingleLinkedList();
		for (int i = 0; i < letter.length; i++) {// read file and sort words in SLL
			try {
				File myObj = new File("word_list.txt");
				Scanner myReader = new Scanner(myObj, "UTF-8");
				while (myReader.hasNextLine()) {
					String data = myReader.nextLine();
					String fileword[] = data.split(",");// seperate english word and turkish word
					String wordletters[] = fileword[0].split("");// for check words first letter
					wordletters[0] = wordletters[0].toUpperCase();
					if (letter[checkletter].equals(wordletters[0])) {
						wordlistSLL.insert(fileword[0]);
					}
				}
				myReader.close();
			} catch (FileNotFoundException e) {
				System.out.println("word_list.txt couldn't found");
				e.printStackTrace();
			}
			checkletter++;
		}
		
		int capacity=0;
		File f = new File("word_list.txt");
		Scanner dosya = new Scanner(f);	
			
		while(dosya.hasNextLine()) {						
			if(dosya.nextLine()!=" ")
			{
				capacity++;
			}	
		}	
		dosya.close();
		Meant meanings=new Meant(capacity);
		try {
			File myObj = new File("word_list.txt");
			Scanner myReader = new Scanner(myObj, "UTF-8");
			while (myReader.hasNextLine()) {
				
				String data = myReader.nextLine();
				String fileword[] = data.split(",");// seperate english word and turkish word
				String word=fileword[0];
				String mean=fileword[1];
				Word dataToAdd=new Word(word, mean);
				meanings.addWord(dataToAdd);
				
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("word_list.txt couldn't found");
			e.printStackTrace();
		}
		
		ScoreTable highscore=new ScoreTable();
		try {
			File myObj = new File("high_score_table.txt");
			Scanner myReader = new Scanner(myObj, "UTF-8");
			while (myReader.hasNextLine()) {
				
				String data = myReader.nextLine();
				if(data.equals("")) {}
				else {
				String fileword[] = data.split(";");// seperate name and score
				String name=fileword[0];
				int score=Integer.valueOf(fileword[1]);
				highscore.Add(name, score);
				}
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("word_list.txt couldn't found");
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		int saveSize = wordlistSLL.size();
		checkletter = 0;
		for (int i = 0; i < saveSize; i++) {
			String transfertoMLL = wordlistSLL.getHeadElement();
			String[] transfertoMLLletterbyletter = transfertoMLL.split("");
			transfertoMLLletterbyletter[0] = transfertoMLLletterbyletter[0].toUpperCase();
			while (letter[checkletter].equals(transfertoMLLletterbyletter[0])) {
				wordlistFromSLL.addWord(letter[checkletter], transfertoMLL);
				wordlistSLL.insert(transfertoMLL);
				wordlistSLL.delete();
				saveSize--;// decrease size each deleted word
				transfertoMLL = wordlistSLL.getHeadElement();
				transfertoMLLletterbyletter = transfertoMLL.split("");
				transfertoMLLletterbyletter[0] = transfertoMLLletterbyletter[0].toUpperCase();
			}
			saveSize++;// to fix first letter count
			checkletter++;
		}
		try {
			File puzzle = new File("puzzle.txt");
			Scanner puzzleReader = new Scanner(puzzle);
			while (puzzleReader.hasNextLine()) {
				String wordsandnumbers = puzzleReader.nextLine();
				String line[] = wordsandnumbers.split("	");
				for (int i = 0; i < line.length; i++) {
					if (line[i].equals("0")) {
						gameboard.addElement(wall, i);
					} else if (line[i].equals("i")) {
						gameboard.addElement("I", i);
					} else {
						line[i] = line[i].toUpperCase();
						gameboard.addElement(line[i], i);
					}
				}
			}
			puzzleReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("puzzle.txt couldn't found");
			e.printStackTrace();
		}
		try {
			File puzzlesolution = new File("solution.txt");
			Scanner solution = new Scanner(puzzlesolution);
			while (solution.hasNextLine()) {
				String answersandnumbers = solution.nextLine();
				answersandnumbers.toUpperCase();
				String line[] = answersandnumbers.split("	");
				for (int i = 0; i < line.length; i++) {
					if (line[i].equals("0")) {
						boardsolution.addElement(wall, i);
					} else if (line[i].equals("i")) {
						boardsolution.addElement("I", i);
					} else {
						line[i] = line[i].toUpperCase();
						boardsolution.addElement(line[i], i);
					}
				}
			}
			solution.close();
		} catch (FileNotFoundException e) {
			System.out.println("solution.txt couldn't found");
			e.printStackTrace();
		}

		tmlis = new TextMouseListener() {
			public void mouseClicked(TextMouseEvent arg0) {
			}

			public void mousePressed(TextMouseEvent arg0) {
				if (mousepr == 0) {
					mousepr = 1;
					mousex = arg0.getX();
					mousey = arg0.getY();
				}
			}

			public void mouseReleased(TextMouseEvent arg0) {
			}
		};
		cn.getTextWindow().addTextMouseListener(tmlis);

		klis = new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
				if (keypr == 0) {
					keypr = 1;
					rkey = e.getKeyCode();
				}
			}

			public void keyReleased(KeyEvent e) {
			}
		};
		cn.getTextWindow().addKeyListener(klis);

		int px = 7, py = 10;

// MENU ////////////////////////////////////////////////////////////////////////////
		cn.getTextWindow().setCursorPosition(0, 0);
		Scanner playerentry = new Scanner(System.in);
		System.out.print("PLAYER 1 NAME    => ");
		String p1name = playerentry.nextLine();
		System.out.print("PLAYER 1 SURNAME => ");
		String p1surname = playerentry.nextLine();
		System.out.print("PLAYER 2 NAME    => ");
		String p2name = playerentry.nextLine();
		System.out.print("PLAYER 2 SURNAME => ");
		String p2surname = playerentry.nextLine();
		Player player1 = new Player(p1name, p1surname);
		Player player2 = new Player(p2name, p2surname);
		cn.getTextWindow().setCursorPosition(0, 0);
		System.out.println("                                                ");
		System.out.println("                                                ");
		System.out.println("                                                ");
		System.out.println("                                                ");// to clean console
////////////////////////////////////////////////////////////////////////////////////////
		Board errorfix = new Board();
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				errorfix.addElement(boardsolution.getElement(i, j), j);
			}
		}
		// boardsolution.display();

		String[][] wordsfromsolution = searchWord(boardsolution);

		boardsolution = new Board();
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				boardsolution.addElement(errorfix.getElement(i, j), j);
			}
		}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////GAME LOOP START
		while (game) {

			cn.setTextAttributes(new TextAttributes(Color.white, Color.black));
			cn.getTextWindow().setCursorPosition(0, 0);
			System.out.println("WORD = " + wordcount);
			cn.getTextWindow().setCursorPosition(20, 0);
			if (whoseturn.equals("player1")) {
				cn.setTextAttributes(new TextAttributes(Color.black, Color.white));
				System.out.print("PLAYER1 SCORE : " + player1.getScore());
				cn.setTextAttributes(new TextAttributes(Color.white, Color.black));
				System.out.print(" PLAYER2 SCORE : " + player2.getScore());
			} else {
				cn.setTextAttributes(new TextAttributes(Color.white, Color.black));
				System.out.print("PLAYER1 SCORE : " + player1.getScore() + " ");
				cn.setTextAttributes(new TextAttributes(Color.black, Color.white));
				System.out.print("PLAYER2 SCORE : " + player2.getScore());
				cn.setTextAttributes(new TextAttributes(Color.white, Color.black));
			}

			cn.getTextWindow().setCursorPosition(0, 2);
			gameboard.display();

			cn.setTextAttributes(new TextAttributes(Color.black, Color.red));
			cn.getTextWindow().setCursorPosition(px, py);
			if (gameboard.getElement(py - 3, px - 1).equals(wall) || gameboard.getElement(py - 3, px - 1).equals("1")) {
				System.out.print(" ");
			} else {
				System.out.print(gameboard.getElement(py - 3, px - 1));
			}

			if (keypr == 1) { // if keyboard button pressed
				if (wordHV == 0) {
					if (px > 1 && px - 1 > wordSI + 1 && rkey == KeyEvent.VK_LEFT)
						px--;
					if (px < 15 && px - 1 < wordEI && rkey == KeyEvent.VK_RIGHT)
						px++;
					if (py > 3 && rkey == KeyEvent.VK_UP) {
					}
					if (py < 17 && rkey == KeyEvent.VK_DOWN) {
					}

				} else if (wordHV == 1) {
					if (px > 1 && rkey == KeyEvent.VK_LEFT) {
					}
					if (px < 15 && rkey == KeyEvent.VK_RIGHT) {
					}
					if (py > 3 && py - 3 > wordSI + 1 && rkey == KeyEvent.VK_UP)
						py--;
					if (py < 17 && py - 3 < wordEI && rkey == KeyEvent.VK_DOWN)
						py++;
				} else {
					if (px > 1 && rkey == KeyEvent.VK_LEFT)
						px--;
					if (px < 15 && rkey == KeyEvent.VK_RIGHT)
						px++;
					if (py > 3 && rkey == KeyEvent.VK_UP)
						py--;
					if (py < 17 && rkey == KeyEvent.VK_DOWN)
						py++;
				}

				char rckey = (char) rkey;
				// left right up down
				if (rckey == '%' || rckey == '\'' || rckey == '&' || rckey == '(') {

					System.out.print(" ");
				} // VK kullanmadan test teknigi
				else
					cn.getTextWindow().output(rckey);

				if (rkey == KeyEvent.VK_SPACE) {
					String str;
					str = cn.readLine(); // keyboardlistener running and readline input by using enter
					cn.getTextWindow().setCursorPosition(5, 20);
					cn.getTextWindow().output(str);
				}
//CHECK USER ENTRY//////////////////////////////////////////////////////////////////////////////////////////////////////////////
				String userentry = Character.toString((char) rkey);
				userentry.toUpperCase();
				String worddirection = "", worddirection2 = "", worddirection1 = "";
				if ((rkey < 91 && rkey > 64) || (rkey < 123 && rkey > 96)) {
					if (DOORisopen) {
						saveboard = new Board();
						for (int i = 0; i < 15; i++) {
							for (int j = 0; j < 15; j++) {
								saveboard.addElement(gameboard.getElement(i, j), j);
							}
						}

						DOORisopen = false;
					}
					if (DOORisopen2) {
						saveboard2 = new Board();
						for (int i = 0; i < 15; i++) {
							for (int j = 0; j < 15; j++) {
								saveboard2.addElement(gameboard.getElement(i, j), j);
							}
						}
						DOORisopen2 = false;
					}
					gameboard.setElement(py - 3, px - 1, userentry);
					if (newWORD) {

						VorH1 = 0;
						wordSI1 = 0;
						wordEI1 = 0;

						for (int i = 0; i < wordsfromsolution.length; i++) {
							if ((wordsfromsolution[i][1] != null && wordsfromsolution[i][1].equals("horizontal"))
									&& (wordsfromsolution[i][4] != null
											&& Integer.parseInt(wordsfromsolution[i][4]) == py - 3)
									&& (wordsfromsolution[i][2] != null
											&& Integer.parseInt(wordsfromsolution[i][2]) < px - 1)
									&& (wordsfromsolution[i][3] != null
											&& Integer.parseInt(wordsfromsolution[i][3]) >= px - 1)) {
								wordSI1 = Integer.parseInt(wordsfromsolution[i][2]);
								wordEI1 = Integer.parseInt(wordsfromsolution[i][3]);
								worddirection1 = wordsfromsolution[i][1];
								VorH1++;
								wordHV1 = 0;// to set movement limit
								saveword1 = new String[1][5];
								saveword1[0][0] = wordsfromsolution[i][0];
								saveword1[0][1] = wordsfromsolution[i][1];
								saveword1[0][2] = wordsfromsolution[i][2];
								saveword1[0][3] = wordsfromsolution[i][3];
								saveword1[0][4] = wordsfromsolution[i][4];
								solutionindex1 = i;
								break;
							}
						}
						for (int i = 0; i < wordsfromsolution.length; i++) {
							if ((wordsfromsolution[i][1] != null && wordsfromsolution[i][1].equals("vertical"))
									&& (wordsfromsolution[i][4] != null
											&& Integer.parseInt(wordsfromsolution[i][4]) == px - 1)
									&& (wordsfromsolution[i][2] != null
											&& Integer.parseInt(wordsfromsolution[i][2]) < py - 3)
									&& (wordsfromsolution[i][3] != null
											&& Integer.parseInt(wordsfromsolution[i][3]) >= py - 3)) {
								wordSI1 = Integer.parseInt(wordsfromsolution[i][2]);
								wordEI1 = Integer.parseInt(wordsfromsolution[i][3]);
								worddirection1 = wordsfromsolution[i][1];
								VorH1++;
								wordHV1 = 1;// to set movement limit
								saveword1 = new String[1][5];
								saveword1[0][0] = wordsfromsolution[i][0];
								saveword1[0][1] = wordsfromsolution[i][1];
								saveword1[0][2] = wordsfromsolution[i][2];
								saveword1[0][3] = wordsfromsolution[i][3];
								saveword1[0][4] = wordsfromsolution[i][4];
								solutionindex1 = i;
								break;
							}
						}

						newWORD = false;
					} else if (newWORD2) {
						// if player2 has turn
						VorH2 = 0;
						wordSI2 = 0;
						wordEI2 = 0;

						for (int i = 0; i < wordsfromsolution.length; i++) {
							if ((wordsfromsolution[i][1] != null && wordsfromsolution[i][1].equals("horizontal"))
									&& (wordsfromsolution[i][4] != null
											&& Integer.parseInt(wordsfromsolution[i][4]) == py - 3)
									&& (wordsfromsolution[i][2] != null
											&& Integer.parseInt(wordsfromsolution[i][2]) < px - 1)
									&& (wordsfromsolution[i][3] != null
											&& Integer.parseInt(wordsfromsolution[i][3]) >= px - 1)) {
								wordSI2 = Integer.parseInt(wordsfromsolution[i][2]);
								wordEI2 = Integer.parseInt(wordsfromsolution[i][3]);
								worddirection2 = wordsfromsolution[i][1];
								VorH2++;
								wordHV2 = 0;// to set movement limit
								saveword2 = new String[1][5];
								saveword2[0][0] = wordsfromsolution[i][0];
								saveword2[0][1] = wordsfromsolution[i][1];
								saveword2[0][2] = wordsfromsolution[i][2];
								saveword2[0][3] = wordsfromsolution[i][3];
								saveword2[0][4] = wordsfromsolution[i][4];
								solutionindex2 = i;
								break;
							}
						}
						for (int i = 0; i < wordsfromsolution.length; i++) {
							if ((wordsfromsolution[i][1] != null && wordsfromsolution[i][1].equals("vertical"))
									&& (wordsfromsolution[i][4] != null
											&& Integer.parseInt(wordsfromsolution[i][4]) == px - 1)
									&& (wordsfromsolution[i][2] != null
											&& Integer.parseInt(wordsfromsolution[i][2]) < py - 3)
									&& (wordsfromsolution[i][3] != null
											&& Integer.parseInt(wordsfromsolution[i][3]) >= py - 3)) {
								wordSI2 = Integer.parseInt(wordsfromsolution[i][2]);
								wordEI2 = Integer.parseInt(wordsfromsolution[i][3]);
								worddirection2 = wordsfromsolution[i][1];
								VorH2++;
								wordHV2 = 1;// to set movement limit
								saveword2 = new String[1][5];
								saveword2[0][0] = wordsfromsolution[i][0];
								saveword2[0][1] = wordsfromsolution[i][1];
								saveword2[0][2] = wordsfromsolution[i][2];
								saveword2[0][3] = wordsfromsolution[i][3];
								saveword2[0][4] = wordsfromsolution[i][4];
								solutionindex2 = i;
								break;
							}
						}
						newWORD2 = false;

					}

					if (whoseturn.equals("player1")) {
						VorH = VorH1;
						wordSI = wordSI1;
						wordEI = wordEI1;
						worddirection = worddirection1;
						wordHV = wordHV1;
						saveword = saveword1;
						solutionindex = solutionindex1;
					} else {
						VorH = VorH2;
						wordSI = wordSI2;
						wordEI = wordEI2;
						worddirection = worddirection2;
						wordHV = wordHV2;
						saveword = saveword2;
						solutionindex = solutionindex2;
					}

//////////////////////////////////////////////////////////////
					errorfix = new Board();
					for (int i = 0; i < 15; i++) {
						for (int j = 0; j < 15; j++) {
							errorfix.addElement(gameboard.getElement(i, j), j);
						}
					}
					String[][] wordsinpuzzle = searchWord(gameboard);
					gameboard = new Board();
					for (int i = 0; i < 15; i++) {
						for (int j = 0; j < 15; j++) {
							gameboard.addElement(errorfix.getElement(i, j), j);
						}
					}
					System.out.print("");
//////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////
					errorfix = new Board();
					for (int i = 0; i < 15; i++) {
						for (int j = 0; j < 15; j++) {
							errorfix.addElement(saveboard.getElement(i, j), j);
						}
					}
					String[][] scorecheckk = searchWord(saveboard);
					saveboard = new Board();
					for (int i = 0; i < 15; i++) {
						for (int j = 0; j < 15; j++) {
							saveboard.addElement(errorfix.getElement(i, j), j);
						}
					}

/////////////////////////////
					if (!player2firstword) {
						errorfix = new Board();
						for (int i = 0; i < 15; i++) {
							for (int j = 0; j < 15; j++) {
								errorfix.addElement(saveboard2.getElement(i, j), j);
							}
						}
						String[][] scorecheckk2 = searchWord(saveboard2);
						saveboard2 = new Board();
						for (int i = 0; i < 15; i++) {
							for (int j = 0; j < 15; j++) {
								saveboard2.addElement(errorfix.getElement(i, j), j);
							}
						}
					}
//////////////////////////////////////////////////////////////
					if (VorH == 1 || VorH == 2) {// if player completing the word which selected
						if (VorH == 2) {
							if (whoseturn.equals("player1")) {
								newWORD = true;
								wordHV = 2;
							} else {
								newWORD2 = true;
								wordHV = 2;
							}
						}
						int scorecount = 0;

						if (VorH != 2 && whoseturn.equals("player1"))
							newWORD = false;
						else if (VorH != 2 && whoseturn.equals("player2"))
							newWORD2 = false;

						String[] togetwords = saveword[0][0].split("");
						String[] MLLwords = wordlistFromSLL.LETTERwords(wordlistFromSLL, togetwords[0]);
						for (int i = 0; i < MLLwords.length; i++) {
							if (MLLwords[i] != null) {
								String[] lettersMLLwords = MLLwords[i].toUpperCase().split("");
								for (int k = 0; k < lettersMLLwords.length; k++) {
									if (lettersMLLwords[k].equals("İ"))
										lettersMLLwords[k] = "I";
								}
								String[] lettersPUZZLEword = wordsinpuzzle[solutionindex][0].split("");
								for (int j = 1; j < lettersPUZZLEword.length; j++) {
									if (lettersPUZZLEword.length != lettersMLLwords.length) {
										MLLwords[i] = null;
									} else if (lettersPUZZLEword[j].equals("1")) {

									} else if (lettersMLLwords[j].equals(lettersPUZZLEword[j])) {
										break;
									}

								}

							}
						}

						for (int i = 0; i < MLLwords.length; i++) {
							if (MLLwords[i] != null)
								scorecount++;
						}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////set right letter on the gameboard and saveboard
						for (int i = 0; i < MLLwords.length; i++) {
							int addscore = 0;
							if (MLLwords[i] != null) {
								String[] lettersMLLwords = MLLwords[i].toUpperCase().split("");
								for (int k = 0; k < lettersMLLwords.length; k++) {
									if (lettersMLLwords[k].equals("İ"))
										lettersMLLwords[k] = "I";
								}
								if (saveword[0][1].equals("horizontal") &&gameboard.getElement(py - 3, px - 1)
										.equals(lettersMLLwords[px - 1 - wordSI])) {
									if (whoseturn.equals("player1"))
										saveboard.setElement(py - 3, px - 1, userentry);
									else
										saveboard2.setElement(py - 3, px - 1, userentry);
									cn.setTextAttributes(new TextAttributes(Color.green, Color.black));
									if (whoseturn.equals("player1")) {
										int newscore = player1.getScore() + 1;
										player1.setScore(newscore);
										cn.getTextWindow().setCursorPosition(0, 19);
										System.out.println("Player1 gets 1 point");
									} else {// if whoseturn = player2
										int newscore = player2.getScore() + 1;
										player2.setScore(newscore);
										cn.getTextWindow().setCursorPosition(0, 19);
										System.out.println("Player2 gets 1 point");
									}
								} else if (saveword[0][1].equals("vertical")&&gameboard.getElement(py - 3, px - 1)
										.equals(lettersMLLwords[py - 3 - wordSI])) {
									if (whoseturn.equals("player1"))
										saveboard.setElement(py - 3, px - 1, userentry);
									else
										saveboard2.setElement(py - 3, px - 1, userentry);
									cn.setTextAttributes(new TextAttributes(Color.green, Color.black));
									if (whoseturn.equals("player1")) {
										int newscore = player1.getScore() + 1;
										player1.setScore(newscore);
										cn.getTextWindow().setCursorPosition(0, 19);
										System.out.println("Player1 gets 1 point");
									} else {// if whoseturn = player2
										int newscore = player2.getScore() + 1;
										player2.setScore(newscore);
										cn.getTextWindow().setCursorPosition(0, 19);
										System.out.println("Player2 gets 1 point");
									}
								}
							}
						}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////check word completing						
						int saveStart = wordSI, lenghtcount = 0;
						for (int i = wordSI; i <= wordEI; i++) {
							if (saveword[0][1].equals("horizontal")
									&& !gameboard.getElement(Integer.parseInt(saveword[0][4]), saveStart).equals("1")) {
								lenghtcount++;
							} else if (saveword[0][1].equals("vertical")
									&& !gameboard.getElement(saveStart, Integer.parseInt(saveword[0][4])).equals("1")) {
								lenghtcount++;
							}
							if (saveStart == wordEI && lenghtcount == saveword[0][0].length()) {
								wordcomplete = true;// open the score calculating
								wordHV = 2;// set to free limit
								if (whoseturn.equals("player1")) {
									newWORD = true;
								} // to save new board after completing word
								else {
									newWORD2 = true;
								}
								DOORisopen = true;
								DOORisopen2 = true;
							}
							saveStart++;
						}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////						
						int increasescore = 0, matchwordcount = 0;
						for (int i = 0; i < MLLwords.length; i++) {
							if (MLLwords[i] != null) {
								String[] lettersMLLwords2 = MLLwords[i].toUpperCase().split("");
								for (int k = 0; k < lettersMLLwords2.length; k++) {
									if (lettersMLLwords2[k].equals("İ"))
										lettersMLLwords2[k] = "I";
								}
								String[] lettersPUZZLEword2 = wordsinpuzzle[solutionindex][0].split("");
								for (int j = 1; j < lettersPUZZLEword2.length; j++) {
									if (lettersMLLwords2[j].equals(lettersPUZZLEword2[j])) {
										increasescore++;
									} else if (matchwordcount == 0 && !lettersPUZZLEword2[j].equals("1")
											&& !lettersMLLwords2[j].equals(lettersPUZZLEword2[j])) {
										increasescore = lettersMLLwords2.length * -2;
										i = MLLwords.length;
										break;
									}
								}
								matchwordcount++;
							}
							
						}
						if (increasescore <= 0) {
							gameboard = new Board();
							if (whoseturn.equals("player1")) {
								int newscore = player1.getScore() + increasescore;
								player1.setScore(newscore);
								for (int i = 0; i < 15; i++) {
									for (int j = 0; j < 15; j++) {
										gameboard.addElement(saveboard.getElement(i, j), j);
										// wordSI2 wordEI2 wordHV
										if (wordHV2 == 0 && wordSI2 < j && wordEI2 >= j) {
											saveboard2.setElement(Integer.parseInt(saveword1[0][4]), j,
													saveboard.getElement(i, j));
										} else if (wordHV2 == 1 && wordSI2 < i && wordEI2 >= i) {
											saveboard2.setElement(i, Integer.parseInt(saveword1[0][4]),
													saveboard.getElement(i, j));
										}
									}
								}
							} else {
								int newscore = player2.getScore() + increasescore;
								player2.setScore(newscore);
								for (int i = 0; i < 15; i++) {
									for (int j = 0; j < 15; j++) {
										gameboard.addElement(saveboard2.getElement(i, j), j);
										// wordSI2 wordEI2 wordHV
										if (wordHV1 == 0 && wordSI1 < j && wordEI1 >= j) {
											saveboard.setElement(Integer.parseInt(saveword2[0][4]), j,
													saveboard2.getElement(i, j));
										} else if (wordHV1 == 1 && wordSI1 < i && wordEI1 >= i) {
											saveboard.setElement(i, Integer.parseInt(saveword2[0][4]),
													saveboard2.getElement(i, j));
										}
									}
								}
							}
							if (whoseturn.equals("player1")) {
								whoseturn = "player2";
								VorH = VorH2;
								wordSI = wordSI2;
								wordEI = wordEI2;
								worddirection = worddirection2;
								wordHV = wordHV2;
								saveword = saveword2;
								solutionindex = solutionindex2;

								if (player2firstword) {
									DOORisopen2 = true;
									wordHV = 2;
									player2firstword = false;
									newWORD2 = true;
								}
							} else {
								whoseturn = "player1";
								VorH = VorH1;
								wordSI = wordSI1;
								wordEI = wordEI1;
								worddirection = worddirection1;
								wordHV = wordHV1;
								saveword = saveword1;
								solutionindex = solutionindex1;
							}

						}
						cn.setTextAttributes(new TextAttributes(Color.green, Color.black));
						String wordtocheckmean="";
						if (wordcomplete) {// word is completed wrong or right
							gameboard = new Board();

							if (whoseturn.equals("player1")) {
								for (int i = 0; i < MLLwords.length; i++) {
									if(MLLwords[i]!=null) {
										String MLLwordPrep = "";
										String[] lettersMLLwords2 = MLLwords[i].toUpperCase().split("");
										for (int k = 0; k < lettersMLLwords2.length; k++) {
											if (lettersMLLwords2[k].equals("İ"))
												lettersMLLwords2[k] = "I";
											MLLwordPrep = MLLwordPrep + lettersMLLwords2[k];
										}
										String saveMLLword=MLLwords[i];
										MLLwords[i] = MLLwordPrep;
										if (MLLwords[i] != null && wordsinpuzzle[solutionindex][0].equals(MLLwords[i])) {
											int newscore = player1.getScore() + 10;
											player1.setScore(newscore);
											cn.setTextAttributes(new TextAttributes(Color.green, Color.black));
											cn.getTextWindow().setCursorPosition(0, 19);
											System.out.println("Player1 completed 1 word and gets 10 point");
											wordtocheckmean=saveMLLword; //SON TAMAMLANAN KELİME BU MU ??? *************************************************------------------------------
											wordcount++;
											completedwords[cwCOUNT]=saveMLLword;cwCOUNT++;
											wordlistSLL.SearchandDel(wordlistSLL, saveMLLword);
											wordlistFromSLL.SearchAndDeleteWord(wordlistFromSLL, lettersMLLwords2[0], saveMLLword);
											boolean question=meanings.question(wordtocheckmean);
											if(question==true) {
												newscore = player1.getScore() + 10;
												player1.setScore(newscore);
												cn.setTextAttributes(new TextAttributes(Color.green, Color.black));
												cn.getTextWindow().setCursorPosition(0, 25);
												System.out.println("Player1 answered correctly and gets 10 point too");
											}
											else
												//do nothing
											break;
										}
									}
								}
								for (int i = 0; i < 15; i++) {
									for (int j = 0; j < 15; j++) {
										gameboard.addElement(saveboard.getElement(i, j), j);
										// wordSI2 wordEI2 wordHV
										if (wordHV2 == 0 && wordSI2 < j && wordEI2 >= j) {
											saveboard2.setElement(Integer.parseInt(saveword1[0][4]), j,
													saveboard.getElement(i, j));
										} else if (wordHV2 == 1 && wordSI2 < i && wordEI2 >= i) {
											saveboard2.setElement(i, Integer.parseInt(saveword1[0][4]),
													saveboard.getElement(i, j));
										}
									}
								}

							} else {
								for (int i = 0; i < MLLwords.length; i++) {
									if(MLLwords[i]!=null) {
										String MLLwordPrep = "";
										String[] lettersMLLwords2 = MLLwords[i].toUpperCase().split("");
										for (int k = 0; k < lettersMLLwords2.length; k++) {
											if (lettersMLLwords2[k].equals("İ"))
												lettersMLLwords2[k] = "I";
											MLLwordPrep = MLLwordPrep + lettersMLLwords2[k];
										}
										String saveMLLword=MLLwords[i];
										MLLwords[i] = MLLwordPrep;
										if (MLLwords[i] != null && wordsinpuzzle[solutionindex][0].equals(MLLwords[i])) {
											int newscore = player2.getScore() + 10;
											player2.setScore(newscore);
											cn.setTextAttributes(new TextAttributes(Color.green, Color.black));
											cn.getTextWindow().setCursorPosition(0, 19);
											System.out.println("Player2 completed 1 word and gets 10 point");
											wordtocheckmean=saveMLLword;//SON TAMAMLANAN KELİME BU MU???
											wordcount++;
											completedwords[cwCOUNT]=saveMLLword;cwCOUNT++;
											wordlistSLL.SearchandDel(wordlistSLL, saveMLLword);
											wordlistFromSLL.SearchAndDeleteWord(wordlistFromSLL, lettersMLLwords2[0], saveMLLword);
											boolean question=meanings.question(wordtocheckmean);
											if(question==true) {
												newscore = player2.getScore() + 10;
												player2.setScore(newscore);
												cn.setTextAttributes(new TextAttributes(Color.green, Color.black));
												cn.getTextWindow().setCursorPosition(0, 25);
												System.out.println("Player2 answered correctly and gets 10 point too");
											}
											else
												//do nothing
											break;
										}
									}
								}
								for (int i = 0; i < 15; i++) {
									for (int j = 0; j < 15; j++) {
										if(saveboard2.getElement(i,j)!=null) {
											gameboard.addElement(saveboard2.getElement(i, j), j);
											// wordSI2 wordEI2 wordHV
											if (wordHV1 == 0 && wordSI1 < j && wordEI1 >= j&&(saveword2[0][4])!=null) {
												saveboard.setElement(Integer.parseInt(saveword2[0][4]), j,
														saveboard2.getElement(i, j));
											} else if (wordHV1 == 1 && wordSI1 < i && wordEI1 >= i&&saveword2[0][4]!=null) {
												saveboard.setElement(i, Integer.parseInt(saveword2[0][4]),
														saveboard2.getElement(i, j));
											}
										}
										else {
											gameboard.addElement(saveboard.getElement(i, j), j);
											// wordSI2 wordEI2 wordHV
											if (wordHV2 == 0 && wordSI2 < j && wordEI2 >= j) {
												saveboard2.setElement(Integer.parseInt(saveword1[0][4]), j,
														saveboard.getElement(i, j));
											} else if (wordHV2 == 1 && wordSI2 < i && wordEI2 >= i) {
												saveboard2.setElement(i, Integer.parseInt(saveword1[0][4]),
														saveboard.getElement(i, j));
											}
										}
									}
								}
								
							}

							wordcomplete = false;
						}
					}

				}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				keypr = 0; // last action
			}
			if(gameboard.equals(wordsfromsolution)) {
				game=false;
			}
			
		showWordList(wordlistFromSLL,completedwords);
		Thread.sleep(10);
		
	//	highscore.writeToFile(); //Cancelled due to some errors
		}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////GAME END          
      
		
		
	
		highscore.Add(player1.getName(), player1.getScore());	//KANKA BUNLARIN OYUN BİTİNCE DEVREYE GİRMESİ GEREKİYO O ŞEKİLDE KOYSANA BUNLARI 
		highscore.Add(player2.getName(), player2.getScore());
		highscore.display();
	}// main parantezi
	
	
}
