import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ConfigReader {

	/**
	 * You will probably not want to use a static method/class for this.
	 * 
	 * This is just an example of how to access different parts of the json
	 * 
	 * @param path The path of the json file to read
	 */

	private GameObject table;
	private List<GameObject> ballsList = new ArrayList<GameObject>();

	public ConfigReader(String path) {

		JSONParser parser = new JSONParser();
		try {
			Object object = parser.parse(new FileReader(path));

			// convert Object to JSONObject
			JSONObject jsonObject = (JSONObject) object;

			// reading the Table section:
			JSONObject jsonTable = (JSONObject) jsonObject.get("Table");

			// reading a value from the table section
			String tableColour = (String) jsonTable.get("colour");

			// reading a coordinate from the nested section within the table
			// note that the table x and y are of type Long (i.e. they are integers)
			Long tableX = (Long) ((JSONObject) jsonTable.get("size")).get("x");
			Long tableY = (Long) ((JSONObject) jsonTable.get("size")).get("y");

			// getting the friction level.
			// This is a double which should affect the rate at which the balls slow down
			Double tableFriction = (Double) jsonTable.get("friction");

			// TODO: delete me, this is just a demonstration:
			//System.out.println("Table colour: " + tableColour + ", x: " + tableX + ", friction: " + tableFriction);

			// reading the "Balls" section:
			JSONObject jsonBalls = (JSONObject) jsonObject.get("Balls");

			// reading the "Balls: ball" array:
			JSONArray jsonBallsBall = (JSONArray) jsonBalls.get("ball");

			//Create Ball and table factory
			GameObjectCreator ballCreator = new BallCreator();
			GameObjectCreator tableCreator = new TableCreator();

			// reading from the array:
			for (Object obj : jsonBallsBall) {
				JSONObject jsonBall = (JSONObject) obj;

				// the ball colour is a String
				String colour = jsonBall.get("colour").toString();

				// the ball position, velocity, mass are all doubles
				Double positionX = (Double) ((JSONObject) jsonBall.get("position")).get("x");
				Double positionY = (Double) ((JSONObject) jsonBall.get("position")).get("y");

				// get the velocity
				Double velocityX = (Double) ((JSONObject) jsonBall.get("velocity")).get("x");
				Double velocityY = (Double) ((JSONObject) jsonBall.get("velocity")).get("y");

				//get the mass
				Double mass = (Double) jsonBall.get("mass");


				//Create ball object
				GameObject newBall = ballCreator.create();
				BallCreator.setAttributes((Ball) newBall, colour, positionX, positionY, velocityX, velocityY, mass);
				this.ballsList.add(newBall);
				// TODO: delete me, this is just a demonstration:
				//System.out.println("Ball x: " + positionX + ", mass: " + mass);
			}
			GameObject newTable = tableCreator.create();
			TableCreator.setAttributes((Table) newTable, tableColour, tableFriction, tableX, tableY, ballsList);
			this.table = newTable;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//return ball objects and table object
	}

	public GameObject getTable() { return (Table) this.table;
	}
	public List<GameObject> getBallsList() {
		return this.ballsList;
	}

	/**
	 * Your main method will probably be in another file!
	 * 
	 * @param args First argument is the path to the config file
	 */
//	public static void main(String[] args) {
//		// if a command line argument is provided, that should be used as the path
//		// if not, you can hard-code a default. e.g. "src/main/resources/config.json"
//		// this makes it easier to test your program with different config files
//		String configPath;
//		if (args.length > 0) {
//			configPath = args[0];
//		} else {
//			configPath = "src/main/resources/config.json";
//		}
//		// parse the file:
//		ConfigReader.parse(configPath);
//	}

}
