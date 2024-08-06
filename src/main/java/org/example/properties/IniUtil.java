package org.example.properties;

import org.ini4j.Profile;
import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;

public class IniUtil {
    private Wini  config = null;

    public IniUtil(String configPath) throws IOException {
        File file = new File(configPath);

        config = new Wini(file);

        config.load();
    }

    public void load() throws IOException {
        config.load();
    }

    public Profile.Section getSection(String section) {
        return config.get(section);
    }

    public String getValue(String section, String key) {
        return config.get(section, key);
    }

    public String getValue(String section, String key, String defaultValue) {
        String rsValue = config.get(section, key);

        if(rsValue == null || rsValue.length() == 0){
            return defaultValue;
        }else{
            return rsValue;
        }
    }

    public <T> T getValue(String section, String key, Class<T> clazz) {
        return config.get(section, key, clazz);
    }

    public void put (String section, String key, Object value) {
        config.put(section, key, value);
    }

    public void store () throws IOException {
        config.load();
        config.store();
        config.load();
    }

    /**
     * 여러곳에서 사용한다면, 반드시 이전데이터는 다시 로드해서 저장하고 저장한 데이터를 다시 로드하도록 하자.
     * @param section
     * @param key
     * @param value
     * @throws IOException
     */
    public void store(String section, String key, Object value) throws  IOException {
        /**
         * 이렇게 매번 load를 하지 않으면 만약 다른 process 에서 해당 함수를 사용할때 문제가 생길수 있다.
         */
        config.load();
        config.put(section, key, value);
        config.store();
        config.load();
    }

    public void putAll(IniUtil target) throws  IOException {
        config.putAll(target.config);
    }
}