package iwjw;

import com.somelogs.javase.spi.Person;
import org.junit.Test;

import java.util.ServiceLoader;

/**
 * 描述
 *
 * @author LBG - 2020/9/17
 */
public class SpiTest {

	@Test
	public void spiTest() {
		ServiceLoader<Person> loader = ServiceLoader.load(Person.class);
		System.out.println("Java SPI");
		loader.forEach(Person::sayHello);
	}
}
