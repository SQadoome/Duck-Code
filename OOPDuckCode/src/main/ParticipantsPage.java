package main;

import java.util.Arrays;

import prettyText.*;
import prettyText.Styles.*;

public class ParticipantsPage extends Page {

	private String[] participants;
	
	public ParticipantsPage(String[] participants) {
		super(new BoxStyle(52));
		//System.out.println(Arrays.toString(participants));
		
		this.participants = radixSort(participants, 0);
		show();
	}
	
	private String[] radixSort(String words[], int target) {
		String[][] temp = new String[26][words.length];
		int[] count = new int[26];
		int count2 = 0;
		
		for (int i = 0; i < words.length; i++) {
			if (words[i] == null) {
				break;
			}
			
			if (target > words[i].length() - 1) {
				String bucket = words[0];
				words[0] = words[i];
				words[i] = bucket;
				count2++;
				continue;
			}
			
			int atIndex = (Character.toLowerCase(words[i].charAt(target)) - '0') - 49;
			temp[atIndex][count[atIndex]] = words[i];
			count[atIndex] += 1;
			
		}
		
		for (int i = 0; i < 26; i++) {
			if (count[i] > 1) {
				temp[i] = radixSort(temp[i], target+1);
			}
			
			for (int j = 0; j < words.length; j++) {
				if (temp[i][j] == null) {
					break;
				} else {
					words[count2] = temp[i][j];
					count2++;
				}
				
				
			}
			
		}
		return words;
	}
	
	public void show() {
		System.out.println(AnsiValues.BOLD + "                   -Participants-" + AnsiValues.DEFAULT);
		String[] lines = new String[8];
		
		int lineCount = 0;
		// set empty slots
		for (int i = 0; i < 16; i +=2 ) {
			lines[lineCount] = (i+1) + "- " + "                   ";
			if (i + 1 < 10) {
				lines[lineCount] += " "; // 10-        11- (the extra digit offsets)
			}
			lines[lineCount] += (i + 2) + "- ";
			lineCount++;
		}
		
		lineCount = 0;
		// fill slots with particiapnts
		for (int i = 0; i < participants.length; i += 2) {
			lines[lineCount] = (i+1) + "- " + participants[i] + " ".repeat(8 + (12 - participants[i].length()));
			lines[lineCount] += (i + 2) + "- ";
			if (i + 1 < participants.length) { // make sure the second current participant actually exists
				lines[lineCount] += participants[i + 1];
			}	
			lineCount++;
		}
		
		printParagraph(lines);
		System.out.println(AnsiValues.BOLD + "register -> to participate | unregister -> to forfeit | back -> go back" + AnsiValues.DEFAULT);
		
	}
}
