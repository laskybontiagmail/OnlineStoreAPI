package com.lasky.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.lasky.utilities.ApplicationProperty;
import com.lasky.utilities.Utility;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class EntityManagerHandlerFactory {
	private static Logger logger = LogManager.getLogger(EntityManagerHandlerFactory.class);
	
	private static String entityBasePackage = ApplicationProperty.get("entity.base.package");
	private static String packages = ApplicationProperty.get("entity.packages");
	private static String entity = ApplicationProperty.get("entity.package");
	
	private static String cfgHbm2ddAuto = ApplicationProperty.get("org.hibernate.cfg.Environment.HBM2DDL_AUTO");
	private static Boolean cfgShowSql = Boolean.valueOf(ApplicationProperty.get("org.hibernate.cfg.Environment.SHOW_SQL"));
	private static Boolean cfgFormatSql = Boolean.valueOf(ApplicationProperty.get("org.hibernate.cfg.Environment.FORMAT_SQL"));
	private static String cfgContextClass = ApplicationProperty.get("org.hibernate.cfg.Environment.CURRENT_SESSION_CONTEXT_CLASS");
	private static Integer cfgBatchFetchSize = Integer.valueOf(ApplicationProperty.get("org.hibernate.cfg.Environment.DEFAULT_BATCH_FETCH_SIZE"));
	private static Boolean cfgGenerateStatistics = Boolean.valueOf(ApplicationProperty.get("org.hibernate.cfg.Environment.GENERATE_STATISTICS"));
	
	private static String cfgDialectMySql = ApplicationProperty.get("org.hibernate.cfg.Environment.DIALECT_MYSQL");
	private static String cfgDriverClassMySql = ApplicationProperty.get("org.hibernate.cfg.Environment.DRIVER_CLASS_MYSQL");
	
	private static String cfgDialectMsSql = ApplicationProperty.get("org.hibernate.cfg.Environment.DIALECT_MSSQL");
	private static String cfgDriverClassMsSql = ApplicationProperty.get("org.hibernate.cfg.Environment.DRIVER_CLASS_MSSQL");
	
	private static EntityManagerHandlerFactory instance;
	
	private Utility utility = Utility.getOnlyInstance();
	
	private EntityManagerHandlerFactory() {
		
	}

	/**
	 * Instance
	 */
	public static EntityManagerHandlerFactory factory() {
		if (instance == null) {
			instance = new EntityManagerHandlerFactory();
		}
		return instance;
	}
	
	/**
	 * Get entity manager factory
	 * @param key
	 * @return {EntityManagerFactory}
	 */
	public EntityManagerFactory getEntityManagerFactory(DatasourceConnection datasourceConnection) {
		String prefix = "getEntityManagerFactory() ";
		EntityManagerFactory entityManagerFactory = null;
		LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = null;
		
		localContainerEntityManagerFactoryBean = configureLocalContainerEntityManagerFactoryBean(datasourceConnection);
		
		// Store
		if (localContainerEntityManagerFactoryBean != null) {
			entityManagerFactory = localContainerEntityManagerFactoryBean.getObject();
		} else {
			String hostName = null;
			if (datasourceConnection != null) {
				hostName = datasourceConnection.getHostname();
			}
			logger.error(prefix + " Unable to connect to the datasource: " + hostName);
		}
		
		return entityManagerFactory;
	}

	/**
	 * Configure local container entity manager factroy bean
	 * @param datasource
	 * @return {LocalContainerEntityManagerFactoryBean}
	 */
	private LocalContainerEntityManagerFactoryBean configureLocalContainerEntityManagerFactoryBean(
			DatasourceConnection datasourceConnection) {
		String prefix = "configureLocalContainerEntityManagerFactoryBean() ";
		LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = null;
		if (datasourceConnection != null) {
			try {
				String[] token = packages.split(",");
				String source = null;
				List<String> sources = new ArrayList<String>();
				int count = 0;
				for (count = 0; count < token.length; count++) {
					if (entity != null && !entity.isEmpty()) {
						source = entityBasePackage + "." + token[count] + "." + entity;
					} else {
						source = entityBasePackage + "." + token[count];
					}

					sources.add(source);
				}
				String[] entities = new String[sources.size()];
				count = 0;
				for (String item : sources) {
					entities[count] = item;
					count++;
				}
				localContainerEntityManagerFactoryBean = getLocalContainerEntityManagerFactoryBean(datasourceConnection, entities);
			} catch (Exception exc) {
				//Generic.causedByException(exc); //TODO
				// Close the connection when having issues on entities.
				try {
					if (localContainerEntityManagerFactoryBean != null) {
						((HikariDataSource) localContainerEntityManagerFactoryBean.getDataSource()).close();
						localContainerEntityManagerFactoryBean.getObject().close();
						localContainerEntityManagerFactoryBean.destroy();
						localContainerEntityManagerFactoryBean = null;
						logger.warn(prefix + " terminate localContainerEntityManagerFactoryBean");
					}

				} catch (Exception exc2) {
					//Generic.causedByException(exc); //TODO
					localContainerEntityManagerFactoryBean = null;
				}
			}
		}
		return localContainerEntityManagerFactoryBean;
	}
	
	/**
	 * Get local container entity manager factory bean
	 * @param datasource
	 * @return {LocalContainerEntityManagerFactoryBean}
	 */
	private LocalContainerEntityManagerFactoryBean getLocalContainerEntityManagerFactoryBean(
			DatasourceConnection datasourceConnection, String[] entities) {
		String prefix = "getLocalContainerEntityManagerFactoryBean() ";
		
		LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		localContainerEntityManagerFactoryBean.setPackagesToScan(entities);
		localContainerEntityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		localContainerEntityManagerFactoryBean
				.setPersistenceUnitName("persistenceUnitName" + System.currentTimeMillis());
		Properties jpaProperties = new Properties();
		
		String jdbcUrl = null;
		//String jdbcUrl = "jdbc:postgresql://{HOSTNAME}{PORT}/{DATABASE}?zeroDateTimeBehavior=convertToNull&rewriteBatchedStatements=true";
		//String jdbcUrl = "jdbc:postgresql://{HOSTNAME}{PORT}/{DATABASE}";
		if (DatasourceConnection.DATABASE_TYPE_MYSQL.equals(datasourceConnection.getDatabaseType())) {
			jdbcUrl = "jdbc:mysql://{HOSTNAME}:{PORT}/{DATABASE}?zeroDateTimeBehavior=convertToNull&rewriteBatchedStatements=true";
		} else if (DatasourceConnection.DATABASE_TYPE_MSSQL.equals(datasourceConnection.getDatabaseType())) {
			jdbcUrl = "jdbc:sqlserver://{HOSTNAME}:{PORT};DataBaseName={DATABASE}"; // this is for MSSQL
		}
		
		try {
			if (datasourceConnection != null) {
				String hostname = datasourceConnection.getHostname();
				String hostPort = datasourceConnection.getHostPort();
				String database = datasourceConnection.getDatabase();
				String username = datasourceConnection.getUsername();
				String password = datasourceConnection.getPassword();

				jdbcUrl = jdbcUrl.replace("{HOSTNAME}", hostname);
				if (hostPort != null && !hostPort.equals("")) {
					jdbcUrl = jdbcUrl.replace("{PORT}", (hostPort));
					//jdbcUrl = jdbcUrl.replace("{PORT}", (":" + hostPort));
				}
				jdbcUrl = jdbcUrl.replace("{DATABASE}", database);
				logger.info(prefix);
				logger.info("-----------------------------------------------------------------------");
				//System.out.println("client:" + datasource + "{" + jdbcUrl + "}");
				logger.info("client:" + hostname + "{" + jdbcUrl + "}");
				logger.info("-----------------------------------------------------------------------");

				// Generic settings
				if (DatasourceConnection.DATABASE_TYPE_MYSQL.equals(datasourceConnection.getDatabaseType())) {
					jpaProperties.put(org.hibernate.cfg.Environment.DIALECT, cfgDialectMySql);
				} else if (DatasourceConnection.DATABASE_TYPE_MSSQL.equals(datasourceConnection.getDatabaseType())) {
					jpaProperties.put(org.hibernate.cfg.Environment.DIALECT, cfgDialectMsSql);
				}
				jpaProperties.put(org.hibernate.cfg.Environment.HBM2DDL_AUTO, cfgHbm2ddAuto);
				jpaProperties.put(org.hibernate.cfg.Environment.SHOW_SQL, cfgShowSql);
				jpaProperties.put(org.hibernate.cfg.Environment.FORMAT_SQL, cfgFormatSql);
				jpaProperties.put(org.hibernate.cfg.Environment.CURRENT_SESSION_CONTEXT_CLASS, cfgContextClass);
				jpaProperties.put(org.hibernate.cfg.Environment.DEFAULT_BATCH_FETCH_SIZE, cfgBatchFetchSize);
				jpaProperties.put(org.hibernate.cfg.Environment.GENERATE_STATISTICS, cfgGenerateStatistics);
				jpaProperties.put(org.hibernate.cfg.Environment.BATCH_STRATEGY, cfgGenerateStatistics);
				jpaProperties.put(org.hibernate.cfg.Environment.USE_NEW_ID_GENERATOR_MAPPINGS, false);

				HikariConfig cpConfig = new HikariConfig();
				cpConfig.setJdbcUrl(jdbcUrl);
				cpConfig.setUsername(username);
				cpConfig.setPassword(password);
				cpConfig.setMaximumPoolSize(15);
				cpConfig.setMinimumIdle(5);
				cpConfig.setConnectionTestQuery("SELECT 1");

				cpConfig.setConnectionTimeout(60000);
				cpConfig.setIdleTimeout(28740000);
				
				//to do put a condition here
				//cpConfig.setDriverClassName("com.mysql.jdbc.Driver");
				//cpConfig.setDriverClassName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
				if (DatasourceConnection.DATABASE_TYPE_MYSQL.equals(datasourceConnection.getDatabaseType())) {
					cpConfig.setDriverClassName(cfgDriverClassMySql);
				} else if (DatasourceConnection.DATABASE_TYPE_MSSQL.equals(datasourceConnection.getDatabaseType())) {
					cpConfig.setDriverClassName(cfgDriverClassMsSql);
				}
				
				HikariDataSource cpDatasource = new HikariDataSource(cpConfig);
				localContainerEntityManagerFactoryBean.setDataSource(cpDatasource);
				localContainerEntityManagerFactoryBean.setJpaProperties(jpaProperties);
				localContainerEntityManagerFactoryBean.afterPropertiesSet();
			} 
		} catch (Exception exception) {
			utility.logFormattedExceptionStackTrace(logger, exception);
			localContainerEntityManagerFactoryBean = null;
		} //finally {
			//DataHandler dataHandler = new DataHandler();
			//// Store
			//if (localContainerEntityManagerFactoryBean != null) {
			//	dataHandler.setDate(new Date());
			//	dataHandler.setValue(localContainerEntityManagerFactoryBean);
			//	set(datasource, dataHandler);
			//	invalidateDatabases.delete(datasource);
			//	
			//} else {
				//// Invalidate database and wait for the assigned interval
				//dataHandler.setDate(new Date());
				//invalidateDatabases.set(datasource, dataHandler);
				//logger.error(prefix + "invalidateDatabase : " + datasource);
			//}
		//}
		
		return localContainerEntityManagerFactoryBean;
	}
}



