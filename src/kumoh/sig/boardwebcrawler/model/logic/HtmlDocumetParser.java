package kumoh.sig.boardwebcrawler.model.logic;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

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
