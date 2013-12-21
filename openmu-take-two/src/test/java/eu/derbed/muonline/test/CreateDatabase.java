/**
 *
 */
package eu.derbed.muonline.test;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;

/**
 * @author Alexandru Bledea
 * @since Dec 21, 2013
 */
public class CreateDatabase {

	public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
		Class.forName("org.hsqldb.jdbcDriver");
		Connection con = DriverManager.getConnection("jdbc:hsqldb:file:databases/muonline", "MU", "123456");
		Statement stmt = con.createStatement();

		final List<String> loadStatements = loadStatements();
		for (String string : loadStatements) {
			stmt.execute(string);
		}
	}

	/**
	 * @throws IOException
	 *
	 */
	private static List<String> loadStatements() throws IOException {
		List<String> readLines = FileUtils.readLines(new File("src/test/resources/create-database"));
		Iterator<String> iterator = readLines.iterator();
		while (iterator.hasNext()) {
			String next = iterator.next();
			if (next.startsWith("#") || next.trim().isEmpty()) {
				iterator.remove();
			}
		}
		return readLines;
	}

}
