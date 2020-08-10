package properter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

/**************************************************************
 ***       S  T  A  G  E    多模块依赖项目                    ***
 **************************************************************
 *                                                            *
 *         Project Name : base                                *
 *                                                            *
 *         File Name : DsProperter.java                       *
 *                                                            *
 *         Programmer : Mr.zhang                              *
 *                                                            *
 *         Start Date : 2020/7/24 17:28                       *
 *                                                            *
 *         Last Update : 2020/7/24 17:28                      *
 *                                                            *
 *------------------------------------------------------------*
 * 功能:                                                       *
 *   每个模块下配置文件Application.properties或者Yaml文件的读取类   *
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
public class DsProperter{

    public static final String PROPERTY_FILE = "application.properties";
    public static final String PROPERTY_FILE_YAML = "application.yml";
    public static final String PROPERTIES_START = "spring.datasource.dynamic";
    private static DsProperter instance = null;
    private static final Properties paraProps = new Properties();

    private DsProperter() {

        InputStream properties = getClass().getResourceAsStream("/" + PROPERTY_FILE);
        InputStream yaml = getClass().getResourceAsStream("/" + PROPERTY_FILE_YAML);
        try {
            if (properties != null) {
                paraProps.load(properties);
            } else {
                paraProps.load(yaml);
            }
        } catch (Exception e) {

            System.err.println("Can not read application.properties file!");

        } finally {
            try {
                assert properties != null;
                properties.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取实例
     */
    public static synchronized DsProperter getInstance() {

        if (instance == null) {
            instance = new DsProperter();
        }
        return instance;

    }

    /**
     * 获取属性
     * @param paraName key
     */
    public String getProperty(String paraName) {

        String property = "";
        try {
            property = paraProps.getProperty(paraName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return property;
    }

    /**
     * 返回配置文件下的指定配置文件的所有key
     */
    public List<String> getList(String preName) {
        List<String> collect = null;
        try {
            Set<String> strings = paraProps.stringPropertyNames();
            collect = strings.stream()
                    .filter(x -> x.startsWith(preName)).collect(Collectors.toList());
            Collections.sort(collect);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return collect;
    }

}
