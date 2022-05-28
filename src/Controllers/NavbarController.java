package Controllers;

import javax.swing.JPanel;

import Interfaces.ControllerInterface;
import Models.Model;
import Views.NavbarViewAction;

public class NavbarController implements ControllerInterface {
    private NavbarViewAction creationView;

    public NavbarController(Model locationmodel) {

        // Controller actions
        this.creationView = new NavbarViewAction();
    }

    @Override
    public JPanel getDefaultView() {
        return creationView.renderView();
    }
}