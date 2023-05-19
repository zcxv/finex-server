package ru.finex.core.cluster;

import org.redisson.api.RObject;
import org.redisson.api.RedissonClient;

import javax.annotation.PreDestroy;

/**
 * @author m0nster.mind
 */
public interface ClusterService {

    /**
     * Redisson client.
     * @return {@link RedissonClient}
     */
    RedissonClient getClient();

    /**
     * Return server role.
     * @return role
     */
    String getRole();

    /**
     * Return name of object with role.
     * Using to access a distributed object. Example:
     * <pre>{@code
     *     var clusterService = ...;
     *     var name = clusterService.getName("com.mygame.MyClass#distributedObject");
     *     var objectBucket = clusterService.getClient().getBucket(name);
     *     var object = objectBucket.get();
     * }</pre>
     * Also, it's equivalent to:
     * <pre>{@code
     *     class MyClass {
     *         @Clustered
     *         RBucket<Object> distributedObject;
     *     }
     * }</pre>
     * @param name name of object
     * @return name of distributed object with role
     */
    String getAddress(String name);

    /**
     * Return name of object with role generated by the caller canonical name.
     * Using to access a distributed object. Example:
     * <pre>{@code
     *     var clusterService = ...;
     *     var name = clusterService.getName(getClass());
     *     var objectBucket = clusterService.getClient().getBucket(name);
     *     var object = objectBucket.get();
     * }</pre>
     * @param caller the caller
     * @return name of distributed object
     */
    String getAddress(Class<?> caller);

    /**
     * Return name of object with role and the field, generated by the caller canonical name.
     * Using to access a distributed object. Example:
     * <pre>{@code
     *     var clusterService = ...;
     *     var name = clusterService.getName(getClass(), "distributedObject");
     *     var objectBucket = clusterService.getClient().getBucket(name);
     *     var object = objectBucket.get();
     * }</pre>
     * Also, it's equivalent to:
     * <pre>{@code
     *     class MyClass {
     *         @Clustered
     *         RBucket<Object> distributedObject;
     *     }
     * }</pre>
     * @param caller the caller
     * @param field the field
     * @return name of distributed object
     */
    String getAddress(Class<?> caller, String field);

    /**
     * Return name of object with role, the method name and the method parameter name.
     * Using to access a distributed object. Example:
     * <pre>{@code
     *     var clusterService = ...;
     *     var name = clusterService.getName(getClass(), "someMethod", "distributedObject");
     *     var objectBucket = clusterService.getClient().getBucket(name);
     *     var object = objectBucket.get();
     * }</pre>
     * Also, it's equivalent to:
     * <pre>{@code
     *     void someMethod(@Clustered RBucket<Object> distributedObject) {
     *         var object = distributedObject.get();
     *     }
     * }</pre>
     * @param caller the caller
     * @param method the method name
     * @param parameter the method parameter name
     * @return name of distributed object
     */
    String getAddress(Class<?> caller, String method, String parameter);

    /**
     * Return count of this server instances (by role) in cluster.
     * @return server instances
     */
    int getInstances();

    /**
     * Register clustered resource as managed.
     * All resources registered as managed will be deleted at {@link PreDestroy PreDestroy} signal
     *  if current server instance with specified role is single.
     * @param resource clustered resource
     */
    void registerManagedResource(RObject resource);

}
