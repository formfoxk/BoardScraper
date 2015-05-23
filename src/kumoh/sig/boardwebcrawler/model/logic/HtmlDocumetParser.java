package kumoh.sig.boardwebcrawler.model.logic;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.w3c.dom.Node;

import se.fishtank.css.selectors.Selectors;
import se.fishtank.css.selectors.dom.W3CNode;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @FileName : HtmlDocumetParser.java
 * @Project : BoardWebCrawler
 * @Date : 2015. 5. 22.
 * @작성자 : YuJoo
 * @프로그램 설명 : URL을 입력 받아 HTML문서를 파싱하는 클레스
 * @프로그램 기능 : 정적 초기화 방식의 싱글톤 패턴 사용
 * @변경이력 :
 */
public class HtmlDocumetParser {

	private volatile static HtmlDocumetParser hdpInstance;

	private HtmlDocumetParser() {
	}

	/** 
	* @Method Name	: getDocument 
	* @Method 설명    	: HTML 문서를 얻는다.
	* @변경이력      	:
	* @param url
	* @return 
	*/
	public Element getDocument(String url) {
		Document doc = null;

		try {
			doc = Jsoup
					.connect(url)
					.header("Accept-Encoding", "gzip, deflate")
					.header("Content-Type", "text/html; charset=utf-8")
					.userAgent(
							"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36")
					.timeout(100 * 1000).get();
		} catch (Exception e) {
			return null;
		}

		return doc.body();
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
		// url을 통해 웹문서를 파싱하고 CssSelector와 일치하는 노드를 얻는다.
		HtmlElement element = getElement(url, cssSelector);
		
		// OnClick 속성을 얻는다.
		String onClkAttr = element.getOnClickAttribute();
		
		// OnClick함수가 존재 하지 않는 경우
		if(onClkAttr.isEmpty())
			return false;
		else
			return true;	
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
		// url을 통해 웹문서를 파싱하고 CssSelector와 일치하는 노드를 얻는다.
		HtmlElement element = getElement(url, cssSelector);
		
		// 노드를 클릭하여 
		HtmlPage newPage = null;
		try {
			newPage = element.click();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// url을 얻는다.
		String newUrl = newPage.getUrl().toString();
		
		return newUrl;
	}
	
	/** 
	* @Method Name	: getUrls 
	* @Method 설명    	: cssSelector들과 일치하는 Element들을 구해 반환하는 함수
	* @변경이력      	:
	* @param url
	* @param cssSelectorList
	* @return 
	*/
	public List<HtmlElement> getUrls(String url, List<String> cssSelectorList) {
		// Html Element를 저장할 연결리스트 생성
		List<HtmlElement> elementList = new LinkedList<HtmlElement>();

		// Page를 얻어 온다.
		HtmlPage page = getPage(url);

		// Selectors 객체 생성
		Selectors selectors = new Selectors(new W3CNode(page));

		// Urls를 구한다.
		for (String cssSelector : cssSelectorList) {
			// CssSelector와 일치하는 Node를 구한다.
			Node node = (Node) selectors.querySelector(cssSelector);

			// Xpath를 얻어 온다.
			String xpath = ((HtmlElement) node).getCanonicalXPath().toString();

			// xPath와 일치하는 HtmlElement를 얻는다.
			HtmlElement element = (HtmlElement) page.getByXPath(xpath).get(0);

			// element를 List에 저장한다.
			elementList.add(element);
		}

		return elementList;

	}
	
	/** 
	* @Method Name	: getElement 
	* @Method 설명    	: 
	* 	1. Jsoup라이브러리의 CssSelector를 fishtank.css라이버러리를 사용하여 XPath로 변환 
	* 	2. HtmlUnit라이브러리를 사용하여 이전에 구한 XPath와 일치하는 Element를 얻은 후 반환
	* @변경이력      	:
	* @param url
	* @param cssSelector
	* @return 
	*/
	private HtmlElement getElement(String url, String cssSelector){
		// Page를 얻어 온다.
		HtmlPage page = getPage(url);
		
		// Selectors 객체 생성
		Selectors selectors = new Selectors(new W3CNode(page));
		
		// CssSelector와 일치하는 Node를 구한다.
		Node node = (Node) selectors.querySelector(cssSelector);
		
		// Xpath를 얻어 온다.
		String xpath = ((HtmlElement)node).getCanonicalXPath().toString();	
		
		// xPath와 일치하는 HtmlElement를 얻는다.
		HtmlElement element = (HtmlElement)page.getByXPath(xpath).get(0);
		
		return element;
	}
	
	private HtmlPage getPage(String url){
		// WebClient를 얻는다.
		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		
		// Page를 얻어 온다.
		HtmlPage page = null;
		try {
			page = webClient.getPage(url);
		} catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return page;
	}
	
	/** 
	* @Method Name	: getInstance 
	* @Method 설명    	: 동기화 + 원자성을 보장하는 getInstance 함수
	* @변경이력      	:
	* @return 
	*/
	public static HtmlDocumetParser getInstance() {
		if (hdpInstance == null) {
			synchronized (HtmlDocumetParser.class) {
				if (hdpInstance == null) {
					hdpInstance = new HtmlDocumetParser();
				}
			}
		}
		return hdpInstance;
	}
}
