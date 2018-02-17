#!/bin/bash

export SPRING_PROFILES_ACTIVE="dev"

#default profile
export DATABASE="itcmdb_dev"
export DATABASE_USERNAME="username"
export DATABASE_PASSWORD="password"
export DATABASE_SERVER="localhost"
export DATABASE_PORT="5432"

export SUPORT_EMAIL="mail@test.com"

#dev profile
export DATABASE_DEV="itcmdb_dev"
export DATABASE_USERNAME_DEV="username"
export DATABASE_PASSWORD_DEV="password"
export DATABASE_SERVER_DEV="localhost"
export DATABASE_PORT_DEV="5432"

export SUPORT_EMAIL_DEV="mail@test.com"

#test profile
export DATABASE_TEST="itcmdb_test"
export DATABASE_USERNAME_TEST="username"
export DATABASE_PASSWORD_TEST="password"
export DATABASE_SERVER_TEST="localhost"
export DATABASE_PORT_TEST="5432"

export SUPORT_EMAIL_TEST="mail@test.com"

#prod profile
export DATABASE_PROD="itcmdb"
export DATABASE_USERNAME_PROD="username"
export DATABASE_PASSWORD_PROD="password"
export DATABASE_SERVER_PROD="localhost"
export DATABASE_PORT_PROD="5432"

#smtp settings

export MAIL_PROTOCOL="smtp"
export MAIL_HOST="smtp.server.net"
export MAIL_PORT="111"
export MAIL_SMTP_AUTH="true"
export MAIL_SMTP_TLS_ENABLE="true"
export MAIL_FROM="mail@test.com"
export MAIL_USERNAME="username"
export MAIL_PASSWORD="password"

export SUPORT_EMAIL_PROD="mail@test.com"


echo "All set!"
