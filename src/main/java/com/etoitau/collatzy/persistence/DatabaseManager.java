package com.etoitau.collatzy.persistence;

import com.etoitau.collatzy.domain.CollatzConfig;
import com.etoitau.collatzy.domain.NumberMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Object for getting, processing, and saving entries in the configs database
 */
@Service
public class DatabaseManager {

    private Logger logger = LoggerFactory.getLogger(DatabaseManager.class);

    @Autowired
    private ConfigNodesRepository repository;


    private CollatzConfig getConfigFromEntry(ConfigEntry entry) {
        if (entry == null) { return null; }
        return new CollatzConfig(entry.getD(), entry.getM(), entry.getP());
    }

    /**
     * Deserialize NumberMap from entry
     * @param entry - the database object
     * @return - new NumberMap created from database object
     */
    public NumberMap getMapFromEntry(ConfigEntry entry) {
        logger.info("getMapFromEntry called");
        NumberMap nm = null;
        if (entry != null) {
            nm = NumberMap.mapFromNodeString(getConfigFromEntry(entry), entry.getSerialNodes());
        }
        return nm;
    }

    /**
     * Update database information
     * @param map - NumberMap with new information explored by user
     * @return - NumberMap from database which has been updated with new information and saved
     */
    @Transactional
    public NumberMap saveMap(NumberMap map) {
        // get Entry from database corresponding to this map
        ConfigEntry entry = getEntry(map.getConfig());
        // get version of map from database
        NumberMap fromDB = getMapFromEntry(entry);
        // update with new values provided
        fromDB.addAll(map.getNodes());
        // save back to db
        String serialized = fromDB.nodesToString();
        entry.setSerialNodes(serialized);
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
