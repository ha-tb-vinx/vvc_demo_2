@echo off
rem =======================================
rem MB83-02-51 ONLINE店別商品展開処理
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MB83-02-51 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 
