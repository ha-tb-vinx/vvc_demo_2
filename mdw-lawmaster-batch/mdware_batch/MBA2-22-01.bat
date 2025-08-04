@echo off
rem =======================================
rem MBA2-22-01 IF店別商品データ作成処理
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MBA2-22-01 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 
