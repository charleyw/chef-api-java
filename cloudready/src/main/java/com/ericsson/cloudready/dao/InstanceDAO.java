package com.ericsson.cloudready.dao;

import java.util.List;

import com.ericsson.cloudready.model.Instance;

public interface InstanceDAO {
    public Instance addInstance(Instance instance);
    public Instance updateInstance(Instance instance);
    public List<Instance> getAllInstances();
    public Instance getInstanceById(String id);
    public List<Instance> getInstancesByUser(String userId);
    public void deleteInstance(String id);
}
