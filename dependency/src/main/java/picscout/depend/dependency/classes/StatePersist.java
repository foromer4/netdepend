package picscout.depend.dependency.classes;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.cedarsoftware.util.io.JsonReader;
import com.cedarsoftware.util.io.JsonWriter;

import picscout.depend.dependency.interfaces.IMapBuilder;
import picscout.depend.dependency.interfaces.IStatePersist;
import picscout.depend.dependency.utils.ConfigUtils;

public class StatePersist implements IStatePersist {

	private static String jsonFilePath;
	private static final Logger logger = LogManager
			.getLogger(StatePersist.class.getName());
	public StatePersist() {
		jsonFilePath = ConfigUtils.readString("jsonFilePath",
				"c:\\temp\\json.txt");
	}

	public void persist(IMapBuilder builderState) {
		
		logger.info("Persisting state to:" + jsonFilePath);
		Writer writer = null;
		try {
			String json = JsonWriter.objectToJson(builderState);
			json = JsonWriter.formatJson(json);

			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(jsonFilePath), "utf-8"));
			writer.write(json);
		} catch (IOException ex) {
			// report
		} finally {
			try {
				writer.close();
			} catch (Exception ex) {
			}
		}

	}

	public IMapBuilder load() {
		logger.info("Loading state from:" + jsonFilePath);
		FileInputStream inStream;
		IMapBuilder map = null;
		JsonReader jr = null;
		try {
			inStream = new FileInputStream(jsonFilePath);
			jr = new JsonReader(inStream);
			map = (IMapBuilder) jr.readObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (jr != null) {
				jr.close();
			}
		}

		return map;
	}

}
