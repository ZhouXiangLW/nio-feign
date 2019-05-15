package niofeigncore.register;

import niofeigncore.annotations.EnableNioFeignClients;
import niofeigncore.annotations.NioFeignClient;
import niofeigncore.utils.ArrayUtils;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.filter.AbstractClassTestingTypeFilter;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;
import reactor.util.StringUtils;

import java.util.*;

public class NioFeignClientRegister implements ImportBeanDefinitionRegistrar,
        ResourceLoaderAware, EnvironmentAware {

    private ResourceLoader resourceLoader;
    private Environment environment;

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }


    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata,
                                        BeanDefinitionRegistry registry) {
        registerDefaultConfiguration(metadata, registry);
        registerFeignClients(metadata, registry);
    }

    private void registerDefaultConfiguration(AnnotationMetadata metadata,
                                              BeanDefinitionRegistry registry) {

    }

    private void registerFeignClients(AnnotationMetadata metadata,
                                     BeanDefinitionRegistry registry) {
        ClassPathScanningCandidateComponentProvider scanner = getScanner();
        scanner.setResourceLoader(resourceLoader);

        Set<String> basePackages;

        Map<String, Object> attrs = metadata.getAnnotationAttributes(EnableNioFeignClients.class.getName());
        AnnotationTypeFilter feignClientTypeFilter = new AnnotationTypeFilter(NioFeignClient.class);

        final Class<?>[] clients = attrs == null ? null : (Class<?>[]) attrs.get("clients");
        if (ArrayUtils.isEmpty(clients)) {
            scanner.addIncludeFilter(feignClientTypeFilter);
            basePackages = getBasePackages(metadata);
        } else {
            final Set<String> clientClasses = new HashSet<>();
            basePackages = new HashSet<>();
            for (Class<?> client : clients) {
                basePackages.add(ClassUtils.getPackageName(client));
                clientClasses.add(client.getCanonicalName());
            }
            AbstractClassTestingTypeFilter filter = new AbstractClassTestingTypeFilter() {
                @Override
                protected boolean match(ClassMetadata metadata) {
                    String cleaned = metadata.getClassName().replaceAll("\\$", ".");
                    return clientClasses.contains(cleaned);
                }
            };

            scanner.addIncludeFilter(new AllTypeFilter(Arrays.asList(filter, feignClientTypeFilter)));
        }

    }

    private ClassPathScanningCandidateComponentProvider getScanner() {
        return new ClassPathScanningCandidateComponentProvider(false, this.environment) {
            @Override
            protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
                return beanDefinition.getMetadata().isIndependent() &&
                        (!beanDefinition.getMetadata().isAnnotation());
            }
        };
    }

    private Set<String> getBasePackages(AnnotationMetadata importingClassMetadata) {
        Map<String, Object> attributes = importingClassMetadata
                .getAnnotationAttributes(EnableNioFeignClients.class.getCanonicalName());
        Set<String> basePackages = new HashSet<>();
        String[] packages = ArrayUtils.combine(
                (String[]) Objects.requireNonNull(attributes).get("value"),
                (String[])attributes.get("basePackages"));
        for (String p : packages) {
            if (StringUtils.hasText(p)) {
                basePackages.add(p);
            }
        }
        if (basePackages.isEmpty()) {
            basePackages.add(ClassUtils.getPackageName(importingClassMetadata.getClassName()));
        }
        return basePackages;
    }

}
