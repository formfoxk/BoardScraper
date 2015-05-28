package kumoh.sig.boardwebcrawler.model.logic;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import kumoh.sig.boardwebcrawler.model.data.XmlNode;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/** 
* @FileName    	: XmlFileProcessor.java 
* @Project    	: BoardWebCrawler 
* @Date       	: 2015. 5. 23. 
* @작성자     	: YuJoo 
* @프로그램 설명		: Xml파일의 입출력과 관련된 일을 처리하는 클레스
* @프로그램 기능		:
* @변경이력		:  
*/
public class XmlFileProcessor {
	/** 
	* @Method Name	: importXmlFileOfScraperTable 
	* @Method 설명    	: XmlFile을 읽어 ScraperTable에 내용을 채운다.
	* @변경이력      	:
	* @param file
	* @param table
	* @param nameAndAuthor 
	*/
	public void importXmlFileOfScraperTable(File file, JTable table, String[] subContent){
		try {
			// Doucument를 얻는다.
			DocumentBuilderFactory docBuildFact = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuild = docBuildFact.newDocumentBuilder();
			Document doc = docBuild.parse(file);
			doc.getDocumentElement().normalize();			
	
			// TableName 태그의 값을 얻는다.
			Element nameElement = (Element)doc.getElementsByTagName("tableName").item(0);
			Node nameNode = nameElement.getFirstChild();
			String name = nameNode.getNodeValue();
			subContent[0] = name;
			
			// author 태그의 값을 얻는다.
			Element authorElement = (Element)doc.getElementsByTagName("author").item(0);
			Node authorNode = authorElement.getFirstChild();
			String author = authorNode.getNodeValue();
			subContent[1] = author;
			
			// author 태그의 값을 얻는다.
			Element urlElement = (Element)doc.getElementsByTagName("url").item(0);
			Node urlNode = urlElement.getFirstChild();
			String url = urlNode.getNodeValue();
			subContent[2] = url;
			
			// table의 모델을 얻어 온다.
			DefaultTableModel model = (DefaultTableModel)table.getModel();
			// table의 데이터 모두 삭제
			for (int i = model.getRowCount()-1; i >= 0; i--) {
				model.removeRow(i);	
			}			
			
			// 테이블의 행값을 XML파일에서 읽어들고 테이블 구축 
			NodeList rows = doc.getElementsByTagName("row");	
			for (int i = 0; i < rows.getLength(); i++) {
				Node personNode = rows.item(i);
 
				if (personNode.getNodeType() == Node.ELEMENT_NODE) {
					Vector<Object> row = new Vector<Object>();
					
					// person엘리먼트 
					Element currentElement = (Element) personNode;
 
					// name 태그
					Element checkElement = (Element)currentElement.getElementsByTagName("check").item(0);
					Node checkNode = checkElement.getFirstChild();
					String check = checkNode.getNodeValue();
 
					// description 태그
					Element descriptionElement = (Element)currentElement.getElementsByTagName("description").item(0);
					Node descriptionNode = descriptionElement.getFirstChild();
					String description = descriptionNode.getNodeValue();
 
					// cssSelector 태그
					Element cssSelectorElement= (Element)currentElement.getElementsByTagName("cssSelector").item(0);
					Node cssSelectorNode = cssSelectorElement.getFirstChild();
					String cssSelector = cssSelectorNode.getNodeValue();
					
					row.add(Boolean.valueOf(check).booleanValue());
					row.add(description);
					row.add(cssSelector);

					model.addRow(row);
				}
			}
 
		}catch (NullPointerException e) {
			System.out.println("XmlFileProcessor/importXmlFileOfScraperTable : NullPointerException");
		}catch (Exception e) {
			e.printStackTrace();
		}
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
	public void exportXmlFileOfScraperTable(String path, JTable table, String tableName, String author, String url){
		try {
			// Document를 얻는다.
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			
			Element rootElement = null;
			Element oneLevelChildElement = null;
			Element twoLevelChildElement = null;
			
			// Root
			rootElement = doc.createElement("ScraperTable");
			doc.appendChild(rootElement);

			// RootElement의 자식노드로 ScraperTableName과 Author 저장
			oneLevelChildElement = doc.createElement("tableName");
			oneLevelChildElement.appendChild(doc.createTextNode(tableName));
			rootElement.appendChild(oneLevelChildElement);
			
			oneLevelChildElement = doc.createElement("author");
			oneLevelChildElement.appendChild(doc.createTextNode(author));
			rootElement.appendChild(oneLevelChildElement);
			
			oneLevelChildElement = doc.createElement("url");
			oneLevelChildElement.appendChild(doc.createTextNode(url));
			rootElement.appendChild(oneLevelChildElement);
			
			// RootElement의 자식노드로 ScraperTable 정보 저장
			
			// table의 모델을 얻는다.
			DefaultTableModel dtm = (DefaultTableModel) table.getModel();
			// table의 행 수 
			int rowCount = dtm.getRowCount();
			
			for (int i = 0; i < rowCount; i++) {
				String check = null, description = null, cssSelector = null; 
				
				check = String.valueOf((boolean) dtm.getValueAt(i, 0));
				description = (String) dtm.getValueAt(i, 1);
				cssSelector = (String) dtm.getValueAt(i, 2);
				
				// LEVEL1의 노드로 row Element 생성
				oneLevelChildElement = doc.createElement("row");
				rootElement.appendChild(oneLevelChildElement);
				
				//check Element
				twoLevelChildElement = doc.createElement("check");
				twoLevelChildElement.appendChild(doc.createTextNode(check));
				oneLevelChildElement.appendChild(twoLevelChildElement);
				//description Element
				twoLevelChildElement = doc.createElement("description");
				twoLevelChildElement.appendChild(doc.createTextNode(description));
				oneLevelChildElement.appendChild(twoLevelChildElement);
				//cssSelector Element
				twoLevelChildElement = doc.createElement("cssSelector");
				twoLevelChildElement.appendChild(doc.createTextNode(cssSelector));
				oneLevelChildElement.appendChild(twoLevelChildElement);
			}
			
			// 내용을 XML파일로 변환
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(
					new File(path));
			transformer.transform(source, result);
			
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}
	
	/** 
	* @Method Name	: exportResultAsXmlFile 
	* @Method 설명    	: 최종 결과를 Xml파일로 출력한다.
	* @변경이력      	:
	* @param path
	* @param xmlNodesList 
	*/
	public void exportResultAsXmlFile(String path, List<LinkedList<XmlNode>> xmlNodesList){
		try {
			// Document를 얻는다.
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			
			Element rootElement = null;
			Element oneLevelChildElement = null;
			Element twoLevelChildElement = null;
			
			// Root
			rootElement = doc.createElement("documents");
			doc.appendChild(rootElement);
			
			// RootElement의 자식노드로 ScraperTable 정보 저장
			
			
			for (LinkedList<XmlNode> xmlNodes : xmlNodesList) {				
				// LEVEL1의 노드로 row Element 생성
				oneLevelChildElement = doc.createElement("document");
				rootElement.appendChild(oneLevelChildElement);
				
				for(XmlNode node : xmlNodes){
					//Tag Element
					twoLevelChildElement = doc.createElement(node.getTag());
					twoLevelChildElement.appendChild(doc.createTextNode(node.getContent()));
					oneLevelChildElement.appendChild(twoLevelChildElement);
				}
			}
			
			// 내용을 XML파일로 변환
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(
					new File(path));
			transformer.transform(source, result);
			
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}
}
