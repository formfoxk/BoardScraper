package kumoh.sig.boardwebcrawler.controller;

import java.io.File;
import java.util.List;

import javax.swing.JTable;
import javax.swing.JTree;

import kumoh.sig.boardwebcrawler.model.data.UserMutableTreeNode;
import kumoh.sig.boardwebcrawler.model.logic.HtmlDocumetParser;
import kumoh.sig.boardwebcrawler.model.logic.JTreeProcesser;
import kumoh.sig.boardwebcrawler.model.logic.XmlFileProcessor;

import org.jsoup.nodes.Element;

import com.gargoylesoftware.htmlunit.html.HtmlElement;

/** 
* @FileName    	: ScraperController.java 
* @Project    	: BoardWebCrawler 
* @Date       	: 2015. 5. 22. 
* @작성자     	: YuJoo 
* @프로그램 설명		: scraper의 로직 함수들을 컨트롤 할 수 있는 클레스(싱글톤 패턴으로 구현)
* @프로그램 기능		:
* @변경이력		:  
*/
public class ScraperController {
	
	private volatile static ScraperController scInstance;
	
	private ScraperController(){}
		
	/** 
	* @Method Name	: getDocument 
	* @Method 설명    	: Html문서를 반환 해주는 함수(싱글톤 패턴)
	* @변경이력      	:
	* @param url
	* @return 
	*/
	public Element getDocument(String url) {
		HtmlDocumetParser hdp = HtmlDocumetParser.getInstance();
		
		return hdp.getDocument(url);
	}
	
	/**
	* @Method Name	: isExist 
	* @Method 설명    	: OnClick함수가 존재 유무의 값을 반환 하는 함수
	* @변경이력      	:
	* @param url
	* @param cssSelector
	* @return 
	*/
	public boolean isExistOnClick(String url, String cssSelector){
		HtmlDocumetParser hdp = HtmlDocumetParser.getInstance();
		
		return hdp.isExistOnClick(url, cssSelector);
	}

	
	/**
	 * @Method Name : getNodes
	 * @Method 설명 : 1. CssSelector를 정규식으로 변경 2. Tree를 순회하면서 CssSelector 정규식과
	 *         일치하는 Node들을 List에 저장한다. 3. List를 반환 한다.
	 * @변경이력 :
	 * @param tree
	 * @param CssSelector
	 * @return
	 */
	public List<UserMutableTreeNode> getNodes(JTree tree, String cssSelector) {
		HtmlDocumetParser hdp = HtmlDocumetParser.getInstance();
		
		return hdp.getNodes(tree, cssSelector);
	}
	
/** 
	* @Method Name	: getUrl 
	* @Method 설명    	: 단일 Url을 얻는 함수
	* @변경이력      	:
	* @param url
	* @param cssSelector
	* @return 
	*/
	public String getUrl(String url, String cssSelector){
		HtmlDocumetParser hdp = HtmlDocumetParser.getInstance();
		
		return hdp.getUrl(url, cssSelector);
	}

/**
	 * @Method Name : getUrls
	 * @Method 설명 : node의 Href 값을 NodeList에 저장
	 * @변경이력 :
	 * @param nodeList
	 * @return
	 */
	public List<String> getUrls(List<UserMutableTreeNode> nodeList) {
		HtmlDocumetParser hdp = HtmlDocumetParser.getInstance();
		
		return hdp.getUrls(nodeList);
	}
		
	/** 
	* @Method Name	: getUrls 
	* @Method 설명    	: Javascript::onclick함수의 이벤트를 발생 시켜 웹페이지를 파싱한 후 url주소를 얻는 함수
	* @변경이력      	:
	* @param url
	* @param cssSelectorList
	* @return 
	*/
	public List<String> getUrls(String url, List<UserMutableTreeNode> nodeList){
		HtmlDocumetParser hdp = HtmlDocumetParser.getInstance();
		
		return hdp.getUrls(url, nodeList);
	}
	
	/** 
	* @Method Name	: buildTree 
	* @Method 설명    	: 트리를 구축하는 함수
	* @변경이력      	:
	* @param url 
	*/
	public void buildTree(JTree tree, Element document){
		JTreeProcesser jp = new JTreeProcesser();
		
		jp.buildTree(tree, document);
	}
	
	/** 
	* @Method Name	: searchTreeNode 
	* @Method 설명    	: 검색 텍스트 필드에 입력된 값과 일치하는 노드들을 확장시켜주는 함수
	* @변경이력      	: 
	* @param tree
	* @param query
	* @param cbItem 
	*/
	public void searchTreeNode(JTree tree, String query, String cbItem){
		JTreeProcesser jp = new JTreeProcesser();
		jp.searchTreeNode(tree, query, cbItem);
	}
	
	/** 
	* @Method Name	: importXmlFileOfScraperTable 
	* @Method 설명    	: XmlFile을 읽어 ScraperTable에 내용을 채운다.
	* @변경이력      	:
	* @param file
	* @param table
	* @param nameAndAuthor 
	*/
	public void importXmlFileOfScraperTable(File file, JTable table, String[] nameAndAuthor){
		XmlFileProcessor xfp = new XmlFileProcessor();
		xfp.importXmlFileOfScraperTable(file, table, nameAndAuthor);
	}
	
	/** 
	* @Method Name	: exportXmlFileOfScraperTable 
	* @Method 설명    	: ScraperTable의 내용을 XmlFile로 생성
	* @변경이력      	:
	* @param path
	* @param table
	* @param tableName
	* @param author 
	*/
	public void exportXmlFileOfScraperTable(String path, JTable table, String tableName, String author){
		XmlFileProcessor xfp = new XmlFileProcessor();
		xfp.exportXmlFileOfScraperTable(path, table, tableName, author);
	}
	
	/** 
	* @Method Name	: getInstance 
	* @Method 설명    	: 동기화 + 원자성을 보장하는 getInstance 함수
	* @변경이력      	:
	* @return 
	*/
	public static ScraperController getInstance() {
		if (scInstance == null) {
			synchronized (HtmlDocumetParser.class) {
				if (scInstance == null) {
					scInstance = new ScraperController();
				}
			}
		}
		return scInstance;
	}
}
