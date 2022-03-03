package Interfaces;

import java.util.HashMap;

/**
 * Interface for all views that act as a form. Input is entered,
 * output is then inquired by another file, often by a controller.
 * 
 * Usually hooked up to model observers to obtain information, but not required.
 * @method void resetView: resets the form's components.
 * @method void updateView (<T>): uses the generic parameter (T) to
 *         update the view using the parameter.
 * @method HashMap<String, Object> getInputs: gathers all of the form's input fields.
 */
public interface FormInterface<T> {
    public void resetView();
    public void updateView(final T UID);
    public HashMap<String, Object> getInputs();
}