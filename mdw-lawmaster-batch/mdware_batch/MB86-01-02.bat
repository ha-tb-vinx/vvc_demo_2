@echo off
rem =======================================
rem MB86-01-02 不稼動判定処理２
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MB86-01-02 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 
