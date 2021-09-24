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
				+ "=====[ ������ �߰� ]=====\n"
				+ "ī�װ� > ");
	
		
		category = sc.nextLine().trim();

		System.out.println("���� > ");
		title = sc.nextLine().trim();
		/*
		if (list.isDuplicate(title)) {
			System.out.printf("������ �ߺ��˴ϴ�!");
			return;
		}
		*/
		System.out.println("���� > ");
		desc = sc.nextLine().trim();
		
		System.out.println("�������� > ");
		due_date = sc.nextLine().trim();
		
		
		TodoItem t = new TodoItem(category, title, desc, due_date);
		list.addItem(t);
		//������ �ڵ�
		System.out.println("*�߰� �Ǿ����ϴ�.*");
		System.out.println();
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		/*System.out.println("[�׸� �߰�]\n"
		+ "������ �׸��� ������ �Է��Ͻÿ� > ");
		*/
		
		System.out.println("\n"
				+ "=====[ ������ ���� ]=====\n"
				+ "������ �׸��� ��ȣ�� �Է��Ͻÿ� > ");
		int number = sc.nextInt();
		for (TodoItem item : l.getList()) {
				TodoUtil.listChosen(l,number);
				System.out.println("�� �׸��� �����Ͻðڽ��ϱ�? (y/n) >");
				char yn = sc.next().charAt(0);
				if(yn=='y') {
					TodoUtil.deleteChosen(l,number);
					System.out.println("*�����Ǿ����ϴ�.*");
					break;
				}
				else break;
		
		}
		System.out.println();
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "=====[ ������ ���� ]=====\n"
				+ "������ �׸��� ��ȣ�� �Է��Ͻÿ� > ");
		
		int number = sc.nextInt();
		for (TodoItem item : l.getList()) {
				TodoUtil.listChosen(l,number);
				System.out.println("�� �׸��� �����Ͻðڽ��ϱ�? (y/n) >");
				char yn = sc.next().charAt(0);
				if(yn=='y') {
					
					break;
				}
				else return;
		
		}
		/*
		if (!l.isDuplicate(title)) {
			System.out.println("*���� �����Դϴ�, �ٽ� �õ����ּ���.*");
			return;
		}
		*/
		sc.nextLine();
		System.out.println("�� ī�װ� > ");
		String new_category = sc.nextLine().trim();
		
		System.out.println("�� ���� >");
		String new_title = sc.next().trim();
		/*
		if (l.isDuplicate(new_title)) {
			System.out.println("������ �ߺ��˴ϴ�! �ٽ� �õ����ּ���.");
			return;
		}
		*/
		sc.nextLine();
		System.out.println("�� ���� > ");
		String new_description = sc.nextLine().trim();
		
		sc.nextLine();
		System.out.println("�� �������� > ");
		String new_duedate = sc.nextLine().trim();
		
		
		int count=0;
		for (TodoItem item : l.getList()) {
			count++;
			if(number==count) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_category, new_title, new_description, new_duedate );
				l.addItem(t);
				System.out.println("�������� �����Ǿ����ϴ�.");
				break;
			}//System.out.println(item.toString());
		}
		System.out.println();

	}

	public static void listAll(TodoList l) {
		System.out.println("[���� ���]");
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
		System.out.println("�� "+ valid+ "���� �׸��� ã�ҽ��ϴ�.");
		System.out.println();
	}
	
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
	
	public static void find(TodoList l, String search) {
		int count=0;
		for(TodoItem item : l.getList()) {
			if(item.getTitle().contains(search)||item.getDesc().contains(search)) {
				count++;
				findChosen(l,item.getTitle());
				
			}
		}
		if(count>0) {
			System.out.println("�� "+count+"���� �׸��� ã�ҽ��ϴ�.");
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
			System.out.println("�� "+count+"���� �׸��� ã�ҽ��ϴ�.");
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
			
			System.out.println("������ �����Ͽ����ϴ�!!! ");
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
}
