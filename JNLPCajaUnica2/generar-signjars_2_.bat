@echo off
setlocal enabledelayedexpansion

REM ==== Configuración ====
set JARSIGNER="C:\Users\sebastian.sepulveda\.sdkman\candidates\java\resp\current---1.6\jarsigner.exe"
set KEYSTORE=src\main\resources\myKeystore
set STOREPASS=123456
set ALIAS=myself
set DIR_JARS=target\JNLPCajaUnica2-2.0.2\app

REM ==== Validación ====
if not exist %KEYSTORE% (
    echo ERROR: No se encontró el keystore en %KEYSTORE%
    exit /b 1
)

if not exist %DIR_JARS% (
    echo ERROR: No se encontró el directorio de JARs en %DIR_JARS%
    exit /b 1
)

echo ==== Firmando JARs en %DIR_JARS% ====
for %%F in (%DIR_JARS%\*.jar) do (
    echo Firmando: %%~nxF
    %JARSIGNER% -keystore %KEYSTORE% -storepass %STOREPASS% -sigalg SHA1withDSA -digestalg SHA1 -tsa http://timestamp.digicert.com "%%F" %ALIAS%
)

echo ==== Proceso completado ====
pause