package com.etoitau.collatzy.persistence;

import com.etoitau.collatzy.CollatzyController;
import com.etoitau.collatzy.domain.CollatzConfig;
import com.etoitau.collatzy.domain.NodeWithResult;
import com.etoitau.collatzy.domain.NumberMap;
import com.etoitau.collatzy.domain.PathNode;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class DatabaseManager {

    private Logger logger = LoggerFactory.getLogger(DatabaseManager.class);

    @Autowired
    private ConfigNodesRepository repository;

    private Gson gson = new Gson();


    public CollatzConfig getConfigFromEntry(ConfigEntry entry) {
        if (entry == null) { return null; }
        return new CollatzConfig(entry.getD(), entry.getM(), entry.getP());
    }

    public NumberMap getMapFromEntry(ConfigEntry entry) {
        logger.info("getMapFromEntry called");
        Type nodeListType = new TypeToken<ArrayList<PathNode>>(){}.getType();
        NumberMap nm = new NumberMap(getConfigFromEntry(entry));
        if (entry != null) {
            ArrayList<PathNode> nodes = gson.fromJson(entry.getJsonNodes(), nodeListType);
            if (nodes != null && !nodes.isEmpty()) {
                for (NodeWithResult node: nodes) {
                    nm.add(node);
                }
            }
        }
        return nm;
    }

    @Transactional
    public NumberMap saveMap(NumberMap map) {
        // get Entry corresponding
        ConfigEntry entry = getEntry(map.getConfig());
        // get current from database
        NumberMap fromDB = getMapFromEntry(entry);
        // update with new values
        fromDB.addAll(map.getNodes());
        // save back to db
        String jsonMap = gson.toJson(fromDB.getNodes());
        entry.setJsonNodes(jsonMap);
        repository.save(entry);
        return fromDB;
    }

    @Transactional
    public ConfigEntry getEntry(CollatzConfig config) {

        List<ConfigEntry> results = repository.findByDAndMAndP(config.getD(), config.getM(), config.getP());
        return (results.isEmpty())? new ConfigEntry(config): results.get(0);
    }

    public NumberMap getMap(CollatzConfig config) {
        return getMapFromEntry(getEntry(config));
    }

    @Transactional
    public void delete(CollatzConfig config) {
        ConfigEntry entry = getEntry(config);
        if (entry != null) {
            repository.delete(entry);
        }
    }

}
