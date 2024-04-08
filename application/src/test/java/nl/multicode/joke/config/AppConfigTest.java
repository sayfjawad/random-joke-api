package nl.multicode.joke.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@RunWith(MockitoJUnitRunner.class)
public class AppConfigTest {

    @Test
    @DisplayName("Given the AppConfig class is registered, "
            + "when the context is refreshed, "
            + "then ModelMapper bean should be created")
    public void testModelMapperBeanCreation() {
        // Given
        final var context = new AnnotationConfigApplicationContext();

        // When
        context.register(AppConfig.class);
        context.refresh();
        final var modelMapper = context.getBean(ModelMapper.class);

        // Then
        assertThat(modelMapper).isNotNull();
    }
}
