//package org.example.roomplanner.controller;
//
//import javafx.application.Platform;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.geometry.Point3D;
//import javafx.scene.SubScene;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.input.KeyCode;
//import javafx.scene.input.KeyEvent;
//import javafx.scene.input.MouseButton;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.input.PickResult;
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.layout.FlowPane;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Box;
//import javafx.scene.Group;
//import javafx.scene.PerspectiveCamera;
//import javafx.scene.SceneAntialiasing;
//import javafx.scene.transform.Rotate;
//import javafx.scene.transform.Translate;
//
//import java.net.URL;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.ResourceBundle;
//
//public class RoomDesignController implements Initializable {
//
//    @FXML private FlowPane furniturePane;
//    @FXML private AnchorPane roomPane;
//
//    private double length, width, height;
//    private PerspectiveCamera camera;
//    private final Rotate rX = new Rotate(-20, Rotate.X_AXIS);
//    private final Rotate rY = new Rotate(-30, Rotate.Y_AXIS);
//    private final Translate camPos = new Translate(0, 0, 0);
//
//    // Для мышиного управления
//    private double anchorX, anchorY;
//    private double anchorAngleX, anchorAngleY;
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        // Читаем параметры, записанные в RoomParams
//        length = org.example.roomplanner.RoomParams.length;
//        width  = org.example.roomplanner.RoomParams.width;
//        height = org.example.roomplanner.RoomParams.height;
//
//        // Ждём, пока roomPane получит реальные размеры, и затем строим сцену
//        Platform.runLater(this::setupScene);
//    }
//
//    /** Строим всю 3D-сцену и UI */
//    private void setupScene() {
//        SubScene sub = build3DScene();
//        populateFurniture("Спальня");
//        initMouseControl(sub);
//        initKeyboardControl(sub);
//    }
//
//    /** Создаём SubScene с комнатой без потолка */
//    private SubScene build3DScene() {
//        Group room3D = new Group();
//        Group grp = new Group();
//        double scale = 100;
//
//        // Пол
//        Box floor = new Box(length * scale, 2, width * scale);
//        floor.setTranslateY(height * scale / 2);
//        grp.getChildren().add(floor);
//
//        // Четыре стены
//        Box w1 = new Box(length * scale, height * scale, 2);
//        w1.setTranslateZ(-width * scale / 2); w1.setTranslateY(height * scale / 2);
//        Box w2 = new Box(length * scale, height * scale, 2);
//        w2.setTranslateZ(width * scale / 2);  w2.setTranslateY(height * scale / 2);
//        Box w3 = new Box(2, height * scale, width * scale);
//        w3.setTranslateX(-length * scale / 2); w3.setTranslateY(height * scale / 2);
//        Box w4 = new Box(2, height * scale, width * scale);
//        w4.setTranslateX(length * scale / 2);  w4.setTranslateY(height * scale / 2);
//
//        grp.getChildren().addAll(w1, w2, w3, w4);
//        room3D.getChildren().add(grp);
//
//        // Создаём SubScene ровно под размер roomPane
//        SubScene sub = new SubScene(room3D,
//                roomPane.getWidth(),
//                roomPane.getHeight(),
//                true,
//                SceneAntialiasing.BALANCED);
//        sub.setFill(Color.web("#F0F8FF"));
//
//        // Настраиваем камеру
//        camera = new PerspectiveCamera(true);
//        camera.setNearClip(0.1);
//        camera.setFarClip(10000);
//        double dist = Math.hypot(length, width) * scale * 1.5;
//        camPos.setZ(-dist);
//        camera.getTransforms().addAll(camPos, rX, rY);
//        sub.setCamera(camera);
//        // Вставляем SubScene в roomPane и «рост» биндим сразу
//        roomPane.getChildren().setAll(sub);
//        AnchorPane.setTopAnchor(sub,    0.0);
//        AnchorPane.setBottomAnchor(sub, 0.0);
//        AnchorPane.setLeftAnchor(sub,   0.0);
//        AnchorPane.setRightAnchor(sub,  0.0);
//        sub.widthProperty().bind(roomPane.widthProperty());
//        sub.heightProperty().bind(roomPane.heightProperty());
//
//        return sub;
//    }
//
//    /** Переходим по категориям мебели */
//    @FXML private void onCategoryClicked(MouseEvent e) {
//        String cat = ((Button)e.getSource()).getText();
//        populateFurniture(cat);
//    }
//
//    /** Заполняем панель мебели иконками и названиями */
//    private void populateFurniture(String cat) {
//        furniturePane.getChildren().clear();
//        Map<String,String> items = new HashMap<>();
//        if ("Спальня".equals(cat)) {
//            items.put("Кровать","bed.png");
//            items.put("Шкаф","wardrobe.png");
//            items.put("Тумбочка","nightstand.png");
//            items.put("Комод","dresser.png");
//        } else if ("Кухня".equals(cat)) {
//            items.put("Стол","table.png");
//            items.put("Стул","chair.png");
//            items.put("Плита","stove.png");
//            items.put("Холодильник","fridge.png");
//        }
//        items.forEach((n,img) -> {
//            AnchorPane cell = new AnchorPane();
//            cell.getStyleClass().add("furniture-item");
//
//            ImageView iv = new ImageView(new Image(
//                    getClass().getResourceAsStream("/org/example/roomplanner/assets/" + img)
//            ));
//            iv.setFitWidth(60);
//            iv.setFitHeight(60);
//            AnchorPane.setTopAnchor(iv, 5.0);
//            AnchorPane.setLeftAnchor(iv, 5.0);
//
//            Label lbl = new Label(n);
//            lbl.setStyle("-fx-font-size:12px; -fx-text-fill:#333;");
//            AnchorPane.setTopAnchor(lbl, 70.0);
//            AnchorPane.setLeftAnchor(lbl, 5.0);
//
//            cell.getChildren().addAll(iv, lbl);
//            furniturePane.getChildren().add(cell);
//        });
//    }
//
//    /** Мышиное управление с pivot-вращением */
//    private void initMouseControl(SubScene sub) {
//        sub.setOnMousePressed(e -> {
//            anchorX = e.getSceneX();
//            anchorY = e.getSceneY();
//            anchorAngleX = rX.getAngle();
//            anchorAngleY = rY.getAngle();
//            PickResult pr = e.getPickResult();
//            Point3D pivot = pr.getIntersectedPoint();
//            rX.setPivotX(pivot.getX());
//            rX.setPivotY(pivot.getY());
//            rX.setPivotZ(pivot.getZ());
//            rY.setPivotX(pivot.getX());
//            rY.setPivotY(pivot.getY());
//            rY.setPivotZ(pivot.getZ());
//        });
//        sub.setOnMouseDragged(e -> {
//            double dx = e.getSceneX() - anchorX;
//            double dy = e.getSceneY() - anchorY;
//            if (e.getButton() == MouseButton.PRIMARY) {
//                rY.setAngle(anchorAngleY + dx * 0.5);
//                rX.setAngle(anchorAngleX - dy * 0.5);
//            } else if (e.getButton() == MouseButton.SECONDARY) {
//                camPos.setX(camPos.getX() + dx);
//                camPos.setY(camPos.getY() + dy);
//            }
//        });
//        sub.setOnScroll(e -> camPos.setZ(camPos.getZ() + e.getDeltaY()));
//    }
//
//    /** Клавиатурное управление в стиле Sims 3 */
//    private void initKeyboardControl(SubScene sub) {
//        sub.setFocusTraversable(true);
//        sub.setOnMouseEntered(e -> sub.requestFocus());
//        sub.setOnKeyPressed(this::handleKey);
//    }
//
//    private void handleKey(KeyEvent e) {
//        double move = 20, lift = 20, rot = 5;
//        KeyCode code = e.getCode();
//        switch (code) {
//            case W      -> camPos.setZ(camPos.getZ() + move);
//            case S      -> camPos.setZ(camPos.getZ() - move);
//            case A      -> camPos.setX(camPos.getX() - move);
//            case D      -> camPos.setX(camPos.getX() + move);
//            case SPACE  -> camPos.setY(camPos.getY() - lift);
//            case SHIFT  -> camPos.setY(camPos.getY() + lift);
//            case Q      -> rY.setAngle(rY.getAngle() - rot);
//            case E      -> rY.setAngle(rY.getAngle() + rot);
//            case R      -> rX.setAngle(rX.getAngle() - rot);
//            case F      -> rX.setAngle(rX.getAngle() + rot);
//            default     -> { }
//        }
//    }
//
//    // Дополнительные методы для кнопок, если нужны
//    public void onRotateUp()    { rX.setAngle(rX.getAngle() - 5); }
//    public void onRotateDown()  { rX.setAngle(rX.getAngle() + 5); }
//    public void onRotateLeft()  { rY.setAngle(rY.getAngle() - 5); }
//    public void onRotateRight() { rY.setAngle(rY.getAngle() + 5); }
//    public void onRotateAngle() { rX.setAngle(-20); rY.setAngle(-30); }
//    public void onZoomIn()      { camPos.setZ(camPos.getZ() + 100); }
//    public void onZoomOut()     { camPos.setZ(camPos.getZ() - 100); }
//}
//
//
//
//





