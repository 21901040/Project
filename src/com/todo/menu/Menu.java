package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
        
        System.out.println("원하시는 작업을 입력하시오(도움말 - help) >");
    }
    public static void prompt()
    {	
    	System.out.println();
    	System.out.println("< ToDoList 관리 명령어 사용법 >");
        System.out.println("1. [ 새로운 스케줄 추가 ]-> add ");
        System.out.println("2. [ 기존 스케줄 삭제 ]-> del ");
        System.out.println("3. [ 기존 스케줄 수정 ]-> edit ");
        System.out.println("4. [ 모든 스케줄 보기 ]-> ls ");
        System.out.println("5. [ 제목순으로 보기 ]-> ls_name_asc ");
        System.out.println("6. [ 제목역순으로 보기 ]-> ls_name_desc ");
        System.out.println("7. [ 날짜와 시간순으로 보기 ]-> ls_date ");
        System.out.println("8. [ 날짜와 시간순으로 보기 ]-> ls_date_desc ");
        System.out.println("9. [ 단어로 제목/내용 찾기 ]-> find (단어를 적으시오) ");
        System.out.println("10. [ 단어로 카테고리 찾기 ]-> find_cate (단어를 적으시오) ");
        System.out.println("11. [ 도움말 ]-> help ");
        System.out.println("12. [ 나가기 ]-> exit ");
        System.out.println();
    	//System.out.print("\n원하시는 작업을 입력하시오 >");
    }
}
