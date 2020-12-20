package com.godmonth.status.transitor.definition.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class StringJsonDefinitionBuilder {


    public static Map<String, Map<String, String>> build(Resource resource) {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = null;
        try (InputStream inputStream = resource.getInputStream()) {
            rootNode = objectMapper.readTree(inputStream);
        } catch (IOException e) {
            throw new ContextedRuntimeException(e);
        }

        Map<String, Map<String, String>> statusConfigs = new HashMap<>();
        for (JsonNode jsonNode : rootNode) {
            String status = jsonNode.get("status").textValue();
            JsonNode triggerList = jsonNode.get("triggers");
            Map<String, String> triggerMap = new HashMap<>();
            for (JsonNode trigger : triggerList) {
                String trigger1 = trigger.get("trigger").textValue();
                String nextStatus = trigger.get("nextStatus").textValue();
                triggerMap.put(trigger1, nextStatus);
            }
            statusConfigs.put(status,triggerMap);
        }
        return statusConfigs;
    }


}
