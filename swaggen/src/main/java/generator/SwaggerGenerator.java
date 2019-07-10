package generator;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import domain.output.Swagger;
import domain.output.SwaggerEndpoint;
import domain.output.SwaggerInfo;
import utils.io.FileMapper;

/**
 * Connects the plugin with the SwaggerGen logic
 * 
 * @author Danny Petitclerc (8074166)
 */

public class SwaggerGenerator {
	
	/**
	 * Given an array of classes that contain methods annotated with SwaggerGen,
	 * generate a list of associated paths and POJO
	 * 
	 * @param klasses an array of classes that contain methods annotated with SwaggerGen
	 * 
	 * @throws JsonParseException JsonParseException
	 * @throws JsonMappingException JsonMappingException
	 * @throws IOException IOException
	 **/
	public static void generateSwaggerFile(Class<?>[] klasses) throws JsonParseException, JsonMappingException, IOException {
		
		// Create Paths
		Map<String, SwaggerEndpoint> paths = generator.PathGenerator.generatePathsFromClassList(klasses);

		Swagger swagger = new Swagger();
		swagger.setVersion("3.0.0");
		
		SwaggerInfo info = new SwaggerInfo();
		// These will be read from a config file.
		info.setTitle("Title");
		info.setDescription("Description");
		info.setVersion("0.0");
		
		swagger.setInfo(info);
		swagger.setPaths(SwaggerEndpoint.convertToValid(paths));

		createYamlFiles(swagger);
		
	}

	private static void createYamlFiles(Swagger swagger) throws JsonParseException, JsonMappingException, IOException {
		for(String path: swagger.getPaths().keySet()) {
			File file = new File(path.substring(1));
			file.mkdirs();
			// yaml file's name will be the base path and the class name
			String name = "/" + path.substring(1).replaceAll("/", "_") + "-test.yaml";
			FileMapper.classToYaml((path + name).substring(1), swagger);
		}
	}
	
}
