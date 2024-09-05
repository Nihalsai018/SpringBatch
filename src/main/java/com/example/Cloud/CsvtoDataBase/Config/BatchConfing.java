package com.example.Cloud.CsvtoDataBase.Config;

import com.example.Cloud.CsvtoDataBase.Entity.Product;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;
import java.util.Date;


@Configuration
@EnableBatchProcessing
public class BatchConfing {

	//1. reader
	@Bean
	public ItemReader<Product> reader() {
		FlatFileItemReader<Product> reader = new FlatFileItemReader<>();
		//1. Load file name + location
		reader.setResource(new ClassPathResource("products.csv"));
		//2. Read Data Line by Line
		reader.setLineMapper(new DefaultLineMapper<Product>() {{
			//3. tkenize data, provide nameso
			setLineTokenizer(new DelimitedLineTokenizer() {{
				setDelimiter(DELIMITER_COMMA);
				setNames("id","product","cost");
			}});

			//4. convert to object
			setFieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
				setTargetType(Product.class);
			}});
		}});
		return reader;
	}
	//2. processor
	/*@Bean
	public ItemProcessor<Product, Product> procesor() {
		return new ProductProcessor();
	}*/
	@Bean
	public ItemProcessor<Product, Product> procesor() {
		return (item) -> {
			double cost = item.getCost();

			item.setGst(cost*12/100.0);
			item.setProdDis(cost*20/100.0);

			return item;
		};
	}

	@Autowired
	private DataSource dataSource;
	//3. writer
	@Bean
	public ItemWriter<Product> writer() {
		JdbcBatchItemWriter<Product> writer = new JdbcBatchItemWriter<>();
		writer.setSql("INSERT INTO product (id, product, cost, gst, prodDis) VALUES (:id, :product, :cost, :gst, :prodDis)");
		writer.setDataSource(dataSource);
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
		return writer;
	}
	public final void test() {
	    System.out.println("nihal");
	    
	}

	//4. listener
	/*@Bean
	public JobExecutionListener listener() {
		return new MyJobListener();
	}*/

	@Bean
	public JobExecutionListener listener() {
		return new JobExecutionListener() {

			public void beforeJob(JobExecution je) {
				System.out.println("ON STARTUP => " + je.getStatus());
				System.out.println("ON STARTUP => " + new Date());
			}

			public void afterJob(JobExecution je) {
				System.out.println("ON END => " + je.getStatus());
				System.out.println("ON END => " + new Date());
			}
		};

	}

	//5. StepBuilderFactory
	@Autowired
	private StepBuilderFactory sf;

	//6. Step
	@Bean
	public Step stepA() {
		return sf.get("stepA")
				. <Product,Product>chunk(5)
				.reader(reader())
				.processor(procesor())
				.writer(writer())
				.build();
	}
/*	public Step stepB() {
		return sf.get("stepA")
				.  <Product,Product>chunk(3)
				.reader(reader())
				.processor(procesor())
				.writer(writer())
				.build(); */


	//7. JobBuilderFactory
	@Autowired
	private JobBuilderFactory jf;

	//8. Job
	@Bean
	public Job jobA() {
		return jf.get("jobA")
				.incrementer(new RunIdIncrementer())
				.listener(listener())
				.start(stepA())
				.build()
				;
	}
}
