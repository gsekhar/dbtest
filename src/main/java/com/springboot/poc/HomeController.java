package com.springboot.poc;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.poc.model.User;


@RestController
public class HomeController {

	/*
	 * @Value("${dev.app.db.username}") private String username;
	 * 
	 * @Value("${dev.app.db.password}") private String password;
	 * 
	 * @Value("${dev.app.db.url}") private String url;
	 */

	@RequestMapping("/")
	public String index() {
		return "Welcome to DB test";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getData/{id}")
	public User getData(@PathVariable("id") String id) {
		String username = "**";
		String password = "***";
		String url = "***";
		User user = new User();
		System.out.println("====>>>>>>dev_app_db_username Env =======" + System.getProperty("dev_app_db_username"));
		System.out.println("====>>>>>>JWS_ADMIN_PASSWORD Env =======" + System.getProperty("JWS_ADMIN_PASSWORD"));
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("Here");
			Connection con = DriverManager.getConnection(url, username, password);
			PreparedStatement ps = con
					.prepareStatement("select FIRSTNAME, LASTNAME, EMAIL FROM DL_USER WHERE ID = " + id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				user.setFirstname(rs.getString(1));
				user.setLastname(rs.getString(2));
				user.setEmail(rs.getString(3));
				user.setMessage("User Exists");
			} else {
				user.setMessage("Not Found");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getsoacon")
	public static boolean pingsoaHost() {
		try (Socket socket = new Socket()) {
			socket.connect(new InetSocketAddress("123124", 8180), 60);
			return true;
		} catch (IOException e) {
			return false; // Either timeout or unreachable or failed DNS lookup.
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/geticomscon")
	public static void pingIcomsHost() {
		String https_url = "";
		URL url;
		try {
			url = new URL(https_url);
		     HttpsURLConnection con = (HttpsURLConnection)url.openConnection();

			System.out.println("Service " + url + " available, yeah!");
		} catch (final MalformedURLException e) {
			throw new IllegalStateException("Bad URL: " + https_url, e);
		} catch (final IOException e) {
			// System.out.println("Service " + url + " unavailable, oh no!", e);
			e.printStackTrace();
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/geticdb")
	public void comsDB() {
		String username = "****";
		String password = "***";
		String url = "";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("Here");
			Connection con = DriverManager.getConnection(url, username, password);
			if (con != null) {
				System.out.println("ICOMsConeec ====>>> YES");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error");
		}

	}
}
