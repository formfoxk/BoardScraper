/**
 * 
 */
package kumoh.sig.boardwebcrawler.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *  
 * 
 * @FileName : ScrapersTableHeaderCellRenderer.java 
 * @Project : D445WebCrawler 
 * @Date : 2015. 2. 14. 
 * @작성자 : YuJoo 
 * @프로그램 설명 : Scrapers 테이블 헤더에 적용하는 셀 렌더러
 * @프로그램 기능 : 헤더의 배경색등 헤더 속성을 설정
 * @변경이력 : 
 */
class TableHeaderCellRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		setText((String) value);
		setFont(new Font("MS PGothic", Font.BOLD, 15));
		setBackground(new Color(0, 128, 0)); // 헤더 배경색
		setForeground(Color.white); // 헤더 글자 색
		setHorizontalAlignment(SwingConstants.CENTER);
		setBorder(new BevelBorder(BevelBorder.RAISED));

		return this;
	}
}
