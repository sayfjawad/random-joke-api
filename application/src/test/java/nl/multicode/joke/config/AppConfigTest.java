package nl.multicode.joke.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@RunWith(MockitoJUnitRunner.class)
public class AppConfigTest {

    @Test
    public void testModelMapperBeanCreation() {
        // Given
        final var context = new AnnotationConfigApplicationContext();
        context.register(AppConfig.class);
        context.refresh();

        // When
        final var modelMapper = context.getBean(ModelMapper.class);

        // Then
        assertThat(modelMapper).isNotNull();
    }
}
