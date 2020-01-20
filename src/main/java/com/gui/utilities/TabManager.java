package com.gui.utilities;

import com.gui.base.BaseJavaFXController;

import java.util.Vector;

/**
 * Singleton class for managing tab refreshes
 */
public class TabManager {
    private static TabManager manager = null;
    private  Vector<BaseJavaFXController> baseControllers;

    private TabManager(){ }

    /**
     * Sets base controllers that have implemented the refresh function
     * @param baseControllers
     */
    public static void setBaseControllers(Vector<BaseJavaFXController> baseControllers){
        if(manager != null)
            return;

        manager = new TabManager();
        manager.baseControllers = baseControllers;
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
}
