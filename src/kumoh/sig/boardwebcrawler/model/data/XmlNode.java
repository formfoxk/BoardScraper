package kumoh.sig.boardwebcrawler.model.data;


/** 
* @FileName    	: XmlNode.java 
* @Project    	: BoardWebCrawler 
* @Date       	: 2015. 5. 26. 
* @작성자     	: YuJoo 
* @프로그램 설명		: Xml파일에 저장될 태그와 태그의 내용을 가지는 클레스
* @프로그램 기능		:
* @변경이력		:  
*/
public class XmlNode{
	private String tag = null;
	private String content = null;
	
	public XmlNode(String tag, String content){
		this.tag = tag;
		this.content = content;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}