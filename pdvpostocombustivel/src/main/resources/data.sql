# Configuração PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/pdv_posto
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.datasource.driver-class-name=org.postgresql.Driver

# Configuração JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.open-in-view=false
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.jdbc.time_zone=UTC

# Importar schema.sql e data.sql automaticamente
spring.sql.init.mode=always
spring.sql.init.platform=postgres