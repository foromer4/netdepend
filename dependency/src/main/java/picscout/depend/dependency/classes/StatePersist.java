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

public class StatePersist implements IStatePersist {

	public void persist(IMapBuilder builderState) {
		Writer writer = null;
		try {
			String json = JsonWriter.objectToJson(builderState);
			json = JsonWriter.formatJson(json);

			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream("c:\\temp\\json.txt"), "utf-8"));
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
		try {
			inStream = new FileInputStream("c:\\temp\\json.txt");
			JsonReader jr = new JsonReader(inStream);
			map = (IMapBuilder)jr.readObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return map;
	}

}
