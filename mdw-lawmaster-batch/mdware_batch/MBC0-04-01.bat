@echo off
rem =======================================
rem MBC0-04-01 アナライズ処理
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MBC0-04-01 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
