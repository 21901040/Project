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
				
		System.out.print("[�׸� �߰�]\n" 
				+ "���� >");
		title = sc.next();
		
		if (l.isDuplicate(title)) {
			System.out.printf("������ �ߺ��˴ϴ�!");
			return;
		}
		
		System.out.print("ī�װ� > ");
		category = sc.next();
		sc.nextLine();
		System.out.print("���� > ");
		desc = sc.nextLine().trim();
		System.out.print("�������� > ");
		due_date = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(title, desc, category, due_date);
		if(l.addItem(t) > 0)
			System.out.println("*�߰� �Ǿ����ϴ�.*");
	}
    
	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[�׸� ����]\n"
				+ "������ �׸��� ��ȣ�� �Է��Ͻÿ� >"); 
		int index = sc.nextInt();
		if(l.deleteItem(index)>0)
			System.out.println("�����Ǿ����ϴ�");
	}

	public static void updateItem(TodoList l) {
		
		String new_title, new_desc, new_category, new_due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[ �׸� ���� ]\n"
				+ "������ �׸��� ��ȣ�� �Է��Ͻÿ� > ");
		int index = sc.nextInt();
		
		System.out.print("�� ���� > ");
		new_title = sc.next().trim();
		System.out.print("�� ī�װ� > ");
		new_category = sc.next();
		sc.nextLine();
		System.out.print("�� ���� > ");
		new_desc = sc.nextLine().trim();
		System.out.print("�� �������� > ");
		new_due_date = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(new_title, new_desc, new_category, new_due_date);
		t.setId(index);
		if(l.updateItem(t) > 0)	
			System.out.println("�������� �����Ǿ����ϴ�.");

	}
	
	public static void listAll(TodoList l) {
		System.out.printf("[��ü ���, �� %d��]\n", l.getCount());
		for(TodoItem item: l.getList()) {
			System.out.println(item.toString());
		}
		
	}
	
	public static void listAll(TodoList l, String orderby, int ordering) {
		System.out.printf("[��ü ���, �� %d��]\n", l.getCount());
		for(TodoItem item: l.getOrderedList(orderby, ordering)) {
			System.out.println(item.toString());
		}
		
	}
	/*
	public static void listChosen(TodoList l, int number) {
		//System.out.println("[���� ���]");
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
		//System.out.println("[���� ���]");
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
		//System.out.println("[���� ���]");
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
		System.out.printf("�� %d���� �׸��� ã�ҽ��ϴ�. \n", count);
	}
	
	public static void findCateList(TodoList l, String cate) {
		int count=0;
		for (TodoItem item: l.getListCategory(cate)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("\n�� %d���� �׸��� ã�ҽ��ϴ�.\n", count);
	}
	
	public static void listCateAll(TodoList l) {
		int count=0;
		for (String item : l.getCategories()) {
			System.out.print(item + " ");
			count++;
		}
		System.out.printf("\n�� %d���� ī�װ��� ��ϵǾ� �ֽ��ϴ�.\n", count);

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
			
			System.out.println("������ �����Ͽ����ϴ�!!! ");
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
				System.out.println("������ ������ �����ϴ�.");
			}
			else {
				System.out.println(""+i+"���� ������ �����Խ��ϴ�.");
		 	}
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	*/
}
