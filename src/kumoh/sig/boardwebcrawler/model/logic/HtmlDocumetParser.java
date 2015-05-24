package kumoh.sig.boardwebcrawler.model.logic;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;

import kumoh.sig.boardwebcrawler.model.data.UserMutableTreeNode;

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
		// HtmlPage를 얻는다.
		HtmlPage page = getPage(url);
		
		// url을 통해 웹문서를 파싱하고 CssSelector와 일치하는 노드를 얻는다.
		HtmlElement element = getElement(page, cssSelector);
		
		// OnClick 속성을 얻는다.
		String onClkAttr = element.getOnClickAttribute();
		
		// OnClick함수가 존재 하지 않는 경우
		if(onClkAttr.isEmpty())
			return false;
		else
			return true;	
	}
	
	/** 
	* @Method Name	: getNodes 
	* @Method 설명    	: 
	* 	1. CssSelector를 정규식으로 변경
	* 	2. Tree를 순회하면서 CssSelector 정규식과 일치하는 Node들을 List에 저장한다.
	*   3. List를 반환 한다.
	* @변경이력      	:
	* @param tree
	* @param CssSelector
	* @return 
	*/
	public List<UserMutableTreeNode> getNodes(JTree tree, String cssSelector){
		List<UserMutableTreeNode> nodeList = new LinkedList<UserMutableTreeNode>();
		
		// Tree의 최상위 노드를 구한다.
		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
		UserMutableTreeNode root = (UserMutableTreeNode) model.getRoot();

		// CssSelector 정규식을 얻는다.
		String regexCssSelector = getRegexCssSelector(cssSelector);
		// 전위 순회
		Enumeration e = root.depthFirstEnumeration();
		
		while (e.hasMoreElements()) {
			// 현재 트리 노드
			UserMutableTreeNode currNode = null;
			currNode = (UserMutableTreeNode) e.nextElement();
			
			// 정규식과 일치 하는 경우
			if(isCheckRex(regexCssSelector, currNode.getCssSelector()))
				nodeList.add(currNode);			
		}
		
		return nodeList;
	}
	
	/** 
	* @Method Name	: getRegexCssSelector 
	* @Method 설명    	: cssSelctor를 정규식으로 변경
	* @변경이력      	:
	* @param cssSelector 
	*/
	private String getRegexCssSelector(String cssSelector){
		int i = cssSelector.length()-1;
		int strSize = cssSelector.length()-1;
		
		boolean isBeforeNum = false;
		boolean isCheckFirst = true;
		
		// 자를 문자열의 범위
		int firstIndex = -1, lastIndex = -1;
		while(i >= 0){
			// 문자 타입을 정수 타입으로 캐스팅
			char ch = cssSelector.charAt(i);
			int castingChar = ch-'0';
			

			// 1~9사이의 첫 숫자이고 다음 문자가 ')'인 경우
			if((castingChar >= 0 && castingChar < 10)
					&& (i < strSize-2)
					&& cssSelector.charAt(i+1) == ')'
					&& isCheckFirst 
					&& !isBeforeNum){
				isBeforeNum = true;
				isCheckFirst = false;
				firstIndex = i;
				lastIndex = i+1;
				i--;
				continue;
			}
			
			// 1~9사이의 숫자이고 이전의 문자가 숫자인 경우
			if(castingChar >= 0 && castingChar < 10 && !isCheckFirst && isBeforeNum){
				firstIndex = i;
				i--;
				continue;
			}
			
			// 이전의 문자가 숫자이고 현재 문자인 경우
			if(ch == '(' && !isCheckFirst && isBeforeNum){
				firstIndex = i;
				break;
			}
			
			i--;
		}
		// 숫자가 없는 경우
		if(firstIndex == -1 || lastIndex == -1) 
			return null;
		
		StringBuffer sb = new StringBuffer(cssSelector);
		// 지정된 범위의 숫자를 삭제한다.
		sb.delete(firstIndex, lastIndex+1);
		// 삭제된 숫자 위치에 정규식을 삽입한다.
		sb.insert(firstIndex, "\\([0-9]+\\)");
		
		return sb.toString();
	}
	
	/** 
	* @Method Name	: isCheckRex 
	* @Method 설명    	: 정규식과 데이터가 매치 된다면 True 아니면 False
	* @변경이력      	:
	* @param rex
	* @param data
	* @return 
	*/
	private boolean isCheckRex(String rex, String data) {
		Matcher match = null;

		try {
			Pattern numP = Pattern.compile(rex);
			match = numP.matcher(data);
		} catch (PatternSyntaxException e) {
			e.getStackTrace();
		}
		return match.find();
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
		// Page를 얻어 온다.
		HtmlPage page = getPage(url);
		
		// url을 통해 웹문서를 파싱하고 CssSelector와 일치하는 노드를 얻는다.
		HtmlElement element = getElement(page, cssSelector);
		
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
	* @Method 설명    	: node의 Href 값을 NodeList에 저장
	* @변경이력      	:
	* @param nodeList
	* @return 
	*/
	public List<String> getUrls(List<UserMutableTreeNode> nodeList){
		// 리스트 인스턴스 생성
		List<String> nodes = new LinkedList<String>();
		
		for(UserMutableTreeNode node : nodeList)
			nodes.add(node.getHref());
		
		return nodes;
	}
	
	/** 
	* @Method Name	: getUrls 
	* @Method 설명    	: Javascript::onclick함수의 이벤트를 발생 시켜 웹페이지를 파싱한 후 url주소를 얻는 함수
	* @변경이력      	:
	* @param url
	* @param cssSelectorList
	* @return 
	*/
	public List<String> getUrls(String url, List<UserMutableTreeNode> nodeList) {
		// url주소들을 저장할 연결리스트 생성
		List<String> urlList = new LinkedList<String>();
		// Thread의 결과값을 저장할 연결리스트 생성
		List<Future<String>> resultList = new LinkedList<Future<String>>();
		
		// ThreadPool 생성
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
		
		// Urls를 구한다.
		for (UserMutableTreeNode node : nodeList) {
			// 노드의 CssSelector를 얻는다.
			String cssSelector = node.getCssSelector();
			
			// Url을 파싱하는 클레스 생성
			UrlParser urlParser = new UrlParser(url, cssSelector);
			
			// url을 파싱하여 얻는다.
			Future<String> result = executor.submit(urlParser);
			
			// element를 List에 저장한다.
			resultList.add(result);
		}
		
		for(Future<String> future: resultList){
            try
            {
            	// 얻어온 Url주소를 List에 저장한다.
            	String newUrl = future.get();
            	
            	// 현재 URL과 추출된 URL이 같지 않은 경우에만 List에 추가
            	if(!newUrl.equals(url))
            		urlList.add(future.get()); 
            }
            catch (InterruptedException | ExecutionException e)
            {
                e.printStackTrace();
            }
        }
        //executor의 서비스를 종료 한다.
        executor.shutdown();
    
        return urlList;
	}
	
	/** 
	* @Method Name	: getHtmlPage 
	* @Method 설명    	: HtmlUnit라이브러리의 HtmlPage를 얻어오는 함수
	* @변경이력      	:
	* @param url
	* @return 
	*/
	public HtmlPage getHtmlPage(String url) {
		// Page를 얻어 온다.
		HtmlPage page = getPage(url);

		return page;
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
	private HtmlElement getElement(HtmlPage page, String cssSelector){
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
	
	/** 
	* @Method Name	: getPage 
	* @Method 설명    	: url을 통해 HTML Page를 얻는 함수
	* @변경이력      	:
	* @param url
	* @return 
	*/
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
	
	private class UrlParser implements Callable<String>
	{
		private String url;
	    private String cssSelector;
	 
	    public UrlParser(String url, String cssSelector) {
	    	this.url = url;
	        this.cssSelector = cssSelector;
	    }
	 
	    @Override
	    public String call() throws Exception {
	    	HtmlPage page = getPage(url);
	    	
	    	// url을 통해 웹문서를 파싱하고 CssSelector와 일치하는 노드를 얻는다.	    	
			HtmlElement element = getElement(page, cssSelector);
			
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
	}
}
