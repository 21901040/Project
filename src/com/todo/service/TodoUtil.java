package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.*;
import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "=====[ ������ �߰� ]=====\n"
				+ "���� > ");
		
		title = sc.nextLine().trim();
		if (list.isDuplicate(title)) {
			System.out.printf("������ �ߺ��˴ϴ�!");
			return;
		}
		//sc.nextLine();
		System.out.println("�ؾ��� �� > ");
		desc = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
		//������ �ڵ�
		System.out.println("*�߰� �Ǿ����ϴ�.*");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		/*System.out.println("[�׸� �߰�]\n"
		+ "������ �׸��� ������ �Է��Ͻÿ� > ");
		*/
		
		System.out.println("\n"
				+ "=====[ ������ ���� ]=====\n"
				+ "���� > ");
		String title = sc.nextLine().trim();;
		
		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				System.out.println("*�����Ǿ����ϴ�.*");
				break;
			}
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "=====[ ������ ���� ]=====\n"
				+ "������ ������ ���� > ");
		
		String title = sc.nextLine().trim();
		
		if (!l.isDuplicate(title)) {
			System.out.println("*���� �����Դϴ�, �ٽ� �õ����ּ���.*");
			return;
		}

		System.out.println("�� ������ �Է��ϼ���.");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("������ �ߺ��˴ϴ�! �ٽ� �õ����ּ���.");
			return;
		}
		sc.nextLine();
		System.out.println("�� ���� �Է� > ");
		String new_description = sc.nextLine().trim();
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println("�������� �����Ǿ����ϴ�.");
			}
		}

	}

	public static void listAll(TodoList l) {
		System.out.println("[���� ���]");
		for (TodoItem item : l.getList()) {
			System.out.println("[" + item.getTitle() + "] :  " + item.getDesc() + " - " + item.getCurrent_date());
			//System.out.println(item.toString());
		}
	}
	
	public static void saveList(TodoList l, String filename) {
		try {
			Writer w = new FileWriter(filename);
			
			for (TodoItem item : l.getList()) {
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
				String title = st.nextToken();
			    String desc = st.nextToken();
			    
			    TodoItem t= new TodoItem(title,desc);
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
