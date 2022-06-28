package ru.finex.ws.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import ru.finex.core.utils.ClassUtils;
import ru.finex.ws.model.entity.GameObjectComponentPrototype;
import ru.finex.ws.model.prototype.ComponentPrototype;
import ru.finex.ws.repository.GameObjectComponentPrototypeRepository;
import ru.finex.ws.service.GameObjectPrototypeService;

import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author m0nster.mind
 */
@Singleton
@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class GameObjectPrototypeServiceImpl implements GameObjectPrototypeService {

    private final GameObjectComponentPrototypeRepository componentRepository;
    private final ObjectMapper objectMapper;

    @Override
    public List<ComponentPrototype> getPrototypesByName(String prototypeName) {
        return componentRepository.findPrototypesByPrototypeName(prototypeName)
            .stream()
            .map(this::mapPrototype)
            .collect(Collectors.toList());
    }

    private ComponentPrototype mapPrototype(GameObjectComponentPrototype prototype) {
        Class<? extends ComponentPrototype> type = ClassUtils.forName(prototype.getComponent())
            .asSubclass(ComponentPrototype.class);

        try {
            return objectMapper.readValue(prototype.getData(), type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
