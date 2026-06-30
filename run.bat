@echo off
title EMS - Employee Management System Runner
cd /d "%~dp0"
echo Starting Employee Management System...
java -Dfile.encoding=UTF-8 -cp "classes;mysql-connector-j-9.0.0.jar" com.employee.gui.LoginFrame
if %errorlevel% neq 0 (
    echo.
    echo Application exited with error code %errorlevel%
    pause
)
