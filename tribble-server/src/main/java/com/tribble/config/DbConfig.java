package com.tribble.config;

import com.tribble.db.dao.LanguageDao;
import com.tribble.db.dao.TranslationDao;
import com.tribble.db.dao.UserDao;
import com.tribble.db.dao.impl.LanguageDaoImpl;
import com.tribble.db.dao.impl.TranslationDaoImpl;
import com.tribble.db.dao.impl.UserDaoImpl;
import com.tribble.db.entity.User;
import com.tribble.db.service.LanguageService;
import com.tribble.db.service.TranslationService;
import com.tribble.db.service.UserService;
import com.tribble.db.service.impl.LanguageServiceImpl;
import com.tribble.db.service.impl.TranslationServiceImpl;
import com.tribble.db.service.impl.UserServiceImpl;
import com.tribble.translation.TranslationHandler;
import com.tribble.translation.YandexTranslationHandler;
import org.apache.catalina.connector.Connector;
import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class DbConfig {

    @Inject
    private Environment environment;

    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", environment.getProperty("hibernate.dialect"));
        properties.setProperty("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));
        properties.setProperty("hibernate.hbm2ddl.auto",
                environment.getProperty("hibernate.hbm2ddl.auto"));
        properties.setProperty("hibernate.hbm2ddl.import_files",
                environment.getProperty("hibernate.hbm2ddl.import_files"));
        return properties;
    }

    @Autowired
    @Bean(name = "hibernateTemplate")
    @Scope(WebApplicationContext.SCOPE_REQUEST)
    public HibernateTemplate getHibernateTemplate(SessionFactory sessionFactory) {
        return new HibernateTemplate(sessionFactory);
    }


    @Autowired
    @Bean(name = "sessionFactory")
    public SessionFactory getSessionFactory(DataSource dataSource) {
        LocalSessionFactoryBuilder localSessionFactoryBuilder = new LocalSessionFactoryBuilder(
                dataSource);
        localSessionFactoryBuilder.addAnnotatedClasses(new Class[]{User.class});
        localSessionFactoryBuilder.addProperties(getHibernateProperties());
        localSessionFactoryBuilder.scanPackages("com.tribble.db.entity");
        return localSessionFactoryBuilder.buildSessionFactory();
    }

    @Bean(name = "dataSource")
    public DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(environment.getProperty("driverClassName"));
        dataSource.setUrl(environment.getProperty("url"));
        dataSource.setUsername(environment.getProperty("username1"));
        dataSource.setPassword(environment.getProperty("password1"));
        return dataSource;
    }

    @Autowired
    @Bean(name = "transactionManager")
    @Scope(WebApplicationContext.SCOPE_REQUEST)
    public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
        hibernateTransactionManager.setSessionFactory(sessionFactory);
        return hibernateTransactionManager;
    }

    @Bean(name = "userDao")
    public UserDao getUserDao() {
        return new UserDaoImpl();
    }

    @Bean(name = "userService")
    public UserService getUserService() {
        return new UserServiceImpl();
    }

    @Bean(name = "translationDao")
    public TranslationDao getTranslationDao() {
        return new TranslationDaoImpl();
    }

    @Bean(name = "translationService")
    public TranslationService getTranslationService() {
        return new TranslationServiceImpl();
    }

    @Bean(name = "translationHandler")
    public TranslationHandler getTranslationHandler() {
        return new YandexTranslationHandler();
    }

    @Bean(name = "languageDao")
    public LanguageDao getLanguageDao() {
        return new LanguageDaoImpl();
    }

    @Bean(name = "languageService")
    public LanguageService getLanguageService() {
        return new LanguageServiceImpl();
    }
}


