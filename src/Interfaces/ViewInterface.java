package Interfaces;

public interface ViewInterface {
    interface EventListener {
        void onDialogClick();
    }

    interface View {
        void setListener(ViewInterface.EventListener listener);
        void updateView();
        void showDialog(String message);
        void showError(String message);
    }
}