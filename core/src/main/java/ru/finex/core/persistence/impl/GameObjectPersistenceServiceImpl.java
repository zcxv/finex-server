package ru.finex.core.persistence.impl;

import lombok.RequiredArgsConstructor;
import ru.finex.core.component.ComponentService;
import ru.finex.core.object.GameObject;
import ru.finex.core.persistence.GameObjectPersistenceService;
import ru.finex.core.persistence.ObjectPersistenceService;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author m0nster.mind
 */
@Singleton
@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class GameObjectPersistenceServiceImpl implements GameObjectPersistenceService {

    private final ObjectPersistenceService objectPersistenceService;
    private final ComponentService componentService;

    @Override
    public void persist(GameObject gameObject) {
        componentService.getComponents(gameObject)
            .forEach(objectPersistenceService::persist);
    }

    @Override
    public void restore(GameObject gameObject) {
        componentService.getComponents(gameObject)
            .forEach(objectPersistenceService::restore);
    }

}
