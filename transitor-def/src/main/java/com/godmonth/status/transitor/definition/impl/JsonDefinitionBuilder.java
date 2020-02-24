package com.godmonth.status.transitor.definition.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.godmonth.status.transitor.definition.intf.StatusDefinition;
import com.godmonth.status.transitor.definition.intf.TriggerDefinition;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonDefinitionBuilder<STATUS, TRIGGER> {

    private Resource resource;

    private Class<? extends StatusDefinition<STATUS, TRIGGER>> definitionClass;

    public Map<STATUS, Map<TRIGGER, STATUS>> build() {

        TypeFactory typeFactory = TypeFactory.defaultInstance();

        ObjectMapper objectMapper = new ObjectMapper();

        CollectionType collectionType = typeFactory.constructCollectionType(List.class, definitionClass);
        List<StatusDefinition<STATUS, TRIGGER>> statusDefinitions;
        try (InputStream inputStream = resource.getInputStream()) {
            statusDefinitions = objectMapper.readValue(inputStream, collectionType);
        } catch (IOException e) {
            throw new ContextedRuntimeException(e);
        }
        Map<STATUS, Map<TRIGGER, STATUS>> statusConfigs = new HashMap<>();

        for (StatusDefinition<STATUS, TRIGGER> statusDefinition : statusDefinitions) {
            Validate.notNull(statusDefinition.getStatus(), "status is null");
            Validate.notNull(statusDefinition.getTriggers(), "triggerDefinitions is null");
            Map<TRIGGER, STATUS> triggerConfig = new HashMap<>();
            for (TriggerDefinition<TRIGGER, STATUS> triggerDefinition : statusDefinition.getTriggers()) {
                Validate.notNull(triggerDefinition.getTrigger(), "trigger is null");
                Validate.notNull(triggerDefinition.getNextStatus(), "nextStatus is null");
                triggerConfig.put(triggerDefinition.getTrigger(), triggerDefinition.getNextStatus());
            }
            statusConfigs.put(statusDefinition.getStatus(), triggerConfig);
        }
        return statusConfigs;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public void setDefinitionClass(Class<? extends StatusDefinition<STATUS, TRIGGER>> definitionClass) {
        this.definitionClass = definitionClass;
    }

}
