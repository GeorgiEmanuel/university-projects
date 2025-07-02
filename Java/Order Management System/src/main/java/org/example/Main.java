package org.example;


import Model.AppModel;
import Presentation.Controller;
import Presentation.View;




public class Main {
    public static void main(String[] args) {

        AppModel appModel = new AppModel();
        View view = new View(appModel);
        Controller controller = new Controller(view);


    }
}