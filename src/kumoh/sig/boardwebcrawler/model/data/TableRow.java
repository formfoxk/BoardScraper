package kumoh.sig.boardwebcrawler.model.data;

/** 
* @FileName    	: TableRow.java 
* @Project    	: BoardWebCrawler 
* @Date       	: 2015. 5. 26. 
* @작성자     	: YuJoo 
* @프로그램 설명		: scraper Table의 row값을 저장하는 클레스
* @프로그램 기능		:
* @변경이력		:  
*/
public class TableRow {
	// NodeDataExtractionProcesser의 알고리즘에서 사용되는 check변수
	private boolean check;
	private String description;
	private String cssSelector;
	
	public TableRow(boolean check, String description, String cssSelector){
		this.check = check;
		this.description = description;
		this.cssSelector = cssSelector;
	}

	public boolean getCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public String getDescription() {
		return description;
	}

	public String getCssSelector() {
		return cssSelector;
	}
}
