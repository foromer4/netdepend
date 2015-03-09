package picscout.depend.dependency.classes;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import com.cedarsoftware.util.io.JsonReader;
import com.cedarsoftware.util.io.JsonWriter;
import picscout.depend.dependency.interfaces.IMapBuilder;
import picscout.depend.dependency.interfaces.IStatePersist;
import picscout.depend.dependency.utils.ConfigUtils;

public class StatePersist implements IStatePersist {

	private static String jsonFilePath;

	public StatePersist() {
		jsonFilePath = ConfigUtils.readString("jsonFilePath",
				"c:\\temp\\json.txt");
	}

	public void persist(IMapBuilder builderState) {
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
