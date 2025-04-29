@echo off
setlocal enabledelayedexpansion

set "APP_DIR=target\JNLPCajaUnica2\app"
echo ^<signJars^>

for %%F in (%APP_DIR%\*.jar) do (
    set "JAR_NAME=%%~nxF"
    echo   ^<signJar^>
    echo     ^<file^>${project.build.directory}/${project.build.finalName}/app/!JAR_NAME!^</file^>
    echo   ^</signJar^>
)

echo ^</signJars^>

pause