package org.example.roomplanner.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import org.example.roomplanner.RoomParams;
// Если используете OBJ-импортёр, раскомментируйте эти строки:
// import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
// import javafx.scene.shape.MeshView;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class RoomDesignController implements Initializable {

    @FXML private FlowPane furniturePane;
    @FXML private AnchorPane roomPane;

    // Параметры комнаты
    private double length, width, height;

    // 3D-группа: комната + добавленные модели
    private Group room3DGroup;

    // Камера и её трансформы
    private PerspectiveCamera camera;
    private final Translate camPos = new Translate();
    private final Rotate camRotX = new Rotate(-20, Rotate.X_AXIS);
    private final Rotate camRotY = new Rotate(-30, Rotate.Y_AXIS);

    // Для мышиного управления камерой
    private double anchorX, anchorY, anchorAngleX, anchorAngleY;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Читаем введённые пользователем параметры
        length = RoomParams.length;
        width  = RoomParams.width;
        height = RoomParams.height;

        // Ожидаем, чтобы roomPane уже имел реальные размеры
        Platform.runLater(this::setupScene);
    }

    /** Собираем 3D-сцену и подключаем UI */
    private void setupScene() {
        SubScene sub = build3DScene();
        populateFurniture("Спальня");   // «Спальня» по умолчанию
        initMouseControl(sub);
        initKeyboardControl(sub);
    }

    /** Создаёт SubScene с неподвижной комнатой и камерой */
    private SubScene build3DScene() {
        room3DGroup = new Group();
        Group room = new Group();
        double s = 100;

        // Пол
        Box floor = new Box(length * s, 2, width * s);
        floor.setTranslateY(height * s / 2);
        room.getChildren().add(floor);

        // 4 стены без потолка
        Box w1 = new Box(length*s, height*s, 2);
        w1.setTranslateZ(-width*s/2); w1.setTranslateY(height*s/2);
        Box w2 = new Box(length*s, height*s, 2);
        w2.setTranslateZ(width*s/2);  w2.setTranslateY(height*s/2);
        Box w3 = new Box(2, height*s, width*s);
        w3.setTranslateX(-length*s/2); w3.setTranslateY(height*s/2);
        Box w4 = new Box(2, height*s, width*s);
        w4.setTranslateX(length*s/2);  w4.setTranslateY(height*s/2);
        room.getChildren().addAll(w1, w2, w3, w4);

        room3DGroup.getChildren().add(room);

        // Собираем SubScene
        SubScene sub = new SubScene(
                room3DGroup,
                roomPane.getWidth(), roomPane.getHeight(),
                true, SceneAntialiasing.BALANCED
        );
        sub.setFill(Color.web("#EAF6FF"));

        // Настраиваем камеру
        camera = new PerspectiveCamera(true);
        camera.setNearClip(0.1);
        camera.setFarClip(10000);
        double dist = Math.hypot(length, width) * s * 1.5;
        camPos.setZ(-dist);
        camera.getTransforms().addAll(camPos, camRotX, camRotY);
        sub.setCamera(camera);

        // Вставляем SubScene в AnchorPane и растягиваем по размеру
        roomPane.getChildren().setAll(sub);
        AnchorPane.setTopAnchor(sub,    0.0);
        AnchorPane.setBottomAnchor(sub, 0.0);
        AnchorPane.setLeftAnchor(sub,   0.0);
        AnchorPane.setRightAnchor(sub,  0.0);
        sub.widthProperty().bind(roomPane.widthProperty());
        sub.heightProperty().bind(roomPane.heightProperty());

        return sub;
    }

    /** Обработчик клика по кнопкам категорий (Левая панель) */
    @FXML
    private void onCategoryClicked(ActionEvent e) {
        String category = ((Button)e.getSource()).getText();
        populateFurniture(category);
    }

    /** Заполняет верхний FlowPane кнопками-превью мебели */
    private void populateFurniture(String category) {
        furniturePane.getChildren().clear();

        // Словарь: название → путь к иконке превью
        Map<String,String> thumbs = new HashMap<>();

        if ("Спальня".equals(category)) {
            thumbs.put("Кровать", "/org/example/roomplanner/assets/bed.png");
        }
        else if ("Кухня".equals(category)) {
            thumbs.put("Стул", "/org/example/roomplanner/assets/chair.png");
        }
        else if ("Гостиная".equals(category)) {
            thumbs.put("Диван", "/org/example/roomplanner/assets/sofa.png");
        }
        else if ("Ванная".equals(category)) {
            thumbs.put("Раковина", "/org/example/roomplanner/assets/sink.png");
        }

        thumbs.forEach((name, thumbPath) -> {
            Button btn = new Button(name,
                    new ImageView(
                            new Image(getClass().getResourceAsStream(thumbPath),
                                    60, 60, true, true)
                    )
            );
            btn.setContentDisplay(ContentDisplay.TOP);
            btn.getStyleClass().addAll("furniture-item", "action-button");

            // по клику можно добавить модель в комнату:
            // btn.setOnAction(ae -> addModelToRoom("/org/example/roomplanner/models/bed.obj"));

            furniturePane.getChildren().add(btn);
        });
    }

    /** Вращение и панорамирование камеры мышью */
    private void initMouseControl(SubScene sub) {
        sub.setOnMousePressed(e -> {
            anchorX = e.getSceneX();
            anchorY = e.getSceneY();
            anchorAngleX = camRotX.getAngle();
            anchorAngleY = camRotY.getAngle();
        });
        sub.setOnMouseDragged(e -> {
            double dx = e.getSceneX() - anchorX;
            double dy = e.getSceneY() - anchorY;
            if (e.getButton() == MouseButton.PRIMARY) {
                camRotY.setAngle(anchorAngleY + dx * 0.5);
                camRotX.setAngle(anchorAngleX - dy * 0.5);
            } else if (e.getButton() == MouseButton.SECONDARY) {
                camPos.setX(camPos.getX() + dx);
                camPos.setY(camPos.getY() + dy);
            }
        });
        sub.setOnScroll(e -> camPos.setZ(camPos.getZ() + e.getDeltaY()));
    }

    /** Управление камерой с клавиатуры (WASD/QE/RF/Space+Shift) */
    private void initKeyboardControl(SubScene sub) {
        sub.setFocusTraversable(true);
        sub.setOnMouseEntered(e -> sub.requestFocus());
        sub.setOnKeyPressed(this::handleKey);
    }

    private void handleKey(KeyEvent e) {
        double mv = 20, lf = 20, rt = 5;
        switch (e.getCode()) {
            case W     -> camPos.setZ(camPos.getZ() + mv);
            case S     -> camPos.setZ(camPos.getZ() - mv);
            case A     -> camPos.setX(camPos.getX() - mv);
            case D     -> camPos.setX(camPos.getX() + mv);
            case SPACE -> camPos.setY(camPos.getY() - lf);
            case SHIFT -> camPos.setY(camPos.getY() + lf);
            case Q     -> camRotY.setAngle(camRotY.getAngle() - rt);
            case E     -> camRotY.setAngle(camRotY.getAngle() + rt);
            case R     -> camRotX.setAngle(camRotX.getAngle() - rt);
            case F     -> camRotX.setAngle(camRotX.getAngle() + rt);
            default    -> {}
        }
    }

    // При наличии OBJ-импортера можно раскомментировать и реализовать:
    /*
    private void addModelToRoom(String objPath) {
        ObjModelImporter importer = new ObjModelImporter();
        importer.read(getClass().getResource(objPath));
        MeshView[] parts = importer.getImport();
        Group model = new Group(parts);
        model.setScaleX(0.5);
        model.setScaleY(0.5);
        model.setScaleZ(0.5);
        model.setTranslateY(-height*50 + 1);
        room3DGroup.getChildren().add(model);
    }
    */
}

