package model;

import java.util.List;

public class PageManager<T> {

	private final int ROW_BY_PAGE = 10;
	
	private List<T> datas;
	private int index;
	private int length;

	////////CONSTRUCTORS///////

	public PageManager() {

	}
	
	public PageManager(List<T> nDatas) {
		this.datas = nDatas;
		this.index = 0;
		this.length = this.datas.size();
	}


	////////SETTER | GETTER////////

	//ROW_BY_PAGE
	public int getRowByPage() {
		return this.ROW_BY_PAGE;
	}
	
	//datas
	public void setDatas(List<T> nDatas) {
		this.datas = nDatas;
		this.length = this.datas.size();
	}

	public List<T> getDatas(){
		return this.datas;
	}

	//index
	public void setIndex(int nIndex) {
		this.index = nIndex;
	}

	public int getIndex(){
		return this.index;
	}

	//length
	@Deprecated //You are not supposed to set length manually
	public void setLength(int nLength) {
		this.length = nLength;
	}

	public int getLength(){
		return this.length;
	}
	
	
	////////ACTIONS////////
	
	public int getNextPageIndex() {
		return Math.min(this.index + this.ROW_BY_PAGE, this.length);
	}
	public void next() {
		this.index = this.getNextPageIndex();
	}
	
	public int getPreviousPageIndex() {
		return Math.max(this.index - this.ROW_BY_PAGE, 0);
	}
	public void previous() {
		this.index = this.getPreviousPageIndex();
	}
	
	public List<T> getCurrentPage(){
		return this.datas.subList(this.index, Math.min(this.index + this.ROW_BY_PAGE, this.length));
	}
	
	////////PRINT////////
	public void printCurrentPage() {
		System.out.println("----------------------");
		
		for(T row : this.getCurrentPage()) {
			System.out.println(row);
		}
		
		System.out.println("----------------------");
	}
	
	public void printPageActions() {
		System.out.println("__________________________________________\n"
				+ "[PREV]       [STOP | 0]     [NEXT | EMPTY]\n");
	}
}
