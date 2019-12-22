package com.ismailyuksel.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.ismailyuksel.model.MobileDevice;

public class MobileDeviceRowMapper implements RowMapper<MobileDevice> {
	@Override
    public MobileDevice mapRow(ResultSet rs, int rowNum) throws SQLException {
		MobileDevice mobileDevice = new MobileDevice();
		mobileDevice.setId(rs.getInt("id"));
		mobileDevice.setBrand(rs.getString("brand"));
		mobileDevice.setModel(rs.getString("model"));
		mobileDevice.setOs(rs.getString("os"));
		mobileDevice.setOsVersion(rs.getString("os_version"));
        return mobileDevice;
    }
}
