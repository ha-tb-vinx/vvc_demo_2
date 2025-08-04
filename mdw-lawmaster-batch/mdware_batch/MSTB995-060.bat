@echo off
rem =======================================
rem MSTB995-060 計量器用マスタファイル送信
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB995-060 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
