package GraphicalUserInterface;

public class TestApp {
    public static void main(String[] args){

        AppModel appModel = new AppModel();
        AppView appView = new AppView(appModel);
        AppController appController = new AppController(appModel, appView);


    }
}
