package com.todo.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TodoItem {
	private int id;
    private String title;
    private String category;
    private String desc;
    private String place;
    private String priority;
    private int is_completed;
    private String due_date;
    private String current_date;
    
    private String number;
    private String name_cate;
    
    

    public TodoItem(String title,  String desc, String category, String place, String priority, int is_completed,String due_date){
    	this.title=title;
    	this.category=category;
        this.desc=desc;
        this.due_date=due_date;
        this.is_completed=is_completed;
        this.priority=priority;
        this.place=place;
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
        this.current_date= f.format(new Date());
        
    }
    
    public TodoItem(String number, String name_cate) {
    	this.number=number;
    	this.name_cate=name_cate;
    }
    
    public int getId() {
    	return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    
    public String getPriority() {
    	return priority;
    }
    public void setPriority(String priority) {
        this.priority = priority;
    }

    public int getIs_completed() {
        return is_completed;
    }

    public void setIs_completed(int Is_completed) {
        this.is_completed = is_completed;
    }
    
    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    
    public String getDue_date() {
        return due_date;
    }

    public void setDuedate(String due_date) {
        this.due_date = due_date;
    }

    public String getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
    }
    
    public String getNumber() {
    	return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    
    public String getName_cate() {
    	return name_cate;
    }
    public void setName_cate(String name_cate) {
        this.name_cate = name_cate;
    }
    
    
    public String toCateString() {
    	return " "+name_cate + ":" + number;
    }
    
    public String toString() {
    	if(is_completed == 1){
    		return "[V] " +id + ".["+ category +"] " + priority + " - " + title + " - " + desc + " - " + place + " - " + due_date + " - " + current_date;
    	}
    	else if(is_completed == 0){
    		return "[ ] " +id + ".["+ category +"] " + priority + " - " + title + " - " + desc + " - " + place + " - " + due_date + " - " + current_date;
    	}
    	else return "[L] " +id + ".["+ category +"] " + priority + " - " + title + " - " + desc + " - " + place + " - " + due_date + " - " + current_date;
    	
    }
    
  
    public String toCurrent_Date() {
    	return current_date;
    }
}
