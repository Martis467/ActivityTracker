package com.exception;

public class UIException extends Exception {

    private String fieldName;
    private String title;
    private UIExceptionType type;
    private String errorMessage;

    /**
     * Basic exception with an error message
     * @param errorMessage
     * @param title
     */
    public UIException(String errorMessage, String title){
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.title = title;
    }

    /**
     * Exception with a type, differently formatted
     * @param fieldName - class name that throws this exception
     * @param title - Title of the UI error window
     * @param type - type of exception
     */
    public UIException(String fieldName, String title, UIExceptionType type){
        super("");
        this.fieldName = fieldName;
        this.title = title;
        this.type = type;

        switch (type){
            case Path: break;
            case Database: constructDatabaseException(); break;
            case NullElement: constructNullElementException(); break;
            case InvalidFields: constructInvalidFieldsException(); break;
            default: break;
        }
    }

    /**
     * Returns the generated error message string
     * @return
     */
    public String getErrorMessage(){return this.errorMessage;}

    /**
     * Returns UI error window title
     * @return
     */
    public String getTitle(){return this.title;}

    private void constructDatabaseException(){
        this.errorMessage = type.toString() + " failed";
    }

    private void constructNullElementException() {
        this.errorMessage = "Variable <" + this.fieldName + "> was null";
    }

    private void constructInvalidFieldsException() {
        this.errorMessage = fieldName + " is incorrect format or empty";
    }

}
