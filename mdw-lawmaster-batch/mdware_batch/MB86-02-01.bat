@echo off
rem =======================================
rem MB86-02-01 単品仕入データ取込処理
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MB86-02-01 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 
