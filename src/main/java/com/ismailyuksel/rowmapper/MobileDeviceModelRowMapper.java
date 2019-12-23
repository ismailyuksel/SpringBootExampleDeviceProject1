package com.ismailyuksel.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.ismailyuksel.model.MobileDeviceModel;
import com.ismailyuksel.model.OsType;

public class MobileDeviceModelRowMapper implements RowMapper<MobileDeviceModel> {
	@Override
    public MobileDeviceModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		MobileDeviceModel mobileDevice = new MobileDeviceModel();
		mobileDevice.setId(rs.getInt("id"));
		mobileDevice.setBrand(rs.getString("brand"));
		mobileDevice.setModel(rs.getString("model"));
		mobileDevice.setOs(OsType.of(rs.getInt("os")).toString());
		mobileDevice.setOsVersion(rs.getString("os_version"));
        return mobileDevice;
    }
}
