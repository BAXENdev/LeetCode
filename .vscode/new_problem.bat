@echo off
setlocal enabledelayedexpansion

:: Check if the correct number of arguments is provided
if "%~2"=="" (
    echo Usage: create_problem.bat ^<filepath^> ^<number^>
    echo Example: create_problem.bat C:\Users\YourName\Projects 1234
    exit /b
)

:: Assign command-line arguments to variables
set ROOT_DIR=%~1
set PROBLEM_NUMBER=%~2

:: Define directory paths
set PROBLEM_DIR=%ROOT_DIR%\problem%PROBLEM_NUMBER%
set TEST_DIR=%PROBLEM_DIR%\test

:: Create directories
mkdir "%PROBLEM_DIR%"
mkdir "%TEST_DIR%"

:: Copy template files
copy "%ROOT_DIR%\.vscode\Solution.java" "%PROBLEM_DIR%\Solution.java"
copy "%ROOT_DIR%\.vscode\SolutionTest.java" "%TEST_DIR%\SolutionTest.java"

:: :: Add package declarations to Solution.java
:: (
::     echo package problem%PROBLEM_NUMBER%;
::     type "%PROBLEM_DIR%\Solution.java"
:: ) > "%PROBLEM_DIR%\Solution.java"
:: 
:: :: Add package declarations to SolutionTest.java
:: (
::     echo package problem%PROBLEM_NUMBER%.test;
::     type "%TEST_DIR%\SolutionTest.java"
:: ) > "%TEST_DIR%\SolutionTest.java"

echo Project for problem %PROBLEM_NUMBER% created successfully at %PROBLEM_DIR%.
@REM pause
