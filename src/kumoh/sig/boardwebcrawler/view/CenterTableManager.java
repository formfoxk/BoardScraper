/**
 * 
 */
package kumoh.sig.boardwebcrawler.view;

import java.awt.Component;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.ToolTipManager;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;


/** 
* @FileName    	: CenterTableManager.java 
* @Project    	: BoardWebCrawler 
* @Date       	: 2015. 5. 23. 
* @작성자     	: YuJoo 
* @프로그램 설명 	: Table Model과 CellRenderer를 관리
* @프로그램 기능 	: Table을 갱신(reloadTable)
* @변경이력		:  
*/
class CenterTableManager {
	/**
	 *  
	 * 
	 * @Method Name : setHeaderProperty 
	 * @Method 기능 : 테이블 헤더의 프로파티를 설정
	 * @변경이력 : 
	 * @param header
	 *             
	 */
	public void setHeaderProperty(JTableHeader header) {
		// 테이블 헤더에 툴팁 설정
		header.setToolTipText("마우스로 사이즈를 변경하세요");
		// 컬럼을 마우스로 이동 못하게
		header.setReorderingAllowed(false);
	}

	/**
	 *  
	 * 
	 * @Method Name : setToolTip 
	 * @Method 기능 : 툴팁정보를 설정함
	 * @변경이력 :  
	 */
	public void setToolTip() {
		ToolTipManager ttm = ToolTipManager.sharedInstance();
		ttm.setEnabled(true); // 툴팁을 활성화 함
		ttm.setInitialDelay(0); // 보여지기 까지의 시간(0은 바로 보여라)
		ttm.setDismissDelay(3000); // 표시한 후 사라지기 까지의 지연시간
	}

	/**
	 *  
	 * 
	 * @Method Name : reloadTable 
	 * @Method 기능 : 테이블 데이타를 갱신함
	 * @변경이력 : 
	 * @param table
	 * @param data
	 *             
	 */
	public void reloadTable(JTable table, Vector<Vector<Object>> data) {		
		
		// 테이블의 새로운 모델을 생성
		CenterTableModel model = new CenterTableModel();
		
		// 새로운 모델에 데이터를 추가
		model.addTableData(data);

		// 새로운 모델을 테이블에 설정 한다
		table.setModel(model);

		// 새로운 모델의 열 크기를 설정 한다.
		initColumnSizes(table);
		
		// 새로운 모델의 Cell들을 설정 한다.
		initCellRenderer(table);
	}
	
	private void initColumnSizes(JTable table) {
		
		// TableModel 반환
		CenterTableModel model = (CenterTableModel) table.getModel();
		
		// TableCellRenderer 반환
		TableCellRenderer headerRenderer = table.getTableHeader()
				.getDefaultRenderer();
		
		// TableColumn, Component, headerWidth, cellWidth
		TableColumn column = null;
		Component comp = null;
		int headerWidth = 0;
		int cellWidth = 0;
		
		/**
		  HardCording 
		 */
		// longValues 설정
		Object[] longValues = {"###############", "###############", "###############", "###############"};
		
		// 열 사이즈
		int columnSize = model.getColumnCount();
		for (int i = 0; i < columnSize; i++) {
			column = table.getColumnModel().getColumn(i);
			comp = headerRenderer.getTableCellRendererComponent(null,
					column.getHeaderValue(), false, false, 0, 0);
			
			headerWidth = comp.getPreferredSize().width;

			comp = table.getDefaultRenderer(model.getColumnClass(i))
					.getTableCellRendererComponent(table, longValues[i], false,
							false, 0, i);
			cellWidth = comp.getPreferredSize().width;

	

			column.setPreferredWidth(Math.max(headerWidth, cellWidth));
		}
	}
	private void initCellRenderer(JTable table){
		// TableModel 반환
		CenterTableModel model = (CenterTableModel) table.getModel();
		
		// 새로운 모델의 컬럼수를 취득
		int columnSize = model.getColumnSize();
		
		// 새모델을 설정후, 컬럼을 재설정
				DefaultTableColumnModel dtcm = (DefaultTableColumnModel) table
								.getColumnModel();
				for (int i = 0; i < columnSize; i++) {
					// i행의 tableColumn 객체 저장
					TableColumn tc = dtcm.getColumn(i);
					
					// Renderer 설정
					/**
					  HardCording 
					 */
					if (i == 2) {
						tc.setCellRenderer(new TableHoriRightAlignCellRenderer());
					} else if (i != 0) {
						tc.setCellRenderer(new TableHoriCenterAlignCellRenderer());
					}
					tc.setHeaderRenderer(new TableHeaderCellRenderer());
				}
		
	}
}
