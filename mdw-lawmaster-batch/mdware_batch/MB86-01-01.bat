@echo off
rem =======================================
rem MB86-01-01 不稼動判定処理１
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MB86-01-01 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 
