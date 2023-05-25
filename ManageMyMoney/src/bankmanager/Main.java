package bankmanager;

import bankmanager.restservice.APIFunction;
import bankmanager.userinterface.frame.home.HomeFrame;

import java.io.IOException;

public class Main {
    public static void main(String[] args){
        try {
            APIFunction.getAllOperations();
        } catch(IOException  e) {
            System.out.println(e.toString());
        }
        HomeFrame.createAndShowGUI(args);
    }
}
