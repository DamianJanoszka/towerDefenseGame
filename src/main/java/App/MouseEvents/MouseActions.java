package App.MouseEvents;

import App.Models.Sprite;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;


public class MouseActions {

    final DragContext dragContext = new DragContext();

    public void makeDraggable(final Sprite sprite) {
        sprite.setOnMousePressed(onMousePressedEventHandler);
        sprite.setOnMouseDragged(onMouseDraggedEventHandler);
        sprite.setOnMouseReleased(onMouseReleasedEventHandler);
    }
    public void makeNotDraggable(final Sprite sprite){
        sprite.setOnMousePressed(makeNotDraggableEventHandler);
        sprite.setOnMouseDragged(makeNotDraggableEventHandler);
        sprite.setOnMouseReleased(makeNotDraggableEventHandler);
    }
    EventHandler<MouseEvent> makeNotDraggableEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
            Sprite sprite = (Sprite) event.getSource();
            sprite.setOpacity(1);
        }
    };

    EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
            Sprite sprite = (Sprite) event.getSource();
            sprite.setOpacity(1);
            dragContext.x = event.getSceneX();
            dragContext.y = event.getSceneY();

        }
    };

    EventHandler<MouseEvent> onMouseDraggedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {

            Sprite sprite = (Sprite) event.getSource();

            double offsetX = event.getSceneX() - dragContext.x;
            double offsetY = event.getSceneY() - dragContext.y;

            sprite.setLocationOffset(offsetX, offsetY);

            dragContext.x = event.getSceneX();
            dragContext.y = event.getSceneY();

        }
    };

    EventHandler<MouseEvent> onMouseReleasedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
            Sprite sprite = (Sprite) event.getSource();
            sprite.setOpacity(0.2);

        }
    };

    class DragContext {

        double x;
        double y;

    }

}