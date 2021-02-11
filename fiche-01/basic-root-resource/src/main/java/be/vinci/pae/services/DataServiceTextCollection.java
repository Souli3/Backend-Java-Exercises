package be.vinci.pae.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.text.StringEscapeUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import be.vinci.pae.domain.Text;

public class DataServiceTextCollection {
	private static final String DB_FILE_PATH = "dbText.json";
	private static final String COLLECTION_NAME = "texts";
	private final static ObjectMapper jsonMapper = new ObjectMapper();
	
	private static List<Text> texts;
	
	static {
		texts = loadDataFromFile();
		
	}

	private static List<Text> loadDataFromFile() {
		try {
			JsonNode node = jsonMapper.readTree(Paths.get(DB_FILE_PATH).toFile());
			JsonNode collection = node.get(COLLECTION_NAME);	
			
			if(collection == null)
				return new ArrayList<Text>();
			return jsonMapper.readerForListOf(Text.class).readValue(node.get(COLLECTION_NAME));
			
		}catch(IOException e) {
			return new ArrayList<Text>();
		}
		
	}
	public static List<Text> getAllText(){
		return texts;
	}
	public static Text addText(Text text) {
		text.setId(nextTextid());
		text.setContent(StringEscapeUtils.escapeHtml4(text.getContent()));
		texts.add(text);
		System.out.println("p2");
		saveDataToFile();
		System.out.println("p3");
		return text;
		
		
	}
	public static List<Text> getAllTextFilter(String level){
		return texts.stream().filter(x->x.getLevel().equals(level)).collect(Collectors.toList());
	}
	
	public static void saveDataToFile() {
		try {
			Path pathToDb = Paths.get(DB_FILE_PATH);
			if (!Files.exists(pathToDb)) {
				
				// write a new collection to the db file
				ObjectNode newCollection = jsonMapper.createObjectNode().putPOJO(COLLECTION_NAME, texts);
				jsonMapper.writeValue(pathToDb.toFile(), newCollection);
				return;

			}
			JsonNode allCollections = jsonMapper.readTree(pathToDb.toFile());

			if (allCollections.has(COLLECTION_NAME)) {// remove current collection
				((ObjectNode) allCollections).remove(COLLECTION_NAME);
			}

			// create a new JsonNode and add it to allCollections
			String currentCollectionAsString = jsonMapper.writeValueAsString(texts);
			JsonNode updatedCollection = jsonMapper.readTree(currentCollectionAsString);
			((ObjectNode) allCollections).putPOJO(COLLECTION_NAME, updatedCollection);
		
			jsonMapper.writeValue(pathToDb.toFile(), allCollections);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static int nextTextid() {
		return texts.size()==0 ? 1 : texts.get(texts.size()-1).getId()+1;
	}
	
}
