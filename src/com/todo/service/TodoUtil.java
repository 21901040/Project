package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;


public class TodoUtil {
	
	
	
	public static void createItem(TodoList list) {
		
		String title, desc, category, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "=====[ 스케줄 추가 ]=====\n"
				+ "카테고리 > ");
	
		
		category = sc.nextLine().trim();

		System.out.println("제목 > ");
		title = sc.nextLine().trim();
		/*
		if (list.isDuplicate(title)) {
			System.out.printf("제목이 중복됩니다!");
			return;
		}
		*/
		System.out.println("내용 > ");
		desc = sc.nextLine().trim();
		
		System.out.println("마감일자 > ");
		due_date = sc.nextLine().trim();
		
		
		TodoItem t = new TodoItem(category, title, desc, due_date);
		list.addItem(t);
		//교수님 코드
		System.out.println("*추가 되었습니다.*");
		System.out.println();
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		/*System.out.println("[항목 추가]\n"
		+ "삭제할 항목의 제목을 입력하시오 > ");
		*/
		
		System.out.println("\n"
				+ "=====[ 스케줄 삭제 ]=====\n"
				+ "삭제할 항목의 번호를 입력하시오 > ");
		int number = sc.nextInt();
		for (TodoItem item : l.getList()) {
				TodoUtil.listChosen(l,number);
				System.out.println("위 항목을 삭제하시겠습니까? (y/n) >");
				char yn = sc.next().charAt(0);
				if(yn=='y') {
					TodoUtil.deleteChosen(l,number);
					System.out.println("*삭제되었습니다.*");
					break;
				}
				else break;
		
		}
		System.out.println();
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "=====[ 스케줄 수정 ]=====\n"
				+ "수정할 항목의 번호를 입력하시오 > ");
		
		int number = sc.nextInt();
		for (TodoItem item : l.getList()) {
				TodoUtil.listChosen(l,number);
				System.out.println("위 항목을 수정하시겠습니까? (y/n) >");
				char yn = sc.next().charAt(0);
				if(yn=='y') {
					
					break;
				}
				else return;
		
		}
		/*
		if (!l.isDuplicate(title)) {
			System.out.println("*없는 제목입니다, 다시 시도해주세요.*");
			return;
		}
		*/
		sc.nextLine();
		System.out.println("새 카테고리 > ");
		String new_category = sc.nextLine().trim();
		
		System.out.println("새 제목 >");
		String new_title = sc.next().trim();
		/*
		if (l.isDuplicate(new_title)) {
			System.out.println("제목이 중복됩니다! 다시 시도해주세요.");
			return;
		}
		*/
		sc.nextLine();
		System.out.println("새 내용 > ");
		String new_description = sc.nextLine().trim();
		
		sc.nextLine();
		System.out.println("새 마감일자 > ");
		String new_duedate = sc.nextLine().trim();
		
		
		int count=0;
		for (TodoItem item : l.getList()) {
			count++;
			if(number==count) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_category, new_title, new_description, new_duedate );
				l.addItem(t);
				System.out.println("스케줄이 수정되었습니다.");
				break;
			}//System.out.println(item.toString());
		}
		System.out.println();

	}

	public static void listAll(TodoList l) {
		System.out.println("[전제 목록]");
		int count=0;
		for (TodoItem item : l.getList()) {
			count++;
			System.out.println(""+count+ ". [" + item.getCategory() + "] :  "+item.getTitle() +  " - " + item.getDesc() + " - " +item.getDue_date()+" - "+ item.getCurrent_date());
			
		}
		System.out.println();
	}
	
	
	public static void listCate(TodoList l) {
		int count=0;
		int valid=0;
		String[]temp = new String[100];
		for (TodoItem item : l.getList()) {
			int num=1;
			temp[count] = item.getCategory();
			for(int i=0; i<count; i++) {
				
				if(temp[i].equals(item.getCategory())) {
					//System.out.print("["+temp[i]+"]");
					num=0; 
				}
			}
			
			count++;
			if(num==0) continue;
			else {
				if(count!=1) System.out.print(" / ");
				System.out.print(item.getCategory());
				valid++;
			}
			//System.out.println(item.toString());
		}
		System.out.println();
		System.out.println("총 "+ valid+ "개의 항목을 찾았습니다.");
		System.out.println();
	}
	
	public static void listChosen(TodoList l, int number) {
		//System.out.println("[전제 목록]");
		int count=0;
		for (TodoItem item : l.getList()) {
			count++;
			if(number==count) {
				System.out.println(""+count+ ". [" + item.getCategory() + "] :  "+item.getTitle() +  " - " + item.getDesc() + " - " +item.getDue_date()+" - "+ item.getCurrent_date());
				break;
			}//System.out.println(item.toString());
		}
	}
	
	public static void findChosen(TodoList l, String title) {
		//System.out.println("[전제 목록]");
		int count=0;
		for (TodoItem item : l.getList()) {
			count++;
			if(title==item.getTitle()) {
				System.out.println(""+count+ ". [" + item.getCategory() + "] :  "+item.getTitle() +  " - " + item.getDesc() + " - " +item.getDue_date()+" - "+ item.getCurrent_date());
				break;
			}//System.out.println(item.toString());
		}
	}
	
	public static void deleteChosen(TodoList l, int number) {
		//System.out.println("[전제 목록]");
		int count=0;
		for (TodoItem item : l.getList()) {
			count++;
			if(number==count) {
				l.deleteItem(item);
				break;
			}//System.out.println(item.toString());
		}
	}
	
	public static void find(TodoList l, String search) {
		int count=0;
		for(TodoItem item : l.getList()) {
			if(item.getTitle().contains(search)||item.getDesc().contains(search)) {
				count++;
				findChosen(l,item.getTitle());
				
			}
		}
		if(count>0) {
			System.out.println("총 "+count+"개의 항목을 찾았습니다.");
		}
		System.out.println();
	}
	
	public static void find_cate(TodoList l, String search) {
		int count=0;
		for(TodoItem item : l.getList()) {
			if(item.getCategory().contains(search)) {
				count++;
				findChosen(l,item.getTitle());
				
			}
		}
		if(count>0) {
			System.out.println("총 "+count+"개의 항목을 찾았습니다.");
		}
		System.out.println();
	}

	public static void saveList(TodoList l, String filename) {
		try {
			Writer w = new FileWriter(filename);
			int count=0;
			for (TodoItem item : l.getList()) {
				count++;
				System.out.print(""+count+"##");
				w.write(""+count+"##");
				System.out.println(item.toSaveString());
				w.write(item.toSaveString());
			}
			w.close();
			
			System.out.println("정보를 저장하였습니다!!! ");
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void loadList(TodoList l, String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			int i=0;
			String oneline;
			
			while((oneline = br.readLine()) != null) {
				
				StringTokenizer st = new StringTokenizer(oneline, "##");
				String category = st.nextToken();
				String title = st.nextToken();
			    String desc = st.nextToken();
			    String due_date = st.nextToken();
			    
			    TodoItem t= new TodoItem(category,title, desc, due_date);
			    l.addItem(t);
				i++;
			}
			
			br.close();
			if(i==0) { 
				System.out.println("가져온 정보가 없습니다.");
			}
			else {
				System.out.println(""+i+"개의 정보를 가져왔습니다.");
		 	}
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
}
