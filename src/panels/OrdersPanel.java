package panels;

import frames.BarFrame;
import models.Category;
import models.Order;
import models.Product;
import models.ProductType;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class OrdersPanel extends BasePanel {
    public int selectedTableNumber;
    public JTable ordersTable;
    public DefaultTableModel ordersTableModel;
    public JTable productsTable;
    public DefaultTableModel productsTableModel;
    public ArrayList<JButton> categoryButtons;
    public ArrayList<JButton> productButtons;

    public OrdersPanel(BarFrame frame, int selectedTableNumber) {
        super(frame);
        this.selectedTableNumber = selectedTableNumber;
        initializeHeader();
        initializeOrdersTable();
        initializeProductsTable();
        initializeCategories();
        initializeFooter();
        frame.dataProvider.fetchOrders(ordersTableModel, selectedTableNumber);
        initializeButtonAddSubtract();
    }

    public void initializeHeader() {
        JButton createButton = new JButton("Създай");
        createButton.setBounds(0, 15, frame.getWidth() / 3, 40);
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createOrder();
            }
        });
        add(createButton);

        JLabel waitressLabel = new JLabel(frame.dataProvider.loggedUser.getName());
        waitressLabel.setBounds(frame.getWidth() / 2 - 75, 10, 150, 30);
        waitressLabel.setFont(new Font("Helvetik", Font.BOLD, 18));
        waitressLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(waitressLabel);

        String tableText = "Маса :" + selectedTableNumber;
        JLabel tableLabel = new JLabel(tableText);
        tableLabel.setBounds(frame.getWidth() / 2 - 50, 40, 100, 30);
        tableLabel.setFont(new Font("Helvetik", Font.BOLD, 18));
        tableLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(tableLabel);

        JButton finishButton = new JButton("Приключи");
        finishButton.setBounds(frame.getWidth() - (frame.getWidth() / 3), 15, frame.getWidth() / 3, 40);
        finishButton.addActionListener(e -> {
            if (ordersTable.getSelectedRow() < 0) {
                showError("Няма избрана поръчка!");
                return;
            }

            // Показване на диалог за въвеждане на отстъпка
            String discountInput = JOptionPane.showInputDialog(frame, "Въведете отстъпка (процент):");
            double discount = 0.0;

            if (discountInput != null && !discountInput.isEmpty()) {
                try {
                    discount = Double.parseDouble(discountInput);
                } catch (NumberFormatException ex) {
                    showError("Невалиден вход за отстъпка. Въведете числова стойност.");
                    return;
                }
            }

            boolean isConfirm = showQuestionPopup("Завършване на поръчката?");
            boolean isDiscount = discount > 0;

            if (isConfirm) {
                boolean discountAmount = isDiscount;
                String totalPriceStr = selectedOrder().getTotalPrice(discountAmount);
                double totalPrice = Double.parseDouble(totalPriceStr.replace(" лв.", "").replace(",", ".")); // Преобразуване на низа в double
                double discountAmounts = (discount / 100) * totalPrice;
                double discountedTotalAmount = totalPrice - discountAmounts;

                showError("Сума на поръчката: " + discountedTotalAmount + " лв.");
                showError("Стойност на отстъпката: " + discountAmounts + " лв.");

                // Премахване на поръчката от списъка с поръчки
                frame.dataProvider.orders.remove(selectedOrder());

                // Прехвърляне към друг екран, екрана за вход
                frame.router.showLogin();
            }
        });
        add(finishButton);
    }

    public void initializeOrdersTable() {
        String cols[] = {"Номер", "Продукти", "Цена"};
        ordersTableModel = new DefaultTableModel();
        ordersTableModel.setColumnIdentifiers(cols);
        ordersTable = new JTable(ordersTableModel);

        ordersTable.getSelectionModel().addListSelectionListener(e -> {
            System.out.println("Селектиран е ред в order table: " + ordersTable.getSelectedRow()); //защо се печата 2 пъти този ред??????
            if (ordersTable.getSelectedRow() > -1) {
                frame.dataProvider.fetchProducts(productsTableModel, selectedOrder());
            }
        });
        JScrollPane ordersPane = new JScrollPane(ordersTable);
        ordersPane.setBounds(0, 60, frame.getWidth() / 3, frame.getHeight() - 150);
        add(ordersPane);
        frame.dataProvider.fetchOrders(ordersTableModel, selectedTableNumber);
    }

    public void initializeProductsTable() {
        String cols[] = {"Продукт", "Количество", "Цена"};
        productsTableModel = new DefaultTableModel();
        productsTableModel.setColumnIdentifiers(cols);

        productsTable = new JTable(productsTableModel);
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        productsTable.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
        productsTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        JScrollPane productsPane = new JScrollPane(productsTable);
        productsPane.setBounds(frame.getWidth() - (frame.getWidth() / 3), 60, frame.getWidth() / 3, frame.getHeight() - 150);
        add(productsPane);
    }

    public void initializeCategories() {
        categoryButtons = new ArrayList<>();
        int buttonX = frame.getWidth() / 2 - (frame.getWidth() / 3) / 2;
        int buttonY = 80;
        for (Category category : frame.dataProvider.categories) {
            JButton button = new JButton(category.title);
            button.setBounds(buttonX, buttonY, frame.getWidth() / 3, 40);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    clear();
                    initializeProductButton(category.type);
                }
            });
            add(button);
            categoryButtons.add(button);
            buttonY += 40;
        }
    }

    public void initializeProductButton(ProductType type) {
        productButtons = new ArrayList<>();
        int buttonX = frame.getWidth() / 2 - (frame.getWidth() / 3) / 2;
        int buttonY = 80;
        for (Product product : frame.dataProvider.products) {
            if (product.getType() == type) {
                JButton button = new JButton(product.getBrand());
                button.setBounds(buttonX, buttonY, frame.getWidth() / 3, 40);
                button.addActionListener(e -> {
                            System.out.println("Натиснат бутон " + button.getText());
                            addProductToOrder(button.getText(), true);
                        }
                );
                add(button);
                productButtons.add(button);
                buttonY += 40;
            }
        }
        JButton backButton = new JButton("Назад");
        backButton.addActionListener(e -> {
            clear();
            initializeCategories();
        });
        backButton.setBounds(buttonX, buttonY, frame.getWidth() / 3, 40);
        add(backButton);
        productButtons.add(backButton);
    }

    public Order selectedOrder() {
        int currentlySelectedOrderRow = ordersTable.getSelectedRow();

        if (currentlySelectedOrderRow < 0) {
            // Показваме попъп съобщение, тъй като няма селектирана поръчка
            JOptionPane.showMessageDialog(frame, "Няма селектирана поръчка", "Грешка", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        int currentlySelectedOrder = Integer.parseInt((String) ordersTable.getModel().getValueAt(currentlySelectedOrderRow, 0)) - 1;
        return frame.dataProvider.orders.get(currentlySelectedOrder);
    }

    public void addProductToOrder(String brand, boolean isIncreasing) {
        System.out.println("Използва се метод addProductToOrder");
        if (ordersTable.getSelectedRow() < 0) {
            showError("Няма селектирана поръчка");
            return;
        }
        int currentlySelectedRow = ordersTable.getSelectedRow();
        System.out.println("Запаметява този ред като селектиран при добавяне на поръчка: " + currentlySelectedRow);

        // Проверка за валиден бранд
        if (brand == null || brand.trim().isEmpty()) {
            showError("Невалиден бранд");
            return;
        }

        boolean isFound = false;

        // Обикаляне на продуктите в поръчката и инкрементиране/декрементиране на количество, ако съвпада бранда
        for (Product prd : selectedOrder().getProducts()) {
            if (prd.getBrand().equals(brand)) {
                if (isIncreasing) {
                    prd.setQuantity(prd.getQuantity() + 1);
                    System.out.println(prd.getBrand() + " increased by 1 ");
                } else {
                    if (prd.getQuantity() > 1) {
                        prd.setQuantity(prd.getQuantity() - 1);
                        System.out.println(prd.getBrand() + " decreased by 1 ");
                    } else {
                        // Ако количеството е 1, премахнете продукта от поръчката
                        selectedOrder().getProducts().remove(prd);
                        System.out.println(prd.getBrand() + " removed from order");
                    }
                }
                System.out.println(" =>      selectedOrder().getProductsCount(): " + selectedOrder().getProductsCount());
                isFound = true;
                break;
            }
        }

        // Създаване и добавяне на нов продукт, ако не е намерен със същия бранд и когато се увеличава количеството
        if (!isFound && isIncreasing) {
            Product product2 = null;

            for (Product product1 : frame.dataProvider.products) {
                if (product1.getBrand().equals(brand)) {
                    product2 = new Product(product1.getUid(), product1.getType(), product1.getSubtype(),
                            product1.getBrand(), product1.getPrice(), 1); // Задайте количество на 1 за новия продукт
                    System.out.print("new Product: " + product2.getBrand());
                    break;
                }
            }

            if (product2 != null) {
                selectedOrder().getProducts().add(product2);
                System.out.println(" =>      selectedOrder().getProductsCount(): " + selectedOrder().getProductsCount());
            }
        }

        frame.dataProvider.fetchProducts(productsTableModel, selectedOrder());
        frame.dataProvider.fetchOrders(ordersTableModel, selectedTableNumber);
        ordersTable.setRowSelectionInterval(currentlySelectedRow, currentlySelectedRow);
    }

    public void initializeFooter() {
        JButton exitButton = new JButton("Отказ");
        exitButton.setBounds(0, frame.getHeight() - 80, frame.getWidth() / 3, 40);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.router.showLogin();
            }
        });
        add(exitButton);
    }

    public void initializeButtonAddSubtract() {
        JButton increaseButton = new JButton("+");
        increaseButton.setBounds(frame.getHeight() - 34, 620, frame.getWidth() / 3 - 250, 40);
        increaseButton.addActionListener(e -> {
            if (productsTable.getSelectedRow() >= 0) { // Проверка за избран продукт
                updateProductQuantity(true);
            } else {
                showError("Няма селектиран продукт");
            }
        });
        add(increaseButton);

        JButton decreaseButton = new JButton("-");
        decreaseButton.setBounds(750, frame.getHeight() - 80, frame.getWidth() / 3 - 250, 40);
        decreaseButton.addActionListener(e -> {
            if (productsTable.getSelectedRow() >= 0) { // Проверка за избран продукт
                updateProductQuantity(false);
            } else {
                showError("Няма селектиран продукт");
            }
        });
        add(decreaseButton);
    }

    public void updateProductQuantity(boolean isIncreasing) {
        System.out.println("Update Product Quantity Called with isIncreasing=" + isIncreasing);
        Order selectedOrder = selectedOrder();
        if (selectedOrder == null) {
            // Показваме попъп съобщение за грешка, тъй като няма селектирана поръчка
            JOptionPane.showMessageDialog(frame, "Няма селектирана поръчка", "Грешка", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int currentlySelectedProductRow = productsTable.getSelectedRow();
        if (currentlySelectedProductRow == -1) {
            showError("Нямате селектирана поръчка");
            return;
        }

        String selectedProductBrand = (String) productsTable.getModel().getValueAt(currentlySelectedProductRow, 0);
        System.out.println("Selected Product Brand: " + selectedProductBrand);

        for (Product product : selectedOrder().getProducts()) {
            if (product.getBrand().equals(selectedProductBrand)) {
                if (isIncreasing) {
                    product.increaseQuantity();
                } else {
                    if (product.getQuantity() > 1) {
                        product.decreaseQuantity();
                    } else {
                        selectedOrder().getProducts().remove(product);
                    }
                }
                break;
            }
        }

        productsTable.clearSelection();
        productsTable.setRowSelectionInterval(currentlySelectedProductRow, currentlySelectedProductRow);

        frame.dataProvider.fetchProducts(productsTableModel, selectedOrder());
        frame.dataProvider.fetchOrders(ordersTableModel, selectedTableNumber);
    }

    public void createOrder() {
        boolean isYes = showQuestionPopup("Отваряне на нова поръчка?");
        if (isYes) {
            Order order = new Order("1", selectedTableNumber, frame.dataProvider.loggedUser);
            frame.dataProvider.orders.add(order);
            frame.dataProvider.fetchOrders(ordersTableModel, selectedTableNumber);
        }
    }

    public void clear() {
        if (categoryButtons != null) {
            for (JButton button : categoryButtons) {
                remove(button);
            }
            repaint();
        }
        if (productButtons != null) {
            for (JButton button : productButtons) {
                remove(button);
            }
            repaint();
        }
    }
}
