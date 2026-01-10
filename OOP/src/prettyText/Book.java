package prettyText;


public class Book {

	private Page[] pages;
	public int activePage;
	
	// constructors
	public Book() {
		
	}
	
	public Book(Page[] pages) {
		this.pages = pages;
		this.activePage = 0;
	}
	// ------
	
	public void setPages(Page[] pages) {
		this.pages = pages;
		activePage = 0;
	}
	
	public Page[] getPages() {
		return pages;
	}
	
	// ------
	public void switchPage(int index) {
		if (index < 0 || index > pages.length) {
			System.out.println(AnsiValues.RED + "Page index out of bounds!" + AnsiValues.DEFAULT);
			return;
		}
		activePage = index;
	}
	
	public void printPageList() {
		int emptySpace = pages[activePage].getStyle().lineLength/2 - 3;
		System.out.println(" ".repeat(emptySpace) + AnsiValues.BOLD + "Page:" + (activePage+1) + "/" + pages.length + AnsiValues.DEFAULT);
	}
	
	public void printActivePage(String[] lines) {
		pages[activePage].printParagraph(lines);
		printPageList();
	}
	
	public void printActivePage(String title, String[] lines) {
		pages[activePage].printParagraph(title, lines);
		printPageList();
	}
	
	public void printActivePage(String[][] paragraphs) {
		pages[activePage].printParagraphs(paragraphs);
		printPageList();
	}
	
}
