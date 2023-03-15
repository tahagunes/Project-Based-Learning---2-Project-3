package aa;

import java.util.Random;
import java.util.Scanner;

public class Meant {
	private Word[] wordlist;
	private int capacity;
	private int counter;

	public Meant(int capacity) {
		this.capacity=capacity;
		wordlist = new Word[capacity];
		counter = 0;
	}

	public Word[] getWordlist() {
		return wordlist;
	}

	public void setWordlist(Word[] wordlist) {
		this.wordlist = wordlist;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public void addWord(Word dataToAdd) {
		wordlist[counter] = dataToAdd;
		counter++;
	}

	public String getMean(String word) {
		String mean = "";
		for (int i = 0; i < wordlist.length; i++) {
			if (word.equalsIgnoreCase(wordlist[i].getWord())) {
				mean = wordlist[i].getMeaning();

			}

		}
		return mean;
	}

	public boolean question(String word) {
		boolean flag = false;
		Random r = new Random();
		String mean = "";
		int r1 = r.nextInt(wordlist.length);
		int r2 = r.nextInt(wordlist.length);
		String mean2 = wordlist[r1].getMeaning();
		String mean3 = wordlist[r2].getMeaning();
		String answera = "";
		String answerb = "";
		String answerc = "";
		for (int i = 0; i < wordlist.length; i++) {
			if (word.equalsIgnoreCase(wordlist[i].getWord())) {
				mean = wordlist[i].getMeaning();
				break;
			}
		}
		while (mean.equals(mean2) || mean.equals(mean3) || mean2.equals(mean3)) {
			r1 = r.nextInt(wordlist.length);
			r2 = r.nextInt(wordlist.length);
			mean2 = wordlist[r1].getMeaning();
			mean3 = wordlist[r2].getMeaning();
		}
		int a = 0;
		int b = 0;
		int c = 0;
		while (a == b || a == c || b == c) {
			a = r.nextInt(3) + 1;
			b = r.nextInt(3) + 1;
			c = r.nextInt(3) + 1;
		}
		if (a == 1) {
			answera = mean;
		} else if (a == 2) {
			answerb = mean;
		} else if (a == 3) {
			answerc = mean;
		}
		if (b == 1) {
			answera = mean2;
		} else if (b == 2) {
			answerb = mean2;
		} else if (b == 3) {
			answerc = mean2;
		}
		if (c == 1) {
			answera = mean3;
		} else if (c == 2) {
			answerb = mean3;
		} else if (c == 3) {
			answerc = mean3;
		}
		System.out.println("What is the meaning of " + word + " in Turkish? Please enter your option.");
		System.out.println("A) " + answera + "     B) " + answerb + "     C) " + answerc);
		Scanner wordscan = new Scanner(System.in);
		System.out.print("Answer: ");
		String answer = wordscan.nextLine();
		answer = answer.toUpperCase();
		System.out.print(answer);
		if (answer.equals("A")) {
			if (answera.equals(mean))
				flag = true;
		} else if (answer.equals("B")) {
			if (answerb.equals(mean))
				flag = true;
		} else if (answer.equals("C")) {
			if (answerc.equals(mean))
				flag = true;
		}
		return flag;
	}
}
