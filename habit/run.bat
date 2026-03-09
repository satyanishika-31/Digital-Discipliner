@echo off
echo ========================================
echo   Habit Tracker - Starting Application
echo ========================================
echo.
echo Make sure MySQL is running on port 3306
echo.
cd %~dp0
java -jar target\habit-0.0.1-SNAPSHOT.jar
pause
