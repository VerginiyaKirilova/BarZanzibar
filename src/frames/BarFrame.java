package frames;

import javax.swing.*;

public class BarFrame extends JFrame {
    public BarRouter router;
    public BarDataProvider dataProvider;

    public BarFrame() {
        super("Bar Zanzibar");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 700);

        dataProvider = new BarDataProvider();
        router = new BarRouter(this);
        router.showLogin();


    }
}
