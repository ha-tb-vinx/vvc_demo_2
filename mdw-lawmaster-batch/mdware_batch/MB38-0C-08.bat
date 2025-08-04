@echo off
rem =======================================
rem MB38-0C-08 店別例外自動作成
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MB38-0C-08 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
