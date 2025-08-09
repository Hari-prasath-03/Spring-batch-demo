package tech.hariprasath.batchdemo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;
import tech.hariprasath.batchdemo.entity.Person;
import tech.hariprasath.batchdemo.repository.PersonRepository;

@Configuration
@RequiredArgsConstructor
public class SpringBatchConfig {

    private final PersonRepository personRepository;

    @Bean
    public FlatFileItemReader<Person> reader() {
        return new FlatFileItemReaderBuilder<Person>()
                .name("personReader")
                .resource(new ClassPathResource("demo.csv"))
                .linesToSkip(1)
                .lineMapper(lineMapper())
                .targetType(Person.class)
                .build();
    }

    private LineMapper<Person> lineMapper() {
        DefaultLineMapper<Person> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(",");
        tokenizer.setStrict(false);
        tokenizer.setNames("userId", "firstName", "lastName", "sex", "email", "phone", "dateOfBirth", "jobTitle");

        BeanWrapperFieldSetMapper<Person> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Person.class);

        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }

    @Bean
    public PersonProcessor processor() {
        return new PersonProcessor();
    }

    @Bean
    public Job job(JobRepository jobRepository, Step step) {
        return new JobBuilder("loadPerson", jobRepository)
                .start(step)
                .build();
    }

    @Bean
    public RepositoryItemWriter<Person> writer() {
        RepositoryItemWriter<Person> writer = new RepositoryItemWriter<>();
        writer.setMethodName("save");
        writer.setRepository(personRepository);
        return writer;
    }

    @Bean
    public Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("loadPersonStep", jobRepository)
                .<Person, Person>chunk(10, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }
}
