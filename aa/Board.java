package aa;


import java.awt.Color;
import enigma.console.TextAttributes;
import enigma.core.Enigma;

public class Board {
	private String[][] element;
	private int rowcount=0;
	private int boardnumber=0;
	public static TextAttributes text=new TextAttributes(Color.white,Color.black);
	public static enigma.console.Console cn=Enigma.getConsole("DEU PUZZLE",150,40,13,0);
	public Board(){
		this.element = new String[15][15];			
		}
	public void addElement(String data,int column) {	
		element[rowcount][column]=data;
		if(column==14)rowcount++;	
	}
	public void display() {
		boardnumber=0;
		System.out.print(" ");
		cn.setTextAttributes(new TextAttributes(Color.white, Color.black));
		for (int i = 0; i < element.length; i++) {
			System.out.print(boardnumber);
			boardnumber++;
			if(boardnumber==10)boardnumber=0;
		}
		System.out.println(" ");
		boardnumber=0;
		for (int i = 0; i < element.length; i++) {
			cn.setTextAttributes(new TextAttributes(Color.white, Color.black));
			System.out.print(boardnumber);
			boardnumber++;			
			if(boardnumber==10)boardnumber=0;
			for (int j = 0; j < element.length; j++) {
				cn.setTextAttributes(new TextAttributes(Color.black, Color.white));
				if(element[i][j].equals("█")) {
					cn.setTextAttributes(new TextAttributes(Color.black, Color.black));
					System.out.print(element[i][j]);
				}
				
				else if(element[i][j].equals("1")) {	
					cn.setTextAttributes(new TextAttributes(Color.black, Color.white));
					System.out.print(" ");
				}
				else {					
					cn.setTextAttributes(new TextAttributes(Color.black, Color.white));
					System.out.print(element[i][j]);
				}
				
			}
			cn.setTextAttributes(new TextAttributes(Color.black, Color.black));
			System.out.println(" ");
		}
		System.out.println("                ");//for clear under board player place mark
	}
	public String getElement(int row,int column) {
		return element[row][column];
	}
	public void setElement(int row,int column,String newelement) {
		element[row][column]=newelement;
	}
}
