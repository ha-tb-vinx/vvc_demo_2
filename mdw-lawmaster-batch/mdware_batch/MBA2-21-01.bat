@echo off
rem =======================================
rem MBA2-21-01 IF商品分類体系マスタ作成処理
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MBA2-21-01 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 
