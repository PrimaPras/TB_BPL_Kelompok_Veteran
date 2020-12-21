package toko;

import java.sql.SQLException;
import java.util.LinkedList;

public interface IControl<T> {
    abstract void menu(String menu);
    abstract void addMenu();
    abstract void editMenu();
    abstract void deleteMenu();
    abstract void showMenu();
    abstract void searchMenu();
    abstract Object search(String key) throws SQLException;
}
