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
				+ "=====[ 스케줄 추가 ]=====\n"
				+ "제목 > ");
		
		title = sc.nextLine().trim();
		if (list.isDuplicate(title)) {
			System.out.printf("제목이 중복됩니다!");
			return;
		}
		//sc.nextLine();
		System.out.println("해야할 일 > ");
		desc = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
		//교수님 코드
		System.out.println("*추가 되었습니다.*");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		/*System.out.println("[항목 추가]\n"
		+ "삭제할 항목의 제목을 입력하시오 > ");
		*/
		
		System.out.println("\n"
				+ "=====[ 스케줄 삭제 ]=====\n"
				+ "제목 > ");
		String title = sc.nextLine().trim();;
		
		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				System.out.println("*삭제되었습니다.*");
				break;
			}
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "=====[ 스케줄 수정 ]=====\n"
				+ "수정할 스케줄 제목 > ");
		
		String title = sc.nextLine().trim();
		
		if (!l.isDuplicate(title)) {
			System.out.println("*없는 제목입니다, 다시 시도해주세요.*");
			return;
		}

		System.out.println("새 제목을 입력하세요.");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("제목이 중복됩니다! 다시 시도해주세요.");
			return;
		}
		sc.nextLine();
		System.out.println("새 내용 입력 > ");
		String new_description = sc.nextLine().trim();
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println("스케줄이 수정되었습니다.");
			}
		}

	}

	public static void listAll(TodoList l) {
		System.out.println("[전제 목록]");
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
				String title = st.nextToken();
			    String desc = st.nextToken();
			    
			    TodoItem t= new TodoItem(title,desc);
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
