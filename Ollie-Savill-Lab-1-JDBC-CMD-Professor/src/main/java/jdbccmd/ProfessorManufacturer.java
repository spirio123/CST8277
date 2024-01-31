/****************************************************************
 * File:  ProfessorManufacturer.java Course materials (23W) CST 8277
 * 
 * @date December 2022
 * @author Teddy Yap
 */
package jdbccmd;

import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Type;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.jemos.podam.api.AttributeMetadata;
import uk.co.jemos.podam.api.DataProviderStrategy;
import uk.co.jemos.podam.typeManufacturers.StringTypeManufacturerImpl;

/**
 * This class used the PODAM (POJO Data Mocker) library.  The idea is to provide the needed data and this class randomize
 * data for the given object.
 * 
 * @author Teddy Yap
 * @version December 2022
 */
//factory design pattern
public class ProfessorManufacturer extends StringTypeManufacturerImpl {
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	//Name of the fields in the Professor class
	private static final String LASTNAME_FIELD = "lastName";
	private static final String FIRSTNAME_FIELD = "firstName";
	private static final String EMAIL_FIELD = "email";
	private static final String PHONENUMBER_FIELD = "phoneNumber";
	//TODONE Add a String constant DEGREE_FIELD
	private static final String DEGREE_FIELD = "degree";
	//TODONE Add a String constant MAJOR_FIELD
	private static final String MAJOR_FIELD = "major";
	
	//Name of the files with data in them
	protected static final String POOL_OF_LASTNAMES = "lastnamePool.txt";
	protected static final String POOL_OF_FIRSTNAMES = "firstnamePool.txt";
	//TODONE Add a String constant POOL_OF_DEGREES
	protected static final String POOL_OF_DEGREES = "degreePool.txt";
	//TODONE Add a String constant POOL_OF_MAJORS
	protected static final String POOL_OF_MAJORS = "majorPool.txt";
	
	//List of useful digits and letters
	protected static final String ALPHA_LETTERS = "abcdefghijklmnopqrstuvwxyz";
	protected static final String DIGITS = "1234567890";
	
	//Random generator which is less predictable than Random class
	//https://stackoverflow.com/a/11052736/764951
	protected static SecureRandom rnd = new SecureRandom();
	
	//List of loaded data pool
	protected static List<String> poolOfLastnames = new ArrayList<>();
	protected static List<String> poolOfFirstnames = new ArrayList<>();
	//TODONE Declare an array list called poolOfDegrees
	protected static List<String> poolOfDegrees = new ArrayList<>();
	//TODONE Declare an array list called poolOfMajors
	protected static List<String> poolOfMajors = new ArrayList<>();

	//Load the pool of data into the list
	static {
		
		ClassLoader theClassLoader = MethodHandles.lookup().lookupClass().getClassLoader();
		
		try (InputStream firstnamePoolInputStream = theClassLoader.getResourceAsStream(POOL_OF_FIRSTNAMES);
			InputStream lastnamePoolInputStream = theClassLoader.getResourceAsStream(POOL_OF_LASTNAMES);
			//TODONE Declare and initialize an input stream object called degreePoolInputStream
				InputStream degreePoolInputStream = theClassLoader.getResourceAsStream(POOL_OF_DEGREES);
			//TODONE Declare and initialize an input stream object called majorPoolInputStream
				InputStream majorPoolInputStream = theClassLoader.getResourceAsStream(POOL_OF_MAJORS);
			
			Scanner lastnamePoolScanner = new Scanner(lastnamePoolInputStream);
			Scanner firstnamePoolScanner = new Scanner(firstnamePoolInputStream);
			//TODONE Declare and initialize a scanner object called degreePoolScanner
				Scanner degreePoolScanner = new Scanner(degreePoolInputStream);
			//TODONE Declare and initialize a scanner object called majorPoolScanner
				Scanner majorPoolScanner = new Scanner(majorPoolInputStream);

		) {
			while (lastnamePoolScanner.hasNext()) {
				poolOfLastnames.add(lastnamePoolScanner.nextLine());
			}

			while (firstnamePoolScanner.hasNext()) {
				poolOfFirstnames.add(firstnamePoolScanner.nextLine());
			}

			//TODONE Load the contents of degreePoolScanner into poolOfDegrees
			while (degreePoolScanner.hasNext()) {
				poolOfDegrees.add(degreePoolScanner.nextLine());
			}
			//TODONE Load the contents of majorPoolScanner into poolOfMajors
			while (majorPoolScanner.hasNext()) {
				poolOfMajors.add(majorPoolScanner.nextLine());
			}
			
			
		} catch (IOException e) {
			logger.error("something went wrong building pools:  {}", e.getLocalizedMessage());
		}

	}

	//Depending on the attribute name, create random data for that field and return it.
	@Override
	public String getType(DataProviderStrategy strategy, AttributeMetadata attributeMetadata,
			Map< String, Type> genericTypesArgumentsMap) {
		
		String stringType = "";
		
		if (EMAIL_FIELD.equals(attributeMetadata.getAttributeName())) {
			
			//Create random customer email
			StringBuilder sb = new StringBuilder();
			
			while (sb.length() < 3) {
				int index = (int) (rnd.nextFloat() * ALPHA_LETTERS.length());
				sb.append(ALPHA_LETTERS.charAt(index));
			}
			
			while (sb.length() < 8) {
				int index = (int) (rnd.nextFloat() * DIGITS.length());
				sb.append(DIGITS.charAt(index));
			}
			
			sb.append("@algonquinlive.com");
			stringType = sb.toString();
			
		} else if (LASTNAME_FIELD.equals(attributeMetadata.getAttributeName())) {
			
			stringType = poolOfLastnames.get(rnd.nextInt(poolOfLastnames.size()));
			
		} else if (FIRSTNAME_FIELD.equals(attributeMetadata.getAttributeName())) {
			
			stringType = poolOfFirstnames.get(rnd.nextInt(poolOfFirstnames.size()));
			
		} else if (PHONENUMBER_FIELD.equals(attributeMetadata.getAttributeName())) {
			
			int npa = rnd.nextInt(643) + 100;
			int extension = rnd.nextInt(9000) + 1000;
			stringType = String.format("613%03d%04d", npa, extension);

		} 

		//TODONE Add an else-if for the DEGREE_FIELD
		else if (DEGREE_FIELD.equals(attributeMetadata.getAttributeName())) {
			stringType = poolOfDegrees.get(rnd.nextInt(poolOfDegrees.size()));
		}
		
		//TODONE Add an else-if for the MAJOR_FIELD
		else if (MAJOR_FIELD.equals(attributeMetadata.getAttributeName())) {
			stringType = poolOfMajors.get(rnd.nextInt(poolOfMajors.size()));
		}
			
		else {
			
			stringType = super.getType(strategy, attributeMetadata, genericTypesArgumentsMap);
			
		}
		
		return stringType;
		
	}

}