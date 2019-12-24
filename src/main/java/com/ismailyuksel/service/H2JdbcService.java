package com.ismailyuksel.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.h2.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import com.ismailyuksel.model.MobileDeviceSearchInnerRequestModel;
import com.ismailyuksel.model.OsType;
import com.ismailyuksel.model.MobileDeviceModel;
import com.ismailyuksel.rowmapper.MobileDeviceModelRowMapper;

@Service
public class H2JdbcService {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static final String MOBILE_DEVICE_INSERT_SQL = "insert into mobile_device (brand, model, os, os_version) values(?, ?, ?, ?)";
	private static final String MOBILE_DEVICE_SELECT_SQL = "select id, brand, model, os, os_version from mobile_device where 1=1 ";
	
	public int addMobileDevice(MobileDeviceModel mobileDevice) throws Exception {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(
		    new PreparedStatementCreator() {
		        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		            PreparedStatement ps =
		                connection.prepareStatement(MOBILE_DEVICE_INSERT_SQL, new String[] {"id"});
					ps.setString(1, mobileDevice.getBrand());
					ps.setString(2, mobileDevice.getModel());
					ps.setInt(3, OsType.ofName(mobileDevice.getOs()).getValue());
					ps.setString(4, mobileDevice.getOsVersion());
		            return ps;
		        }
		    },
		    keyHolder);
		
		return keyHolder.getKey().intValue();
	}
	
    public List<MobileDeviceModel> getMobileDevice(MobileDeviceSearchInnerRequestModel mobileDeviceInnerRequest) throws Exception {
    	List<String> paramList = new ArrayList<>();
    	String dynamicSql = getDynamicSql(mobileDeviceInnerRequest, paramList);
    	return getMobileDeviceList(paramList, dynamicSql);
    }

	private List<MobileDeviceModel> getMobileDeviceList(List<String> paramList, String dynamicSql) {
		List<MobileDeviceModel> mobileDevice = new ArrayList<>();
    	if(paramList == null || paramList.isEmpty()) {
    		mobileDevice = jdbcTemplate.query(dynamicSql, new MobileDeviceModelRowMapper());
    	} else {
    		Object[] agr = new Object[paramList.size()];
    		paramList.toArray(agr);
    		mobileDevice = jdbcTemplate.query(dynamicSql, agr, new MobileDeviceModelRowMapper());
    	}
		return mobileDevice;
	}

	private String getDynamicSql(MobileDeviceSearchInnerRequestModel mobileDeviceInnerRequest, List<String> paramList) {
		String dynamicSql = MOBILE_DEVICE_SELECT_SQL;
    	if(mobileDeviceInnerRequest.getId() != 0) {
    		dynamicSql += " and id = ?";
    		paramList.add(String.valueOf(mobileDeviceInnerRequest.getId()));
    	}
    	if(!StringUtils.isNullOrEmpty(mobileDeviceInnerRequest.getBrand())) {
    		dynamicSql += " and brand = ?";
    		paramList.add(mobileDeviceInnerRequest.getBrand());
    	}
    	if(!StringUtils.isNullOrEmpty(mobileDeviceInnerRequest.getModel())) {
    		dynamicSql += " and model = ?";
    		paramList.add(mobileDeviceInnerRequest.getModel());
    	}
    	if(mobileDeviceInnerRequest.getOsType() != null) {
    		dynamicSql += " and os = ?";
    		paramList.add(String.valueOf(mobileDeviceInnerRequest.getOsType().getValue()));
    	}
    	if(!StringUtils.isNullOrEmpty(mobileDeviceInnerRequest.getOsVersion())) {
    		dynamicSql += " and os_version = ?";
    		paramList.add(mobileDeviceInnerRequest.getOsVersion());
    	}
		return dynamicSql;
	}

}
