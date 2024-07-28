package com.sgse.configuration;

import java.io.IOException;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 * @author Juan Carlos Arguello Ortiz
 * @version 1.0
 */
public class SimpleGrantedAuthorityDeserializer extends StdDeserializer<SimpleGrantedAuthority> {

	private static final long serialVersionUID = 1L;

	public SimpleGrantedAuthorityDeserializer() {
		super(SimpleGrantedAuthority.class);
	}

	// Deserializa un JSON
	@Override
	public SimpleGrantedAuthority deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		JsonNode tree = p.getCodec().readTree(p);
		return new SimpleGrantedAuthority(tree.get("authority").textValue());
	}
}