package com.todo.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.todo.service.DbConnect;
import com.todo.service.TodoSortByDate;
import com.todo.service.TodoSortByName;

public class TodoList {
	Connection conn;

	public TodoList() {
		this.conn = DbConnect.getConnection();
	}
	
	public void importData(String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			String sql = "insert into list (title, memo, category, place, priority, is_completed, current_date, due_date)"
					+ "values (?,?,?,?,?,?,?,?);";
			int records = 0;
			while((line = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, "##");
				String category = st.nextToken();
				String title = st.nextToken();
				String description = st.nextToken();
				String place = st.nextToken();
				String priority = st.nextToken();
				int is_completed = Integer.parseInt(st.nextToken());
				String due_date = st.nextToken();
				String current_date = st.nextToken();
				
				//preparedStatement pstmt = conn.
				//Connection co = conn.getConnection();
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,title);
				pstmt.setString(2,description);
				pstmt.setString(3,category);
				pstmt.setString(4,place);
				pstmt.setString(5,priority);
				pstmt.setInt(6,is_completed);
				pstmt.setString(7,current_date);
				pstmt.setString(8,due_date);
				int count = pstmt.executeUpdate();
				if(count > 0) records++;
				pstmt.close();
			}
			System.out.println(records + " records read!!");
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public int addItem(TodoItem t) {
		String sql = "insert into list (title, memo, category, place, priority, is_completed, current_date, due_date)"
				+ "values (?,?,?,?,?,?,?,?);";
		PreparedStatement pstmt;
		int count=0;
		try {

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,t.getTitle());
			pstmt.setString(2,t.getDesc());
			pstmt.setString(3,t.getCategory());
			pstmt.setString(4,t.getPlace());
			pstmt.setString(5,t.getPriority());
			pstmt.setInt(6,t.getIs_completed());
			pstmt.setString(7,t.getCurrent_date());
			pstmt.setString(8,t.getDue_date());
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public ArrayList<TodoItem> getLinked(){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		ArrayList<TodoItem> listc = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
		String sql = "SELECT list.id, list.title, list.memo, list.category, list.place, list.priority, "
				+ "list.is_completed, list.current_date, list.due_date, category.number, category.name_cate FROM list"
				+ " INNER JOIN category ON list.category= category.number";
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next()) {
			int id = rs.getInt("id");
			String name_cate = rs.getString("name_cate");
			String title = rs.getString("title");
			String description = rs.getString("memo");
			String place = rs.getString("place");
			String priority = rs.getString("priority");
			int is_completed = rs.getInt("is_completed");
			String current_date = rs.getString("current_date");
			String due_date = rs.getString("due_date");
			TodoItem t = new TodoItem(title, description, name_cate, place, priority, is_completed, due_date);
			t.setId(id);
			t.setCurrent_date(current_date);;
			list.add(t);
			
		}
		stmt.close();
	} catch(SQLException e) {
		e.printStackTrace();
	}
	return list;
	}
	
	public ArrayList<TodoItem> getCategory(){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
		String sql = "SELECT * FROM category;";
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next()) {
			
			String number = rs.getString("number");
			String name_cate = rs.getString("name_cate");
			TodoItem c = new TodoItem(number, name_cate);
			list.add(c);
		
		}
		stmt.close();
	} catch(SQLException e) {
		e.printStackTrace();
	}
	return list;
	}
	
	public int addCategory(TodoItem c){
		String sql = "insert into category (number, name_cate)"
				+ "values (?,?);";
		PreparedStatement pstmt;
		int count=0;
		try {

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,c.getNumber());
			pstmt.setString(2,c.getName_cate());
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public int deleteItem(int index) {
		String sql ="delete from list where id=?;";
		PreparedStatement pstmt;
		int count=0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, index);
			count = pstmt.executeUpdate();
					pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public int deleteCompleted(int index) {
		String sql ="delete from list where is_completed=?;";
		PreparedStatement pstmt;
		int count=0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 1);
			count = pstmt.executeUpdate();
					pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public ArrayList<TodoItem> getCompleted(int keyword){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		try {
			String sql = " SELECT list.id, list.title, list.memo, list.category, list.place, list.priority, list.is_completed, list.current_date, list.due_date, category.number, category.name_cate FROM list"
					+ " INNER JOIN category ON list.category= category.number where is_completed = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, keyword);
			ResultSet rs = pstmt.executeQuery();

			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String name_cate = rs.getString("name_cate");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String place = rs.getString("place");
				String priority = rs.getString("priority");
				int is_completed = rs.getInt("is_completed");
				String current_date = rs.getString("current_date");
				String due_date = rs.getString("due_date");
				TodoItem t = new TodoItem(title, description, name_cate, place, priority, is_completed, due_date);
				t.setId(id);
				t.setCurrent_date(current_date);;
				list.add(t);
			}
			pstmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public int updateCompleted(int index) {
		String sql = "update list set is_completed=?"
				+ " where id = ?;";
		PreparedStatement pstmt;
		int count=0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 1);
			pstmt.setInt(2, index);
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public int updateLate(int index) {
		String sql = "update list set is_completed=?"
				+ " where  id = ?;";
		PreparedStatement pstmt;
		int count=0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,2);
			pstmt.setInt(2, index);
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public int updateItem(TodoItem t) {
		String sql = "update list set title=?, memo=?, category=?, place=?, priority=?, is_completed=?, current_date=?, due_date=?"
				+ " where id = ?;";
		PreparedStatement pstmt;
		int count=0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,t.getTitle());
			pstmt.setString(2,t.getDesc());
			pstmt.setString(3,t.getCategory());
			pstmt.setString(4,t.getPlace());
			pstmt.setString(5,t.getPriority());
			pstmt.setInt(6,t.getIs_completed());
			pstmt.setString(7, t.getCurrent_date());
			pstmt.setString(8,t.getDue_date());
			pstmt.setInt(9, t.getId());
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public int getCount() {
		Statement stmt;
		int count =0;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT count(id) FROM list;";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			count = rs.getInt("count(id)");
			stmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public int getCountCate() {
		Statement stmt;
		int count =0;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT count(number) FROM category;";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			count = rs.getInt("count(number)");
			stmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public ArrayList<TodoItem> getList(){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql ="SELECT * FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String place = rs.getString("place");
				String priority = rs.getString("priority");
				int is_completed = rs.getInt("is_completed");
				String current_date = rs.getString("current_date");
				String due_date = rs.getString("due_date");
				TodoItem t = new TodoItem(title, description, category, place, priority, is_completed, due_date);
				t.setId(id);
				t.setCurrent_date(current_date);;
				list.add(t);
			}
			stmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getList(String keyword){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		keyword = "%"+keyword+"%";
		try {
			String sql ="SELECT list.id, list.title, list.memo, list.category, list.place, list.priority, list.is_completed, list.current_date, list.due_date, category.number, category.name_cate FROM list"
					+ " INNER JOIN category ON list.category= category.number WHERE title like ? or memo like ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			pstmt.setString(2, keyword);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String name_cate = rs.getString("name_cate");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String place = rs.getString("place");
				String priority = rs.getString("priority");
				int is_completed = rs.getInt("is_completed");
				String current_date = rs.getString("current_date");
				String due_date = rs.getString("due_date");
				TodoItem t = new TodoItem(title, description, name_cate, place, priority, is_completed, due_date);
				t.setId(id);
				t.setCurrent_date(current_date);;
				list.add(t);
			}
			pstmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getListCategory(String keyword){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		try {
			String sql ="SELECT list.id, list.title, list.memo, list.category, list.place, list.priority, list.is_completed, list.current_date, list.due_date, category.number, category.name_cate FROM list"
					+ " INNER JOIN category ON list.category= category.number where category.name_cate = ?;";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			ResultSet rs = pstmt.executeQuery();

			while(rs.next()) {
				int id = rs.getInt("id");
				String name_cate = rs.getString("name_cate");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String place = rs.getString("place");
				String priority = rs.getString("priority");
				int is_completed = rs.getInt("is_completed");
				String current_date = rs.getString("current_date");
				String due_date = rs.getString("due_date");
				TodoItem t = new TodoItem(title, description, name_cate, place, priority, is_completed, due_date);
				t.setId(id);
				t.setCurrent_date(current_date);
				
				list.add(t);
				
			}
			pstmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getListDone(int index){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		try {
			String sql ="SELECT list.id, list.title, list.memo, list.category, list.place, list.priority, list.is_completed, list.current_date, list.due_date, category.number, category.name_cate FROM list"
					+ " INNER JOIN category ON list.category= category.number WHERE id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, index);
			ResultSet rs = pstmt.executeQuery();

			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String name_cate = rs.getString("name_cate");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String place = rs.getString("place");
				String priority = rs.getString("priority");
				int is_completed = rs.getInt("is_completed");
				String current_date = rs.getString("current_date");
				String due_date = rs.getString("due_date");
				TodoItem t = new TodoItem(title, description, name_cate, place, priority, is_completed, due_date);
				t.setId(id);
				t.setCurrent_date(current_date);;
				list.add(t);
			}
			pstmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<String> getCategories() {
		ArrayList<String> list = new ArrayList<String>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql ="SELECT DISTINCT list.category, category.number, category.name_cate FROM list INNER JOIN"
					+ " category ON list.category= category.number;";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				//int id = rs.getInt("id");
				//String category = rs.getString("category");
				//String title = rs.getString("title");
				//String description = rs.getString("memo");
				//String due_date = rs.getString("due_date");
				//String current_date = rs.getString("current_date");
				//TodoItem t= new TodoItem(title, description, category, due_date );
				//t.setId(id);
				//t.setCurrent_date(current_date);;
				String name_cate = rs.getString("name_cate");
				list.add(name_cate);
			}
			stmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getOrderedList(String orderby, int ordering){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT list.id, list.title, list.memo, list.category, list.place, list.priority, list.is_completed, list.current_date, list.due_date, category.number, category.name_cate FROM list"
					+ " INNER JOIN category ON list.category=category.number ORDER BY " + orderby;
			if (ordering==0)
				sql += " desc";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String name_cate = rs.getString("name_cate");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String place = rs.getString("place");
				String priority = rs.getString("priority");
				int is_completed = rs.getInt("is_completed");
				String current_date = rs.getString("current_date");
				String due_date = rs.getString("due_date");
				TodoItem t = new TodoItem(title, description, name_cate, place, priority, is_completed, due_date);
				t.setId(id);
				t.setCurrent_date(current_date);;
				list.add(t);
				
			}
			stmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
/*
	public void sortByName() {
		Collections.sort(list, new TodoSortByName());

	}

	public void listAll() {
		System.out.println(""
				+ "inside list_All method\n");
		for (TodoItem myitem : list) {
			System.out.println(myitem.getCategory() + myitem.getTitle() + myitem.getDesc() + myitem.getDue_date());
		}
	}
	
	public void reverseList() {
		Collections.reverse(list);
	}

	public void sortByDate() {
		Collections.sort(list, new TodoSortByDate());
	}

	public int indexOf(TodoItem t) {
		return list.indexOf(t);
	}
    */
	public Boolean isDuplicate(String title) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		list = getList();
		for (TodoItem item : list) {
			if (title.equals(item.getTitle())) return true;
		}
		return false;
	}
	
}
