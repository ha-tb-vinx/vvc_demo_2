@echo off
rem =======================================
rem MSTB904-180 DWH用マスタファイル送信
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB904-180 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
