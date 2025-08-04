@echo off
rem =======================================
rem MB45-04-01 BKテーブル作成処理
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MB45-04-01 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 
