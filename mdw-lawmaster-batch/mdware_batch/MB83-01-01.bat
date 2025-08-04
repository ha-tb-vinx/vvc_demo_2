@echo off
rem =======================================
rem MB83-01-01 TMP BATマスタ作成処理
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MB83-01-01 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 
