package mvillalobos.rd.statefun.ping.implementation.configurations;

import mvillalobos.rd.statefun.ping.api.annotations.Statefun;
import mvillalobos.rd.statefun.ping.api.annotations.ValueSpecMarker;
import org.apache.flink.statefun.sdk.java.StatefulFunction;
import org.apache.flink.statefun.sdk.java.StatefulFunctionSpec;
import org.apache.flink.statefun.sdk.java.StatefulFunctions;
import org.apache.flink.statefun.sdk.java.TypeName;
import org.apache.flink.statefun.sdk.java.ValueSpec;
import org.apache.flink.statefun.sdk.java.handler.RequestReplyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Field;
import java.util.Map;

@Configuration
public class StateFunConfig {

	private final static Logger logger = LoggerFactory.getLogger(StateFunConfig.class);

	@Bean
	public RequestReplyHandler requestReplyHandler(WebApplicationContext context) {
		final StatefulFunctions statefulFunctions = new StatefulFunctions();
		final Map<String, Object> beansWithAnnotation = context.getBeansWithAnnotation(Statefun.class);

		beansWithAnnotation.forEach((name, bean) -> {

			// this is hardcore spring inversion of control and reflection magic
			// used to configure the request reply handler
			// first it gets all the beans annotated with StateFun
			// it dynamically creates a type name based on the StateFun annotation
			// then it uses reflection to find the fields in that stateful function that are
			// annotated with ValueSpecDeclaration. It adds that to the spec builder.
			// then it creates a supplier for the spec builder, and builds the spec

			final Statefun statefun = bean.getClass().getAnnotation(Statefun.class);
			final TypeName typeName = TypeName.typeNameOf(statefun.namespace(), statefun.name());
			final StatefulFunctionSpec.Builder specBuilder = StatefulFunctionSpec.builder(typeName);
			logger.info("Configure RequestReplyHandler name: {}", name);

			for (Field field : bean.getClass().getDeclaredFields()) {
				if (field.isAnnotationPresent(ValueSpecMarker.class)) {
					field.setAccessible(true);
					try {
						final ValueSpec<?> valueSpec = (ValueSpec<?>) field.get(bean);
						logger.info("value spec name: {}, type: {}", valueSpec.name(), valueSpec.typeName());
						specBuilder.withValueSpec(valueSpec);
					} catch (IllegalAccessException e) {
						logger.error("could not access value spec.", e);
						throw new RuntimeException(e);
					}
				}
			}
			specBuilder.withSupplier(() -> context.getBean(name, StatefulFunction.class));

			statefulFunctions.withStatefulFunction(specBuilder.build());
		});
		return statefulFunctions.requestReplyHandler();
	}
}
