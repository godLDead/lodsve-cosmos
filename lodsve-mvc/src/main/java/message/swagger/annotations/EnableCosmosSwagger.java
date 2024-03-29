package message.swagger.annotations;

import com.mangofactory.swagger.paths.RelativeSwaggerPathProvider;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import message.swagger.config.CosmosSwaggerConfiguration;
import org.springframework.context.annotation.Import;

/**
 * 启用cosmos-mvc中得swagger.
 *
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0, 16/1/18 下午10:50
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({CosmosSwaggerRegistrar.class, CosmosSwaggerConfiguration.class})
public @interface EnableCosmosSwagger {
    /**
     * url路径前缀
     *
     * @return
     */
    String prefix() default RelativeSwaggerPathProvider.ROOT;
}
