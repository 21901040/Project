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
	
	
	
	public static void createItem(TodoList l) {
		
		String title, desc, category, due_date;
		Scanner sc = new Scanner(System.in);
				
		System.out.print("[항목 추가]\n" 
				+ "제목 >");
		title = sc.next();
		
		if (l.isDuplicate(title)) {
			System.out.printf("제목이 중복됩니다!");
			return;
		}
		
		System.out.print("카테고리 > ");
		category = sc.next();
		sc.nextLine();
		System.out.print("내용 > ");
		desc = sc.nextLine().trim();
		System.out.print("마감일자 > ");
		due_date = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(title, desc, category, due_date);
		if(l.addItem(t) > 0)
			System.out.println("*추가 되었습니다.*");
	}
    
	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[항목 삭제]\n"
				+ "삭제할 항목의 번호를 입력하시오 >"); 
		int index = sc.nextInt();
		if(l.deleteItem(index)>0)
			System.out.println("삭제되었습니다");
	}

	public static void updateItem(TodoList l) {
		
		String new_title, new_desc, new_category, new_due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[ 항목 수정 ]\n"
				+ "수정할 항목의 번호를 입력하시오 > ");
		int index = sc.nextInt();
		
		System.out.print("새 제목 > ");
		new_title = sc.next().trim();
		System.out.print("새 카테고리 > ");
		new_category = sc.next();
		sc.nextLine();
		System.out.print("새 내용 > ");
		new_desc = sc.nextLine().trim();
		System.out.print("새 마감일자 > ");
		new_due_date = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(new_title, new_desc, new_category, new_due_date);
		t.setId(index);
		if(l.updateItem(t) > 0)	
			System.out.println("스케줄이 수정되었습니다.");

	}
	
	public static void listAll(TodoList l) {
		System.out.printf("[전체 목록, 총 %d개]\n", l.getCount());
		for(TodoItem item: l.getList()) {
			System.out.println(item.toString());
		}
		
	}
	
	public static void listAll(TodoList l, String orderby, int ordering) {
		System.out.printf("[전체 목록, 총 %d개]\n", l.getCount());
		for(TodoItem item: l.getOrderedList(orderby, ordering)) {
			System.out.println(item.toString());
		}
		
	}
	/*
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
	*/
	public static void findList(TodoList l, String keyword) {
		int count=0;
		for(TodoItem item: l.getList(keyword)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("총 %d개의 항목을 찾았습니다. \n", count);
	}
	
	public static void findCateList(TodoList l, String cate) {
		int count=0;
		for (TodoItem item: l.getListCategory(cate)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("\n총 %d개의 항목을 찾았습니다.\n", count);
	}
	
	public static void listCateAll(TodoList l) {
		int count=0;
		for (String item : l.getCategories()) {
			System.out.print(item + " ");
			count++;
		}
		System.out.printf("\n총 %d개의 카테고리가 등록되어 있습니다.\n", count);

	}
	/*

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
	*/
	/*
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
	*/
}
