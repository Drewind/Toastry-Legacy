import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Constants.ActiveController;
import Controllers.HomeController;
import Controllers.NavbarController;
import Controllers.ProductController;
import Entities.Entity;
import Entities.Product;
import Graphics.Text.RegularText;
import Models.DailyStatsModel;
import Models.LocationModel;
import Models.Model;
import Models.ProductModel;
import Utilities.Styler;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

public class App {
    private static JFrame window;
    private static HomeController homeController;
    private static ProductController productController;
    private static NavbarController navController;
    private static final ProductModel productModel = new ProductModel();
    private static final Model locationModel = new LocationModel();
    private static DailyStatsModel statsModel;
    private static final JPanel MAIN_PANEL = new JPanel(new CardLayout());
    
    public static void main(String[] args) throws Exception {
        initializeControllers();
        initializeWindow();
        randomizeTestData(); // @todo remove this

        MAIN_PANEL.setOpaque(false); // Set to invisble so the content pane background is visible.
        window.getContentPane().setBackground(Styler.APP_BG_COLOR); // Sets content pane background.
        window.getContentPane().add(navController.getDefaultView(), BorderLayout.NORTH);
        window.getContentPane().add(MAIN_PANEL, BorderLayout.CENTER);

        // App icon
        try {
            BufferedImage logoImage = ImageIO.read(new File("media/logos/Toastry-logos_transparent.png"));
            Image logo = logoImage.getScaledInstance(256, 256, Image.SCALE_SMOOTH);
            window.setIconImage(logo);
        } catch (IOException ex) {
            System.out.println("WARNING: Couldn't load application icon.");
        }

        //MAIN_PANEL.add(this.createHomeCard(), "main");
        MAIN_PANEL.add(homeController.getDefaultView(), ActiveController.HOME.name());
        MAIN_PANEL.add(productController.getDefaultView(), ActiveController.PRODUCT.name());
    }

    private static void initializeControllers() {
        statsModel = new DailyStatsModel();

        // Products (initialize, load in productModel)
        productController = new ProductController(productModel, MAIN_PANEL);
        homeController = new HomeController(productModel, statsModel, MAIN_PANEL);
        navController = new NavbarController(locationModel);
    }

    private static void initializeWindow() {
        window = new JFrame("Toastry");
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new BorderLayout());

        // window.setSize((int) SCREEN_SIZE.getWidth(), (int) SCREEN_SIZE.getHeight());
        window.setSize((int) 900, 550);
        window.getContentPane().setBackground(Styler.CONTAINER_BACKGROUND);

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

        for (Entity product : productModel.getEntities()) {
            randomInt = Min + (int)(Math.random() * ((Max - Min) + 1));
            System.out.println("APP: #" + product.getID() + " " + ((Product)product).getProductName() + "'s YSales now at " + randomInt);
            ((Product)product).addSale(randomInt);
        }
    }
}