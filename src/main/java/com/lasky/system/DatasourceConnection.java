package com.lasky.system;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.AccessLevel;

@Getter
@Setter
@AllArgsConstructor
/**
 * Datasource Connection Information class
 *
 */
public class DatasourceConnection {
	public static final String DATABASE_TYPE_MSSQL = "MS-SQL";
	public static final String DATABASE_TYPE_MYSQL = "MySQL";
	
	private String databaseType;
	private String hostname;
	private String hostPort;
	private String database;
	private String username;
	private String password;
	
	
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private static DatasourceConnection defaultInstance = null;
	
	public DatasourceConnection() { }
	
	public static DatasourceConnection getDefault() {
		
		if (defaultInstance == null) {
			defaultInstance = new DatasourceConnection();
			
			defaultInstance.databaseType = DATABASE_TYPE_MYSQL;
			defaultInstance.hostname = "localhost";
			defaultInstance.hostPort = "3306";
			defaultInstance.database = "OnlineStore";
			defaultInstance.username = "root";
			defaultInstance.password = "sigegPangayogPassword";
		}
		
		return DatasourceConnection.defaultInstance;
	}
}


