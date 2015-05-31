/**
 * 
 */
package kumoh.sig.boardscraper.view;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;


/** 
* @FileName    	: CenterTableModel.java 
* @Project    	: BoardWebCrawler 
* @Date       	: 2015. 5. 23. 
* @작성자     	: YuJoo 
* @프로그램 설명 	: Scrapers의 테이블 모델을 관리
* @프로그램 기능 	:  1. 모델에 컬럼과 데이터를 추가 2. 컬럼 셀들의 편집불가 지정 3. 각 컬럼의 반환 오브젝트 타입을 지점
* @변경이력		:  
*/
class CenterTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;
	
	private int columnSize;

	/** 컬럼의 오브젝트 형태를 동적으로 지정하기위한 벡터 */

	Vector<Object> object = new Vector<Object>();
	
	/** 
	* @Method Name	: getColumnSize 
	* @Method 기능	: 재설정한 컬럼의 수를 최득
	* @변경이력		: 
	* @return 
	*/
	public int getColumnSize() {
		return columnSize;

	}

	/** 
	* @Method Name	: addTableData 
	* @Method 기능	: 테이블 모델에 헤더명과 데이터를 추가한다.
	* @변경이력		: 
	* @param data 
	* 	첫째요소는 컬럼명, 둘째부터는 열 데이터
	*/
	public void addTableData(Vector<Vector<Object>> data) {

		Vector<Object> temp;
		int rowSize, colSize;
		
		// 열의 크기 지정
		columnSize = data.get(0).size();

		rowSize = data.size();
		for (int i = 0; i < rowSize; i++) {
			temp = data.get(i);
			
			// Object의 DataType 초기화
			if (i == 0) {
				// Row Object 저장
				colSize = temp.size();
				
				for (int j = 0; j < colSize; j++) {
					this.addColumn(temp.get(j));
					object.add(String.class);
				}
			} else {
				this.addRow(temp);
			}
		}
	}
	
	/** 
	* @see javax.swing.table.AbstractTableModel#getColumnClass(int)
	* @Method Name	: getColumnClass 
	* @Method 기능	:
	* 	getColumnClass()메소드를 재정의해서
	* 	문자열을 반환하던 것을 클래스 반환으로 바꾼다.
	* 	헤더 추가시 지정한 클래스 형태로 컬럼을 지정한다. 
	* @변경이력		: 
	* @param columnIndex
	* @return 
	*/
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return (Class<?>) (object.get(columnIndex));
	}

}
