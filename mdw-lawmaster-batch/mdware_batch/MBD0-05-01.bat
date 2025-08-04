@echo off
rem =======================================
rem MBD0-05-01 バックアップ（AS400連携ファイル）
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MBD0-05-01 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 
