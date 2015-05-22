/**
 * 
 */
package kumoh.sig.boardwebcrawler.view;

import javax.swing.JTable;

/**
 *  
 * 
 * @FileName : BaseTable.java 
 * @Project : D445WebCrawler 
 * @Date : 2015. 2. 14. 
 * @작성자 : YuJoo 
 * @프로그램 설명 : JTable을 상속한 유저정의 테이블 
 * @프로그램 기능 : 
 *  1. 테이블의 각 열의 높이를 설정함 
 *  2. 테이블의 셀 속성지정
 * @변경이력 : 
 */
class SouthBaseTable extends JTable {

	private static final long serialVersionUID = 1L;

	// 생성자
	public SouthBaseTable() {

		this.setRowHeight(30);
		this.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

	}

}
