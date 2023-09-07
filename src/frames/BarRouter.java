package frames;

import panels.*;

public class BarRouter {
    public BarFrame frame;

    public BarRouter(BarFrame frame) {
        this.frame = frame;
    }

    public void showLogin() {
        LoginPanel panel = new LoginPanel(frame);
        this.frame.setContentPane(panel);
        this.frame.validate();
    }

    public void showUsersPanel() {
        UsersPanel panel = new UsersPanel(frame);
        this.frame.setContentPane(panel);
        this.frame.validate();
    }

    public void showManagerPanel() {
        ManagerPanel panel = new ManagerPanel(frame);
        this.frame.setContentPane(panel);
        this.frame.validate();
    }

    public void showTables() {
        TablesPanel panel = new TablesPanel(frame);
        this.frame.setContentPane(panel);
        this.frame.validate();
    }

    public void showOrderPanel(int tableNumber) {
        OrdersPanel panel = new OrdersPanel(frame, tableNumber);
        this.frame.setContentPane(panel);
        this.frame.validate();
    }

}
