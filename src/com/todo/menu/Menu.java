package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
        
        System.out.println("���Ͻô� �۾��� �Է��Ͻÿ�(���� - help) >");
    }
    public static void prompt()
    {	
    	System.out.println();
    	System.out.println("< ToDoList ���� ��ɾ� ���� >");
        System.out.println("1. [ ���ο� ������ �߰� ]-> add ");
        System.out.println("2. [ ���� ������ ���� ]-> del ");
        System.out.println("3. [ ���� ������ ���� ]-> edit ");
        System.out.println("4. [ ��� ������ ���� ]-> ls ");
        System.out.println("5. [ ��������� ���� ]-> ls_name_asc ");
        System.out.println("6. [ ���񿪼����� ���� ]-> ls_name_desc ");
        System.out.println("7. [ ��¥�� �ð������� ���� ]-> ls_date ");
        System.out.println("8. [ ��¥�� �ð������� ���� ]-> ls_date_desc ");
        System.out.println("9. [ �ܾ�� ����/���� ã�� ]-> find (�ܾ �����ÿ�) ");
        System.out.println("10. [ �ܾ�� ī�װ� ã�� ]-> find_cate (�ܾ �����ÿ�) ");
        System.out.println("11. [ ���� ]-> help ");
        System.out.println("12. [ ������ ]-> exit ");
        System.out.println();
    	//System.out.print("\n���Ͻô� �۾��� �Է��Ͻÿ� >");
    }
}
