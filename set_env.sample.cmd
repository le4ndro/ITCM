:: Name:     set_env.sample.bat
:: Purpose:  Configures enviroment variables for ITCM
:: Author:   Leandro Souza
:: Revision: February 2018 - initial version

@ECHO OFF

:: variables

SET SPRING_PROFILES_ACTIVE=dev

:: default profile
SET DATABASE=itcmdb_dev
SET DATABASE_USERNAME=username
SET DATABASE_PASSWORD=password
SET DATABASE_SERVER=localhost
SET DATABASE_PORT=5432

SET SUPORT_EMAIL=mail@test.com

:: dev profile
SET DATABASE_DEV=itcmdb_dev
SET DATABASE_USERNAME_DEV=username
SET DATABASE_PASSWORD_DEV=password
SET DATABASE_SERVER_DEV=localhost
SET DATABASE_PORT_DEV=5432

SET SUPORT_EMAIL_DEV=mail@test.com

:: test profile
SET DATABASE_TEST=itcmdb_test
SET DATABASE_USERNAME_TEST=username
SET DATABASE_PASSWORD_TEST=password
SET DATABASE_SERVER_TEST=localhost
SET DATABASE_PORT_TEST=5432

SET SUPORT_EMAIL_TEST=mail@test.com

:: prod profile
SET DATABASE_PROD=itcmdb
SET DATABASE_USERNAME_PROD=username
SET DATABASE_PASSWORD_PROD=password
SET DATABASE_SERVER_PROD=localhost
SET DATABASE_PORT_PROD=5432

:: smtp settings

SET MAIL_PROTOCOL=smtp
SET MAIL_HOST=smtp.server.net
SET MAIL_PORT=111
SET MAIL_SMTP_AUTH=true
SET MAIL_SMTP_TLS_ENABLE=true
SET MAIL_FROM=mail@test.com
SET MAIL_USERNAME=username
SET MAIL_PASSWORD=password

SET SUPORT_EMAIL_PROD=mail@test.com


echo All set!


:END
ENDLOCAL
ECHO ON
@EXIT /B 0
