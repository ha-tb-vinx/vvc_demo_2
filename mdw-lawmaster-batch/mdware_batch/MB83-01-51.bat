@echo off
rem =======================================
rem MB83-01-51 ONLINE_TMP_BATマスタ作成処理
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MB83-01-51 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 
