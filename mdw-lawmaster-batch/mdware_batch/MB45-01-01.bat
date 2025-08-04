@echo off
rem =======================================
rem MB45-01-01 TMPテーブル作成処理
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MB45-01-01 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 
