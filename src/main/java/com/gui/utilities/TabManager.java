package com.gui.utilities;

import com.gui.base.BaseJavaFXController;
import javafx.scene.control.TextArea;

import java.util.Vector;

/**
 * Singleton class for managing tab refreshes and console
 */
public class TabManager {
    private static TabManager manager = null;
    private Vector<BaseJavaFXController> baseControllers;
    private TextArea console;

    private TabManager(){ }

    /**
     * Sets base controllers that have implemented the refresh function
     * @param baseControllers
     */
    public static void setBaseControllers(Vector<BaseJavaFXController> baseControllers, TextArea console){
        if(manager != null)
            return;

        manager = new TabManager();
        manager.baseControllers = baseControllers;
        manager.console = console;
    }

    /**
     * Returns the controller that is asked.
     * @param controllerClass - UI tab class that has implemented BaseJavaFXController
     * @return null or BaseJavaFXController
     */
    public static BaseJavaFXController getController(Class<? extends BaseJavaFXController> controllerClass){
         return manager.baseControllers.stream().filter(x -> controllerClass.isInstance(x))
                 .findFirst().orElse(null);
    }

    public static TextArea getConsole(){return manager.console;}
}
