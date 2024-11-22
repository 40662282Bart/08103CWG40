# Use the MySQL image version 8.3
FROM mysql:8.3

# Set the working directory to /tmp inside the container
WORKDIR /tmp

# Copy all SQL files from the world_db repository into the container’s /tmp folder
COPY world_db/*.sql /tmp/
COPY world_db/*.dump /tmp/

# Initialize the MySQL database using world.sql
COPY world_db/world.sql /docker-entrypoint-initdb.d/

# Set the root password for MySQL (replace "example" with a secure password in production)
ENV MYSQL_ROOT_PASSWORD group40password
