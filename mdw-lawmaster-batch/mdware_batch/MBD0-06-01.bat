@echo off
rem =======================================
rem MBD0-06-01 バックアップ（AS400連携ＤＢ）
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MBD0-06-01 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 
