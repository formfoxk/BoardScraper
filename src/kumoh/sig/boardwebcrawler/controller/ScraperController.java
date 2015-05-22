package kumoh.sig.boardwebcrawler.controller;

import javax.swing.JTree;

import kumoh.sig.boardwebcrawler.model.logic.HtmlDocumetParser;
import kumoh.sig.boardwebcrawler.model.logic.JTreeProcesser;

import org.jsoup.nodes.Element;

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
