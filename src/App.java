import javax.swing.JFrame;
import javax.swing.JPanel;

import Constants.ActiveController;
import Controllers.HomeController;
import Controllers.NavbarController;
import Controllers.ProductController;
import Entities.Location;
import Entities.Product;
import Entities.Transaction;
import Graphics.Text.RegularText;
import Models.DailyStatsModel;
import Models.IModelInterface;
import Models.LocationModel;
import Models.ProductModel;
import Models.TransactionModel;
import Utilities.Styler;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.BorderLayout;
import java.awt.CardLayout;

public class App {
    private static JFrame window;
    private static HomeController homeController;
    private static ProductController productController;
    private static NavbarController navController;
    private static IModelInterface<Product> productModel;
    private static IModelInterface<Location> locationModel;
    private static IModelInterface<Transaction> transactionModel;
    private static DailyStatsModel statsModel;
    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private static final JPanel MAIN_PANEL = new JPanel(new CardLayout());
    
    public static void main(String[] args) throws Exception {
        initializeControllers();
        initializeWindow();
        randomizeTestData(); // @todo remove this

        window.getContentPane().add(navController.getDefaultView(), BorderLayout.NORTH);
        window.getContentPane().add(MAIN_PANEL, BorderLayout.CENTER);

        //MAIN_PANEL.add(this.createHomeCard(), "main");
        MAIN_PANEL.add(homeController.getDefaultView(), ActiveController.HOME.name());
        MAIN_PANEL.add(productController.getDefaultView(), ActiveController.PRODUCT.name());
    }

    private static void initializeControllers() {
        // Products (initialize)
        productModel = new ProductModel();
        productModel.loadEntities();

        // Stats (requires productModel initialized)
        statsModel = new DailyStatsModel();

        // Products (initialize, load in productModel)
        productController = new ProductController(productModel, MAIN_PANEL);

        // Home
        homeController = new HomeController(productModel, transactionModel, statsModel, MAIN_PANEL);

        // Locations
        locationModel = new LocationModel();
        locationModel.loadEntities();
        navController = new NavbarController(locationModel);

        // Transactions
        transactionModel = new TransactionModel(productModel);
    }

    private static void initializeWindow() {
        window = new JFrame("Toastry");
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new BorderLayout());

        window.setSize((int) SCREEN_SIZE.getWidth(), (int) SCREEN_SIZE.getHeight());
        window.getContentPane().setBackground(Styler.APP_BG_COLOR);

        window.getContentPane().add(new RegularText("Developed by Andrew Michael"), BorderLayout.SOUTH);
    }

    /**
     * Used to generate data to test the system with.
     * 
     * Currently testing product data.
     */
    private static void randomizeTestData() {
        int Min = 0;
        int Max = 10;
        int randomInt;

        for (Product product : productModel.getEntities()) {
            randomInt = Min + (int)(Math.random() * ((Max - Min) + 1));
            System.out.println("APP: #" + product.getID() + " " + product.getProductName() + "'s YSales now at " + randomInt);
            product.setDailySales(randomInt);
        }
    }
}