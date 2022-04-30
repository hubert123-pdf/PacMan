
package src;

import src.scenes.FirstScene;
import src.scenes.SecondScene;

class Main
{
    public static void main(String[] args) throws InterruptedException {

        FirstScene firstScene = new FirstScene();
        SecondScene secondScene = new SecondScene();

        secondScene.setScene();
        Thread.sleep(10);
        firstScene.setScene();
    }
}