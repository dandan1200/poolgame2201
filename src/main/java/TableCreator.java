import java.util.List;

public class TableCreator implements GameObjectCreator{
    public GameObject create(){
        return new Table();
    }
    public static void setAttributes(Table t, String colour, double friction, long sizeX, long sizeY, List<GameObject> ballsList){
        t.setAttributes(colour, friction, sizeX, sizeY, ballsList);
    }


}
