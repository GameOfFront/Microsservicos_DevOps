FROM ubuntu:22.04

RUN apt-get update && apt-get install -y mysql-server && apt-get clean

ENV MYSQL_ROOT_PASSWORD=root
ENV MYSQL_DATABASE=meubanco
ENV MYSQL_USER=usuario
ENV MYSQL_PASSWORD=senha

COPY init.sql /init.sql

EXPOSE 3306

CMD service mysql start && \
    mysql -u root -e "CREATE DATABASE IF NOT EXISTS meubanco;" && \
    mysql -u root meubanco < /init.sql && \
    tail -f /dev/null
