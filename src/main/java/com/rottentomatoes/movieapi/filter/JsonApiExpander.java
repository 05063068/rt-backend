package com.rottentomatoes.movieapi.filter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * 
 * Copied from https://github.com/flixster/rtv2/blob/master/src/main/java/com/rottentomatoes/utils/JsonApiExpander.java
 * 
 * Expands the input Json by including the related objects in the core object 
 */
public class JsonApiExpander {
    protected static final ObjectMapper mapper = new ObjectMapper();

    public static JsonNode expand(JsonNode capiResponse) {
        JsonNode rootData = capiResponse.get("data");
        JsonNode inclusions = capiResponse.get("included");
        JsonNode metaNode = capiResponse.get("meta");

        Map<String, Map<String, JsonNode>> inclusionRepo = buildInclusionRepo(inclusions);
        JsonNode output = hydrateJapiObject(rootData, inclusionRepo, metaNode);

        return output;
    }

    private static Map<String, Map<String, JsonNode>> buildInclusionRepo(JsonNode inclusions) {
        Map<String, Map<String, JsonNode>> inclusionRepo = new HashMap<>();

        for (JsonNode obj : inclusions) {
            String type = obj.get("type").asText();
            String id = obj.get("id").asText();

            Map<String, JsonNode> typeMap;
            if (inclusionRepo.containsKey(type)) {
                typeMap = inclusionRepo.get(type);
            } else {
                typeMap = new HashMap<String, JsonNode>();
                inclusionRepo.put(type, typeMap);
            }
            typeMap.put(id, obj);
        }
        return inclusionRepo;
    }

    private static ObjectNode getItemNode(JsonNode obj, Map<String, Map<String, JsonNode>> inclusionRepo) {
        JsonNode attributes = obj.get("attributes");
        JsonNode relationships = obj.get("relationships");
        ObjectNode retval = mapper.createObjectNode();
        // 1. Always append id and type if they are there
        if (null != obj.get("id")) {
            retval.put("id", obj.get("id").asText());
        }
        if (null != obj.get("type")) {
            retval.put("type", obj.get("type").asText());
        }

        // 2. Add attributes
        if (attributes != null) {
            merge(retval, attributes);
        }

        // 3. iterate through relationships and recursively hydrate
        if (relationships != null) {
            Iterator<Entry<String, JsonNode>> relationsIter = relationships.fields();
            while (relationsIter.hasNext()) {
                Map.Entry<String, JsonNode> rel = relationsIter.next();

                String relKey = rel.getKey();
                JsonNode relData = rel.getValue().path("data");
                JsonNode metaData = rel.getValue().path("meta");

                if (relData.isMissingNode() || relData.isNull()) {
                    continue;
                } else if (relData.isArray()) {
                    // Relationship data is an array (toMany relationship)
                    // Insert empty ArrayList into retval
                    ArrayNode relList = mapper.createArrayNode();

                    Iterator<JsonNode> relElementsIter = relData.elements();
                    while (relElementsIter.hasNext()) {
                        JsonNode relElement = relElementsIter.next();
                        String type = relElement.get("type").asText();
                        String id = relElement.get("id").asText();

                        // Resolve in inclusionRepo and recursively serialize into relList
                        if (inclusionRepo.containsKey(type) && inclusionRepo.get(type).containsKey(id)) {
                            JsonNode resolvedInclusion = inclusionRepo.get(type).get(id);
                            relList.add(hydrateJapiObject(resolvedInclusion, inclusionRepo));
                        }
                    }

                    retval.set(relKey, relList);
                    if (!metaData.isMissingNode() && !metaData.isNull()) {
                        retval.set(relKey + "Meta", metaData);
                    }
                } else if (relData.isObject()) {
                    // Relationship data is an object (toOne relationship)
                    String type = relData.get("type").asText();
                    String id = relData.get("id").asText();

                    // Resolve in inclusionRepo and recursively serialize into relList
                    if (inclusionRepo.containsKey(type) && inclusionRepo.get(type).containsKey(id)) {
                        JsonNode resolvedInclusion = inclusionRepo.get(type).get(id);
                        retval.set(relKey, hydrateJapiObject(resolvedInclusion, inclusionRepo));
                    } else {
                        // Pass along relationship type and ID if inclusion is not available
                        retval.set(relKey, hydrateJapiObject(relData, inclusionRepo));
                    }
                    if (!metaData.isMissingNode() && !metaData.isNull()) {
                        retval.set(relKey + "Meta", metaData);
                    }
                } else {
                    throw new IllegalStateException(); // Neither array nor object?
                }
            }
        }
        return retval;
    }

    private static JsonNode hydrateJapiObject(JsonNode obj, Map<String, Map<String, JsonNode>> inclusionRepo,
            JsonNode metaNode) {
        if (null == obj) {
            return mapper.createObjectNode();
        } else if (obj instanceof ArrayNode) {
            ObjectNode retval = mapper.createObjectNode();
            ArrayNode dataNode = mapper.createArrayNode();
            Iterator<JsonNode> iter = obj.elements();
            while (iter.hasNext()) {
                JsonNode itemNode = iter.next();
                dataNode.add(getItemNode(itemNode, inclusionRepo));
            }
            retval.set("data", dataNode);
            retval.set("meta", metaNode);
            return retval;
        }
        return getItemNode(obj, inclusionRepo);
    }

    private static JsonNode hydrateJapiObject(JsonNode obj, Map<String, Map<String, JsonNode>> inclusionRepo) {
        if (null == obj) {
            return mapper.createObjectNode();
        }
        return getItemNode(obj, inclusionRepo);
    }

    private static JsonNode merge(JsonNode toNode, JsonNode fromNode) {
        Iterator<String> fieldNames = fromNode.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            JsonNode jsonNode = toNode.get(fieldName);
            // if field exists and is an embedded object
            if (jsonNode != null && jsonNode.isObject()) {
                merge(jsonNode, fromNode.get(fieldName));
            } else {
                if (toNode instanceof ObjectNode) {
                    // Overwrite field
                    JsonNode value = fromNode.get(fieldName);
                    ((ObjectNode) toNode).set(fieldName, value);
                }
            }
        }
        return toNode;
    }
}