package aa;
import java.io.FileWriter;
import java.io.File; // Import the File class

import java.io.FileNotFoundException; // Import this class to handle errors
import java.io.IOException;
import java.util.Scanner; // Import the Scanner class to read text files

public class ScoreTable {
	private Player head;
	private Player tail;

	public ScoreTable() {
		head = null;
		tail = null;
	}

	public void Add(String name, int score) {
		int tempscore = score;
		String tempname = name;

		if (head == null && tail == null) {
			Player newNode = new Player(tempname, tempscore);
			head = newNode;
			tail = newNode;
		} else {
			Player newNode = new Player(tempname, tempscore);
			Player temp = head;
			while (temp.getNext() != null && temp.getNext().getScore() >= score) {
				temp = temp.getNext();
			}
			newNode.setPrevious(temp);
			newNode.setNext(temp.getNext());

			if (temp.getNext() != null)
				temp.getNext().setPrevious(newNode);
			else
				tail = newNode;
			temp.setNext(newNode);
		}

		/*
		 * if (head == null) { newNode = new Player(tempname,tempscore); head = newNode;
		 * tail = newNode; } else { Player toAdd=new Player(name, score); Player
		 * temp=head; while(temp.getNext()!=null&&temp.getNext().getScore()>=score) {
		 * 
		 * temp=temp.getNext(); } if(temp.getNext().equals(null)) { temp.setNext(toAdd);
		 * tail=toAdd; } else { Player temp2=temp.getNext(); temp.setNext(toAdd);
		 * toAdd.setPrevious(temp); toAdd.setNext(temp2); temp2.setPrevious(toAdd); }
		 * 
		 * 
		 * }
		 */
	}

	public void display() {
		Player temp = head;
		int counter = 1;
		System.out.println("\n"+"\n" + "**--HIGH SCORE TABLE--**");
		while (temp != null) {
			System.out.println(counter + "-) " + temp.getName() + "-->> " + temp.getScore());
			counter++;
			temp = temp.getNext();
		}
	}

	public void writeToFile() {
		File myObj = new File("hig_score_table.txt");
		myObj.delete();
		myObj = new File("hig_score_table.txt");
		try {
			myObj.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Player tempp = head;
		int counter = 1;
		while (tempp != null)
			try {
				FileWriter myWriter = new FileWriter("hig_score_table.txt",true);
				while (tempp != null) {
					if (counter == 1) {
						myWriter.write("**--HIGH SCORE TABLE--**" + "\n");
					}
						myWriter.write(tempp.getName() + ";" + tempp.getScore() + "\n");
						if (tempp.getNext() == null)
							{
							break;
							}else
							tempp = tempp.getNext();
						counter++;
					}
					

					

				
				myWriter.close();
	
			} catch (IOException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}
		

	}
}
