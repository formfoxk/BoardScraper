package kumoh.sig.boardwebcrawler.model.data;

import javax.swing.tree.DefaultMutableTreeNode;

public class UserMutableTreeNode extends DefaultMutableTreeNode{

	private static final long serialVersionUID = 1L;
	private String tagName;
	private String content;
	private String cssSelector;
	private String href;
	private String url;
	
	public UserMutableTreeNode(String tagName, String content, String cssSelector, String href, String url){
		this.tagName = tagName;
		this.content = content;
		this.cssSelector = cssSelector;
		this.href = href;
		this.url = url;
	}
	
	public String getUrl() {
		return url;
	}

	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getContent() {
		return content;
	}

	public String getCssSelector() {
		return cssSelector;
	}

	public String getHref() {
		return href;
	}

	public String toString(){
		return tagName;
	}
}
