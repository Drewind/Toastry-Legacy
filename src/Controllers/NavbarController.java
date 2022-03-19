package Controllers;

import javax.swing.JPanel;

import Interfaces.ControllerInterface;
import Models.IModelInterface;
import Views.NavbarViewAction;

public class NavbarController implements ControllerInterface {
    private IModelInterface<?> model;
    private NavbarViewAction creationView;

    public NavbarController(IModelInterface<?> model) {
        this.model = model;

        // Controller actions
        this.creationView = new NavbarViewAction();
    }

    @Override
    public JPanel getDefaultView() {
        return creationView.renderView();
    }
}