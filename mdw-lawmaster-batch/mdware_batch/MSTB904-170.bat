@echo off
rem =======================================
rem MSTB904-170 DWH用マスタファイル送信
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB904-170 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
