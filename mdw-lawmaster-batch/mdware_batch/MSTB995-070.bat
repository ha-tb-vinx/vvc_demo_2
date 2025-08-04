@echo off
rem =======================================
rem MSTB995-070 計量器用バックアップ処理
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB995-07011 %CLS_HOME%\batch\properties release use
IF %ERRORLEVEL% NEQ 0 goto errorL%

%execjava% mdware.common.batch.util.control.BatchController MSTB995-07012 %CLS_HOME%\batch\properties release use
IF %ERRORLEVEL% NEQ 0 goto errorL%

%execjava% mdware.common.batch.util.control.BatchController MSTB995-07013 %CLS_HOME%\batch\properties release use
IF %ERRORLEVEL% NEQ 0 goto errorL%

%execjava% mdware.common.batch.util.control.BatchController MSTB995-07014 %CLS_HOME%\batch\properties release use
IF %ERRORLEVEL% NEQ 0 goto errorL%

%execjava% mdware.common.batch.util.control.BatchController MSTB995-07015 %CLS_HOME%\batch\properties release use
IF %ERRORLEVEL% NEQ 0 goto errorL%

%execjava% mdware.common.batch.util.control.BatchController MSTB995-07016 %CLS_HOME%\batch\properties release use
IF %ERRORLEVEL% NEQ 0 goto errorL%

%execjava% mdware.common.batch.util.control.BatchController MSTB995-07017 %CLS_HOME%\batch\properties release use
IF %ERRORLEVEL% NEQ 0 goto errorL%

%execjava% mdware.common.batch.util.control.BatchController MSTB995-07021 %CLS_HOME%\batch\properties release use
IF %ERRORLEVEL% NEQ 0 goto errorL%

%execjava% mdware.common.batch.util.control.BatchController MSTB995-07022 %CLS_HOME%\batch\properties release use
IF %ERRORLEVEL% NEQ 0 goto errorL%

%execjava% mdware.common.batch.util.control.BatchController MSTB995-07026 %CLS_HOME%\batch\properties release use
IF %ERRORLEVEL% NEQ 0 goto errorL%

%execjava% mdware.common.batch.util.control.BatchController MSTB995-07031 %CLS_HOME%\batch\properties release use
IF %ERRORLEVEL% NEQ 0 goto errorL%

%execjava% mdware.common.batch.util.control.BatchController MSTB995-07033 %CLS_HOME%\batch\properties release use
IF %ERRORLEVEL% NEQ 0 goto errorL%

%execjava% mdware.common.batch.util.control.BatchController MSTB995-07042 %CLS_HOME%\batch\properties release use
IF %ERRORLEVEL% NEQ 0 goto errorL%

%execjava% mdware.common.batch.util.control.BatchController MSTB995-07085 %CLS_HOME%\batch\properties release use
IF %ERRORLEVEL% NEQ 0 goto errorL%

%execjava% mdware.common.batch.util.control.BatchController MSTB995-07093 %CLS_HOME%\batch\properties release use
IF %ERRORLEVEL% NEQ 0 goto errorL%

%execjava% mdware.common.batch.util.control.BatchController MSTB995-07095 %CLS_HOME%\batch\properties release use
IF %ERRORLEVEL% NEQ 0 goto errorL%

:end

exit 0

:error

exit 1