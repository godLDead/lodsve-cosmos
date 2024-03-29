package message.config.loader.properties;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import message.config.core.InitConfigPath;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * 配置文件解析.
 *
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0, 14-8-17 下午9:44
 */
public class ConfigurationLoader {
    private static Properties prop = new Properties();

    private ConfigurationLoader() {
    }

    public static Properties getConfigFileProperties(Resource... resources) throws IOException {
        Properties prop = new Properties();

        loadProperties(prop, resources);

        prop.put("params.root", InitConfigPath.getParamsRoot());
        return prop;
    }

    public static Properties getConfigProperties() {
        return prop;
    }

    private static void loadProperties(Properties prop, Resource[] resources) {
        for (Resource resource : resources) {
            try {
                PropertiesLoaderUtils.fillProperties(prop, new EncodedResource(resource, "UTF-8"));
            } catch (IOException e) {
                throw new RuntimeException("load配置文件发生异常");
            }
        }
    }

    public static Resource getConfigFile(String fileName) {
        ResourceLoader loader = new DefaultResourceLoader();

        return loader.getResource("file:" + InitConfigPath.getParamsRoot() + File.separator + "files" + File.separator + fileName);
    }

    public static void init() throws Exception {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("file:" + InitConfigPath.getParamsRoot() + "/*.properties");

        loadProperties(prop, resources);
        prop.put("params.root", InitConfigPath.getParamsRoot());
    }
}
