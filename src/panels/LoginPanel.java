package panels;

import frames.BarFrame;
import models.UserType;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends BasePanel{
    public LoginPanel(BarFrame frame) {
        super(frame);
        JLabel welcomeLabel = new JLabel("Бар Zanzibar Вписване");
        welcomeLabel.setBounds(frame.getWidth()/2 - 150, 100, 300, 50);
        welcomeLabel.setFont(new Font("Helvetik", Font.BOLD, 26));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(welcomeLabel);

        JLabel enterPassLabel = new JLabel("Въведи парола");
        enterPassLabel.setBounds(frame.getWidth()/2 - 60, welcomeLabel.getY() + 60, 120, 40);
        enterPassLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(enterPassLabel);

        JTextField pinField = new JTextField();
        pinField.setBounds(frame.getWidth()/2 - 60, enterPassLabel.getY() + 50, 120, 40);
        pinField.setHorizontalAlignment(SwingConstants.CENTER);
        add(pinField);

        JButton loginButton = new JButton("ВХОД");
        loginButton.setBounds(frame.getWidth()/2 - 50, pinField.getY() + 50, 100, 40);
        loginButton.addActionListener(e -> {
            if(frame.dataProvider.isCorrectLogin(pinField.getText())){
                if(frame.dataProvider.loggedUser.getType() == UserType.MANAGER){
                   frame.router.showManagerPanel();
                }else {
                    frame.router.showTables();
                }

            }else {
                showError("Грешна парола.Моля въведете вашата парола отново!");
            }
        });
        add(loginButton);

    }
}
