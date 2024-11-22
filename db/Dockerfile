# Use the MySQL image version 8.3
FROM mysql:8.3

# Initialize the MySQL database using world.sql
COPY world.sql /docker-entrypoint-initdb.d/

# Set the root password for MySQL (replace "example" with a secure password in production)
ENV MYSQL_ROOT_PASSWORD group40password
