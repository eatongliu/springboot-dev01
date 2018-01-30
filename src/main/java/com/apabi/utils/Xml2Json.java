package com.apabi.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

public class Xml2Json extends DefaultHandler {

	class Node {
		String name;
		StringBuffer value;
		Node parent;
		Map<String, JsonNode> childrens;

		public Node(String name) {
			this.name = name;
			this.value = new StringBuffer();
			this.childrens = new LinkedHashMap<String, JsonNode>();
		}
	}

	private Node current = new Node("");
	private ObjectMapper jsonMapper = new ObjectMapper();

	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		Node node = new Node(qName);
		node.parent = current;
		current = node;

		for (int idx = 0; idx < attributes.getLength(); idx++) {
			current.childrens.put(attributes.getQName(idx),
					TextNode.valueOf(attributes.getValue(idx)));
		}
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		JsonNode node = null;
		if (current.childrens.size() > 0) {
			ObjectNode objNd = jsonMapper.createObjectNode();
			objNd.setAll(current.childrens);

			if (current.value.length() > 0)
				objNd.put("text", current.value.toString());
			node = objNd;
		} else if (current.value.length() > 0) {
			node = TextNode.valueOf(current.value.toString());
		}

		current = current.parent;
		if (node != null) {
			appendNode(current.childrens, qName, node);			
		}
		//format json
	}

	protected void appendNode(Map<String, JsonNode> nodeMap, String key, JsonNode node) {
		if (nodeMap.containsKey(key)) {
			JsonNode child = nodeMap.get(key);
			ArrayNode array;
			if (!child.isArray()){
				array = jsonMapper.createArrayNode();
				array.add(child);		
				nodeMap.put(key, array);
			}else
				array = (ArrayNode) child;
			array.add(node);
		}else if(nodeMap.containsKey("count")&&nodeMap.get("count").textValue().equals("1")&&(key.equals("Places")||key.equals("Paper")||key.equals("Article")||key.equals("Image")||key.equals("Period")||key.equals("Page"))){
				ArrayNode array;
				array = jsonMapper.createArrayNode();
				array.add(node);
				nodeMap.put(key, array);
			
		}else{
			nodeMap.put(key, node);

		}
	}

	public void characters(char ch[], int start, int length)
			throws SAXException {
		String text = new String(ch, start, length).trim();
		if (!text.isEmpty())
			current.value.append(text);
	}

	public String parse(InputStream is) throws ParserConfigurationException,
			SAXException, IOException {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		parser.parse(is, this);
		String json = "{}"; 				
		for (JsonNode node : current.childrens.values())
			json = node.toString();
		return json;
	}	
	
	public JsonNode parseToNode(InputStream is) throws ParserConfigurationException,
		SAXException, IOException {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		parser.parse(is, this);
		JsonNode jsonNode = null;
		for (JsonNode node : current.childrens.values())
			jsonNode = node;
	return jsonNode;
}
	
	
	public String parse(String xml) throws ParserConfigurationException, SAXException, IOException{
		ByteArrayInputStream is = new ByteArrayInputStream(xml.getBytes("UTF-8"));
		return parse(is);
	}

	public static void main(String[] args) throws ParserConfigurationException,
			SAXException, IOException {
		/*String xml = "<root v=\"1\" r=\"2\" >\n<title v='3'>ok</title><title>aaa</title><book />\n</root>";
		ByteArrayInputStream is = new ByteArrayInputStream(
				xml.getBytes("UTF-8"));
		System.out.println(new Xml2Json().parse(xml));*/
		
	}

	
	
}
