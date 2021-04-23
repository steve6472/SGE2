package steve6472.sge.main.node.core;

import org.json.JSONObject;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 19.04.2020
 * Project: NoiseGenerator
 *
 ***********************/
public interface ExtraData
{
	JSONObject save();

	void load(JSONObject json);
}
