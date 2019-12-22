package com.ismailyuksel.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import com.ismailyuksel.model.MobileDevice;

@Service
public class H2JdbcService {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static final String MOBILE_DEVICE_INSERT_SQL = "insert into mobile_device (brand, model, os, os_version) values(?, ?, ?, ?)";
	
	
	public int addMobileDevice(MobileDevice mobileDevice) throws Exception {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(
		    new PreparedStatementCreator() {
		        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		            PreparedStatement ps =
		                connection.prepareStatement(MOBILE_DEVICE_INSERT_SQL, new String[] {"id"});
					ps.setString(1, mobileDevice.getBrand());
					ps.setString(2, mobileDevice.getModel());
					ps.setString(3, mobileDevice.getOs());
					ps.setString(4, mobileDevice.getOsVersion());
		            return ps;
		        }
		    },
		    keyHolder);
		
		return keyHolder.getKey().intValue();
	}

}
